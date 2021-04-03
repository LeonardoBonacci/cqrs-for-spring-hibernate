package guru.bonacci.cqrs.hibernate.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import guru.bonacci.cqrs.mvc.OrderServ;
import guru.bonacci.cqrs.mvc.OrderStuff;
import guru.bonacci.cqrs.mvc.RNFException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaPostInsertEventHandler extends AbstractPostInsertEventHandler {
	
	@Autowired OrderServ serv;
	@Autowired KafkaTemplate<String, OrderStuff> kafkaTemplate;

    @Override
    public void run() {
        log.info("------HIBERNATE INSERT EVENT------");
        try {
        	OrderStuff stuff = serv.get((Long)event.getId());
            log.info("sending to broker {}", stuff);
    	    kafkaTemplate.send("major-tom", stuff.getExtId(), stuff);
		} catch (RNFException e) {
			e.printStackTrace();
		}
    }
}
