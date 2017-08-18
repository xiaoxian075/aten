package com.core.job;

import com.admin.service.StatisticsService;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Administrator on 2017/2/15.
 */
public class StatisticsJob {
    @Resource
    private StatisticsService statisticsService;

    public void work(){
        try {
            System.out.print("开始统计昨天设备的交易信息。。。。");
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -1); //得到前一天
            String date = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
            Integer state = statisticsService.getIsStatistics(date);
            if(state == 0){
                statisticsService.getYesterdayStatistics(date);
            }else{
                System.out.println("已经统计过。。。");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
