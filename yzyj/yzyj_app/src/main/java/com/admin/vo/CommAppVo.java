package com.admin.vo;

import java.util.HashMap;

/**
 * Created by wjf on 2016/10/9.
 */
public class CommAppVo {
    private String message;
    private Integer statusCode;
    private boolean success;
    private java.util.HashMap hashMap;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public HashMap getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap hashMap) {
        this.hashMap = hashMap;
    }
}
