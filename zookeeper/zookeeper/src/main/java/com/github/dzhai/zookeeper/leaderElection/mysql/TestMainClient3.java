package com.github.dzhai.zookeeper.leaderElection.mysql;

public class TestMainClient3 {

	
	public static void main(String[] args) throws Exception {
		new MysqlLeaderElection("localhost:2181", "3");
		while(true);
	}
}
