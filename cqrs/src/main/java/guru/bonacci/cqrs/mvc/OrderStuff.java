package guru.bonacci.cqrs.mvc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class OrderStuff {

    @Id @GeneratedValue(strategy = GenerationType.AUTO) private long id;
    @NotNull @Column private String stuff;
    @NotNull @Column(unique = true) private String extId;
}
