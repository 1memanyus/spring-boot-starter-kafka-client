package onememanyus.kafka.schemaregistry;

import lombok.Data;

@Data
public class SchemaResponse {
	String  subject;
	Integer version;
	Integer id;
	String  schema;
}
