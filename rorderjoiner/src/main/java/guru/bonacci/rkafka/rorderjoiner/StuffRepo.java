package guru.bonacci.rkafka.rorderjoiner;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public interface StuffRepo extends ReactiveMongoRepository<Stuff, String> {
 
    Mono<Stuff> findFirstByExtId(String extId);
}