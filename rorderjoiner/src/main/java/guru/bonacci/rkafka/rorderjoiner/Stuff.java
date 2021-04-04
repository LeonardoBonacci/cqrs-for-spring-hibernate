package guru.bonacci.rkafka.rorderjoiner;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "order-stuff")
public class Stuff {

    @Id 
    private String foo;
    private String bar;
}