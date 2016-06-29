package com.github.dzhai.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class SessionUtils {

	// public static Admin getAdmin(HttpServletRequest request) {
	// try {
	// Admin admin = (Admin)
	// request.getSession().getAttribute(CommonConstants.SESSION_NAME);
	// return admin;
	// } catch (Exception e) {
	//
	// }
	// return null;
	// }

	// public static String getAccount(HttpServletRequest request){
	// Admin admin=getAdmin(request);
	// if(admin==null){
	// return null;
	// }
	// return admin.getAccount();
	// }

	public static boolean isLogin(HttpServletRequest request) {

		// if (request.getSession().getAttribute(CommonConstants.SESSION_NAME)
		// != null) {
		// return true;
		// } else {
		// return false;
		// }

		return false;

	}

	// public static void login(Admin admin, HttpServletRequest request) {
	// request.getSession().setAttribute(CommonConstants.SESSION_NAME, admin);
	// }
}
