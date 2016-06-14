package com.net.security;



import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.net.base.dao.UserManagerDao;
import com.net.base.service.log.LoginLogManager;
import com.net.base.util.DateUtil;
import com.net.base.util.JsonUtils;
import com.net.base.util.ResponseUtils;
import com.net.security.entity.SysUser;


/**
 * @Description:用户验证授权回调
 * @Company:东方网信股份有限公司
 * @Date: 2016年4月25日
 * @Time: 下午8:39:05
 * @author gongjzh@net-east.com
 */
 public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {  
	 
    @Autowired
	UserManagerDao managerDao;
	 
	private String redirectURL = "/main/main.jsp";
  
    public AjaxAuthenticationSuccessHandler() {}  
  
    /**
     * 
     * 方法名：
     * 方法说明:
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     * <B>修改记录:</B><BR>
     * Date: 2016年5月24日下午2:42:07
     * @author gongjzh@net-east.com
     */
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,  
            Authentication authentication) throws IOException, ServletException {	
    	//验证用户账号是否过期
    	String userName = ((User)authentication.getPrincipal()).getUsername();
    	SysUser user = setSysUser(userName);
    	if(null == user)return;
    	//验证用户信息
    	if(!isValidDate(request,response,authentication, user))return;
    	//保存用户登录信息
    	saveUserSession(user, request);
    	//添加登录日志
    	saveLoginLog(request, authentication, user, 1, "");
    	
        Map<String, String> map = new HashMap<String, String>();
		map.put("targetUrl", request.getContextPath()+redirectURL);

		LoginMessager messager = new LoginMessager();
		messager.setSuccess(true);
		messager.setContents(map);
		

		toMsg(response, messager);
		
	
    } 
    
    
	/**
	 * 
	 * 方法名：
	 * 方法说明:验证用户账号是否过期
	 * @param response	
	 * @param request
	 * <B>修改记录:</B><BR>
	 * Date: 2016年5月6日下午1:26:25
	 * @author gongjzh@net-east.com
	 */
	private boolean isValidDate(HttpServletRequest request, HttpServletResponse response,Authentication authResult,SysUser user){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date paseDate = null;
        String msgKey = "";
		
		try {
			paseDate = DateUtil.parseDate(sdf.format(date));
			if(DateUtil.parseDate(user.getStart_date()).after(paseDate)){
				msgKey =  "该用户账号未开启";
			}else if(DateUtil.parseDate(user.getEnd_date()).before(paseDate)){
				msgKey =  "该用户账号已过期";
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
		if(msgKey.length() > 0 ){
			//添加登录日志
	    	saveLoginLog(request, authResult, user, 0, "");
			SecurityContextHolder.clearContext();
			SecurityContextHolder.getContext().setAuthentication(null);
			
		    Map<String, String> map = new HashMap<String, String>();
			map.put("error", msgKey);

			LoginMessager messager = new LoginMessager();
			messager.setFailure(true);
			messager.setContents(map);

			
			toMsg(response, messager);
			return false;
	    	
	    }
		return true;

	}

    
    
	private void toMsg(HttpServletResponse response,LoginMessager messager){
		PrintWriter out = null;
		try {
			JSONObject  jsonObject = JSONObject.fromObject(messager);
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
     * 方法名：setSysUser
     * 方法说明:user login for save user information
     * @param sessionMap
     * @param session
     * Date: 2016年3月7日上午11:23:09
     * @author lijia
     */
	private SysUser setSysUser(String userName){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userName);
		List<Map> sessionMap = managerDao.findRoleAndOperaType(params);
		if(null == sessionMap || sessionMap.size() <= 0)return null;
		
    	SysUser user = new SysUser();
		user.setUserId(sessionMap.get(0).get("userId").toString());
		user.setUserName(sessionMap.get(0).get("userName").toString());
		user.setOrgCode(sessionMap.get(0).get("orgCode").toString());
		user.setAuthorityType(sessionMap.get(0).get("operateType").toString());
		user.setRoleName(sessionMap.get(0).get("roleName").toString());
		user.setStart_date(sessionMap.get(0).get("userStartDate").toString());
		user.setEnd_date(sessionMap.get(0).get("userEndDate").toString());
		user.setRoleStartDate(sessionMap.get(0).get("roleStartDate").toString());
		user.setRoleEndDate(sessionMap.get(0).get("roleEndDate").toString());
		user.setDefaultPage(sessionMap.get(0).get("defaultPage").toString());
		
		return user;
    }
	
	/**
	 * 
	 * 方法名：
	 * 方法说明:保存用户登录信息
	 * @param user
	 * @param request
	 * <B>修改记录:</B><BR>
	 * Date: 2016年5月24日下午7:35:43
	 * @author gongjzh@net-east.com
	 */
	private void saveUserSession(SysUser user, HttpServletRequest request){
		request.getSession().setAttribute("user", user);
    	request.getSession().setAttribute("userName", user.getUserName());
    	request.getSession().setAttribute("userId", user.getUserId());
    	
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
	private void saveLoginLog(HttpServletRequest request,Authentication authResult,SysUser user, Integer status, String msgKey){
		
		Map map = new HashMap<String, String>();
		

		// 尝试登录的用户
		map.put("attemptLoginUser", ((User) authResult.getPrincipal()).getUsername());
		
		// 用户所属省份
		map.put("orgCode", user == null?"":user.getOrgCode());

		// 登录成功1，失败0
		map.put("status", status);
		
		//操作类型  1退出，登录0
		map.put("loginType", 0);

		map.put("reason", msgKey);

		new LoginLogManager().baseRequestMap(map, request);
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}
	
	
	
} 


