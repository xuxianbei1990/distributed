package delayqueue;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

/**
 * Name redis的 键值空间
 *
 * @author xuxb
 * Date 2019-01-19
 * VersionV1.0
 * @description spring https://www.cnblogs.com/floay/p/6708186.html
 * reids.conf 配置
 * #notify-keyspace-events ""
 * notify-keyspace-events Ex
 * <p>
 * 缺点：Redis的发布/订阅目前是即发即弃(fire and forget)模式的，因此无法实现事件的可靠通知
 * 。也就是说，如果发布/订阅的客户端断链之后又重连，则在客户端断链期间的所有事件都丢失了。
 */
public class RedisKeyspaceNotify {
    private static final String ADDRESS = "192.168.2.2";
    int port = 6379;
    JedisPool jedisPool;

//    {
//        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
//        jedisPool = new JedisPool(poolConfig, ADDRESS, port, Protocol.DEFAULT_TIMEOUT, "123456");
//    }

    class RedisSub extends JedisPubSub {
        @Override
        public void onMessage(String channel, String message) {
            System.out.println(System.currentTimeMillis() + "ms" + message + "订单取消");
        }
    }

    private RedisSub subscribe = new RedisSub();

    void init() {
        new Thread(() -> getRedis().subscribe(subscribe, "__keyevent@0__:expired")).start();
    }

    Jedis getRedis() {
        return jedisPool.getResource();
    }

    void productionDelayMessage() {
        for (int i = 0; i < 5; i++) {
            String orderId = "OID0000001" + i;
            getRedis().setex(orderId, 3, orderId);
            System.out.println(System.currentTimeMillis() + "ms: redis 生成一个订单任务：" + "OID0000001" + i);
        }
    }

    public static void main(String[] args) {
        RedisKeyspaceNotify redisKeyspaceNotify = new RedisKeyspaceNotify();
        redisKeyspaceNotify.init();
        redisKeyspaceNotify.productionDelayMessage();
    }
}
