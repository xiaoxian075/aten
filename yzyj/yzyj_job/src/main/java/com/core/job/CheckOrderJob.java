package com.core.job;

import com.admin.model.CheckRecord;
import com.admin.service.OrderCheckService;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/4.
 */
public class CheckOrderJob {

    @Resource
    private OrderCheckService orderCheckService;

    public void work() {
        try {
            System.out.print("开始对账。。。。。。。");
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -1); //得到前一天
            Date date = calendar.getTime();
            CheckRecord checkRecord  =  orderCheckService.getorderCheckByDay(new SimpleDateFormat("yyyy-MM-dd").format(date));
            if(StringUtils.isEmpty(checkRecord)){
                CheckRecord checkRecord1 = new CheckRecord();
                checkRecord1.setType(1);//自动对账
                checkRecord1.setCheckDay(new SimpleDateFormat("yyyy-MM-dd").format(date));
                checkRecord1.setCheckDayStr(new SimpleDateFormat("yyyyMMdd").format(date));
                orderCheckService.restartCheck(checkRecord1);
            }else{
                System.out.print("已对过账。。。。。。");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
