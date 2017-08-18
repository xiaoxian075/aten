package com.admin.dao;


import com.admin.model.BaseExample;
import com.admin.model.SmsGroup;
import com.core.mybatis.DataTablesPage;

import java.util.List;
import java.util.Map;

public interface SmsGroupDaoMapper {

    List<SmsGroup> selectList(DataTablesPage<SmsGroup> page, BaseExample baseExample);

    boolean insertOne(SmsGroup smsGroup);

    int deleteOne(Long id);

    int updateOne(Map<String, Object> params);

    SmsGroup selectOne(Long groupId);

    List<SmsGroup> selectAll(Map<String, Object> params);

    int updateOneCountMore(Map<String,Object> params);

    int updateOneCountLess(Map<String,Object> params);

    Long selectCountByName(String name);
}
