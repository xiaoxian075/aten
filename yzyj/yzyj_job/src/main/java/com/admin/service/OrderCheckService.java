package com.admin.service;

import com.admin.model.CheckRecord;
import com.admin.model.Order;
import com.core.generic.GenericService;

/**
 * Created by Administrator on 2016/11/4.
 */
public interface OrderCheckService extends GenericService<Order,String> {

    Integer restartCheck(CheckRecord checkRecord);

    CheckRecord getorderCheckByDay(String day);

    Integer insertYzIssuedAgentInCome(String day);

    Integer getSendByDay(String day);

}
