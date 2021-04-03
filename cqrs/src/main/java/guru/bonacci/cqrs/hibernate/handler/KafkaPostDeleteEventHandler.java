package guru.bonacci.cqrs.hibernate.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import guru.bonacci.cqrs.mvc.OrderServ;
import guru.bonacci.cqrs.mvc.OrderStuff;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaPostDeleteEventHandler extends AbstractPostDeleteEventHandler {

	@Autowired OrderServ serv;
	@Autowired KafkaTemplate<String, OrderStuff> kafkaTemplate;
	
    @Override
    public void run() {
        log.info("------HIBERNATE DELETE EVENT------");

        String extId = ((OrderStuff)event.getEntity()).getExtId();
        log.info("sending tombstone to broker {}", extId);
	    kafkaTemplate.send("major-tom", extId, null);
    }
}
