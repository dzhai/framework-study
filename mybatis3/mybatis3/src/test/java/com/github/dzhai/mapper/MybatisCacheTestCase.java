package com.github.dzhai.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.github.dzhai.BaseTestCase;
import com.github.dzhai.model.User;

public class MybatisCacheTestCase extends BaseTestCase {

	@Test
	public void level1() {
		SqlSession sqlSession = sqlSessionFactory.openSession();// 创建代理对象
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

		// 下边查询使用一个SqlSession
		// 第一次发起请求，查询id为1的用户
		User user1 = userMapper.selectByPrimaryKey(1);
		System.out.println(user1);

		// 如果sqlSession去执行commit操作（执行插入、更新、删除），清空SqlSession中的一级缓存，这样做的目的为了让缓存中存储的是最新的信息，避免脏读。

		// 更新user1的信息
		user1.setUsername("测试用户22");
		userMapper.updateByPrimaryKey(user1);
		// 执行commit操作去清空缓存
		sqlSession.commit();

		// 第二次发起请求，查询id为1的用户
		User user2 = userMapper.selectByPrimaryKey(1);
		System.out.println(user2);
		
		User user3 = userMapper.selectByPrimaryKey(1);
		System.out.println(user3);
		sqlSession.close();
	}
	
	@Test
	public void level2(){
	    SqlSession sqlSession1 = sqlSessionFactory.openSession();
	    SqlSession sqlSession2 = sqlSessionFactory.openSession();
	    SqlSession sqlSession3 = sqlSessionFactory.openSession();
	    // 创建代理对象
	    UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
	    // 第一次发起请求，查询id为1的用户
	    User user1 = userMapper1.selectByPrimaryKey(1);
	    System.out.println(user1);  
	    //这里执行关闭操作，将sqlsession中的数据写到二级缓存区域
	    sqlSession1.close();

	    //sqlSession3用来清空缓存的，如果要测试二级缓存，需要把该部分注释掉
	    //使用sqlSession3执行commit()操作
	    UserMapper userMapper3 = sqlSession3.getMapper(UserMapper.class);
	    User user  = userMapper3.selectByPrimaryKey(1);	    
	    user.setUsername("倪升武");
	    userMapper3.updateByPrimaryKey(user);
	    //执行提交，清空UserMapper下边的二级缓存
	    sqlSession3.commit();
	    sqlSession3.close();

	    UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);
	    // 第二次发起请求，查询id为1的用户
	    User user2 = userMapper2.selectByPrimaryKey(1);
	    System.out.println(user2);

	    sqlSession2.close();
	}
}
