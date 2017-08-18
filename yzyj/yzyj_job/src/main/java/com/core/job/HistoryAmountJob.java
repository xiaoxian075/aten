package com.core.job;

import com.admin.service.HistoryAmountService;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/11/24.
 */
public class HistoryAmountJob {

    @Resource
    private HistoryAmountService historyAmountService;

    public void work() {
        try {
            System.out.print("刷卡扫码。。。。");
            historyAmountService.statisticsHistoryAmount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
