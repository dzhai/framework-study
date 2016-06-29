package com.github.dzhai.interceptor;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

@ControllerAdvice
public class JsonpResponseBodyAdvice extends AbstractJsonpResponseBodyAdvice{

	public JsonpResponseBodyAdvice(){
		super("callback");
	}
}
