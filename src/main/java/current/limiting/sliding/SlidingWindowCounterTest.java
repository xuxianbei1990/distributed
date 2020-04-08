package current.limiting.sliding;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Name
 *
 * @author xuxb
 * Date 2018-12-05
 * VersionV1.0
 * @description
 *
 * https://go12345.iteye.com/blog/1744728
 *
 * http://blog.51cto.com/zero01/2307787 这篇文章解释最能理解
 */
public class SlidingWindowCounterTest {

    private ExecutorService es = Executors.newCachedThreadPool();
    private ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();

    public void testNWindow() throws IOException {
        SlidingWindowCounter swc = new SlidingWindowCounter(3);
        ses.scheduleAtFixedRate(() -> {
            Loops.fixLoop(swc::increase, new Random().nextInt(10));
        }, 10, 2, TimeUnit.MILLISECONDS);
        ses.scheduleAtFixedRate(() -> {
            System.out.println(swc);
            swc.advance();
        }, 1, 1, TimeUnit.SECONDS);
        ses.scheduleAtFixedRate(() -> {
            swc.resizeWindow(new Random().nextInt(10));
        }, 1, 10, TimeUnit.SECONDS);
        System.in.read();
    }


    public void test1Window() {
        SlidingWindowCounter swc = new SlidingWindowCounter(1);
        System.out.println(swc);
        swc.increase();
        swc.increase();
        System.out.println(swc);
        swc.advance();
        System.out.println(swc);
        swc.increase();
        swc.increase();
        System.out.println(swc);
    }

    public void test3Window() {
        SlidingWindowCounter swc = new SlidingWindowCounter(3);
        System.out.println(swc);
        swc.increase();
        System.out.println(swc);
        swc.advance();
        System.out.println(swc);
        swc.increase();
        swc.increase();
        System.out.println(swc);
        swc.advance();
        System.out.println(swc);
        swc.increase();
        swc.increase();
        swc.increase();
        System.out.println(swc);
        swc.advance();
        System.out.println(swc);
        swc.increase();
        swc.increase();
        swc.increase();
        swc.increase();
        System.out.println(swc);
        swc.advance();
        System.out.println(swc);
    }

}

