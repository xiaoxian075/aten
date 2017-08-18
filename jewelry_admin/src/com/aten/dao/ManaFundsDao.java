package com.aten.dao;

import java.math.BigInteger;
import java.util.Map;

import com.aten.model.orm.ManaBills;
import com.aten.model.orm.ManaFunds;

public interface ManaFundsDao  extends CommonDao<ManaFunds, String>{

	ManaFunds getBySellerid(BigInteger seller_id);

	int updateoneBalanceById(Map<String, Object> paraMap);

	int updateBalance(Map<String, Object> para);

}
