package com.core.util;

import com.admin.model.*;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2016/12/8.
 */
public class ClassInstanceUtil {

    public static WithdrawModel createWithdrawModel(AgentTotalChange agentTotalChange) {
        WithdrawModel withdrawModel = new WithdrawModel();
        withdrawModel.setMoney(FunUtil.fenToYuan(agentTotalChange.getTotalFee()));
        withdrawModel.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(agentTotalChange.getCreateTime()));
        return withdrawModel;
    }

    public static InComeRecordModel createInComeRecordModel(InComeRecord inComeRecord) {
        InComeRecordModel inComeRecordModel = new InComeRecordModel();
        inComeRecordModel.setMoney(FunUtil.fenToYuan(inComeRecord.getMoney()));
        if(inComeRecord.getType() == 1){
            inComeRecordModel.setType("收益");
        }if(inComeRecord.getType() == 2){
            inComeRecordModel.setType("补贴");
        }if(inComeRecord.getType() == 3){
            inComeRecordModel.setType("返现");
        }
        inComeRecordModel.setCheckDay(inComeRecord.getCheckDay());
        return inComeRecordModel;
    }

    public static DeviceDetailModel createDeviceDetailModel(DeviceList deviceList) {
        DeviceDetailModel deviceDetailModel = new DeviceDetailModel();
        deviceDetailModel.setDeviceCode(deviceList.getDeviceCode());
        if(deviceList.getDayCount() == null){
            deviceDetailModel.setDayCount("0.00");
        }else{
            deviceDetailModel.setDayCount(FunUtil.fenToYuan(deviceList.getDayCount()));
        }
        deviceDetailModel.setDeviceName(deviceList.getDeviceName());
        deviceDetailModel.setMerchantYunPayAccount(deviceList.getMerchantYunPayAccount());
        deviceDetailModel.setMerchantPhone(deviceList.getMerchantPhone());
        deviceDetailModel.setActivationCode(deviceList.getActivationCode());
        deviceDetailModel.setMerchantName(deviceList.getMerchantName());
        if(deviceList.getMoneyCount() == null){
            deviceDetailModel.setMoneyCount("0.00");
        }else{
            deviceDetailModel.setMoneyCount(FunUtil.fenToYuan(deviceList.getMoneyCount()));
        }
        int state = Integer.parseInt(deviceList.getState());
        if(state == 0){
            deviceDetailModel.setState("禁用");
        }else{
            deviceDetailModel.setState("启用");
        }
        deviceDetailModel.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(deviceList.getCreateTime()));
        deviceDetailModel.setLklMerchantCode(deviceList.getLklMerchantCode());
        deviceDetailModel.setLklMerminalCode(deviceList.getLklMerminalCode());
        return deviceDetailModel;
    }

    public static OrderDetailModel createOrderDetailModel(OrderDetail orderDetail) {
        OrderDetailModel orderDetailModel = new OrderDetailModel();
        if(orderDetail.getCount() == null){
            orderDetailModel.setCount("0.00");
        }else{
            orderDetailModel.setCount(FunUtil.fenToYuan(orderDetail.getCount()));
        }
        orderDetailModel.setPayTime(orderDetail.getPayTime());
        if(orderDetail.getDayMoney() == null){
            orderDetailModel.setDayMoney("0.00");
        }else{
            orderDetailModel.setDayMoney(FunUtil.fenToYuan(orderDetail.getDayMoney()));
        }
        String type = orderDetail.getType();
        if(type.equals("Y01")){
            orderDetailModel.setType("扫码支付");
        }else if(type.equals("00")){
            orderDetailModel.setType("借记卡");
        }else if(type.equals("01")){
            orderDetailModel.setType("贷记卡");
        }
        orderDetailModel.setTime(orderDetail.getTime().substring(11,19));
        return orderDetailModel;
    }

    public static MerchantInfoModel createMerchantInfoModel(DeviceList deviceList) {
        MerchantInfoModel merchantInfoModel = new MerchantInfoModel();
        if(deviceList.getmDayCount() == null){
            merchantInfoModel.setDayMoney("0.00");
        }else{
            merchantInfoModel.setDayMoney(FunUtil.fenToYuan(deviceList.getmDayCount()));
        }
        if(deviceList.getmMonthCount() == null){
            merchantInfoModel.setMonthMoney("0.00");
        }else{
            merchantInfoModel.setMonthMoney(FunUtil.fenToYuan(deviceList.getmMonthCount()));
        }
        if(deviceList.getmHistoryCount() == null){
            merchantInfoModel.setHistoryMoney("0.00");
        }else{
            merchantInfoModel.setHistoryMoney(FunUtil.fenToYuan(deviceList.getmHistoryCount()));
        }
        merchantInfoModel.setName(deviceList.getMerchantName());
        merchantInfoModel.setmPhone(deviceList.getMerchantPhone());
        merchantInfoModel.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(deviceList.getCreateTime()));
        merchantInfoModel.setMyId(deviceList.getMerchantYunPayAccount());
        merchantInfoModel.setMerchantCode(deviceList.getLklMerchantCode());
        return merchantInfoModel;
    }
}
