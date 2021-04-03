package guru.bonacci.cqrs.hibernate.handler;

import org.hibernate.event.spi.PostInsertEvent;

public interface PostInsertEventHandler extends Runnable {
    void register(PostInsertEvent event);
}
