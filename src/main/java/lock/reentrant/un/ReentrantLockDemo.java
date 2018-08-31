package lock.reentrant.un;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 为每个锁关联一个获取计数器和一个所有者线程，当计数值为0时，
 * 这个锁就被认为是没有被任何线程所占有的。当线程请求一个未被持有的锁时，计数值将会递增。
 * 而当线程退出同步代码时，计数器会相应地递减。当计数值为0时，则释放该锁
 */
public class ReentrantLockDemo {
	private Lock lock = new ReentrantLock();

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
		ReentrantLockDemo reentrantLockDemo = new ReentrantLockDemo();
		Widget widget = reentrantLockDemo.new LoggingWidget();
		widget.doSomething();
	}

}
