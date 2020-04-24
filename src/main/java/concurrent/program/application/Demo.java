package concurrent.program.application;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * User: xuxb
 * Date: 2018-09-11
 * Time: 17:38
 * Version:V1.0
 */
public class Demo {
    PrintProcessor printProcessor;

    public Demo() {
        SaveProcessor saveProcessor = new SaveProcessor();
        saveProcessor.start();
        printProcessor = new PrintProcessor(saveProcessor);
        printProcessor.start();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Callable<Integer> callable = () -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("1");
            }
            return 2;
        };

        FutureTask<Integer> future = new FutureTask(callable);
        Thread threadcal = new Thread(future);
        threadcal.start();
        threadcal.interrupt();
        System.out.println(future.get()) ;
    }

    private static void ThreadTest() throws InterruptedException {
        Thread thread = new Thread(() -> {
            Thread son = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("Begin son");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("son");
                }
            }, "ts");
            son.setDaemon(true);
            son.start();
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
                System.out.println("1");
                throw new RuntimeException("dddsst");
            }

        });
        thread.setUncaughtExceptionHandler((t, e) -> {
            System.out.println(t.getName());
            e.printStackTrace();
        });
        thread.setName("test");
        //守护线程；一般是false, 如果true 谁创建这个线程，它就守护谁，并随被守护者消亡而消亡
        thread.setDaemon(false);
        //线程启动
        thread.start();
        Thread.sleep(10);
//        //线程关闭
        thread.interrupt();
        //等待thread 结束
        thread.join();
    }

    /**
     * 责任链的概念
     */
    private static void testRequest() {
        Request request = new Request();
        request.setName("xxb");
        new Demo().doTest(request);
    }

    public void doTest(Request request) {
        printProcessor.processorRequest(request);
    }
}
