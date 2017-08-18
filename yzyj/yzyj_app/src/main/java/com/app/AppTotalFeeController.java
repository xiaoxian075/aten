package com.app;

import com.admin.model.*;
import com.admin.service.AgencyPersonService;
import com.admin.service.UserService;
import com.core.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/8.
 */
@Controller
@RequestMapping(value = "/app/totalFee")
public class AppTotalFeeController {
    @Resource
    private UserService userService;
    @Resource
    private AgencyPersonService agencyPersonService;

    /**
     * 获取 代理人余额, 总额
     */
    @RequestMapping(value = "getAgentTotalFee", method = RequestMethod.POST)
    @ResponseBody
    public  String getAgentTotalFee(HttpServletRequest request, String data) {
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

            BaseExample baseExample = new BaseExample();
            baseExample.createCriteria().andEqualTo("agent_unique",user.getAgentUnique());
            List<AgencyPerson> list = agencyPersonService.selectByExample(baseExample);

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
            if(list.size() > 0){
                java.util.HashMap map = new java.util.HashMap();
                AgencyPerson agencyPerson = list.get(0);
                if(agencyPerson.getTotalFee() == null){
                    map.put("totalFee", "0.00");
                }else{
                    map.put("totalFee", FunUtil.fenToYuan(agencyPerson.getTotalFee()));
                }
                if(agencyPerson.getRemainingSum() == null){
                    map.put("remainingSum","0.00");
                }else{
                    map.put("remainingSum",FunUtil.fenToYuan(agencyPerson.getRemainingSum()));
                }
                //收益还是
                String type = hashMap.getString("type");
                if(!StringUtils.isEmpty(type)){
                	mapPara.put("type",type);
                }
                List<InComeRecord> list1 = agencyPersonService.getInComeRecord(mapPara);
                List<InComeRecordModel> iList = new ArrayList<InComeRecordModel>();
                //定义总金额
                BigDecimal totalMoney = new BigDecimal("0.00");
                if (!StringUtils.isEmpty(list1)) {
                    for (Iterator<InComeRecord> iterator = list1.iterator(); iterator.hasNext();) {
                        InComeRecord inComeRecord = (InComeRecord) iterator.next();
                        //计算总金额
                        totalMoney = totalMoney.add(new BigDecimal(inComeRecord.getMoney()));
                        InComeRecordModel inComeRecordModel = ClassInstanceUtil.createInComeRecordModel(inComeRecord);
                        iList.add(inComeRecordModel);
                    }
                }
                HashMap<String, Object> ObjMap = new HashMap<String, Object>();
                map.put("inComeList",iList);
                ObjMap.put("inList", map);
                //除以100，分转成元
                totalMoney = totalMoney.divide(new BigDecimal("100"));
                ObjMap.put("totalMoney", totalMoney);
                return AppDesUtil.posAppDesEncrypt(ObjMap,"成功",1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AppDesUtil.posAppDesEncrypt(null,"失败",0);
    }
}
