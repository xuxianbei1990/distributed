package soa.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZooKeeperLockTest {

	public static void testCuratorLock() {
		CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().connectString(ZookeeperServer.url)
				// 4种重试方式
				.sessionTimeoutMs(4000).retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
		curatorFramework.start();
		InterProcessMutex interProcessMutex = new InterProcessMutex(curatorFramework, "/locks");

		CountDownLatch countDownLatch = new CountDownLatch(10);
		for (int i = 0; i < 10; i++) {
			new Thread(() -> {

				try {
					countDownLatch.await();
					interProcessMutex.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}, "Thread-" + i).start();
			countDownLatch.countDown();
		}
	}

	public static void main(String[] args) throws IOException {
//		testDistributedLock();
		testCuratorLock();
		System.in.read();
	}

	public static void testDistributedLock() {
		CountDownLatch countDownLatch = new CountDownLatch(10);
		for (int i = 0; i < 10; i++) {
			new Thread(() -> {

				try {
					countDownLatch.await();
					DistributedLock distributedLock = new DistributedLock();
					distributedLock.lock();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}, "Thread-" + i).start();
			countDownLatch.countDown();
		}
	}

}
