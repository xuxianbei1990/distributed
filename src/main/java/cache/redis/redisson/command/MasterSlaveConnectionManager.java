package cache.redis.redisson.command;

import cache.redis.redisson.api.RFuture;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;
import org.fusesource.hawtbuf.codec.Codec;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author: xuxianbei
 * Date: 2020/4/1
 * Time: 20:00
 * Version:V1.0
 */
public class MasterSlaveConnectionManager implements CommandAsyncExecutor {

    private HashedWheelTimer timer;
    MasterSlaveConnectionManager() {
        timer = new HashedWheelTimer(Executors.defaultThreadFactory(), 0, TimeUnit.MILLISECONDS, 1024, false);
    }

    @Override
    public <T, R> RFuture<R> evalWriteAsync(String key, Codec codec, String script, List<Object> keys, Object... params) {
        return null;
    }

    public Timeout newTimeout(TimerTask task, long delay, TimeUnit unit) {
        try {
            return timer.newTimeout(task, delay, unit);
        } catch (IllegalStateException e) {
            // timer is shutdown
            return new Timeout() {
                @Override
                public Timer timer() {
                    return null;
                }

                @Override
                public TimerTask task() {
                    return null;
                }

                @Override
                public boolean isExpired() {
                    return false;
                }

                @Override
                public boolean isCancelled() {
                    return false;
                }

                @Override
                public boolean cancel() {
                    return false;
                }
            };
        }
    }
}
