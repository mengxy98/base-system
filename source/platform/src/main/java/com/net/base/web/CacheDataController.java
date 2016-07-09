package com.net.base.web;


import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.net.base.dao.DeviceManagerDao;
import com.net.base.service.cache.CacheDataService;


@RequestMapping(value="/cache")
@Controller
public class CacheDataController {
	
	@Autowired
	private CacheDataService CacheDataService;
	
	@Autowired
	DeviceManagerDao deviceManagerDao;
	
	@RequestMapping(value="/getDevData.do",method=RequestMethod.GET)
	@ResponseBody
	public String getCacheData(int deviceId){
		if (deviceId < 0) return "";
		String mess = CacheDataService.getDeviceData(deviceId);
		return mess;
	}
	
	@RequestMapping(value="/setDevData.do",method=RequestMethod.POST)
	@ResponseBody
	public boolean inputCacheData(int deviceId,String dataList){
		return CacheDataService.setDevDataNew(deviceId, dataList);
	}
	
	@RequestMapping(value="/setDevDataEmp.do",method=RequestMethod.POST)
	@ResponseBody
	public boolean setDevDataEmp(int deviceId,String dataList){
		return CacheDataService.setDevDataNew(deviceId, dataList);
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
	
	
	/**
	 * 插入数据库的接口开发
	 */
	/*@RequestMapping(value="/setDevData.do",method=RequestMethod.POST)
	@ResponseBody
	public boolean inputCacheData(int Did,String dataList){
		return CacheDataService.setDevData(Did, dataList);
	}*/
	
	
	/**
	 * 根据设备IP获取设备ID。	
	 */
	@RequestMapping(value="/getDeviceIp.do",method=RequestMethod.POST)
	@ResponseBody
	public String getDeviceId(String deviceIp){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deviceIp", deviceIp);
		List<Map<String,Object>> list = deviceManagerDao.findDeviceManagermentListDao(map);
		if(null != list && list.size() > 0){
			Object objIp = list.get(0).get("ip");
			if (objIp != null) {
				return objIp.toString();
			}
		}
		return "";
	}
	
	
	/**
	 * 获取所有设备信息。
	 */
	@RequestMapping(value="/getAllDeviceInfo.do",method=RequestMethod.GET)
	@ResponseBody
	public List<Map<String,Object>> getAllDeviceInfo(){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String,Object>> list = deviceManagerDao.findDeviceManagermentListDao(map);
		return list;
	}
}
