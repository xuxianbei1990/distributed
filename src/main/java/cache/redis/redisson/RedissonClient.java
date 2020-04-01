package cache.redis.redisson;

import cache.redis.redisson.api.RLock;

/**
 * @author: xuxianbei
 * Date: 2020/4/1
 * Time: 17:40
 * Version:V1.0
 */
public interface RedissonClient {

    RLock getLock(String name);
}
