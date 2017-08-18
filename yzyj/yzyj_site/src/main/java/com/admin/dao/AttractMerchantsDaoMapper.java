package com.admin.dao;

import com.admin.model.AttractMerchants;
import com.admin.model.BaseExample;
import com.admin.model.DynamicInfo;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface AttractMerchantsDaoMapper extends GenericDao<AttractMerchants,String> {
    List<AttractMerchants> selectByExample(BaseExample baseExample);
    List<AttractMerchants> selectByExampleAndPage(DataTablesPage<AttractMerchants> page, BaseExample baseExample);
}
