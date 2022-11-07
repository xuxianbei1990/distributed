package concurrent.program.synchronize;

import practice.studyJdk.MyHashMap;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * @author: xuxianbei
 * Date: 2020/4/26
 * Time: 15:40
 * Version:V1.0
 */
public class SynVolatileDemo implements Runnable {
    private static int count = 0;
    private static volatile MyHashMap object = null;
    private static HashMap hashMap = new HashMap();
    CountDownLatch countDownLatch;
    CyclicBarrier cyclicBarrier;

    public SynVolatileDemo(CountDownLatch countDownLatch, CyclicBarrier cyclicBarrier) {
        this.countDownLatch = countDownLatch;
        this.cyclicBarrier = cyclicBarrier;
    }

    @SneakyThrows
    public static void main(String[] args) {
        int countThread = 1000;
        CountDownLatch countDownLatch = new CountDownLatch(countThread);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(countThread);
        for (int i = 0; i < countThread; i++) {
            Thread thread = new Thread(new SynVolatileDemo(countDownLatch, cyclicBarrier));
            thread.start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "count:" +
                count);
    }

    @SneakyThrows
    @Override
    public void run() {
        cyclicBarrier.await();
        Thread.sleep(1);
        addCount();
        countDownLatch.countDown();
    }



    public /*synchronized*/ static void addCount() {
            count++;
    }

}
