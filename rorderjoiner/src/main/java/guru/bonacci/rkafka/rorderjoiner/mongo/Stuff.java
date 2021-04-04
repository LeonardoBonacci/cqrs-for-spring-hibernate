package guru.bonacci.rkafka.rorderjoiner.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Stuff {

    private long id;
    private String stuff;
    @Id private String extId;
}