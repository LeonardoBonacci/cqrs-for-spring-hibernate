package guru.bonacci.kafka.cqrs.handler;

import org.hibernate.event.spi.PostDeleteEvent;

public interface PostDeleteEventHandler extends Runnable {
    void register(PostDeleteEvent event);
}
