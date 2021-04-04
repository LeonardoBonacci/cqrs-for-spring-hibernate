package guru.bonacci.rkafka.rorderjoiner.mongo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public interface StuffRepo extends ReactiveMongoRepository<Stuff, String> {
 
    Mono<Stuff> findFirstByExtId(String extId);
    Mono<Stuff> findFirstByExtId(Mono<String> extId);
}