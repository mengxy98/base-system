package com.net.base.interceptor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Interceptor拦截器
 * 
 * @author zll
 *
 */
public class ControllerInterceptor implements HandlerInterceptor {
	Logger log = Logger.getLogger(ControllerInterceptor.class);
	Date startTime = null;
	Date endTime = null;
	Object controller = null;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			startTime = Calendar.getInstance().getTime();
			MethodParameter[] arr = handlerMethod.getMethodParameters();
			String parameterString = "";
			for (int i = 0; i < arr.length; i++) {
				parameterString += arr[i].getParameterName() + " ";
			}
			controller = handlerMethod.getBean().getClass();
			log.info("当前请求Controller为:" + controller
					+ ",执行方法为:" + handlerMethod.getMethod().getName()
					+ ",方法定义的请求参数为:"
					+ (parameterString.length() > 0 ? parameterString : "null"));
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			endTime = Calendar.getInstance().getTime();
			log.info("Controller开始时间为:"
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTime)
					+ "   结束时间为:"
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTime)
					+ "   共耗时" + (endTime.getTime() - startTime.getTime()) + "毫秒");
		}
	}

}
