package concurrent.program.studyJdk;

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
    public void lock() {
        if (compareAndSetState(0, 1)) {
           setExclusiveOwnerThread(Thread.currentThread());
        } else{
            acquire(1);
        }
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

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
