package concurrent.program;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Name
 *
 * @author xxb
 * Date 2019/6/4
 * VersionV1.0
 * @description
 */
class CyclicBarrierRunnable implements Runnable {

    private CyclicBarrier cyclicBarrier;

    public CyclicBarrierRunnable(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName());
            cyclicBarrier.await();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(100, () -> System.out.println(100 + "弄完了"));
        for (int i = 0; i < cyclicBarrier.getParties(); i++) {
            new Thread(new CyclicBarrierRunnable(cyclicBarrier)).start();
        }
    }
}
