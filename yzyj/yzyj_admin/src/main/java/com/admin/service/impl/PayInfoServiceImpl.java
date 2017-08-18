package com.admin.service.impl;

import com.admin.dao.PayInfoDaoMapper;
import com.admin.model.BaseExample;
import com.admin.model.PayInfo;
import com.admin.service.PayInfoService;
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
public class PayInfoServiceImpl extends GenericServiceImpl<PayInfo, String> implements PayInfoService {
    @Resource
    private PayInfoDaoMapper payInfoDaoMapper;

    @Override
    public GenericDao<PayInfo, String> getDao() {
        return payInfoDaoMapper;
    }


    public List<PayInfo> selectByExampleAndPage(DataTablesPage<PayInfo> page, BaseExample baseExample){
        return payInfoDaoMapper.selectByExampleAndPage(page,baseExample);
    }
    public List<PayInfo> selectByExample(BaseExample baseExample){
        return payInfoDaoMapper.selectByExample(baseExample);
    }

}
