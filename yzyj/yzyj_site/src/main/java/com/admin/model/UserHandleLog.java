package com.admin.model;

import java.util.Date;

/**
 * Created by Administrator on 2016/9/12.
 */
public class UserHandleLog {
    private String sysId;
    private Date createTime;
    private String userId;
    private String handleIndex;
    private Integer handleType;
    private String handleModel;
    private String handleBody;
    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getHandleType() {
        return handleType;
    }

    public void setHandleType(Integer handleType) {
        this.handleType = handleType;
    }

    public String getHandleModel() {
        return handleModel;
    }

    public void setHandleModel(String handleModel) {
        this.handleModel = handleModel;
    }

    public String getHandleBody() {
        return handleBody;
    }

    public void setHandleBody(String handleBody) {
        this.handleBody = handleBody;
    }

    public String getHandleIndex() {
        return handleIndex;
    }

    public void setHandleIndex(String handleIndex) {
        this.handleIndex = handleIndex;
    }
}
