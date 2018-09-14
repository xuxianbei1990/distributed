package concurrent.program.Exception;

/**
 * User: xuxb
 * Date: 2018-09-14
 * Time: 22:24
 * Version:V1.0
 */
public class ThreadExcepiton implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.printf("ThreadName:%s\n", t.getName());
        System.out.printf("ThreadId: %s\n", t.getId());
        System.out.printf("Exception:%s:%s\n",e.getClass().getName(),e.getMessage());
    }
}
