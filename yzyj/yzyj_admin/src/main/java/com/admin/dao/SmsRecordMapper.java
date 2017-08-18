package com.admin.dao;


import com.admin.model.BaseExample;
import com.admin.model.SmsRecord;
import com.core.mybatis.DataTablesPage;

import java.util.List;

public interface SmsRecordMapper {

    int insertOne(SmsRecord record);

    List<SmsRecord> selectList(DataTablesPage<SmsRecord> page, BaseExample baseExample);
}
