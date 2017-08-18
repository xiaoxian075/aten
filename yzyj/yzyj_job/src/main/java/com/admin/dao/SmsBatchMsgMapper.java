package com.admin.dao;


import com.admin.model.SmsBatchMsg;

import java.util.List;
import java.util.Map;

public interface SmsBatchMsgMapper {
    List<SmsBatchMsg> selectListForActive();

    int updateOneState(Map<String, Object> params);
}
