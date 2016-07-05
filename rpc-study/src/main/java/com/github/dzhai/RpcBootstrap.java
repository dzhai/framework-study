package com.github.dzhai;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * RPC服务启动入口  
 */
public class RpcBootstrap {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("spring-zk-rpc-server.xml");
	}
}