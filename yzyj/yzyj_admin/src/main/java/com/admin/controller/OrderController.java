package com.admin.controller;

import com.admin.model.*;
import com.admin.service.OrderService;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.core.entity.JSONResult;
import com.core.mybatis.DataTablesPage;
import com.core.util.AppFunUtil;
import com.core.util.ClassInstanceUtil;
import com.core.util.CommRedisFun;
import com.core.util.FunUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/6/8.
 */
@Controller
@RequestMapping(value = "/admin/posOrder")
public class OrderController {
    @Resource
    private OrderService orderService;

    /**
     * 订单明细首页
     * @param model
     * @return
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model) {
        HashMap<Object,Object> map = new HashMap<Object,Object>();
        //刷卡
        map.put("type",1);
        Long skCount = orderService.getCountByType(map);
        model.addAttribute("skCount", FunUtil.fenToYuanL(skCount));
        //扫码
        map.put("type",2);
        Long smCount = orderService.getCountByType(map);
        model.addAttribute("smCount", FunUtil.fenToYuanL(smCount));
        return "admin/posOrder/index";
    }

    /**
     * 获取所有的订单信息
     * @param page
     * @param model
     * @param order
     * @return
     */
    @RequestMapping(value = "getList",method = RequestMethod.GET)
    @ResponseBody
    public String getList(DataTablesPage<Order> page, Model model, Order order) {
        try{
            BaseExample baseExample = new BaseExample();
            //订单类型  POS
            BaseExample.Criteria criteria = baseExample.createCriteria();
            if(!StringUtils.isEmpty(order.getOrderNumber())){
                criteria.andEqualTo("o.ORDER_NUMBER",order.getOrderNumber());
            }
            if(!StringUtils.isEmpty(order.getLklMerchantCode())){
                criteria.andEqualTo("device.LKL_MERCHANT_CODE",order.getLklMerchantCode());
            }
            if(!StringUtils.isEmpty(order.getMerchantYunId())){
                criteria.andEqualTo("o.MERCHANT_YUN_ID",order.getMerchantYunId());
            }
            if(!StringUtils.isEmpty(order.getYunId())){
                criteria.andEqualTo("o.YUN_ID",order.getYunId());
            }
            if(order.getSdate() != null){
                String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getSdate());
                criteria.andGreaterThanOrEqualTo("TO_CHAR(o.CREATE_TIME,'yyyy-mm-dd hh24:mi:ss')",s);
            }
            if(order.getEdate() != null){
                String e = new SimpleDateFormat("yyyy-MM-dd").format(order.getEdate());
                criteria.andLessThanOrEqualTo("TO_CHAR(o.CREATE_TIME,'yyyy-mm-dd hh24:mi:ss')",e+" 23:59:59");
            }
            if(order.getOrderType() != null){
                criteria.andEqualTo("o.ORDER_TYPE",order.getOrderType().toString());
            }
            if(order.getPayState() != null){
                criteria.andEqualTo("o.PAY_STATE",order.getPayState().toString());
            }
            if(!StringUtils.isEmpty(order.getPayType())){
                criteria.andEqualTo("o.PAY_TYPE",order.getPayType().toString());
            }
            criteria.andEqualTo("1","1");
            baseExample.setOrderByClause("o.CREATE_TIME desc ");
            orderService.selectPosPage(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    @RequestMapping(value = "/agentPosOrder",method = RequestMethod.GET)
    public String agentPosOrder(HttpServletRequest request, Model model) {
        User user = AppFunUtil.getUser(request);
        model.addAttribute("agentUnique", user.getAgentUnique());
        return "admin/posOrder/agentPosOrder";
    }

    /**
     * 获取代理人的订单信息（根据agent_unique唯一性）
     * @param request
     * @param page
     * @param model
     * @param order
     * @return
     */
    @RequestMapping(value = "getAgentPosOrderList",method = RequestMethod.GET)
    @ResponseBody
    public String getAgentPosOrderList(HttpServletRequest request,DataTablesPage<Order> page, Model model, Order order) {
        try{
            BaseExample baseExample = new BaseExample();
//            User user = AppFunUtil.getUser(request);
            //订单类型  POS
            BaseExample.Criteria criteria = baseExample.createCriteria();
            if(!StringUtils.isEmpty(order.getMachineCode())){
                criteria.andEqualTo("o.MACHINECODE",order.getMachineCode());
            }
            if(!StringUtils.isEmpty(order.getMerchantYunId())){
                criteria.andEqualTo("o.MERCHANT_YUN_ID",order.getMerchantYunId());
            }
            if(order.getSdate() != null){
                String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getSdate());
                criteria.andGreaterThanOrEqualTo("TO_CHAR(o.CREATE_TIME,'yyyy-mm-dd hh24:mi:ss')",s);
            }
            if(order.getEdate() != null){
                String e = new SimpleDateFormat("yyyy-MM-dd").format(order.getEdate());
                criteria.andLessThanOrEqualTo("TO_CHAR(o.CREATE_TIME,'yyyy-mm-dd hh24:mi:ss')",e+" 23:59:59");
            }
            if(order.getOrderType() != null){
                criteria.andEqualTo("o.ORDER_TYPE",order.getOrderType().toString());
            }
            if(order.getPayState() != null){
                criteria.andEqualTo("o.PAY_STATE",order.getPayState().toString());
            }
            if(!StringUtils.isEmpty(order.getPayType())){
                criteria.andEqualTo("o.PAY_TYPE",order.getPayType().toString());
            }
            criteria.andEqualTo("device.agent_unique",order.getAgentUnique());
            baseExample.setOrderByClause("o.CREATE_TIME desc ");
            orderService.selectPosPage(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    @RequestMapping(value = "/repairOrderIndex",method = RequestMethod.GET)
    public String repairOrderIndex(Model model) {
        return "admin/posOrder/repairOrderIndex";
    }

    /**
     *  获取需要补单的信息
     * @param page
     * @param model
     * @param order ORDER_NUMBER根据订单编号查找
     * @return
     */
    @RequestMapping(value = "/getRepairOrderList",method = RequestMethod.GET)
    @ResponseBody
    public String getRepairOrderList(DataTablesPage<Order> page, Model model, Order order) {
        try{
            BaseExample baseExample = new BaseExample();
            //订单类型  POS
            BaseExample.Criteria criteria = baseExample.createCriteria();
            if(!StringUtils.isEmpty(order.getOrderNumber())){
                criteria.andEqualTo("o.ORDER_NUMBER",order.getOrderNumber());
                criteria.andEqualTo("ORDER_TYPE","1");
                orderService.selectPosPage(page,baseExample);
            }else{

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 提交申请补单
     * @param request
     * @param order
     * @return
     */
    @RequestMapping(value = "editOrderStatus", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<Order> editOrderStatus(HttpServletRequest request,Order order) {
        if (!StringUtils.isEmpty(order.getOrderNumber())) {
            try {
                List<Order> order1 = orderService.getOrderByNumber(order.getOrderNumber());
                if(order1.size() == 0){
                    return new JSONResult<Order>(null, "没有找到该笔订单", false);
                }
                if(order1.get(0).getPayState() == 1){
                    return new JSONResult<Order>(null, "该笔订单已经是已支付状态", false);
                }
                List<RepairOrder> repairOrder = orderService.getRepairOrderByNumber(order.getOrderNumber());
                if(repairOrder.size() != 0){
                    if(repairOrder.get(0).getAlStatus() == 0){
                        return new JSONResult<Order>(null, "该笔订单已经在补单中", false);
                    }else{
                        orderService.updateRepairOrderStatus(repairOrder.get(0),0);
                        return new JSONResult<Order>(null, "提交成功,等待审批", true);
                    }
                }else{
                    order1.get(0).setLklPayTime(order.getLklPayTime());
                    Integer state = orderService.insertRepairOrder(request,order1);
                    if(state == 1){
                        return new JSONResult<Order>(null, "提交成功,等待审批", true);
                    }else{
                        return new JSONResult<Order>(null, "操作失败", false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<Order>(null, "操作失败", false);
            }
        }else {
            return new JSONResult<Order>(null, "操作失败", false);
        }
    }

    @RequestMapping(value = "/waitRepairOrderIndex",method = RequestMethod.GET)
    public String waitRepairOrderIndex(Model model) {
        return "admin/posOrder/waitRepairOrderIndex";
    }

    @RequestMapping(value = "/alreadyRepairOrderIndex",method = RequestMethod.GET)
    public String alreadyRepairOrderIndex(Model model) {
        return "admin/posOrder/alreadyRepairOrderIndex";
    }

    /**
     * 获取等待补单的订单信息
     * @param page
     * @param model
     * @param repairOrder
     * @return
     */
    @RequestMapping(value = "/getWaitRepairOrderList",method = RequestMethod.GET)
    @ResponseBody
    public String getWaitRepairOrderList(DataTablesPage<Order> page, Model model, RepairOrder repairOrder) {
        try{
            BaseExample baseExample = new BaseExample();
            //订单类型  POS
            BaseExample.Criteria criteria = baseExample.createCriteria();
            if(!StringUtils.isEmpty(repairOrder.getOrderNumber())){
                criteria.andEqualTo("ORDER_NUMBER",repairOrder.getOrderNumber());
            }
            criteria.andEqualTo("AL_STATUS","0");
            baseExample.setOrderByClause("REPAIR_TIME desc ");
            orderService.selectRepairOrderPage(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 获取已经审批通过的补单信息
     * @param page
     * @param model
     * @param repairOrder
     * @return
     */
    @RequestMapping(value = "/getAlreadyRepairOrderList",method = RequestMethod.GET)
    @ResponseBody
    public String getAlreadyRepairOrderList(DataTablesPage<Order> page, Model model, RepairOrder repairOrder) {
        try{
            BaseExample baseExample = new BaseExample();
            //订单类型  POS
            BaseExample.Criteria criteria = baseExample.createCriteria();
            if(!StringUtils.isEmpty(repairOrder.getOrderNumber())){
                criteria.andEqualTo("ORDER_NUMBER",repairOrder.getOrderNumber());
            }
            criteria.andNotEqualTo("AL_STATUS","0");
            baseExample.setOrderByClause("AL_TIME desc ");
            orderService.selectRepairOrderPage(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }
    /**
     * 补单拒绝通过（驳回）
     * @param request
     * @param repairOrder
     * @return
     */
    @RequestMapping(value = "reject", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<RepairOrder> reject(HttpServletRequest request,RepairOrder repairOrder) {
        if (repairOrder.getId() != 0) {
            try {
                List<RepairOrder> repairOrder1 = orderService.getRepairOrderByNumber(repairOrder.getOrderNumber());
                if(repairOrder1.size() ==0 ){
                    return new JSONResult<RepairOrder>(null, "没找到审批订单号", false);
                }else{
                    orderService.updateRepairOrderStatus(repairOrder,2);
                    return new JSONResult<RepairOrder>(null, "操作成功", true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<RepairOrder>(null, "操作失败", false);
            }
        }else {
            return new JSONResult<RepairOrder>(null, "操作失败", false);
        }
    }

    /**
     * 补单通过
     * @param request
     * @param repairOrder
     * @return
     */
    @RequestMapping(value = "pass", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<RepairOrder> pass(HttpServletRequest request,RepairOrder repairOrder) {
        if (repairOrder.getId() != 0) {
            try {
                List<RepairOrder> repairOrder1 = orderService.getRepairOrderByNumber(repairOrder.getOrderNumber());
                if(repairOrder1.size() ==0 ){
                    return new JSONResult<RepairOrder>(null, "该笔订单编号在审批列表中有误", false);
                }else{
                    /**
                     * 判断订单状态
                     */
                    List<Order> order = orderService.getOrderByNumber(repairOrder.getOrderNumber());
                    if(order.size() == 0){
                        return new JSONResult<RepairOrder>(null, "没有找到该笔订单", false);
                    }
                    if(order.get(0).getPayState() == 1){
                        return new JSONResult<RepairOrder>(null, "该笔订单已经是已支付状态", false);
                    }
                    /**
                     * 修改审批的状态
                     */
                    Integer state = orderService.updateRepairOrderStatus(repairOrder,1);
                    if(state == 1){
                        /**
                         * 修改订单的状态
                         */
                        repairOrder.setLklPayTime(repairOrder1.get(0).getLklPayTime());
                        Integer state1 =  orderService.updateOrderStateByNumber(repairOrder);
                        if(state1 == 1){
                            /**
                             * 开启定时扫描
                             */
                            //CommConstant.blnOrderJob = true;
                            CommRedisFun.setHKey("staticData","blnOrderJob","1");
                        }
                    }
                    return new JSONResult<RepairOrder>(null, "操作成功", true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<RepairOrder>(null, "操作失败", false);
            }
        }else {
            return new JSONResult<RepairOrder>(null, "操作失败", false);
        }
    }

    /**
     * 订单详情页面
     * @param model
     * @param order
     * @return
     */
    @RequestMapping(value = "orderDetail", method = RequestMethod.GET)
    public String orderDetail(Model model, Order order) {
        try {
            List<OrderDetail> list = new ArrayList<OrderDetail>();
            if(StringUtils.isEmpty(order.getOrderNumber())){
                model.addAttribute("detail",list);
            }else{
                List<Order> orderList = orderService.getOrderByNumber(order.getOrderNumber());
                if(orderList.size() == 0){
                    model.addAttribute("detail",list);
                }else{
                    for (Iterator<Order> iterator = orderList.iterator(); iterator.hasNext();) {
                        Order iOrder = (Order) iterator.next();
                        OrderDetail orderDetail = ClassInstanceUtil
                                .createOrderDetailModel(iOrder);
                        list.add(orderDetail);
                    }
                    if(list.size() == 1){
                        model.addAttribute("detail",list.get(0));
                    }else{
                        model.addAttribute("detail",list.get(1));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "admin/posOrder/detail";
    }
}
