package com.admin.dao;

import com.admin.model.BaseExample;
import com.admin.model.PosWithdrawRecord;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/1/14.
 */
public interface PosWithdrawDaoMapper  extends GenericDao<PosWithdrawRecord,String> {

    List<PosWithdrawRecord> selectByExampleAndPage(DataTablesPage<PosWithdrawRecord> page, BaseExample baseExample);

    void inserBatchPosWithdraw(List<PosWithdrawRecord> posWithdrawRecord);

    PosWithdrawRecord getPosWithdrawRecordById(@Param("id") int id);

    void updateResult(PosWithdrawRecord posWithdrawRecord);

    void deleteSend(@Param("pid") int pid);

    PosWithdrawRecord getCountWithdraw();

    List<PosWithdrawRecord> searchSendRecordList(PosWithdrawRecord posWithdrawRecord);
}
