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

	private final OrderServ serv;

    @PostMapping
    public ResponseEntity<OrderStuff> c(@Valid @RequestBody OrderStuff order) {
    	return ResponseEntity.ok().body(serv.cr(order));
    }

    @GetMapping("/{extId}")
    public ResponseEntity<OrderStuff> r(@PathVariable(value = "extId") String extId) throws RNFException {
        return ResponseEntity.ok().body(serv.re(extId));
    }

    @PutMapping("/{id}") 
    public OrderStuff u(@PathVariable(value = "id") Long id, @Valid @RequestBody OrderStuff order) {
    	order.setId(id);
        return serv.up(order);
    }
    
    @DeleteMapping("/{extId}")
    public void d(@PathVariable(value = "extId") String extId) throws RNFException {
    	serv.de(extId);
    }
}
