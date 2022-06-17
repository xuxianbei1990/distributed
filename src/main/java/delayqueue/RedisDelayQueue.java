package delayqueue;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.Tuple;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * Name
 *
 * @author xuxb
 * Date 2019-01-18
 * VersionV1.0
 * @description redis的zset, zset是一个有序集合；利用redis的score排序来实现分布式下
 * 延迟任务。
 * 订单超时时间戳与订单号分别设置为score和member,
 */
public class RedisDelayQueue {

    private static final String ADDRESS = "192.168.2.2";
    int port = 6379;
    JedisPool jedisPool;

    {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();

        jedisPool = new JedisPool(poolConfig, ADDRESS, port, Protocol.DEFAULT_TIMEOUT, "123456");
    }


    Jedis getRedis() {
        return jedisPool.getResource();
    }

    void productionDelayMessage() {
        for (int i = 0; i < 5; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, 3);
            int secode3 = (int) (calendar.getTimeInMillis() / 1000);
            getRedis().zadd("OrderId", secode3, "OID0000001" + i);
            System.out.println(System.currentTimeMillis() + "ms: redis 生成一个订单任务：" + "OID0000001" + i);
        }
    }

    void consumerDelayMessage() {
        Jedis jedis = getRedis();
        while (true) {
            Set<Tuple> items = jedis.zrangeWithScores("OrderId", 0, 1);
            if (items == null || items.isEmpty()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            Tuple tuple = (Tuple) items.toArray()[0];
            int score = (int) (tuple).getScore();
            Calendar calendar = Calendar.getInstance();
            int nowSeconds = (int) (calendar.getTimeInMillis() / 1000);
            if (nowSeconds > score) {
                String orderid = tuple.getElement();
                if (jedis.zrem("OrderId", orderid) > 0) {
                    System.out.println(System.currentTimeMillis() + "消费了一个：" + orderid);
                }
            }
        }
    }


    // 非线程安全
    //解决方案有3：1：用分布式锁，但是用分布式锁，性能下降了，该方案不细说
    // 2. if (jedis.zrem("OrderId", orderid) > 0) 只有删除成功才进行消费
    //3. 使用redis Keyspace Notifications，中文翻译就是键空间机制  delayqueue--- RedisKeyspaceNotify
    private static void unSafeMultiThread() {
        RedisDelayQueue redisDelayQueue = new RedisDelayQueue();
        redisDelayQueue.productionDelayMessage();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new RedisDelayQueue().consumerDelayMessage();
            }).start();
            countDownLatch.countDown();
        }
    }

    // 单线程测试
    private static void singleThread() {
        RedisDelayQueue redisDelayQueue = new RedisDelayQueue();
        redisDelayQueue.productionDelayMessage();
        redisDelayQueue.consumerDelayMessage();
    }
}
