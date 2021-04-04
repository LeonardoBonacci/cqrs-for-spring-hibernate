package guru.bonacci.rkafka.rorderjoiner;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Slf4j
@SpringBootApplication
@AllArgsConstructor
public class ReactiveKafkaApp {

	public static void main(String[] args) {
        SpringApplication.run(ReactiveKafkaApp.class, args);
    }

	static final String TOPIC_IN = "orders";
	static final String TOPIC_OUT = "order-enrich";

	private final KafkaReceiver<String, String> receiver;
	private final KafkaSender<String, String> sender;
	private final StuffRepo repo;

	
    public Flux<?> flux() {
        return receiver
        		.receive()
        		.doOnNext(rec -> log.info("Received {}", rec.value()))
        		.flatMap(m -> {
        			Mono<ProducerRecord<String, String>> joined = transform(m.key(), m.value());
        			return joined.map(j -> SenderRecord.create(j, m.receiverOffset()));
    			})
                .as(sender::send)
                .doOnNext(m -> m.correlationMetadata().acknowledge())
                .doOnCancel(() -> close());
    }
    
    // FIXME json deserialize the message and use variables
    public Mono<ProducerRecord<String, String>> transform(String key, String o) {
    	log.warn("search on {}", key);
    	return repo.findFirstByExtId(key)
			.doOnNext(stuff -> log.info("Found stuff {}", stuff.toString()))
            .map(Stuff::toString)
    		.map(stuffString -> o.toString() + "<>" + stuffString)
    		.map(transformed -> new ProducerRecord<>(TOPIC_OUT, key, transformed));
    }
    
    public void close() {
        if (sender != null)
            sender.close();
    }

	@Bean
	CommandLineRunner runner() {
		return args -> {
			repo.findAll().subscribe(System.out::println);

			// trigger consumption
            flux().blockLast();
            close();
		};
	}
}
