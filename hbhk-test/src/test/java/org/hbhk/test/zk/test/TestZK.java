package org.hbhk.test.zk.test;

import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.hbhk.test.zookeeper.WatchInvoke;

public class TestZK extends WatchInvoke {

	private String path = "";

	@Override
	public boolean isMatch(String path, EventType type) {
		return needContinueWatch(path, type, null);
	}

	@Override
	public boolean needContinueWatch(String path, EventType type,
			KeeperState state) {
		if (path != null && path.startsWith(this.path)
				&& type == EventType.NodeDataChanged) {
			return true;
		}
		return false;
	}

	@Override
	public String getListenPath() {
		return path;
	}

	@Override
	public void invoke(String path) {
		
	}

}
