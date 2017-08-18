package com.admin.model;

import java.util.Date;

/**
 * Created by wjf on 2016/10/20.
 */
public class AgencyPerson extends CommModel {
    private String id;
    private String agentUnique;
    private String realName;    //真实姓名
    private String phone;    //联系电话
    private String loginName; //登入名
    private String address;    //家庭住址
    private String papersType;    //验证类型
    private String papersNumber;    //验证类型码
    private String province;    //代理省份
    private String city;    //代理城市
    private String yunPayLoginName;    //云支付账号
    private String note;    //备注
    private Date joinTime;    //代理时间
    private Integer status;    //状态
    private Integer remainingSum;//剩余余额
    private Integer totalFee;//
    private Integer countAgent;

    public Integer getCountAgent() {
        return countAgent;
    }

    public void setCountAgent(Integer countAgent) {
        this.countAgent = countAgent;
    }

    public Integer getRemainingSum() {
        return remainingSum;
    }

    public void setRemainingSum(Integer remainingSum) {
        this.remainingSum = remainingSum;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPapersType() {
        return papersType;
    }

    public void setPapersType(String papersType) {
        this.papersType = papersType;
    }

    public String getPapersNumber() {
        return papersNumber;
    }

    public void setPapersNumber(String papersNumber) {
        this.papersNumber = papersNumber;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getYunPayLoginName() {
        return yunPayLoginName;
    }

    public void setYunPayLoginName(String yunPayLoginName) {
        this.yunPayLoginName = yunPayLoginName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getAgentUnique() {
        return agentUnique;
    }

    public void setAgentUnique(String agentUnique) {
        this.agentUnique = agentUnique;
    }
}
