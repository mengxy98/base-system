package com.net.base.core;

import java.util.Map;

/**
 * Description:上下文资源管理类
 * Company:XX.com
 * Date: 2016年5月28日
 * Time: 上午10:43:56
 * @author 914962981@qq.com
 */
public interface LocalResourcesManager {

	public Map<String,Object> getCodeInfoMapByType(String codeType);
	public Map<String,Object> getCodeInfoMapByCode(String codeType,String[] code);
	
}

