package com.net.base.interceptors;

import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import com.net.base.service.log.SysLogDispatchManager;

/**
 * @Description:日志拦截器，用来记录操作日志
 * @Company:东方网信股份有限公司
 * @Date: 2016年1月28日
 * @Time: 下午3:56:04
 * @author gongjzh@net-east.com
 */
public class SyslogInterceptor {

	@Autowired
	private SysLogDispatchManager sysLogDispatchManager;


	/**
	 * @方法名:
	 * @方法说明:方法执行完毕
	 * @修改记录:
	 * @日期
	 * @修改者
	 * @修改内容
	 * @Date: 2016年1月28日下午3:56:37
	 * @author haiyang
	 * @version 1.0
	 * @since:
	 */
	public void doAfterReturning(JoinPoint jp, Object rtv) {
		sysLogDispatchManager.dispatchAfterReturning(jp);
	}

	/**
	 * @方法名:
	 * @方法说明:方法执行前
	 * @修改记录:
	 * @日期
	 * @修改者
	 * @修改内容
	 * @Date: 2016年1月28日下午6:13:50
	 * @author haiyang
	 * @version 1.0
	 * @since:
	 */
	public void doBeforeInvoke(JoinPoint jp) {

	}

	/**
	 * @方法名:
	 * @方法说明:环绕通知
	 * @修改记录:
	 * @日期:2016年2月20日
	 * @修改者:haiyang
	 * @修改内容:
	 * @author haiyang
	 * @version 1.0
	 * @since:
	 */
	public Object doAroundInvoke(JoinPoint jp) throws Throwable {
		//return commonPermissionDispatchManager.dispatchAround(jp);
		return null;
	}

}
