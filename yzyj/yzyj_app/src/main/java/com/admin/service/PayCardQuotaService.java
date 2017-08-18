package com.admin.service;

import com.admin.model.BaseExample;
import com.admin.model.DeviceInfo;
import com.admin.model.Order;
import com.admin.model.PayCardQuota;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;
import com.core.util.MapGetterTool;

import java.util.List;

/**
 * Created by wjf on 2016/4/15.
 */
public interface PayCardQuotaService extends GenericService<PayCardQuota,String> {
    List<PayCardQuota> selectByExampleAndPage(DataTablesPage<PayCardQuota> page, BaseExample baseExample);
    List<PayCardQuota> selectByExample(BaseExample baseExample);
}
