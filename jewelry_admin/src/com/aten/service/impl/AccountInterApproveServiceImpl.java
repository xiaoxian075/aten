package com.aten.service.impl;


import com.aten.model.orm.AccountIntegralLog;
import com.aten.model.orm.AccountInterApprove;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aten.dao.AccountDao;
import com.aten.dao.AccountIntegralLogDao;
import com.aten.dao.AccountInterApproveDao;
import com.aten.service.AccountInterApproveService;

@Service("accountInterApproveService")
public class AccountInterApproveServiceImpl extends CommonServiceImpl<AccountInterApprove, String>
		implements AccountInterApproveService {

	private AccountInterApproveDao accountInterApproveDao;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AccountIntegralLogDao accountIntegralLogDao;

	@Autowired
	public AccountInterApproveServiceImpl(AccountInterApproveDao accountInterApproveDao) {
		super(accountInterApproveDao);
		this.accountInterApproveDao = accountInterApproveDao;
	}

	@Override
	@Transactional
	public void approve(AccountInterApprove accountInterApprove) {
		accountInterApproveDao.update(accountInterApprove);
		if ("1".equals(accountInterApprove.getAudit_state())) {
			HashMap<String, Object> paraMap = new HashMap<String, Object>();
			String integral = "0";
			String type = "";
			if ("2".equals(accountInterApprove.getIo_type())) {
				integral = accountInterApprove.getInter_value();
				type = "5";
			} else if ("1".equals(accountInterApprove.getIo_type())) {
				integral = "-" + accountInterApprove.getInter_value();
				type = "6";
			}
			paraMap.put("integral", integral);
			paraMap.put("account", accountInterApprove.getLogin_name());
			accountDao.updateIntegral(paraMap);
			// 积分帐单
			AccountIntegralLog alog = new AccountIntegralLog();
			alog.setAccount_id(accountInterApprove.getAccount_id());
			alog.setIntegral(accountInterApprove.getInter_value());
			alog.setType(type);
			alog.setState("1");
			alog.setIo_type(accountInterApprove.getIo_type());
			accountIntegralLogDao.insert(alog);
		}
	}

}
