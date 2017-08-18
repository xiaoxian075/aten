package com.admin.service.impl;

import com.admin.dao.PayCardQuotaDaoMapper;
import com.admin.model.BaseExample;
import com.admin.model.PayCardQuota;
import com.admin.service.PayCardQuotaService;
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
public class PayCardQuotaServiceImpl extends GenericServiceImpl<PayCardQuota, String> implements PayCardQuotaService {
    @Resource
    private PayCardQuotaDaoMapper payCardQuotaDaoMapper;
    @Override
    public GenericDao<PayCardQuota, String> getDao() {
        return payCardQuotaDaoMapper;
    }


    public List<PayCardQuota> selectByExampleAndPage(DataTablesPage<PayCardQuota> page, BaseExample baseExample){
        return payCardQuotaDaoMapper.selectByExampleAndPage(page,baseExample);
    }
    public List<PayCardQuota> selectByExample(BaseExample baseExample){
        return payCardQuotaDaoMapper.selectByExample(baseExample);
    }


}
