package com.net.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.net.base.core.SpringContext;
import com.net.base.dao.BasicDao;


public class MyInvocationSecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {
	
	//@Autowired
	private BasicDao basicDao;
	
	/*ApplicationContext ctx=new ClassPathXmlApplicationContext("classpath:resources/applicationContext.xml"); 
	IcResourceDaoCustom icResourceDaoCustom= null;
	IcRoleResourceDaoCustom icRoleResourceDaoCustom= null;
	*/
	
///    private UrlMatcher urlMatcher = new AntUrlPathMatcher();
	//private AntPathRequestMatcher pathMatcher;

    private static Map<String,Collection<ConfigAttribute>> resourceMap =null;
	
	public MyInvocationSecurityMetadataSource(){
		/*icResourceDaoCustom = (IcResourceDaoCustom)ctx.getBean("icResourceDaoCustom"); 
		icRoleResourceDaoCustom = (IcRoleResourceDaoCustom)ctx.getBean("icRoleResourceDaoCustom"); 
		*/
		basicDao = (BasicDao)SpringContext.getBean("basicDao");
		
		loadResourceDefine();
	}
	
	/**
	 * 
	 * 看看loadResourceDefine方法，我在这里，假定index.jsp和i.jsp这两个资源，需要ROLE_ADMIN角色的用户才能访问。
      * 
	 * Date: Apr 13, 20133:53:49 PM
	 * @author by gongjinzhao
	 */
	private void loadResourceDefine(){
		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		List<Map> resourceAll = basicDao.queryForList("sc_menu_tree.queryData");
  
		for (Map icResource : resourceAll) {
			Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
			List<Map> roleList = basicDao.queryForList("sc_menu_tree.queryRoleMenuName",icResource);
			for (Map icRole : roleList) {
				ConfigAttribute ca = new SecurityConfig(icRole.get("role_name").toString());
				atts.add(ca);
			}
			if(null != icResource.get("href_target"))resourceMap.put(icResource.get("href_target").toString(), atts);
			
		}  
		
		
		/*Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
		ConfigAttribute ca = new SecurityConfig("ROLE_ANONYMOUS");
		atts.add(ca);
		resourceMap.put("/index.jsp", atts);*/
		
	}

	

	 /**
	  * 
	  * 这个类中，还有一个最核心的地方，就是提供某个资源对应的权限定义，即getAttributes方法返回的结果。注意，我例子中使用的是AntUrlPathMatcher这个path matcher来检查URL是否与资源定义匹配，事实上你还要用正则的方式来匹配，或者自己实现一个matcher。
      **/
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
	    String url = ((FilterInvocation)object).getRequestUrl();
	    HttpServletRequest request = ((FilterInvocation)object).getHttpRequest();
	    for (Map.Entry<String, Collection<ConfigAttribute>> rsmap : resourceMap.entrySet()) {
			String resURL = rsmap.getKey();
			//pathMatcher = new AntPathRequestMatcher(resURL);
			//if(pathMatcher.matches(request)){
			/*if(urlMatcher.pathMatchesUrl(resURL, url)){
				return resourceMap.get(resURL);
			}*/
			
		}
		return resourceMap.get(url);
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
			return null;
	}
	
	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
