package com.net.base.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.net.base.annotation.FunType;
import com.net.base.annotation.LogComponent;
import com.net.base.annotation.LogMethod;
import com.net.base.dao.BasicDao;
import com.net.base.util.DateUtil;
import com.net.base.util.JsonUtils;
import com.net.base.util.ListPageUtils;


/**
 * 
 * Description:角色管理
 * Company:东方网信技术股份有限公司
 * Date: 2016年5月25日
 * Time: 下午5:02:17
 * @author gongjzh@net-east.com
 */
@LogComponent(description="角色管理",funcType=FunType.ROLE_MANAGER)
@Controller
@RequestMapping("/roleManagementController")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RoleManagementController {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	BasicDao basicDao;

	/**
	 * 
	 * 方法名：queryData
	 * 方法说明:角色查询
	 * @param request
	 * @param role
	 * @return
	 * <B>修改记录:</B><BR>
	 * Date: 2016年5月25日下午4:58:11
	 * @author gongjzh@net-east.com
	 */
	@LogMethod(description="查询角色",funcType=FunType.ROLE_MANAGER)
	@RequestMapping("/queryData.do")
	@ResponseBody
	public Map queryData(HttpServletRequest request, String role) {
		Map<String, Object> map = ListPageUtils.getListPageDatas(request);
		map.put("role", role);
		List<Map> list = basicDao.queryForList("sc_role.queryData", map);
		Integer count = (Integer) (basicDao.queryForObject(
				"sc_role.queryDataCount", map));
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Integer id = map.get("start")==null?1:(Integer)map.get("start");
		Iterator<Map> it = list.iterator();
		while (it.hasNext()) {
			id += 1;
			Map mapId = it.next();
			mapId.put("id", id);
		}
		returnMap.put("recordsTotal", count);
		if(map.get("start")!=null)returnMap.put("recordsFiltered", count);
		returnMap.put("data", list);
		return returnMap;
	}

	/**
	 * 
	 * 方法名：addData
	 * 方法说明:添加角色配置数据
	 * @param params
	 * @return
	 * <B>修改记录:</B><BR>
	 * Date: 2016年5月25日下午8:17:34
	 * @author gongjzh@net-east.com
	 */
	@LogMethod(description="添加角色配置数据",funcType=FunType.ROLE_MANAGER)
	@ResponseBody
	@RequestMapping("/addData.do")
	public List addData(HttpServletRequest request,String params) {
		List<Map<String, String>> para = JsonUtils.getJson4List(params);
		Map<String, String> param = para.get(0);
		param.put("CREATE_TIME", DateUtil.getNow());
		param.put("UPDATE_TIME", DateUtil.getNow());
		List returnList = new ArrayList();
		Map messageMap = new HashMap();
		try {
			basicDao.insert("sc_role.addData", param);
			messageMap.put("state", "0");
			returnList.add(messageMap);
		} catch (Exception e) {
			messageMap.put("state", "1");
			returnList.add(messageMap);
		}

		return returnList;
	}

	/**
	 * 查询需要修改数据
	 */
	@RequestMapping("/queryOldData.do")
	@ResponseBody
	public List<Map> queryOldData(HttpServletRequest request, String ROLE_ID) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ROLE_ID", ROLE_ID);
		List<Map> list = basicDao.queryForList("sc_role.queryOldData", map);
		return list;
	}


	/**
	 * 
	 * 方法名：
	 * 方法说明:修改角色配置数据
	 * @param params
	 * @return
	 * <B>修改记录:</B><BR>
	 * Date: 2016年5月25日下午8:16:20
	 * @author gongjzh@net-east.com
	 */
	@LogMethod(description="修改角色配置数据",funcType=FunType.ROLE_MANAGER)
	@ResponseBody
	@RequestMapping("/updateData.do")
	public List updateData(HttpServletRequest request,String params) {
		List<Map<String, String>> para = JsonUtils.getJson4List(params);
		Map<String, String> param = para.get(0);
		param.put("UPDATE_TIME", DateUtil.getNow());
		List returnList = new ArrayList();
		Map messageMap = new HashMap();
		try {
			basicDao.update("sc_role.updateData", param);
			messageMap.put("state", "0");
			returnList.add(messageMap);
		} catch (Exception e) {
			messageMap.put("state", "1");
			returnList.add(messageMap);
		}

		return returnList;
	}

	/**
	 * 
	 * 方法名：delectData
	 * 方法说明:删除角色配置数据
	 * @param ROLE_ID
	 * @return
	 * <B>修改记录:</B><BR>
	 * Date: 2016年5月25日下午8:19:38
	 * @author gongjzh@net-east.com
	 */
	@LogMethod(description="删除角色配置数据",funcType=FunType.ROLE_MANAGER)
	@ResponseBody
	@RequestMapping("/delectData.do")
	public List delectData(HttpServletRequest request,String ROLE_ID) {
		List returnList = new ArrayList();
		Map messageMap = new HashMap();
		int wrong=0;
		int right=0;
		try {
			String [] str=ROLE_ID.split(",");
			for (int i = 0; i < str.length; i++) {
				Map map =new HashMap();
				map.put("ROLE_ID", str[i]);
				//判断角色是否被用户占用
			List<Map> returnData=basicDao.queryForList("sc_role.queryUser",map);
			if(returnData.size()>0){
				wrong++;
			}else {
				right++;
				  basicDao.delete("sc_role.delectData", str[i]);
				  basicDao.delete("sc_role.delectDatas", str[i]);
			}
			}
			messageMap.put("state", "0");
			messageMap.put("right", right);
			messageMap.put("wrong", wrong);
			returnList.add(messageMap);
		} catch (Exception e) {
			messageMap.put("state", "1");
			returnList.add(messageMap);
		}

		return returnList;
	}

	/**
	 * 查询菜单状态
	 */
	@ResponseBody
	@RequestMapping("/queryMenuState.do")
	public List<Map> queryMenuState(String ROLE_ID) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ROLE_ID", ROLE_ID);
		List<Map> returnList = (List<Map>) basicDao.queryForList("sc_role.queryMenuState",map);
		return returnList;

	}
	
	
    /**
     * 
     * 方法名：updateMenuState
     * 方法说明:修改角色菜单数据
     * @param ROLE_ID
     * @param menuId
     * @return
     * <B>修改记录:</B><BR>
     * Date: 2016年5月25日下午8:21:47
     * @author gongjzh@net-east.com
     */
	@LogMethod(description="修改角色菜单数据",funcType=FunType.ROLE_MANAGER)
	@ResponseBody
	@RequestMapping("/updateMenuState.do")
	public List updateMenuState(String ROLE_ID,Integer[] menuId ) {
		List returnList = new ArrayList();
		Map messageMap = new HashMap();
		try {
			basicDao.delete("sc_role.deleteRoleMenu", ROLE_ID);
			if(null!=menuId){
				for (int i = 0; i < menuId.length; i++) {
					Map param =new HashMap<String, String>();
					param.put("ROLE_ID",ROLE_ID );
					param.put("menuId",menuId[i] );
					basicDao.insert("sc_role.addRoleMenu", param);
				}
			}
			messageMap.put("state", "0");
			returnList.add(messageMap);
		} catch (Exception e) {
			messageMap.put("state", "1");
			returnList.add(messageMap);
		}

		return returnList;
	}
	
	
	/**
	 * 查询默认菜单
	 */
	@ResponseBody
	@RequestMapping("/queryDefaultPage.do")
	public List queryDefaultPage() {
		List returnList = basicDao.queryForList("sc_role.queryDefaultPage");
		return returnList;
	}
	
	
}
