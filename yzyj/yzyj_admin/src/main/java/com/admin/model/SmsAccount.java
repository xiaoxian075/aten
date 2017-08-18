package com.admin.model;

import java.io.Serializable;
import java.sql.Timestamp;


public class SmsAccount implements Serializable{
    private static final long serialVersionUID = 1523491741770193584L;

    private long id;
    private String name;
    private int sex;
    private String phone;
    private long groupId;
    private Timestamp createTime;
    private Timestamp updateTime;

    public SmsAccount() {
    }

    public SmsAccount(long id, String name, int sex, String phone, long groupId, Timestamp createTime, Timestamp updateTime) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.groupId = groupId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
