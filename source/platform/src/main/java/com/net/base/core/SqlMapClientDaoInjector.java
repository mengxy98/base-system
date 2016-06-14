/**
 * 
 */
package com.net.base.core;

import java.util.Iterator;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.util.Assert;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 类说明: 向iBatis的DAO中注入依赖<br>
 * 创建时间: 2011-1-26 上午10:51:28<br>
 * 
 * @author 刘岩松<br>
 * @email: seraph115@gmail.com<br>
 */
public class SqlMapClientDaoInjector implements ApplicationContextAware,
		InitializingBean {

	private static final Logger logger = Logger
			.getLogger(SqlMapClientDaoInjector.class);

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContext.setApplicationContext(applicationContext);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(dataSource,
				"SqlMapClientDaoInjector's property 'dataSource' is required.");
		Assert.notNull(sqlMapClient,
				"SqlMapClientDaoInjector's property 'sqlMapClient' is required.");
		injectDependence();
	}

	private void injectDependence() {
		ApplicationContext ctx = SpringContext.getApplicationContext();
		Map<String, SqlMapClientDaoSupport> map = ctx.getBeansOfType(
				SqlMapClientDaoSupport.class, true, true);
		logger.debug("Inject dependency for SqlMapClientDaoSupport with [DataSource, SqlMapClient]...");
		for (Iterator<String> i = map.keySet().iterator(); i.hasNext();) {
			try {
				String supportName = (String) i.next();
				SqlMapClientDaoSupport support = map.get(supportName);
				support.setSqlMapClient(sqlMapClient);
				support.setDataSource(dataSource);

				logger.debug("Inject dependency for SqlMapClientDaoSupport is finshed, bean's name: ["
						+ supportName + "]");

			} catch (RuntimeException e) {
				logger.error("SqlMapClientDaoInjector.injectDependence()", e); //$NON-NLS-1$
			}
		}
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}

	private DataSource dataSource;

	private SqlMapClient sqlMapClient;

}
