package com.github.dzhai.rpc;

import com.github.dzhai.annotation.RpcService;

/**
 * 实现服务接口  
 */
@RpcService(HelloService.class) // 指定远程接口
public class HelloServiceImpl implements HelloService {

	@Override
	public String hello(String name) {
		return "Hello! " + name;
	}

}