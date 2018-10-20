package concurrent.program.stress;

import org.apache.mina.util.ConcurrentHashSet;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: xuxb
 * Date: 2018-10-19
 * Time: 10:59
 * Version:V1.0
 */
public class ThreadSafe {

    private static final AtomicInteger ID = new AtomicInteger(0);

    private  String tradeNo() {
        final int i = ID.incrementAndGet();
        return System.currentTimeMillis() + String.format("-%04d", i);
    }

    public static void main(String[] args) {
        ThreadSafe threadSafe = new ThreadSafe();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2000);
        CountDownLatch countDownLatch = new CountDownLatch(2000);
        ConcurrentHashSet<String> sb = new ConcurrentHashSet();
        for (int i = 0; i < 2000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        cyclicBarrier.await();
                        sb.add(threadSafe.tradeNo());
                        countDownLatch.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(sb.size());
    }
}
