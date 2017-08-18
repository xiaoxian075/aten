package com.admin.service;

import com.admin.model.Statistics;
import com.core.generic.GenericService;

/**
 * Created by Administrator on 2017/2/15.
 */
public interface StatisticsService  extends GenericService<Statistics,String> {

    void getYesterdayStatistics(String day);

    Integer getIsStatistics(String day);
}
