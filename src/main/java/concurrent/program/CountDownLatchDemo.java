package concurrent.program;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Name
 *
 * @author xxb
 * Date 2019/6/4
 * VersionV1.0
 * @description
 */
class CountDownRunnable implements Runnable {

    private CountDownLatch countDownLatch;

    public CountDownRunnable(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        countDownLatch.countDown();
        System.out.println(Thread.currentThread().getName());
    }
}

public class CountDownLatchDemo {


    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            new Thread(new CountDownRunnable(countDownLatch)).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("finished");
    }
}
