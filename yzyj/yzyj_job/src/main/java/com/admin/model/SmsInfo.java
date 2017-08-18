package com.admin.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class SmsInfo implements Serializable {
    private static final long serialVersionUID = 1523491741770193584L;

    private Long id;
    private Integer totalCount;    //条数
    private BigDecimal totalFee;      //费用
    private String unit;        //币种
    private String text;        //内容
    private Timestamp createTime;

    public SmsInfo() {}

    public SmsInfo(Long id, Integer totalCount, BigDecimal totalFee, String unit, String text, Timestamp createTime) {
        this.id = id;
        this.totalCount = totalCount;
        this.totalFee = totalFee;
        this.unit = unit;
        this.text = text;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
