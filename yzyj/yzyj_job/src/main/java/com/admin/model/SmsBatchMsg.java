package com.admin.model;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class SmsBatchMsg implements Serializable {
    private static final long serialVersionUID = 1523491741770193584L;

    private Long id;
    private String text;
    private String mobiles;
    private Integer state;  //0:已更新 1:未更新
    private Timestamp sendTime;
    private Timestamp createTime;
    private Timestamp updateTime;

    public SmsBatchMsg() {
    }

    public SmsBatchMsg(Long id, String text, String mobiles, Integer state, Timestamp sendTime, Timestamp createTime, Timestamp updateTime) {
        this.id = id;
        this.text = text;
        this.mobiles = mobiles;
        this.state = state;
        this.sendTime = sendTime;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMobiles() {
        return mobiles;
    }

    public void setMobiles(String mobiles) {
        this.mobiles = mobiles;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Timestamp getSendTime() {
        return sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
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

