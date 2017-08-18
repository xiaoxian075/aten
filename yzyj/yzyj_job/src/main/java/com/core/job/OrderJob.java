package com.core.job;

import com.admin.service.PayInfoService;
import com.core.util.CommRedisFun;
import com.core.util.FunUtil;
import com.core.util.Log;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by wjf on 2016/10/8.
 */
public class OrderJob {
    @Resource
    private PayInfoService payInfoService;
    /**
     * 处理用户（往云支付那边推送订单）
     */
    public void work() {
        try {
            //redis blnOrderJob值为1的时候，开始执行推送订单
            if(CommRedisFun.getHKey("staticData","blnOrderJob").equals("1")){
                Log.out("job","开始处理订单推送云支付................"+ FunUtil.fromDateH(new Date()));
                //处理完成 , 无错误返回 , 关闭读表操作
                if(payInfoService.updateOrderInfo() == 0){
                }
                //关闭推送
                CommRedisFun.setHKey("staticData","blnOrderJob","0");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
