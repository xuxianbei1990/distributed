package soa.zookeeper.discover;

import java.util.List;

public interface LoadBalance {

	String selectHost(List<String> repos);
}
