package com.net.base.web;


import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.net.base.service.cache.CacheDataService;


@RequestMapping(value="/cache")
@Controller
public class CacheDataController {
	
	@Autowired
	private CacheDataService CacheDataService;
	
	@RequestMapping(value="/getDevData.do",method=RequestMethod.GET)
	public String getCacheData(int Tid){
		if (Tid < 0) return "";
		String mess = CacheDataService.getTaskAllDeviceData(Tid);
		if(Tid==69){
			inputCacheData(70,"20.5,3.4,0,2016/3/30 14:34:23,RTKfixed,2.32,137.301,3219200.01709464,634607.41813379");
		}
		return transformData(mess);
	}
	
	@RequestMapping(value="/setDevData.do",method=RequestMethod.POST)
	public boolean inputCacheData(int Did,String dataList){
		return CacheDataService.setDevData(Did, dataList);
	}
	/**
	 * @return 页面返回数据字符转码
	 */
	public String transformData(String mess) {
		try {
			return new String(mess.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			return mess;
	    }
	}
}
