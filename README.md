# Streams in times of Rest
El amor en los tiempos del cólera

Pluggable mechanism based on JPA Event Listeners to export data to Kafka topics without interference in the business logic/code. 
Sink this Kafka topic into ElasticSearch and use Reactor Kafka with Reactive Spring Data Reactve ElasticSearch to join/enrich (with) this data, sourcing it back to another topic.
