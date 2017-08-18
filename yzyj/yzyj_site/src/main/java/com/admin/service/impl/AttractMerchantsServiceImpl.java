package com.admin.service.impl;

import com.admin.dao.AttractMerchantsDaoMapper;
import com.admin.dao.DynamicInfoDaoMapper;
import com.admin.model.AttractMerchants;
import com.admin.model.BaseExample;
import com.admin.model.DynamicInfo;
import com.admin.service.AttractMerchantsService;
import com.admin.service.DynamicInfoService;
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
public class AttractMerchantsServiceImpl extends GenericServiceImpl<AttractMerchants, String> implements AttractMerchantsService {
    @Resource
    private AttractMerchantsDaoMapper attractMerchantsDaoMapper;
    @Override
    public GenericDao<AttractMerchants, String> getDao() {
        return attractMerchantsDaoMapper;
    }


    public List<AttractMerchants> selectByExampleAndPage(DataTablesPage<AttractMerchants> page, BaseExample baseExample){
        return attractMerchantsDaoMapper.selectByExampleAndPage(page,baseExample);
    }
    public List<AttractMerchants> selectByExample(BaseExample baseExample){
        return attractMerchantsDaoMapper.selectByExample(baseExample);
    }


}
