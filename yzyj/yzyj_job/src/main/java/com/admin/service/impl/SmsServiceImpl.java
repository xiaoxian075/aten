package com.admin.service.impl;

import com.admin.dao.SmsBatchMsgMapper;
import com.admin.dao.SmsInfoMapper;
import com.admin.dao.SmsModelMapper;
import com.admin.dao.SmsRecordMapper;
import com.admin.model.*;
import com.admin.service.SmsService;
import com.core.util.CommConstant;
import com.core.util.JsonUtil;
import com.google.gson.reflect.TypeToken;
import com.inter.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  短信定时任务
 *  @author chenjx
 *  @date 2017-08-16
 */
@Service
public class SmsServiceImpl implements SmsService {
    private static String APPKEY = "";//"c45db841fcf37e48e379e0123193cbf6";
    static {
        List<DictTable> listData = CommConstant.dictMapList.get("sms_appkey");
        if (listData!=null && listData.size()==1) {
            DictTable dictTable = listData.get(0);
            if (dictTable!=null)
                APPKEY = dictTable.getDictBh();
        }
    }

    @Resource
    private SmsModelMapper smsModelMapper;

    @Resource
    private SmsBatchMsgMapper smsBatchMsgMapper;

    @Resource
    private SmsInfoMapper smsInfoMapper;

    @Resource
    private SmsRecordMapper smsRecordMapper;





    @Override
    public boolean synModel() {
        List<SmsModel> listSmsModel = smsModelMapper.selectListForSyn();    //所有状态为“审核中”的模板

        for (SmsModel smsModel : listSmsModel) {
            //同步
            ReqMsg<ModelNode> reqmsg = AtyNet.getModel(APPKEY, smsModel.getId());
            if (reqmsg == null || reqmsg.getCode() != 0 || reqmsg.getT() == null)
                continue;
            ModelNode modelNode = reqmsg.getT();

            int status = AtyNet.getState(modelNode.getCheck_status());
            if (status == 0)
                continue;


            smsModel.setId(modelNode.getTpl_id());
            smsModel.setState(status);
            String reason = modelNode.getReason();
            if (reason == null)
                reason = " ";
            smsModel.setReason(reason);
            smsModel.setUpdateTime(new Timestamp(new Date().getTime()));

            if (1 != smsModelMapper.updateOne(smsModel))
                continue;
        }

        return true;
    }

    @Override
    public boolean batchSendTimer() {

        List<SmsBatchMsg> listSmsBatchMsg = smsBatchMsgMapper.selectListForActive();

        long curTime = new Date().getTime();
        Map<String,Object> params = new HashMap<String,Object>();
        for (SmsBatchMsg smsBatchMsg : listSmsBatchMsg) {
            long sendTime = smsBatchMsg.getSendTime().getTime();
            if (curTime<sendTime)
                continue;
            params.clear();
            params.put("id",smsBatchMsg.getId());
            params.put("state",0);
            params.put("updateTime", new Timestamp(new Date().getTime()));
            if (1 != smsBatchMsgMapper.updateOneState(params))      //将状态置为0（已更新)
                continue;

            String mobiles = smsBatchMsg.getMobiles();
            List<String> arrMobile = JsonUtil.toJson(mobiles,new TypeToken<List<String>>() {}.getType());
            String content = smsBatchMsg.getText();

            ReqMsg<SmsInfoList> reqmsg = AtyNet.batchSendSms(APPKEY, arrMobile, content);
            if (reqmsg == null)
                continue;

            //储存发送记录
            if (reqmsg.getCode() == 0 && reqmsg.getT() != null) {
                if (!saveSmsList(reqmsg.getT(), content))
                    continue;
            }
        }

        return true;
    }

    private boolean saveSmsList(SmsInfoList smsInfoList,String text) {
        boolean result = true;

        Timestamp curTime = new Timestamp(new Date().getTime());
        SmsInfo smsInfo = new SmsInfo(
                0L,
                smsInfoList.getTotal_count(),
                smsInfoList.getTotal_fee(),
                smsInfoList.getUnit(),
                text,
                curTime);
        if (1 != smsInfoMapper.insertOne(smsInfo))
            result = false;

        List<SmsInfoNode> listInfo = smsInfoList.getData();
        if (listInfo!=null && listInfo.size()>0) {
            for (SmsInfoNode node : listInfo) {
                if (node==null)
                    continue;
                SmsRecord record = new SmsRecord(
                        0L,
                        smsInfo.getId(),
                        node.getSid(),
                        node.getMobile(),
                        node.getCount(),
                        node.getFee(),
                        node.getCode(),
                        node.getMsg(),
                        curTime
                );
                if (1!=smsRecordMapper.insertOne(record))
                    continue;
            }
        }
        return result;
    }
}
