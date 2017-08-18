package com.core.job;

import com.admin.service.OrderCheckService;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/2/17.
 */
public class YzIssuedJobService {
    @Resource
    private OrderCheckService orderCheckService;

    public void work(){
        try {
            System.out.println("开始下发。。。。。。。");
            Date date = new Date();
            String day = new SimpleDateFormat("yyyy-MM-dd").format(date);
            //获取当天是否已经下发过  0：没有 1：有
            int state = orderCheckService.getSendByDay(day);
            if(state == 0){
                orderCheckService.insertYzIssuedAgentInCome(day);
            }else{
                System.out.println("已经下发过。。。。。。。");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
