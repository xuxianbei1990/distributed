package concurrent.program;

/**
 * User: xuxb
 * Date: 2018-09-08
 * Time: 21:00
 * Version:V1.0
 */
public class SynchronizeDemo {
    public  void  test () {
        synchronized (this) {
            System.out.println("1");
        }

    }
}
