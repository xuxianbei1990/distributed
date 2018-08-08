package soa.memcached;

import java.util.Map;

import com.schooner.MemCached.MemcachedItem;
import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

public class MemcachedClient {

	static void init() {
		String[] servers = {"192.168.174.128:11211"};
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(servers);
		// 容错
		pool.setFailover(true);
		// 设置初始连接数
		pool.setInitConn(10);
		pool.setMinConn(5);
		//设置最大连接数
		pool.setMaxConn(25);
		// 设置链接池维护线程的睡眠时间
		pool.setMaintSleep(30);
		//使用Nagle算法
		pool.setNagle(false);
		//设置socket的读取等待超时时间
		pool.setSocketTO(3000);
		//设置连接心跳检测开关
		pool.setAliveCheck(true);
		// 设置hash算法
		pool.setHashingAlg(SockIOPool.CONSISTENT_HASH);
		pool.initialize();
	}
	
	public static void functionDisplay() {
		MemCachedClient memCachedClient = new MemCachedClient();
		System.out.println("SET: " + memCachedClient.set("key1", "value1"));

		
		memCachedClient.set("key", 1);
		System.out.println(memCachedClient.get("key"));
		memCachedClient.add("key", 1);
		System.out.println(memCachedClient.get("key"));
		memCachedClient.set("key1", 2);
		memCachedClient.set("key2", 3);
		memCachedClient.set("key", 2);
		memCachedClient.replace("key", 3);
		
	    Object value = memCachedClient.get("key");
	    System.out.println(value);
	    
	    // 获取一组数据
	    String[] keys = {"key1", "key2"};
	    Map<String, Object> values = memCachedClient.getMulti(keys);
	    System.out.println(values);
	    
	    memCachedClient.add("key-name", "库拉");
	    memCachedClient.prepend("key-name", "hello");
	    memCachedClient.append("key-name", "必杀技");
	    
	    //并发操作
	    MemcachedItem item = memCachedClient.gets("key");
	    memCachedClient.cas("key", (Integer)item.getValue() + 1, item.getCasUnique());
	    
	    // 增加 1
	    memCachedClient.incr("key", 1);
	    // 减 1
	    memCachedClient.decr("key", 1);
	}
	
	public static void main(String[] args) {
		
		init();
		functionDisplay();
	}

}
