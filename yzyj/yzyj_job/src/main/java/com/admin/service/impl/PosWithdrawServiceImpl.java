package com.admin.service.impl;

import com.admin.dao.PosWithdrawDaoMapper;
import com.admin.model.BaseExample;
import com.admin.model.PosWithdrawRecord;
import com.admin.service.PosWithdrawService;
import com.alibaba.fastjson.JSONObject;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import com.core.util.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/1/12.
 */
@Service
public class PosWithdrawServiceImpl extends GenericServiceImpl<PosWithdrawRecord, String> implements PosWithdrawService {

    @Resource
    private PosWithdrawDaoMapper posWithdrawDaoMapper;
    @Override
    public GenericDao<PosWithdrawRecord, String> getDao() {
        return null;
    }

    @Override
    public Integer getPosWithdrawListFromYunPay() {
        Integer state = 0;
        try{
            String withdrawType = "4";//固定值
            HashMap map = new HashMap();
            Calendar calendar = Calendar.getInstance();
            calendar.add(calendar.DATE,-1);//昨天的数据
            Date date = calendar.getTime();

            map.put("auditTime", new SimpleDateFormat("yyyy-MM-dd").format(date)); // 审批时间
            map.put("type", withdrawType); //提现类型
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(withdrawType).append("&").append(CommConstant.yunRechargeDesKey);
            String verifySign = MD5Util.encodeByMD5Base64(stringBuffer.toString());
            map.put("sign", FunUtil.getString(verifySign)); //验签

            String body = JSONObject.toJSONString(map);
            body = DesUtil.encrypt(body, CommConstant.yunRecYunDataDesKey);
            String retBody = HttpPostClient.getPostData(CommConstant.yunPosWithdrawUrl, body, "UTF-8");
            MapGetterTool retMap = new MapGetterTool(AppDesUtil.decryptDataToMap(retBody, CommConstant.yunRecYunDataDesKey));
            retMap.getString("message");
            if("1".equals(retMap.getString("statusCode"))){
                state = 1;
                List<HashMap<Object,Object>> list = retMap.getList("list");
                List<PosWithdrawRecord> lp = new ArrayList<PosWithdrawRecord>();
                if(list.size() == 0){

                }else{
                    for(int i = 0; i < list.size(); i++){
                        PosWithdrawRecord p = new PosWithdrawRecord();
                        p.setId((int)list.get(i).get("id"));
                        p.setAccountName(list.get(i).get("accountName").toString());
                        p.setIsSend(0);
                        p.setAuditStatus((int)list.get(i).get("auditStatus"));
                        p.setAmount((int)list.get(i).get("amount"));
                        p.setBankAccount(list.get(i).get("bankAccount").toString());
                        p.setBankName(list.get(i).get("bankName").toString());
                        p.setLoginName(list.get(i).get("loginName").toString());
                        p.setWithdrawNote(list.get(i).get("withdrawNote").toString());
                        p.setAuditTime(FunUtil.fromDateL(Long.parseLong(list.get(i).get("auditTime").toString())));
                        p.setWithdrawTime(FunUtil.fromDateL(Long.parseLong(list.get(i).get("withdrawTime").toString())));
                        lp.add(i,p);
                    }
                    posWithdrawDaoMapper.inserBatchPosWithdraw(lp);
                }
                return state;
            }else{
                return state;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return state;
    }

    @Override
    public List<PosWithdrawRecord> selectByExampleAndPage(DataTablesPage<PosWithdrawRecord> page, BaseExample baseExample) {
        return posWithdrawDaoMapper.selectByExampleAndPage(page,baseExample);
    }

    @Override
    public PosWithdrawRecord getPosWithdrawRecordById(int id) {
        return posWithdrawDaoMapper.getPosWithdrawRecordById(id);
    }

    @Override
    public void updateResult(PosWithdrawRecord posWithdrawRecord, String money) {
        posWithdrawRecord.setIsSendMoney(money);
        posWithdrawRecord.setIsSend(1);
        posWithdrawRecord.setIsSendTime(new Date());
        posWithdrawDaoMapper.updateResult(posWithdrawRecord);
    }
}
