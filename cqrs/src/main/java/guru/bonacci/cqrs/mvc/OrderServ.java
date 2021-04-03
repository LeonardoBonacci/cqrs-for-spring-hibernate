package guru.bonacci.cqrs.mvc;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

/**
 * Pointless service wrapper for demo purposes...
 */
@Service
@AllArgsConstructor
public class OrderServ {

	private final OrderRepo repo;

    public List<OrderStuff> all() {
        return repo.findAll();
    }

    public OrderStuff cr(OrderStuff stuff) {
    	return repo.save(stuff);
    }

    public OrderStuff get(Long id) throws RNFException {
        return repo.findById(id).orElseThrow(() -> new RNFException("No order with id :: " + id));
    }

    public OrderStuff re(String extId) throws RNFException {
        return repo.findByExtId(extId).orElseThrow(() -> new RNFException("No order :: " + extId));
    }

    public OrderStuff up(OrderStuff stuff) {
        return repo.save(stuff);
    }
    
    public void de(String extId) throws RNFException {
        OrderStuff order = repo.findByExtId(extId).orElseThrow(() -> new RNFException("No order :: " + extId));
        repo.deleteById(order.getId());
    }
}
