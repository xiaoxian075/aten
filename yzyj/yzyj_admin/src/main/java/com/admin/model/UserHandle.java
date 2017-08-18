package com.admin.model;

/**
 * Created by Administrator on 2016/8/30.
 */
public class UserHandle {
    private String sysId;
    private String userId;
    private String moduleId;
    private String handleArray;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getHandleArray() {
        return handleArray;
    }

    public void setHandleArray(String handleArray) {
        this.handleArray = handleArray;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }
}
