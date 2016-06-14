package com.net.base.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.net.base.dao.ScLoginLogDao;
import com.net.base.dao.ibatis.ext.BaseSqlMapClientDaoSupport;

/**
 * @Description:登录日志接口实现
 * @Company:东方网信股份有限公司
 * @Date: 2016年4月25日
 * @Time: 下午8:44:44
 * @author gongjzh@net-east.com
 */
@Repository
public class ScLoginLogDaoImpl extends BaseSqlMapClientDaoSupport implements ScLoginLogDao {

	@Override
	public void insert(Map map) {

		this.getSqlMapClientTemplate().insert("sc_login_log.insert", map);

	}

}
