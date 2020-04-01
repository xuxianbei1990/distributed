package cache.redis.redisson.command;

import cache.redis.redisson.api.RFuture;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import org.fusesource.hawtbuf.codec.Codec;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: xuxianbei
 * Date: 2020/4/1
 * Time: 17:44
 * Version:V1.0
 */
public interface CommandAsyncExecutor {
    <T, R> RFuture<R> evalWriteAsync(String key, Codec codec,  String script, List<Object> keys, Object ... params);
    Timeout newTimeout(TimerTask task, long delay, TimeUnit unit);
}
