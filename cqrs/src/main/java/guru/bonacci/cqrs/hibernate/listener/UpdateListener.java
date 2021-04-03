package guru.bonacci.cqrs.hibernate.listener;

import java.util.List;

import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.cqrs.hibernate.handler.PostUpdateEventHandler;

@Component
public class UpdateListener implements PostUpdateEventListener {

	private static final long serialVersionUID = 1L;

	private final List<PostUpdateEventHandler> handlers;
    private final ExecutorServiceFactory factory;

    @Autowired
    public UpdateListener(List<PostUpdateEventHandler> handlers, ExecutorServiceFactory factory) {
        this.handlers = handlers;
        this.factory = factory;
    }

    @Override
    public void onPostUpdate(PostUpdateEvent postUpdateEvent) {
        for (PostUpdateEventHandler han : handlers) {
            han.register(postUpdateEvent);
            this.factory.getExecutorService().execute(han);
        }
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister entityPersister) {
        return false;
    }
}