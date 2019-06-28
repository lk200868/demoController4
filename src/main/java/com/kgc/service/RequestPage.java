package com.kgc.service;

import java.util.Map;

/**
 * 
* @ClassName: RequestPage 
* @Description: 翻页参数对象，用于接收页面传输参数 
* @author luobb 
* @version V1.0  
* @see V1.0 
* @date 2017年9月10日 下午4:20:06 
*
 */
public class RequestPage{
	
	 /**
     * 页码，从1开始
     */
    private int pageNum;
    /**
     * 页面大小
     */
    private int pageSize;
    
    /**
     * 页面参数,key-value
     */
    private Map<String, Object> param;
    
    /**
     * 排序
     */
    private Map<String, String> sort;
    
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public Map<String, Object> getParam() {
		return param;
	}
	public void setParam(Map<String, Object> param) {
		this.param = param;
	}
	public Map<String, String> getSort() {
		return sort;
	}
	public void setSort(Map<String, String> sort) {
		this.sort = sort;
	}
}
