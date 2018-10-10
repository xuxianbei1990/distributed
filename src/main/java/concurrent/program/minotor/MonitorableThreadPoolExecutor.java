package concurrent.program.minotor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * User: xuxb
 * Date: 2018-10-08
 * Time: 19:12
 * Version:V1.0
 */
public class MonitorableThreadPoolExecutor extends ThreadPoolExecutor {

    private Map<String, MonitorHandler> handlerMap = new HashMap<>();

    private final Object lock = new Object();

    public MonitorableThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public MonitorableThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public MonitorableThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public MonitorableThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        for (MonitorHandler handler : handlerMap.values()) {
            if (handler.usable()) {
                handler.before(t, r);
            }
        }
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        for (MonitorHandler handler : handlerMap.values()) {
            if (handler.usable()) {
                handler.after(r, t);
            }
        }
    }

    @Override
    protected void terminated() {
        super.terminated();
        for (MonitorHandler handler : handlerMap.values()) {
            if (handler.usable()) {
                handler.terminated(getLargestPoolSize(), getCompletedTaskCount());
            }
        }
    }

    public MonitorHandler addMonitorTask(String key, MonitorHandler task, boolean overrideIfExist) {
        if (overrideIfExist) {
            synchronized (lock) {
                return handlerMap.put(key, task);
            }
        } else {
            synchronized (lock) {
                return handlerMap.putIfAbsent(key, task);
            }
        }

    }

    public MonitorHandler addMonitorTask(String key, MonitorHandler task) {
        return addMonitorTask(key, task, true);
    }

    public MonitorHandler removeMonitorTask(String key) {
        synchronized (lock) {
            return handlerMap.remove(key);
        }
    }
}
