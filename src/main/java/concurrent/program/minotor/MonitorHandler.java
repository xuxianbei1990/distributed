package concurrent.program.minotor;

/**
 * User: xuxb
 * Date: 2018-10-08
 * Time: 19:08
 * Version:V1.0
 */

//监控处理器, 目的是把before和after抽象出来, 以便在{@link MonitorableThreadPoolExecutor}中形成一条监控处理器链
public interface MonitorHandler {

    boolean usable();

    void before(Thread thread, Runnable runnable);

    void after(Runnable runnable, Throwable throwable);

    void terminated(int largestPoolSize, long completedTaskCount);
}
