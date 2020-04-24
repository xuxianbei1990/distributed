package concurrent.program.studyJdk;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.util.HashSet;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: xuxianbei
 * Date: 2020/4/23
 * Time: 15:30
 * Version:V1.0
 */
public class MyThreadPoolExecutor {
    private final ReentrantLock mainLock = new ReentrantLock();

    private final HashSet<MyWorker> workers = new HashSet();
    private static final boolean ONLY_ONE = true;
    private long completedTaskCount;
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int RUNNING = -1 << COUNT_BITS;
    private static final int STOP = 1 << COUNT_BITS;
    private static final int TIDYING = 2 << COUNT_BITS;
    private static final int TERMINATED = 3 << COUNT_BITS;
    private static final int CAPACITY = (1 << COUNT_BITS) - 1;

    /**
     * ctl 代表两个意思，高三位代表状态，低29位代表线程数量
     */
    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    private static final int SHUTDOWN = 0 << COUNT_BITS;
    private final AccessControlContext acc;
    private volatile int corePoolSize;
    private volatile int maximumPoolSize;
    private volatile RejectedExecutionHandler handler;
    private final BlockingQueue<Runnable> workQueue;
    private volatile long keepAliveTime;
    private int largestPoolSize;
    private volatile ThreadFactory threadFactory;

    private volatile boolean allowCoreThreadTimeOut;

    private static int ctlOf(int rs, int wc) {
        return rs | wc;
    }

    private static int workerCountOf(int c) {
        return c & CAPACITY;
    }

    private static int runStateOf(int c) {
        return c & ~CAPACITY;
    }

    private static boolean runStateAtLeast(int c, int s) {
        return c >= s;
    }

    private static boolean runStateLessThan(int c, int s) {
        return c < s;
    }

    public MyThreadPoolExecutor(int corePoolSize,
                                int maximumPoolSize,
                                long keepAliveTime,
                                TimeUnit unit,
                                BlockingQueue<Runnable> workQueue,
                                ThreadFactory threadFactory,
                                RejectedExecutionHandler handler) {
        if (corePoolSize < 0 ||
                maximumPoolSize <= 0 ||
                maximumPoolSize < corePoolSize ||
                keepAliveTime < 0)
            throw new IllegalArgumentException();
        if (workQueue == null || threadFactory == null || handler == null)
            throw new NullPointerException();
        this.acc = System.getSecurityManager() == null ?
                null :
                AccessController.getContext();
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.workQueue = workQueue;
        this.keepAliveTime = unit.toNanos(keepAliveTime);
        this.threadFactory = threadFactory;
        this.handler = handler;
    }

    public void execute(Runnable command) {
        if (command == null)
            throw new NullPointerException();
        //就是0
        int c = ctl.get();
        //如果工作线程小于核心线程
        if (workerCountOf(c) < corePoolSize) {
            //添加工作线程
            if (addWorker(command, true))
                return;
            c = ctl.get();
        }
        //如果超过核心线程，先添加任务
        if (isRunning(c) && workQueue.offer(command)) {
            int recheck = ctl.get();
            if (!isRunning(recheck) && remove(command))
                reject(command);
            else if (workerCountOf(recheck) == 0)
                addWorker(null, false);
            //队列加满了，在考虑添加非核心线程
        } else if (!addWorker(command, false))
            //拒绝策略
            reject(command);
    }

