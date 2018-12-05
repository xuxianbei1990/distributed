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

    static void doExpireString() {
        for (int i = 0; i < 100; i++) {
            // NX是不存在时才set， XX是存在时才set， EX是秒，PX是毫秒
            jedis.set("expirekey", "expirekey www.baidu.com", "XX", "EX", 120);
            System.out.print("redis 存储的字符串为" + jedis.get("expirekey"));
        }

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
//        for (int i = 0; i < 10; i++) {
        jedis = new Jedis("192.168.2.2", 6379);
        jedis.auth("123456");
        testIsRunSuccess();
//        }
        doExpireString();
    }

}
