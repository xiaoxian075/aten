package com.admin.service.impl;

import com.admin.dao.ApplyMerchantsDaoMapper;
import com.admin.model.ApplyMerchants;
import com.admin.model.BaseExample;
import com.admin.service.ApplyMerchantsService;
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
public class ApplyMerchantsServiceImpl extends GenericServiceImpl<ApplyMerchants, String> implements ApplyMerchantsService {
    @Resource
    private ApplyMerchantsDaoMapper applyMerchantsDaoMapper;
    @Override
    public GenericDao<ApplyMerchants, String> getDao() {
        return applyMerchantsDaoMapper;
    }

    public List<ApplyMerchants> selectByExampleAndPage(DataTablesPage<ApplyMerchants> page, BaseExample baseExample){
        return applyMerchantsDaoMapper.selectByExampleAndPage(page,baseExample);
    }
    public List<ApplyMerchants> selectByExample(BaseExample baseExample){
        return applyMerchantsDaoMapper.selectByExample(baseExample);
    }


}
