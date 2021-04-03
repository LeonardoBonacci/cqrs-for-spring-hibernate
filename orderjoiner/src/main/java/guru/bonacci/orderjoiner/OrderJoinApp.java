package guru.bonacci.orderjoiner;

import java.util.function.BiFunction;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OrderJoinApp {

	public static void main(String[] args) {
		SpringApplication.run(OrderJoinApp.class, args);
	}

	public static class KStreamOrderJoinApp {

		@Bean
		public BiFunction<KStream<String, String>, KTable<String, String>, KStream<String, String>> process() {
			return (orders, stuff) -> 
				orders
					.peek((k,order) -> System.out.println("in: " + order))
					.leftJoin(stuff, 
						(or, st) -> or.toString().trim() + "<>" + st.toString().trim(),
						Joined.with(Serdes.String(), Serdes.String(), Serdes.String()))
					.peek((k,enrich) -> System.out.println("out: " + enrich));
		}
	}
}
