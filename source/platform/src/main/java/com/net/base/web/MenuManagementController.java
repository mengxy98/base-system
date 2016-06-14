package com.net.base.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.net.base.annotation.FunType;
import com.net.base.annotation.LogComponent;
import com.net.base.annotation.LogMethod;
import com.net.base.dao.BasicDao;
import com.net.base.util.JsonUtils;

/**
 * 
 * Description:菜单管理
 * Company:东方网信技术股份有限公司
 * Date: 2016年5月25日
 * Time: 下午8:22:46
 * @author gongjzh@net-east.com
 */
@LogComponent(description="菜单管理",funcType=FunType.MENU_MANAGER)
@Controller
@RequestMapping("/menuManagementController")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MenuManagementController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	BasicDao basicDao;

	/**
	 * 
	 * 方法名：queryData
	 * 方法说明:查询全部菜单数据
	 * @return
	 * <B>修改记录:</B><BR>
	 * Date: 2016年5月25日下午8:33:01
	 * @author gongjzh@net-east.com
	 */
	@LogMethod(description="查询全部菜单数据",funcType=FunType.MENU_MANAGER)
	@RequestMapping("/queryData.do")
	@ResponseBody
	public List<Map> queryData(HttpServletRequest request) {
		List<Map> list = basicDao.queryForList("sc_menu_tree.queryData");
		return list;
	}

	/**
	 * 
	 * 方法名：addData
	 * 方法说明:添加菜单数据配置
	 * @param params
	 * @return
	 * <B>修改记录:</B><BR>
	 * Date: 2016年5月25日下午8:33:41
	 * @author gongjzh@net-east.com
	 */
	@LogMethod(description="添加菜单数据配置",funcType=FunType.MENU_MANAGER)
	@ResponseBody
	@RequestMapping("/addData.do")
	public List addData(HttpServletRequest request,String params) {
		List<Map<String, String>> para = JsonUtils.getJson4List(params);
		Map<String, String> param = para.get(0);
		List returnList = new ArrayList();
		Map messageMap = new HashMap();
		try {
			basicDao.insert("sc_menu_tree.addData", param);
			messageMap.put("state", "0");
			returnList.add(messageMap);
		} catch (Exception e) {
			messageMap.put("state", "1");
			returnList.add(messageMap);
			logger.debug(e.toString());
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 
	 * 方法名：queryOldData
	 * 方法说明:查询单个菜单数据
	 * @param menuId
	 * @return
	 * <B>修改记录:</B><BR>
	 * Date: 2016年5月25日下午8:34:39
	 * @author gongjzh@net-east.com
	 */
	@LogMethod(description="查询单个菜单数据",funcType=FunType.MENU_MANAGER)
	@RequestMapping("/queryOldData.do")
	@ResponseBody
	public List<Map> queryOldData(HttpServletRequest request,String menuId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("menuId", menuId);
		List<Map> list = (List<Map>) basicDao.queryForList(
				"sc_menu_tree.queryOldData", map);
		return list;
	}

	/**
	 * 
	 * 方法名：updateData
	 * 方法说明:修改单个菜单数据
	 * @param params
	 * @return
	 * <B>修改记录:</B><BR>
	 * Date: 2016年5月25日下午8:35:37
	 * @author gongjzh@net-east.com
	 */
	@LogMethod(description="修改单个菜单数据",funcType=FunType.MENU_MANAGER)
	@ResponseBody
	@RequestMapping("/updateData.do")
	public List updateData(HttpServletRequest request,String params) {
		List<Map<String, String>> para = JsonUtils.getJson4List(params);
		Map<String, String> param = para.get(0);
		List returnList = new ArrayList();
		Map messageMap = new HashMap();
		try {
			basicDao.update("sc_menu_tree.updateData", param);
			messageMap.put("state", "0");
			returnList.add(messageMap);
		} catch (Exception e) {
			messageMap.put("state", "1");
			returnList.add(messageMap);
			logger.debug(e.toString());
			e.printStackTrace();
		}

		return returnList;
	}

	/**
	 * 
	 * 方法名：delectData
	 * 方法说明:删除菜单配置数据
	 * @param menuIdArr
	 * @return
	 * <B>修改记录:</B><BR>
	 * Date: 2016年5月25日下午8:36:11
	 * @author gongjzh@net-east.com
	 */
	@LogMethod(description="删除菜单配置数据",funcType=FunType.MENU_MANAGER)
	@RequestMapping("/delectData.do")
	@ResponseBody
	public List<Map> delectData(HttpServletRequest request,Integer[] menuIdArr) {
		Map messageMap = new HashMap();
		List returnList = new ArrayList();
		Integer right = 0;
		Integer wrong = 0;
		try {
			for (int i = 0; i < menuIdArr.length; i++) {
				Map<String, Object> map = new HashMap<String, Object>();

				map.put("MENU_ID", menuIdArr[i]);
				List<Map> list = (List<Map>) basicDao.queryForList(
						"sc_menu_tree.queryRoleMenu", map);
				if (list == null || list.size() == 0) {
                   right++;
                   //删除菜单表数据
                   basicDao.delete("sc_menu_tree.delectData",map);
				}else{
					wrong++;
				}
			}
			messageMap.put("success", right);
			messageMap.put("fail", wrong);
			messageMap.put("state", "0");
			returnList.add(messageMap);
		} catch (Exception e) {
			messageMap.put("state", "1");
			returnList.add(messageMap);
			logger.debug(e.toString());
			e.printStackTrace();
		}

		return returnList;
	}
	
	/**
	 * 查询导航栏菜单
	 */
	@RequestMapping("/queryMenuData.do")
	@ResponseBody
	public List<Map> queryMenuData(String userName) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userName", userName);
		List<Map> data=new ArrayList<Map>();
		try {
			//通过用户ID查询对应的菜单
		 data =	basicDao.queryForList("sc_menu_tree.queryMenu",map);
		} catch (Exception e) {
			logger.debug(e.toString());
			e.printStackTrace();
		}
		return data;
		
	}

}
