package concurrent.program.Exception;

/**
 * User: xuxb
 * Date: 2018-09-14
 * Time: 22:30
 * Version:V1.0
 */
public class ThreadExceptionRun {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
//            try {
                Integer i = Integer.parseInt("ss");
                System.out.println(i);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        });
        thread.setUncaughtExceptionHandler(new ThreadExcepiton());
        thread.start();
    }
}
