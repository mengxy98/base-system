package com.net.base.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.net.base.dao.SyslogDao;
import com.net.base.dao.ibatis.ext.BaseSqlMapClientDaoSupport;



/**
 * @Description:操作日志记录实现类
 * @Company:东方网信股份有限公司
 * @Date: 2016年1月28日
 * @Time: 下午4:06:30
 * @author gongjzh@net-east.com
 */
@Repository
public class SyslogDaoImpl extends BaseSqlMapClientDaoSupport implements SyslogDao {

	/**
	 * 方法名：记录实现
	 * 方法说明:
	 * @param log
	 * <B>修改记录:</B><BR>
	 * <PRE>
	 * 日期
	 * 修改者
	 * 修改内容
	 * <PRE>
	 * Date: 2016年1月28日下午4:12:25
	 * @author gongjzh
	 * @version 1.0
	 * @since:
	 */
	@Override
	public void log(Map log) {
		
		this.getSqlMapClientTemplate().insert("sc_log.log", log);
		
	}

}
