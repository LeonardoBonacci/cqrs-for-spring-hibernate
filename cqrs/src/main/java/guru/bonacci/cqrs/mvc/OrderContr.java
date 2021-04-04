package guru.bonacci.cqrs.mvc;

import java.util.List;

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

    @GetMapping
    public List<OrderStuff> all() {
        return serv.all();
    }

    @PostMapping
    public ResponseEntity<OrderStuff> c(@Valid @RequestBody OrderStuff stuff) {
    	return ResponseEntity.ok().body(serv.cr(stuff));
    }

    @GetMapping("/{foo}")
    public ResponseEntity<OrderStuff> r(@PathVariable(value = "foo") String foo) throws RNFException {
        return ResponseEntity.ok().body(serv.re(foo));
    }

    @PutMapping("/{id}") 
    public OrderStuff u(@PathVariable(value = "id") Long id, @Valid @RequestBody OrderStuff stuff) {
    	stuff.setId(id);
        return serv.up(stuff);
    }
    
    @DeleteMapping("/{foo}")
    public void d(@PathVariable(value = "foo") String foo) throws RNFException {
    	serv.de(foo);
    }
}
