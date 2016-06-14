package com.net.base.service.log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.net.base.dao.SyslogDao;

/**
 * 
 * Description:操作日志线程处理类
 * Company:东方网信技术股份有限公司
 * Date: 2016年5月16日
 * Time: 下午4:49:38
 * @author gongjzh@net-east.com
 */
public class LogThread implements  Runnable {

	
	private SyslogDao syslogDao;
	
	private List<Map> initList = new ArrayList<Map>();
	
	
	public LogThread(SyslogDao syslogDao, Map map){
		this.syslogDao = syslogDao;
		initList.add(map);
	}
	
	public void run() {
		
			try {
				List<Map> operList = getNewRunData();
				for (Map map : operList) {
					syslogDao.log(map);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		
	}
	
	/**
	 * 
	 * 方法名：reIntList
	 * 方法说明:防止在list进行转移完后，进行clear同时，有新的加入，导致日志丢失
	 * <B>修改记录:</B><BR>
	 * Date: 2016年5月16日下午5:50:17
	 * @author gongjzh@net-east.com
	 */
	public List<Map> getNewRunData(){
		List<Map> operList = new ArrayList<Map>();
		operList.clear();
		operList.addAll(initList);
		initList.removeAll(operList);
		return operList;
	}
	
}