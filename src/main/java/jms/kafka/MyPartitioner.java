package jms.kafka;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

//自定义分区
public class MyPartitioner implements Partitioner {

	private Random random = new Random();

	@Override
	public void configure(Map<String, ?> configs) {
		// TODO Auto-generated method stub

	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		List<PartitionInfo> list = cluster.partitionsForTopic(topic);
		int partitionNum = 0;
		if (key == null) {
			partitionNum = random.nextInt(list.size());
		} else {
			partitionNum = Math.abs(key.hashCode() & list.size());
		}
		System.out.println("key:" + key + "value:" + value + "partition:" + partitionNum);
		return partitionNum;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
