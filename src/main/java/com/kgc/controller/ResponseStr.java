package com.kgc.controller;

/**
 * @ClassName ResponseStr
 * @Description response返回基础类
 * @Author Amos
 * @Version V1.0
 * @Since JDK1.8
 * @Date 2017/9/29 17:35
 */
public class ResponseStr {

    private Object data;
    private String code;
    private boolean isSuccess;
    private String msg;

    public ResponseStr() {
    }

    public ResponseStr(Object data, String code, boolean isSuccess, String msg) {
        this.data = data;
        this.code = code;
        this.isSuccess = isSuccess;
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
