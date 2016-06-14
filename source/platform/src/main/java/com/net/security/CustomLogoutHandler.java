package com.net.security;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.net.base.service.log.LoginLogManager;

/**
 * 
 * Description:登出成功处理类
 * Company:东方网信技术股份有限公司
 * Date: 2016年5月24日
 * Time: 下午8:16:32
 * @author gongjzh@net-east.com
 */
public class CustomLogoutHandler implements LogoutHandler  {
	public CustomLogoutHandler() { }
		 
	 @Override
	public void logout(HttpServletRequest request,
		            HttpServletResponse response, Authentication authentication) {
		   //System.out.println("CustomLogoutSuccessHandler.logout() is called!");
		 if(authentication != null && null != authentication.getPrincipal()){
		   saveLoginLog(request, ((User) authentication.getPrincipal()).getUsername(), "退出成功");
		 }
	 }
	 
		/**
		 * 
		 * 方法名：
		 * 方法说明:添加个人登录日志 
		 * @param request
		 * @param authResult
		 * @param user
		 * @param status
		 * @param msgKey
		 * <B>修改记录:</B><BR>
		 * Date: 2016年5月25日下午9:26:36
		 * @author gongjzh@net-east.com
		 */
		private void saveLoginLog(HttpServletRequest request,String userName,  String msgKey){
			
			Map map = new HashMap<String, String>();
			

			// 尝试登录的用户
			map.put("attemptLoginUser", userName);
			
			// 用户所属省份
			map.put("orgCode", "");

			// 登录成功1，失败0
			map.put("status", 1);
			
			//操作类型  1退出，登录0
			map.put("loginType", 1);

			map.put("reason", msgKey);

			new LoginLogManager().baseRequestMap(map, request);
		}
	  
}
