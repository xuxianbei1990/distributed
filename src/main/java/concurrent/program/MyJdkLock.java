package concurrent.program;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Name
 *
 * @author xxb
 * Date 2019/5/31
 * VersionV1.0
 * @description 自定义锁 参考ReentrantLock
 */
public class MyJdkLock implements Lock {
    ReentrantLock lock;
    Sync sync;


    MyJdkLock() {
        sync = new Sync();
    }

    @Override
    public void lock() {
        sync.lock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        // 直接由同步器帮你完成
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.unlock();
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    class Sync extends AbstractQueuedSynchronizer {

        protected void lock() {
            //这里肯定需要一个信号量标记下;
            //说明aqs这个方法记录当前状态，虽然不知道怎么实现，但是我觉得这个状态肯定是记录在线程中的
            // 不对我觉得下面这个函数是临界资源被各个线程抢夺。原来这个临界资源是0；一定是放在方法区或者堆的值。
            // 关键 unsafe.compareAndSwapInt 的 stateOffset
            // 下面代码的意思；原子操作更改 AQS 的state;也就是说这行执行之后，AQS的state会变成1
            //stateOffset = unsafe.objectFieldOffset
            //                (AbstractQueuedSynchronizer.class.getDeclaredField("state"));
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
            } else {
                // 这就是可以重入锁
                acquire(1);
            }
        }

        //下面处理重入锁逻辑和获取锁
        @Override
        protected boolean tryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            //如果等于0 再次去尝试抢夺一次锁 ；并且处理下一次队列中的获取锁
            if (c == 0) {
                if (compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
                //问题来了，谁第一次改变了state？ 答案是第一次compareAndSetState;
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
        protected boolean tryRelease(int arg) {
            int c = getState() - arg;
            if (getExclusiveOwnerThread() != Thread.currentThread()) {
                throw new IllegalMonitorStateException();
            }
            boolean free = false;
            if (c == 0) {
                free = true;
                setExclusiveOwnerThread(null);
            }
            setState(c);
            return free;
        }

        public void unlock() {
            release(1);
        }

        public Condition newCondition() {
            return new ConditionObject();
        }
    }

    public static void main(String[] args) {
        MyJdkLock myJdkLock = new MyJdkLock();
        Condition condition = myJdkLock.newCondition();
        try {
            myJdkLock.lock();
            condition.await();
            myJdkLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
