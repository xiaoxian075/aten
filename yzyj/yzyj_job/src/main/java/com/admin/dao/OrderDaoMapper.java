package com.admin.dao;

import com.admin.model.BaseExample;
import com.admin.model.Order;
import com.admin.model.RepairOrder;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface OrderDaoMapper extends GenericDao<Order,String> {

    List<Order> selectByExample(BaseExample baseExample);

    List<Order> selectScanCodeOrder(BaseExample baseExample);

    List<Order> selectByExampleAndPage(DataTablesPage<Order> page, BaseExample baseExample);

    List<Order> selectPosPage(DataTablesPage<Order> page, BaseExample baseExample);

    List<RepairOrder> selectRepairOrderPage(DataTablesPage<Order> page, BaseExample baseExample);

    List<Order> selectMerchantOrder(BaseExample baseExample);

    Integer updateOrderPayState(Order order);

    Integer updateOrderPayStateBySm(Order order);

    Integer updateOrderState(Order order);

    Integer updateOrderPushState(Order order);

    Integer updateRepairOrderStatus(RepairOrder repairOrder);

    List<Order> searchOrder(Order order);

    Integer getCountByType(HashMap<Object, Object> map);

    List<Order> getOrderByNumber(@Param("orderNumber") String orderNumber);

    List<RepairOrder> getRepairOrderByNumber(@Param("orderNumber") String orderNumber);

    Integer insertRepairOrder(RepairOrder repairOrder);

    Integer updateOrderStateByNumber(Order order);

}
