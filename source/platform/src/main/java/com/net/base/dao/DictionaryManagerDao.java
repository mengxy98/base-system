package com.net.base.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("rawtypes")
public interface DictionaryManagerDao {
	
	public List<Map> findDict(Map params);
	
	
}
