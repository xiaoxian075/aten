package com.admin.dao;

import com.admin.model.CheckRecord;
import com.admin.model.Order;
import com.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface OrderCheckDaoMapper extends GenericDao<Order,String> {

    List<HashMap<Object,Object>> checkPosByDay(HashMap<Object,Object> map);

    void insertCheckOrderRecord(@Param("toalFee") Integer toalFee,
                                @Param("count") Integer count,
                                @Param("lkl_check_status") Integer lkl_check_status,
                                @Param("check_result") String check_result,
                                @Param("check_unique") String check_unique,
                                @Param("check_day") String check_day);


    void updateCheckOrderRecord(@Param("YPtoalFee") Integer YPtoalFee,
                                @Param("YPcount") Integer YPcount,
                                @Param("yp_check_status") Integer yp_check_status,
                                @Param("check_result") String check_result,
                                @Param("check_unique") String check_unique);


    void insertAgentIncomeRecord(@Param("incomeMoney") double incomeMoney,
                                 @Param("agent_unique") String agent_unique,
                                 @Param("checkDay") String checkDay,
                                 @Param("type") String type);


    void updateYZRestartCheck(@Param("toalFee") Integer toalFee,
                              @Param("count") Integer count,
                              @Param("lkl_check_status") Integer lkl_check_status,
                              @Param("check_result") String check_result,
                              @Param("check_unique") String check_unique,
                              @Param("check_day") String check_day);

    List<HashMap<Object,Object>> getTotalCountByDay(HashMap<Object,Object> map);

    CheckRecord getorderCheckByDay(@Param("day")String day);

}
