package com.receive;

import com.admin.model.Order;
import com.admin.service.OrderService;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.core.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/10/8.
 */
@Controller
@RequestMapping(value = "/receive/yunPay")
public class RecYunPayController {
    @Resource
    private OrderService orderService;

    /**
     * 获取最近生成的订单信息
     */
    @RequestMapping(value = "getOrderInfo")
    @ResponseBody
    public void getOrderInfo(HttpServletRequest request,HttpServletResponse response) {
        try{
            java.util.HashMap map = getClientPostMap(request);
            if(map == null){
                returnDataClient(response,null,"获取最近生成的订单信息失败",0);
                return ;
            }

            MapGetterTool dataMap = new MapGetterTool(map);
            String orderId = dataMap.getString("orderId");
            /**
             * 读取 Redis 数据
             * 查询库中数据 , 确认订单 , 金额
             */
            java.util.Map retMap = CommRedisFun.getHGetAll(orderId);
            //直接从redis读取数据
            if(retMap != null){
                if(retMap.size()>0){
                    System.out.println("==========================从redis获取扫码订单信息========================");
                    returnDataClient(response, retMap, "读取扫码订单信息成功", 1);
                }else{
                    //如果redis过期，则读取数据库的订单信息  2表示这笔订单是扫码
                    System.out.println("==========================从数据库获取扫码订单信息========================");
                    List<Order> list = orderService.getOrderByNumberAndOrderType(orderId,2);
                    if(list.size() > 0){
                        retMap.put("orderId",list.get(0).getOrderNumber());
                        retMap.put("mYId",list.get(0).getMerchantYunId());
                        retMap.put("fee",FunUtil.fenToYuan(list.get(0).getTotalFee()));
                        retMap.put("timeout",list.get(0).getCreateTime().getTime()+900000);//15分钟失效
                        returnDataClient(response, retMap, "读取扫码订单信息成功", 1);
                    }else{
                        returnDataClient(response, retMap, "订单信息不存在"+dataMap.getString("orderId"), 0);
                    }
                }
            }
            return ;
        }catch (Exception e ){
            e.printStackTrace();
        }
        returnDataClient(response,null,"getOrderInfo====失败",0);
    }

    /**
     * POS扫码消息通知
     */
    @RequestMapping(value = "msgNotice")
    @ResponseBody
    public void msgNotice(HttpServletRequest request,HttpServletResponse response) {
        try{
            java.util.HashMap map = getClientPostMap(request);
            if(map == null){
                returnDataClient(response,null,"POS扫码消息通知失败",0);
                return ;
            }
            /**
             * 验证签名
             */
            MapGetterTool mapGetterTool = new MapGetterTool(map);
            StringBuffer stringBuffer = new StringBuffer();
            List<String> list=new ArrayList<String>(mapGetterTool.getMap("data").keySet());
            Collections.sort(list);
            //付款通知
            MapGetterTool mapData = new MapGetterTool( mapGetterTool.getMap("data"));
            if("payNotice".equals(mapData.getString("noticeType"))){
                map.put("action",mapData.getString("noticeType"));
                for (String key:list){
                    stringBuffer.append(mapData.getString(key)).append("&");
                }
            }else if("cancelPayNotice".equals(mapData.getString("noticeType"))){
                //取消付款通知
                map.put("action",mapData.getString("noticeType"));
                for (String key:list){
                    stringBuffer.append(mapData.getString(key)).append("&");
                }
            }
            stringBuffer.append(CommConstant.yunRechargeDesKey);
            String sign = MD5Util.encodeByMD5Base64(stringBuffer.toString());

            if(sign.equals(mapGetterTool.getString("sign"))){
                System.out.println("===========================云支付回调POS扫码消息通知进行验证成功==========================");
                CommAppVo commAppVo = orderService.updateScanCodeOrder(mapData);
                if(commAppVo.getStatusCode() == 1) {
                    returnDataClient(response, commAppVo.getHashMap(), commAppVo.getMessage(), commAppVo.getStatusCode());
                    return ;
                }
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        returnDataClient(response,null,"POS扫码消息通知失败",0);
    }

    /**
     * 刷卡云支付确认回调
     */
    @RequestMapping(value = "confirm")
    @ResponseBody
    public void confirm(HttpServletRequest request,HttpServletResponse response) {
        System.out.println("===========================刷卡云支付确认回调==========================");
        String retStr = "";
        int compareOkNum = 0;
        try{
            String data = AppFunUtil.getClientPostStr(request,"UTF-8");
            String req = data.toString();//请求数据
            //解密
            req = DesUtil.decrypt(req, CommConstant.yunRecYunDataDesKey);
            JSONObject json = JSON.parseObject(req);
            /**
             * 查询库中数据 , 确认订单 , 金额
             */
            if(json == null){
                returnDataClient(response,null,"云支付确认订单信息失败",0);
                return ;
            }

            String amount = json.getString("amount");
            String orderId = json.getString("orderId");//订单号
            String merLoginName = json.getString("merLoginName");
            String commission = json.getString("commission");
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(amount).append("&").append(orderId).append("&").append(merLoginName).append("&").append(commission).append("&").append(CommConstant.yunRechargeDesKey);
            String verifySign = MD5Util.encodeByMD5Base64(stringBuffer.toString());

            //验证 签名 是否正确
            if(getString(verifySign).equals(json.getString("sign"))) {
                java.util.Map retMap = CommRedisFun.getHGetAll(orderId);
                //直接从redis读取过期
                if (retMap != null) {
                    MapGetterTool mapGetterTool =new MapGetterTool(retMap);
                    if (getString(orderId).equals(mapGetterTool.getString("orderId"))) {
                        compareOkNum++;
                    }
                    if (getString(merLoginName).equals(mapGetterTool.getString("merLoginName"))) {
                        compareOkNum++;
                    }
                    if (getString(amount + "").equals(mapGetterTool.getString("amount"))) {
                        compareOkNum++;
                    }
                    if (getString(commission + "").equals(mapGetterTool.getString("commission"))) {
                        compareOkNum++;
                    }
                }
            }
            //验证3次比较是否都成功
            if (compareOkNum == 4) {
                returnDataClient(response,null,"云支付回调验证成功",1);
            }else{
                returnDataClient(response,null,"刷卡云支付确认回调失败",0);
            }
        }catch (Exception e ){
            e.printStackTrace();
            returnDataClient(response,null,"exception",0);
        }
    }
    public static java.util.HashMap getClientPostMap(HttpServletRequest request) {
        try{
            return AppFunUtil.getClientPostMap(request,CommConstant.yunRecYunDataDesKey);
        }catch (Exception e ){

        }
        return null;
    }

    public static void returnDataClient(HttpServletResponse response,Object obj,String message,Integer statusCode) {
        try{
            AppFunUtil.returnDataClient(response,obj,message,statusCode, CommConstant.yunRecYunDataDesKey);
        }catch (Exception e ){

        }
    }

    public static String getString(String str){
        try{
            if(StringUtils.isEmpty(str)){
                return "";
            }else{
                return str;
            }
        }catch (Exception e ){

        }
        return "";
    }

}
