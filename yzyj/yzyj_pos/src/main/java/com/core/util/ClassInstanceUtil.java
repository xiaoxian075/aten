package com.core.util;

import com.admin.model.Order;
import com.admin.model.OrderDetail;
import com.admin.model.OrderPosDetail;
import com.admin.model.OrderPosDetailModel;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2016/11/15.
 */
public class ClassInstanceUtil {

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

    public static OrderPosDetailModel createOrderPosDetailModel(OrderPosDetail orderPosDetail) {
        OrderPosDetailModel orderPosDetailModel = new OrderPosDetailModel();
        if(orderPosDetail.getCount() == null){
            orderPosDetailModel.setCount("0.00");
        }else{
            orderPosDetailModel.setCount(FunUtil.fenToYuan(orderPosDetail.getCount()));
        }
        orderPosDetailModel.setPayTime(orderPosDetail.getPayTime());
        if(orderPosDetail.getDayMoney() == null){
            orderPosDetailModel.setDayMoney("0.00");
        }else{
            orderPosDetailModel.setDayMoney(FunUtil.fenToYuan(orderPosDetail.getDayMoney()));
        }
        String type = orderPosDetail.getType();
        if(type.equals("Y01")){
            orderPosDetailModel.setType("扫码支付");
        }else if(type.equals("00")){
            orderPosDetailModel.setType("借记卡");
        }else if(type.equals("01")){
            orderPosDetailModel.setType("贷记卡");
        }
        orderPosDetailModel.setTime(orderPosDetail.getTime().substring(11,19));
        orderPosDetailModel.setOrderNumber(orderPosDetail.getOrderNumber());
        return orderPosDetailModel;
    }

}
