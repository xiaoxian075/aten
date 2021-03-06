package com.admin.service.impl;

import com.admin.dao.DeviceInfoDaoMapper;
import com.admin.model.BaseExample;
import com.admin.model.DeviceInfo;
import com.admin.service.DeviceInfoService;
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
public class DeviceInfoServiceImpl extends GenericServiceImpl<DeviceInfo, String> implements DeviceInfoService {
    @Resource
    private DeviceInfoDaoMapper deviceInfoDaoMapper;
    @Override
    public GenericDao<DeviceInfo, String> getDao() {
        return deviceInfoDaoMapper;
    }


    public List<DeviceInfo> selectByExampleAndPage(DataTablesPage<DeviceInfo> page, BaseExample baseExample){
        return deviceInfoDaoMapper.selectByExampleAndPage(page,baseExample);
    }
    public List<DeviceInfo> selectByExample(BaseExample baseExample){
        return deviceInfoDaoMapper.selectByExample(baseExample);
    }
    public List<DeviceInfo> selectMachineInfo(BaseExample baseExample){
        return deviceInfoDaoMapper.selectMachineInfo(baseExample);
    }
    public DeviceInfo selectToken(String token){
        return deviceInfoDaoMapper.selectToken(token);
    }

    @Override
    public void deleteInfoDevice(String deviceUnique) {
        deviceInfoDaoMapper.deleteInfoDevice(deviceUnique);
    }
}
