package guru.bonacci.kafka.cqrs.mvc;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderContr {

	private final OrderRepo repo;

    @PostMapping
    public ResponseEntity<OrderStuff> c(@Valid @RequestBody OrderStuff order) {
    	return ResponseEntity.ok().body(repo.save(order));
    }

    @GetMapping("/{extId}")
    public ResponseEntity<OrderStuff> r(@PathVariable(value = "extId") String extId) throws RNFException {
        OrderStuff order = repo.findByExtId(extId).orElseThrow(() -> new RNFException("No order with :: " + extId));
        return ResponseEntity.ok().body(order);
    }

    @PutMapping("/{id}") 
    public OrderStuff u(@PathVariable(value = "id") Long id, @Valid @RequestBody OrderStuff order) {
    	order.setId(id);
        return repo.save(order);
    }
    
    @DeleteMapping("/{extId}")
    public void d(@PathVariable(value = "extId") String extId) throws RNFException {
        OrderStuff order = repo.findByExtId(extId).orElseThrow(() -> new RNFException("No order with :: " + extId));
        repo.deleteById(order.getId());
    }
}
