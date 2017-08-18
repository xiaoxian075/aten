package com.admin.dao;

import com.admin.model.BaseExample;
import com.admin.model.WithdrawRecord;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2016/11/15.
 */
public interface WithdrawRecordDaoMapper extends GenericDao<WithdrawRecord,String> {

    List<WithdrawRecord> selectByExampleAndPage(DataTablesPage<WithdrawRecord> page, BaseExample baseExample);

    List<WithdrawRecord> selectByExampleAndPage1(DataTablesPage<WithdrawRecord> page, BaseExample baseExample);

    Integer getCountWithdraw();

    Integer getCountWithdrawByAgent(@Param("agentUnique") String agentUnique);
}
