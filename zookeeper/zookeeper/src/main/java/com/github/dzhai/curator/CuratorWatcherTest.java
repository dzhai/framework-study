package com.github.dzhai.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.RetryNTimes;

/**
 * Curator framework watch test.
 */
public class CuratorWatcherTest {

	/** Zookeeper info */
	private static final String ZK_ADDRESS = "127.0.0.1:2181";
	private static final String ZK_PATH = "/zktest";

	
	public static void main(String[] args) throws Exception {
  
		//pathCache();
		//nodeCache();
		treeCache();
    }
	
	
	private static void pathCache() throws Exception{
	      // 1.Connect to zk
        CuratorFramework client = CuratorFrameworkFactory.newClient(
                ZK_ADDRESS,
                new RetryNTimes(10, 5000)
        );
        client.start();
        System.out.println("zk client start successfully!");

        // 2.Register watcher
        //path cache
        PathChildrenCache watcher = new PathChildrenCache(
                client,
                ZK_PATH,
                true    // if cache data
        );
        watcher.getListenable().addListener(new PathChildrenCacheListener() {
			@Override
			public void childEvent(CuratorFramework client1, PathChildrenCacheEvent event) throws Exception {
				  ChildData data = event.getData();
		            if (data == null) {
		                System.out.println("No data in event[" + event + "]");
		            } else {
		                System.out.println("Receive event: "
		                        + "type=[" + event.getType() + "]"
		                        + ", path=[" + data.getPath() + "]"
		                        + ", data=[" + new String(data.getData()) + "]"
		                        + ", stat=[" + data.getStat() + "]");
		            }
				
			} 
          
        });
        watcher.start(StartMode.BUILD_INITIAL_CACHE);
        System.out.println("Register zk watcher successfully!");

        Thread.sleep(Integer.MAX_VALUE);
	} 
	private static void nodeCache() throws Exception{
		   // 1.Connect to zk
        CuratorFramework client = CuratorFrameworkFactory.newClient(
                ZK_ADDRESS,
                new RetryNTimes(10, 5000)
        );
        client.start();
        System.out.println("zk client start successfully!");

        // 2.Register watcher
        //path cache
        NodeCache watcher = new NodeCache(
                client,
                ZK_PATH,
                true    // if cache data
        );
        watcher.getListenable().addListener(new NodeCacheListener() {
			@Override
			public void nodeChanged() throws Exception {
				
				System.out.println("change............");
			} 
          
        });
        watcher.start();
        
        System.out.println("Register zk watcher successfully!");

        Thread.sleep(Integer.MAX_VALUE);
		
	} 
	private static void treeCache() throws Exception{
		   // 1.Connect to zk
        CuratorFramework client = CuratorFrameworkFactory.newClient(
                ZK_ADDRESS,
                new RetryNTimes(10, 5000)
        );
        client.start();
        System.out.println("zk client start successfully!");

        // 2.Register watcher
        //path cache
        TreeCache watcher = new TreeCache(
                client,
                ZK_PATH
        );
        watcher.getListenable().addListener(new TreeCacheListener() {
			@Override
			public void childEvent(CuratorFramework client1, TreeCacheEvent event) throws Exception {
				  ChildData data = event.getData();
		            if (data == null) {
		                System.out.println("No data in event[" + event + "]");
		            } else {
		                System.out.println("Receive event: "
		                        + "type=[" + event.getType() + "]"
		                        + ", path=[" + data.getPath() + "]"
		                        + ", data=[" + new String(data.getData()) + "]"
		                        + ", stat=[" + data.getStat() + "]");
		            }
				
			}
       
        });
        watcher.start();
        System.out.println("Register zk watcher successfully!");

        Thread.sleep(Integer.MAX_VALUE);
	} 
}