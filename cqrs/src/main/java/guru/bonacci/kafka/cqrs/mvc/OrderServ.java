package guru.bonacci.kafka.cqrs.mvc;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

/**
 * Pointless service wrapper for demo purposes...
 */
@Service
@AllArgsConstructor
public class OrderServ {

	private final OrderRepo repo;

    public OrderStuff cr(OrderStuff order) {
    	return repo.save(order);
    }

    public OrderStuff get(Long id) throws RNFException {
        return repo.findById(id).orElseThrow(() -> new RNFException("No order with id :: " + id));
    }

    public OrderStuff re(String extId) throws RNFException {
        return repo.findByExtId(extId).orElseThrow(() -> new RNFException("No order :: " + extId));
    }

    public OrderStuff up(OrderStuff order) {
        return repo.save(order);
    }
    
    public void de(String extId) throws RNFException {
        OrderStuff order = repo.findByExtId(extId).orElseThrow(() -> new RNFException("No order :: " + extId));
        repo.deleteById(order.getId());
    }
}
