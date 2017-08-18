package com.admin.service.impl;

import com.admin.dao.PayInfoDaoMapper;
import com.admin.model.BaseExample;
import com.admin.model.Order;
import com.admin.model.PayInfo;
import com.admin.service.OrderService;
import com.admin.service.PayInfoService;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import com.core.util.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
@Service
public class PayInfoServiceImpl extends GenericServiceImpl<PayInfo, String> implements PayInfoService {
    @Resource
    private PayInfoDaoMapper payInfoDaoMapper;
    @Resource
    private OrderService orderService;
    @Override
    public GenericDao<PayInfo, String> getDao() {
        return payInfoDaoMapper;
    }


    public List<PayInfo> selectByExampleAndPage(DataTablesPage<PayInfo> page, BaseExample baseExample){
        return payInfoDaoMapper.selectByExampleAndPage(page,baseExample);
    }
    public List<PayInfo> selectByExample(BaseExample baseExample){
        return payInfoDaoMapper.selectByExample(baseExample);
    }


    /**
     * 更新付款信息
     * @param dataMap
     * @return
     */
    public java.util.HashMap updatePayInfo(java.util.Map<String, String> dataMap){
        java.util.HashMap retMap = new java.util.HashMap();
        try{
            PayInfo payInfo = new PayInfo();
            payInfo.setMchId(dataMap.get("mch_id"));/*商户号*/
            payInfo.setDeviceInfo(dataMap.get("device_info"));        /*设备号*/
            payInfo.setNonceStr(dataMap.get("nonce_str"));          /*随机字符串*/
            payInfo.setSign( dataMap.get("sign"));               /*签名*/
            payInfo.setResultCode(dataMap.get("result_code"));        /*业务结果*/
            payInfo.setErrCode( dataMap.get("err_code"));           /*错误代码*/
            payInfo.setErrCodeDes( dataMap.get("err_code_des"));       /*错误代码描述*/
            payInfo.setOpenid(dataMap.get("openid"));             /*用户标识*/
            payInfo.setTradeType( dataMap.get("trade_type"));         /*交易类型*/
            payInfo.setTotalFee(FunUtil.stringToInteger(dataMap.get("total_fee")));          /*总金额*/
            payInfo.setTransactionId(dataMap.get("transaction_id"));     /*商户支付订单号*/
            payInfo.setOutTradeNo(dataMap.get("out_trade_no"));       /*商户订单号*/
            payInfo.setAttach(dataMap.get("attach"));             /*商家数据包*/
            payInfo.setTimeEnd(FunUtil.stringToDateFormat(dataMap.get("time_end"),"yyyyMMddHHmmss"));           //支付完成时间
            payInfo.setPayType( dataMap.get("pay_type"));           //支付方式
            payInfo.setCardNo(dataMap.get("card_no"));            //交易卡号
            payInfo.setPayAmt(FunUtil.stringToInteger(dataMap.get("pay_amt")));            //支付金额
            payInfo.setBatchbillno( dataMap.get("batchbillno"));        //批次号
            payInfo.setSystraceno( dataMap.get("systraceno"));         //凭证号
            payInfo.setOrderidScan(dataMap.get("orderid_scan"));       //扫码订单号
            payInfo.setRefernumber( dataMap.get("refernumber"));        //系统参考号
            payInfo.setBankType( dataMap.get("bank_type"));          //付款银行
            payInfo.setFeeType( dataMap.get("fee_type"));           //货币种类
            payInfo.setCashFee(FunUtil.stringToInteger(dataMap.get("cash_fee")));           //现金支付金额
            payInfo.setCashFeeType(dataMap.get("cash_fee_type"));      //现金支付货币类型
            payInfo.setCouponFee(FunUtil.stringToInteger(dataMap.get("coupon_fee")));         //代金券或立减优惠金额
            payInfo.setCouponCount(FunUtil.stringToInteger(dataMap.get("coupon_count")));       //代金券或立减优惠使用数量
            payInfo.setCouponId(dataMap.get("coupon_id_$n"));       //代金券或立减优惠ID
            payInfo.setCouponFeeN(FunUtil.stringToInteger(dataMap.get("coupon_fee_$n")));      //单个代金券或立减优惠支付金额
            payInfo.setIsSubscribe(dataMap.get("is_subscribe"));       //是否关注公众账号
            /**
             * 检查 订单是否已存在
             *      存在 不新增 ,不处理
             *      不存在 新增订单
             */
            insertSelective(payInfo);
            /**
             * 检查 是否已付款成功 推送状态 payInfo SUCCESS
             * 标志订单已付款
             */
            String retCode = payInfo.getResultCode();
            if(StringUtils.isEmpty(retCode)){
                retCode = "";
            }
            if("SUCCESS".equals(retCode.toUpperCase())){
                updateOrderPayState(payInfo);
                System.out.println("===========================成功接收拉卡拉的通知,用户已经刷卡完成支付==========================");
            }
            retMap.put("return_code", "SUCCESS");
            retMap.put("return_msg", "ok");

        }catch (Exception e ){
            e.printStackTrace();
        }
        return retMap;
    }

