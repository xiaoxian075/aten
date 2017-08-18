package com.communal.entity;

import java.io.Serializable;

/**
 * 功能描述：响应的结果对象
 * 作者： huangdw
 * 时间：2015/11/13.
 */
public class Result implements Serializable {
    private static final long serialVersionUID = 6288374846131788743L;

    /**
     * 信息
     */
    private String message;

    /**
     * 状态码
     */
    private int statusCode;

    /**
     * 是否成功
     */
    private boolean success;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Result() {

    }

    public Result(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
