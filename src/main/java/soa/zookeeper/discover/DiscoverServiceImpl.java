package soa.zookeeper.discover;

import java.util.ArrayList;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import soa.zookeeper.register.ZKConfig;

public class DiscoverServiceImpl implements IDiscoverService {
	
	private CuratorFramework curatorFramework;
	private List<String> repos = new ArrayList<>();

	{// Fluent编程风格
		curatorFramework = CuratorFrameworkFactory.builder().connectString(ZKConfig.CONNECTION_STR)
				.sessionTimeoutMs(4000).retryPolicy(new ExponentialBackoffRetry(1000, 10)).build();
	}
	
	@Override
	public String discover(String serviceName) {
		String path = ZKConfig.REGISTER_PATH + "/" + serviceName;
		try {
			repos = curatorFramework.getChildren().forPath(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 动态发现服务
		registerWatcher(path);
		
		// 负载均衡
		LoadBalance loadBanlance = new RandomLoadBalance();
		return loadBanlance.selectHost(repos);
	}
	
	private void registerWatcher(final String path) {
		PathChildrenCache childrenCache = new PathChildrenCache(curatorFramework, path, true);
		PathChildrenCacheListener pccl = new PathChildrenCacheListener() {

			@Override
			public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
				repos = curatorFramework.getChildren().forPath(path);
				
			}
			
		};
		childrenCache.getListenable().addListener(pccl);
		try {
			childrenCache.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
