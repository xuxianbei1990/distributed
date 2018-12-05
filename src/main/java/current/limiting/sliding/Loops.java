package current.limiting.sliding;

import java.util.concurrent.TimeUnit;

/**
 * Name
 *
 * @author xuxb
 * Date 2018-12-05
 * VersionV1.0
 * @description
 */
public class Loops {

    public static void dieLoop(Runnable runnable) {
        while (true) {
            run(runnable);
        }
    }

    public static void rateLoop(Runnable runnable, int mills) {
        while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(mills);
            } catch (InterruptedException e) {

            }
            run(runnable);
        }
    }

    public static void fixLoop(Runnable runnable, int loop) {
        for (int i = 0; i < loop; i++) {
            run(runnable);
        }
    }

    private static void run(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

