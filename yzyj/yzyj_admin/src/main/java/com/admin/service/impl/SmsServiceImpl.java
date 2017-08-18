package com.admin.service.impl;

import com.admin.dao.*;
import com.admin.model.*;
import com.admin.service.SmsService;
import com.core.mybatis.DataTablesPage;
import com.core.util.CommConstant;
import com.inter.*;
import com.util.JsonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RunnableFuture;

/**
 * @author chenjx
 * @date 2017/08/11
 */
@Service public class SmsServiceImpl implements SmsService {

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
    private SmsDaoMapper smsDaoMapper;

    @Resource
    private SmsGroupDaoMapper smsGroupDaoMapper;

    @Resource
    private SmsAccountDaoMapper smsAccountDaoMapper;

    @Resource
    private SmsInfoMapper smsInfoMapper;

    @Resource
    private SmsRecordMapper smsRecordMapper;

    @Resource
    private SmsBatchMsgMapper smsBatchMsgMapper;


    @Override
    public List<SmsModel> selectList(DataTablesPage<SmsModel> page, BaseExample baseExample) {
        return smsDaoMapper.selectList(page,baseExample);
    }

    @Override
    public boolean insertModel(SmsModel smsModel) {
        if (smsModel==null)
            return false;

        ReqMsg<ModelNode> reqmsg = AtyNet.addModel(APPKEY, smsModel.getSign()+smsModel.getContent());
        if (reqmsg==null || reqmsg.getCode()!=0 || reqmsg.getT()==null)
            return false;
        ModelNode modelNode = reqmsg.getT();

        int state = AtyNet.getState(modelNode.getCheck_status());
        if (state==0)
            return false;
        smsModel.setId(modelNode.getTpl_id());
        smsModel.setState(state);
        String reason = modelNode.getReason();
        if (reason==null)
            reason = " ";
        smsModel.setReason(reason);
        Timestamp timestamp = new Timestamp(new Date().getTime());
        smsModel.setCreateTime(timestamp);
        smsModel.setUpdateTime(timestamp);

        if (1!=smsDaoMapper.insertOne(smsModel))
            return false;
        return true;
    }

    @Override
    public ReqMsg<Object> editModel(SmsModel smsModel) {
        if (smsModel==null)
            return new ReqMsg<Object>(1001,"参数错误",null);

        ReqMsg<ModelNode> reqmsg = AtyNet.updModel(APPKEY, smsModel.getId(), smsModel.getSign()+smsModel.getContent());
        if (reqmsg==null) {
            return new ReqMsg<Object>(1003,"接口出错",null);
        }
        if ( reqmsg.getCode()!=0 || reqmsg.getT()==null) {
            return new ReqMsg<Object>(reqmsg.getCode(), reqmsg.getDesc(), null);
        }
        ModelNode modelNode = reqmsg.getT();

        int state = AtyNet.getState(modelNode.getCheck_status());
        if (state==0)
            return new ReqMsg<Object>(1004,"状态不对",null);
        smsModel.setId(modelNode.getTpl_id());
        smsModel.setState(state);
        String reason = modelNode.getReason();
        if (reason==null)
            reason = " ";
        smsModel.setReason(reason);
        smsModel.setUpdateTime(new Timestamp(new Date().getTime()));

        if (1!=smsDaoMapper.updateOne(smsModel))
            return new ReqMsg<Object>(1005,"执行出错",null);
        return new ReqMsg<Object>(0,"succ",null);
    }

    @Override
    public boolean synModel(Long id) {
        if (id==null || id<=0)
            return false;

        SmsModel smsModel = smsDaoMapper.selectOne(id);
        if (smsModel==null)
            return false;

        if (smsModel.getState()!=2)    //不是审核状态中的，就不用同步
            return true;


        //同步
        ReqMsg<ModelNode> reqmsg = AtyNet.getModel(APPKEY, id);
        if (reqmsg==null || reqmsg.getCode()!=0 || reqmsg.getT()==null)
            return false;
        ModelNode modelNode = reqmsg.getT();

        int status = AtyNet.getState(modelNode.getCheck_status());
        if (status==0)
            return false;


        smsModel.setId(modelNode.getTpl_id());
        smsModel.setState(status);
        String reason = modelNode.getReason();
        if (reason==null)
            reason = " ";
        smsModel.setReason(reason);
        smsModel.setUpdateTime(new Timestamp(new Date().getTime()));

        if (1!=smsDaoMapper.updateOne(smsModel))
            return false;

        return true;
    }

    @Override
    public List<SmsModel> signList() {
        return smsDaoMapper.signList();
    }

    /**
     * 短信群发
     * @param arrMobile
     * @param content
     * @return
     */
    @Override
    public boolean batchSendSms(List<String> arrMobile, String content) {
        return batchSms(arrMobile,content);
    }

    private boolean batchSms(List<String> arrMobile, String content) {
        ReqMsg<SmsInfoList> reqmsg = AtyNet.batchSendSms(APPKEY, arrMobile, content);
        if (reqmsg==null) {
            return false;
        }

        //储存发送记录
        if (reqmsg.getCode()==0 && reqmsg.getT()!=null) {
            if (!saveSmsList(reqmsg.getT(),content))
                return false;
        }

        return true;
    }

    @Override
    public boolean saveBatchSendSms(List<String> arrMobile, String content, Timestamp sendTime, Timestamp curTime) {
        boolean result = false;
        String jsonMobile = JsonUtil.toString(arrMobile);
        SmsBatchMsg smsBatchMsg = new SmsBatchMsg(0L,content,jsonMobile,1,sendTime,curTime,curTime);
        if (1 == smsBatchMsgMapper.insertOne(smsBatchMsg))
            result = true;
        return result;
    }

