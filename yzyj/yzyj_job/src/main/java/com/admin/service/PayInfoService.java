package com.admin.service;

import com.admin.model.BaseExample;
import com.admin.model.PayInfo;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by wjf on 2016/4/15.
 */
public interface PayInfoService extends GenericService<PayInfo,String> {

    List<PayInfo> selectByExampleAndPage(DataTablesPage<PayInfo> page, BaseExample baseExample);

    List<PayInfo> selectByExample(BaseExample baseExample);

    Integer updateOrderInfo();

}
