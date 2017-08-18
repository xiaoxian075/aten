package com.admin.service.impl;

import com.admin.dao.StatisticsDaoMapper;
import com.admin.model.Statistics;
import com.admin.service.StatisticsService;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/2/14.
 */
@Service
public class StatisticsServiceImpl extends GenericServiceImpl<Statistics, String> implements StatisticsService {

    @Resource
    private StatisticsDaoMapper statisticsDaoMapper;

    @Override
    public GenericDao<Statistics, String> getDao() {
        return null;
    }

    @Override
    public Statistics getCount() {
        return statisticsDaoMapper.getCount();
    }

    @Override
    public Statistics getCountByType(int type,String day) {
        Statistics statistics = null;
        if(type == 1){
            statistics = statisticsDaoMapper.getCountByTypeAndDay(day);
        }else{
            statistics = statisticsDaoMapper.getCountByTypeAndMonth(day);
        }
        return statistics;
    }

    @Override
    public List<Statistics> getAgentDeviceAllCountMoney(HashMap hashMap) {
        return statisticsDaoMapper.getAgentDeviceAllCountMoney(hashMap);
    }

    @Override
    public List<Statistics> getAgentDeviceMonthCountMoney(HashMap hashMap) {
        return statisticsDaoMapper.getAgentDeviceMonthCountMoney(hashMap);
    }

    @Override
    public List<Statistics> getAgentDeviceCount(HashMap hashMap) {
        return statisticsDaoMapper.getAgentDeviceCount(hashMap);
    }

    @Override
    public List<Statistics> getAgentDeviceMonthCount(HashMap hashMap) {
        return statisticsDaoMapper.getAgentDeviceMonthCount(hashMap);
    }
}
