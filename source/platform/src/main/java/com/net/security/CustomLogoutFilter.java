package com.net.security;

import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * 
 * Description:退出过滤器,相关数据记录清除器
 * Company:东方网信技术股份有限公司
 * Date: 2016年5月24日
 * Time: 下午8:17:49
 * @author gongjzh@net-east.com
 */
public class CustomLogoutFilter extends LogoutFilter  {

	 public CustomLogoutFilter(String logoutSuccessUrl, LogoutHandler[] handlers) {
		        super(logoutSuccessUrl, handlers);
	 }
	
	public CustomLogoutFilter(LogoutSuccessHandler logoutSuccessHandler,
			LogoutHandler[] handlers) {
		super(logoutSuccessHandler, handlers);
		
	}	

}
