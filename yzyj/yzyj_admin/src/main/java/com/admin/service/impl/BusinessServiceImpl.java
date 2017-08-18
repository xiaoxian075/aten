package com.admin.service.impl;

import com.admin.dao.BusinessDaoMapper;
import com.admin.model.BaseExample;
import com.admin.model.Business;
import com.admin.model.BusinessPicture;
import com.admin.service.BusinessService;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import com.core.util.FunUtil;
import com.core.util.YunPayUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/11/30.
 */
@Service
public class BusinessServiceImpl extends GenericServiceImpl<Business, String> implements BusinessService {
    @Resource
    private BusinessDaoMapper businessDaoMapper;

    @Override
    public List<Business> selectByExampleAndPage(DataTablesPage<Business> page, BaseExample baseExample) {
        return businessDaoMapper.selectByExampleAndPage(page,baseExample);
    }

    @Override
    public List<Business> selectAllByExampleAndPage(DataTablesPage<Business> page, BaseExample baseExample) {
        return businessDaoMapper.selectAllByExampleAndPage(page,baseExample);
    }

    @Override
    public List<Business> selectByExample(BaseExample baseExample) {
        return businessDaoMapper.selectByExample(baseExample);
    }

    @Override
    public List<Business> getBusinessInfo() {
        return businessDaoMapper.getBusinessInfo();
    }

    @Override
    public Integer insertBusiness(Business business) {
        //设置商户标识
        boolean resultBoolean = YunPayUtil.setMerchantIdentify(business.getYunPayAccount(),1);
        if(resultBoolean){
            Integer maxId = businessDaoMapper.selectMaxId();
            if(StringUtils.isEmpty(maxId)){
                maxId = 1;
            }
            maxId = 100000+maxId;
            String dateStr = FunUtil.fromDatStr(new Date());
            String merchantNo = dateStr + maxId.intValue();

            business.setMerchantNo(merchantNo);
            String result = business.getAgentUnique();
            String[] a = result.split(",");
            business.setAgentUnique(a[0]);
            business.setAgencyName(a[1]);
            business.setStatus(1);//待审批
            business.setCreateTime(FunUtil.fromDateH(new Date()));
            businessDaoMapper.insertBusiness(business);
            return 1;
        }
        return 0;
    }

    @Override
    public Integer returnMNo(){
        Integer maxId = businessDaoMapper.selectMaxId();
        if(StringUtils.isEmpty(maxId)){
            maxId = 1;
        }
        maxId = 100000+maxId;
        return  maxId;
    }

    @Override
    public void deletePic(int id) {
        businessDaoMapper.deletePic(id);
    }

    @Override
    public void updateBusiness(Business business) {
        businessDaoMapper.updateBusiness(business);
    }

    @Override
    public List<Business> getBusinessNameByAgentUnique(String agentUnique) {
        return businessDaoMapper.getBusinessNameByAgentUnique(agentUnique);
    }

    @Override
    public Business getBusinessNameById(String id) {
        return businessDaoMapper.getBusinessNameById(id);
    }

    @Override
    public void updateBusAnegcy(String agentUnique) {
        businessDaoMapper.updateBusAnegcy(agentUnique);
    }

    @Override
    public Business getBusinessInfoByYId(String loginName) {
        return businessDaoMapper.getBusinessInfoByYId(loginName);
    }

    @Override
    public void insertBusinessPicture(BusinessPicture businessPicture) {
        businessDaoMapper.insertBusinessPicture(businessPicture);
    }

    @Override
    public void updateApproval(int id,int status) {
        businessDaoMapper.updateApproval(id,status);
    }

    @Override
    public void inserBatchBusiness(List<Business> businesses) {
        businessDaoMapper.inserBatchBusiness(businesses);
    }

    @Override
    public Business selectByBId(int id) {
        return businessDaoMapper.selectByBId(id);
    }

    @Override
    public List<BusinessPicture> selectByPId(int id) {
        return businessDaoMapper.selectByPId(id);
    }

    @Override
    public GenericDao<Business, String> getDao() {
        return null;
    }
}
