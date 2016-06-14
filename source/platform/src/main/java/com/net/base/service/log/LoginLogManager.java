package com.net.base.service.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tools.ant.util.DateUtils;

import com.net.base.core.SpringContext;
import com.net.base.dao.ScLoginLogDao;
import com.net.base.util.AddressUtil;
import com.net.base.util.CookieUtil;


public class LoginLogManager {
	
		
	private ScLoginLogDao scLoginLogDao;
	
	

	public ScLoginLogDao getScLoginLogDao() {
		return scLoginLogDao;
	}



	public void setScLoginLogDao(ScLoginLogDao scLoginLogDao) {
		this.scLoginLogDao = scLoginLogDao;
	}



	/**
	 * @方法名:
	 * @方法说明:获取基本的请求信息
	 * @修改记录:
	 * @日期:2016年4月25日
	 * @修改者:gongjzh
	 * @修改内容:
	 * @author gongjzh
	 * @version 1.0
	 * @since:
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public  void baseRequestMap(Map map, HttpServletRequest request) {
		if(scLoginLogDao == null){
		   scLoginLogDao = (ScLoginLogDao)SpringContext.getBean("scLoginLogDao");
		}
		map.put("method", request.getMethod());
		map.put("scheme", request.getScheme());
		map.put("contextPath", request.getContextPath());
		map.put("protocal", request.getProtocol());
		map.put("charactorEncoding", request.getCharacterEncoding());
		map.put("cookieName", CookieUtil.JSESSIONID);
		map.put("cookieValue", CookieUtil.getCookieValueByName(request, CookieUtil.JSESSIONID));
		map.put("requestURI", request.getRequestURI());
		map.put("createDate",
				DateUtils.format(new Date(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));

		map.put("remoteAddr", AddressUtil.getClientIp(request));
		// 如果nginx做代理，则获取正式的ip
		map.put("xRealIp", request.getHeader("X-Real-IP"));
		map.put("remoteHost", request.getRemoteHost());
		map.put("remotePort", request.getRemotePort());

		map.put("User-Agent", request.getHeader("User-Agent"));
		
		//scLoginLogDao.insert(map);
	}
}
