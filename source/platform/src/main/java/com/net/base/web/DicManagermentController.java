package com.net.base.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.net.base.core.LocalResourcesManager;

@Controller
@RequestMapping("/dicManagermentController")
public class DicManagermentController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	LocalResourcesManager localResourcesManager;
	/*
	 * 设备类型名称字典表
	 */
	@ResponseBody
	@RequestMapping("/typeNameDict.do")
	public Map<String, Object> typeNameDict(HttpServletRequest request) {
		String type = request.getParameter("type");//获取字典类型的数据
		if (null == type || type.length() == 0) return null; //禁止获取全部的字典数据
		String[] codeStrings = type.split(",");
		Map<String, Object> returnMap = localResourcesManager.getCodeInfoMapByCode("PUBLIC_PARAMS",codeStrings);
		return returnMap;
	}
	
	
}
