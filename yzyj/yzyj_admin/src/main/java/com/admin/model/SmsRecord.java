package com.admin.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class SmsRecord implements Serializable {
    private static final long serialVersionUID = 1523491741770193584L;

    private Long id;
    private Long parentId;
    private String sid;
    private String mobile;
    private Integer count;
    private BigDecimal fee;
    private Integer state;
    private String msg;
    private Timestamp createTime;

    public SmsRecord() {
    }

    public SmsRecord(Long id, Long parentId, String sid, String mobile, Integer count, BigDecimal fee, Integer state, String msg, Timestamp createTime) {
        this.id = id;
        this.parentId = parentId;
        this.sid = sid;
        this.mobile = mobile;
        this.count = count;
        this.fee = fee;
        this.state = state;
        this.msg = msg;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
