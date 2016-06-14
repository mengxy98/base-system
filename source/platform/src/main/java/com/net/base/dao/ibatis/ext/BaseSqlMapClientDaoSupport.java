package com.net.base.dao.ibatis.ext;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;
import com.net.base.util.ReflectionUtil;

@Service
public abstract class BaseSqlMapClientDaoSupport extends SqlMapClientDaoSupport {

	@Autowired
	private SqlExecutor sqlExecutor;

	public SqlExecutor getSqlExecutor() {
		return sqlExecutor;
	}

	public void setSqlExecutor(SqlExecutor sqlExecutor) {
		this.sqlExecutor = sqlExecutor;
	}

	@PostConstruct
	public void initialize() throws Exception {
		if (sqlExecutor != null) {
			SqlMapClient sqlMapClient = getSqlMapClientTemplate()
					.getSqlMapClient();
			if (sqlMapClient instanceof ExtendedSqlMapClient) {
				ReflectionUtil.setFieldValue(
						((ExtendedSqlMapClient) sqlMapClient).getDelegate(),
						"sqlExecutor", SqlExecutor.class, sqlExecutor);
			}
		}
	}
}