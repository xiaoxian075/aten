package com.admin.dao;

import com.admin.model.CommModel;
import com.admin.model.SmsLog;
import com.core.generic.GenericDao;

import java.util.Map;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface SmsLogDaoMapper extends GenericDao<CommModel,String> {

    SmsLog querySmsLogByMap(Map<Object, Object> map);

    void insert(SmsLog smsLog);
}
