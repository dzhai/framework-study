package com.github.dzhai.zookeeper.leaderElection.mysql;

public class TestMainClient2 {

	
	public static void main(String[] args) throws Exception {
		new MysqlLeaderElection("localhost:2181", "2");
		Thread.sleep(20000);
	}
}
