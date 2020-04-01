package cache.redis.redisson;

import cache.redis.redisson.api.RFuture;
import cache.redis.redisson.api.RLock;
import cache.redis.redisson.command.CommandAsyncExecutor;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import lombok.extern.slf4j.Slf4j;
import org.fusesource.hawtbuf.codec.LongCodec;

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * @author: xuxianbei
 * Date: 2020/4/1
 * Time: 17:42
 * Version:V1.0
 */
@Slf4j
public class RedissonLock implements RLock {

    protected long internalLockLeaseTime;

    final String name;

    final String entryName;
    final CommandAsyncExecutor commandExecutor;

    public RedissonLock(CommandAsyncExecutor commandExecutor, String name) {
        this.commandExecutor = commandExecutor;
        this.name = name;
        this.entryName = "1" + ":" + name;
    }

    public void lock() {
        try {
            lockInterruptibly();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void lockInterruptibly() throws InterruptedException {
        lockInterruptibly(-1, null);
    }

    public void lockInterruptibly(long leaseTime, TimeUnit unit) throws InterruptedException {
        long threadId = Thread.currentThread().getId();
        Long ttl = tryAcquire(leaseTime, unit, threadId);
    }

    private Long tryAcquire(long leaseTime, TimeUnit unit, long threadId) {
        return null;
//        return get(tryAcquireAsync(leaseTime, unit, threadId));
    }

    private <T> RFuture<Long> tryAcquireAsync(long leaseTime, TimeUnit unit, final long threadId) {
        if (leaseTime != -1) {
            //重入锁
            return tryLockInnerAsync(leaseTime, unit, threadId);
        }
        /**
         * lockWatchdogTimeout =  30 * 1000 看门狗的过期时间
         */
        RFuture<Long> ttlRemainingFuture = tryLockInnerAsync(30 * 1000, TimeUnit.MILLISECONDS, threadId);
        ttlRemainingFuture.addListener(new FutureListener<Long>() {

            @Override
            public void operationComplete(io.netty.util.concurrent.Future<Long> future) throws Exception {
                if (!future.isSuccess()) {
                    return;
                }

                Long ttlRemaining = future.getNow();
                // lock acquired
                if (ttlRemaining == null) {
                    scheduleExpirationRenewal(threadId);
                }
            }

        });
        return ttlRemainingFuture;
    }

    /**
     * 重入锁
     *
     * @param leaseTime
     * @param unit
     * @param threadId
     * @param <T>
     * @return
     */
    <T> RFuture<T> tryLockInnerAsync(long leaseTime, TimeUnit unit, long threadId) {
        internalLockLeaseTime = unit.toMillis(leaseTime);
        /**
         * 参数key， 时间 ，线程id
         * 如果不存在key 那么 设置 key线程id ,占位符
         * 设置key的 超时时间
         * 如果存在，那么判断key的线程id是否存在
         *   如果存在：hincrby 增加线程id 次数且更新新的过期值。
         *   不存在：Redis Pttl 命令以毫秒为单位返回 key 的剩余过期时间。
         */
        String str = "if (redis.call('exists', KEYS[1]) == 0) then " +
                "redis.call('hset', KEYS[1], ARGV[2], 1); " +
                "redis.call('pexpire', KEYS[1], ARGV[1]); " +
                "return nil; " +
                "end; " +
                "if (redis.call('hexists', KEYS[1], ARGV[2]) == 1) then " +
                "redis.call('hincrby', KEYS[1], ARGV[2], 1); " +
                "redis.call('pexpire', KEYS[1], ARGV[1]); " +
                "return nil; " +
                "end; " +
                "return redis.call('pttl', KEYS[1]);";
        return null;
    }

    public static class ExpirationEntry {

        private long threadId;
        private Timeout timeout;

        public ExpirationEntry(long threadId, Timeout timeout) {
            super();
            this.threadId = threadId;
            this.timeout = timeout;
        }

        public long getThreadId() {
            return threadId;
        }

        public Timeout getTimeout() {
            return timeout;
        }

    }

    private static final ConcurrentMap<String, ExpirationEntry> expirationRenewalMap = new ConcurrentHashMap();

    protected String getEntryName() {
        return entryName;
    }

    private void scheduleExpirationRenewal(final long threadId) {
        if (expirationRenewalMap.containsKey(getEntryName())) {
            return;
        }

        Timeout task = commandExecutor.newTimeout(new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {

                RFuture<Boolean> future = renewExpirationAsync(threadId);

                future.addListener(new FutureListener<Boolean>() {
                    @Override
                    public void operationComplete(Future<Boolean> future) throws Exception {
                        expirationRenewalMap.remove(getEntryName());
                        if (!future.isSuccess()) {
                            log.error("Can't update lock " + getName() + " expiration", future.cause());
                            return;
                        }

                        if (future.getNow()) {
                            // reschedule itself
                            scheduleExpirationRenewal(threadId);
                        }
                    }


                });
            }

        }, internalLockLeaseTime / 3, TimeUnit.MILLISECONDS);

        if (expirationRenewalMap.putIfAbsent(getEntryName(), new ExpirationEntry(threadId, task)) != null) {
            task.cancel();
        }
    }

    protected RFuture<Boolean> renewExpirationAsync(long threadId) {
        return commandExecutor.evalWriteAsync(getName(), LongCodec.INSTANCE,
                "if (redis.call('hexists', KEYS[1], ARGV[2]) == 1) then " +
                        "redis.call('pexpire', KEYS[1], ARGV[1]); " +
                        "return 1; " +
                        "end; " +
                        "return 0;",
                Collections.<Object>singletonList(getName()),
                internalLockLeaseTime, threadId);
    }

    public String getName() {
        return name;
    }
}
