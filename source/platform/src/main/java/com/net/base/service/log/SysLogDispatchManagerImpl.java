package com.net.base.service.log;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.tools.ant.util.DateUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;




import com.net.base.annotation.LogAction;
import com.net.base.annotation.LogComponent;
import com.net.base.annotation.LogMethod;
import com.net.base.dao.SyslogDao;
import com.net.base.util.AddressUtil;
import com.net.base.util.CookieUtil;


/**
 * @Description:操作日志分发实现
 * @Company:东方网信股份有限公司
 * @Date: 2016年1月28日
 * @Time: 下午4:10:05
 * @author gongjzh@net-east.com
 */
@Repository
public class SysLogDispatchManagerImpl implements SysLogDispatchManager {

	@Autowired
	private SyslogDao syslogDao;


	
	

	/**
	 * 
	 * 方法名：dispatchAfterReturning
	 * 方法说明:执行方法并且返回值后
	 * @param jp
	 * <B>修改记录:</B><BR>
	 * Date: 2016年4月28日上午9:49:18
	 * @author gongjzh@net-east.com
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void dispatchAfterReturning(JoinPoint jp) {
		MethodInvocationProceedingJoinPoint jpThiz = (MethodInvocationProceedingJoinPoint) jp;
		// 拦截目标类
		Class targetClass = jpThiz.getTarget().getClass();
		
     
		if (targetClass.isAnnotationPresent(LogComponent.class) || targetClass.isAnnotationPresent(LogAction.class) ) {
			Signature signature = jpThiz.getSignature();
			// 类名称
			//String className = targetClass.getSimpleName();
			// 方法名称
			//String methodName = signature.getName();
			
			
			//判断是否对方法注释，优先取方法注解，否则取类注解
		    LogMethod logMethod = getLogMethod(jpThiz);
		    if(null != logMethod){//方法注解忽略
				if(logMethod.isIgnore())return; 
		    }
		  
		    getNomalFunLog(jpThiz, logMethod);
		   
		
		}
	}
	
	
	/**
	 * 
	 * 方法名：getNomalFunLog
	 * 方法说明:  拦截目标类(针对一般类)
	 * @param jpThiz
	 * @param logMethod
	 * <B>修改记录:</B><BR>
	 * Date: 2016年4月29日下午4:21:27
	 * @author gongjzh@net-east.com
	 */
	private void getNomalFunLog(MethodInvocationProceedingJoinPoint jpThiz,LogMethod logMethod){
		// 拦截目标类
		Class targetClass = jpThiz.getTarget().getClass();
		
		Object[] args = jpThiz.getArgs();
		HttpServletRequest request = (HttpServletRequest) args[0];
		
		String description = "";
		String funcType = "";
		if(null != logMethod){//优先取方法注解
			description = logMethod.description();
			funcType = logMethod.funcType();
		}else {//否则取类注解
		
				LogComponent logComponent = (LogComponent) targetClass.getAnnotation(LogComponent.class);
				description = logComponent.description();
				funcType = logComponent.funcType();
		}	
		if("".equals(description.trim()))return;//无注解则退出
		
		Map map = new HashMap<String, String>();
		map.put("operation", description);
		map.put("funcType", funcType);
		initSysLogData(jpThiz, request, map);
	}

	
	
	
	/**
	 * 
	 * 方法名：getLogMethod
	 * 方法说明:获取方法注解类
	 * @param jp
	 * @return
	 * <B>修改记录:</B><BR>
	 * Date: 2016年4月28日下午4:59:07
	 * @author gongjzh@net-east.com
	 */
	private LogMethod getLogMethod(MethodInvocationProceedingJoinPoint jp){
		Object[] arguments = jp.getArgs();
        Method[] method = jp.getTarget().getClass().getMethods();  
       // 方法名称
        String methodName = jp.getSignature().getName();
     			
       LogMethod methodCache = null; 
        for (Method m : method) {  
            if (m.getName().equals(methodName)) {  
                Class[] tmpCs = m.getParameterTypes();  
                if (tmpCs.length == arguments.length) {  
                	 methodCache = m.getAnnotation(LogMethod.class); 
                	 if(methodCache != null){
                		 break;
                	 } 
                		  
                }  
            }  
        }
        
        return methodCache;
	}
	
	

	
	/**
	 * 
	 * 方法名：initSysLogData
	 * 方法说明:封装日志数据实体类
	 * @param jpThiz
	 * @param request
	 * @param map
	 * <B>修改记录:</B><BR>
	 * Date: 2016年4月28日下午4:52:17
	 * @author gongjzh@net-east.com
	 */
	private void initSysLogData(MethodInvocationProceedingJoinPoint jpThiz, HttpServletRequest request, Map map){
		 try {
			// 拦截目标类
			Class targetClass = jpThiz.getTarget().getClass();
			
			Signature signature = jpThiz.getSignature();
			// 类名称
			String className = targetClass.getSimpleName();
			// 方法名称
			String methodName = signature.getName();
			String queryString = request.getQueryString();
			if(queryString != null && queryString.length() > 2000){//限制长度
				queryString = queryString.substring(0,2000);
			}
			map.put("className", className);
			map.put("methodName", methodName);
			map.put("method", request.getMethod());
			map.put("scheme", request.getScheme());
			map.put("contextPath", request.getContextPath());
			map.put("protocal", request.getProtocol());
			map.put("queryString", queryString);
			map.put("charactorEncoding", request.getCharacterEncoding());
			map.put("cookieName", CookieUtil.JSESSIONID);
			map.put("cookieValue", CookieUtil.getCookieValueByName(request, CookieUtil.JSESSIONID));
			map.put("requestURI", request.getRequestURI());
			map.put("createDate", DateUtils.format(new Date(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
			map.put("remoteAddr", AddressUtil.getClientIp(request));
			// 如果nginx做代理，则获取正式的ip
			map.put("xRealIp", request.getHeader("X-Real-IP"));
			map.put("remoteHost", request.getRemoteHost());
			map.put("remotePort", request.getRemotePort());
	
			// 客户端标识
			map.put("User-Agent", request.getHeader("User-Agent"));
	
			// 获取登录的用户信息
			//BaseUser user = LoginUtil.getUser();
			//if (null != user) {
			//	map.put("loginUser", user.getUserName());
			//	map.put("orgCode", user.getOrgCode());
			//} else {
				map.put("loginUser", "");
				map.put("orgCode", "");
			//}
		    
			//多线程处理 20个
			
			ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20); 
			fixedThreadPool.execute(new LogThread(syslogDao, map));
			
			//syslogDao.log(map);
	    } catch (Exception e) {
			e.printStackTrace();
		}
	}


}

