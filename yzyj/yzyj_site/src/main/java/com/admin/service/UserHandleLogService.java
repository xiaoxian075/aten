package com.admin.service;

import com.admin.model.BaseExample;
import com.admin.model.UserHandleLog;
import com.admin.vo.UserHandleLogVo;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface UserHandleLogService extends GenericService<UserHandleLog,String> {
    List<UserHandleLog> selectByExampleAndPage(DataTablesPage<UserHandleLog> page, BaseExample baseExample);
    List<UserHandleLog> selectByExample(BaseExample baseExample);
    Integer insertUserHandleCompare(UserHandleLogVo userHandleLogVo);
    Integer insertUserHandleLog(Object thisObj, Integer handType, String body);
}
