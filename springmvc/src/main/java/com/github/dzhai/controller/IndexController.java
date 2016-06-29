package com.github.dzhai.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.dzhai.model.User;
import com.github.dzhai.service.IBaseService;
import com.github.dzhai.service.IUserService;

@Controller
@RequestMapping
public class IndexController {

	@Autowired
	private IBaseService<User,Integer> userService;
	
	@Autowired
	private IUserService<User,Integer> userService2;
	
	@RequestMapping("index")
	public String index() {
		return "index";
	}
	
	@RequestMapping("getUser")
	@ResponseBody
	public User getUser(){	
		//User user=userService.selectByPrimaryKey(1);
		User user=userService2.selectByPrimaryKey(1);
		return user;
	}
}
