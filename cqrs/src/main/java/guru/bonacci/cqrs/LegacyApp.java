package guru.bonacci.cqrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class LegacyApp {

	public static void main(String[] args) {
		SpringApplication.run(LegacyApp.class, args);
	}
}
