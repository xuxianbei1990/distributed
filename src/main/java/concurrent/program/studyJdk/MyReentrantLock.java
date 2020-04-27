package concurrent.program.studyJdk;

import java.lang.reflect.Constructor;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: xuxianbei
 * Date: 2020/4/26
 * Time: 11:34
 * Version:V1.0
 */
public class MyReentrantLock {
    private String value;

    public MyReentrantLock(String value) {
        this.value = value;
    }

    public static void main(String[] args) throws Exception {
        ReentrantLock reentrantLock = new ReentrantLock();
        try {
            Class clazz = MyReentrantLock.class;
            Constructor[] constructors = clazz.getConstructors();
            Constructor myReentrantLock = MyReentrantLock.class.getConstructor(constructors[0].getParameterTypes()[0]);
            System.out.println(myReentrantLock.newInstance("tt"));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
