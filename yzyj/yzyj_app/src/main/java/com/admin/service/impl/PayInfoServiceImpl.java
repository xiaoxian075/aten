package com.admin.service.impl;

import com.admin.dao.DeviceInfoDaoMapper;
import com.admin.dao.PayInfoDaoMapper;
import com.admin.model.BaseExample;
import com.admin.model.DeviceInfo;
import com.admin.model.Order;
import com.admin.model.PayInfo;
import com.admin.service.DeviceInfoService;
import com.admin.service.OrderService;
import com.admin.service.PayInfoService;
import com.alibaba.fastjson.JSONObject;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import com.core.util.*;
import org.apache.http.client.methods.HttpPost;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
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
            payInfo.setTimeEnd(FunUtil.stringToDateFormat(dataMap.get("time_end"),"yyyyMMddhhmmss"));           //支付完成时间
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
             * 标志订单已付款
             */
            updateOrderPayState(payInfo);
            //开启处理
            CommConstant.blnOrderJob = true;
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
            //payInfo.
            Order order = new Order();
            order.setOrderNumber(payInfo.getOutTradeNo());
            order.setPayType(payInfo.getPayType());
            order.setCardNo(payInfo.getCardNo());
            order.setPayTime(payInfo.getTimeEnd());
            order.setPayState(1);
        }catch (Exception e ){
            e.printStackTrace();
        }
        return state;
    }

}
