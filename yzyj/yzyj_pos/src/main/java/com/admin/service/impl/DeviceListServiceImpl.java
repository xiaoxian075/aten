package com.admin.service.impl;

import com.admin.dao.DeviceListDaoMapper;
import com.admin.model.BaseExample;
import com.admin.model.DeviceList;
import com.admin.service.DeviceListService;
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
public class DeviceListServiceImpl extends GenericServiceImpl<DeviceList, String> implements DeviceListService {
    @Resource
    private DeviceListDaoMapper deviceListDaoMapper;

    @Override
    public GenericDao<DeviceList, String> getDao() {
        return deviceListDaoMapper;
    }


    public List<DeviceList> selectByExampleAndPage(DataTablesPage<DeviceList> page, BaseExample baseExample){
        return deviceListDaoMapper.selectByExampleAndPage(page,baseExample);
    }


    public List<DeviceList> selectByExample(BaseExample baseExample){
        return deviceListDaoMapper.selectByExample(baseExample);
    }

    @Override
    public void updateState(String machineCode) {
        deviceListDaoMapper.updateState(machineCode);
    }


}
