package soa.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;

public class ZWWatcher implements Watcher {

	@Override
	public void process(WatchedEvent event) {
		if (event.getType() == EventType.NodeDeleted) {
			System.out.println("node delete");
		} else if (event.getType() == EventType.NodeChildrenChanged) {
			System.out.println("node NodeChildrenChanged");
		} else if (event.getType() == EventType.NodeCreated) {
			System.out.println("node NodeCreated");
		} else if (event.getType() == EventType.NodeDataChanged) {
			System.out.println("node NodeDataChanged");
		}

	}

}
