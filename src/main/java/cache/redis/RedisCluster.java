package cache.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Name
 *
 * @author xuxb
 * Date 2018-10-31
 * VersionV1.0
 * @description
 */
public class RedisCluster {

    public static void main(String[] args) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();

        // 最大连接数
        poolConfig.setMaxTotal(1);

        // 最大空闲数
        poolConfig.setMaxIdle(1);

        // 最大允许等待时间，如果超过这个时间还未获取到连接，则会报JedisException异常：
        poolConfig.setMaxWaitMillis(5000);

        Set<HostAndPort> nodes = new LinkedHashSet<>();
        nodes.add(new HostAndPort("192.168.2.2", 7000));
        nodes.add(new HostAndPort("192.168.2.2", 7001));
        nodes.add(new HostAndPort("192.168.2.2", 7002));
        nodes.add(new HostAndPort("192.168.2.2", 7003));
        nodes.add(new HostAndPort("192.168.2.2", 7004));
        nodes.add(new HostAndPort("192.168.2.2", 7005));
        JedisCluster cluster = new JedisCluster(nodes, poolConfig);
        String name = cluster.get("name");
        System.out.println(name);
        cluster.set("age", "18");
        System.out.println(cluster.get("age"));
        try {
            cluster.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
