package com.github.dzhai.common;

import java.util.Properties;

import com.github.dzhai.util.ConfigUtils;

public class CommonConstants {
	
	public final static Properties COMMON=ConfigUtils.getPropertiesFile("conf/common.properties");	
	
	public static String getCommonValue(String key){		
		return COMMON.getProperty(key);
	}
}
