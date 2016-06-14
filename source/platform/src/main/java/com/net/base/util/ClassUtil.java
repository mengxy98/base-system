/**
 * 
 */
package com.net.base.util;

import org.apache.commons.lang.StringUtils;

/**
 * 类说明: 类工具<br>
 * 创建时间: 2008-9-8 下午10:22:37<br>
 * 
 * @author 刘岩松<br>
 * @email: seraph115@gmail.com<br>
 */
public class ClassUtil {

	/**
	 * 功能说明: 将字符串首写字母转为大写<br>
	 * 创建者: 刘岩松<br>
	 * 创建时间: 2008-9-8 下午10:23:37<br>
	 * 
	 * @param entityName
	 * @return
	 */
	public static String getShortClassName(String entityName) {
		return StringUtils.capitalize(entityName);
	}

}
