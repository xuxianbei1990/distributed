package cache.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * Name 哨兵模式实现
 *
 * @author xuxb
 * Date 2018-10-29
 * VersionV1.0
 * @description
 */
public class RedisSentinel {
    public static void main(String[] args) {
        Set<String> sentinels = new HashSet<>();
        // 配置的是哨兵的地址。
        String hostB = "192.168.2.3:26379";
        sentinels.add(hostB);

        JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinels, "123456");

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            System.out.println(jedis.get("key"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        pool.close();
    }
}
