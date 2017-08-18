package com.admin.dao;

import com.admin.model.BaseExample;
import com.admin.model.PayInfo;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface PayInfoDaoMapper extends GenericDao<PayInfo,String> {

    List<PayInfo> selectByExample(BaseExample baseExample);

    List<PayInfo> selectByExampleAndPage(DataTablesPage<PayInfo> page, BaseExample baseExample);
}
