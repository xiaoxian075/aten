package com.admin.dao;

import com.admin.model.Statistics;
import com.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/11/24.
 */
public interface StatisticsDaoMapper  extends GenericDao<Statistics,String> {

    Statistics getCountByTypeAndDay(@Param("day")String day);

    Statistics getCountByTypeAndMonth(@Param("day")String day);

    Statistics getCount();

    Statistics getYesterdayStatistics(@Param("day") String day);

    List<Statistics> getYesterdayCount(@Param("day") String day);

    List<Statistics> getAgentDeviceAllCountMoney(HashMap hashMap);

    List<Statistics> getAgentDeviceMonthCountMoney(HashMap hashMap);

    List<Statistics> getAgentDeviceCount(HashMap hashMap);

    List<Statistics> getAgentDeviceMonthCount(HashMap hashMap);

}
