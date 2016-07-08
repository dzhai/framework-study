package com.github.dzhai.zookeeper.leaderElection.mysql;

public class TestMainClient1 {

	
	public static void main(String[] args) throws Exception {
		new MysqlLeaderElection("localhost:2181", "1");
		Thread.sleep(10000);
	}
}