    private boolean addWorker(Runnable firstTask, boolean core) {
        retry:
        for (; ; ) {
            int c = ctl.get();
            int rs = runStateOf(c);
            //SHUTDOWN 就是0
            if (rs >= SHUTDOWN &&
                    !(rs == SHUTDOWN &&
                            firstTask == null &&
                            !workQueue.isEmpty()))
                return false;
            for (; ; ) {
                int wc = workerCountOf(c);
                if (wc >= CAPACITY ||
                        wc >= (core ? corePoolSize : maximumPoolSize))
                    return false;
                if (compareAndIncrementWorkerCount(c))
                    break retry;
            }
        }

        boolean workerStarted = false;
        boolean workerAdded = false;
        MyWorker w = null;
        try {
            w = new MyWorker(firstTask);
            final Thread t = w.thread;
            if (t != null) {
                final ReentrantLock mainLock = this.mainLock;
                mainLock.lock();
                try {
                    int rs = runStateOf(ctl.get());

                    if (rs < SHUTDOWN ||
                            (rs == SHUTDOWN && firstTask == null)) {
                        if (t.isAlive()) // precheck that t is startable
                            throw new IllegalThreadStateException();
                        workers.add(w);
                        int s = workers.size();
                        if (s > largestPoolSize)
                            largestPoolSize = s;
                        workerAdded = true;
                    }
                } finally {
                    mainLock.unlock();
                }
                if (workerAdded) {
                    t.start();
                    workerStarted = true;
                }
            }

        } finally {
//            if (!workerStarted)
//                addWorkerFailed(w);
        }
        return workerStarted;
    }

    public ThreadFactory getThreadFactory() {
        return threadFactory;
    }

    final void reject(Runnable command) {
        handler.rejectedExecution(command, null);
    }

    public boolean remove(Runnable task) {
        return false;
    }

    private final class MyWorker
            extends AbstractQueuedSynchronizer
            implements Runnable {
        final Thread thread;
        /**
         * Initial task to run.  Possibly null.
         */
        Runnable firstTask;
        volatile long completedTasks;

        MyWorker(Runnable firstTask) {
            setState(-1); // inhibit interrupts until runWorker
            this.firstTask = firstTask;
            this.thread = getThreadFactory().newThread(this);
        }

        @Override
        public void run() {
            runWorker(this);
        }

        public void unlock() {
            release(1);
        }

        public boolean tryLock() {
            return tryAcquire(1);
        }

        public void lock() {
            acquire(1);
        }

        protected boolean tryAcquire(int unused) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

    }

    final void runWorker(MyWorker w) {
        Thread wt = Thread.currentThread();
        Runnable task = w.firstTask;
        w.firstTask = null;
        w.unlock();
        boolean completedAbruptly = true;
        try {
            while (task != null || (task = getTask()) != null) {
                //加锁
                w.lock();
                //中断操作
                if ((runStateAtLeast(ctl.get(), STOP) ||
                        (Thread.interrupted() &&
                                runStateAtLeast(ctl.get(), STOP))) &&
                        !wt.isInterrupted())
                    wt.interrupt();
                try {
                    beforeExecute(wt, task);
                    Throwable thrown = null;
                    try {
                        task.run();
                    } catch (RuntimeException x) {
                        thrown = x;
                        throw x;
                    } catch (Error x) {
                        thrown = x;
                        throw x;
                    } catch (Throwable x) {
                        thrown = x;
                        throw new Error(x);
                    } finally {
                        afterExecute(task, thrown);
                    }
                } finally {
                    task = null;
                    w.completedTasks++;
                    w.unlock();
                }
            }
        } finally {
            processWorkerExit(w, completedAbruptly);
        }
    }

    private void processWorkerExit(MyWorker w, boolean completedAbruptly) {
        if (completedAbruptly) // If abrupt, then workerCount wasn't adjusted
            decrementWorkerCount();

        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            completedTaskCount += w.completedTasks;
            workers.remove(w);
        } finally {
            mainLock.unlock();
        }
        tryTerminate();

