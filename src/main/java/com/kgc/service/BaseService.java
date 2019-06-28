package com.kgc.service;

import com.kgc.dao.BaseDao;

import java.io.Serializable;


public interface BaseService<T,ID extends Serializable, D extends BaseDao<T, ID>> {
	
}