    /**
     * 线程锁 , 更新订单状态
     * @param payInfo
     * @return
     */
    private synchronized Integer updateOrderPayState(PayInfo payInfo){
        Integer state = 0;
        try{
            Order order = new Order();
            order.setOrderNumber(payInfo.getOutTradeNo());
            order.setPayType(payInfo.getPayType());
            order.setCardNo(payInfo.getCardNo());
            order.setPayTime(payInfo.getTimeEnd());
            /**
             * 收到成功通知，不马上改支付的状态，防止冲正订单给用户充值
             */
            //order.setPayState(1);
            order.setOrderState(1);
            orderService.updateOrderPayState(order);
        }catch (Exception e ){
            e.printStackTrace();
        }
        return state;
    }

    private Integer checkOrderInfoData(Order order){
        Integer state = 999;
        try{
            /**
             * 余额出错
             */
            if(order.getTotalFee() == null){
                order.setTotalFee(0);
                state = 90;
                return state;
            }
            /**
             * 总额出错
             */
            if(order.getCounterFee() == null){
                order.setCounterFee(0);
                state = 91;
                return state;
            }
            /**
             * 支付时间出错
             */
            if(order.getPayTime() == null){
                state = 93;
                return state;
            }
            /**
             * 订单编号
             */
            if(order.getOrderNumber() == null){
                state = 94;
                return state;
            }
            /**
             * 商户云支付账号
             */
            if(order.getMerchantYunId() == null){
                state = 95;
                return state;
            }
            /**
             * 支付者云支付账号
             */
            if(order.getYunId() == null){
                state = 96;
                return state;
            }
            state = 1;
        }catch (Exception e ){
            e.printStackTrace();
        }
        return state;
    }

    public Integer updateOrderInfo(){
        Integer state = 0 ;
        Integer isErrorState = 0;
        try {
            BaseExample baseExample = new BaseExample();
            baseExample.createCriteria().andEqualTo("order_state","1").andEqualTo("pay_state","1").andEqualTo("order_type","1");
            List<Order> list =orderService.selectByExample(baseExample);
            if(list != null && list.size() >0) {
                for (Order order:list){
                    String body = "";
                    java.util.HashMap map = new java.util.HashMap();

                    /**
                     * 检查数据 有效 , 返回对应状态
                     */
                    Integer checkState = checkOrderInfoData(order);
                    if( checkState != 1){
                        //数据错误 , 修改订单为对应状态
                        order.setOrderState(checkState);
                        orderService.updateOrderPushState(order);
                    }

                    //总额  减去 ,手续费
                    map.put("amount",order.getTotalFee());
                    map.put("payTime",order.getPayTime().getTime());
                    map.put("orderId",order.getOrderNumber());
                    map.put("merLoginName",order.getMerchantYunId());
                    map.put("loginName",order.getYunId());
                    map.put("commission",order.getCounterFee());
                    CommRedisFun.setKeyExpire(order.getOrderNumber(),map,60);

                    String sign = map.get("amount")+"&"+order.getPayTime().getTime()+"&"+order.getOrderNumber()+"&"+order.getMerchantYunId()+"&"+order.getCounterFee()+"&"+ CommConstant.yunRechargeDesKey;
                    System.out.println(sign);
                    sign = MD5Util.encodeByMD5Base64(sign);
                    map.put("sign",sign);
                    body = JSONObject.toJSONString(map);
                    body = DesUtil.encrypt(body, CommConstant.yunRecYunDataDesKey);
                    String retBody = HttpPostClient.getPostData(CommConstant.yunRechargeUrl,body,"UTF-8");
                    MapGetterTool  mapGetterTool = new MapGetterTool(AppDesUtil.decryptDataToMap(retBody,CommConstant.yunRecYunDataDesKey));

                    if("1".equals(mapGetterTool.getString("statusCode"))){
                        order.setOrderState(2);
                        orderService.updateOrderPushState(order);
                    }else{
                        isErrorState = 1;
                        if(mapGetterTool.getInteger("statusCode") == 0){
                            order.setOrderState(99);
                        }else{
                            order.setOrderState(mapGetterTool.getInteger("statusCode"));
                        }

                        orderService.updateOrderPushState(order);
                        Log.exception(mapGetterTool.getString("message")+mapGetterTool.getString("statusCode"));
                    }
                }
            }
            if(isErrorState == 0){
                state = 1;
            }else{
                state = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            state = 0;
        }
        return state;
    }
}
