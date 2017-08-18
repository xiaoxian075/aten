package com.core.job;

import com.admin.service.PosWithdrawService;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/1/12.
 */
public class PosWithdrawJob {
    @Resource
    private PosWithdrawService posWithdrawService;
    /**
     * 处理用户（暂无用到）
     */
    public void work() {
        try {
            System.out.print("开始获取昨天pos商户提现记录。。。。");
            posWithdrawService.getPosWithdrawListFromYunPay();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
