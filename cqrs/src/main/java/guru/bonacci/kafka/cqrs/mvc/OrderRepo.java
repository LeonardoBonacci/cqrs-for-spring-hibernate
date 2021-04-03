package guru.bonacci.kafka.cqrs.mvc;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<OrderStuff, Long> {
	
	Optional<OrderStuff> findByExtId(String extId); 
}
