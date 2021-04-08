package guru.bonacci.cqrs.jpa.listeners;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import guru.bonacci.cqrs.BeanUtil;
import guru.bonacci.cqrs.kafka.KafkaTopicConfig;
import guru.bonacci.cqrs.mvc.OrderServ;
import guru.bonacci.cqrs.mvc.OrderStuff;
import guru.bonacci.cqrs.mvc.RNFException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class OrderStuffListener {

	private final KafkaTemplate<String, OrderStuff> template;

	@PostPersist @PostUpdate 
	public void persist(OrderStuff stuff) {
		OrderServ serv = BeanUtil.getBean(OrderServ.class);
		
		try {
			stuff = serv.get(stuff.getId());
			log.info("sending to broker '{}'", stuff);
			this.template.send(KafkaTopicConfig.TOPIC, stuff.getFoo(), stuff);
		} catch (RNFException e) {
			e.printStackTrace();
		}
	}
	
	@PostRemove 
	public void remove(OrderStuff stuff) {
		log.info("sending tombstone to broker for key '{}'", stuff.getFoo());
		this.template.send(KafkaTopicConfig.TOPIC, stuff.getFoo(), null);
	}

}