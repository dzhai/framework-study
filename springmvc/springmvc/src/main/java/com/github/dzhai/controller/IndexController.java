package com.github.dzhai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.dzhai.model.User;

@Controller
@RequestMapping
public class IndexController {

	@RequestMapping("index")
	public String index() {
		return "index";
	}
	
	@RequestMapping("getUser")
	@ResponseBody
	public User getUser(){
		User user=new User();
		user.setPassword("password");
		user.setUsername("username");
		return user;
	}
}
