package concurrent.program;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * User: xuxb
 * Date: 2018-09-11
 * Time: 12:59
 * Version:V1.0
 */
class PrintNumber implements Runnable {

    private static final ReentrantLock LOCK = new ReentrantLock();

    private static final Condition[] pArray = new Condition[3];

    private final Integer printNumber;

    private volatile static Integer incNumber = 1;

    static {
        Integer.valueOf(59);
        //打印1的条件
        pArray[0] = LOCK.newCondition();
        //打印2的条件
        pArray[1] = LOCK.newCondition();
        //打印3的条件
        pArray[2] = LOCK.newCondition();
    }

    PrintNumber(Integer pn) {
        this.printNumber = pn;
    }

    void print() {
        LOCK.lock();
        try {
            //否决条件
            while (!incNumber.equals(printNumber)) {
                //挂起不符合条件的线程
                Integer temp = printNumber - 1;
                pArray[temp].await();
            }
            //action
            System.out.println(incNumber);
            incNumber++;
            if (incNumber > 3) {
                incNumber = 1;
            }
            //通知下个打印的线程 激活
            Integer temp = incNumber - 1;
            System.out.println("temp" + temp);
            pArray[temp].signalAll();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            LOCK.unlock();
        }
    }

    @Override
    public void run() {

        print();

    }
}

class MyReentrantLock implements Lock {

    private final Sync sync;

   public MyReentrantLock(Sync sync) {
        this.sync = new NonfairSync();
    }

    abstract static class Sync extends AbstractQueuedSynchronizer {
        abstract void lock();
    }

    class NonfairSync extends Sync {

        @Override
        void lock() {
            if (compareAndSetState(0, 1))
                setExclusiveOwnerThread(Thread.currentThread());
            else
                /*
                尝试获取锁，如果是同一个线程，则增加计数，不是则添加到等待队列
                 */
                acquire(1);
        }

        final boolean nonfairTryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                if (compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            }
            else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0) // overflow
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }

        protected final boolean tryAcquire(int arg) {
            return nonfairTryAcquire(arg);
        }

        protected final boolean tryRelease(int releases) {
            int c = getState() - releases;
            if (Thread.currentThread() != getExclusiveOwnerThread())
                throw new IllegalMonitorStateException();
            boolean free = false;
            if (c == 0) {
                free = true;
                setExclusiveOwnerThread(null);
            }
            setState(c);
            return free;
        }
    }

    @Override
    public void lock() {
        sync.lock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
       sync.release(1);

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}


public class ReentrantLockDemo {
    public static void main(String[] args) {
        new Thread(new PrintNumber(1)).start();
        new Thread(new PrintNumber(2)).start();
        new Thread(new PrintNumber(3)).start();
    }
}



