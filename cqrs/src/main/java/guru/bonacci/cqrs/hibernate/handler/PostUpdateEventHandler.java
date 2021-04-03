package guru.bonacci.cqrs.hibernate.handler;

import org.hibernate.event.spi.PostUpdateEvent;

public interface PostUpdateEventHandler extends Runnable {
    void register(PostUpdateEvent event);
}
