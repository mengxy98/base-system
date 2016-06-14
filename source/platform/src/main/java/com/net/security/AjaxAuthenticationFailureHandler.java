package com.net.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.ibatis.common.logging.Log;
import com.ibatis.common.logging.LogFactory;
import com.net.base.service.log.LoginLogManager;
import com.net.base.util.JsonUtils;
import com.net.base.util.ResponseUtils;
import com.net.security.entity.SysUser;

/**
 * 
 * Description:登录失败处理类
 * Company:东方网信技术股份有限公司
 * Date: 2016年5月24日
 * Time: 下午2:32:53
 * @author gongjzh@net-east.com
 */
public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {  
    protected final Log logger = LogFactory.getLog(getClass());  
  
    public AjaxAuthenticationFailureHandler() {  
    }  
  
    /**
     * 
     * 方法名：onAuthenticationFailure
     * 方法说明:失败处理
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     * <B>修改记录:</B><BR>
     * Date: 2016年5月24日下午2:36:29
     * @author gongjzh@net-east.com
     */
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,  
            AuthenticationException exception) throws IOException, ServletException {  
    	String userName = exception.getAuthentication().getPrincipal().toString();
    	String error = "用户名或密码错误！";//exception.getLocalizedMessage();

    	//添加登录失败日志
    	saveLoginLog(request,userName,  error);
    	
		Map<String, String> map = new HashMap<String, String>();
		map.put("error", error);
		LoginMessager messager = new LoginMessager();
		messager.setFailure(true);
		messager.setContents(map);

		JSONObject jsonObject = JSONObject.fromObject(messager);
		toMsg(response, jsonObject);
		
    } 
    
    private void toMsg(HttpServletResponse response,JSONObject jsonObject){
		PrintWriter out = null;
		try {
			ResponseUtils.setJsonHeader(response);
		    out = response.getWriter();

			out.print(JsonUtils.toJson(jsonObject));
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != out)
			  out.close();
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
		map.put("status", 0);
		
		//操作类型  1退出，登录0
		map.put("loginType", 0);

		map.put("reason", msgKey);

		new LoginLogManager().baseRequestMap(map, request);
	}

}
