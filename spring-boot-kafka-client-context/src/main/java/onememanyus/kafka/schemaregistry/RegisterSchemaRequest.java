package onememanyus.kafka.schemaregistry;

import lombok.Data;

@Data
public class RegisterSchemaRequest {
	final String schema;
}
