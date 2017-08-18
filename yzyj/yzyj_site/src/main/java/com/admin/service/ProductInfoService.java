package com.admin.service;

import com.admin.model.BaseExample;
import com.admin.model.ProductInfo;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by wjf on 2016/4/15.
 */
public interface ProductInfoService extends GenericService<ProductInfo,String> {
    List<ProductInfo> selectByExampleAndPage(DataTablesPage<ProductInfo> page, BaseExample baseExample);
    List<ProductInfo> selectByExample(BaseExample baseExample);
}
