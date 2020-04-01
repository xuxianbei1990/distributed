package cache.redis.redisson;

import cache.redis.redisson.api.RLock;

/**
 * @author: xuxianbei
 * Date: 2020/4/1
 * Time: 17:41
 * Version:V1.0
 */
public class Redisson implements RedissonClient {
    @Override
    public RLock getLock(String name) {
        return new RedissonLock(null, name);
    }
}
