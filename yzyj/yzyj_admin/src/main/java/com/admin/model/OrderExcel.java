package com.admin.model;

/**
 * Created by Administrator on 2016/11/15.
 */
public class OrderExcel {

    private String orderNumber;    //订单编号
    private String createTime;    //创建时间
    private String machineCode;    //机器码
    private String deviceName;      //设备名称
    private String merchantName;    //商户姓名
    private String merchantYunId;   //商户云支付帐号
    private String yunId;    //云支付帐号
    private String totalFee;    //实付金额
    private String cardNo;    //交易卡号
    private String orderType;    //订单类型
    private String payState;    //支付状态
    private String payTime;    //支付时间
    private String payType;    //支付方式
    private String realName;    //代理人
    private String counterFee;//手续费
    private String lklTerminalCode;//终端号

    public String getLklTerminalCode() {
        return lklTerminalCode;
    }

    public void setLklTerminalCode(String lklTerminalCode) {
        this.lklTerminalCode = lklTerminalCode;
    }

    public String getCounterFee() {
        return counterFee;
    }

    public void setCounterFee(String counterFee) {
        this.counterFee = counterFee;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
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

    public void setPayTime(String payTime) {this.payTime = payTime;}
}
