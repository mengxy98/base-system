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

/**
 * Description:缓存数据接口
 * Company:www.yumi.com
 * Date: 2016年7月20日
 * Time: 下午4:00:36
 * @author m541206807@163.com
 */
@RequestMapping(value="/cache")
@Controller
public class CacheDataController {
	
	@Autowired
	private CacheDataService CacheDataService;
	
	@Autowired
	DeviceManagerDao deviceManagerDao;
	
	/**
	 * 方法说明:获取设备数据的接口
	 * @param deviceId
	 * @return
	 * Date: 2016年7月20日下午4:01:04
	 * @author mengxy
	 * @version 1.0
	 * @since:
	 */
	@RequestMapping(value="/getDevData.do",method=RequestMethod.GET)
	@ResponseBody
	public String getCacheData(int deviceId){
		if (deviceId < 0) return "";
		String mess = CacheDataService.getDeviceData(deviceId);
		return mess;
	}
	
	/**
	 * 方法说明:设备插入定位数据和表层数据,是对外开放的接口，不用登陆就可以使用的
	 * @param deviceId
	 * @param dataList
	 * @return
	 * Date: 2016年7月20日下午4:05:08
	 * @author mengxy
	 * @version 1.0
	 * @since:
	 */
	@RequestMapping(value="/setDevData.do",method=RequestMethod.POST)
	@ResponseBody
	public boolean inputCacheData(int deviceId,String dataList){
		if(deviceId < 0)return false;
		if (dataList.endsWith(";")) {
			dataList = dataList.substring(0,dataList.length()-1);
		}
		return CacheDataService.setDevDataNew(deviceId, dataList);
	}
	@RequestMapping(value="/setDevDataOld.do",method=RequestMethod.POST)
	@ResponseBody
	public boolean setDevDataOld(int deviceId,String dataList){
		if(deviceId < 0)return false;
		if (dataList.endsWith(";")) {
			dataList = dataList.substring(0,dataList.length()-1);
		}
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
	 * 根据设备IP获取设备ID。	
	 */
	@RequestMapping(value="/getDeviceIP.do",method=RequestMethod.POST)
	@ResponseBody
	public String getDeviceId(String deviceIp){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deviceIp", deviceIp);
		List<Map<String,Object>> list = deviceManagerDao.findDeviceManagermentListDao(map);
		if(null != list && list.size() > 0){
			Object objId = list.get(0).get("id");
			if (objId != null) {
				return objId.toString();
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
	
	
	/**
	 * 根据设备ip获取任务信息。
	 */
	@RequestMapping(value="/getTaskInfo.do",method=RequestMethod.GET)
	@ResponseBody
	public String getTaskInfo(String deviceIP){
		if(deviceIP==null || deviceIP.length()==0)return "";
		return "";
	}
}
