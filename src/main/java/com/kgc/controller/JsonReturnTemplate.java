package com.kgc.controller;

public class JsonReturnTemplate {
	public static final String RETURN_STATUS_SESSION_OUT = "2";
	public static final String RETURN_STATUS_SUCCESS = "1";
	public static final String RETURN_STATUS_FAILED = "0";
	
	private String status = "0"; //0:失败; 1：成功; 2:会话失效;
	private String sessionId;    //会话ID
	private Object error;        //异常信息，错误堆栈信息
	private Object options;      //操作编码
	private Object data;         //传输数据内容
	private String msg;          //消息
	
	public JsonReturnTemplate(String status){
		this.status = status;
	}
	
	public JsonReturnTemplate(String status,Object options){
		this.status = status;
		this.options = options;
	}
	public JsonReturnTemplate(){}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Object getError() {
		return error;
	}
	public void setError(Object error) {
		this.error = error;
	}
	public Object getOptions() {
		return options;
	}
	public void setOptions(Object options) {
		this.options = options;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
   
   
}
