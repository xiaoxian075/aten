package com.admin.model;

import java.io.Serializable;
import java.sql.Timestamp;


public class SmsGroup implements Serializable{
    private static final long serialVersionUID = 381618178879041822L;

    private long id;
    private String name;
    private int accCount;
    private int state;
    private Timestamp createTime;
    private Timestamp updateTime;

    public SmsGroup() {

    }

    public SmsGroup(long id, String name, int accCount, int state, Timestamp createTime, Timestamp updateTime) {
        this.id = id;
        this.name = name;
        this.accCount = accCount;
        this.state = state;
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

    public int getAccCount() {
        return accCount;
    }

    public void setAccCount(int accCount) {
        this.accCount = accCount;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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
