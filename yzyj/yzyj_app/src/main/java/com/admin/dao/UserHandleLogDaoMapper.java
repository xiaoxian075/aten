package com.admin.dao;

import com.admin.model.BaseExample;
import com.admin.model.UserHandleLog;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface UserHandleLogDaoMapper extends GenericDao<UserHandleLog,String> {
    List<UserHandleLog> selectByExample(BaseExample baseExample);
    List<UserHandleLog> selectByExampleAndPage(DataTablesPage<UserHandleLog> page, BaseExample baseExample);
}
