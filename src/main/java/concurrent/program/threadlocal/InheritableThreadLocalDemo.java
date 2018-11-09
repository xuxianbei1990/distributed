package concurrent.program.threadlocal;

/**
 * Name  InheritableThreadLocal
 * ，可以允许线程及该线程创建的子线程均可以访问同一个变量(有些OOP中的proteced的意味)，
 * 链接：https://www.imooc.com/article/26795?block_id=tuijian_wz
 *
 * @author xuxb
 * Date 2018-11-05
 * VersionV1.0
 * @description
 */
public class InheritableThreadLocalDemo {
    private static InheritableThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();

    public static class MyRunnable implements Runnable {

        MyRunnable(String name) {
            System.out.println(name + " => " + Thread.currentThread().getName() + ":" + threadLocal.get());
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
        }
    }


    public static void main(String[] args) {
        threadLocal.set(1);

        System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
        Thread t1 = new Thread(new MyRunnable("R-A"), "A");
        Thread t2 = new Thread(new MyRunnable("R-B"), "B");

        t1.start();
        t2.start();
    }
}
