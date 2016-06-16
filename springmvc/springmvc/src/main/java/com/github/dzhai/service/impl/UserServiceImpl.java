package com.github.dzhai.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.github.dzhai.mapper.UserMapper;
import com.github.dzhai.model.User;
import com.github.dzhai.service.AbstractBaseServiceImpl;

@Service("userService")
public class UserServiceImpl extends AbstractBaseServiceImpl<User,Integer> {

	@Resource
	private UserMapper userMapper;

	@Override
	protected Object getCurrentDao() {

		return userMapper;
	}


}
