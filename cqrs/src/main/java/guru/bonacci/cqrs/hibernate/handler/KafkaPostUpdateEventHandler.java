package guru.bonacci.cqrs.hibernate.handler;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import guru.bonacci.cqrs.kafka.KafkaTopicConfig;
import guru.bonacci.cqrs.mvc.OrderServ;
import guru.bonacci.cqrs.mvc.OrderStuff;
import guru.bonacci.cqrs.mvc.RNFException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaPostUpdateEventHandler extends AbstractPostUpdateEventHandler {

	@Autowired OrderServ serv;
//	@Autowired KafkaTemplate<String, OrderStuff> kafkaTemplate;
	
    @Override
    public void run() {
        log.info("------HIBERNATE UPDATE EVENT------");
//        try {
//        	OrderStuff stuff = serv.get((Long)event.getId());
//            log.info("sending to broker {}", stuff);
//			kafkaTemplate.send(KafkaTopicConfig.TOPIC, stuff.getFoo(), stuff).get();
//		} catch (RNFException | InterruptedException | ExecutionException e) {
//			e.printStackTrace();
//		}
    }
}
