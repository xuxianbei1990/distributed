package jms.kafka;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class KafkaConsumerDemo extends Thread {

	private KafkaConsumer<Integer, String> kafkaConsumer;

	public KafkaConsumerDemo(String topic) {
		Properties properties = new Properties();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.2.3:9092,192.168.2.4:9092,192.168.2.5:9092");
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaConsumerDemo");
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.IntegerDeserializer");
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringDeserializer");
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		kafkaConsumer = new KafkaConsumer<Integer, String>(properties);
		kafkaConsumer.subscribe(Collections.singletonList(topic));
	}

	@Override
	public void run() {
		while (true) {
			ConsumerRecords<Integer, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(100));
			for (ConsumerRecord<Integer, String> consumerRecord: consumerRecords) {
				System.out.println(consumerRecord.partition() + "message receive:" + consumerRecord.value());
//				kafkaConsumer.commitAsync();
			}

		}
	}
	
	public static void main(String[] args) {
		new KafkaConsumerDemo("test").start();;
	}
}
