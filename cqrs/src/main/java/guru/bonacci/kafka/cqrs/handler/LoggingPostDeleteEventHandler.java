package guru.bonacci.kafka.cqrs.handler;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoggingPostDeleteEventHandler extends AbstractPostDeleteEventHandler {

	@Override
    public void run() {
        System.out.println("------HIBERNATE EVENT------");
        System.out.println("Thread ID of event listener: " + Thread.currentThread().getId());

        StringBuilder sb = new StringBuilder();
        sb.append("Delete entity ")
                .append(this.event.getPersister().getEntityMetamodel().getName())
                .append(" with ID ")
                .append(this.event.getId())
                .append(" was committed by Hibernate. ");

        log.info(sb.toString());
    }    
}
