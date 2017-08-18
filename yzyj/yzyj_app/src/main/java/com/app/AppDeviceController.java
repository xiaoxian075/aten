package com.app;

import com.admin.model.*;
import com.admin.service.AgencyPersonService;
import com.admin.service.DeviceListService;
import com.admin.service.OrderService;
import com.admin.service.UserService;
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
 * Created by Administrator on 2016/12/8.
 */
@Controller
@RequestMapping(value = "/app/device")
public class AppDeviceController {
    @Resource
    private UserService userService;
    @Resource
    private AgencyPersonService agencyPersonService;
    @Resource
    private DeviceListService deviceListService;
    @Resource
    private OrderService orderService;

    /**
     * 获取设备列表并统计设备当天的交易总额
     *
     * @param request
     * @param data
     * @return
     */
    @RequestMapping(value = "getDeviceList", method = RequestMethod.POST)
    @ResponseBody
    public String getDeviceList(HttpServletRequest request, String data) {
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
            User user = AppFunUtil.checkToken(request, userService, hashMap.getString("token"));
            if (user.getState() != 1) {
                return AppDesUtil.posAppDesEncrypt(null, user.getNote(), user.getState());
            }
            //查询代理人信息
            BaseExample baseExample = new BaseExample();
            baseExample.createCriteria().andEqualTo("agent_unique", user.getAgentUnique());
            List<AgencyPerson> list = agencyPersonService.selectByExample(baseExample);
            List<DeviceDetailModel> ddList = new ArrayList<DeviceDetailModel>();
            //如果存在代理人信息
            if (list.size() > 0) {
            	//条件查询  分页参数 云付通帐号搜索 
                java.util.HashMap mapPara = new java.util.HashMap();
            	mapPara.put("pageStart",hashMap.getInteger("pageStart"));
                mapPara.put("pageEnd",hashMap.getInteger("pageEnd"));
                //代理人唯一帐号
                mapPara.put("agentUnique",list.get(0).getAgentUnique());
                //云付通帐号
                if(!StringUtils.isEmpty(hashMap.getString("yunPayAccount"))){
                    mapPara.put("yunPayAccount",hashMap.getString("yunPayAccount"));
                }
                HashMap map = new HashMap();
                //获取代理人设备列表
                List<DeviceList> dList = deviceListService.getDeviceListByAgent(mapPara);
                if (dList.size() == 0) {
                    map.put("deviceList", ddList);
                    return AppDesUtil.posAppDesEncrypt(map, "成功获取", 1);
                } else {
                    //获取当天设备收益记录  改成云支付帐号输出
                    List<DeviceList> list2 = deviceListService.getMoneyByAgentUniqueAndDay(user.getAgentUnique());
                    //当天的收益记录
                    if (list2.size() == 0) {
                        for (int i = 0; i < dList.size(); i++) {
                            dList.get(i).setDayCount(0);
                        }
                    } else {
                        for (int i = 0; i < dList.size(); i++) {
                            for (int j = 0; j < list2.size(); j++) {
                                if (list2.get(j).getMachineCode().equals(dList.get(i).getDeviceCode())) {
                                    dList.get(i).setDayCount(list2.get(j).getMoneyCount());
                                }
                            }
                        }
                    }
                    //获取设备累计收益列表
                    List<DeviceList> list1 = deviceListService.getMoneyByAgentUnique(user.getAgentUnique());
                    for (int i = 0; i < dList.size(); i++) {
                        for (int j = 0; j < list1.size(); j++) {
                            if (list1.get(j).getMachineCode().equals(dList.get(i).getDeviceCode())) {
                                dList.get(i).setMoneyCount(list1.get(j).getMoneyCount());
                            }
                        }
                    }
                    //重组model
                    if (!StringUtils.isEmpty(dList)) {
                        for (Iterator<DeviceList> iterator = dList.iterator(); iterator.hasNext(); ) {
                            DeviceList deviceList = (DeviceList) iterator.next();
                            DeviceDetailModel deviceDetailModel = ClassInstanceUtil.createDeviceDetailModel(deviceList);
                            ddList.add(deviceDetailModel);
                        }
                    }
                    map.put("deviceList", ddList);
                    return AppDesUtil.posAppDesEncrypt(map, "成功获取", 1);
                }
            } else {
                return AppDesUtil.posAppDesEncrypt(null, "没找到当前用户", 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AppDesUtil.posAppDesEncrypt(null, "失败", 0);
    }

    /**
     * 获取交易总额详情
     *
     * @param request
     * @param data
     * @return
     */
    @RequestMapping(value = "getOrderDetail", method = RequestMethod.POST)
    @ResponseBody
    public String getOrderDetail(HttpServletRequest request, String data) {
        try {
            MapGetterTool hashMap = new MapGetterTool(AppFunUtil.getDataMap(data));
            if (hashMap == null) {
                return AppDesUtil.posAppDesEncrypt(null, "失败", 0);
            }
            /**
             * 验证是否登入 , 并返回对象
             */
            User user = AppFunUtil.checkToken(request, userService, hashMap.getString("token"));
            if (user.getState() != 1) {
                return AppDesUtil.posAppDesEncrypt(null, user.getNote(), user.getState());
            }
            //通过设备号查询信息
            String machineCode = hashMap.getString("deviceCode");
            //判断是什么设备类型的
            String type = hashMap.getString("type");
            if (StringUtils.isEmpty(machineCode)) {
                return AppDesUtil.posAppDesEncrypt(null, "没找到设备编码", 1);
            }
            //获取支付类型
            HashMap<String, String> paraMap = new HashMap<String, String>();
            paraMap.put("deviceCode", machineCode);
            String pay_type =  hashMap.getString("pay_type");
            if (!StringUtils.isEmpty(pay_type)) {
            	 paraMap.put("pay_type", pay_type);
            }
            //搜索开始时间
            String startDay =  hashMap.getString("startDay");
            if (!StringUtils.isEmpty(startDay)) {
           	 	paraMap.put("startDay", startDay+" 00:00:00");
            }
            //搜索结束时间
            String endDay =  hashMap.getString("endDay");
            if (!StringUtils.isEmpty(endDay)) {
           	 	paraMap.put("endDay", endDay+" 23:59:59");
            }
            //
            //List<OrderDetail> detail = orderService.getOrderDetailListByMap(paraMap);
            //获取搜索日期的列表
            List<OrderDetail> detail = orderService.getOrderDetailListByMap(paraMap);
            //获取搜索日期底下的列表
            List<OrderDetail> detail1 = orderService.getOrderDetailDayListByMap(paraMap);
            //Android数据格式
            if (type.equals("1")) {
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
                List<OrderDetailModel> odList = new ArrayList<OrderDetailModel>();
                if (!StringUtils.isEmpty(detail1)) {
                    for (Iterator<OrderDetail> iterator = detail1.iterator(); iterator.hasNext(); ) {
                        OrderDetail orderDetail = (OrderDetail) iterator.next();
                        OrderDetailModel orderDetailModel = ClassInstanceUtil.createOrderDetailModel(orderDetail);
                        odList.add(orderDetailModel);
                    }
                }
                map.put("odList", odList);
                return AppDesUtil.posAppDesEncrypt(map, "成功获取", 1);
            }

            //ios数据格式
            if(type.equals("2")){
                String dateStr = "";
                List list = new ArrayList();
                java.util.HashMap map = new java.util.HashMap<>();
                for(int j=0;j<detail1.size();j++){
                    if(dateStr.equals(detail1.get(j).getTime().substring(0,10))){
                        String payType = detail1.get(j).getType();
                        if(payType.equals("Y01")){
                            detail1.get(j).setType("扫码支付");
                        }
                        if(payType.equals("00")){
                            detail1.get(j).setType("借记卡");
                        }
                        if(payType.equals("01")){
                            detail1.get(j).setType("贷记卡");
                        }
                        detail1.get(j).setTime(detail1.get(j).getTime().substring(11,19));
                        detail1.get(j).setDayMoneyStr(FunUtil.fenToYuan(detail1.get(j).getDayMoney()));
                        list.add(detail1.get(j));
                    }else{
                        if(list.size() > 0){
                            map.put("list"+dateStr,list);
                        }
                        list = new ArrayList();
                        String payType = detail1.get(j).getType();
                        if(payType.equals("Y01")){
                            detail1.get(j).setType("扫码支付");
                        }
                        if(payType.equals("00")){
                            detail1.get(j).setType("借记卡");
                        }
                        if(payType.equals("01")){
                            detail1.get(j).setType("贷记卡");
                        }
                        dateStr = detail1.get(j).getTime().substring(0,10);
                        detail1.get(j).setTime(detail1.get(j).getTime().substring(11,19));
                        detail1.get(j).setDayMoneyStr(FunUtil.fenToYuan(detail1.get(j).getDayMoney()));
                        list.add(detail1.get(j));
                    }
                }
                if(list.size() > 0){
                    map.put("list"+dateStr,list);
                }
                /**
                 * 组合
                 */
                java.util.ArrayList listAll = new java.util.ArrayList<>();
                for(OrderDetail orderDetail : detail){
                    java.util.HashMap map1 = new java.util.HashMap<>();
                    map1.put("count", FunUtil.fenToYuan(orderDetail.getCount()));
                    map1.put("day",orderDetail.getPayTime());
                    map1.put("list",map.get("list"+orderDetail.getPayTime()));
                    listAll.add(map1);
                }
                return AppDesUtil.posAppDesEncrypt(listAll,"成功获取",1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AppDesUtil.posAppDesEncrypt(null, "失败", 0);
    }
}
