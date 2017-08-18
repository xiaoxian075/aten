package com.admin.service;

import com.admin.model.*;
import com.admin.vo.CommAppVo;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;
import com.core.util.MapGetterTool;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wjf on 2016/4/15.
 */
public interface AgencyPersonService extends GenericService<AgencyPerson,String> {

    List<AgencyPerson> selectByExampleAndPage(DataTablesPage<AgencyPerson> page, BaseExample baseExample);

    List<AgencyPerson> selectByExample(BaseExample baseExample);

    CommAppVo updateAgentTotalFee(MapGetterTool mapGetterTool, User user);

    List<InComeRecord> getInComeRecord(HashMap<Object,Object> mapPara);

    String forgetPassSms(HttpServletRequest request,String mobile);

    AgencyPerson getAgencyPersonByInfo(String mobile);

    Integer updatePass(String agentUnique,String pass);

    AgencyPerson getAgencyInfo(String agentUnique);

    String updatePic(HttpServletRequest request,String headPic,String agentUnique,String headPicName) throws IOException;

}
