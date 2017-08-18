package com.app;

import com.admin.model.*;
import com.admin.service.DeviceInfoService;
import com.admin.service.DeviceListService;
import com.admin.service.OrderService;
import com.admin.service.ReportService;
import com.core.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */

@Controller
@RequestMapping(value = "/app/report")
public class AppReportController {
    @Resource
    private DeviceInfoService deviceInfoService;
    @Resource
    private ReportService reportService;
    @Resource
    private OrderService orderService;
    @Resource
    private DeviceListService deviceListService;



    /**
     * 打印接口
     * 今天汇总 type
     * 昨天汇总
     * 补打最后一笔
     */
    @RequestMapping(value = "getPrintData", method = RequestMethod.POST)
    @ResponseBody
    public String getPrintData(HttpServletRequest request, String data) {
        try {
            /**
             * 获取 Map 对象
             */
            MapGetterTool hashMap = new MapGetterTool(AppFunUtil.getDataMap(data));
            if (hashMap == null) {
                return AppDesUtil.posAppDesEncrypt(null, "失败", 0);
            }
            /**
             * 验证是否登入 , 并返回对象
             */
            DeviceInfo deviceInfo = AppFunUtil.checkToken(request, deviceInfoService, hashMap.getString("token"));
            if (deviceInfo.getState() != 1) {
                return AppDesUtil.posAppDesEncrypt(null, deviceInfo.getNote(), deviceInfo.getState());
            }
            /**
             * type
             *  10 最后一笔
             *  11 汇总昨天
             *  12 汇总今天
             */
            Order order = new Order();
            order.setDeviceUnique(deviceInfo.getDeviceUnique());
            String type = hashMap.getString("type");
            if("10".equals(type)){
                /**
                 * 最后一笔
                 */
                List<Order> list = reportService.selectTodayLastOne(order);
                if(list.size() > 0){
                    Order order1 = list.get(0);
                    java.util.HashMap map = new java.util.HashMap();
                    map.put("merchantName",FunUtil.dealString(deviceInfo.getMerchantName()));//商户名称
                    map.put("lklMerchantCode",FunUtil.dealString(deviceInfo.getLklMerchantCode()));//商户编码
                    map.put("lklTerminalCode",FunUtil.dealString(deviceInfo.getLklTerminalCode()));//设备编码
                    map.put("merchantYunId",FunUtil.dealString(deviceInfo.getMerchantYunId())); //商户 帐号
                    map.put("payType",order1.getPayType()); //付款类型
                    map.put("payTypeMc",CommDictList.getDictMc("t_order_pay_type",order1.getPayType())); //付款类型名称
                    map.put("clientYunId",FunUtil.dealStrHide(order1.getYunId(),4));//顾客云ID
                    map.put("cardNo",FunUtil.dealStrHide(order1.getCardNo(),6));//顾客云ID
                    map.put("payTime",FunUtil.getFormatDate(order1.getPayTime(),"yyyy-MM-dd HH:mm:ss"));//付款时间
                    map.put("realAmount", FunUtil.fenToYuan(order1.getTotalFee()));//实付金额
                    map.put("orderAmount", FunUtil.fenToYuan(order1.getTotalFee()));//订单金额
                    map.put("preferentialPrice", (order1.getTotalFee() > order1.getRealAmount()  ) ? FunUtil.fenToYuan(order1.getTotalFee()-order1.getRealAmount()):0);//优惠金额
                    map.put("payState", 1);
                    map.put("type", type);
                    return AppDesUtil.posAppDesEncrypt(map,"成功",1);
                }else{
                    return AppDesUtil.posAppDesEncrypt(null,"当前无订单",0);
                }
            }else if("11".equals(type)){ //汇总昨天
                order.setPayTime(FunUtil.dateDayAdd(FunUtil.getSysDate(),-1));
            }else if("12".equals(type)){//汇总今天
                order.setPayTime(FunUtil.dateDayAdd(FunUtil.getSysDate(),0));
            }
            /**
             * 汇总数据编码转换
             */
            if("11".equals(type) || "12".equals(type)){
                List<java.util.HashMap> list = reportService.selectPrintCollect(order);
                if(list.size() == 0){
                    return AppDesUtil.posAppDesEncrypt(null,FunUtil.getFormatDate(order.getPayTime(),"yyyy-MM-dd")+"无订单",0);
                }
                List<java.util.HashMap> newList = new ArrayList<>();
                Integer allSumNum = 0,allSumFee = 0;
                for(java.util.HashMap map : list){
                    java.util.HashMap newMap = new java.util.HashMap();
                    MapGetterTool mapGetterTool = new MapGetterTool(map);
                    newMap.put("payTypeMc",CommDictList.getDictMc("t_order_pay_type",map.get("PAY_TYPE")+""));
                    allSumNum += mapGetterTool.getInteger("NUM");
                    allSumFee += mapGetterTool.getInteger("SUM_TOTAL_FEE");
                    newMap.put("sumNum",mapGetterTool.getInteger("NUM"));
                    newMap.put("sumFee",FunUtil.fenToYuan(mapGetterTool.getInteger("SUM_TOTAL_FEE")));
                    newList.add(newMap);
                }
                java.util.HashMap map = new java.util.HashMap();
                map.put("merchantName",FunUtil.dealString(deviceInfo.getMerchantName()));//商户名称
                map.put("lklMerchantCode",FunUtil.dealString(deviceInfo.getLklMerchantCode()));//商户编码
                map.put("lklTerminalCode",FunUtil.dealString(deviceInfo.getLklTerminalCode()));//设备编码
                map.put("merchantYunId",FunUtil.dealString(deviceInfo.getMerchantYunId())); //商户 帐号
                map.put("sumTime",FunUtil.getFormatDate(order.getPayTime(),"yyyy-MM-dd")); //所有合计数量
                map.put("allSumNum",allSumNum); //所有合计数量
                map.put("allSumFee",FunUtil.fenToYuan(allSumFee)); //所有合计费用
                map.put("dataList",newList);
                map.put("type",type);
                return AppDesUtil.posAppDesEncrypt(map,"成功",1);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return AppDesUtil.posAppDesEncrypt(null,"失败",0);
    }

    /**
     * 获取设备帐单
     */
    @RequestMapping(value = "getDeviceBill", method = RequestMethod.POST)
    @ResponseBody
    public String getDeviceBill(HttpServletRequest request, String data) {
        try{
            /**
             * 获取 Map 对象
             */
            MapGetterTool hashMap = new MapGetterTool(AppFunUtil.getDataMap(data));
            if(hashMap == null){
                return AppDesUtil.posAppDesEncrypt(null,"失败",0);
            }
            /**
             * 验证是否登入 , 并返回对象
             */
            DeviceInfo deviceInfo = AppFunUtil.checkToken(request,deviceInfoService,hashMap.getString("token"));
            if(deviceInfo.getState() != 1 ){
                return AppDesUtil.posAppDesEncrypt(null,deviceInfo.getNote(),deviceInfo.getState());
            }
            //获取支付类型
            HashMap<String, String> paraMap = new HashMap<String, String>();
            paraMap.put("deviceCode", deviceInfo.getMachineCode());
            String pay_type =  hashMap.getString("pay_type");
            if (!StringUtils.isEmpty(pay_type)) {
                paraMap.put("pay_type", pay_type);
            }
            //搜索开始时间
            String startDay =  hashMap.getString("startDate");
            if (!StringUtils.isEmpty(startDay)) {
                paraMap.put("startDay", startDay+" 00:00:00");
            }
            //搜索结束时间
            String endDay =  hashMap.getString("endDate");
            if (!StringUtils.isEmpty(endDay)) {
                paraMap.put("endDay", endDay+" 23:59:59");
            }
            //获取搜索日期的列表
            List<OrderPosDetail> detail = orderService.getOrderDetailListByMap(paraMap);
            //获取搜索日期底下的列表
            List<OrderPosDetail> detail1 = orderService.getOrderDetailDayListByMap(paraMap);
            //Android数据格式
                for (int i = 0; i < detail.size(); i++) {
                    String str = detail.get(i).getPayTime();
                    for (int j = 0; j < detail1.size(); j++) {
                        if (str.equals(detail1.get(j).getTime().substring(0, 10))) {
                            detail1.get(j).setPayTime(detail.get(i).getPayTime());
                            detail1.get(j).setCount(detail.get(i).getCount());
                        }
                    }
                }
                HashMap map = new HashMap();
                List<OrderPosDetailModel> odList = new ArrayList<OrderPosDetailModel>();
                if (!StringUtils.isEmpty(detail1)) {
                    for (Iterator<OrderPosDetail> iterator = detail1.iterator(); iterator.hasNext(); ) {
                        OrderPosDetail orderPosDetail = (OrderPosDetail) iterator.next();
                        OrderPosDetailModel orderPosDetailModel = ClassInstanceUtil.createOrderPosDetailModel(orderPosDetail);
                        odList.add(orderPosDetailModel);
                    }
                }
                map.put("odList", odList);
                return AppDesUtil.posAppDesEncrypt(map, "成功获取", 1);
        }catch (Exception e ){
            e.printStackTrace();
        }
        return AppDesUtil.posAppDesEncrypt(null,"失败",0);
    }

    /**
     * 获取设备商户信息
     */
    @RequestMapping(value = "getMerchantInfo", method = RequestMethod.POST)
    @ResponseBody
    public String getMerchantInfo(HttpServletRequest request,String data) {
        try{
            /**
             * 获取 Map 对象
             */
            MapGetterTool hashMap = new MapGetterTool(AppFunUtil.getDataMap(data));
            if(hashMap == null){
                return AppDesUtil.posAppDesEncrypt(null,"获取设备商户信息失败",0);
            }
            /**
             * 验证是否登入 , 并返回对象
             */
            DeviceInfo deviceInfo = AppFunUtil.checkToken(request,deviceInfoService,hashMap.getString("token"));
            if(deviceInfo.getState() != 1 ){
                return AppDesUtil.posAppDesEncrypt(null,deviceInfo.getNote(),deviceInfo.getState());
            }
            /**
             * 查询信息
             */
            BaseExample baseExample = new BaseExample();
            baseExample.createCriteria().andEqualTo("state","1").andEqualTo("device_unique",deviceInfo.getDeviceUnique());
            List<DeviceList> infoList = deviceListService.selectByExample(baseExample);
            java.util.HashMap map = new java.util.HashMap();
            if(infoList.size() > 0){
                map.put("merchantName",infoList.get(0).getMerchantName()); //所有合计数量
                map.put("merchantYunPayAccount",infoList.get(0).getMerchantYunPayAccount()); //所有合计数量
                map.put("merchantCode",infoList.get(0).getLklMerchantCode()); //所有合计费用
                map.put("terminalCode",infoList.get(0).getLklTerminalCode());
                return AppDesUtil.posAppDesEncrypt(map,"成功",1);
            }else{
                return AppDesUtil.posAppDesEncrypt(null,"不存在该设备商户信息!",0);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return AppDesUtil.posAppDesEncrypt(null,"获取设备商户信息失败",0);
    }


    /**
     * 获取订单详情
     */
    @RequestMapping(value = "getOrderDetail", method = RequestMethod.POST)
    @ResponseBody
    public String getOrderDetail(HttpServletRequest request,String data) {
        try{
            /**
             * 获取 Map 对象
             */
            MapGetterTool hashMap = new MapGetterTool(AppFunUtil.getDataMap(data));
            if(hashMap == null){
                return AppDesUtil.posAppDesEncrypt(null,"查看订单详情失败",0);
            }
            /**
             * 验证是否登入 , 并返回对象
             */
            DeviceInfo deviceInfo = AppFunUtil.checkToken(request,deviceInfoService,hashMap.getString("token"));
            if(deviceInfo.getState() != 1 ){
                return AppDesUtil.posAppDesEncrypt(null,deviceInfo.getNote(),deviceInfo.getState());
            }

            /**
             * 查询信息并重组model
             */
            List<Order> orderList = orderService.getOrderByNumber(hashMap.getString("orderNumber"));
            List<OrderDetail> list = new ArrayList<OrderDetail>();
            if(orderList.size()>0){
                for (Iterator<Order> iterator = orderList.iterator(); iterator.hasNext();) {
                    Order iOrder = (Order) iterator.next();
                    OrderDetail orderDetail = ClassInstanceUtil
                            .createOrderDetailModel(iOrder);
                    list.add(orderDetail);
                }
                return AppDesUtil.posAppDesEncrypt(list,"成功",1);
            }else{
                return AppDesUtil.posAppDesEncrypt(null,"无法查看扫码订单详情!",1);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return AppDesUtil.posAppDesEncrypt(null,"查看订单详情失败",0);
    }
}
