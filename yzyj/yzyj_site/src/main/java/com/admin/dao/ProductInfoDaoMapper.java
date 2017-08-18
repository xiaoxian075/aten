package com.admin.dao;

import com.admin.model.BaseExample;
import com.admin.model.ProductInfo;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface ProductInfoDaoMapper extends GenericDao<ProductInfo,String> {
    List<ProductInfo> selectByExample(BaseExample baseExample);
    List<ProductInfo> selectByExampleAndPage(DataTablesPage<ProductInfo> page, BaseExample baseExample);
}
