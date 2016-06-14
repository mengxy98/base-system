package com.net.base.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class ListPageUtils {

	@SuppressWarnings("rawtypes")
	public static Map<String, Object> getListPageDatas(HttpServletRequest request){
		String orderNum = "", orderDes = "", order = "",search="";
		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String keyString = (String) enumeration.nextElement();
			if (Pattern.compile("order\\[\\d\\]\\[column\\]")
					.matcher(keyString).matches()) {
				orderNum = request.getParameter(keyString);
			} else if (Pattern.compile("order\\[\\d\\]\\[dir\\]")
					.matcher(keyString).matches()) {
				orderDes = request.getParameter(keyString);
				order = request.getParameter("columns[" + orderNum + "][data]");
			}else if(Pattern.compile("search\\[value\\]").matcher(keyString).matches()){
				search=request.getParameter(keyString);
			}
		}
		Integer start = request.getParameter("start")==null?null:Integer.valueOf(request.getParameter("start"));
		Integer size = request.getParameter("length")==null?null:Integer.valueOf(request.getParameter("length"));
		Map<String, Object> linkids = new HashMap<String, Object>();
		linkids.put("start", start);
		linkids.put("size", size);
		linkids.put("order", order); 
		linkids.put("orderDes", orderDes); 
		if(search!=null&&search!=""){
		linkids.put("search","%"+search+"%");
		}
		return linkids;
			
		
	}
	
	}
