package com.admin.model;

/**
 * Created by Administrator on 2017/3/9.
 */
public class OrderDetail {
    private String orderNumber;    //订单编号
    private String createTime;    //创建时间
    private String machineCode;    //机器码
    private String deviceName;//设备名称
    private String merchantName;//商户姓名
    private String merchantYunId;//商户云支付帐号
    private String yunId;    //云支付帐号
    private String totalFee;    //实付金额
    private String cardNo;    //交易卡号
    private String orderType;    //订单类型
    private String payState;    //支付状态
    private String payTime;    //支付时间
    private String payType;    //支付方式
    private String lklMerchantCode;//拉卡拉商户号
    private String lklTerminalCode;//拉卡拉终端号
    private String refernumber;//拉卡拉参考号
    private String tradeType;//拉卡拉交易类型
    private String batchbillno;//拉卡拉批次号
    private String systraceno;//拉卡拉凭证号
    private String counterFee;//手续费
    private String realName;//代理人

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCounterFee() {
        return counterFee;
    }

    public void setCounterFee(String counterFee) {
        this.counterFee = counterFee;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantYunId() {
        return merchantYunId;
    }

    public void setMerchantYunId(String merchantYunId) {
        this.merchantYunId = merchantYunId;
    }

    public String getYunId() {
        return yunId;
    }

    public void setYunId(String yunId) {
        this.yunId = yunId;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
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
}
