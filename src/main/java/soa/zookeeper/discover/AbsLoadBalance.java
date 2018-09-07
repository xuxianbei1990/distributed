package soa.zookeeper.discover;

import java.util.List;

public abstract class AbsLoadBalance implements LoadBalance {

	@Override
	public String selectHost(List<String> repos) {
		if (repos == null || repos.size() == 0) {
			return null;
		}
		if (repos.size() == 1) {
			return repos.get(0);
		}
		return doSelect(repos);
	}

	protected abstract String doSelect(List<String> repos);
}
