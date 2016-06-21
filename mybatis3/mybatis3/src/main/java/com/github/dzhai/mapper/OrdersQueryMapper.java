package com.github.dzhai.mapper;

import java.util.List;

import com.github.dzhai.model.Orders;
import com.github.dzhai.model.OrdersUser;
import com.github.dzhai.model.User;

public interface OrdersQueryMapper {

	public List<OrdersUser> findOrdersUserResultType();

	public List<Orders> findOrdersUserResultMap();
	
	
	public List<Orders> findOrdersAndOrderDetailResultMap();
	
	
	public List<User> findUserAndItemsResultMap(); 
}
