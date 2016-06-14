/**
 * 
 */
package com.net.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.web.filter.OncePerRequestFilter;

import com.net.base.core.SpringContext;
import com.net.base.service.log.LoginLogManager;
import com.net.base.util.JsonUtils;
import com.net.base.util.ResponseUtils;
import com.net.sysconfig.service.CustomGenericManageableCaptchaService;



/**
 * 
 * Description:Ajax的权限过滤器
 * Company:东方网信技术股份有限公司
 * Date: 2016年5月6日
 * Time: 上午9:17:58
 * @author gongjzh@net-east.com
 */
public class AcegiAjaxFilter extends OncePerRequestFilter {
	


	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		// 验证是否成功
		if(!getMsg(response, request)){
			return;
		}
        
		// 是否是ajax请求
		if (!isAjaxRequest(request)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		
		RedirectResponseWrapper wrapper = new RedirectResponseWrapper(response);
		
		filterChain.doFilter(request, wrapper);
		
		
	
	}

	private boolean isAjaxRequest(HttpServletRequest request) {
		return request.getParameter("ajax") != null;
	}
	
	/**
	 * 
	 * 方法名：
	 * 方法说明:验证验证码
	 * @param response
	 * @param request
	 * <B>修改记录:</B><BR>
	 * Date: 2016年5月6日下午1:23:08
	 * @author gongjzh@net-east.com
	 */
	private boolean getMsg(HttpServletResponse response,HttpServletRequest request ){
		JSONObject jsonObject = null;
		// 获取验证码验证bean
	    CustomGenericManageableCaptchaService captchaService= (CustomGenericManageableCaptchaService) SpringContext.getBean("customGenericManageableCaptchaService");

		// 获取传入的验证码值
		String capatcha = request.getParameter("capatcha");
		// 验证是否成功
		boolean isCheck = captchaService.validateResponseForID(request.getSession().getId(),capatcha);
	    if(!isCheck){
	    	String errorMsg = "验证码错误";
	    	saveLoginLog(request, errorMsg);
	    	Map<String, String> map = new HashMap<String, String>();
			map.put("error", errorMsg);

			LoginMessager messager = new LoginMessager();
			messager.setFailure(true);
			messager.setContents(map);

			jsonObject = JSONObject.fromObject(messager);
			toMsg(response, jsonObject);
	    	return false;
	    }else{
	    	captchaService.removeCaptcha(request.getSession().getId());
	    }
	    return true;
	    
       

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
	private void saveLoginLog(HttpServletRequest request, String msgKey){
		
		Map map = new HashMap<String, String>();
		

		// 尝试登录的用户
		map.put("attemptLoginUser", "");
		
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
