package com.admin.service.impl;

import com.admin.model.Account;
import com.admin.model.BaseExample;
import com.admin.service.AccountService;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/12/14.
 */
@Service
public class AccountServiceImpl extends GenericServiceImpl<Account, String> implements AccountService {
    @Override
    public List<Account> selectByExampleAndPage(DataTablesPage<Account> page, BaseExample baseExample) {
        return null;
    }

    @Override
    public List<Account> selectByExample(BaseExample baseExample) {
        return null;
    }

    @Override
    public GenericDao<Account, String> getDao() {
        return null;
    }
}
