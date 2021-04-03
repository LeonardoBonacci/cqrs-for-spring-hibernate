package guru.bonacci.kafka.cqrs.handler;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoggingPostInsertEventHandler extends AbstractPostInsertEventHandler {
    @Override
    public void run() {
        log.info("------HIBERNATE EVENT------");
        String sb = "New entity " +
                this.event.getPersister().getEntityMetamodel().getName() +
                " with ID " +
                this.event.getId() +
                " was committed by Hibernate.\n";
        log.info(sb);
    }
}
