package com.admin.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by wjf on 2016/10/10.
 */
public class Order extends CommModel {
    private String sysId;    //sys_id
    private java.util.Date createTime;    //创建时间
    private String orderNumber;    //订单编号
    private String yunId;    //云支付帐号
    private String machineCode;    //机器码
    private Integer totalFee;    //总金额
    private Integer realAmount;    //实付金额
    private java.util.Date payTime;    //支付时间
    private String payType;    //支付方式
    private String cardNo;    //交易卡号
    private String cardType;    //交易卡号
    private Integer orderType;    //订单类型
    private Integer orderState;    //订单状态
    private Integer payState;    //支付状态
    private String merchantName;
    private String deviceName;
    private String deviceUnique;
    private String agentUnique;
    private String merchantYunId;
    private String lklMerchantCode;
    private String lklTerminalCode;
    private String umengToken;
    private Integer counterFee;
    private Integer excelType;
    private String lklPayTime;
    private String refernumber;//拉卡拉参考号
    private String tradeType;//拉卡拉交易类型
    private String batchbillno;//拉卡拉批次号
    private String systraceno;//拉卡拉凭证号
    private Integer excessAmount;//超额的费用
    private String realName;//代理人
    private Integer searchType;//搜索类型
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sdate;    //支付开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date edate;    //支付结束时间
    private Integer errorState;
    private Integer appPayState;
    private Date appPayTime;
    private String strSdate;
    private String strEdate;

    public Integer getSearchType() {
        return searchType;
    }

    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getExcessAmount() {
        return excessAmount;
    }

    public void setExcessAmount(Integer excessAmount) {
        this.excessAmount = excessAmount;
    }

    public String getRefernumber() {
        return refernumber;
    }

    public void setRefernumber(String refernumber) {
        this.refernumber = refernumber;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getBatchbillno() {
        return batchbillno;
    }

    public void setBatchbillno(String batchbillno) {
        this.batchbillno = batchbillno;
    }

    public String getSystraceno() {
        return systraceno;
    }

    public void setSystraceno(String systraceno) {
        this.systraceno = systraceno;
    }

    public String getLklPayTime() {
        return lklPayTime;
    }

    public void setLklPayTime(String lklPayTime) {
        this.lklPayTime = lklPayTime;
    }

    public Integer getExcelType() {
        return excelType;
    }

    public void setExcelType(Integer excelType) {
        this.excelType = excelType;
    }

    public String getStrSdate() {
        return strSdate;
    }

    public void setStrSdate(String strSdate) {
        this.strSdate = strSdate;
    }

    public String getStrEdate() {
        return strEdate;
    }

    public void setStrEdate(String strEdate) {
        this.strEdate = strEdate;
    }

    public String getLklMerchantCode() {
        return lklMerchantCode;
    }

    public void setLklMerchantCode(String lklMerchantCode) {
        this.lklMerchantCode = lklMerchantCode;
    }

    public String getLklTerminalCode() {
        return lklTerminalCode;
    }

    public void setLklTerminalCode(String lklTerminalCode) {
        this.lklTerminalCode = lklTerminalCode;
    }

    public Integer getErrorState() {
        return errorState;
    }

    public void setErrorState(Integer errorState) {
        this.errorState = errorState;
    }

    public Integer getAppPayState() {
        return appPayState;
    }

    public void setAppPayState(Integer appPayState) {
        this.appPayState = appPayState;
    }

    public Date getAppPayTime() {
        return appPayTime;
    }

    public void setAppPayTime(Date appPayTime) {
        this.appPayTime = appPayTime;
    }

    public Date getSdate() {
        return sdate;
    }

    public void setSdate(Date sdate) {
        this.sdate = sdate;
    }

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }
    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Integer getOrderStateW() {
        return orderStateW;
    }

    public void setOrderStateW(Integer orderStateW) {
        this.orderStateW = orderStateW;
    }

    public Integer getPayStateW() {
        return payStateW;
    }

    public void setPayStateW(Integer payStateW) {
        this.payStateW = payStateW;
    }

    private Integer orderStateW;    //订单状态
    private Integer payStateW;    //支付状态


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getYunId() {
        return yunId;
    }

    public void setYunId(String yunId) {
        this.yunId = yunId;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public Integer getPayState() {
        return payState;
    }

    public void setPayState(Integer payState) {
        this.payState = payState;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceUnique() {
        return deviceUnique;
    }

    public void setDeviceUnique(String deviceUnique) {
        this.deviceUnique = deviceUnique;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getAgentUnique() {
        return agentUnique;
    }

    public void setAgentUnique(String agentUnique) {
        this.agentUnique = agentUnique;
    }

    public String getMerchantYunId() {
        return merchantYunId;
    }

    public void setMerchantYunId(String merchantYunId) {
        this.merchantYunId = merchantYunId;
    }

    public String getUmengToken() {
        return umengToken;
    }

    public void setUmengToken(String umengToken) {
        this.umengToken = umengToken;
    }

    public Integer getCounterFee() {
        return counterFee;
    }

    public void setCounterFee(Integer counterFee) {
        this.counterFee = counterFee;
    }

    public Integer getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(Integer realAmount) {
        this.realAmount = realAmount;
    }
}
