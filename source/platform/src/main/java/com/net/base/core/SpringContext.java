package com.net.base.core;

import org.springframework.context.ApplicationContext;

/**
 * 类说明:<br>
 * 创建时间: 2007-12-4 下午03:50:57<br>
 * 
 * @author 刘岩松<br>
 * @email: seraph115@gmail.com<br>
 * @see com.seraph.bi.suite.support.core.SpringContextBinder
 */
public class SpringContext {

	public static ApplicationContext applicationContext;

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(
			ApplicationContext applicationContext) {
		SpringContext.applicationContext = applicationContext;
	}

	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}
}
