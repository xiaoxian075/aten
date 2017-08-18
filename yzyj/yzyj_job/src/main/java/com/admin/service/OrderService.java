package com.admin.service;

import com.admin.model.BaseExample;
import com.admin.model.DeviceInfo;
import com.admin.model.Order;
import com.admin.model.RepairOrder;
import com.admin.vo.CommAppVo;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;
import com.core.util.MapGetterTool;

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


    CommAppVo insertScanCodeOrder(MapGetterTool dataMap, DeviceInfo deviceInfo);

    Integer updateAppPayState(MapGetterTool dataMap, DeviceInfo deviceInfo);

    Integer updateOrderPayState(Order order);

    Integer updateOrderPayStateBySm(Order order);

    Integer updateOrderPushState(Order order);

    CommAppVo updateScanCodeOrder(MapGetterTool dataMap);

    List<Order> searchOrder(Order order);

    Integer getCountByType(HashMap<Object,Object> map);

    List<Order> getOrderByNumber(String orderNumber);

    List<RepairOrder> getRepairOrderByNumber(String orderNumber);

    Integer insertRepairOrder(HttpServletRequest request,List<Order> order);

    Integer updateRepairOrderStatus(RepairOrder repairOrder,int status);

    Integer updateOrderStateByNumber(RepairOrder repairOrder);
}
