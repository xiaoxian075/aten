package com.aten.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.aten.model.orm.OrderRefundLogsNode;
import com.aten.model.orm.OrderRefundNode;

public interface OrderRefundDao {

	int selectcountRefundByPage(Map<String, Object> paraMap);

	List<OrderRefundNode> selectlistRefundByPage(Map<String, Object> paraMap);

	OrderRefundNode selectoneRefundById(String refund_id);

	List<OrderRefundLogsNode> selectlistRefundLogsByRefundid(String refund_id);

	OrderRefundLogsNode selectoneRefundLogsById(BigInteger refund_log_id);

	//int updateoneRefundLogsForGrant(Map<String, Object> paraMap);

	int updateoneRefundForGrant(Map<String, Object> paraMap);

	int insertoneRefundLogs(OrderRefundLogsNode refundLogs);
	
}


