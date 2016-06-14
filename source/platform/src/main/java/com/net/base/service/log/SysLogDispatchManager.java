package com.net.base.service.log;

import org.aspectj.lang.JoinPoint;

/**
 * @Description:操作日志记录分发接口
 * @Company:东方网信股份有限公司
 * @Date: 2016年1月28日
 * @Time: 下午4:08:57
 * @author gongjzh@net-east.com
 */
public interface SysLogDispatchManager {

	/**
	 * 
	 * 方法名：dispatchAfterReturning
	 * 方法说明:操作后分发处理
	 * @param jp
	 * <B>修改记录:</B><BR>
	 * Date: 2016年4月28日上午10:09:19
	 * @author gongjzh@net-east.com
	 */
	void dispatchAfterReturning(JoinPoint jp);

}
