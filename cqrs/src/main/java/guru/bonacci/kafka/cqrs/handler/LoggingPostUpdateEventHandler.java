package guru.bonacci.kafka.cqrs.handler;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoggingPostUpdateEventHandler extends AbstractPostUpdateEventHandler {
	
	@Override
    public void run() {
		log.info("------HIBERNATE EVENT------");
		log.info("Thread ID of event listener: " + Thread.currentThread().getId());

        StringBuilder sb = new StringBuilder();
        sb.append("Update to entity ")
                .append(this.event.getPersister().getEntityMetamodel().getName())
                .append(" with ID ")
                .append(this.event.getId())
                .append(" was committed by Hibernate. ")
                .append("The following fields were updated: \n");

        for (int p : this.event.getDirtyProperties()) {
            sb.append("\t");
            sb.append(this.event.getPersister().getEntityMetamodel().getProperties()[p].getName());
            sb.append(" (Old value: ")
                    .append(this.event.getOldState()[p])
                    .append(", New value: ")
                    .append(this.event.getState()[p])
                    .append(")\n");
        }

        log.info(sb.toString());
    }
}
