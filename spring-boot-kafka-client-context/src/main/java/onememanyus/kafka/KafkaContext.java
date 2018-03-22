package onememanyus.kafka;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.ConfigDef;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j @RequiredArgsConstructor
public class KafkaContext {
	private static final Map<String,ConfigDef> CONFIGS = new HashMap<>();
	private static final ConfigDef extractConfig(Class<?> klass) {
		try {
			for(Field f : klass.getDeclaredFields()) {
				if(Modifier.isStatic(f.getModifiers()) && "CONFIG".equals(f.getName())) {
					f.setAccessible(true);
					return (ConfigDef)f.get(klass);
				}
			}
		} catch(Exception e) {
			log.error("Unable to extract CONFIG field from {}",klass,e);
		}
		return null;		
	}
	static {
		CONFIGS.put("admin", extractConfig(AdminClientConfig.class));
		CONFIGS.put("consumer", extractConfig(ConsumerConfig.class));
		CONFIGS.put("producer", extractConfig(ProducerConfig.class));
	}
	public static ConfigDef config(String name) {
		return CONFIGS.get(name);
	}
	
	
	final KafkaContext parent;
	final Properties   mine;
	
	public KafkaContext(Properties mine) {
		this(null,mine);
	}

	public Properties forConfigDef(Properties base,ConfigDef config) {
		if(parent != null)
			base = parent.forConfigDef(base,config);
		for(String name : config.names()) {
			String value = mine.getProperty(name);
			if(value != null) base.setProperty(name, value);
		}
		return base;
	}
	
	public Properties forConfigDef(ConfigDef config) {
		return forConfigDef(new Properties(),config);
	}
	

	
	
}
