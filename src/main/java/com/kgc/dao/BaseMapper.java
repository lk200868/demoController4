package com.kgc.dao;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseMapper<T, ID extends Serializable> extends BaseDao<T,ID> {
//public interface BaseMapper<T, ID extends Serializable>  {

	int deleteByPrimaryKey(ID id);

	int insert(T record);

	int insertSelective(T record);

	T selectByPrimaryKey(ID id);
	
	int updateByPrimaryKeyWithBLOBs(T record);

	int updateByPrimaryKeySelective(T record);

	int updateByPrimaryKey(T record);

	List<T> selectAll(Map <String, Object> queryParam);

	List<Map<String, Object>> selectAllToMap(Map <String, Object> queryParam);

}
