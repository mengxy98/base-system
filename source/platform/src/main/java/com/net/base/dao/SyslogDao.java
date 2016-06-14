package com.net.base.dao;

import java.util.Map;

/**
 * @Description:日志记录接口
 * @Company:东方网信股份有限公司
 * @Date: 2016年1月28日
 * @Time: 下午4:05:01
 * @author gongjzh@net-east.com
 */
public interface SyslogDao {

	/**
	 * @方法名:记录操作
	 * @方法说明:
	 * @修改记录:
	 * @日期
	 * @修改者
	 * @修改内容
	 * @Date: 2016年1月28日下午4:12:11
	 * @author gongjzh
	 * @version 1.0
	 * @since:
	 */
	void log(Map log);

}
