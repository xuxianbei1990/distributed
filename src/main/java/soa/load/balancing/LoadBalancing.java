package soa.load.balancing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * 负载均衡
 * 
 * @author xuxb
 *
 */
public class LoadBalancing {
	static Map<String, Integer> serverWeightMap;
	static Integer pos = 0;

	/**
	 * 本来是配置数据库的
	 */
	public void roundRobin() {
		serverWeightMap = new HashMap<String, Integer>();
		serverWeightMap.put("192.168.1.100", 1);
		serverWeightMap.put("192.168.1.101", 1);

		// 权重4
		serverWeightMap.put("192.168.1.102", 4);
		serverWeightMap.put("192.168.1.103", 1);

		// 权重3
		serverWeightMap.put("192.168.1.105", 3);
		serverWeightMap.put("192.168.1.106", 1);
	}

	/**
	 * 轮询 将请求按照顺序轮流分配到后端，不关心服务器实际连接数和当前的系统负载。
	 */
	public static String testRoundRobin() {

		Map<String, Integer> serverMap = new HashMap<String, Integer>();
		serverMap.putAll(serverWeightMap);

		Set<String> keySet = serverMap.keySet();
		ArrayList<String> keyList = new ArrayList<String>();
		keyList.addAll(keySet);

		String server = null;

		synchronized (pos) {
			if (pos >= keySet.size()) {
				pos = 0;
			}
			server = keyList.get(pos);
			pos ++;
		}
		return server;
	}
	
	/**
	 * 随机  轮询的改进版。
	 * @return
	 */
	public static String testRandom() {
		Map<String, Integer> serverMap = new HashMap<String, Integer>();
		serverMap.putAll(serverWeightMap);
		
		//取得Ip地址list
		Set<String> keySet = serverMap.keySet();
		ArrayList<String> keyList = new ArrayList<String>();
		keyList.addAll(keySet);
		Random random = new Random();
		int randomPos = random.nextInt(keyList.size());
		
		String server = keyList.get(randomPos);
		return server;
	}

	/**
	 * 源地址哈希（Hash）法
	 * 该算法保证了相同的客户端ip地址会被hash到同一个服务器。直到服务器列表变更。
	 * 
	 * @param remoteip
	 * @return
	 */
	public static String testConsumerHash(String remoteip) {
		
		Map<String, Integer> serverMap = new HashMap<String, Integer>();
		serverMap.putAll(serverWeightMap);
		
		Set<String> keySet = serverMap.keySet();
		ArrayList<String> keyList = new ArrayList<String>();
		keyList.addAll(keySet);
		
		int hashCode = remoteip.hashCode();
		int serverListSize = keyList.size();
		int serverPos = hashCode % serverListSize;
		
		return keyList.get(serverPos);
	}
	
	public static void main(String[] args) {

	}
}
