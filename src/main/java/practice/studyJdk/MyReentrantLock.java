package practice.studyJdk;

import java.lang.reflect.Constructor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: xuxianbei
 * Date: 2020/4/26
 * Time: 11:34
 * Version:V1.0
 */
public class MyReentrantLock extends AbstractQueuedSynchronizer implements Lock {
    private String value;

    public MyReentrantLock(String value) {
        this.value = value;
    }

    public static void main(String[] args) throws Exception {
        ReentrantLock reentrantLock = new ReentrantLock();
        try {
            Class clazz = MyReentrantLock.class;
            Constructor[] constructors = clazz.getConstructors();
            Constructor myReentrantLock = MyReentrantLock.class.getConstructor(constructors[0].getParameterTypes()[0]);
            System.out.println(myReentrantLock.newInstance("tt"));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected boolean tryAcquire(int acquires) {
        final Thread current = Thread.currentThread();
        int c = getState();
        if (c == 0) {
            if (compareAndSetState(0, acquires)) {
                setExclusiveOwnerThread(current);
                return true;
            }
        } else if (current == getExclusiveOwnerThread()) {
            int nextc = c + acquires;
            if (nextc < 0) // overflow
                throw new Error("Maximum lock count exceeded");
            setState(nextc);
            return true;
        }
        return false;
    }

    @Override
    protected boolean tryRelease(int releases) {
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

    @Override
    public void lock() {
        if (compareAndSetState(0, 1)) {
            setExclusiveOwnerThread(Thread.currentThread());
        } else {
            acquire(1);
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        release(1);
    }

    @Override
    public Condition newCondition() {
        return new ConditionObject();
    }
}
