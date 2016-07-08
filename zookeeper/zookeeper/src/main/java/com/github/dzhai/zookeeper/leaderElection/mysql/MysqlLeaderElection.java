package com.github.dzhai.zookeeper.leaderElection.mysql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class MysqlLeaderElection {

	protected ZooKeeper zk;

	protected String nodeName;

	public MysqlLeaderElection(String connectString, String nodeName) {
		try {
			ProposoWatcher wc = new ProposoWatcher();
			this.zk = new ZooKeeper(connectString, 60000, wc);
			this.nodeName = nodeName;

			findLeader();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void findLeader() throws Exception {
		if (zk.exists("/master", false) == null) {
			zk.create("/master", null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		if (zk.exists("/servers", false) == null) {
			zk.create("/servers", null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		zk.create("/servers/" + nodeName, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

		List<String> list = zk.getChildren("/servers", false);
		String[] nodes = list.toArray(new String[list.size()]);
		Arrays.sort(nodes);
		if (nodeName.equals(nodes[0])) {// 初次
			System.out.println("this is master" + nodes[0]);
			zk.setData("/master", nodeName.getBytes(), -1);

		} else {
			// 监控比自己次小的Node
			String lower = "";
			for (int i = 0; i < nodes.length; i++) {
				if (nodeName.equals(nodes[i])) {
					lower = nodes[i - 1];
					break;
				}
			}
			zk.exists("/servers/" + lower, true);
		}
	}

	class ProposoWatcher implements Watcher {
		@Override
		public void process(WatchedEvent event) {
			switch (event.getType()) {
			case None: {
				// connectionEvent(event);
				break;
			}
			case NodeDeleted: {
				// servers.remove(event.getPath());
				System.out.println("node  removed**" + event.getPath());
				String delName = event.getPath().substring(event.getPath().lastIndexOf("/"));
				// final ZooKeeper zk1 = zk;
				List<String> children = new ArrayList<String>();
				try {
					children = zk.getChildren("/servers", false);
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				String[] nodes = children.toArray(new String[children.size()]);
				Arrays.sort(nodes);
				for (int i = 0; i < nodes.length; i++) {
					if (nodes[i].compareTo(delName) > 0) {
						// TODO:curr Node become master
						System.out.println("this is master" + nodes[i]);
						try {
							zk.setData("/master", nodes[i].getBytes(), -1);
						} catch (KeeperException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						break;
					}
				}
				break;
			}
			default:
				break;
			}
		}
	}
}