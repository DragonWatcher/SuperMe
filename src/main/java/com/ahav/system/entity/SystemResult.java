package com.ahav.system.entity;
/**
 * 接口返回结果实体类
 * <br>类名：Result<br>
 * 作者： mht<br>
 * 日期： 2018年8月3日-下午1:51:12<br>
 */
public class SystemResult {
    /** 返回状态码，为http状态码*/
    private Integer statusCode;
    /** 返回消息*/
    private String message;
    /** 返回数据*/
    private Object data;
    
    public SystemResult() {
    }
    
    public SystemResult(Integer statusCode, String message, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{statusCode : " + statusCode + ", message : " + message + ", data : " + data + "}";
    }
    
}
