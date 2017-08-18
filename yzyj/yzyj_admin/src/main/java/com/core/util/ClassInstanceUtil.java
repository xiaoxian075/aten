package com.core.util;

import com.admin.model.*;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2016/11/15.
 */
public class ClassInstanceUtil {

    public static OrderExcel createOrderExcelModel(Order order,int type) {
        OrderExcel orderExcel = new OrderExcel();
        orderExcel.setOrderNumber(order.getOrderNumber());
        if(!StringUtils.isEmpty(order.getCreateTime())) {
            orderExcel.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getCreateTime()));
        }else{
            orderExcel.setCreateTime(null);
        }
        orderExcel.setMachineCode(order.getMachineCode());
        orderExcel.setDeviceName(order.getDeviceName());
        orderExcel.setMerchantName(order.getMerchantName());
        orderExcel.setMerchantYunId(order.getMerchantYunId());
        if(type == 1){
            orderExcel.setYunId(order.getYunId());
            orderExcel.setCardNo(order.getCardNo());
            orderExcel.setRealName(order.getRealName());
            if(order.getCounterFee() != null){
                orderExcel.setCounterFee(FunUtil.fenToYuan(order.getCounterFee()));
            }else{
                orderExcel.setCounterFee("0.00");
            }
            orderExcel.setLklTerminalCode(order.getLklTerminalCode());
        }
        orderExcel.setTotalFee(FunUtil.fenToYuan(order.getTotalFee()));
        switch (order.getOrderType().intValue()) {
            case 1:
                orderExcel.setOrderType("POS机付款");break;
            case 2:
                orderExcel.setOrderType("云支付付款");break;
           }
        switch (order.getPayState().intValue()) {
            case 0:
                orderExcel.setPayState("未支付");break;
            case 1:
                orderExcel.setPayState("已支付");break;
           }
        if(!StringUtils.isEmpty(order.getPayTime())) {
            orderExcel.setPayTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getPayTime()));
        }else{
            orderExcel.setPayTime(null);
        }
        switch (order.getPayType()) {
            case "00":
                orderExcel.setPayType("借记卡");break;
            case "01":
                orderExcel.setPayType("贷记卡");break;
            case "Y01":
                orderExcel.setPayType("云支付扫码");break;
        }
        return orderExcel;
    }

    public static DeviceListExcel createDeviceListExcelModel(DeviceList deviceList) {
        DeviceListExcel deviceListExcel = new DeviceListExcel();
        deviceListExcel.setRealName(deviceList.getRealName());
        deviceListExcel.setLklMerchantCode(deviceList.getLklMerchantCode());
        deviceListExcel.setLklTerminalCode(deviceList.getLklTerminalCode());
        deviceListExcel.setMerchantName(deviceList.getMerchantName());
        deviceListExcel.setMerchantYunPayAccount(deviceList.getMerchantYunPayAccount());
        deviceListExcel.setSubsidyTime(deviceList.getSubsidyTime());
        return deviceListExcel;
    }

    public static InComeRecordExcel createInComeExcelModel(InComeRecord inComeRecord) {
        InComeRecordExcel inComeRecordExcel = new InComeRecordExcel();
        inComeRecordExcel.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(inComeRecord.getTime()));
        inComeRecordExcel.setCheckDay(inComeRecord.getCheckDay());
        inComeRecordExcel.setMoney(FunUtil.fenToYuan(inComeRecord.getMoney()));
        inComeRecordExcel.setAgentName(inComeRecord.getAgentName());
        inComeRecordExcel.setAgentYPLoginName(inComeRecord.getAgentYPLoginName());
        switch (inComeRecord.getType()){
            case "1":
                inComeRecordExcel.setType("代理收益");break;
            case "2":
                inComeRecordExcel.setType("云智补贴");break;
            case "3":
                inComeRecordExcel.setType("购机返现");break;
        }
        return inComeRecordExcel;
    }

    public static OrderDetail createOrderDetailModel(Order order) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderNumber(order.getOrderNumber());
        orderDetail.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getCreateTime()));
        orderDetail.setMachineCode(order.getMachineCode());
        orderDetail.setDeviceName(order.getDeviceName());
        orderDetail.setMerchantName(order.getMerchantName());
        orderDetail.setMerchantYunId(order.getMerchantYunId());
        orderDetail.setYunId(order.getYunId());
        orderDetail.setTotalFee(FunUtil.fenToYuan(order.getTotalFee()));
        orderDetail.setLklMerchantCode(order.getLklMerchantCode());
        orderDetail.setLklTerminalCode(order.getLklTerminalCode());
        orderDetail.setCardNo(order.getCardNo());
        switch (order.getOrderType().intValue()) {
            case 1:
                orderDetail.setOrderType("POS机付款");break;
            case 2:
                orderDetail.setOrderType("云支付付款");break;
        }
        switch (order.getPayState().intValue()) {
            case 0:
                orderDetail.setPayState("未支付");break;
            case 1:
                orderDetail.setPayState("已支付");break;
        }
        orderDetail.setPayTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getPayTime()));
        switch (order.getPayType()) {
            case "00":
                orderDetail.setPayType("借记卡");break;
            case "01":
                orderDetail.setPayType("贷记卡");break;
            case "Y01":
                orderDetail.setPayType("云支付扫码");break;
        }
        orderDetail.setRefernumber(order.getRefernumber());
        orderDetail.setBatchbillno(order.getBatchbillno());
        orderDetail.setSystraceno(order.getSystraceno());
        orderDetail.setRealName(order.getRealName());
        switch (order.getTradeType()){
            case "012001":
                orderDetail.setTradeType("消费交易");break;
            case "012010":
                orderDetail.setTradeType("消费冲正交易");break;
            default :
                orderDetail.setTradeType("查看状态码");break;
        }
        if(order.getCounterFee() == null){
            orderDetail.setCounterFee("0.00");
        }else{
            orderDetail.setCounterFee(FunUtil.fenToYuan(order.getCounterFee()));
        }
        return orderDetail;
    }


    public static PosWithdrawRecordExcel createSendExcelModel(PosWithdrawRecord posWithdrawRecord) {
        PosWithdrawRecordExcel posWithdrawRecordExcel = new PosWithdrawRecordExcel();
        posWithdrawRecordExcel.setYftNumber(posWithdrawRecord.getYftNumber());
        posWithdrawRecordExcel.setLoginName(posWithdrawRecord.getLoginName());
        posWithdrawRecordExcel.setAmount(posWithdrawRecord.getAmount());
        posWithdrawRecordExcel.setBankAccount(posWithdrawRecord.getBankAccount());
        posWithdrawRecordExcel.setAccountName(posWithdrawRecord.getAccountName());
        posWithdrawRecordExcel.setBankName(posWithdrawRecord.getBankName());
        posWithdrawRecordExcel.setWithdrawTime(posWithdrawRecord.getWithdrawTime());
        posWithdrawRecordExcel.setAuditTime(posWithdrawRecord.getAuditTime());
        posWithdrawRecordExcel.setIsSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(posWithdrawRecord.getIsSendTime()));
        posWithdrawRecordExcel.setIsSendMoney(posWithdrawRecord.getIsSendMoney());
        posWithdrawRecordExcel.setOrderLogNo(posWithdrawRecord.getOrderLogNo());
        posWithdrawRecordExcel.setLklMerchantCode(posWithdrawRecord.getLklMerchantCode());
        posWithdrawRecordExcel.setLklTerminalCode(posWithdrawRecord.getLklTerminalCode());
        return posWithdrawRecordExcel;
    }

}
