package com.core.job;

import com.admin.model.DictTable;
import com.admin.service.PayInfoService;
import com.admin.service.SmsService;
import com.core.util.CommConstant;

import javax.annotation.Resource;
import java.util.List;

/**
 *  短信定时任务
 *  @author chenjx
 *  @date 2017-08-16
 */
public class SmsTimerJobService {
    @Resource
    private SmsService smsService;



    public void work(){
        System.out.println("开始任务");
        smsService.synModel();
        smsService.batchSendTimer();
    }
}
