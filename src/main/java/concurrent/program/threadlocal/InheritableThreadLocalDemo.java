package concurrent.program.threadlocal;

/**
 * Name  InheritableThreadLocal
 * ，可以允许线程及该线程创建的子线程均可以访问同一个变量(有些OOP中的proteced的意味)，
 * 链接：https://www.imooc.com/article/26795?block_id=tuijian_wz
 * 简单的说就是父线程使用ThreadLocal 设置了一个值，那么该父线程创建的子线程也会拿到这个值。
 *
 * 实现方式：在创建新的线程时候判断父类线程inheritableThreadLocals是否存在，存在就赋值
 * @author xuxb
 * Date 2018-11-05
 * VersionV1.0
 * @description
 */
public class InheritableThreadLocalDemo {
    private static ThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();

    public static class MyRunnable implements Runnable {

        MyRunnable(String name) {
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
            threadLocal.set(2);
            System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
        }
    }


    public static void main(String[] args) throws InterruptedException {
        threadLocal.set(1);

        System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
        Thread t1 = new Thread(new MyRunnable("R-A"), "A");
        Thread t2 = new Thread(new MyRunnable("R-B"), "B");

        t1.start();
        Thread.sleep(100);
        t2.start();
    }
}
