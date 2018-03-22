package onememanyus.spring.config;

import org.springframework.context.annotation.Configuration;

import onememanyus.spring.KafkaAnnotationBeanPostProcessor;

@Configuration
public class KafkaConfiguration {

	public KafkaAnnotationBeanPostProcessor kafkaPostProcessor() {
		return new KafkaAnnotationBeanPostProcessor();
	}
	
}
