package com.admin.service;

import com.admin.model.BaseExample;
import com.admin.model.PosWithdrawRecord;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2017/1/12.
 */
public interface PosWithdrawService extends GenericService<PosWithdrawRecord,String> {

    Integer getPosWithdrawListFromYunPay();

    List<PosWithdrawRecord> selectByExampleAndPage(DataTablesPage<PosWithdrawRecord> page, BaseExample baseExample);

    PosWithdrawRecord getPosWithdrawRecordById(int id);

    void updateResult(PosWithdrawRecord posWithdrawRecord,String money);

}
