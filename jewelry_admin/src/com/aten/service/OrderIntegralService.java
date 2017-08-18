package com.aten.service;

import com.aten.model.orm.OrderExpressNode;
import com.aten.model.orm.OrderIntegral;

public  interface OrderIntegralService extends CommonService<OrderIntegral, String>{

	OrderExpressNode getOrderExpress(String order_number);
	

	
}

