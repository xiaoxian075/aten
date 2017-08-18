package com.admin.model;



import java.io.Serializable;
import java.sql.Timestamp;


public class SmsModel implements Serializable{
    private static final long serialVersionUID = -5879063813151407939L;

    private long id;
    private String title;
    private String sign;
    private String content;
    private int state;
    private String reason;
    private Timestamp createTime;
    private Timestamp updateTime;

    public SmsModel() {
    }

    public SmsModel(long id, String title, String sign, String content, int state, String reason, Timestamp createTime, Timestamp updateTime) {
        this.id = id;
        this.title = title;
        this.sign = sign;
        this.content = content;
        this.state = state;
        this.reason = reason;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
