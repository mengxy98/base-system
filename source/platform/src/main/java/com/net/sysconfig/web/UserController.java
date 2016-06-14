package com.net.sysconfig.web;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.net.base.annotation.FunType;
import com.net.base.annotation.LogComponent;
import com.net.base.annotation.LogMethod;
import com.net.base.dao.UserManagerDao;
import com.net.sysconfig.dao.UserMgrDao;
import com.net.sysconfig.service.CustomGenericManageableCaptchaService;
import com.net.sysconfig.service.UserMgrService;

/**
 * 
 * Description:个人用户信息管理
 * Company:东方网信技术股份有限公司
 * Date: 2016年5月25日
 * Time: 下午8:59:09
 * @author gongjzh@net-east.com
 */
@LogComponent(description="个人用户信息管理",funcType=FunType.PERSION_MANAGER)
@Controller
@RequestMapping("/userController")
public class UserController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserMgrService userMgrService;
	
	@Autowired
	private UserMgrDao userMgrDao;
	
	@Autowired
	private UserManagerDao userManagerDao;
	
	@Autowired
	private CustomGenericManageableCaptchaService customGenericManageableCaptchaService;  
  

    
    /**
     * 获取服务器时间
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/getServiceTime.do")
    public Date getServiceTime(HttpServletRequest request, HttpServletResponse response){
    	TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		return date;
    }
    
	/**
	 * 
	 * 方法名：查询个人用户信息管理
	 * 方法说明:getUserByName
	 * @param request
	 * @return
	 * <B>修改记录:</B><BR>
	 * Date: 2016年5月25日下午8:59:38
	 * @author gongjzh@net-east.com
	 */
	@LogMethod(description="查询个人用户信息",funcType=FunType.PERSION_MANAGER)
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getUserByName.do")
    @ResponseBody
    public Map getUserByName(HttpServletRequest request, HttpServletResponse response,String userName){
    	Map param = new HashMap();
    	param.put("userName", userName);
    	
    	List<Map> list = userManagerDao.getUserByName(param);
    	
    	Map returnMap = list.get(0);
    	
    	return returnMap;
    }
    
	
	/**
	 * 
	 * 方法名：updateUserInfo
	 * 方法说明:修改个人用户信息管理
	 * @param userName
	 * @param PHONE_NUM
	 * @param CELLPHONE_NUM
	 * @param EMAIL
	 * @return
	 * <B>修改记录:</B><BR>
	 * Date: 2016年5月25日下午9:02:14
	 * @author gongjzh@net-east.com
	 */
	@LogMethod(description="修改个人用户信息",funcType=FunType.PERSION_MANAGER)
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping("/updateUserInfo.do")
    @ResponseBody
	public Map updateUserInfo(HttpServletRequest request,String userName,String PHONE_NUM,String CELLPHONE_NUM,String EMAIL){
    	Map returnMap = new HashMap();
    	Map param = new HashMap();
    	
    	param.put("userName", userName);
    	param.put("PHONE_NUM", PHONE_NUM);
    	param.put("CELLPHONE_NUM", CELLPHONE_NUM);
    	param.put("EMAIL", EMAIL);
    	
    	try {
    		userManagerDao.updateUserInfo(param);
    		returnMap.put("msg", "success");
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("msg", "faild");
		}
    	
    	return returnMap;
    }
    
	/**
	 * 
	 * 方法名：updatePwd
	 * 方法说明:修改个人用户密码
	 * @param userName
	 * @param pwd
	 * @param newPwd
	 * @return
	 * <B>修改记录:</B><BR>
	 * Date: 2016年5月25日下午9:02:29
	 * @author gongjzh@net-east.com
	 */
	@LogMethod(description="修改个人用户密码",funcType=FunType.PERSION_MANAGER)
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping("/updatePwd.do")
    @ResponseBody
	public Map updatePwd(HttpServletRequest request,String userName,String pwd,String newPwd){
    	Map returnMap = new HashMap();
    	Map param = new HashMap();
    	
    	param.put("pwd", pwd);
    	param.put("newPwd", newPwd);
    	param.put("userName", userName);
    	
    	List<Map> user = userManagerDao.getUserByName(param);
    	if(pwd.equals(user.get(0).get("PASSWORD"))){
    		try {
    			userManagerDao.updatePwd(param);
    			returnMap.put("msg", "success");
			} catch (Exception e) {
				e.printStackTrace();
				returnMap.put("msg", "faild");
			}
    	}else{
    		returnMap.put("msg", "wrong pwd");
    	}
    	
    	return returnMap;
    }
    
    

	
}
