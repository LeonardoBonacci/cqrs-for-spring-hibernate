package guru.bonacci.cqrs.hibernate.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.cqrs.mvc.OrderServ;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaPostInsertEventHandler extends AbstractPostInsertEventHandler {
	
	@Autowired OrderServ serv;
//	@Autowired KafkaTemplate<String, OrderStuff> kafkaTemplate;

    @Override
    public void run() {
        log.info("------HIBERNATE INSERT EVENT------");
//        try {
//        	OrderStuff stuff = serv.get((Long)event.getId());
//            log.info("sending to broker {}", stuff);
////			kafkaTemplate.send(KafkaTopicConfig.TOPIC, stuff.getFoo(), stuff).get();
//		} catch (InterruptedException | ExecutionException | RNFException e) {
//			e.printStackTrace();
//		}
    }
}
