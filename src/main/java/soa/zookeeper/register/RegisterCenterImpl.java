package soa.zookeeper.register;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class RegisterCenterImpl implements IRegisterCenter {

	private CuratorFramework curatorFramework;

	{// Fluent编程风格
		curatorFramework = CuratorFrameworkFactory.builder().connectString(ZKConfig.CONNECTION_STR)
				.sessionTimeoutMs(4000).retryPolicy(new ExponentialBackoffRetry(1000, 10)).build();
	}

	@Override
	public boolean register(String serviceName, String serviceAddress) {
		String servicePath = ZKConfig.REGISTER_PATH + "/" + serviceName;
		try {
			if (curatorFramework.checkExists().forPath(servicePath) == null) {
				curatorFramework.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT)
						.forPath(servicePath);
			}

			String addressPath = servicePath + "/" + serviceAddress;
			String rsNode = curatorFramework.create().withMode(CreateMode.PERSISTENT)
					.forPath(addressPath);
			System.out.println("服务注册成功：" + rsNode);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
