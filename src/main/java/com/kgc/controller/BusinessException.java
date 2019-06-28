package com.kgc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class BusinessException extends RuntimeException{
	private static final long serialVersionUID = 8109303352556055331L;
	private static Logger logger = LoggerFactory.getLogger(BusinessException.class.getName());
	
	private String code;
	private String message;
	private String title;
	private Exception e;

	public BusinessException(String code, String title, String message, Exception e) {
		super(title);
		this.code = code;
		this.title = title;
		this.message = message;
		this.e = e;
	}

	public BusinessException(String message, Exception e) {
		super(message);
		this.code = ErrorCode.UNKNOW_ERROR;
		this.message = message;
		this.title = "系统出错了！";
		this.e = e;
	}

	public BusinessException(String title, String message) {
		super(message);
		this.code = ErrorCode.UNKNOW_ERROR;
		this.message = message;
		this.title = title;
		this.e = e;
	}
	
	
	public BusinessException(String message) {
		super(message);
		this.code = ErrorCode.UNKNOW_ERROR;
		this.message = message;
	}
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Exception getException() {
		return e;
	}

	public void setException(Exception e) {
		this.e = e;
	}

	public String toString(){
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(CommonUtils.getCurentAppServerTimestamp())
			.append("\tcode:").append(code).append("\t").append(title).append("\t").append(message)
			.append("\tExceptionStackTrace[").append(CommonUtils.getExceptionStackTraceToString(e)).append("]");
		return strBuf.toString();
	}
	
	public void logger(){
		logger.error(this.toString());
	}

}
