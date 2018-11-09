package concurrent.program.threadlocal;

import java.util.function.Supplier;

/**
 * Name
 *
 * @author xuxb
 * Date 2018-11-05
 * VersionV1.0
 * @description
 */
class User {
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

public class ThreadLocalDemo {
    public final static ThreadLocal<String> TEST_THREAD_NAME_LOCAL = new ThreadLocal<>();
    public final static ThreadLocal<String> TEST_THREAD_VALUE_LOCAL = new ThreadLocal<>();
    public final static ThreadLocal<User> USER_THREAD_LOCAL =
            ThreadLocal.withInitial((Supplier) () -> new User("1", 1)
            );

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            final String name = "线程-【" + i + "】";
            final String value = String.valueOf(i);
            new Thread() {
                public void run() {
                    try {
                        TEST_THREAD_NAME_LOCAL.set(name);
                        TEST_THREAD_VALUE_LOCAL.set(value);
                        System.out.println(USER_THREAD_LOCAL.get().toString());
                        callA();
                    } finally {
                        TEST_THREAD_NAME_LOCAL.remove();
                        TEST_THREAD_VALUE_LOCAL.remove();
                        USER_THREAD_LOCAL.remove();
                    }
                }
            }.start();
        }
    }

    public static void callA() {
        new ThreadLocalDemo().callB();
    }

    public static void callB() {
        System.out.println(TEST_THREAD_NAME_LOCAL.get() + "/t=/t" + TEST_THREAD_VALUE_LOCAL.get());
    }

}
