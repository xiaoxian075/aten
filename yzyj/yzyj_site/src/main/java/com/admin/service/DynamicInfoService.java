package com.admin.service;

import com.admin.model.BaseExample;
import com.admin.model.DictTable;
import com.admin.model.DynamicInfo;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by wjf on 2016/4/15.
 */
public interface DynamicInfoService extends GenericService<DynamicInfo,String> {
    List<DynamicInfo> selectByExampleAndPage(DataTablesPage<DynamicInfo> page, BaseExample baseExample);
    List<DynamicInfo> selectByExample(BaseExample baseExample);
}
