package com.net.base.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.web.context.ServletContextAware;


public class LocalResourcesInitial extends JdbcDaoSupport implements
		ServletContextAware, ApplicationContextAware {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private ServletContext servletContext;
	
	public ServletContext getServletContext() {
		return servletContext;
	}

	public void initialize() {
		setSystemParams();
	}

	private void setSystemParams() {

		String sql = "SELECT T2.TYPE AS paramcode, CAST(GROUP_CONCAT(CONCAT(T1.ID,':',T1.`NAME`)) as CHAR)AS paramvalue "
				+ " FROM SC_DIC T1  JOIN SC_DIC_TYPE T2 ON T1.TYPEID=T2.ID GROUP BY T2.TYPE";
		
		final Object[] params = new Object[] {};
		final Map<String,Object> resultMap = new HashMap<String,Object>();

		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		try {
			jdbcTemplate.query(sql, params, new RowCallbackHandler() {
				public void processRow(ResultSet rs) {
					String key;
					String value;
					try {
						key = rs.getString("paramcode");
						value = rs.getString("paramvalue");
						resultMap.put(key, value);
					} catch (SQLException e) {
						logger.error(e.getMessage());
					}
				}
			});
		} catch (Exception e) {
			logger.debug("Table not found");
		}
		
		servletContext.setAttribute("PUBLIC_PARAMS", resultMap);
		
	}

	public void rebuild() {
		setSystemParams();
	}
	
	public void rebuildByTableName(String tableName) {
		setSystemParams();
	}

	public void rebuild(List<String> entityNames) {

	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext=servletContext;
	}

	


}