        int c = ctl.get();
        if (runStateLessThan(c, STOP)) {
            if (!completedAbruptly) {
                int min = allowCoreThreadTimeOut ? 0 : corePoolSize;
                if (min == 0 && !workQueue.isEmpty())
                    min = 1;
                if (workerCountOf(c) >= min)
                    return; // replacement not needed
            }
            addWorker(null, false);
        }
    }

    final void tryTerminate() {
        for (; ; ) {
            int c = ctl.get();
            if (isRunning(c) ||
                    runStateAtLeast(c, TIDYING) ||
                    (runStateOf(c) == SHUTDOWN && !workQueue.isEmpty()))
                return;

            if (workerCountOf(c) != 0) { // Eligible to terminate
                interruptIdleWorkers(ONLY_ONE);
                return;
            }

            final ReentrantLock mainLock = this.mainLock;
            mainLock.lock();
            try {
                if (ctl.compareAndSet(c, ctlOf(TIDYING, 0))) {
                    try {
                        terminated();
                    } finally {
                        ctl.set(ctlOf(TERMINATED, 0));
//                        termination.signalAll();
                    }
                    return;
                }
            } finally {
                mainLock.unlock();
            }
        }
    }

    protected void terminated() {
    }

    private void interruptIdleWorkers(boolean onlyOne) {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            for (MyWorker w : workers) {
                Thread t = w.thread;
                if (!t.isInterrupted() && w.tryLock()) {
                    try {
                        t.interrupt();
                    } catch (SecurityException ignore) {
                    } finally {
                        w.unlock();
                    }
                }
                if (onlyOne)
                    break;
            }
        } finally {
            mainLock.unlock();
        }
    }

    private static boolean isRunning(int c) {
        return c < SHUTDOWN;
    }

    protected void beforeExecute(Thread t, Runnable r) {
    }

    protected void afterExecute(Runnable r, Throwable t) {
    }

    private Runnable getTask() {
        boolean timedOut = false; // Did the last poll() time out?
        for (; ; ) {
            int c = ctl.get();
            int rs = runStateOf(c);

            // Check if queue empty only if necessary.
            if (rs >= SHUTDOWN && (rs >= STOP || workQueue.isEmpty())) {
                decrementWorkerCount();
                return null;
            }

            int wc = workerCountOf(c);

            boolean timed = allowCoreThreadTimeOut || wc > corePoolSize;

            if ((wc > maximumPoolSize || (timed && timedOut))
                    && (wc > 1 || workQueue.isEmpty())) {
                if (compareAndDecrementWorkerCount(c))
                    return null;
                continue;
            }
            try {
                Runnable r = timed ?
                        workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS) :
                        workQueue.take();
                if (r != null)
                    return r;
                timedOut = true;
            } catch (InterruptedException retry) {
                timedOut = false;
            }
        }
    }

    private void decrementWorkerCount() {
        do {
        } while (!compareAndDecrementWorkerCount(ctl.get()));
    }

    private boolean compareAndDecrementWorkerCount(int expect) {
        return ctl.compareAndSet(expect, expect - 1);
    }

    private boolean compareAndIncrementWorkerCount(int expect) {
        return ctl.compareAndSet(expect, expect + 1);
    }

    public static void main(String[] args) throws InterruptedException {

        demo();
    }

    private static void demo() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2, 2, 0, TimeUnit.SECONDS, new LinkedBlockingQueue(10),
                (r) -> {
                    Thread thread = new Thread(r, "test");
                    thread.setDaemon(false);
                    return thread;
                }, new ThreadPoolExecutor.DiscardOldestPolicy());
        //Callable
        threadPoolExecutor.submit(() -> {
            System.out.println(1);
            return 1;
        });
        //Runnable
        threadPoolExecutor.execute(() -> System.out.println("1"));
        threadPoolExecutor.execute(() -> System.out.println("2"));

//        Thread.sleep(3000);
//        threadPoolExecutor.execute(() -> System.out.println(1));
        //如果队列有数据，等队列结束
//        threadPoolExecutor.shutdown();
//        //不等队列
//        threadPoolExecutor.shutdownNow();
//        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        //丢弃最老的
//        new ThreadPoolExecutor.DiscardOldestPolicy();
//        //调用当前线程
//        new ThreadPoolExecutor.CallerRunsPolicy();
//        //拒绝, 报错  RejectedExecutionException
//        new ThreadPoolExecutor.AbortPolicy();
//        //吃掉
//        new ThreadPoolExecutor.DiscardPolicy();
    }
}
