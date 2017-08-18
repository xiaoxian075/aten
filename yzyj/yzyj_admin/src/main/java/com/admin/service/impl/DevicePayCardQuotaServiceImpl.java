package com.admin.service.impl;

import com.admin.dao.DevicePayCardQuotaDaoMapper;
import com.admin.model.BaseExample;
import com.admin.model.DevicePayCardQuota;
import com.admin.service.DevicePayCardQuotaService;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import com.core.util.CommConstant;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
@Service
public class DevicePayCardQuotaServiceImpl extends GenericServiceImpl<DevicePayCardQuota, String> implements DevicePayCardQuotaService {
    @Resource
    private DevicePayCardQuotaDaoMapper devicePayCardQuotaDaoMapper;
    @Override
    public GenericDao<DevicePayCardQuota, String> getDao() {
        return devicePayCardQuotaDaoMapper;
    }


    public List<DevicePayCardQuota> selectByExampleAndPage(DataTablesPage<DevicePayCardQuota> page, BaseExample baseExample){
        return devicePayCardQuotaDaoMapper.selectByExampleAndPage(page,baseExample);
    }
    public List<DevicePayCardQuota> selectByExample(BaseExample baseExample){
        return devicePayCardQuotaDaoMapper.selectByExample(baseExample);
    }

    public java.util.HashMap selectDevicePaySumFee(java.util.HashMap map){
        return devicePayCardQuotaDaoMapper.selectDevicePaySumFee(map);
    }
    public java.util.HashMap selectDeviceExcessPaySumFee(java.util.HashMap map){
        return devicePayCardQuotaDaoMapper.selectDeviceExcessPaySumFee(map);
    }
    /**
     * 初始化 刷卡限额
     */
    public void initPayCardQuotaData(){
        try{
            BaseExample baseExample = new BaseExample();
            List<DevicePayCardQuota> list = devicePayCardQuotaDaoMapper.selectByExampleAll(baseExample);
            CommConstant.devicePayCardQuotaMapList = new java.util.HashMap();
            for(DevicePayCardQuota cardQuota:list){
                String key = "";
                if(StringUtils.isEmpty(cardQuota.getDeviceUnique())){
                    key = cardQuota.getQuotaGroup()+"";
                }else{
                    key = cardQuota.getQuotaGroup()+"&"+ cardQuota.getDeviceUnique();
                }
                CommConstant.devicePayCardQuotaMapList.put("CardQuota"+key,cardQuota);
            }
        }catch (Exception e ){

        }

    }
}
