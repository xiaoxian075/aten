package com.admin.dao;

import com.admin.model.ApplyMerchants;
import com.admin.model.BaseExample;
import com.admin.model.DictTable;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface ApplyMerchantsDaoMapper extends GenericDao<ApplyMerchants,String> {
    List<ApplyMerchants> selectByExample(BaseExample baseExample);
    List<ApplyMerchants> selectByExampleAndPage(DataTablesPage<ApplyMerchants> page, BaseExample baseExample);
}
