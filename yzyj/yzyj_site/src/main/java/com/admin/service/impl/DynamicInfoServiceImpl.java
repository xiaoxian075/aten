package com.admin.service.impl;

import com.admin.dao.DynamicInfoDaoMapper;
import com.admin.model.BaseExample;
import com.admin.model.DynamicInfo;
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
public class DynamicInfoServiceImpl extends GenericServiceImpl<DynamicInfo, String> implements DynamicInfoService {
    @Resource
    private DynamicInfoDaoMapper dynamicInfoDaoMapper;
    @Override
    public GenericDao<DynamicInfo, String> getDao() {
        return dynamicInfoDaoMapper;
    }


    public List<DynamicInfo> selectByExampleAndPage(DataTablesPage<DynamicInfo> page, BaseExample baseExample){
        return dynamicInfoDaoMapper.selectByExampleAndPage(page,baseExample);
    }
    public List<DynamicInfo> selectByExample(BaseExample baseExample){
        return dynamicInfoDaoMapper.selectByExample(baseExample);
    }


}
