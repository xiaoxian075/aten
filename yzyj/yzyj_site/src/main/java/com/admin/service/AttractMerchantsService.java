package com.admin.service;

import com.admin.model.AttractMerchants;
import com.admin.model.BaseExample;
import com.admin.model.DynamicInfo;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by wjf on 2016/4/15.
 */
public interface AttractMerchantsService extends GenericService<AttractMerchants,String> {
    List<AttractMerchants> selectByExampleAndPage(DataTablesPage<AttractMerchants> page, BaseExample baseExample);
    List<AttractMerchants> selectByExample(BaseExample baseExample);
}
