package com.admin.service.impl;

import com.admin.dao.AgencyPersonDaoMapper;
import com.admin.dao.DeviceListDaoMapper;
import com.admin.model.*;
import com.admin.service.DeviceInfoService;
import com.admin.service.DeviceListService;
import com.admin.vo.CommAppVo;
import com.alibaba.fastjson.JSONObject;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import com.core.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
@Service
public class DeviceListServiceImpl extends GenericServiceImpl<DeviceList, String> implements DeviceListService {
    @Resource
    private DeviceListDaoMapper deviceListDaoMapper;
    @Resource
    private DeviceInfoService deviceInfoService;
    @Resource
    private AgencyPersonDaoMapper agencyPersonDaoMapper;

    @Override
    public GenericDao<DeviceList, String> getDao() {
        return deviceListDaoMapper;
    }


    public List<DeviceList> selectByExampleAndPage(DataTablesPage<DeviceList> page, BaseExample baseExample){
        return deviceListDaoMapper.selectByExampleAndPage(page,baseExample);
    }

    public List<DeviceList> selectAllByExampleAndPage(DataTablesPage<DeviceList> page, BaseExample baseExample){
        return deviceListDaoMapper.selectByExampleAndPage(page,baseExample);
    }


    public List<DeviceList> selectByExample(BaseExample baseExample){
        return deviceListDaoMapper.selectByExample(baseExample);
    }

    @Override
    public List<DeviceList> getMoneyByAgentUnique(String agentUnique,String type) {
        return deviceListDaoMapper.getMoneyByAgentUnique(agentUnique,type);
    }

    @Override
    public List<DeviceList> getMoneyByAgentUniqueAndDay(String agentUnique,String type) {
        return deviceListDaoMapper.getMoneyByAgentUniqueAndDay(agentUnique,type);
    }

    public Integer deleteAgentDevice(DeviceList deviceList ){
        Integer state = 0;
        state = deviceListDaoMapper.deleteAgentDevice(deviceList);
        return state;
    }
    @Transactional
    public Integer insertAgentDevice(User user, DeviceList deviceList ){
        Integer state = 0;
        try{
            String deviceUnique = FunUtil.randNumID();
            Integer maxId = deviceListDaoMapper.selectMaxId();
            if(StringUtils.isEmpty(maxId)){
                maxId = 1;
            }
            maxId = 600000+maxId;
            deviceList.setDeviceCode(maxId+"");
            deviceList.setActivationCode(FunUtil.getRandom(6));
            deviceList.setAgentUnique(user.getAgentUnique());
            deviceList.setDeviceUnique(deviceUnique);
            deviceList.setCreateTime(new Date());
            deviceList.setState("0");
            deviceList.setSubsidyStatus("0");
            deviceListDaoMapper.insertSelective(deviceList);
            //添加设备初始到  ，设备信息表中
            DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.setDeviceUnique(deviceList.getAgentUnique());
            deviceInfo.setMachineCode(deviceList.getDeviceCode());
            deviceInfo.setActivationCode(deviceList.getActivationCode());
            deviceInfo.setDeviceUnique(deviceList.getDeviceUnique());
            deviceInfoService.insertSelective(deviceInfo);
            state = 1;
        }catch (Exception e ){
            e.printStackTrace();
        }
        return state;
    }

    @Override
    public Long getCountMoneyByAgentUnique(String agentUnique) {
        return deviceListDaoMapper.getCountMoneyByAgentUnique(agentUnique);
    }

    @Override
    public void updateApproval(int id, int approvalStatus,int state) {
        deviceListDaoMapper.updateApproval(id,approvalStatus,state);
    }

    @Override
    public void updateByMId(String agentUnique, String id) {
        deviceListDaoMapper.updateByMId(agentUnique,id);
    }

    @Override
    public List<DeviceList> getDeviceListByYId(String loginName) {
        return deviceListDaoMapper.getDeviceListByYId(loginName);
    }

    @Override
    public void updateYunId(String merchantYunPayAccountUpdate, String merchantYunPayAccount) {
        deviceListDaoMapper.updateYunId(merchantYunPayAccountUpdate,merchantYunPayAccount);
    }

    @Override
    public void updateOneKey(int state) {
        deviceListDaoMapper.updateOneKey(state);
    }

