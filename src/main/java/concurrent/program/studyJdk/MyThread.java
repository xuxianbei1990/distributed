package concurrent.program.studyJdk;

/**
 * @author: xuxianbei
 * Date: 2020/4/7
 * Time: 20:31
 * Version:V1.0
 */
public class MyThread extends Thread implements Runnable {

    MyThreadLocal.ThreadLocalMap threadLocals = null;

    //获取当前线程是操作系统
    public static native MyThread currentThread();


    @Override
    public void run() {

    }
}
