package com.admin.dao;


import com.admin.model.BaseExample;
import com.admin.model.SmsAccount;
import com.core.mybatis.DataTablesPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SmsAccountDaoMapper {

    List<SmsAccount> selectList(DataTablesPage<SmsAccount> page, BaseExample baseExample);

    SmsAccount selectOne(Long id);

    int insertOne(SmsAccount smsAccount);

    int deleteOne(Long id);

    List<SmsAccount> selectAllAccount(Map<String, Object> params);

    int deleteByGroupid(Long groupId);

    int updateOne(SmsAccount smsAccount);

    List<SmsAccount> selectAccountByGroupId(Long groupId);
}
