package com.admin.model;

/**
 * Created by Administrator on 2016/10/11.
 */
public class PayCardQuota {
    private String sysId;    //sys_id
    private String quotaName;    //配额名称
    private Integer quotaType;    //配额类型
    private Integer cardType;    //银行卡类型
    private String cardTypeMc;    //银行卡类型 名称
    private Integer quotaOne;    //单笔
    private Integer quotaDay;    //单日
    private Integer quotaMonth;    //单月

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getQuotaName() {
        return quotaName;
    }

    public void setQuotaName(String quotaName) {
        this.quotaName = quotaName;
    }

    public Integer getQuotaType() {
        return quotaType;
    }

    public void setQuotaType(Integer quotaType) {
        this.quotaType = quotaType;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public Integer getQuotaOne() {
        return quotaOne;
    }

    public void setQuotaOne(Integer quotaOne) {
        this.quotaOne = quotaOne;
    }

    public Integer getQuotaDay() {
        return quotaDay;
    }

    public void setQuotaDay(Integer quotaDay) {
        this.quotaDay = quotaDay;
    }

    public Integer getQuotaMonth() {
        return quotaMonth;
    }

    public void setQuotaMonth(Integer quotaMonth) {
        this.quotaMonth = quotaMonth;
    }

    public String getCardTypeMc() {
        return cardTypeMc;
    }

    public void setCardTypeMc(String cardTypeMc) {
        this.cardTypeMc = cardTypeMc;
    }
}
