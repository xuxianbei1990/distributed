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
                    throw new RuntimeException(t.getName(), e);
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
        if (student.getAge()  == 1) {
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println("system" +  getStackTrace(e));
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        CompletableFutureDemo demo = new CompletableFutureDemo();
        demo.test();
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
