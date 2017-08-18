package com.admin.service.impl;

import com.admin.dao.UserHandleDaoMapper;
import com.admin.model.BaseExample;
import com.admin.model.UserHandle;
import com.admin.service.UserHandleService;
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
public class UserHandleServiceImpl extends GenericServiceImpl<UserHandle, String> implements UserHandleService {
    @Resource
    private UserHandleDaoMapper userHandleDaoMapper;
    @Override
    public GenericDao<UserHandle, String> getDao() {
        return userHandleDaoMapper;
    }


    public List<UserHandle> selectByExampleAndPage(DataTablesPage<UserHandle> page, BaseExample baseExample){
        return userHandleDaoMapper.selectByExampleAndPage(page,baseExample);
    }
    public List<UserHandle> selectByExample(BaseExample baseExample){
        return userHandleDaoMapper.selectByExample(baseExample);
    }

}
