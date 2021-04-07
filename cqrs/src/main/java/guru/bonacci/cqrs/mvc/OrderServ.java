package guru.bonacci.cqrs.mvc;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service wrapper for demo purposes...
 */
@Slf4j
@Service
@AllArgsConstructor
public class OrderServ {

	private final OrderRepo repo;
	private final KafkaTemplate<String, String> template;

    public List<OrderStuff> all() {
        return repo.findAll();
    }

    @Transactional(transactionManager = "chainedTxM")
    public OrderStuff cr(OrderStuff stuff) {
    	log.info("cre {}", stuff);
    	OrderStuff os = repo.save(stuff);
    	try {
			this.template.send("abc", "def").get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return os;
    }

    public OrderStuff get(Long id) throws RNFException {
        return repo.findById(id).orElseThrow(() -> new RNFException("No order with id :: " + id));
    }

    public OrderStuff re(String foo) throws RNFException {
        return repo.findByFoo(foo).orElseThrow(() -> new RNFException("No order :: " + foo));
    }

    public OrderStuff up(OrderStuff stuff) {
    	log.info("upd {}", stuff);
        return repo.save(stuff);
    }
    
    public void de(String foo) throws RNFException {
    	log.info("del {}", foo);
        var order = repo.findByFoo(foo).orElseThrow(() -> new RNFException("No order :: " + foo));
        repo.deleteById(order.getId());
    }
}
