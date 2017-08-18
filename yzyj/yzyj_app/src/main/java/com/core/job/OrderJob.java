package com.core.job;

import com.admin.service.PayInfoService;
import com.core.util.CommConstant;
import com.core.util.Log;

import javax.annotation.Resource;

/**
 * Created by wjf on 2016/10/8.
 */
public class OrderJob {
    @Resource
    private PayInfoService payInfoService;
    /**
     * 处理用户
     */
    public void work() {
        try {
            if(CommConstant.blnOrderJob){
                CommConstant.blnOrderJob = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
