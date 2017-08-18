package com.admin.service;

import com.admin.model.BaseExample;
import com.admin.model.WithdrawRecord;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2016/11/15.
 */
public interface WithdrawRecordService extends GenericService<WithdrawRecord,String> {

    List<WithdrawRecord> selectByExampleAndPage(DataTablesPage<WithdrawRecord> page, BaseExample baseExample);

    List<WithdrawRecord> selectByExampleAndPage1(DataTablesPage<WithdrawRecord> page, BaseExample baseExample);

    Integer getCountWithdraw();

    Integer getCountWithdrawByAgent(String agentUnique);
}
