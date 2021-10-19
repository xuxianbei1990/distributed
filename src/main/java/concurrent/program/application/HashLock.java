package concurrent.program.application;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * Hash锁
 *
 * @author: xuxianbei
 * Date: 2021/8/27
 * Time: 10:33
 * Version:V1.0
 */
@Slf4j
public class HashLock<T, V> {

    private ReentrantLock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    private Map<T, V> map = new HashMap<>();

    public void put(T t, V v) {
        actomic(() -> {
            map.put(t, v);
            condition.signal();
            return null;
        });
    }

    private V actomic(Supplier<V> runnable) {
        try {
            if (lock.tryLock(1, TimeUnit.MINUTES)) {
                return runnable.get();
            }
        } catch (InterruptedException e) {
            log.info("HashLock 异常", e);
        } finally {
            lock.unlock();
        }
        return null;
    }

    public V getAndRemove(T t) {
        return actomic(() -> {
            V v = map.get(t);
            int max = 0;
            while (v == null && max < 5) {
                try {
                    condition.await(1, TimeUnit.MINUTES);
                } catch (InterruptedException e) {
                    log.info("HashLock 异常", e);
                }
                v = map.get(t);
                max++;
            }
            if (v != null) {
                map.remove(t);
            }
            return v;
        });
    }

    public static void main(String[] args) {
        HashLock hashLock = new HashLock();
        for (int i = 0; i < 10; i++) {
            AtomicInteger atomicInteger = new AtomicInteger(i);
            Thread putThread = new Thread(() -> {

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                hashLock.put(atomicInteger.get(), atomicInteger.get() * 100);
                System.out.println("put");


            });
            putThread.start();
        }
        Thread getThread = new Thread(() -> {
//            try {
//                Thread.sleep(200);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            for (int i = 0; i < 10; i++) {
                System.out.println(hashLock.getAndRemove(i));
                System.out.println("get");
            }

        });
        getThread.start();

    }
}
