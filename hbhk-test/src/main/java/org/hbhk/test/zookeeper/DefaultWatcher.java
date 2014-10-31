package org.hbhk.test.zookeeper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * 默认的监听器
 */
public class DefaultWatcher implements Watcher {

	protected Log log = LogFactory.getLog(getClass());

	public void process(WatchedEvent event) {
		log.info("default-watcher path:" + event.getPath() + " state:"
				+ event.getState());
	}

}
