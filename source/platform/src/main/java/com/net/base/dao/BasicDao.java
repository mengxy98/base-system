package com.net.base.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.net.base.dao.ibatis.ext.BaseSqlMapClientDaoSupport;
import com.net.base.system.pageTag.Pagination;
@Component
public class BasicDao<T> extends BaseSqlMapClientDaoSupport {

	@SuppressWarnings("unchecked")
	public List<T> queryForList(String statementName, int offset, int limit) {
		return getSqlMapClientTemplate().queryForList(statementName, offset,
				limit);
	}
	@SuppressWarnings("unchecked")
	public List<T> queryForList(String statementName) {
       return getSqlMapClientTemplate().queryForList(statementName);
	
	}
	
	
	@SuppressWarnings("unchecked")
	public List<T> queryForList(String statementName, Map<String, ?> params,int offset, int limit) {
		return getSqlMapClientTemplate().queryForList(statementName, params,offset,
				limit);
	}

	@SuppressWarnings("unchecked")
	public <T> T queryForObject(String statementName, Object parameterObject) {
		return (T) getSqlMapClientTemplate().queryForObject(statementName,
				parameterObject);
	}
	@SuppressWarnings("unchecked")
	public <T> T queryForObject(String statementName) {
		return (T) getSqlMapClientTemplate().queryForObject(statementName
				);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> queryForList(String statementName, Object parameterObject) {
		return getSqlMapClientTemplate().queryForList(statementName,
				parameterObject);
	}

	public Pagination<T> getPage(String statementName, Integer page,
			int pageSize, Map<String, ?> params) {
		if (page == null) {
			page = 1;
		}

		int offset = (page - 1) * pageSize;
		List<T> list = this.queryForList(statementName,params, offset, pageSize);
		int count = (Integer) getSqlMapClientTemplate().queryForObject(
				statementName + "Count", params);
		return new Pagination<T>(page, pageSize, count, list);
	}

	public Pagination<T> getPage(String statementName, Integer page) {
		return this.getPage(statementName, page, Pagination.DEFAULT_PAGESIZE,
				new HashMap<String, Object>());
	}

	public Pagination<T> getPage(String statementName, Integer page,
			Map<String, ?> parms) {
		return this.getPage(statementName, page, Pagination.DEFAULT_PAGESIZE,
				parms);
	}

	public <T> Object insert(String statementName, T obj) {
	    return getSqlMapClientTemplate().insert(statementName, obj);
	}

	public <T> int delete(String statementName, T obj) {
		return getSqlMapClientTemplate().delete(statementName, obj);
	}

	public <T> int update(String statementName, T obj) {
		return getSqlMapClientTemplate().update(statementName, obj);
	}

	public <T> T show(String statementName, T obj) {
		return (T) getSqlMapClientTemplate().queryForObject(statementName, obj);
	}

}
