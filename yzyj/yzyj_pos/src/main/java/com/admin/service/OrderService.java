package com.admin.service;

import com.admin.model.*;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;
import com.core.util.CommAppVo;
import com.core.util.MapGetterTool;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface OrderService extends GenericService<Order,String> {

    List<Order> selectByExampleAndPage(DataTablesPage<Order> page, BaseExample baseExample);

    List<Order> selectByExample(BaseExample baseExample);

    CommAppVo insertOrderCheck(MapGetterTool dataMap, DeviceInfo deviceInfo);

    CommAppVo insertScanCodeOrder(MapGetterTool dataMap, DeviceInfo deviceInfo);

    Integer updateAppPayState(MapGetterTool dataMap, DeviceInfo deviceInfo);

    Integer updateOrderPayState(Order order);

    Integer updateOrderPayStateBySm(Order order);

    Integer updateOrderPushState(Order order);

    CommAppVo updateScanCodeOrder(MapGetterTool dataMap);

    List<Order> getOrderByNumber(String orderNumber);

    List<Order> getOrderByNumberAndOrderType(String orderNumber, int type);

    Integer getCountPay(String orderNumber);

    List<OrderPosDetail> getOrderDetailListByMap(HashMap<String, String> map);

    List<OrderPosDetail> getOrderDetailDayListByMap(HashMap<String, String> map);


}
