package com.admin.service.impl;

import com.admin.dao.UserHandleLogDaoMapper;
import com.admin.model.BaseExample;
import com.admin.model.User;
import com.admin.model.UserHandleLog;
import com.admin.service.UserHandleLogService;
import com.admin.vo.UserHandleLogVo;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import com.core.util.AppFunUtil;
import com.core.util.CommUserHandle;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
@Service
public class UserHandleLogServiceImpl extends GenericServiceImpl<UserHandleLog, String> implements UserHandleLogService {
    @Resource
    private UserHandleLogDaoMapper userHandleLogDaoMapper;
    @Override
    public GenericDao<UserHandleLog, String> getDao() {
        return userHandleLogDaoMapper;
    }


    public List<UserHandleLog> selectByExampleAndPage(DataTablesPage<UserHandleLog> page, BaseExample baseExample){
        return userHandleLogDaoMapper.selectByExampleAndPage(page,baseExample);
    }
    public List<UserHandleLog> selectByExample(BaseExample baseExample){
        return userHandleLogDaoMapper.selectByExample(baseExample);
    }

    /**
     * 用户操作比较
     */
    public Integer insertUserHandleCompare(UserHandleLogVo userHandleLogVo){
        String body = CommUserHandle.UserHandleCompare(userHandleLogVo.getOldVo(),userHandleLogVo.getNewVo());
        User user = AppFunUtil.getUser(userHandleLogVo.getRequest());
        UserHandleLog userHandleLog = new UserHandleLog();
        userHandleLog.setUserId(user.getId());
        userHandleLog.setHandleIndex(userHandleLogVo.getIndex());
        userHandleLog.setHandleBody(body);
        userHandleLog.setHandleModel(userHandleLogVo.getThisObj().getClass().getName());
        userHandleLog.setHandleType(userHandleLogVo.getHandType());
        userHandleLog.setIp(AppFunUtil.getIpAddr(userHandleLogVo.getRequest()));
        return userHandleLogDaoMapper.insertSelective(userHandleLog);
    }


    /**
     * 用户操作记录
     * @param thisObj 操作类this
     * @param handType 操作类型 1 新增 ,2 修改 3 删除
     * @param body
     * @return
     */
    public Integer insertUserHandleLog(Object thisObj , Integer handType, String body){
        UserHandleLog userHandleLog = new UserHandleLog();
        userHandleLog.setHandleBody(body);
        userHandleLog.setHandleModel(thisObj.getClass().getName());
        userHandleLog.setHandleType(handType);
        return userHandleLogDaoMapper.insertSelective(userHandleLog);
    }
}
