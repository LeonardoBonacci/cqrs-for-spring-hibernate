package guru.bonacci.kafka.cqrs.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.kafka.cqrs.mvc.OrderServ;
import guru.bonacci.kafka.cqrs.mvc.OrderStuff;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoggingPostDeleteEventHandler extends AbstractPostDeleteEventHandler {

	@Autowired OrderServ serv;
	
    @Override
    public void run() {
        log.info("------HIBERNATE DELETE EVENT------");
        log.error(((OrderStuff)event.getEntity()).getExtId());
    }
}
