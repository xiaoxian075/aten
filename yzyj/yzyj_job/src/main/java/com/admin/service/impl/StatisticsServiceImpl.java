package com.admin.service.impl;

import com.admin.dao.OrderCheckDaoMapper;
import com.admin.dao.StatisticsDaoMapper;
import com.admin.model.Statistics;
import com.admin.service.StatisticsService;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.util.CommConstant;
import com.core.util.FunUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/2/15.
 */
@Service
public class StatisticsServiceImpl  extends GenericServiceImpl<Statistics, String> implements StatisticsService {

    @Resource
    private StatisticsDaoMapper statisticsDaoMapper;
    @Resource
    private OrderCheckDaoMapper orderCheckDaoMapper;
    @Override
    public GenericDao<Statistics, String> getDao() {
        return null;
    }

    @Override
    public void getYesterdayStatistics(String day) {
        try{
            Statistics statistics = statisticsDaoMapper.getYesterdayStatistics(day);
            double double_yzInComeMoney = statistics.getYzCount() * 0.001;//云智账号收益
            Integer yzInComeMoney = FunUtil.doubleToInt(double_yzInComeMoney);
            HashMap<Object, Object> map = new HashMap<Object, Object>();
            map.put("checkDay", day);
            List<HashMap<Object,Object>> listMap = orderCheckDaoMapper.getTotalCountByDay(map);
            Integer counterFee = 0;
            if(listMap.size() != 0){
                for (HashMap map1 : listMap) {
                    int totalfee = Integer.parseInt(map1.get("TOTALFEE").toString());
                    double incomeMoney = ((totalfee) * CommConstant.auIncomeScale);
                    Integer counterFee1 = FunUtil.doubleToInt(incomeMoney);//代理人收益
                    counterFee += counterFee1;
                }
            }
            statistics.setYzCount(yzInComeMoney);
            statistics.setYzCountQFZY(yzInComeMoney-counterFee);
            statistics.setAgentCount(counterFee);
            statistics.setAddTime(new Date());
            //获取刷卡,扫码信息
            List<Statistics> statistics1 = statisticsDaoMapper.getYesterdayCount(day);
            for(int i = 0;i<statistics1.size();i++){
                if(statistics1.get(i).getTypeCount() == 1){
                    statistics.setSkCount(statistics1.get(i).getMoneyCount());
                    statistics.setSkCountBS(statistics1.get(i).getBsCount());
                }
                if(statistics1.get(i).getTypeCount() == 2){
                    statistics.setSmCount(statistics1.get(i).getMoneyCount());
                    statistics.setSmCountBS(statistics1.get(i).getBsCount());
                }
            }
            statistics.setStatisticsTime(day);
            //插入统计信息
            statisticsDaoMapper.insertSelective(statistics);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Integer getIsStatistics(String day) {
        return statisticsDaoMapper.getIsStatistics(day);
    }
}
