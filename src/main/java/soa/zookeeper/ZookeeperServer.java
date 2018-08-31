package soa.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZookeeperServer {

	public static String url = "192.168.2.2:2181";
	static int sessionTimeOut = 2000;

	public static void testWatcher(ZooKeeper zookeeper) throws KeeperException, InterruptedException {
		zookeeper.create("/xuxianbei", "1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		
		Stat stat = zookeeper.exists("/xuxianbei", new Watcher(){

			@Override
			public void process(WatchedEvent event) {
				System.out.println(event.getType() + "->" + event.getPath());
				
				try {
//					zookeeper.getChildren(path, watch)
//					zookeeper.getData(path, watch, stat)
					zookeeper.exists("/xuxianbei", true);
				} catch (KeeperException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
		
		stat = zookeeper.setData("/xuxianbei", "2".getBytes(), stat.getVersion());
		
		Thread.sleep(1000);
		
		zookeeper.delete("/xuxianbei", stat.getVersion());
		
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		//建立连接
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		ZooKeeper zookeeper = new ZooKeeper(url, sessionTimeOut, new Watcher() {

			@Override
			public void process(WatchedEvent event) {
				System.out.println("默认事件: " + event.getType());
				// 如果收到了服务器的响应事件，连接成功
				if (Event.KeeperState.SyncConnected == event.getState()) {
					countDownLatch.countDown();
				}
				
			}
			
		});
		countDownLatch.await();
		
		// 测试watcher
		testWatcher(zookeeper);
		//创建节点测试
//		testCreate(zookeeper);
		
		zookeeper.close();
		
	}

	public static void testCreate(ZooKeeper zookeeper) throws KeeperException, InterruptedException {
		System.out.println(zookeeper.getState());
//		Thread.sleep(1000);
//		System.out.println(zookeeper.getState());
		try {
			// 创建节点。
			zookeeper.create("/root", "root data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// 获取节点内容  stat 表示节点状态。
		Stat stat = new Stat();
		byte[] data = zookeeper.getData("/root", false, stat);
//		
		System.out.println(new String(data));
		
		// 设置节点内容最多保存1M数据
		zookeeper.setData("/root", "hello".getBytes(), stat.getVersion());
		
		byte[] data1 = zookeeper.getData("/root", false, stat);
		System.out.println(new String(data1));
//		// 删除节点
		zookeeper.delete("/root", stat.getVersion());
//		
//		// 添加子节点
//		zookeeper.create("/root/child", "child data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//		
//		// 判断节点是否存在
//	    stat = zookeeper.exists("/root/child1", false);
//		System.out.println(stat == null ? "not exist" : "exist");
	}

}
