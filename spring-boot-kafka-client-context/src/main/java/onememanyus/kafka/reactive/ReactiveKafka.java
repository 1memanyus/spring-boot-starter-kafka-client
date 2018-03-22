package onememanyus.kafka.reactive;

import java.util.function.Function;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Header;

import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuple4;
import reactor.util.function.Tuple5;
import reactor.util.function.Tuples;

public class ReactiveKafka {
	
	public static final <K,V> Function<Tuple2<K,V>,Tuple3<String,K,V>> onTopic(final String topic) {
		return (tuple) -> Tuples.of(topic, tuple.getT1(), tuple.getT2());
	}
	
	public static final <K,V> Function<Tuple3<String,K,V>,Tuple4<String,Integer,K,V>> onPartition(final int partition) {
		return (tuple) -> Tuples.of(tuple.getT1(),partition,tuple.getT2(),tuple.getT3());
	}
	
	public static final <K,V> Function<Tuple2<K,V>,ProducerRecord<K,V>> toProducerRecord(final String topic) {
		return (tuple) -> new ProducerRecord<>(topic,tuple.getT1(),tuple.getT2());
	}
	
	public static final <K,V> ProducerRecord<K,V> toProducerRecord(Tuple3<String,K,V> tuple) {
		return new ProducerRecord<>(tuple.getT1(),tuple.getT2(),tuple.getT3());
	}
	
	public static final <K,V> ProducerRecord<K,V> toProducerRecord(Tuple4<String,Integer,K,V> tuple) {
		return new ProducerRecord<>(tuple.getT1(),tuple.getT2(),tuple.getT3(),tuple.getT4());
	}
	
	public static final <K,V> ProducerRecord<K,V> toProducerRecord(Tuple5<String,Integer,K,V,Iterable<Header>> tuple) {
		return new ProducerRecord<>(tuple.getT1(),tuple.getT2(),tuple.getT3(),tuple.getT4(),tuple.getT5());
	}
	
	public static final <K,V> Function<ProducerRecord<K,V>,Mono<RecordMetadata>> send(final Producer<K,V> producer) {
		return (record) -> {
			return Mono.create((sink) -> { 
				producer.send(record, (metadata,exception) -> {
					if(exception != null) {
						sink.error(exception);
						return;
					}
					sink.success(metadata);
				});
			});
		};
	}
	
	
	
}
