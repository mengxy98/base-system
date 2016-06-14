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
import com.net.base.dao.UserManagerDao;
import com.net.base.util.JsonUtils;
import com.net.base.util.ListPageUtils;


/**
 * 
 * Description:用户管理
 * Company:东方网信技术股份有限公司
 * Date: 2016年5月25日
 * Time: 下午8:37:32
 * @author gongjzh@net-east.com
 */
@LogComponent(description="用户管理",funcType=FunType.USER_MANAGER)
@Controller
@RequestMapping("/UserManagermentController")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class UserManagermentController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UserManagerDao managerDao;
	/*
	 * 机构名称字典表
	 */
	@ResponseBody
	@RequestMapping("/orgNameDict.do")
	public List<Map> orgNameDict() {
		List<Map> data = managerDao.findOrgDict();
		return data;
	}
	
	//获取角色的名字
	@ResponseBody
	@RequestMapping("/finderoleName.do")
	public List<Map> finderoleName() {
		List<Map> data = managerDao.finderoleName();
		return data;
	}
	
    
	/**
	 * 
	 * 方法名：userManagermentList
	 * 方法说明:查询用户管理列表数据
	 * @param request
	 * @param orgcode
	 * @param role
	 * @param user
	 * @return
	 * <B>修改记录:</B><BR>
	 * Date: 2016年5月25日下午8:39:03
	 * @author gongjzh@net-east.com
	 */
	@LogMethod(description="查询用户管理列表数据",funcType=FunType.USER_MANAGER)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/userManagermentList.do")
	@ResponseBody
	public Map userManagermentList(HttpServletRequest request, String orgcode,String role, String user) {
		Map<String, Object> map = ListPageUtils.getListPageDatas(request);
		if (orgcode.equals("1")) {
			orgcode = "";
		}
		map.put("orgcode", orgcode);
		map.put("role", role);
		map.put("user", user);
		List<Map> list = managerDao.findUserManagermentListDao(map);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Integer id = map.get("start")==null?1:(Integer)map.get("start");
		Iterator<Map> it = list.iterator();
		while (it.hasNext()) {
			id += 1;
			Map mapId = it.next();
			mapId.put("number", id);
		}
		Integer count = managerDao.findUserManagermentListCountDao(map);
		returnMap.put("recordsTotal", count);
		if(map.get("start")!=null)returnMap.put("recordsFiltered", count);
		returnMap.put("data", list);
		return returnMap;
	}
	

	/**
	 * 
	 * 方法名：addUserManagerment
	 * 方法说明:添加用户数据
	 * @param params
	 * @return
	 * <B>修改记录:</B><BR>
	 * Date: 2016年5月25日下午8:39:25
	 * @author gongjzh@net-east.com
	 */
	@LogMethod(description="添加用户数据",funcType=FunType.USER_MANAGER)
	@RequestMapping("/addUserManagerment.do")
	@ResponseBody
	public Map addUserManagerment(HttpServletRequest request,@RequestParam String params) {
		List<Map<String, String>> para = JsonUtils.getJson4List(params);
		Map<String, String> param = para.get(0);
		String createDate = new SimpleDateFormat("YYYYMMdd").format(new Date());
		param.put("createDate", createDate);
		param.put("updateDate", createDate);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			managerDao.addUserManagermentDao(param);
			returnMap.put("msg", "添加成功");
		} catch (Exception e) {
			returnMap.put("msg", "添加失败");
		}
		//获取用户名和密码
		Map<String,Object> findMap = new HashMap<String, Object>();
		findMap.put("userName",param.get("userName"));
		findMap.put("password",param.get("password"));
		//通过用户名和密码获取主键
		List<Map> data = managerDao.findPrimaryKeyDao(findMap);
		if (data !=null && data.size()>0){
			//获取关联角色的id
			Map<String, Object> userAndRole = new HashMap<String, Object>();
			userAndRole.put("userId",data.get(0).get("userId"));
			userAndRole.put("roleId",param.get("roleName"));
			//往用户和角色关联的表插入相应的码值
			managerDao.addUserAndRoleDao(userAndRole);
		}
		return returnMap;
	}
	
	/**
	 * 
	 * 方法名：deleteUserManagerment
	 * 方法说明:删除用户数据
	 * @param request
	 * @param userID
	 * @return
	 * <B>修改记录:</B><BR>
	 * Date: 2016年5月25日下午8:39:59
	 * @author gongjzh@net-east.com
	 */
	@LogMethod(description="删除用户数据",funcType=FunType.USER_MANAGER)
	@RequestMapping("/deleteUserManagerment.do")
	@ResponseBody
	public Map deleteUserManagerment(HttpServletRequest request, String userID) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if(userID !=null){
			try {
				managerDao.deleteUserManagermentDao(userID);
				returnMap.put("msg", "删除成功");
			} catch (Exception e) {
				returnMap.put("msg", "删除失败");
			}
		}else{
			returnMap.put("msg", "删除失败");
		}
		return returnMap;
	}


	/**
	 * 
	 * 方法名：modifyUserManagerment
	 * 方法说明:修改用户数据
	 * @param params
	 * @return
	 * <B>修改记录:</B><BR>
	 * Date: 2016年5月25日下午8:40:32
	 * @author gongjzh@net-east.com
	 */
	@LogMethod(description="修改用户数据",funcType=FunType.USER_MANAGER)
	@RequestMapping("/modifyUserManagerment.do")
	@ResponseBody
	public Map modifyUserManagerment(HttpServletRequest request,@RequestParam String params) {
		List<Map<String, String>> para = JsonUtils.getJson4List(params);
		Map<String, String> param = para.get(0);
		String ps = param.get("password");
		if(ps==null || "null".equals(ps)){
			param.remove("password");
		}
		String updateDate = new SimpleDateFormat("YYYYMMdd").format(new Date());
		param.put("updateDate", updateDate);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			managerDao.modifyUserManagermentDao(param);
			returnMap.put("msg", "修改成功");
		} catch (Exception e) {
			returnMap.put("msg", "修改失败");
		}
		return returnMap;
	}
	
	//查询用户名是否重复
	@RequestMapping("/userRepeat.do")
	@ResponseBody
	public Map userRepeat(HttpServletRequest request, String userName) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<Map> result= managerDao.userRepeatDao(userName);
		if (result.size()>0) {
			returnMap.put("msg", "1");
		}else {
			returnMap.put("msg", "0");
		}
		return returnMap;
	}
}
