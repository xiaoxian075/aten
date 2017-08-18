package com.admin.dao;

import com.admin.model.BaseExample;
import com.admin.model.SmsModel;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * @author chenjx
 */
public interface SmsDaoMapper  {

    List<SmsModel> selectList(DataTablesPage<SmsModel> page, BaseExample baseExample);

    int insertOne(SmsModel smsModel);

    int deleteOne(Long id);

    SmsModel selectOne(Long id);

    int updateOne(SmsModel smsModel);

    List<SmsModel> signList();

    Long selectCountByName(String title);
}
