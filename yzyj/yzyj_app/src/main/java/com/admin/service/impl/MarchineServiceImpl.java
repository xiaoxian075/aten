package com.admin.service.impl;

import com.admin.dao.MarchineDaoMapper;
import com.admin.model.BaseExample;
import com.admin.model.Marchine;
import com.admin.service.MarchineService;
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
public class MarchineServiceImpl extends GenericServiceImpl<Marchine, String> implements MarchineService {
    @Resource
    private MarchineDaoMapper marchineDaoMapper;
    @Override
    public GenericDao<Marchine, String> getDao() {
        return marchineDaoMapper;
    }


    public List<Marchine> selectByExampleAndPage(DataTablesPage<Marchine> page, BaseExample baseExample){
        return marchineDaoMapper.selectByExampleAndPage(page,baseExample);
    }
    public List<Marchine> selectByExample(BaseExample baseExample){
        return marchineDaoMapper.selectByExample(baseExample);
    }

}
