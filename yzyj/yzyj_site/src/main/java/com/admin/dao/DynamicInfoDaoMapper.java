package com.admin.dao;

import com.admin.model.BaseExample;
import com.admin.model.DictTable;
import com.admin.model.DynamicInfo;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface DynamicInfoDaoMapper extends GenericDao<DynamicInfo,String> {
    List<DynamicInfo> selectByExample(BaseExample baseExample);
    List<DynamicInfo> selectByExampleAndPage(DataTablesPage<DynamicInfo> page, BaseExample baseExample);
}
