package com.admin.dao;

import com.admin.model.SmsModel;

import java.util.List;

/**
 *  短信定时任务
 *  @author chenjx
 *  @date 2017-08-16
 */
public interface SmsModelMapper {
    List<SmsModel> selectListForSyn();

    int updateOne(SmsModel smsModel);
}
