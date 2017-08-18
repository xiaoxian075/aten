package com.admin.service;

import com.admin.model.BaseExample;
import com.admin.model.UserHandle;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface UserHandleService extends GenericService<UserHandle,String> {

    List<UserHandle> selectByExampleAndPage(DataTablesPage<UserHandle> page, BaseExample baseExample);

    List<UserHandle> selectByExample(BaseExample baseExample);
}
