package com.admin.service;

import com.admin.model.BaseExample;
import com.admin.model.Business;
import com.admin.model.BusinessPicture;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2016/11/30.
 */
public interface BusinessService extends GenericService<Business,String> {

    List<Business> selectByExampleAndPage(DataTablesPage<Business> page, BaseExample baseExample);

    List<Business> selectAllByExampleAndPage(DataTablesPage<Business> page, BaseExample baseExample);

    List<Business> selectByExample(BaseExample baseExample);

    List<Business> getBusinessInfo();

    Integer insertBusiness(Business business);

    void insertBusinessPicture(BusinessPicture businessPicture);

    void updateApproval(int id,int status);

    void inserBatchBusiness(List<Business> businesses);

    Business selectByBId(int id);

    List<BusinessPicture> selectByPId(int id);

    Integer returnMNo();

    void deletePic(int id);

    void updateBusiness(Business business);

    List<Business> getBusinessNameByAgentUnique(String agentUnique);

    Business getBusinessNameById(String id);

    void updateBusAnegcy(String agentUnique);

    Business getBusinessInfoByYId(String loginName);


}
