package com.admin.vo;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/10/9.
 */
public class CommAppVo {
    private String message;
    private Integer statusCode;
    private boolean success;
    private JSONObject totalData;
    private Integer intData;
    private List<HashMap<Object,Object>> data;


    public boolean isSuccess() {
        return success;
    }

    public Integer getIntData() {
        return intData;
    }

    public void setIntData(Integer intData) {
        this.intData = intData;
    }

    public JSONObject getTotalData() {
        return totalData;
    }
    public void setTotalData(JSONObject totalData) {
        this.totalData = totalData;
    }

    public  List<HashMap<Object, Object>> getData() {
        return data;
    }
    public void setData(List<HashMap<Object, Object>> data) {
        this.data = data;
    }
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
