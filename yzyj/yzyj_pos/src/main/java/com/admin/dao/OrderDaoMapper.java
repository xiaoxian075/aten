package com.admin.dao;

import com.admin.model.*;
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



    List<Order> selectMerchantOrder(BaseExample baseExample);

    Integer updateOrderPayState(Order order);

    Integer updateOrderPayStateBySm(Order order);

    Integer updateOrderState(Order order);

    Integer updateOrderPushState(Order order);

    List<Order> getOrderByNumber(@Param("orderNumber") String orderNumber);

    List<Order> getOrderByNumberAndOrderType(@Param("orderNumber") String orderNumber, @Param("type") int type);

    Integer getCountPay(@Param("orderNumber") String orderNumber);

    List<OrderPosDetail> getOrderDetailListByMap(HashMap<String, String> map);

    List<OrderPosDetail> getOrderDetailDayListByMap(HashMap<String, String> map);


}
