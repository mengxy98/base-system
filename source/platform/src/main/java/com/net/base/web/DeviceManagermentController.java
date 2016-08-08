package com.net.base.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.net.base.annotation.FunType;
import com.net.base.annotation.LogComponent;
import com.net.base.annotation.LogMethod;
import com.net.base.dao.DeviceManagerDao;
import com.net.base.util.JsonUtils;
import com.net.base.util.ListPageUtils;

@LogComponent(description="设备管理",funcType=FunType.DEVICE_MANAGER)
@Controller
@RequestMapping("/deviceManagermentController")
public class DeviceManagermentController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	DeviceManagerDao deviceManagerDao;
	
	@LogMethod(description="查询设备数据",funcType=FunType.DEVICE_MANAGER)
	@ResponseBody
	@RequestMapping("/deviceManagermentList.do")
	public Map<String,Object> deviceManagermentList(HttpServletRequest request, String deviceType,String deviceName) {
		Map<String, Object> map = ListPageUtils.getListPageDatas(request);
		map.put("deviceType", deviceType);
		map.put("deviceName", deviceName);
		List<Map<String,Object>> list = deviceManagerDao.findDeviceManagermentListDao(map);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Integer id = map.get("start")==null?1:(Integer)map.get("start");
		Iterator<Map<String, Object>> it = list.iterator();
		while (it.hasNext()) {
			id += 1;
			Map<String, Object> mapId = it.next();
			mapId.put("number", id);
		}
		Integer count = deviceManagerDao.findDeviceManagermentListCountDao(map);
		returnMap.put("recordsTotal", count);
		if(map.get("start")!=null)returnMap.put("recordsFiltered", count);
		returnMap.put("data", list);
		return returnMap;
	}
	
	@LogMethod(description="查询设备数据",funcType=FunType.DEVICE_MANAGER)
	@ResponseBody
	@RequestMapping("/getDeviceList.do")
	public List<Map<String,Object>> getDeviceList() {
		return deviceManagerDao.findDeviceList();
	}
	
	
	@LogMethod(description="添加设备数据",funcType=FunType.DEVICE_MANAGER)
	@RequestMapping("/addDeviceManagerment.do")
	@ResponseBody
	public Map<String,Object> addDeviceManagerment(HttpServletRequest request,@RequestParam String params) {
		List<Map<String, String>> para = JsonUtils.getJson4List(params);
		Map<String, String> param = para.get(0);
		String createDate = new SimpleDateFormat("YYYYMMdd").format(new Date());
		param.put("createTime", createDate);
		param.put("updateTime", createDate);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			deviceManagerDao.addDeviceManagermentDao(param);
			returnMap.put("msg", "添加成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			returnMap.put("msg", "添加失败");
		}
		return returnMap;
	}
	
	
	@LogMethod(description="获取设备任务数据",funcType=FunType.DEVICE_MANAGER)
	@RequestMapping("/getDevTask.do")
	@ResponseBody
	public String getDevTask(String deviceId) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if(deviceId !=null){
			try {
				return deviceManagerDao.getDevTask(deviceId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	@LogMethod(description="添加设备任务数据",funcType=FunType.DEVICE_MANAGER)
	@RequestMapping("/addDevTask.do")
	@ResponseBody
	public Map<String,Object> addDevTask(HttpServletRequest request,@RequestParam String params) {
		List<Map<String, String>> para = JsonUtils.getJson4List(params);
		Map<String, String> param = para.get(0);
		String createDate = new SimpleDateFormat("YYYYMMdd").format(new Date());
		param.put("createTime", createDate);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			deviceManagerDao.addDevTask(param);
			returnMap.put("msg", "添加成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			returnMap.put("msg", "添加失败");
		}
		return returnMap;
	}
	
	@LogMethod(description="删除设备数据",funcType=FunType.DEVICE_MANAGER)
	@RequestMapping("/deleteDeviceManagerment.do")
	@ResponseBody
	public Map<String,Object> deleteDeviceManagerment(HttpServletRequest request, String deviceId) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if(deviceId !=null){
			try {
				deviceManagerDao.deleteDeviceManagermentDao(deviceId);
				returnMap.put("msg", "删除成功");
			} catch (Exception e) {
				logger.error(e.getMessage());
				returnMap.put("msg", "删除失败");
			}
		}else{
			returnMap.put("msg", "删除失败");
		}
		return returnMap;
	}
	
	
	
	@LogMethod(description="修改设备数据",funcType=FunType.DEVICE_MANAGER)
	@RequestMapping("/modifyDeviceManagerment.do")
	@ResponseBody
	public Map<String,Object> modifyDeviceManagerment(HttpServletRequest request,@RequestParam String params) {
		List<Map<String, String>> para = JsonUtils.getJson4List(params);
		Map<String, String> param = para.get(0);
		String createDate = new SimpleDateFormat("YYYYMMdd").format(new Date());
		param.put("updateTime", createDate);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			deviceManagerDao.modefyDeviceManagermentDao(param);
			returnMap.put("msg", "修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			returnMap.put("msg", "修改失败");
		}
		return returnMap;
	}
}
