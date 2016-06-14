package com.net.base.system.page;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;

import com.net.base.util.ArrayUtil;

/**
 * 工具类，用于将HttpRequest的请求信息转给criteria对象
 * 
 * @author hui_ease@163.com
 */
public class CriteriaUtil {
	public static Criteria changeRequest(HttpServletRequest request) {
		Criteria cri = new Criteria();
		Map<String, String> map = fillMap(request);
		String pageNo = map.get("page");
		if (pageNo != null && NumberUtils.isDigits(pageNo))
			cri.setPageNo(Integer.parseInt(pageNo));
		cri.setData(map);
		return cri;
	}

	public static Map<String, String> fillMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String[]> params = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : params.entrySet()) {
			map.put(entry.getKey(), ArrayUtil.join(entry.getValue()));
		}
		return map;
	}
}
