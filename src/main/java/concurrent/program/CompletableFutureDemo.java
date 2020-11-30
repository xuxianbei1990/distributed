package concurrent.program;

import lombok.extern.slf4j.Slf4j;
import serializable.Student;
import serializable.Teacher;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.*;

/**
 * @author: xuxianbei
 * Date: 2020/5/29
 * Time: 10:31
 * Version:V1.0
 */
@Slf4j
public class CompletableFutureDemo {


    /**
     * 线程池
     */
    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(10, 20,
            Integer.MAX_VALUE, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(),
            (r) -> {
                Thread thread = new Thread(r);
                thread.setUncaughtExceptionHandler((Thread t, Throwable e) -> {
                    System.out.println("thread RuntimeException");
                    throw new RuntimeException(t.getName(), e);
//                    System.out.println(e);
                });
                System.out.println("thread 1");
                return thread;
            });

    public Student getStudent() {
        Student student = new Student();
        student.setAge(1);
        student.setName("xxb");
        student.setTeacher(new Teacher());
        student.getTeacher().setName("lukaker");
        System.out.println("1");
        if (student.getAge() == 1) {
            //查下堆栈信息是否会产生死锁 不会
            throw new RuntimeException("sdf");
        }
        return student;
    }

    private void test() {
        try {
            Student student1 = CompletableFuture.supplyAsync(() -> getStudent(), EXECUTOR)
                    .handleAsync((student, throwable) -> {
                        if (throwable != null) {
                            throw new RuntimeException("future", throwable);
                        }
                        System.out.println(student);
                        return student;
                    }, EXECUTOR).get(1000, TimeUnit.MICROSECONDS);
            EXECUTOR.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println("system" + getStackTrace(e));
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    /**
     * completableFuture 异常处理
     */
    private void testCompletableFutureException() {
        try {
            CompletableFuture<Student> completableFuture = CompletableFuture.supplyAsync(() ->
                    getStudent(), EXECUTOR)
                    .exceptionally(e -> null);
            System.out.println(completableFuture.get());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println("system" + getStackTrace(e));
        }

        EXECUTOR.shutdown();
    }

    /**
     * Runnable 异常处理
     */
    private void testCompletableRunnableException() {
        try {
            CompletableFuture completableFuture = new CompletableFuture();

            EXECUTOR.execute(() -> {
                try {
                    completableFuture.complete(getStudent());
                } catch (Exception e) {
                    completableFuture.completeExceptionally(e);
                }
            });
            System.out.println(completableFuture.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println("system" + getStackTrace(e));
        }
        EXECUTOR.shutdown();
    }

    /**
     * Runnable 异常处理
     * 说明这里如果抛异常的话，异常会放到future进行返回处理，
     */
    private void testFutureException() {
       Future<Student> studentFuture = EXECUTOR.submit(() ->
                getStudent());
        try {
            System.out.println(studentFuture.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        EXECUTOR.shutdown();
    }


    public static void main(String[] args) {
        CompletableFutureDemo demo = new CompletableFutureDemo();
        demo.testFutureException();
    }

    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        String var3;
        try {
            throwable.printStackTrace(pw);
            var3 = sw.toString();
        } finally {
            pw.close();
        }

        return var3;
    }
}
