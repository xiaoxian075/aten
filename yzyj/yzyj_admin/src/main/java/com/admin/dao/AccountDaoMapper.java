package com.admin.dao;

import com.admin.model.Account;
import com.admin.model.AgencyPerson;
import com.admin.model.BaseExample;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface AccountDaoMapper extends GenericDao<Account,String> {

    List<Account> selectByExample(BaseExample baseExample);

    List<Account> selectByExampleAndPage(DataTablesPage<Account> page, BaseExample baseExample);

}
