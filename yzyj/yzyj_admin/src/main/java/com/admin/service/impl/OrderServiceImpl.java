package com.admin.service.impl;

import com.admin.dao.OrderDaoMapper;
import com.admin.model.*;
import com.admin.service.OrderService;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import com.core.util.AppFunUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
@Service
public class OrderServiceImpl extends GenericServiceImpl<Order, String> implements OrderService {

    @Resource
    private OrderDaoMapper orderDaoMapper;

    @Override
    public GenericDao<Order, String> getDao() {
        return orderDaoMapper;
    }


    public List<Order> selectByExampleAndPage(DataTablesPage<Order> page, BaseExample baseExample){
        return orderDaoMapper.selectByExampleAndPage(page,baseExample);
    }
    public List<Order> selectPosPage(DataTablesPage<Order> page, BaseExample baseExample){
        return orderDaoMapper.selectPosPage(page,baseExample);
    }

    @Override
    public List<RepairOrder> selectRepairOrderPage(DataTablesPage<Order> page, BaseExample baseExample) {
        return orderDaoMapper.selectRepairOrderPage(page,baseExample);
    }

    public List<Order> selectByExample(BaseExample baseExample){
        return orderDaoMapper.selectByExample(baseExample);
    }



    @Override
    public List<Order> searchOrder(Order order) {
        return orderDaoMapper.searchOrder(order);
    }

    @Override
    public Long getCountByType(HashMap<Object, Object> map) {
        return orderDaoMapper.getCountByType(map);
    }

    @Override
    public List<Order> getOrderByNumber(String orderNumber) {
        return orderDaoMapper.getOrderByNumber(orderNumber);
    }

    @Override
    public List<RepairOrder> getRepairOrderByNumber(String orderNumber) {
        return orderDaoMapper.getRepairOrderByNumber(orderNumber);
    }

    @Override
    public Integer insertRepairOrder(HttpServletRequest request, List<Order> order) {
        Integer state = 0;
        try{
            User user = AppFunUtil.getUser(request);
            RepairOrder repairOrder = new RepairOrder();
            if(order.size() == 0){
                return state;
            }else{
                repairOrder.setOrderNumber(order.get(0).getOrderNumber());
                repairOrder.setRepairTime(new Date());
                repairOrder.setAlStatus(0);
                repairOrder.setUserName(user.getUsername());
                repairOrder.setLklPayTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(order.get(0).getLklPayTime()));
                repairOrder.setRepairMoney(order.get(0).getTotalFee());
                orderDaoMapper.insertRepairOrder(repairOrder);
                state = 1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return state;
    }

    @Override
    public Integer updateRepairOrderStatus(RepairOrder repairOrder,int status) {
        Integer state = 0;
        try {
            RepairOrder repairOrder1 = new RepairOrder();
            repairOrder1.setOrderNumber(repairOrder.getOrderNumber());
            repairOrder1.setAlStatus(status);
            repairOrder1.setAlTime(new Date());
            orderDaoMapper.updateRepairOrderStatus(repairOrder1);
            state = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return state;
    }

    @Override
    public Integer updateOrderStateByNumber(RepairOrder repairOrder) {
        Integer state = 0;
        try{
            Order order1 = new Order();
            order1.setOrderNumber(repairOrder.getOrderNumber());
            order1.setPayTime(repairOrder.getLklPayTime());
            order1.setPayState(1);
            order1.setOrderState(1);
            order1.setAppPayState(1);
            order1.setAppPayTime(new Date());
            orderDaoMapper.updateOrderStateByNumber(order1);
            state = 1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return state;
    }



    @Override
    public Integer getCountByDevice(DeviceList deviceList) {
        return orderDaoMapper.getCountByDevice(deviceList);
    }

}
