package com.github.dzhai.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.github.dzhai.BaseTestCase;
import com.github.dzhai.model.User;

public class UserMapperTestCase extends BaseTestCase {

	@Test
	public void selectByPrimaryKey() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 创建UserMapper对象，mybatis自动生成mapper代理对象
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		User user = userMapper.selectByPrimaryKey(1);
		log.info(user.toString());
	}
	
	
	
	
	@Test
	public void updateByPrimaryKeySelective() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			// 创建UserMapper对象，mybatis自动生成mapper代理对象
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			User user=new User();
			user.setId(1);
			user.setUsername("lisi");		
			userMapper.updateByPrimaryKeySelective(user);
			sqlSession.commit();
			log.info(user.toString());
			
		}catch(Exception e){
			sqlSession.rollback();
		} finally{			
			sqlSession.close();
		}
	}
}
