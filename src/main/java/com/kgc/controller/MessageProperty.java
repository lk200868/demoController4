/**
 * Project Name:washing-common
 * File Name:MessageProperty.java
 * Package Name:cn.com.bluemoon.washing.common.error
 * Date:2016年12月2日下午2:31:46
 * Copyright (c) 2016, Amos All Rights Reserved.
 */
package com.kgc.controller;

import java.io.InputStreamReader;
import java.util.Properties;

/**
 * ClassName:MessageProperty<br/>
 * Function: 信息提示配置<br/>
 * Date:2017/9/29 17:44
 * 
 * @author Amos
 * @version 1.0.0
 * @since JDK1.8
 * @see
 */
public class MessageProperty {
	/**
	 * 0000=操作成功 1001参数异常 1002=数据不存在 9999=服务异常
	 */
	public static final String SUCCESS_CODE = "0000";
	public static final String ERROR_PARAM_CODE = "1001";
	public static final String ERROR_OBJECT_NOT_FOUND_CODE = "1002";

	public static final String ERROR_SERVICE_CODE = "9999";


	private static MessageProperty messageProperty = new MessageProperty();
	private static Properties properties = new Properties();

	static {
		try {
			properties.load(new InputStreamReader (MessageProperty.class.getResourceAsStream("/message.properties"), "UTF-8"));
		} catch (Exception e) {
		}
	}

	/**
	 * 
	 * getInstance:创建单例模式的Configs文件读取类. <br/>
	 *
	 * @author Amos
	 * @return
	 * @since JDK 1.8
	 */
	public static MessageProperty getInstance() {
		if (null == messageProperty) {
			messageProperty = new MessageProperty();
		}
		return messageProperty;
	}

	/**
	 * 
	 * getProperty:读取配置文件中的值，如果读取不到就返回"". <br/>
	 *
	 * @author Amos
	 * @param key
	 * @return
	 * @since JDK 1.7
	 */
	public String getProperty(String key) {
		String result = null;
		if (properties.containsKey(key)) {
			result = properties.getProperty(key, "");
		}
		return result;
	}
}
