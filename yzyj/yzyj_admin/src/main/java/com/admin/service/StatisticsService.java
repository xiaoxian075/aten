package com.admin.service;

import com.admin.model.Statistics;
import com.core.generic.GenericService;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/2/14.
 */

public interface StatisticsService extends GenericService<Statistics,String> {

    Statistics getCount();

    Statistics getCountByType(int type,String day);

    List<Statistics> getAgentDeviceAllCountMoney(HashMap hashMap);

    List<Statistics> getAgentDeviceMonthCountMoney(HashMap hashMap);

    List<Statistics> getAgentDeviceCount(HashMap hashMap);

    List<Statistics> getAgentDeviceMonthCount(HashMap hashMap);
}
