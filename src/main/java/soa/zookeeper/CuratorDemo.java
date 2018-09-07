package soa.zookeeper;

import java.io.IOException;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

public class CuratorDemo {

	public static void main(String[] args) throws Exception {
		CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().connectString(ZookeeperServer.url)
				// 4种重试方式
				.sessionTimeoutMs(4000).retryPolicy(new ExponentialBackoffRetry(1000, 3)).namespace("curator").build();

		curatorFramework.start();

		//针对该节点，更新和创建  针对该节点的子节点 增删改
		addListenerWithTreeCache(curatorFramework, "/mic");
		
		//针对该节点，更新和创建
//		testWatcher(curatorFramework, "/mic");
		
		//针对该节点的子节点 增删改
//		testPathChildCache(curatorFramework, "/mic");
		//基本操作
//		testBase(curatorFramework);

		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		curatorFramework.close();

	}

	public static void addListenerWithTreeCache(CuratorFramework curatorFramework, String path) {
		TreeCache treeCache = new TreeCache(curatorFramework, path);
		TreeCacheListener treeCacheListener = new TreeCacheListener() {

			@Override
			public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
				System.out.println(event.getType() + "->" + event.getData().getPath());
				
			}
			
			
		};
		treeCache.getListenable().addListener(treeCacheListener);
		try {
			treeCache.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testPathChildCache(CuratorFramework curatorFramework, String path) {
		PathChildrenCache pathChildCache = new PathChildrenCache(curatorFramework, path, true);
		PathChildrenCacheListener pathChildrenCacheListener = new PathChildrenCacheListener() {

			@Override
			public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
				System.out.println("receive Event:" + event.getType());
				
			}
			
		};
		pathChildCache.getListenable().addListener(pathChildrenCacheListener);
		try {
			pathChildCache.start(PathChildrenCache.StartMode.NORMAL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * PathChildCache 监听一个节点下子节点创建、删除、更新
	 * NodeCache 监听一个节点的更新和创建事件
	 * TreeCache 综合PatchChildCache 和 NodeCache特性
	 */
	public static void testWatcher(CuratorFramework curatorFramework, String path) {
		final NodeCache nodeCache = new NodeCache(curatorFramework, path, false);
		NodeCacheListener nodeCacheListener = new NodeCacheListener(){

			@Override
			public void nodeChanged() throws Exception {
				System.out.println("Receive Event: " + nodeCache.getCurrentData().getPath());
				
			}
			
		};
		
		
	}
	
	public static void testBase(CuratorFramework curatorFramework) throws Exception {
		// 原生api必须逐层创建。父节点必须存在
		// 创建
//		curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/mic/node1",
//				"1".getBytes());

		Stat stat = new Stat();
		curatorFramework.getData().storingStatIn(stat).forPath("/mic/node1");

		curatorFramework.setData().withVersion(stat.getVersion()).forPath("/mic/node1", "xx".getBytes());

		// 删除
		// curatorFramework.delete().deletingChildrenIfNeeded().forPath("/mic/node1");
	}

}
