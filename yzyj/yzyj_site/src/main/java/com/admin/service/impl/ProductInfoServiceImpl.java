package com.admin.service.impl;

import com.admin.dao.ProductInfoDaoMapper;
import com.admin.model.BaseExample;
import com.admin.model.ProductInfo;
import com.admin.service.ProductInfoService;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
@Service
public class ProductInfoServiceImpl extends GenericServiceImpl<ProductInfo, String> implements ProductInfoService {
    @Resource
    private ProductInfoDaoMapper productInfoDaoMapper;
    @Override
    public GenericDao<ProductInfo, String> getDao() {
        return productInfoDaoMapper;
    }


    public List<ProductInfo> selectByExampleAndPage(DataTablesPage<ProductInfo> page, BaseExample baseExample){
        return productInfoDaoMapper.selectByExampleAndPage(page,baseExample);
    }
    public List<ProductInfo> selectByExample(BaseExample baseExample){
        return productInfoDaoMapper.selectByExample(baseExample);
    }


}
