package com.github.dzhai.zookeeper.lock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

/**
 * 
 * 本类简单实现了分布式锁的机制
 * 
 * 采用单机zookeeper服务器测试；运行多次本机程序，相当于多个客户端用户 全部启动完成后，第一个客户端的会话需要手动中断，相当于触发客户端宕机现象
 * 
 * 
 * 本类实现的分布式锁避免羊群效应（Herd Effect），具体可详见代码
 * 
 * 
 * 
 * 
 * @author Liu Dengtao
 *
 *         2014-2-28
 */
public class DistributedLock extends ConnectionWatcher {

	public String join(String groupPath) throws KeeperException, InterruptedException {

		String path = groupPath + "/lock-" + zk.getSessionId() + "-";

		// 建立一个顺序临时节点
		String createdPath = zk.create(path, null/* data */, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println("Created " + createdPath);

		return createdPath;
	}

	/**
	 * 检查本客户端是否得到了分布式锁
	 * 
	 * @param groupPath
	 * @param myName
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public boolean checkState(String groupPath, String myName) throws KeeperException, InterruptedException {

		List<String> childList = zk.getChildren(groupPath, false);

		String[] myStr = myName.split("-");
		long myId = Long.parseLong(myStr[2]);

		boolean minId = true;
		for (String childName : childList) {
			String[] str = childName.split("-");
			long id = Long.parseLong(str[2]);
			if (id < myId) {
				minId = false;
				break;
			}
		}

		if (minId) {
			System.out.println(new Date() + "我得到了分布锁，哈哈！ myId:" + myId);

			return true;
		} else {
			System.out.println(new Date() + "继续努力吧，  myId:" + myId);

			return false;
		}
	}

	/**
	 * 若本客户端没有得到分布式锁，则进行监听本节点前面的节点（避免羊群效应）
	 * 
	 * @param groupPath
	 * @param myName
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void listenNode(final String groupPath, final String myName) throws KeeperException, InterruptedException {

		List<String> childList = zk.getChildren(groupPath, false);

		String[] myStr = myName.split("-");
		long myId = Long.parseLong(myStr[2]);

		List<Long> idList = new ArrayList<Long>();
		Map<Long, String> sessionMap = new HashMap<Long, String>();

		for (String childName : childList) {
			String[] str = childName.split("-");
			long id = Long.parseLong(str[2]);
			idList.add(id);
			sessionMap.put(id, str[1] + "-" + str[2]);
		}

		Collections.sort(idList);

		int i = idList.indexOf(myId);
		if (i <= 0) {
			throw new IllegalArgumentException("数据错误！");
		}

		// 得到前面的一个节点
		long headId = idList.get(i - 1);

		String headPath = groupPath + "/lock-" + sessionMap.get(headId);
		System.out.println("添加监听：" + headPath);

		Stat stat = zk.exists(headPath, new Watcher() {

			@Override
			public void process(WatchedEvent event) {
				System.out.println("已经触发了" + event.getType() + "事件！");

				try {
					while (true) {
						if (checkState(groupPath, myName)) {
							Thread.sleep(3000);
							System.out.println(new Date() + " 系统关闭！");
							System.exit(0);
						}

						Thread.sleep(3000);

					}
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

		});

		System.out.println(stat);

	}

	public static void main(String[] args) throws Exception {
		DistributedLock joinGroup = new DistributedLock();
		joinGroup.connect("localhost:" + "2181");

		// zookeeper的根节点；运行本程序前，需要提前生成
		String groupName = "zkRoot";
		String memberName = "_locknode_";
		String path = "/" + groupName + "/" + memberName;

		String myName = joinGroup.join(path);
		if (!joinGroup.checkState(path, myName)) {
			joinGroup.listenNode(path, myName);
		}

		Thread.sleep(Integer.MAX_VALUE);

		joinGroup.close();

	}
}