package onememanyus.kafka.reactive;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.avro.Schema;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import onememanyus.kafka.schemaregistry.RegisterSchemaRequest;
import onememanyus.kafka.schemaregistry.SchemaResponse;
import reactor.core.publisher.Mono;


public class ReactiveSchemaRegistryClient {
	
	final WebClient     client;
	final Schema.Parser parser = new Schema.Parser();
	final Map<Integer,Mono<Schema>> cachedSchemas = new ConcurrentHashMap<>();
	
	public ReactiveSchemaRegistryClient(WebClient client) {
		this.client = client;
	}
	
	public ReactiveSchemaRegistryClient(String url) {
		this(WebClient.create(url));
	}
	
	public Mono<Schema> getUncachedSchemaById(int id) {
		return client.get()
			.uri("/schemas/ids/{id}",id).accept(MediaType.APPLICATION_JSON)
			.retrieve()
			.bodyToMono(String.class)
			.map(parser::parse);
	}
	
	public Mono<Schema> getById(int id) {
		return cachedSchemas.computeIfAbsent(id, this::getUncachedSchemaById);
	}
	
	public Mono<Integer> register(String subject,Schema schema) {
		
		return client.post()
				.uri("/subjects/{id}",subject).accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.CONTENT_TYPE,"application/vnd.schemaregistry.v1+json")
				.syncBody(new RegisterSchemaRequest(schema.toString(false)))
				.retrieve()
				.bodyToMono(SchemaResponse.class)
				.map(SchemaResponse::getId)
		;
	}
	
}
