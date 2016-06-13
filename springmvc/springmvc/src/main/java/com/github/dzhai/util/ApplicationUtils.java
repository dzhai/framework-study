package com.github.dzhai.util;

import javax.servlet.ServletContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ServletContextAware;

public class ApplicationUtils implements ServletContextAware, ApplicationContextAware {

	private static ServletContext servletContext;

	private static ApplicationContext applicationContext;

	@Override
	public void setServletContext(ServletContext context) {
		servletContext = context;
		context.setAttribute("contextPath", context.getContextPath());
		//TODO 页面用到的全局变量		
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		applicationContext = arg0;
	}

	public static ServletContext getServletContext() {
		return servletContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static Object getAttribute(String name) {
		return servletContext.getAttribute(name);
	}

	public static void setAttribute(String name, Object value) {
		servletContext.setAttribute(name, value);
	}

	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

}
