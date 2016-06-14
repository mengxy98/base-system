package com.net.base.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.net.base.dao.BasicDao;
import com.net.base.util.LoggerInfo;


/**
 * 
 * Description:定时扫描统管注册信息 Company:net-east Date: 20160326 12:11:14 PM
 * 
 * @author gongjzh@net-east.com
 */
@Service
public class SysHandleService {
	
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");

	private static long startNao = 0l;

	private static long endNao = 0l;

	@Resource
	BasicDao basicDao;


	/**
	 * 定时入口 每10分钟操作一次 方法名：doPoint 
	 * 
	 * @param
	 * @return Date: 20160326 12:11:14 PM
	 * @author by gongjzh@net-east.com
	 */
	public void doPoint() {
		try {
			countRunTime("search  content begin", "start");
		
		     //内容
			
			countRunTime("search  content end", "end");

		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	
	


	/**
	 * 计算运行时间
	 * 
	 * @param message
	 * @param type
	 */
	public void countRunTime(String message, String type) {
		if (type.equals("start")) {
			LoggerInfo.getInfoLogger(
					message + "[Begin][" + sdf.format(new Date()) + "]", 2);
			startNao = System.nanoTime();
			System.out.println(message + "[Begin][" + sdf.format(new Date())
					+ "]");
		} else if (type.equals("end")) {
			endNao = System.nanoTime();
			LoggerInfo.getInfoLogger(
					message + "[Over][" + sdf.format(new Date()) + "]", 2);
			LoggerInfo.getInfoLogger(
					"run_time(s):"
							+ (Double.valueOf(endNao - startNao) / 1000000000),
					2);
			System.out.println(message + "[Over][" + sdf.format(new Date())
					+ "]");
		}

	}
	


	

}
