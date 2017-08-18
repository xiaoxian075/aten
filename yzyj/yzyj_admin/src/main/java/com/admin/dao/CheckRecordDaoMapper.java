package com.admin.dao;

import com.admin.model.*;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface CheckRecordDaoMapper extends GenericDao<AgencyPerson,String> {

    List<CheckRecord> selectByExample(BaseExample baseExample);

    List<CheckRecord> selectByExampleAndPage(DataTablesPage<CheckRecord> page, BaseExample baseExample);

    List<InComeRecord> selectByExampleAndPage1(DataTablesPage<InComeRecord> page, BaseExample baseExample);

    CheckRecord getCheckRecordByDay(BaseExample baseExample);

    List<InComeRecord> getInComeRecordDay(HashMap hashMap);

    List<InComeRecord> getInComeRecordMonth(HashMap hashMap);

    List<InComeRecord> searchInComeList(InComeRecord inComeRecord);
}
