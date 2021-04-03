package guru.bonacci.kafka.cqrs.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.kafka.cqrs.mvc.OrderServ;
import guru.bonacci.kafka.cqrs.mvc.RNFException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoggingPostInsertEventHandler extends AbstractPostInsertEventHandler {
	
	@Autowired OrderServ serv;
	
    @Override
    public void run() {
        log.info("------HIBERNATE INSERT EVENT------");
        try {
            log.error(serv.get((Long)event.getId()).toString());
		} catch (RNFException e) {
			e.printStackTrace();
		}
    }
}
