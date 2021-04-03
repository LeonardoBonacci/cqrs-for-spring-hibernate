package guru.bonacci.kafka.cqrs.handler;

import org.hibernate.event.spi.PostUpdateEvent;

public abstract class AbstractPostUpdateEventHandler implements PostUpdateEventHandler {
    protected PostUpdateEvent event;

    @Override
    public final void register(PostUpdateEvent ev) {
        this.event = ev;
    }
}
