package com.admin.service;

import com.admin.model.Order;
import com.admin.model.OrderDetail;
import com.core.generic.GenericService;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wjf on 2016/4/15.
 */
public interface OrderService extends GenericService<Order,String> {

    List<OrderDetail> getOrderDetailList(String deviceCode);

    List<OrderDetail> getOrderDetailDayList(String deviceCode);

    List<Order> getMerchantMoneyCount(HashMap map);

    List<OrderDetail> getOrderDetailListByMap(HashMap<String,String> map);

    List<OrderDetail> getOrderDetailDayListByMap(HashMap<String,String> map);
    
    
}
