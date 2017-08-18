package com.admin.dao;

import com.admin.model.BaseExample;
import com.admin.model.Business;
import com.admin.model.BusinessPicture;
import com.core.mybatis.DataTablesPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2016/11/29.
 */
public interface BusinessDaoMapper {

    List<Business> selectByExampleAndPage(DataTablesPage<Business> page, BaseExample baseExample);

    List<Business> selectAllByExampleAndPage(DataTablesPage<Business> page, BaseExample baseExample);

    void insertBusiness(Business business);

    List<Business> selectByExample(BaseExample baseExample);

    void insertBusinessPicture(BusinessPicture businessPicture);

    void updateApproval(@Param("id")int id,
                        @Param("status")int status);

    List<Business> getBusinessInfo();

    void inserBatchBusiness(List<Business> businesses);

    Business selectByBId(@Param("id")int id);

    List<BusinessPicture> selectByPId(@Param("id")int id);

    Integer selectMaxId();

    void deletePic(@Param("id")int id);

    void updateBusiness(Business business);

    List<Business> getBusinessNameByAgentUnique(@Param("agentUnique") String agentUnique);

    Business getBusinessNameById(@Param("id") String id);

    void updateBusAnegcy(@Param("agentUnique") String agentUnique);

    Business getBusinessInfoByYId(@Param("loginName")String loginName);

}
