package com.kgc.service;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.kgc.dao.BaseMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;


public abstract class BaseSQLServiceImpl<T, ID extends Serializable, D extends BaseMapper<T, ID>> implements BaseSQLService<T, ID, D> {
	private static Logger logger = LoggerFactory.getLogger(BaseSQLServiceImpl.class.getName());
	
	@Autowired  
	protected HttpSession session;

	@Autowired  
	protected HttpServletRequest request;
	
	private Class<D> mapperClass;
	protected D mapper;
	
	protected List<ResultMapping> resultMapping;

	@Autowired
	protected SqlSession sqlSession ;
	
	@SuppressWarnings("unchecked")
	public BaseSQLServiceImpl(){
		mapperClass = GenericsUtils.getSuperClassGenricType(this.getClass(),2);
	}
	
	public D getMapper() {
		return mapper;
	}

	@PostConstruct
	private void initMapper(){
		if(mapper == null){
			logger.debug("" + mapperClass);
			mapper = sqlSession.getMapper(mapperClass);
		}
		resultMapping = sqlSession.getConfiguration().getResultMap(mapperClass.getName() + ".BaseResultMap").getResultMappings();
	}

	@Override
	public int delete(ID id) {
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(T record) {
		return mapper.insertSelective(record);
	}

	@Override
	public T selectByPrimaryKey(ID id) {
		return mapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int updateByPrimaryKeyWithBLOBs(T record) {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(T record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(T record) {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int insert(T record) {
		return mapper.insert(record);
	}
	
	/**
	 * 执行对应mapper.xml中的"selectAll"语句， 返回对象为mapper.xml中resultMap定义的实体类
	 * pageNum: 当前页数
	 * pageSize: 每页记录数
	 * order: 排序字符串：如 id asc, name desc, create_time desc
	 */
	@Override
	public PageInfo<T> selectAll(Map<String, Object> queryParam, Pagination<T> p) {
		if(queryParam == null){
			queryParam = new HashMap<String, Object>();
		}
		
		if(StringUtils.isNotBlank(p.getSort())){
			String sort = p.getSort();
			for (ResultMapping mp : resultMapping) {
				sort = sort.replaceAll(mp.getProperty(), mp.getColumn());
			}
			queryParam.put("orderby", sort);
		}
		PageHelper.startPage(p.getPageNum(), p.getPageSize());
		Page<T> page = (Page<T>) mapper.selectAll(queryParam);
		return new PageInfo<T>(page);
	}
	
	public PageInfo<T> page(RequestPage p){
		Map<String, Object> queryParam = p.getParam();
		
		if(queryParam == null) {
			queryParam = new HashMap<String, Object>();
		}
		
		/*
		if(StringUtils.isNotBlank(p.getSort())){
			String sort = p.getSort();
			for (ResultMapping mp : resultMapping) {
				sort = sort.replaceAll(mp.getProperty(), mp.getColumn());
			}
			queryParam.put("orderby", sort);
		}
		*/
		
		PageHelper.startPage(p.getPageNum(), p.getPageSize());
		
		Page<T> page = (Page<T>) mapper.selectAll(queryParam);
		return new PageInfo<T>(page);
	}


	/**
	 * 执行对应mapper.xml中的"selectAllToMap"语句， 返回对象会以Map<String,Object>封装
	 * pageNum: 当前页数
	 * pageSize: 每页记录数
	 * order: 排序字符串：如 id asc, name desc, create_time desc
	 */
	@Override
	public PageInfo<Map<String, Object>> selectAllToMap(Map<String, Object> queryParam, Pagination<Map<String, Object>> p) {
		if(queryParam == null){
			queryParam = new HashMap<String, Object>();
		}
		if(StringUtils.isNotBlank(p.getSort())){
			queryParam.put("orderby", p.getSort());
		}
		PageHelper.startPage(p.getPageNum(), p.getPageSize());
		Page<Map<String, Object>> page =  (Page<Map<String, Object>>) mapper.selectAllToMap(queryParam);
		return new PageInfo<Map<String, Object>>(page);
	}
	
}