package concurrent.program;

/**
 * User: xuxb
 * Date: 2018-09-13
 * Time: 17:29
 * Version:V1.0
 */
public class VolatileDemo {

    private static int a = 0, x= 0;
    private static int b = 0, y=0;
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()-> {
            a = 1;
            x = b;
        });
        Thread t2 = new Thread(() -> {
           b = 1;
           y = a;
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("x" + x +"y" + y);
    }
}
