package com.admin.service.impl;

import com.admin.dao.AgencyPersonDaoMapper;
import com.admin.dao.SmsLogDaoMapper;
import com.admin.model.*;
import com.admin.service.AgencyPersonService;
import com.admin.service.AgentTotalChangeService;
import com.admin.vo.CommAppVo;
import com.alibaba.fastjson.JSONObject;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import com.core.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
@Service
public class AgencyPersonServiceImpl extends GenericServiceImpl<AgencyPerson, String> implements AgencyPersonService {
    @Resource
    private AgencyPersonDaoMapper agencyPersonDaoMapper;
    @Resource
    private AgentTotalChangeService agentTotalChangeService;
    @Resource
    private SmsLogDaoMapper smsLogDaoMapper;

    @Override
    public GenericDao<AgencyPerson, String> getDao() {
        return agencyPersonDaoMapper;
    }


    public List<AgencyPerson> selectByExampleAndPage(DataTablesPage<AgencyPerson> page, BaseExample baseExample){
        return agencyPersonDaoMapper.selectByExampleAndPage(page,baseExample);
    }

    public List<AgencyPerson> selectByExample(BaseExample baseExample){
        return agencyPersonDaoMapper.selectByExample(baseExample);
    }

    @Transactional
    public synchronized CommAppVo updateAgentTotalFee(MapGetterTool mapGetterTool, User user){
        AgencyPerson agencyPerson = new AgencyPerson();
        agencyPerson.setRemainingSum((mapGetterTool.getInteger("totalFee")*100));
        agencyPerson.setAgentUnique(user.getAgentUnique());
        mapGetterTool.put("orderId",FunUtil.randNumID());
        //先去查看检查云支付的转账状态
        CommAppVo commAppVo = sendReq(mapGetterTool,user);
        //成功
        if(1 == (commAppVo.getStatusCode())){
            Integer num = retSynAgentTotalFee(agencyPerson);
            if(num > 0){
                //扣除余额成功 , 新增记录
                AgentTotalChange agentTotalChange = new AgentTotalChange();
                agentTotalChange.setCreateTime(new Date());
                agentTotalChange.setAgentUnique(user.getAgentUnique());
                agentTotalChange.setOrderNumber(mapGetterTool.getString("orderId")+"");
                agentTotalChange.setTotalFee(agencyPerson.getRemainingSum());
                agentTotalChange.setType(11); // 10 结算 , 11 提现
                agentTotalChange.setState(1);
                agentTotalChange.setNote(mapGetterTool.getString("note"));
                agentTotalChangeService.insertSelective(agentTotalChange);
            }
        }
        return commAppVo;
    }

    @Override
    public List<InComeRecord> getInComeRecord(HashMap<Object,Object> mapPara) {
        return agencyPersonDaoMapper.getInComeRecord(mapPara);
    }

    @Override
    public String  forgetPassSms(HttpServletRequest request,String mobile) {
        String state = "";
        AgencyPerson agencyPerson = agencyPersonDaoMapper.getAgencyPersonByInfo(mobile);
        if(agencyPerson == null){
            state = "0,请填写你代理的手机号码";
        }else{
            String code = AppFunUtil.getRandomCode();
            String msg = "修改登录密码的验证码是: "+code+" 打死也不要告诉别人哦！";
            try{
                String result = AppFunUtil.sendSms(smsLogDaoMapper,mobile,msg,1,code);
                if(!result.equals("200")){
                    request.getSession().setAttribute("mobile", mobile);
                    request.getSession().setAttribute("forgetSmsCode", code);
                    state = "1,发送成功！";
                }else{
                    state = "0,操作太频繁啦。。请稍后再来！";
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return state;
    }

    @Override
    public AgencyPerson getAgencyPersonByInfo(String mobile) {
        return agencyPersonDaoMapper.getAgencyPersonByInfo(mobile);
    }

    @Override
    public Integer updatePass(String agentUnique, String pass) {
        return agencyPersonDaoMapper.updatePass(agentUnique,pass);
    }

    @Override
    public AgencyPerson getAgencyInfo(String agentUnique) {
        return agencyPersonDaoMapper.getAgencyInfo(agentUnique);
    }

    private synchronized  Integer retSynAgentTotalFee(AgencyPerson agencyPerson){
        //加锁处理
        return agencyPersonDaoMapper.updateAgentTotalFee(agencyPerson);
    }

    private CommAppVo sendReq(MapGetterTool mapGetterTool, User user){
        CommAppVo commAppVo = new CommAppVo();
        try{
            java.util.HashMap map = new java.util.HashMap();
            map.put("loginName",user.getMerchantYunId());
            map.put("amount",mapGetterTool.getInteger("totalFee"));
            map.put("orderId",mapGetterTool.getString("orderId"));
            String sign = FunUtil.signBase64Md5(CommConstant.yunSignDesKey,user.getMerchantYunId(),map.get("amount")+"",mapGetterTool.getString("orderId"));
            CommRedisFun.setKeyExpire(sign,"1",60);
            map.put("sign",sign);

            String body = JSONObject.toJSONString(map);
            body = DesUtil.encrypt(body, CommConstant.yunRecYunDataDesKey);
            String retBody = HttpPostClient.getPostData(CommConstant.yunHanderPay,body,"UTF-8");
            MapGetterTool retMap = new MapGetterTool(AppDesUtil.decryptDataToMap(retBody,CommConstant.yunRecYunDataDesKey));
//            if(1 == retMap.getInteger("statusCode")){
//                AgentTotalChange agentTotalChange = new AgentTotalChange();
//                agentTotalChange.setOrderNumber(mapGetterTool.getString("orderId")+"");
//                agentTotalChange.setState(1);
//                agentTotalChangeService.update(agentTotalChange);
//            }else{
//                Log.out("提现失败",retMap.getString("message"));
//            }
            commAppVo.setMessage(retMap.getString("message"));
            commAppVo.setStatusCode(retMap.getInteger("statusCode"));
        }catch (Exception e ){
            e.printStackTrace();
        }
        return commAppVo;
    }

    @Override
    public String updatePic(HttpServletRequest request,String headPic,String agentUnique,String headPicName) throws IOException {
        Format fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String filename = fmt.format(new Date());
        //String fileURL = "";
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] p = decoder.decodeBuffer(headPic);
        String path = request.getRealPath("/") + "head/";
        String fileName = filename + ".jpg";
        File file = new File(path, fileName);//可以是任何图片格式.jpg,.png等
        FileOutputStream fos = new FileOutputStream(file);
        InputStream in = new ByteArrayInputStream(p);
        byte[] b = new byte[1024];
        int nRead = 0;
        while ((nRead = in.read(b)) != -1) {
            fos.write(b, 0, nRead);
        }
        fos.flush();
        fos.close();
        in.close();
        Integer state = agencyPersonDaoMapper.updateHeadPic(fileName,agentUnique);
       /* if(!headPicName.equals("head.jpg")){
            String fileURL = request.getRealPath("/") + "head/" + headPicName + ".jpg" ;
            StringUtil.delFile(fileURL);
        }*/
        if(state == 1){
            return fileName;
        }else{
            return null;
        }
    }
}
