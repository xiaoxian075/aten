package com.admin.service;

import com.admin.model.*;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wjf on 2016/4/15.
 */
public interface OrderService extends GenericService<Order,String> {

    List<Order> selectByExampleAndPage(DataTablesPage<Order> page, BaseExample baseExample);

    List<Order> selectPosPage(DataTablesPage<Order> page, BaseExample baseExample);

    List<RepairOrder> selectRepairOrderPage(DataTablesPage<Order> page, BaseExample baseExample);

    List<Order> selectByExample(BaseExample baseExample);

    List<Order> searchOrder(Order order);

    Long getCountByType(HashMap<Object,Object> map);

    List<Order> getOrderByNumber(String orderNumber);

    List<RepairOrder> getRepairOrderByNumber(String orderNumber);

    Integer insertRepairOrder(HttpServletRequest request,List<Order> order);

    Integer updateRepairOrderStatus(RepairOrder repairOrder,int status);

    Integer updateOrderStateByNumber(RepairOrder repairOrder);

    Integer getCountByDevice(DeviceList deviceList);

}
