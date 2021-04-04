package guru.bonacci.rkafka.rorderjoiner;

import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.client.reactive.ReactiveRestClients;
import org.springframework.data.elasticsearch.config.AbstractReactiveElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories;

@EnableReactiveElasticsearchRepositories
public class ElasticReactiveConfig extends AbstractReactiveElasticsearchConfiguration {

	@Override
	public ReactiveElasticsearchClient reactiveElasticsearchClient() {
		return ReactiveRestClients.create(ClientConfiguration.localhost());
	}
}