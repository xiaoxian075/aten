package com.admin.service;

import com.admin.model.*;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */
public interface CheckRecordService extends GenericService<CheckRecord,String> {

    List<CheckRecord> selectByExampleAndPage(DataTablesPage<CheckRecord> page, BaseExample baseExample);

    List<InComeRecord> selectByExampleAndPage1(DataTablesPage<InComeRecord> page, BaseExample baseExample);

    List<CheckRecord> selectByExample(BaseExample baseExample);

    CheckRecord getCheckRecordByDay(BaseExample baseExample);

    List<InComeRecord> getInComeRecordDay(HashMap hashMap);

    List<InComeRecord> getInComeRecordMonth(HashMap hashMap);

    List<InComeRecord> searchInComeList(InComeRecord inComeRecord);

}
