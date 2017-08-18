package com.aten.service.impl;

import com.aten.model.orm.AccountBalanceApprove;
import com.aten.model.orm.AccountBill;
import com.aten.model.orm.BillFlow;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aten.dao.AccountBalanceApproveDao;
import com.aten.dao.AccountBillDao;
import com.aten.dao.AccountDao;
import com.aten.dao.BillFlowDao;
import com.aten.dao.ManaFundsDao;
import com.aten.service.AccountBalanceApproveService;
import com.communal.util.FunUtil;
import com.communal.util.RandomCharUtil;

@Service("accountBalanceApproveService")
public class AccountBalanceApproveServiceImpl extends CommonServiceImpl<AccountBalanceApprove, String>
		implements AccountBalanceApproveService {

	private AccountBalanceApproveDao accountBalanceApproveDao;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AccountBillDao accountBillDao;
	@Autowired
	private BillFlowDao billFlowDao;
	@Autowired
	private ManaFundsDao manaFundsDao;

	@Autowired
	public AccountBalanceApproveServiceImpl(AccountBalanceApproveDao accountBalanceApproveDao) {
		super(accountBalanceApproveDao);
		this.accountBalanceApproveDao = accountBalanceApproveDao;
	}

	@Override
	@Transactional
	public void approve(AccountBalanceApprove accountBalanceApprove) {
		accountBalanceApproveDao.update(accountBalanceApprove);
		if ("1".equals(accountBalanceApprove.getAudit_state())) {
			HashMap<String, Object> paraMap = new HashMap<String, Object>();
			String amount = "0";
			String bill_name = "";
			if ("2".equals(accountBalanceApprove.getIo_type())) {
				amount = accountBalanceApprove.getApprove_amount();
				bill_name = "系统充值";
			} else if ("1".equals(accountBalanceApprove.getIo_type())) {
				amount = "-" + accountBalanceApprove.getApprove_amount();
				bill_name = "系统扣减";
			}
			paraMap.put("amount", amount);
			paraMap.put("account", accountBalanceApprove.getLogin_name());
			accountDao.updateBalance(paraMap);
			// 会员帐单
			String ralationId = RandomCharUtil.randOrderNumber();
			AccountBill accountBill = new AccountBill();
			accountBill.setBill_number(ralationId);
			accountBill.setAccount_id(new BigInteger(accountBalanceApprove.getAccount_id()));
			accountBill.setBill_name(bill_name);
			accountBill.setPay_way(1);
			if ("1".equals(accountBalanceApprove.getIo_type())) {
				accountBill.setBill_type(2);
			} else if ("2".equals(accountBalanceApprove.getIo_type())) {
				accountBill.setBill_type(1);
			}
			accountBill.setIo_type(Integer.valueOf(accountBalanceApprove.getIo_type()));
			accountBill.setAmount(new BigInteger(accountBalanceApprove.getApprove_amount()));
			accountBill.setNote(accountBalanceApprove.getSubmitter_note());
			accountBillDao.insert(accountBill);

			String balance = "0";
			if ("2".equals(accountBalanceApprove.getIo_type())) {
				balance = "-" + accountBalanceApprove.getApprove_amount();
			} else if ("1".equals(accountBalanceApprove.getIo_type())) {
				balance = accountBalanceApprove.getApprove_amount();
			}
			Map<String, Object> para = new HashMap<String, Object>();
			para.put("fund_id", 1);
			para.put("balance", balance);
			manaFundsDao.updateBalance(para);
			// 运营商资金记录流
			BillFlow billFlow = new BillFlow();
			billFlow.setSeller_id(new BigInteger("0"));
			billFlow.setTrade_id(FunUtil.randNumID());
			billFlow.setBill_amount(new BigInteger(accountBalanceApprove.getApprove_amount()));
			billFlow.setBill_time(new Timestamp(System.currentTimeMillis()));
			if ("1".equals(accountBalanceApprove.getIo_type())) {
				billFlow.setBill_type(1);// 收入
				billFlow.setOrder_type_name("系统充值");
				billFlow.setOrder_type("3");
				// 0：消费 1：退款 2：系统扣减
				// 3：系统充值 4:提现
				// 5：VIP升级 6:订金 7：尾款
			} else if ("2".equals(accountBalanceApprove.getIo_type())) {
				billFlow.setBill_type(0);// 支出
				billFlow.setOrder_type_name("系统扣减");
				billFlow.setOrder_type("2");
				// 0：消费 1：退款 2：系统扣减
				// 3：系统充值 4:提现
				// 5：VIP升级 6:订金 7：尾款
			}
			billFlow.setPay_way("1");
			billFlow.setPay_way_name("余额支付");
			billFlow.setRalation_id(ralationId);
			billFlow.setNote(accountBalanceApprove.getSubmitter_note());
			billFlow.setOper_man(accountBalanceApprove.getApprove_mana_name());
			billFlow.setOper_time(new Timestamp(System.currentTimeMillis()));
			billFlowDao.insert(billFlow);
		}
	}

}
