package com.admin.dao;

import com.admin.model.BaseExample;
import com.admin.model.Order;
import com.admin.model.OrderDetail;
import com.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface OrderDaoMapper extends GenericDao<Order,String> {

    List<Order> selectByExample(BaseExample baseExample);

    List<OrderDetail> getOrderDetailList(@Param("deviceCode") String deviceCode);

    List<OrderDetail> getOrderDetailDayList(@Param("deviceCode") String deviceCode);

    List<Order> getMerchantMoneyCount(HashMap map);

    List<OrderDetail> getOrderDetailListByMap(HashMap<String,String> map);

    List<OrderDetail> getOrderDetailDayListByMap(HashMap<String,String> map);
    
}
