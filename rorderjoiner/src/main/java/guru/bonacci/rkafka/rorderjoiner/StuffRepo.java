package guru.bonacci.rkafka.rorderjoiner;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public interface StuffRepo extends ReactiveCrudRepository<Stuff, String> {
 
    Mono<Stuff> findFirstByFoo(String foo);
}