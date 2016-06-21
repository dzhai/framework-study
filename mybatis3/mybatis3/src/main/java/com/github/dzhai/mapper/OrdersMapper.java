package com.github.dzhai.mapper;

import com.github.dzhai.model.Orders;
import com.github.dzhai.model.OrdersUser;

public interface OrdersMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Orders record);

	int insertSelective(Orders record);

	Orders selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Orders record);

	int updateByPrimaryKey(Orders record);

	/**
	 * ResultType
	 * 
	 */
	Orders findOrdersResultType(Integer id);

	/**
	 * ResultMap
	 * 
	 */
	OrdersUser findOrdersResultMap(Integer id);
}