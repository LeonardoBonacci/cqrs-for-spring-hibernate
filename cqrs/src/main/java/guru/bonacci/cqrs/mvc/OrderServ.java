package guru.bonacci.cqrs.mvc;

import java.util.List;

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

    public List<OrderStuff> all() {
        return repo.findAll();
    }
    
    @Transactional(transactionManager = "chainedTxM")
    public OrderStuff cr(OrderStuff stuff) {
    	log.info("cre {}", stuff);
    	return repo.save(stuff);
    }

    public OrderStuff get(Long id) throws RNFException {
        return repo.findById(id).orElseThrow(() -> new RNFException("No order with id :: " + id));
    }

    public OrderStuff re(String foo) throws RNFException {
        return repo.findByFoo(foo).orElseThrow(() -> new RNFException("No order :: " + foo));
    }

    @Transactional(transactionManager = "chainedTxM")
    public OrderStuff up(OrderStuff stuff) {
    	log.info("upd {}", stuff);
        return repo.save(stuff);
    }
    
    @Transactional(transactionManager = "chainedTxM")
    public void de(String foo) throws RNFException {
    	log.info("del {}", foo);
        var order = repo.findByFoo(foo).orElseThrow(() -> new RNFException("No order :: " + foo));
        repo.deleteById(order.getId());
    }
}
