package com.admin.service.impl;

import com.admin.dao.ServiceMarkDaoMapper;
import com.admin.model.BaseExample;
import com.admin.model.ServiceMark;
import com.admin.service.ServiceMarkService;
import com.admin.vo.DictVo;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ${huangdw} on 2017/3/25.
 */
@Service
public class ServiceMarkServiceImpl extends GenericServiceImpl<ServiceMark, String> implements ServiceMarkService {
    @Resource
    private ServiceMarkDaoMapper serviceMarkDaoMapper;
    @Override
    public GenericDao<ServiceMark, String> getDao() {
        return serviceMarkDaoMapper;
    }
    public List<ServiceMark> selectByExampleAndPage(DataTablesPage<ServiceMark> page, BaseExample baseExample){
        return serviceMarkDaoMapper.selectByExampleAndPage(page,baseExample);
    }
    public List<DictVo>selectService(){
        return serviceMarkDaoMapper.selectService();
    }
}
