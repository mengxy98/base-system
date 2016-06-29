package com.net.base.core;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.ServletContextAware;

/**
 * Description:
 * Company:XX.com
 * Date: 2016年5月28日
 * Time: 上午10:44:27
 * @author 914962981@qq.com
 */
@Repository
public class LocalResourcesManagerImpl implements LocalResourcesManager,
		ServletContextAware, ApplicationContextAware {

	private ServletContext servletContext;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContext.setApplicationContext(applicationContext);
	}

	@SuppressWarnings("unchecked")
	public Map<String,Object> getCodeInfoMapByType(String codeType) {
		Map<String,Object> map =  (Map<String,Object>)servletContext.getAttribute(codeType);
		return map;
	}

	@Override
	public Map<String, Object> getCodeInfoMapByCode(String codeType,String[] code) {
		Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
		if (code == null || code.length == 0) return returnMap;
		Map<String,Object> map =  getCodeInfoMapByType(codeType);
		for (int i = 0; i < code.length; i++) {
			String valueString = (String) map.get(code[i]);
			String[] value = StringUtils.split(valueString,",");
			for (String emp : value) {
				String[] val = StringUtils.split(emp,":");
				returnMap.put(val[0], val[1]);
			}
		}
		return returnMap;
	}

	
}
