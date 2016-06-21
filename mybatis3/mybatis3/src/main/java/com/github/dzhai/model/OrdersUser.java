package com.github.dzhai.model;

import java.io.Serializable;

public class OrdersUser extends Orders implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;

	private String sex;

	private String address;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}