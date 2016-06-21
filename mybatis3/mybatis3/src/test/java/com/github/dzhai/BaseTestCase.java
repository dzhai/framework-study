package com.github.dzhai;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTestCase {

	protected SqlSessionFactory sqlSessionFactory;
	
	protected Logger log=LoggerFactory.getLogger(this.getClass());

	@Before
	public void before() throws Exception {
		// mybatis配置文件
		String resource = "mybatis-config.xml";
		// 得到配置文件的流
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// 创建会话工厂SqlSessionFactory,要传入mybaits的配置文件的流
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}

}
