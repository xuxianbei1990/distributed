package cache.redis;

import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class RedisJava {
	static Jedis jedis;

	public static void testIsRunSuccess() {
		System.out.println("连接成功");
		System.out.println("服务正在运行：" + jedis.ping());
	}

	/**
	 * 存储字符串
	 */
	static void doStorageString() {
		jedis.set("runoobkey", "www.baidu.com");
		System.out.print("redis 存储的字符串为" + jedis.get("runoobkey"));
	}

	/**
	 * 存储列表
	 */
	static void doStorageList() {
		jedis.set("site-list", "Runoob");
		jedis.set("site-list", "Google");
		jedis.set("site-list", "Taobao");

		List<String> list = jedis.lrange("site-list", 0, 2);
		for (int i = 0; i < list.size(); i++) {
			System.out.println("列表项为：" + list.get(i));
		}
	}

	static void keysSimple() {
		Set<String> keys = jedis.keys("*");
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			String key = it.next();
			System.out.println(key);
		}
	}

	public static void main(String[] args) {
		jedis = new Jedis("192.168.174.128", 6379);
		testIsRunSuccess();
		doStorageString();
	}

}
