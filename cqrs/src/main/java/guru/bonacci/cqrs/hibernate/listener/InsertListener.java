package guru.bonacci.cqrs.hibernate.listener;

import java.util.List;

import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.cqrs.hibernate.handler.PostInsertEventHandler;

@Component
public class InsertListener implements PostInsertEventListener {

    private static final long serialVersionUID = 1L;

	private final List<PostInsertEventHandler> handlers;
    private final ExecutorServiceFactory factory;

    @Autowired
    public InsertListener(List<PostInsertEventHandler> handlers, ExecutorServiceFactory factory) {
        this.handlers = handlers;
        this.factory = factory;
    }

    @Override
    public void onPostInsert(PostInsertEvent postInsertEvent) {
        for (PostInsertEventHandler han : handlers) {
            han.register(postInsertEvent);
            this.factory.getExecutorService().execute(han);
        }
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister entityPersister) {
        return false;
    }
}
