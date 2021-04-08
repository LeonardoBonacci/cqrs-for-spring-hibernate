package guru.bonacci.cqrs.mvc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import guru.bonacci.cqrs.jpa.listeners.OrderStuffListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners({OrderStuffListener.class})
public class OrderStuff {

    @Id @GeneratedValue(strategy = GenerationType.AUTO) private Long id;
    @NotNull @Column(unique = true) private String foo;
    @NotNull @Column private String bar;
}
