package com.github.dzhai.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

public class ZKUtils {

	public static final String ZK_ADDRESS = "127.0.0.1:2181";
	
	private static class CuratorFrameworkSingle{
		
		private static CuratorFramework client=CuratorFrameworkFactory.newClient(
                ZK_ADDRESS,
                new RetryNTimes(10, 5000));
	}
	
	
	public static CuratorFramework getCuratorFramework(){
		return ZKUtils.CuratorFrameworkSingle.client;
	}
}
