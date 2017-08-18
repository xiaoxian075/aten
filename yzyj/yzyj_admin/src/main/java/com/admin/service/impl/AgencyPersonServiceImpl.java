package com.admin.service.impl;

import com.admin.dao.AgencyPersonDaoMapper;
import com.admin.dao.DeviceListDaoMapper;
import com.admin.model.AgencyPerson;
import com.admin.model.BaseExample;
import com.admin.model.DeviceList;
import com.admin.model.User;
import com.admin.service.AgencyPersonService;
import com.admin.service.UserService;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import com.core.util.CommDictList;
import com.core.util.EncryptUtil;
import com.core.util.FunUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
@Service
public class AgencyPersonServiceImpl extends GenericServiceImpl<AgencyPerson, String> implements AgencyPersonService {
    @Resource
    private AgencyPersonDaoMapper agencyPersonDaoMapper;
    @Resource
    private DeviceListDaoMapper deviceListDaoMapper;
    @Resource
    private UserService userService;
    @Override
    public GenericDao<AgencyPerson, String> getDao() {
        return agencyPersonDaoMapper;
    }


    public List<AgencyPerson> selectByExampleAndPage(DataTablesPage<AgencyPerson> page, BaseExample baseExample){
        return agencyPersonDaoMapper.selectByExampleAndPage(page,baseExample);
    }
    public List<AgencyPerson> selectByExample(BaseExample baseExample){
        return agencyPersonDaoMapper.selectByExample(baseExample);
    }

    public Integer insertAgentInfo(AgencyPerson agencyPerson){
        Integer state = 0;
        try{
            //新增 代理人 , 同时为代理人添加帐号
            String pwd = CommDictList.getDictVal ("init_user_pwd","pwd");
            String rid = CommDictList.getDictVal ("init_user_agent_rid","rid");
            String pass = EncryptUtil.generatePassword(pwd);
            String agentUnique = FunUtil.randNumID();
            agencyPerson.setAgentUnique(agentUnique);
            agencyPerson.setJoinTime(new Date());
            agencyPerson.setTotalFee(0);
            agencyPerson.setRemainingSum(0);
            agencyPersonDaoMapper.insertSelective(agencyPerson);

            User user= new User();
            user.setAgentUnique(agentUnique);
            user.setUsername(agencyPerson.getYunPayLoginName());
            user.setPassword(pass);
            user.setRid(rid);
            user.setCreatetime(new Date());
            user.setUpdateaccount(agencyPerson.getRealName());
            if(!StringUtils.isEmpty(agentUnique)){
                user.setNickname(agencyPerson.getRealName());
            }
            user.setStatus("1");
            user.setHeadUrl("head.jpg");
            userService.insertSelective(user);
            state = 1;
        }catch (Exception e ){
            e.printStackTrace();
        }
        return state;
    }

    @Override
    public AgencyPerson selectByAgencyUnique(String agencyUnique) {
       return agencyPersonDaoMapper.selectByPrimaryKey(agencyUnique);
    }

    @Override
    public void updateAgencyPerson(AgencyPerson agencyPerson) {
        agencyPersonDaoMapper.updateAgencyPerson(agencyPerson);
    }

    @Override
    public List<DeviceList> getDeviceListByAgentUnique(String agentUnique) {
        return deviceListDaoMapper.getDeviceListByAgentUnique(agentUnique);
    }

    @Override
    public List<AgencyPerson> getAgencyList() {
        return agencyPersonDaoMapper.getAgencyList();
    }

    @Override
    public List<AgencyPerson> getMoneyByAgentUnique(String agentUnique) {
        return agencyPersonDaoMapper.getMoneyByAgentUnique(agentUnique);
    }

    @Override
    public Integer getAllRemainingSum() {
        return agencyPersonDaoMapper.getAllRemainingSum();
    }
}
