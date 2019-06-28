package com.kgc.service;

import com.github.pagehelper.PageInfo;
import com.kgc.dao.BaseMapper;
import java.io.Serializable;
import java.util.Map;


public interface BaseSQLService<T, ID extends Serializable, D extends BaseMapper<T, ID>> extends BaseService<T, ID, D>{

	int delete(ID deptId);
 
	int insert(T record);

	int insertSelective(T record);

	
	T selectByPrimaryKey(ID id);
	
	//T selectByPrimaryKey(ID id);
	
	int updateByPrimaryKeyWithBLOBs(T record);

	int updateByPrimaryKeySelective(T record);

	int updateByPrimaryKey(T record);
	
	PageInfo<T> selectAll(Map <String, Object> queryParam, Pagination <T> page);
	
	PageInfo<T> page(RequestPage page);
	
	PageInfo<Map<String, Object>> selectAllToMap(Map <String, Object> queryParam, Pagination <Map <String, Object>> page);

}
