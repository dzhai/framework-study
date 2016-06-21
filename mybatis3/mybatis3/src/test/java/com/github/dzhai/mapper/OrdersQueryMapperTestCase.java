package com.github.dzhai.mapper;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import com.github.dzhai.BaseTestCase;
import com.github.dzhai.model.OrderDetail;
import com.github.dzhai.model.Orders;
import com.github.dzhai.model.OrdersUser;
import com.github.dzhai.model.User;

public class OrdersQueryMapperTestCase extends BaseTestCase {
	
	@Test
	public void findOrdersUserResultMap() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			// 创建UserMapper对象，mybatis自动生成mapper代理对象
			OrdersQueryMapper ordersUserMapper = sqlSession.getMapper(OrdersQueryMapper.class);	
			List<Orders> list= ordersUserMapper.findOrdersUserResultMap();
			for(Orders order:list){
				log.info(order.getId()+"-"+order.getUser().getUsername());
			}	
		}finally{			
			sqlSession.close();
		}
	}
	
	
	@Test
	public void findOrdersUserResulType() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			// 创建UserMapper对象，mybatis自动生成mapper代理对象
			OrdersQueryMapper ordersUserMapper = sqlSession.getMapper(OrdersQueryMapper.class);		
			List<OrdersUser> list= ordersUserMapper.findOrdersUserResultType();
			for(OrdersUser order:list){
				log.info(order.getId()+"-"+order.getUsername());
			}	
		}finally{			
			sqlSession.close();
		}
	}
	
	
	@Test
	public void findOrdersAndOrderDetailResultMap() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			// 创建UserMapper对象，mybatis自动生成mapper代理对象
			OrdersQueryMapper ordersUserMapper = sqlSession.getMapper(OrdersQueryMapper.class);		
			List<Orders> list= ordersUserMapper.findOrdersAndOrderDetailResultMap();
			for(Orders order:list){				
				for(OrderDetail detail:order.getOrderdetails()){
					log.info(order.getId()+"-"+order.getUser().getUsername()+"-"+detail.getItemsId());
				}
			}				
		}finally{			
			sqlSession.close();
		}
	}
	
	@Test
	public void findUserAndItemsResultMap() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			// 创建UserMapper对象，mybatis自动生成mapper代理对象
			OrdersQueryMapper ordersUserMapper = sqlSession.getMapper(OrdersQueryMapper.class);		
			List<User> list= ordersUserMapper.findUserAndItemsResultMap();
			for(User user:list){				
				for(Orders order:user.getOrdersList()){				
					for(OrderDetail detail:order.getOrderdetails()){
						log.info(order.getId()+"-"+user.getUsername()+"-"+detail.getItems().getName());
					}
				}	
			}				
		}finally{			
			sqlSession.close();
		}
	}
}
