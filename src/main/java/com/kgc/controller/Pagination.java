package com.kgc.controller;

import com.github.pagehelper.Page;

/**
 * 自定义分页组件，继承PageHelper的Page类，加入sort，作用排序参数
 * 
 *
 * @param <E>
 */
public class Pagination<E> extends Page<E> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String sort;
	
	public Pagination(){
		super();
	}

    public Pagination(int pageNum, int pageSize) {
        super(pageNum, pageSize);
    }
    
    public Pagination(int pageNum, int pageSize, String sort) {
        super(pageNum, pageSize);
        this.sort = sort;
    } 

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	
}
