package com.app;

import com.admin.model.*;
import com.admin.service.AgencyPersonService;
import com.admin.service.AgentTotalChangeService;
import com.admin.service.UserService;
import com.admin.vo.CommAppVo;
import com.core.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/12/8.
 */
@Controller
@RequestMapping(value = "/app/withdraw")
public class AppWithdrawController {

    @Resource
    private UserService userService;
    @Resource
    private AgencyPersonService agencyPersonService;
    @Resource
    private AgentTotalChangeService agentTotalChangeService;
    /**
     * 接收 APP硬件 代理请求提现
     */
    @RequestMapping(value = "recAgentExtract", method = RequestMethod.POST)
    @ResponseBody
    public String recAgentExtract(HttpServletRequest request, String data) {
        try{
            /**
             * 获取 Map 对象
             */
            MapGetterTool hashMap = new MapGetterTool(AppFunUtil.getDataMap(data));
            if(hashMap == null){
                return AppDesUtil.posAppDesEncrypt(null,"失败",0);
            }
            /**
             * 验证是否登入 , 并返回对象
             */
            User user = AppFunUtil.checkToken(request,userService,hashMap.getString("token"));
            if(user.getState() != 1 ){
                return AppDesUtil.posAppDesEncrypt(null,user.getNote(),user.getState());
            }
            if(hashMap.getInteger("totalFee") < 100){
                return AppDesUtil.posAppDesEncrypt(null,"每笔提现最少100元",3);
            }
            if((hashMap.getInteger("totalFee") %  100) != 0){
                return AppDesUtil.posAppDesEncrypt(null,"提现金额必须是100倍数",4);
            }
            /**
             * 查询 库中代理 是否存在
             * 检查 该代理余额
             */
            BaseExample baseExample = new BaseExample();
            baseExample.createCriteria().andEqualTo("agent_unique",user.getAgentUnique());
            List<AgencyPerson> list = agencyPersonService.selectByExample(baseExample);
            //判断代理人提现金额是否足够
            if(list.get(0).getRemainingSum() < (hashMap.getInteger("totalFee")*100)){
                return AppDesUtil.posAppDesEncrypt(null,"你当前账号余额不足",0);
            }
            //云支付返回提现27账号的结果
            CommAppVo commAppVo = agencyPersonService.updateAgentTotalFee(hashMap,user);
            if(1 == commAppVo.getStatusCode()){
                return AppDesUtil.posAppDesEncrypt(null,commAppVo.getMessage(),1);
            }else{
                return AppDesUtil.posAppDesEncrypt(null,"提现失败,请联系云智客服",0);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return AppDesUtil.posAppDesEncrypt(null,"失败",0);
    }

    /**
     * 提现记录
     * @param request
     * @param data
     * @return
     */
    @RequestMapping(value = "getWithdrawRecord", method = RequestMethod.POST)
    @ResponseBody
    public String getWithdrawRecord(HttpServletRequest request, String data){
        try {
            /**
             * 获取 Map 对象
             */
            MapGetterTool hashMap = new MapGetterTool(AppFunUtil.getDataMap(data));
            if(hashMap == null){
                return AppDesUtil.posAppDesEncrypt(null,"失败",0);
            }
            /**
             * 验证是否登入 , 并返回对象
             */
            User user = AppFunUtil.checkToken(request,userService,hashMap.getString("token"));
            if(user.getState() != 1 ){
                return AppDesUtil.posAppDesEncrypt(null,user.getNote(),user.getState());
            }

            java.util.HashMap hashMap1 = agentTotalChangeService.selectAgentGrandTotal(user.getAgentUnique());
            if(StringUtils.isEmpty(hashMap1)){
                return AppDesUtil.posAppDesEncrypt(null,"暂无提现记录",1);
            }
            java.util.HashMap map = new java.util.HashMap();
            map.put("total", FunUtil.fenToYuan(Integer.parseInt(hashMap1.get("TOTAL_FEE").toString())));

            java.util.HashMap mapPara = new java.util.HashMap();
            mapPara.put("agentUnique",user.getAgentUnique());
            mapPara.put("pageStart",hashMap.getInteger("pageStart"));
            mapPara.put("pageEnd",hashMap.getInteger("pageEnd"));
            /**
             * 按时间来搜索
             */
            String startDay = hashMap.getString("startDay");
            String endDay = hashMap.getString("endDay");
            if(!StringUtils.isEmpty(startDay)){
                mapPara.put("startDay",startDay);
            }
            if(!StringUtils.isEmpty(endDay)){
                mapPara.put("endDay",endDay);
            }

            List<AgentTotalChange> list = agentTotalChangeService.getTotalChangList(mapPara);
            List<WithdrawModel> wList = new ArrayList<WithdrawModel>();
            if (!StringUtils.isEmpty(list)) {
                for (Iterator<AgentTotalChange> iterator = list.iterator(); iterator.hasNext();) {
                    AgentTotalChange agentTotalChange = (AgentTotalChange) iterator.next();
                    WithdrawModel withdrawModel = ClassInstanceUtil.createWithdrawModel(agentTotalChange);
                    wList.add(withdrawModel);
                }
            }
            map.put("RecordList",wList);
            return AppDesUtil.posAppDesEncrypt(map,"成功",1);
        }catch (Exception e ){
            e.printStackTrace();
        }
        return AppDesUtil.posAppDesEncrypt(null,"失败",0);
    }
}
