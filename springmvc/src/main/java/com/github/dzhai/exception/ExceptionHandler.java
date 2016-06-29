package com.github.dzhai.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import com.github.dzhai.util.RequestUtils;
import com.github.dzhai.util.ResponseUtils;

@Component
public class ExceptionHandler implements HandlerExceptionResolver {

	private Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,Exception ex) {
		log.error("error", ex);
		try {
			if (RequestUtils.isAjax(request)) {
				ResponseUtils.write(response, 500);
			} else {
				request.getRequestDispatcher("/error.htm").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView();
	}
}
