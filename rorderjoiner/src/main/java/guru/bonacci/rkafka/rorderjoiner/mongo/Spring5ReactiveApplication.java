package guru.bonacci.rkafka.rorderjoiner.mongo;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Spring5ReactiveApplication {

	public static void main(String[] args) {
        SpringApplication.run(Spring5ReactiveApplication.class, args);
    }

	@Bean
	CommandLineRunner demo(StuffRepo repo) {
		return args -> {
		    repo.deleteAll().subscribe();

		    // save a couple of customers
		    repo.saveAll(Arrays.asList(new Stuff(1l, "something", "fibo"), new Stuff(2l, "else", "nacci"))).subscribe();
			repo.findAll().subscribe(System.out::println);
			repo.findFirstByExtId("fibon").subscribe(System.out::println);
		};
	}
}
