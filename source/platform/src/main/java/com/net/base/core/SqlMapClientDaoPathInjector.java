package com.net.base.core;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.util.Assert;

/**
 * 类说明: 向iBatis的DAO中注入依赖，通过path进行选择<br>
 * 创建时间: 2011-1-26 上午10:51:28<br>
 * 
 * @author 刘岩松<br>
 * @email: seraph115@gmail.com<br>
 */
public class SqlMapClientDaoPathInjector implements ApplicationContextAware, InitializingBean {

	private static final Logger logger = Logger.getLogger(SqlMapClientDaoPathInjector.class);
	
	 private Map<String, SqlMapClientDAOContext> mapPath;
	 
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContext.setApplicationContext(applicationContext);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.mapPath, "SqlMapClientDaoInjector's mapingPath 'SqlMapClientDaoPathInjector' is required.");
		injectDependence();
	}
	
	@SuppressWarnings("rawtypes")
	private void injectDependence() throws Exception{
		/*ApplicationContext ctx = SpringContext.getApplicationContext();
		Map<String, SqlMapClientDaoSupport> map = ctx.getBeansOfType(SqlMapClientDaoSupport.class, true, true);
		logger.debug("Inject dependency for SqlMapClientDaoPathInjector with [sqlMapClientDaoSupport]...");
		for (Iterator<String> i = map.keySet().iterator(); i.hasNext();) {
			try {
				String supportName = (String) i.next();
				SqlMapClientDaoSupport support = map.get(supportName);
				SqlMapClientDaoSupport target = findMatch(support);
				support.setSqlMapClient(target.getSqlMapClient());
				support.setDataSource(target.getDataSource());
				
				logger.debug("Inject dependency for SqlMapClientDaoPathSupport is finshed, bean's name: [" + supportName + "]");
				
			} catch (RuntimeException e) {
				logger.error("SqlMapClientDaoInjector.injectDependence()", e); //$NON-NLS-1$
			}
		}*/
		
		ApplicationContext ctx = SpringContext.getApplicationContext();
		
	    Map map = ctx.getBeansOfType(SqlMapClientDaoSupport.class);
	    logger.debug("Inject dependency for SqlMapClientDaoPathInjector with [sqlMapClientDaoSupport]...");
	    for (Iterator i = map.keySet().iterator(); i.hasNext(); )
	      try {
	        String supportName = (String)i.next();
	        SqlMapClientDaoSupport support = (SqlMapClientDaoSupport)getRealTarget((SqlMapClientDaoSupport)map.get(supportName));
	        SqlMapClientDAOContext target = findMatch(support);

	        if (target == null) {
	          logger.error("类:" + support.getClass().getName() + "; 未找到指定的DATASOURCE，使用默认值进行注入");
	        }
	        else
	        {
	          support.setSqlMapClient(target.getSqlMapClient());
	          support.setDataSource(target.getDataSource());

	          logger.debug("Inject dependency for SqlMapClientDaoPathSupport is finshed, bean's name: [" + supportName + "]");
	        }
	      } catch (RuntimeException e) {
	        logger.error("SqlMapClientDaoInjector.injectDependence()", e);
	      }
		
	}
	
	/*private SqlMapClientDaoSupport findMatch(SqlMapClientDaoSupport support){
		return mapPath.get(support.getClass().getPackage().getName());
	}

	private  Map<String,SqlMapClientDaoSupport> mapPath;

	public Map<String, SqlMapClientDaoSupport> getMapPath() {
		return mapPath;
	}

	public void setMapPath(Map<String, SqlMapClientDaoSupport> mapPath) {
		this.mapPath = mapPath;
	}*/
	
	  @SuppressWarnings("unchecked")
	private <T> T getRealTarget(T t) throws Exception
	  {
	    if (!AopUtils.isAopProxy(t)) {
	      return t;
	    }
	    Object object = ((Advised)t).getTargetSource().getTarget();
	    return (T)object;
	  }

	  @SuppressWarnings("rawtypes")
	private SqlMapClientDAOContext findMatch(SqlMapClientDaoSupport support)
	  {
	    for (Map.Entry entry : this.mapPath.entrySet()) {
	      String name = support.getClass().getPackage().getName();
	      if (((String)entry.getKey()).contains(name))
	        return (SqlMapClientDAOContext)entry.getValue();
	    }
	    return null;
	  }

	  public Map<String, SqlMapClientDAOContext> getMapPath()
	  {
	    return this.mapPath;
	  }

	  public void setMapPath(Map<String, SqlMapClientDAOContext> mapPath) {
	    this.mapPath = mapPath;
	  }

}
