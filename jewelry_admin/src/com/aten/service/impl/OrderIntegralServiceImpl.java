package com.aten.service.impl;

import com.aten.model.orm.OrderExpressNode;
import com.aten.model.orm.OrderIntegral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aten.dao.OrderDao;
import com.aten.dao.OrderIntegralDao;
import com.aten.service.OrderIntegralService;
import com.aten.service.OrderService;

@Service("orderIntegralService")
public class OrderIntegralServiceImpl extends CommonServiceImpl<OrderIntegral, String> implements OrderIntegralService {

	private OrderIntegralDao orderIntegralDao;
	@Autowired
	private OrderDao orderDao;

	@Autowired
	public OrderIntegralServiceImpl(OrderIntegralDao orderIntegralDao) {
		super(orderIntegralDao);
		this.orderIntegralDao = orderIntegralDao;
	}

	@Override
	public OrderExpressNode getOrderExpress(String order_number) {
		return orderDao.selectonOrderExpressByOrderid(order_number);
	}

}
