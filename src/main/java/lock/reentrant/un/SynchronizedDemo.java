package lock.reentrant.un;

import java.util.concurrent.ConcurrentHashMap;

/*
 * 我们可以看到Synchronized关键字是可重入锁。
 */
public class SynchronizedDemo {
	class Widget {
		public synchronized void doSomething() {
			System.out.println("Widget calling doSomething...");
		}
	}

	class LoggingWidget extends Widget {
		@Override
		public synchronized void doSomething() {
			System.out.println("LoggingWidget calling doSomething");
			super.doSomething();
		}
	}

	public static void main(String[] args) {
		SynchronizedDemo synchronizedDemo = new SynchronizedDemo();
		SynchronizedDemo.Widget widget = synchronizedDemo.new LoggingWidget();
		widget.doSomething();

		ConcurrentHashMap concurrentHashMap;
	}

}
