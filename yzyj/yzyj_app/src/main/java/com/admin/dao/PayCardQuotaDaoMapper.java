package com.admin.dao;

import com.admin.model.BaseExample;
import com.admin.model.PayCardQuota;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface PayCardQuotaDaoMapper extends GenericDao<PayCardQuota,String> {
    List<PayCardQuota> selectByExample(BaseExample baseExample);
    List<PayCardQuota> selectByExampleAndPage(DataTablesPage<PayCardQuota> page, BaseExample baseExample);
    java.util.HashMap selectYunIdPayCount(java.util.HashMap map);
}
