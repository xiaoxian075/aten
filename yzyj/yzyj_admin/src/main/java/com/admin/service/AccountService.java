package com.admin.service;

import com.admin.model.Account;
import com.admin.model.BaseExample;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2016/12/14.
 */
public interface AccountService extends GenericService<Account,String> {

    List<Account> selectByExampleAndPage(DataTablesPage<Account> page, BaseExample baseExample);

    List<Account> selectByExample(BaseExample baseExample);

}
