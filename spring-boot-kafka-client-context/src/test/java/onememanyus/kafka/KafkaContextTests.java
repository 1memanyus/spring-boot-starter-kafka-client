package onememanyus.kafka;

import org.junit.Assert;
import org.junit.Test;

public class KafkaContextTests {

	@Test
	public void testExtraction() {
		Assert.assertNotNull("AdminClient configuration extracted", KafkaContext.config("admin"));
		Assert.assertNotNull("Consumer configuration extracted",    KafkaContext.config("consumer"));
		Assert.assertNotNull("Producer configuration extracted",    KafkaContext.config("producer"));
	}
	
}
