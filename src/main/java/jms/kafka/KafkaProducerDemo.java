package jms.kafka;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaProducerDemo extends Thread {

	private KafkaProducer<Integer, String> kafkaProducer;

	private final boolean isAysnc;
	private final String topic;

	public KafkaProducerDemo(String topic, boolean isAysnc) {
		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.2.2:9092"); //,192.168.2.4:9092,192.168.2.5:9092
		properties.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaProducerDemo");
		//0 不需要确认（性能高，数据丢失）1：只需要leader确认，-1：需要全部确认（最安全，性能最低）
		properties.put(ProducerConfig.ACKS_CONFIG, "-1");
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.IntegerSerializer");
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringSerializer");
		properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "jms.kafka.MyPartitioner");
//	  优化项：	batch.size  大小超过就发送。  linger.ms 发送间隔  max.request.size（1M） 当前请求最大字节数
		kafkaProducer = new KafkaProducer<Integer, String>(properties);
		this.topic = topic;
		this.isAysnc = isAysnc;
	}

	@Override
	public void run() {
		int num = 0;
		while (num < 50) {
			String message = "message--" + num;
			System.out.println(message);
			if (isAysnc) {//异步
				kafkaProducer.send(new ProducerRecord<Integer, String>(topic, message), new Callback() {

					@Override
					public void onCompletion(RecordMetadata metadata, Exception exception) {
						System.out.println("aysnc-offest:" + metadata.offset() + "-> partition" + metadata.partition());
					}

				});
			} else {//同步
				try {
					RecordMetadata recordMetadata = kafkaProducer
							.send(new ProducerRecord<Integer, String>(topic, message)).get();
					System.out.println(
							"sync-offset:" + recordMetadata.offset() + "->partition" + recordMetadata.partition());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			num++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		KafkaProducerDemo kpd = new KafkaProducerDemo("testJava", true);
		kpd.start();
	}

}
