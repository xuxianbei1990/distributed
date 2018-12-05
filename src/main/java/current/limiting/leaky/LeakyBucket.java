package current.limiting.leaky;

/**
 * Name 漏斗逻辑大体是这样，
 *
 * @author xuxb
 * Date 2018-12-05
 * VersionV1.0
 * @description 但是以下无法正式使用，因为至少下面代码线程不安全
 */
public class LeakyBucket extends Thread {

    private int capacity; // 桶的容量
    private int rate; // 水漏出的速度
    private long water; // 当前水量(当前累积请求数)

    public LeakyBucket(int capacity, int rate) {
        water = 0;
        this.capacity = capacity;
        this.rate = rate;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                Thread.sleep(rate * 10);
                water = Math.max(0, water - 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean grand() {
        try {
            Thread.sleep(rate);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if ((water + 1) < capacity) {
            // 尝试加水,并且水还未满
            water += 1;
            return true;
        } else {
            // 水满，拒绝加水
            return false;
        }
    }


    public static void main(String[] args) {
        LeakyBucket leakyBucket = new LeakyBucket(10, 1);
        leakyBucket.start();
        for (int i = 0; i < 100; i++) {
            boolean b = leakyBucket.grand();
            if (b) {
                System.out.println(i);
            }
        }
    }

}
