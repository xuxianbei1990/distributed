package soa.zookeeper.discover;

import java.util.List;
import java.util.Random;

public class RandomLoadBalance extends AbsLoadBalance {

	@Override
	protected String doSelect(List<String> repos) {
		Random random = new Random();
		return repos.get(random.nextInt(repos.size()));
	}

}
