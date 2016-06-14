/**
 * 
 */
package com.net.base.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 类说明: 应用启动后，绑定Spring上下文<br>
 * 创建时间: 2011-3-25 下午02:56:43<br>
 * 
 * @author 刘岩松<br>
 * @email: seraph115@gmail.com<br>
 */
public class SpringContextBinder implements ApplicationContextAware {

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContext.setApplicationContext(applicationContext);
	}

}
