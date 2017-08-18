package com.admin.service.impl;

import com.admin.model.BaseExample;
import com.admin.model.DictTable;
import com.admin.service.DevicePayCardQuotaService;
import com.admin.service.DictTableService;
import com.admin.service.InitDataService;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
@Service
public class InitDataServiceImpl extends GenericServiceImpl<DictTable, String> implements InitDataService {
    @Resource
    private DictTableService dictTableService;
    @Resource
    private DevicePayCardQuotaService devicePayCardQuotaService;
    @Override
    public GenericDao<DictTable, String> getDao() {
        return null;
    }


    public List<DictTable> selectByExampleAndPage(DataTablesPage<DictTable> page, BaseExample baseExample){
        return null;
    }

    public List<DictTable> selectByExample(BaseExample baseExample){
        return null;
    }

    @PostConstruct
    public void InitMemoryData(){
        try{
            long startLong = System.currentTimeMillis();
            /**
             * 初始化字典数据
             */
            dictTableService.initDictData();
            devicePayCardQuotaService.initPayCardQuotaData();
            long runLong = System.currentTimeMillis() - startLong;
            System.out.println("----------------------初始化内存完成----------------------------"+(runLong / 1000) + "秒");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
