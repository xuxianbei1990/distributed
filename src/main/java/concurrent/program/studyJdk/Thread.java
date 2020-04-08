package concurrent.program.studyJdk;

/**
 * @author: xuxianbei
 * Date: 2020/4/7
 * Time: 20:31
 * Version:V1.0
 */
public class Thread implements Runnable {

    ThreadLocal.ThreadLocalMap threadLocals = null;

    //获取当前线程是操作系统
    public static native Thread currentThread();


    @Override
    public void run() {

    }
}
