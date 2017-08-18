package com.admin.service;

import com.admin.model.*;
import com.core.mybatis.DataTablesPage;
import com.inter.ReqMsg;
import com.inter.SmsInfoList;

import java.sql.Timestamp;
import java.util.List;


/**
 * @author chenjx
 */
public interface SmsService {

    List<SmsModel> selectList(DataTablesPage<SmsModel> page, BaseExample baseExample);

    boolean insertModel(SmsModel smsModel);

    boolean delModel(Long id);

    SmsModel selectOne(Long id);

    ReqMsg<Object> editModel(SmsModel smsModel);

    boolean synModel(Long id);

    List<SmsModel> signList();

    boolean batchSendSms(List<String> arrMobile, String content);

    List<SmsGroup> selectGroupList(DataTablesPage<SmsGroup> page, BaseExample baseExample);

    List<SmsGroup> selectAllGroup(Integer state);

    boolean insertGroup(String accName);

    boolean delGroup(Long id);

    boolean updateStateGroup(Long id, int state);

    List<SmsAccount> selectAccountList(DataTablesPage<SmsAccount> page, BaseExample baseExample);

    boolean insertAccount(String name, int sex, String phone, long groupId);


    boolean delAccount(Long id);

    List<SmsAccount> selectAllAccount(Long id);

    List<SmsRecord> selectSmsRecordList(DataTablesPage<SmsRecord> page, BaseExample baseExample);

    boolean editAccount(Long id, String accName, Integer addSex, String phone, Long groupId);

    SmsAccount getAccount(Long id);

    boolean checkGroupName(String groupName);

    boolean checkModelName(String title);

    List<SmsAccount> selectAccountByGroupId(Long id);

    boolean saveBatchSendSms(List<String> arrMobile, String content, Timestamp sendTime, Timestamp curTime);

    SmsGroup getGroup(Long groupId);
}
