package concurrent.program;

import java.util.concurrent.TimeUnit;

/**
 * User: xuxb
 * 线程状态
 * Date: 2018-09-11
 * Time: 18:49
 * Version:V1.0
 */
public class ThreadStatusDemo {

    // 线程安全终止例子
    public static void test() {
        Thread interrupter1 = new Thread(() -> {
            int i = 0;
            while (!Thread.currentThread().isInterrupted()) {
                i++;
            }
            System.out.println(Thread.currentThread().isInterrupted());
            Thread.interrupted();// 复位操作
            System.out.println(Thread.currentThread().isInterrupted());
            System.out.println(i);
        });
        interrupter1.start();
        try {
            Thread.sleep(1000);
            interrupter1.interrupt();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        test();
//        new Thread(() -> {
//            while (true) {
//                try {
//                    TimeUnit.SECONDS.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//                , "timewaiting").start();
//
//        new Thread(() -> {
//            while (true) {
//                synchronized (ThreadStatusDemo.class) {
//                    try {
//                        ThreadStatusDemo.class.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }, "waiting").start();
//
//        new Thread(new BlockDemo(), "BlockDemo1").start();
//        new Thread(new BlockDemo(), "BlockDemo2").start();


    }

    static class BlockDemo implements Runnable {
        @Override
        public void run() {
            synchronized (BlockDemo.class) {
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
