package cache.redis;

import redis.clients.jedis.Jedis;

import java.util.Collections;

/**
 * Redis分布式锁
 * User: xuxb
 * Date: 2018-10-11
 * Time: 20:15
 * Version:V1.0
 * https://www.cnblogs.com/linjiqin/p/8003838.html
 */
public class RedisLock {

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    // 防止死锁
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    private static final Long RELEASE_SUCCESS = 1L;

    private Jedis jedis = null;

    private enum JedisSingleton {
        INSTANCE;
        private Jedis oneJedis = null;

        private JedisSingleton() {
            oneJedis = new Jedis("192.168.174.128", 6379);
        }

        public Jedis getJedis() {
            return oneJedis;
        }
    }

    RedisLock() {
        jedis = JedisSingleton.INSTANCE.getJedis();
    }


    public boolean tryLock(String lockkey, String value) {
        return this.tryLock(lockkey, value);
    }

    public boolean tryLock(String lockkey, String value, long time) throws InterruptedException {
        String result = jedis.set(lockkey, value, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, time);
        return LOCK_SUCCESS.equals(result);
    }

    public boolean tryRelease(String lockKey, String value) {
        //Lua代码
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(value));
        return RELEASE_SUCCESS.equals(result);
    }

}
