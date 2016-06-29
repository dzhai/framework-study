package com.github.dzhai.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.github.dzhai.util.RequestUtils;
import com.github.dzhai.util.ResponseUtils;
import com.github.dzhai.util.SessionUtils;

public class LoginHandlerInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		if (SessionUtils.isLogin(request)) {
			return true;
		} else {
			if (RequestUtils.isAjax(request)) {
				ResponseUtils.write(response, 501);
			} else {
				String contextPath = request.getContextPath();
				response.sendRedirect(contextPath + "/login.htm");
			}
			return false;
		}
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)throws Exception {

	}

}
