package com.admin.controller;

import com.admin.model.*;
import com.admin.service.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.core.entity.JSONResult;
import com.core.mybatis.DataTablesPage;
import com.core.util.FunUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/2/14.
 * 统计表报
 */
@Controller
@RequestMapping(value = "/admin/statistics")
public class StatisticsController {

    @Resource
    private StatisticsService statisticsService;
    @Resource
    private AgencyPersonService agencyPersonService;
    @Resource
    private CheckRecordService checkRecordService;
    @Resource
    private DeviceListService deviceListService;
    @Resource
    private DictTableService dictTableService;

    /**
     * 统计所有的
     * @param model
     * @return
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model) {
        Statistics statistics = statisticsService.getCount();
        model.addAttribute("yzCount", FunUtil.fenToYuanL(statistics.getYzCount()));
        model.addAttribute("yzCountQFZY", FunUtil.fenToYuanL(statistics.getYzCountQFZY()));
        model.addAttribute("agentCount", FunUtil.fenToYuanL(statistics.getAgentCount()));
        model.addAttribute("skCount", FunUtil.fenToYuanL(statistics.getSkCount()));
        model.addAttribute("skCountBS", statistics.getSkCountBS());
        model.addAttribute("smCount", FunUtil.fenToYuanL(statistics.getSmCount()));
        model.addAttribute("smCountBS", statistics.getSmCountBS());
        return "admin/statistics/index";
    }

    /**
     * 按月 或者 按日
     * @param statistics
     * @return
     */
    @RequestMapping(value="/getCountByType",method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<Statistics> getCountByType(Statistics statistics) {
        Statistics statistics1 = null;
        if(statistics.getType() == 1){//日期查询
            if(!StringUtils.isEmpty(statistics.getDayTime())){
                try{
                    statistics1 = statisticsService.getCountByType(statistics.getType(),statistics.getDayTime());
                    return new JSONResult<Statistics>(statistics1, "操作成功", true);
                }catch (Exception e){
                    e.printStackTrace();
                    return new JSONResult<Statistics>(null, "操作失败", false);
                }
            }else{
                return new JSONResult<Statistics>(null, "请选择要查询的日期", false);
            }
        }else if(statistics.getType() == 2){//月份查询
            if(!StringUtils.isEmpty(statistics.getMonthTime())){
                try{
                    statistics1 = statisticsService.getCountByType(statistics.getType(),statistics.getMonthTime());
                    return new JSONResult<Statistics>(statistics1, "操作成功", true);
                }catch (Exception e){
                    e.printStackTrace();
                    return new JSONResult<Statistics>(null, "操作失败", false);
                }
            }else{
                return new JSONResult<Statistics>(null, "请选择要查询的月份", false);
            }
        }else{
            return new JSONResult<Statistics>(null, "查询条件不符合", false);
        }
    }

    /**
     * 跳转代理人收益统计页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/agentStatisticsIndex",method = RequestMethod.GET)
    public String agentStatisticsIndex(Model model) {
        Integer remainingSum = agencyPersonService.getAllRemainingSum();
        model.addAttribute("remainingSum",FunUtil.fenToYuan(remainingSum));
        return "admin/statistics/agentStatisticsxIndex";
    }

    /**
     * 代理人收益统计
     * @param page
     * @param model
     * @param agencyPerson
     * @return
     */
    @RequestMapping(value = "/getAgentStatisticsList",method = RequestMethod.GET)
    @ResponseBody
    public String agentStatisticsx(DataTablesPage<AgencyPerson> page, Model model,AgencyPerson agencyPerson) {
        String day = "";
        String month = "";
        HashMap hashMap = new HashMap();
        try{
            BaseExample baseExample = new BaseExample();
            if(!StringUtils.isEmpty(agencyPerson.getYunPayLoginName())){
                baseExample.createCriteria().andEqualTo("YUNPAYLOGINNAME",agencyPerson.getYunPayLoginName());
            }
            if(!StringUtils.isEmpty(agencyPerson.getRealName())){
                baseExample.createCriteria().andEqualTo("REALNAME",agencyPerson.getRealName());
            }
            //如果搜索条件不为空，则搜索用户输入的
            if(!StringUtils.isEmpty(agencyPerson.getStrDay())){
                day = agencyPerson.getStrDay();
            }else{
                //为空则默认搜索日期为昨天的
                day = FunUtil.getYesterdayH();
            }
            List<AgencyPerson> list  = agencyPersonService.selectByExampleAndPage(page,baseExample);
            if(list.size() == 0){
                return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
            }
            if(!StringUtils.isEmpty(agencyPerson.getStrMonth())){
                month = agencyPerson.getStrMonth();
            }else{
                month = FunUtil.fromDateH(new Date()).substring(0,7);
            }
            hashMap.put("day",day);
            hashMap.put("month",month);
            if(list.size()>1){
            }else{
                hashMap.put("agentUnique",list.get(0).getAgentUnique());
            }
            //天
            List<InComeRecord> listInComeDay = checkRecordService.getInComeRecordDay(hashMap);
            List<AgencyPerson> listPage = page.getResult();
            for(int i=0;i<listPage.size();i++){
                for(int j=0;j<listInComeDay.size();j++){
                    if(listInComeDay.get(j).getAgentUnique().equals(listPage.get(i).getAgentUnique())){
                        listPage.get(i).setDayCount(listInComeDay.get(j).getMoney());
                    }
                }
            }
            //月
            List<InComeRecord> listInComeMonth = checkRecordService.getInComeRecordMonth(hashMap);
            for(int i=0;i<listPage.size();i++){
                for(int j=0;j<listInComeMonth.size();j++){
                    if(listInComeMonth.get(j).getAgentUnique().equals(listPage.get(i).getAgentUnique())){
                        listPage.get(i).setMonthCount(listInComeMonth.get(j).getAgentMonthCount());
                    }
                }
            }
            page.setResult(listPage);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 跳转代理人每月补贴统计页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/agentBtIndex",method = RequestMethod.GET)
    public String agentBtIndex(Model model) {
        Integer remainingSum = agencyPersonService.getAllRemainingSum();
        model.addAttribute("remainingSum",FunUtil.fenToYuan(remainingSum));
        return "admin/statistics/agentBtIndex";
    }

    /**
     * 代理人每个月补贴统计
     * @param page
     * @param model
     * @param agencyPerson
     * @return
     */
    @RequestMapping(value = "/getAgentStatisticsBtList",method = RequestMethod.GET)
    @ResponseBody
    public String agentStatisticsxBt(DataTablesPage<AgencyPerson> page, Model model,AgencyPerson agencyPerson) {
        String month = "";
        HashMap hashMap = new HashMap();
        try{
            BaseExample baseExample = new BaseExample();
            if(!StringUtils.isEmpty(agencyPerson.getYunPayLoginName())){
                baseExample.createCriteria().andEqualTo("YUNPAYLOGINNAME",agencyPerson.getYunPayLoginName());
            }
            if(!StringUtils.isEmpty(agencyPerson.getRealName())){
                baseExample.createCriteria().andEqualTo("REALNAME",agencyPerson.getRealName());
            }
            List<AgencyPerson> list  = agencyPersonService.selectByExampleAndPage(page,baseExample);
            if(list.size() == 0){
                return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
            }
            hashMap.put("type","2");
            if(list.size()>1){
            }else{
                //条件搜索的时候
                hashMap.put("agentUnique",list.get(0).getAgentUnique());
            }
            List<AgencyPerson> listPage = page.getResult();
            //月
            List<InComeRecord> listInComeMonth = checkRecordService.getInComeRecordMonth(hashMap);
            for(int i=0;i<listPage.size();i++){
                for(int j=0;j<listInComeMonth.size();j++){
                    if(listInComeMonth.get(j).getAgentUnique().equals(listPage.get(i).getAgentUnique())){
                        listPage.get(i).setMonthCount(listInComeMonth.get(j).getAgentMonthCount());
                    }
                }
            }
            page.setResult(listPage);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 跳转代理人代理设备总收益统计页面
     * @param model
     * @param deviceList
     * @return
     */
    @RequestMapping(value = "/tradeCountIndex",method = RequestMethod.GET)
    public String tradeCountIndex(Model model,DeviceList deviceList) {
        return "admin/statistics/tradeCountIndex";
    }

    /**
     * 获取代理人代理设备总收益列表
     * @param page
     * @param model
     * @param agencyPerson
     * @return
     */
    @RequestMapping(value = "/getTradeCountList",method = RequestMethod.GET)
    @ResponseBody
    public String getTradeCountList(DataTablesPage<AgencyPerson> page, Model model,AgencyPerson agencyPerson) {
        String month = "";
        HashMap hashMap = new HashMap();
        try{
            BaseExample baseExample = new BaseExample();
            if(!StringUtils.isEmpty(agencyPerson.getYunPayLoginName())){
                baseExample.createCriteria().andEqualTo("YUNPAYLOGINNAME",agencyPerson.getYunPayLoginName());
            }
            if(!StringUtils.isEmpty(agencyPerson.getRealName())){
                baseExample.createCriteria().andEqualTo("REALNAME",agencyPerson.getRealName());
            }
            List<AgencyPerson> list  = agencyPersonService.selectByExampleAndPage(page,baseExample);
            if(list.size() == 0){
                return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
            }
            if(list.size()>1){
            }else{
                //代理人搜索
                hashMap.put("agentUnique",list.get(0).getAgentUnique());
            }
            if(!StringUtils.isEmpty(agencyPerson.getStrMonth())){
                month = agencyPerson.getStrMonth();
            }else{
                month = FunUtil.fromDateH(new Date()).substring(0,7);
            }
            hashMap.put("month",month);
            List<AgencyPerson> listPage = page.getResult();
            //当月
            List<Statistics> list1 = statisticsService.getAgentDeviceMonthCountMoney(hashMap);
            for(int i=0;i<listPage.size();i++){
                for(int j=0;j<list1.size();j++){
                    if(list1.get(j).getAgentUnique().equals(listPage.get(i).getAgentUnique())){
                        listPage.get(i).setAgentDeviceMonthCountMoney(list1.get(j).getAgentDeviceMonthCountMoney());
                    }
                }
            }
            //累计
            List<Statistics> list2 = statisticsService.getAgentDeviceAllCountMoney(hashMap);
            for(int i=0;i<listPage.size();i++){
                for(int j=0;j<list2.size();j++){
                    if(list2.get(j).getAgentUnique().equals(listPage.get(i).getAgentUnique())){
                        listPage.get(i).setAgentDeviceAllCountMoney(list2.get(j).getAgentDeviceAllCountMoney());
                    }
                }
            }
            //代理设备数
            List<Statistics> list3 = statisticsService.getAgentDeviceCount(hashMap);
            for(int i=0;i<listPage.size();i++){
                for(int j=0;j<list3.size();j++){
                    if(list3.get(j).getAgentUnique().equals(listPage.get(i).getAgentUnique())){
                        listPage.get(i).setAgentDeviceCount(list3.get(j).getAgentDeviceCount());
                    }
                }
            }
            //代理每个月新增设备数
            List<Statistics> list4 = statisticsService.getAgentDeviceMonthCount(hashMap);
            for(int i=0;i<listPage.size();i++){
                for(int j=0;j<list4.size();j++){
                    if(list4.get(j).getAgentUnique().equals(listPage.get(i).getAgentUnique())){
                        listPage.get(i).setAgentDeviceMonthCount(list4.get(j).getAgentDeviceMonthCount());
                    }
                }
            }
            page.setResult(listPage);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 交易详情页
     * @param model
     * @param statistics
     * @return
     */
    @RequestMapping(value = "tradeDetail", method = RequestMethod.GET)
    public String tradeDetail(Model model, Statistics statistics) {
        model.addAttribute("agentUnique",statistics.getAgentUnique());
        Integer result = Integer.parseInt(dictTableService.selectList("tyj_money").get(0).getDictBh())*100;
        model.addAttribute("countCondition",result);
        return "admin/statistics/tradeDetail";
    }

    /**
     * 获取交易列表
     * @param model
     * @param deviceList
     * @param statistics
     * @return
     */
    @RequestMapping(value = "getTradeDetailList",method = RequestMethod.GET)
    @ResponseBody
    public String getTradeDetailList(HttpServletRequest request, DataTablesPage<DeviceList> page, Model model, DeviceList deviceList,Statistics statistics) {
        try{
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
            if(!StringUtils.isEmpty(deviceList.getMerchantYunPayAccount())){
                criteria.andEqualTo("device.MERCHANT_YUN_PAY_ACCOUNT",deviceList.getMerchantYunPayAccount());
            }
            if(!StringUtils.isEmpty(deviceList.getLklMerchantCode())){
                criteria.andEqualTo("device.LKL_MERCHANT_CODE",deviceList.getLklMerchantCode());
            }
            if(!StringUtils.isEmpty(deviceList.getMerchantName())){
                criteria.andLike("device.MERCHANT_NAME",deviceList.getMerchantName());
            }
            if(!StringUtils.isEmpty(deviceList.getLklMachineCode())){
                criteria.andLike("device.LKL_MACHINE_CODE",deviceList.getLklMachineCode());
            }
            if(!StringUtils.isEmpty(deviceList.getLklTerminalCode())){
                criteria.andLike("device.LKL_TERMINAL_CODE",deviceList.getLklTerminalCode());
            }
            if(!StringUtils.isEmpty(deviceList.getState())){
                criteria.andEqualTo("device.STATE",deviceList.getState());
            }
            criteria.andEqualTo("device.agent_unique",statistics.getAgentUnique());
            criteria.andEqualTo("device.tyj_status","0");

            baseExample.setOrderByClause("device.CREATE_TIME desc ");
            deviceListService.selectByExampleAndPage(page,baseExample);
            List<DeviceList> list = page.getResult();

            //统计每台设备累计总和
            //2 表示只统计（刷卡）
            List<DeviceList> list1 = deviceListService.getMoneyByAgentUnique(statistics.getAgentUnique(),"2");
            for(int i=0;i<list.size();i++){
                for(int j=0;j<list1.size();j++){
                    if(list1.get(j).getMachineCode().equals(list.get(i).getDeviceCode())){
                        list.get(i).setMoneyCount(list1.get(j).getMoneyCount());
                    }
                }
            }
            //统计每台设备当天总和
            //2 表示只统计刷卡的
            List<DeviceList> list2 = deviceListService.getMoneyByAgentUniqueAndDay(statistics.getAgentUnique(),"2");
            for(int i=0;i<list.size();i++){
                for(int j=0;j<list2.size();j++){
                    if(list2.get(j).getMachineCode().equals(list.get(i).getDeviceCode())){
                        list.get(i).setDayCount(list2.get(j).getMoneyCount());
                    }
                }
            }
            page.setResult(list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

}
