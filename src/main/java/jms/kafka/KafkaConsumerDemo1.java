package jms.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerDemo1 extends Thread {

    private KafkaConsumer<Integer, String> kafkaConsumer;

    public KafkaConsumerDemo1(String topic) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.2.2:9092");//,192.168.2.4:9092,192.168.2.5:9092
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaConsumerDemo");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.IntegerDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        //latest;最新
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        kafkaConsumer = new KafkaConsumer<Integer, String>(properties);
//        指定某个主题分区消费
//        TopicPartition topicPartition = new TopicPartition(topic, 0);
//        kafkaConsumer.assign(Arrays.asList(topicPartition));
        kafkaConsumer.subscribe(Collections.singletonList(topic));
    }

    @Override
    public void run() {
        while (true) {
            //max.poll.records
            ConsumerRecords<Integer, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<Integer, String> consumerRecord : consumerRecords) {
                System.out.println("第二个消费者的partition:" +  consumerRecord.partition() + "message receive:" + consumerRecord.value());
//				kafkaConsumer.commitAsync();
            }

        }
    }

    public static void main(String[] args) {
        new KafkaConsumerDemo1("testJava").start();
//        TopicPartition topicPartition = new TopicPartition("test", 0);
//        System.out.println(Arrays.asList(topicPartition));
    }

}
