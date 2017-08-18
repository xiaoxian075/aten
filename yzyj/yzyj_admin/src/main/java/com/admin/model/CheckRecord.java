package com.admin.model;

import java.util.Date;

/**
 * Created by Administrator on 2016/11/9.
 */
public class CheckRecord extends CommModel{
    private int id;
    private int orderNum;
    private int accountCount;
    private Date checkDate;
    private int lklCheckStatus;
    private int ypCheckStatus;
    private String checkUnique;
    private String lklCheckResult;
    private String ypCheckResult;
    private String checkDay;
    private String checkDayStr;
    private String checkDayOne;
    private int ypOrderNum;
    private int ypAccountCount;
    private int type;

    public String getCheckDayOne() {
        return checkDayOne;
    }

    public void setCheckDayOne(String checkDayOne) {
        this.checkDayOne = checkDayOne;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getYpOrderNum() {
        return ypOrderNum;
    }

    public void setYpOrderNum(int ypOrderNum) {
        this.ypOrderNum = ypOrderNum;
    }

    public int getYpAccountCount() {
        return ypAccountCount;
    }

    public void setYpAccountCount(int ypAccountCount) {
        this.ypAccountCount = ypAccountCount;
    }

    public String getCheckDayStr() {
        return checkDayStr;
    }

    public void setCheckDayStr(String checkDayStr) {
        this.checkDayStr = checkDayStr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCheckDay() {
        return checkDay;
    }

    public void setCheckDay(String checkDay) {
        this.checkDay = checkDay;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public int getAccountCount() {
        return accountCount;
    }

    public void setAccountCount(int accountCount) {
        this.accountCount = accountCount;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public int getLklCheckStatus() {
        return lklCheckStatus;
    }

    public void setLklCheckStatus(int lklCheckStatus) {
        this.lklCheckStatus = lklCheckStatus;
    }

    public int getYpCheckStatus() {
        return ypCheckStatus;
    }

    public void setYpCheckStatus(int ypCheckStatus) {
        this.ypCheckStatus = ypCheckStatus;
    }

    public String getCheckUnique() {
        return checkUnique;
    }

    public void setCheckUnique(String checkUnique) {
        this.checkUnique = checkUnique;
    }

    public String getLklCheckResult() {
        return lklCheckResult;
    }

    public void setLklCheckResult(String lklCheckResult) {
        this.lklCheckResult = lklCheckResult;
    }

    public String getYpCheckResult() {
        return ypCheckResult;
    }

    public void setYpCheckResult(String ypCheckResult) {
        this.ypCheckResult = ypCheckResult;
    }
}