    @Override
    public SmsGroup getGroup(Long groupId) {
        return smsGroupDaoMapper.selectOne(groupId);
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
                    result = false;
            }
        }
        return result;
    }

    @Override
    public boolean delModel(Long id) {
        SmsModel smsModel = smsDaoMapper.selectOne(id);
        if (smsModel==null)
            return false;

        ReqMsg<ModelNode> reqmsg = AtyNet.delModel(APPKEY, id);
        if (reqmsg==null || reqmsg.getCode()!=0 || reqmsg.getT()==null)
            return false;

        if (1!=smsDaoMapper.deleteOne(id))
            return false;

        return true;
    }

    @Override
    public SmsModel selectOne(Long id) {
        return smsDaoMapper.selectOne(id);
    }









    @Override
    public List<SmsGroup> selectGroupList(DataTablesPage<SmsGroup> page, BaseExample baseExample) {
        return smsGroupDaoMapper.selectList(page,baseExample);
    }

    @Override
    public List<SmsGroup> selectAllGroup(Integer state) {
        Map<String,Object> params = new HashMap<String,Object>();
        if (state!=null && state==1)
            params.put("state",state);
        return smsGroupDaoMapper.selectAll(params);
    }

    @Override
    public boolean insertGroup(String accName) {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        return smsGroupDaoMapper.insertOne(new SmsGroup(0,accName,0,1,timestamp,timestamp));
    }

    @Override
    @Transactional
    public boolean delGroup(Long id) {
        SmsGroup smsGroup = smsGroupDaoMapper.selectOne(id);
        if (smsGroup==null)
            return false;
        if (0 > smsAccountDaoMapper.deleteByGroupid(id))
            return false;
        if (1 != smsGroupDaoMapper.deleteOne(id)) {
            throw new RuntimeException();
        }
        return true;
    }

    @Override
    public boolean updateStateGroup(Long id, int state) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id",id);
        params.put("state",state);
        params.put("updateTime",new Timestamp(new Date().getTime()));
        if (1!=smsGroupDaoMapper.updateOne(params))
            return false;
        return true;
    }

    @Override
    public List<SmsAccount> selectAccountList(DataTablesPage<SmsAccount> page, BaseExample baseExample) {
        return smsAccountDaoMapper.selectList(page,baseExample);
    }

    @Override
    @Transactional
    public boolean insertAccount(String name, int sex, String phone, long groupId) {
        SmsGroup smsGroup = smsGroupDaoMapper.selectOne(groupId);
        if (smsGroup==null)
            return false;
        Timestamp timestamp = new Timestamp(new Date().getTime());
        if (1 != smsAccountDaoMapper.insertOne(new SmsAccount(0,name,sex,phone,groupId,timestamp,timestamp))) {
            return false;
        }

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id",groupId);
        params.put("updateTime",timestamp);
        if (1 != smsGroupDaoMapper.updateOneCountMore(params)) {
            throw new RuntimeException();
        }

        return true;
    }

    @Override
    public boolean editAccount(Long id, String name, Integer sex, String phone, Long groupId) {
        SmsGroup smsGroup = smsGroupDaoMapper.selectOne(groupId);
        if (smsGroup==null)
            return false;
        Timestamp timestamp = new Timestamp(new Date().getTime());
        if (1 != smsAccountDaoMapper.updateOne(new SmsAccount(id,name,sex,phone,groupId,timestamp,timestamp))) {
            return false;
        }

        return true;
    }

    @Override
    public SmsAccount getAccount(Long id) {
        return smsAccountDaoMapper.selectOne(id);
    }

    @Override
    public boolean checkGroupName(String groupName) {
        Long count = smsGroupDaoMapper.selectCountByName(groupName);
        if (count==0){
            return true;
        }
        return false;
    }

    @Override
    public boolean checkModelName(String title) {
        Long count = smsDaoMapper.selectCountByName(title);
        if (count==0){
            return true;
        }
        return false;
    }

    @Override
    public List<SmsAccount> selectAccountByGroupId(Long id) {
        return smsAccountDaoMapper.selectAccountByGroupId(id);
    }


    @Override
    public boolean delAccount(Long id) {
        SmsAccount smsAccount = smsAccountDaoMapper.selectOne(id);
        if (smsAccount==null)
            return false;

        if (1 != smsAccountDaoMapper.deleteOne(id))
            return false;

        Timestamp timestamp = new Timestamp(new Date().getTime());
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id",smsAccount.getGroupId());
        params.put("updateTime",timestamp);
        if (1 != smsGroupDaoMapper.updateOneCountLess(params)) {
            throw new RuntimeException();
        }

        return true;
    }

    @Override
    public List<SmsAccount> selectAllAccount(Long id) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id",id);
        return smsAccountDaoMapper.selectAllAccount(params);
    }

    @Override
    public List<SmsRecord> selectSmsRecordList(DataTablesPage<SmsRecord> page, BaseExample baseExample) {
        List<SmsRecord> listRecord = smsRecordMapper.selectList(page,baseExample);
        if (listRecord==null)
            return null;

        Map<Long,SmsInfo> mapInfo = new HashMap<Long,SmsInfo>();
        for (SmsRecord record : listRecord) {
            if (record.getState()==0) {
                Long parentId = record.getParentId();
                if (parentId != null && parentId > 0) {
                    SmsInfo smsInfo = mapInfo.get(parentId);
                    if (smsInfo == null) {
                        smsInfo = smsInfoMapper.selectOne(parentId);
                        mapInfo.put(parentId, smsInfo);
                    }
                    record.setMsg(smsInfo.getText());
                }
            }
        }

        return listRecord;
    }




}
