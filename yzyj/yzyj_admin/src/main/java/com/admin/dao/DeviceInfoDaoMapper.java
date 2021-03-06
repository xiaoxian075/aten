package com.admin.dao;

import com.admin.model.BaseExample;
import com.admin.model.DeviceInfo;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface DeviceInfoDaoMapper extends GenericDao<DeviceInfo,String> {

    List<DeviceInfo> selectByExample(BaseExample baseExample);


    List<DeviceInfo> selectByExampleAndPage(DataTablesPage<DeviceInfo> page, BaseExample baseExample);


    void deleteInfoDevice(@Param("deviceUnique") String deviceUnique );

    Integer updateState(@Param("state") int state,
                        @Param("deviceUnique") String deviceUnique);

}
