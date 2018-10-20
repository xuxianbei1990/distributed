package concurrent.program.Exception;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 线程池异常处理
 * http://blog.onlycatch.com/post/Java线程池异常处理最佳实践
 * User: xuxb
 * Date: 2018-10-20
 * Time: 15:48
 * Version:V1.0
 */
public class ThreadPoolException {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        executeException();

        //callable 异常处理示例
        callableException();
    }

    private static void callableException() {
        ThreadPoolExecutor service = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        Callable callable = () -> {
            for (int i = 0; i < 10; i++) {
                if (i == 8 && (new Random()).nextInt(2) == 1) {
                    throw new RuntimeException("报异常啦");
                }
//                System.out.printf("%s: %d \n", Thread.currentThread().getName(), i);
            }
            return "SUCCESS";
        };
        List<Future<String>> resultList = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            Future<String> future = service.submit(callable);
            resultList.add(future);
        }

        do {
            for (int i = 0; i < resultList.size(); i++) {
                Future<String> result = resultList.get(i);
                System.out.printf("Task %d : %s \n", i, result.isDone());
            }
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } while (service.getCompletedTaskCount() < resultList.size());
        System.out.println("Results as folloers:" + service.getActiveCount());
        for (int i = 0; i < resultList.size(); i++) {
            Future<String> result = resultList.get(i);
            String number = null;
            try {
                number = result.get();// blocking method
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            System.out.printf("task: %d, result %s:\n", i, number);
        }
        System.out.println("Results as folloers:" + service.getActiveCount());
        service.shutdown();
    }

    // execute 线程池异常捕捉
    private static void executeException() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10,
                r -> {
                    Thread thread = new Thread(r);
                    thread.setUncaughtExceptionHandler(new ThreadExcepiton());
                    return thread;
                }
        );

        for (int j = 0; j < 10; j++) {
            executorService.execute(() -> {
                try {
                    for (int i = 3; i >= 0; i--) {
                        TimeUnit.SECONDS.sleep(1);
                        // 当i=0时候抛出的非受检异常，将导致当前线程被JVM杀死
                        // 但异常会被在上面设置的ThreadExceptionHandler捕获到，进而被处理
                        System.out.println(12 / i);
                    }
                } catch (InterruptedException e) {//受检异常直接在run方法体内部处理
                    System.out.println("我捕获异常啦");
                    e.printStackTrace();
                }

            });
        }

        Thread.sleep(1000);
        executorService.shutdown();
    }
}
