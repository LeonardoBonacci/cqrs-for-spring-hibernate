package guru.bonacci.rkafka.rorderjoiner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;

@Slf4j
public class SampleScenarios {

    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String[] TOPICS = {
        "orders",
        "order-enrich"
    };

    public static class KafkaTransform extends AbstractScenario {
        private final String sourceTopic;
        private final String destTopic;

        public KafkaTransform(String bootstrapServers, String sourceTopic, String destTopic) {
            super(bootstrapServers);
            this.sourceTopic = sourceTopic;
            this.destTopic = destTopic;
        }
        public Flux<?> flux() {
            KafkaSender<String, String> sender = sender(senderOptions());
            return KafkaReceiver.create(receiverOptions(Collections.singleton(sourceTopic)))
                                .receive()
                                .map(m -> SenderRecord.create(transform(m.key(), m.value()), m.receiverOffset()))
                                .as(sender::send)
                                .doOnNext(m -> m.correlationMetadata().acknowledge())
                                .doOnCancel(() -> close());
        }
        public ProducerRecord<String, String> transform(String key, String val) {
            return new ProducerRecord<>(destTopic, key, val + UUID.randomUUID().toString());
        }
    }


    static abstract class AbstractScenario {
        String bootstrapServers = BOOTSTRAP_SERVERS;
        String groupId = "sample-group";
        KafkaSender<String, String> sender;
        List<Disposable> disposables = new ArrayList<>();

        AbstractScenario(String bootstrapServers) {
            this.bootstrapServers = bootstrapServers;
        }
        public abstract Flux<?> flux();

        public void runScenario() throws InterruptedException {
            flux().blockLast();
            close();
        }

        public void close() {
            if (sender != null)
                sender.close();
            for (Disposable disposable : disposables)
                disposable.dispose();
        }

        public SenderOptions<String, String> senderOptions() {
            Map<String, Object> props = new HashMap<>();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            props.put(ProducerConfig.CLIENT_ID_CONFIG, "sample-producer");
            props.put(ProducerConfig.ACKS_CONFIG, "all");
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            return SenderOptions.create(props);
        }

        public KafkaSender<String, String> sender(SenderOptions<String, String> senderOptions) {
            sender = KafkaSender.create(senderOptions);
            return sender;
        }

        public ReceiverOptions<String, String> receiverOptions() {
            Map<String, Object> props = new HashMap<>();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
            props.put(ConsumerConfig.CLIENT_ID_CONFIG, "sample-consumer");
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            return ReceiverOptions.<String, String>create(props);
        }

        public ReceiverOptions<String, String> receiverOptions(Collection<String> topics) {
            return receiverOptions()
                    .addAssignListener(p -> log.warn("Group {} partitions assigned {}", groupId, p))
                    .addRevokeListener(p -> log.warn("Group {} partitions assigned {}", groupId, p))
                    .subscription(topics);
        }
     }

    public static void main(String[] args) throws Exception {
        AbstractScenario sampleScenario = new KafkaTransform(BOOTSTRAP_SERVERS, TOPICS[0], TOPICS[1]);
        sampleScenario.runScenario();
    }
}