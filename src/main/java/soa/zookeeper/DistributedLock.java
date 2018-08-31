package soa.zookeeper;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class DistributedLock implements Lock, Watcher {

	private static final String ZOOKEEPER_ADDR = "192.168.2.2:2181";
	private static final String ROOT = "/Lock";

	private CountDownLatch countDownLatch;

	private ZooKeeper zookeeper;

	private String CURRENT_LOCK;

	private String WAIT_LOCK;

	public DistributedLock() {
		try {
			zookeeper = new ZooKeeper(ZOOKEEPER_ADDR, 4000, this);
			Stat stat = zookeeper.exists(ROOT, false);
			if (null == stat) {
				zookeeper.create(ROOT, "1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void process(WatchedEvent event) {
		if (this.countDownLatch != null) {
			countDownLatch.countDown();
		}

	}

	@Override
	public void lock() {
		if (tryLock()) {
			return;
		}
		waitForLock(WAIT_LOCK);
	}

	public boolean waitForLock(String prev) {
		try {
			Stat stat = zookeeper.exists(prev, true);
			if (null != stat) {
				System.out.println(Thread.currentThread().getName() + "->等待锁" + ROOT + '/' + prev + "释放");
			}
			countDownLatch = new CountDownLatch(1);
			countDownLatch.await();
			countDownLatch = null;
			System.out.println(Thread.currentThread().getName() + "->获得锁成功"  + CURRENT_LOCK);
		} catch (KeeperException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		return true;
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean tryLock() {
		try {
			// 创建临时有序节点
			CURRENT_LOCK = zookeeper.create(ROOT + "/", "1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
					CreateMode.EPHEMERAL_SEQUENTIAL);
			System.out.println(Thread.currentThread().getName() + "->" + CURRENT_LOCK + "尝试竞争锁");
			List<String> childrens = zookeeper.getChildren(ROOT, false);
			SortedSet<String> sortSet = new TreeSet<String>();
			for (String children : childrens) {
				sortSet.add(ROOT + "/" + children);
			}
			String firstNode = sortSet.first();
			if (CURRENT_LOCK.equals(firstNode)) {
				System.out.println(Thread.currentThread().getName() + "->" + CURRENT_LOCK + "获取锁成功");
				return true;
			}
			// 取出比该节点小的所有集合
			SortedSet<String> lessThenMe = sortSet.headSet(CURRENT_LOCK);
			if (!lessThenMe.isEmpty()) {
				WAIT_LOCK = lessThenMe.last();
			}

		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unlock() {
		System.out.println(Thread.currentThread().getName() +  "->释放锁");
		try {
			zookeeper.delete(CURRENT_LOCK, -1);
			CURRENT_LOCK = null;
			zookeeper.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (KeeperException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

}
