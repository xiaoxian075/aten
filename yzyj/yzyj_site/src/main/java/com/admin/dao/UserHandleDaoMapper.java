package com.admin.dao;

import com.admin.model.BaseExample;
import com.admin.model.UserHandle;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface UserHandleDaoMapper extends GenericDao<UserHandle,String> {
    List<UserHandle> selectByExample(BaseExample baseExample);
    List<UserHandle> selectByExampleAndPage(DataTablesPage<UserHandle> page, BaseExample baseExample);
}
