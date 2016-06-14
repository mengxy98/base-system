package com.net.base.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description:cookie工具类
 * @Company:东方网信股份有限公司
 * @Date: 2016年2月4日
 * @Time: 下午1:52:05
 * @author huanghy@net-east.com
 */
public class CookieUtil {

	public static final String JSESSIONID = "JSESSIONID";

	public static Cookie[] getCookies(HttpServletRequest request) {
		return request.getCookies();
	}

	/**
	 * @方法名:
	 * @方法说明:根据名称获取cookie
	 * @修改记录:
	 * @日期:2016年2月4日
	 * @修改者:haiyang
	 * @修改内容:
	 * @author haiyang
	 * @version 1.0
	 * @since:
	 */
	public static Cookie getCookieByName(HttpServletRequest request, String name) {
		Cookie[] cookies = getCookies(request);

		if (cookies.length > 0) {

			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(name)) {
					return cookies[i];
				}
			}

		}
		return null;
	}

	/**
	 * @方法名:
	 * @方法说明:根据cookie名称取值
	 * @修改记录:
	 * @日期:2016年2月4日
	 * @修改者:haiyang
	 * @修改内容:
	 * @author haiyang
	 * @version 1.0
	 * @since:
	 */
	public static String getCookieValueByName(HttpServletRequest request, String name) {
		Cookie cookie = getCookieByName(request, name);
		if (null != cookie) {
			return cookie.getValue();
		}
		return null;
	}

}
