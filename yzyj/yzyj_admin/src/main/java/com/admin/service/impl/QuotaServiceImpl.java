package com.admin.service.impl;

import com.admin.dao.QuotaDaoMapper;
import com.admin.model.BaseExample;
import com.admin.model.Quota;
import com.admin.service.QuotaService;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/11/29.
 */
@Service
public class QuotaServiceImpl extends GenericServiceImpl<Quota, String> implements QuotaService {

    @Resource
    private QuotaDaoMapper quotaDaoMapper;
    @Override
    public GenericDao<Quota, String> getDao() {
        return null;
    }

    @Override
    public List<Quota> selectByExampleAndPage(DataTablesPage<Quota> page, BaseExample baseExample) {
        return quotaDaoMapper.selectByExampleAndPage(page,baseExample);
    }

    @Override
    public Quota selectByQuotaId(int id) {
        return quotaDaoMapper.selectByQuotaId(id);
    }

    @Override
    public void insertQuotaInfo(Quota quota) {
        quotaDaoMapper.insertQuotaInfo(quota);
    }

    @Override
    public void updateQuotaInfo(Quota quota) {
        quotaDaoMapper.updateQuotaInfo(quota);
    }

    @Override
    public Integer selectMinGroup() {
        return quotaDaoMapper.selectMinGroup();
    }
}
