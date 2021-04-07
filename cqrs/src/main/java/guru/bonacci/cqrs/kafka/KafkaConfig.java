package guru.bonacci.cqrs.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

@Configuration
public class KafkaConfig {

	@Bean
	public ProducerFactory<String, String> producerFactory() {
		DefaultKafkaProducerFactory<String, String> f 
				= new DefaultKafkaProducerFactory<>(senderProps());
		f.setTransactionIdPrefix("tx-");
		return f;
	}

	private Map<String, Object> senderProps() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "me-again");
		return props;
	}

	@Bean
    public KafkaTemplate<String, String> kafkaTemplateServingDetailsListener() {
        return new KafkaTemplate<String, String>(producerFactory());
    }
	
	@Bean
	public KafkaTransactionManager<String, String> kafkaTransactionManager() {
		KafkaTransactionManager<String, String> ktm = new KafkaTransactionManager<>(producerFactory());
		ktm.setTransactionSynchronization(AbstractPlatformTransactionManager.SYNCHRONIZATION_ON_ACTUAL_TRANSACTION);
		return ktm;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		return new JpaTransactionManager();
	}

	@Bean
	public ChainedTransactionManager chainedTxM(JpaTransactionManager jpa, KafkaTransactionManager<?, ?> kafka) {
		return new ChainedTransactionManager(jpa, kafka);
	}
}