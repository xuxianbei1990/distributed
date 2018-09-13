package concurrent.program;

/**
 * User: xuxb
 * Date: 2018-09-08
 * Time: 21:00
 * Version:V1.0
 */
public class SynchronizeDemo {
    private  static SynchronizeDemo synchronizeDemo = null;

    public void test() {
        //作用域是对象
        synchronized (this) {
            System.out.println("1");

        }
    }

    //作用域是类
    public static  SynchronizeDemo GetInstance() {
        if (synchronizeDemo == null) {
            synchronizeDemo = new SynchronizeDemo();
        }
        synchronizeDemo.test();
        return synchronizeDemo;
    }

    public static void main(String[] args) {
        SynchronizeDemo.GetInstance();
    }
}
