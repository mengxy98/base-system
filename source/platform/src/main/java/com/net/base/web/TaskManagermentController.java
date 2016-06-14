package com.net.base.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.net.base.dao.TaskManagerDao;
import com.net.base.util.JsonUtils;
import com.net.base.util.ListPageUtils;

@LogComponent(description="任务管理",funcType=FunType.TASK_MANAGER)
@Controller
@RequestMapping("/taskManagermentController")
public class TaskManagermentController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	TaskManagerDao taskManagerDao;
	
	@LogMethod(description="查询任务数据",funcType=FunType.TASK_MANAGER)
	@ResponseBody
	@RequestMapping("/getDataList.do")
	public Map<String,Object> getDataList(HttpServletRequest request, String taskName) {
		Map<String, Object> map = ListPageUtils.getListPageDatas(request);
		map.put("taskName", taskName);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Integer count = taskManagerDao.findMainDataListCount(map);
		returnMap.put("recordsTotal", count);
		if(map.get("start")!=null){
			returnMap.put("recordsFiltered", count);
		}else{
			map.put("start", 0);
		}
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if (count != null && count > 0) {
			list = taskManagerDao.findMainDataList(map);
			returnMap.put("data", list);
		}else{
			returnMap.put("data", list);
		}
		return returnMap;
	}
	
	@LogMethod(description="查询任务数据",funcType=FunType.TASK_MANAGER)
	@ResponseBody
	@RequestMapping("/getTaskList.do")
	public List<Map<String,Object>> getTaskList() {
		return taskManagerDao.getTaskList();
	}
	
	@LogMethod(description="添加任务数据",funcType=FunType.TASK_MANAGER)
	@RequestMapping("/addData.do")
	@ResponseBody
	public Map<String,Object> addData(HttpServletRequest request,@RequestParam String params) {
		List<Map<String, String>> para = JsonUtils.getJson4List(params);
		Map<String, String> param = para.get(0);
		String createDate = new SimpleDateFormat("YYYYMMdd").format(new Date());
		param.put("createTime", createDate);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			taskManagerDao.addMainData(param);
			returnMap.put("msg", "添加成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			returnMap.put("msg", "添加失败");
		}
		return returnMap;
	}
	
	
	@LogMethod(description="删除任务数据",funcType=FunType.TASK_MANAGER)
	@RequestMapping("/deleteData.do")
	@ResponseBody
	public Map<String,Object> deleteData(HttpServletRequest request, String id) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if(id !=null){
			try {
				taskManagerDao.deleteMainData(id);
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
	
	
	@LogMethod(description="修改任务数据",funcType=FunType.TASK_MANAGER)
	@RequestMapping("/modifyData.do")
	@ResponseBody
	public Map<String,Object> modifyData(HttpServletRequest request,@RequestParam String params) {
		List<Map<String, String>> para = JsonUtils.getJson4List(params);
		Map<String, String> param = para.get(0);
		String createDate = new SimpleDateFormat("YYYYMMdd").format(new Date());
		param.put("updateTime", createDate);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			taskManagerDao.modefyMainData(param);
			returnMap.put("msg", "修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			returnMap.put("msg", "修改失败");
		}
		return returnMap;
	}
}
