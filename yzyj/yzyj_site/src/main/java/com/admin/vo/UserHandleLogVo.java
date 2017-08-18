package com.admin.vo;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/9/13.
 */
public class UserHandleLogVo {
    private HttpServletRequest request;
    private Object thisObj;
    private Integer handType;
    private Object oldVo;
    private Object newVo;
    private String index;

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public Object getThisObj() {
        return thisObj;
    }

    public void setThisObj(Object thisObj) {
        this.thisObj = thisObj;
    }

    public Integer getHandType() {
        return handType;
    }

    public void setHandType(Integer handType) {
        this.handType = handType;
    }

    public Object getOldVo() {
        return oldVo;
    }

    public void setOldVo(Object oldVo) {
        this.oldVo = oldVo;
    }

    public Object getNewVo() {
        return newVo;
    }

    public void setNewVo(Object newVo) {
        this.newVo = newVo;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
