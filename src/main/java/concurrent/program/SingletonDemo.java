package concurrent.program;

/**
 * User: xuxb
 * Date: 2018-09-12
 * Time: 22:41
 * Version:V1.0
 * 单例模式
 */
//多线程单例模式
class MutiThread {
    /*
    ，通过Java反射机制是能够实例化构造方法为private的类的，
    那基本上会使所有的Java单例实现失效。此问题在此处不做讨论，
    姑且闭着眼就认为反射机制不存在。）
     */
    private MutiThread() {
    }

    private static final MutiThread oneThread = new MutiThread();

    public static MutiThread GetInstance() {
        return oneThread;
    }

}

// 多线程调用才初始化
class MutiThreadDispatch {
    // 核心代码volatile 防止重排序
    private volatile static MutiThreadDispatch mutiThreadDispatch = null;

    private MutiThreadDispatch() {

    }

    public MutiThreadDispatch getInstance() {
        if (mutiThreadDispatch == null) {
            synchronized (MutiThreadDispatch.class) {
                if (mutiThreadDispatch == null) {
                        /*
                        多线程环境下：new MutiThreadDispatch(）分成三步的会因为
                        指令重排序导致异常
                        1 memory=allocate();// 分配内存 相当于c的malloc
                        2 ctorInstanc(memory) //初始化对象
                        3 instance=memory //设置instance指向刚分配的地址
                         */
                    mutiThreadDispatch = new MutiThreadDispatch();
                }
            }
        }
        return mutiThreadDispatch;
    }
}

//最好的单例模式
enum EasySingleton {
    INSTANCE;

    public void main() {
        System.out.println("1");
    }
}

//单线程单例模式  多线程下会出现多个实例
class OneThread {
    private OneThread() {

    }

    private static OneThread oneThread = null;

    public static OneThread getInstance() {
        if (null == oneThread) {
            oneThread = new OneThread();
        }
        return oneThread;
    }
}


public class SingletonDemo {

}
