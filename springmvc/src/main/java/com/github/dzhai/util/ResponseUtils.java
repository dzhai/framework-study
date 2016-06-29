package com.github.dzhai.util;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSONObject;

public class ResponseUtils {

	public static JSONObject returnJson(int code) {
		return returnJson(code, "");
	}

	public static JSONObject returnJson(int code, String message) {
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("message", message);
		return json;
	}

	public static JSONObject returnJson(String key, String value) {
		JSONObject json = new JSONObject();
		json.put(key, value);
		return json;
	}

	public static void write(HttpServletResponse response, int code) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(code+"");
		out.flush();
		out.close();
	}
}
