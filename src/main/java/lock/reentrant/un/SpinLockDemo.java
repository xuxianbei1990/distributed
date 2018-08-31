package lock.reentrant.un;

import java.util.concurrent.atomic.AtomicReference;


/*
 * 不可重入锁
 * 输出结果：LoggingWidget calling doSomething
 * 我们可以看到在LoggingWidget类中doSomething方法时，
 * 通过锁进入临界区，并在临界区中调用了父类的该方法，而父类的方法要获取到同一个锁，被阻塞，导致死锁发生。
 * 注释掉的代码是改写重入锁代码 
 * 
 * 参考：https://www.jianshu.com/p/2afa9297bbcb
 */
class SpinLock {
	private AtomicReference<Thread> sign = new AtomicReference<>();

	public void lock() {
		Thread current = Thread.currentThread();
//		if (cur == owner.get()){
//            count ++;
//            return;
//        }
		
		while (!sign.compareAndSet(null, current)) {
		}
	}

	public void unlock() {
		Thread cur = Thread.currentThread();
//		if (cur == sign.get()){
//            if (count != 0){
//                count --;
//            } else {
            	sign.compareAndSet(cur,null);
//            }
//        }
		
	}
}

public class SpinLockDemo {
	private SpinLock lock = new SpinLock();

	class Widget {
		public void doSomething() {
			lock.lock();
			System.out.println("Widget calling doSomething");
			lock.unlock();
		}
	}

	class LoggingWidget extends Widget {
		@Override
		public void doSomething() {
			lock.lock();
			System.out.println("LoggingWidget calling doSomething");
			super.doSomething();
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		SpinLockDemo spinLockDemo = new SpinLockDemo();
		SpinLockDemo.Widget widget = spinLockDemo.new LoggingWidget();
		widget.doSomething();
	}

}
