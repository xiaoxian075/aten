package com.admin.dao;

import com.admin.model.Statistics;
import com.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2016/11/24.
 */
public interface StatisticsDaoMapper extends GenericDao<Statistics,String> {

    Statistics getYesterdayStatistics(@Param("day") String day);

    List<Statistics> getYesterdayCount(@Param("day") String day);

    Integer getIsStatistics(@Param("day") String day);

}
