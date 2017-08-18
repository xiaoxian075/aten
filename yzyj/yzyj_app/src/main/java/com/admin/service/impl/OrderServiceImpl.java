package com.admin.service.impl;

import com.admin.dao.OrderDaoMapper;
import com.admin.model.Order;
import com.admin.model.OrderDetail;
import com.admin.service.OrderService;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Override
    public List<OrderDetail> getOrderDetailList(String deviceCode) {
        return orderDaoMapper.getOrderDetailList(deviceCode);
    }

    @Override
    public List<OrderDetail> getOrderDetailDayList(String deviceCode) {
        return orderDaoMapper.getOrderDetailDayList(deviceCode);
    }

    @Override
    public List<Order> getMerchantMoneyCount(HashMap map) {
        return orderDaoMapper.getMerchantMoneyCount(map);
    }

    /**
	 * @author linjunqin
	 * @Description 获取云支付帐号下对应设置号刷卡记录 
	 * @param
	 * @date 2017-3-31 上午11:55:59
	 */
	@Override
	public List<OrderDetail> getOrderDetailListByMap(HashMap<String,String> map) {
		return orderDaoMapper.getOrderDetailListByMap(map);
	}

	@Override
	public List<OrderDetail> getOrderDetailDayListByMap(HashMap<String,String> map) {
		return orderDaoMapper.getOrderDetailDayListByMap(map);
	}
}
