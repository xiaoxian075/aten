package com.admin.service;

import com.admin.model.ApplyMerchants;
import com.admin.model.AttractMerchants;
import com.admin.model.BaseExample;
import com.admin.model.DictTable;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by wjf on 2016/4/15.
 */
public interface ApplyMerchantsService extends GenericService<ApplyMerchants,String> {
    List<ApplyMerchants> selectByExampleAndPage(DataTablesPage<ApplyMerchants> page, BaseExample baseExample);
    List<ApplyMerchants> selectByExample(BaseExample baseExample);
}
