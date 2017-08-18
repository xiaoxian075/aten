package com.aten.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.aten.model.orm.OrderAuxiliary;
import com.aten.model.orm.OrderDeposit;
import com.aten.model.orm.OrderDetailNode;
import com.aten.model.orm.OrderExpressNode;
import com.aten.model.orm.OrderFullbook;
import com.aten.model.orm.OrderNode;
import com.aten.model.orm.OrderTransNode;

public interface OrderDao {
	
	int selectcountOrderByPage(Map<String, Object> paraCount);
	
	List<OrderNode> selectlistOrderByPage(Map<String, Object> paraMap);

	List<OrderDetailNode> selectlistOrderDetailByOrderid(BigInteger order_id);

	OrderNode selectoneOrderByOrderid(BigInteger order_id);
	
	OrderNode selectoneOrderByOrderNumber(String order_number);
	
	OrderExpressNode selectonOrderExpressByOrderid(String order_number);

	int updateoneOrderForOrderstate(Map<String,Object> paraMap);

	int updateoneOrderDetailForChangpriceById(Map<String, Object> paraMap);

	int updateoneOrderForChangpriceById(Map<String, Object> paraMap);

	int updateoneOrderExpressForExpress(Map<String, Object> paraMap);

	int updateoneOrderExpressForExpressSendgoods(Map<String, Object> paraMap);

	int insertoneOrderTrans(OrderTransNode trans);

	OrderFullbook selectoneOrderFullbookByOrderid(BigInteger order_id);

	OrderDeposit selectoneOrderDepositbookByOrderid(BigInteger order_id);

	OrderAuxiliary selectoneOrderAuxiliaryByOrderid(String order_number);

	int updateoneOrderDetailForSendgoods(Map<String, Object> paraMap);

	OrderDetailNode selectoneOrderDetailById(BigInteger detail_id);
	OrderDetailNode selectoneOrderDetailByNumber(String detail_number);

	int updateoneOrderDetailForStateById(Map<String, Object> paraMap);

	int getcountOrderExpressByFastcode(String fast_code);

	

}
