package soa.zookeeper;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

public class ZookeeperServer {

	static String url = "192.168.174.128:22";
	static int sessionTimeOut = 200;

	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		ZooKeeper zookeeper = new ZooKeeper(url, sessionTimeOut, null);
		try {
			// 创建节点。
			zookeeper.create("/root", "root data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 删除节点
		zookeeper.delete("/root", -1);
		// 设置节点内容最多保存1M数据
		zookeeper.setData("/root", "hello".getBytes(), -1);
		// 获取节点内容  stat 表示节点状态。
		Stat stat = new Stat();
		byte[] data = zookeeper.getData("/root", false, stat);
		
		System.out.println(data);
		// 添加子节点
		zookeeper.create("/root/child", "child data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		
		// 判断节点是否存在
	    stat = zookeeper.exists("/root/child1", false);
		System.out.println(stat == null ? "not exist" : "exist");
		
		
		
	}

}
