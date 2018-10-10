package concurrent.program;

import java.util.HashSet;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

public class ConcurrentCollection  {

    static void BaseMap() {
        HashSet<String> set;
        ConcurrentHashMap chm = new ConcurrentHashMap();
        chm.put("str", "1");
        chm.size();
        ConcurrentLinkedQueue clq;
        LinkedBlockingQueue lbq;
        ArrayBlockingQueue abq;
        CountDownLatch cdl2;

        ExecutorService threadService =  Executors.newFixedThreadPool(10);
        threadService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("1");
            }
        });


        CyclicBarrier cb = new CyclicBarrier(10);
        try {
            cb.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        ThreadLocal threadLocal = new ThreadLocal();

        Semaphore semaphore = new Semaphore(10);
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CountDownLatch cdl = new CountDownLatch(2);
        cdl.countDown();
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    static void test(ReadLock readLock) {
        readLock.lock();
        System.out.print("2");
//        readLock.unlock();
    }

    static void BaseLock() {
        ReentrantReadWriteLock rrwl = new ReentrantReadWriteLock();
        ReadLock readLock = rrwl.readLock();
        ReentrantReadWriteLock.WriteLock wl = rrwl.writeLock();
        wl.lock();
        new Thread("yy") {
            @Override
            public void run() {
                test(readLock);
            }
        }.start();
        new Thread("xx") {
            @Override
            public void run() {
                test(readLock);
            }
        }.start();

    }

    public static void main(String[] args) {

        System.out.println(1 << 16);
        System.out.println(1 << 16 >>> 16);
        System.out.println(1 << 16 & ((1 << 16) - 1));
        System.out.println(1 >>> 16);
        BaseMap();
    }

}