    @Override
    public DeviceList getDeviceListByDId(String id) {
        return deviceListDaoMapper.getDeviceListByDId(id);
    }

    @Override
    public void updateGroup(String devicUnique, String group) {
        deviceListDaoMapper.updateGroup(devicUnique,group);
    }

    @Override
    public void updateSubsidyStatus(String id, String subsidyStatus) {
        String time  = FunUtil.fromDateH(new Date());
        deviceListDaoMapper.updateSubsidyStatus(id,subsidyStatus,time);
    }

    @Override
    public Integer updateStatusAndMoney(DeviceList deviceList, int money) {
        Integer state = 0;
        double money1 = money * 100;//单位：元转成分
        String time  = FunUtil.fromDateH(new Date());
        state = deviceListDaoMapper.updateSubsidyStatus(deviceList.getId(),deviceList.getSubsidyStatus(),time);
        if(state == 1){
            InComeRecord inComeRecord = new InComeRecord();
            inComeRecord.setType("3");
            inComeRecord.setTime(new Date());
            inComeRecord.setAgentUnique(deviceList.getAgentUnique());
            inComeRecord.setMoney(money * 100);//元转成分
            inComeRecord.setCheckDay(FunUtil.fromDateY(new Date()));
            //插入收益记录
            agencyPersonDaoMapper.insertAgentIncomeRecord(inComeRecord);
            //修改代理人金额
            agencyPersonDaoMapper.updateAgencyPersonMoney(FunUtil.doubleToInt(money1),deviceList.getAgentUnique());
        }else{
            //回滚
            throw  new RuntimeException();
        }
        return state;
    }

    @Override
    public List<DeviceList> searchDeviceList(DeviceList deviceList) {
        return deviceListDaoMapper.searchDeviceList(deviceList);
    }

    @Override
    public synchronized int updateTyjStatusAndMoney(DeviceList deviceList,int money) {
        int state = 0;
        try {
            java.util.HashMap map = new java.util.HashMap();
            map.put("amount",money*100);
            MapGetterTool mapGetterTool = new MapGetterTool(map);
            deviceList.setTyjStatus("2");
            int result = deviceListDaoMapper.updateTyjStatus1(deviceList);
            if(1 == result){
                CommAppVo commAppVo = sendReq(mapGetterTool,deviceList);
                if(1 == commAppVo.getStatusCode()){
                    state = 1;
                }
            }
        }catch (Exception e){
            throw new RuntimeException();
        }
        return state;
    }

    //往云支付那边发送指令
    private CommAppVo sendReq(MapGetterTool mapGetterTool, DeviceList deviceList){
        CommAppVo commAppVo = new CommAppVo();
        try{
            java.util.HashMap map = new java.util.HashMap();
            map.put("loginName",deviceList.getMerchantYunPayAccount());
            map.put("amount",mapGetterTool.getInteger("amount"));
            map.put("orderId",deviceList.getTyjNumber());
            String sign = FunUtil.signBase64Md5(CommConstant.YZ_SINGDES,deviceList.getMerchantYunPayAccount(),
                    map.get("amount")+"",deviceList.getTyjNumber());
            CommRedisFun.setKeyExpire(sign,"2",60);
            map.put("sign",sign);

            String body = JSONObject.toJSONString(map);
            body = DesUtil.encrypt(body, CommConstant.YZ_DATADES);
            String retBody = HttpPostClient.getPostData(CommConstant.yunReturnPledgeUrl,body,"UTF-8");
            MapGetterTool retMap = new MapGetterTool(AppDesUtil.decryptDataToMap(retBody,CommConstant.YZ_DATADES));
            commAppVo.setMessage(retMap.getString("message"));
            commAppVo.setStatusCode(retMap.getInteger("statusCode"));
        }catch (Exception e ){
            e.printStackTrace();
        }
        return commAppVo;
    }

    @Override
    public int updateTyjStatus(DeviceList deviceList,String status) {
        int state = 0;
        if(!StringUtils.isEmpty(deviceList)){
            deviceList.setTyjStatus(status);
            deviceList.setTyjTime(FunUtil.fromDateH(new Date()));
            deviceList.setTyjNumber(FunUtil.randNumID());
            deviceListDaoMapper.updateTyjStatus(deviceList);
            state = 1;
        }
        return state;
    }
}
