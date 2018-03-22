package onememanyus.kafka.reactive;

import org.apache.avro.Schema;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import onememanyus.junit.KafkaCluster;

public class ReactiveSchemaRegistryClientTests {

	@Rule 
	public KafkaCluster cluster = new KafkaCluster();
	
	
	@Test
	public void testRegisterSchema() {
		
		ReactiveSchemaRegistryClient client = new ReactiveSchemaRegistryClient(cluster.getSchemaRegistry().connectString());
		Schema schema = client.getById(1).block();
		
	}
	
}
