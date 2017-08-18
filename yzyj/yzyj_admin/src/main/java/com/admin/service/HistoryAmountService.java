package com.admin.service;

import com.admin.model.Amount;
import com.core.generic.GenericService;

/**
 * Created by Administrator on 2016/11/24.
 */
public interface HistoryAmountService extends GenericService<Amount,String> {

    void statisticsHistoryAmount();
}
