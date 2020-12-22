package concurrent.program.threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: xuxianbei
 * Date: 2020/12/19
 * Time: 15:43
 * Version:V1.0
 * 主要解决父线程池创建变量，子线程池无法传递问题。
 * 也可以解决同一个线程池，父线程set。子线程获取的情况
 */
public class TransmittableThreadLocalDemo {
    static TransmittableThreadLocal<String> threadLocal = new TransmittableThreadLocal<>();
    static ExecutorService pool = TtlExecutors.getTtlExecutorService(Executors.newFixedThreadPool(5));
    static ExecutorService pool2 = TtlExecutors.getTtlExecutorService(Executors.newFixedThreadPool(5));

    //如果采用jdk的threadLocal 难以保证在线程池执行的时候变量的传递
//    static ThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
    //static ThreadLocal<String> threadLocal = new ThreadLocal<>();
//    static ExecutorService pool2 = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            int index = i;
            pool.execute(new Thread(() -> {
                //传递值
                threadLocal.set("java " + index);
                pool2.execute(() -> {
                    //获取值
                    System.out.println(Thread.currentThread().getName() + " 获取修改后的值:" + threadLocal.get());
                });
            }));
        }

        try {
            Thread.sleep(1000);
            pool.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
