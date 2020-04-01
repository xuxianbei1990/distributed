package cache.redis.redisson.api;

import io.netty.util.concurrent.FutureListener;

/**
 * @author: xuxianbei
 * Date: 2020/4/1
 * Time: 17:53
 * Version:V1.0
 */
public interface RFuture<V> {

    RFuture<V> addListener(FutureListener<? super V> listener);
}
