package concurrent.program.minotor;

import org.apache.commons.lang.math.RandomUtils;

import java.util.Map;
import java.util.concurrent.*;

/**
 * User: xuxb
 * Date: 2018-10-10
 * Time: 19:43
 * Version:V1.0
 */
public class Tester {
    static volatile boolean stop = false;

    public static void main(String[] args) throws InterruptedException {

        final MonitorableThreadPoolExecutor pool = new MonitorableThreadPoolExecutor(5, 10, 30, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        pool.addMonitorTask("TimeMonitorTask", newTimeMonitorHandler());

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                startAddTask(pool);
            }
        });
        t.start();

        Thread.sleep(50);
        stop = true;
        t.join();
        pool.shutdown();
        pool.awaitTermination(100, TimeUnit.SECONDS);
    }

    private static MonitorHandler newTimeMonitorHandler() {
        return new MonitorHandler() {
            // 任务开始时间记录map, 多线程增删, 需用ConcurrentHashMap
            Map<Runnable, Long> timeRecords = new ConcurrentHashMap<>();

            @Override
            public boolean usable() {
                return true;
            }

            @Override
            public void before(Thread thread, Runnable runnable) {
                System.out.println(String.format("%s: before[%s -> %s]", time(), thread, runnable));
                timeRecords.put(runnable, System.currentTimeMillis());
            }

            @Override
            public void after(Runnable runnable, Throwable throwable) {
                long end = System.currentTimeMillis();
                Long start = timeRecords.remove(runnable);

                Object result = null;
                if (throwable == null && runnable instanceof FutureTask<?>) { // 有返回值的异步任务，不一定是Callable<?>，也有可能是Runnable
                    try {
                        result = ((Future<?>) runnable).get();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // reset
                    } catch (ExecutionException e) {
                        throwable = e;
                    } catch (CancellationException e) {
                        throwable = e;
                    }
                }

                if (throwable == null) { // 任务正常结束
                    if (result != null) { // 有返回值的异步任务
                        System.out.println(String.format("%s: after[%s -> %s], costs %d millisecond, result: %s", time(), Thread.currentThread(), runnable, end - start, result));
                    } else {
                        System.out.println(String.format("%s: after[%s -> %s], costs %d millisecond", time(), Thread.currentThread(), runnable, end - start));
                    }
                } else {
                    System.err.println(String.format("%s: after[%s -> %s], costs %d millisecond, exception: %s", time(), Thread.currentThread(), runnable, end - start, throwable));
                }
            }

            @Override
            public void terminated(int largestPoolSize, long completedTaskCount) {
                System.out.println(String.format("%s:largestPoolSize=%d, completedTaskCount=%s", time(), largestPoolSize, completedTaskCount));
            }
        };
    }

    // 随机runnable或者callable<?>, 任务随机抛异常
    private static void startAddTask(MonitorableThreadPoolExecutor pool) {
        int count = 0;
        while (!stop) {
            if (RandomUtils.nextBoolean()) {// 丢Callable<?>任务
                pool.submit(new Callable<Boolean>() {

                    public Boolean call() throws Exception {
                        // 随机抛异常
                        boolean bool = RandomUtils.nextBoolean();
                        // 随机耗时 0~100 ms
                        Thread.sleep(RandomUtils.nextInt(100));
                        if (bool) {
                            throw new RuntimeException("thrown randomly");
                        }
                        return bool;
                    }

                });
            } else { // 丢Runnable
                pool.submit(new Runnable() {

                    public void run() {
                        // 随机耗时 0~100 ms
                        try {
                            Thread.sleep(RandomUtils.nextInt(100));
                        } catch (InterruptedException e) {
                        }
                        // 随机抛异常
                        if (RandomUtils.nextBoolean()) {
                            throw new RuntimeException("thrown randomly");
                        }
                    }

                });
            }
            System.out.println(String.format("%s:submitted %d task", time(), ++count));
        }
    }

    private static String time() {
        return String.valueOf(System.currentTimeMillis());
    }
}
