package com.admin.dao;

import com.admin.model.Amount;
import com.core.generic.GenericDao;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/11/24.
 */
public interface HistoryAmountDaoMapper  extends GenericDao<Amount,String> {

    List<Amount> getAmountByDay(HashMap<Object,Object> map);

    void inserBatchAmount(List<Amount> list);

}
