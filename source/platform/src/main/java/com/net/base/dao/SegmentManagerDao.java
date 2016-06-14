package com.net.base.dao;


import java.util.List;
import java.util.Map;

public interface SegmentManagerDao {
	List<Map<String, Object>> findSegmentManagermentListDao(Map<String, Object> map);

	void addSegmentManagermentDao(Map<String, String> param);

	void deleteSegmentManagermentDao(String id);

	void modefySegmentManagermentDao(Map<String, String> param);

	Integer findSegmentManagermentListCount(Map<String, Object> map);

	List<Map<String, Object>> getSegmentList();

}
