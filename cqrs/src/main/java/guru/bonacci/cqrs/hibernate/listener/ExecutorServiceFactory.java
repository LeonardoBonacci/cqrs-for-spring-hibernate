package guru.bonacci.cqrs.hibernate.listener;

import java.util.concurrent.ExecutorService;

public interface ExecutorServiceFactory {
    ExecutorService getExecutorService();
}
