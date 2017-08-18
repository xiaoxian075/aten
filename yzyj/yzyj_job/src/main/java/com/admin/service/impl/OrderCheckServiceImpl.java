package com.admin.service.impl;

import com.admin.dao.AgencyPersonDaoMapper;
import com.admin.dao.OrderCheckDaoMapper;
import com.admin.dao.StatisticsDaoMapper;
import com.admin.model.*;
import com.admin.service.OrderCheckService;
import com.admin.vo.CommAppVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.util.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */

@Service
public class OrderCheckServiceImpl extends GenericServiceImpl<Order, String> implements OrderCheckService {

    @Resource
    private OrderCheckDaoMapper orderCheckDaoMapper;
    @Resource
    private AgencyPersonDaoMapper agencyPersonDaoMapper;
    @Resource
    private StatisticsDaoMapper statisticsDaoMapper;

    @Override
    public GenericDao<Order, String> getDao() {
        return orderCheckDaoMapper;
    }

    /**
     * 云智对账刷卡
     *
     * @param data
     * @param
     * @return
     */
    public CommAppVo checkPosOrder(Integer toalFee, Integer count, String data) {
        CommAppVo retVo = new CommAppVo();
        MapGetterTool mapGetterTool = new MapGetterTool(AppDesUtil.toLinkedHashMap(data));

        if (!Integer.valueOf(toalFee).equals(mapGetterTool.getInteger("amountCount"))) {
            retVo.setMessage("对帐云智服务器金额不相等");
            retVo.setStatusCode(10012);
            return retVo;
        }
        if (!Integer.valueOf(count).equals(mapGetterTool.getInteger("orderNum"))) {
            retVo.setMessage("对帐云智服务器笔数不相等");
            retVo.setStatusCode(10013);
            return retVo;
        }
        if (Integer.valueOf(toalFee).equals(mapGetterTool.getInteger("amountCount")) && Integer.valueOf(count).equals(mapGetterTool.getInteger("orderNum"))) {
            retVo.setMessage("跟云智服务器对账成功");
            retVo.setStatusCode(10000);
            return retVo;
        }
        return retVo;
    }

    /**
     * 云支付对账
     *
     * @param data
     * @return
     */
    public CommAppVo checkYunPayData(String data) {
        CommAppVo retVo = new CommAppVo();
        try {
            HashMap map = new HashMap();
            JSONObject jsonObject = JSON.parseObject(data);
            //对帐传参
            map.put("orderNum", jsonObject.get("orderNum")); // 订单数
            map.put("amountCount", jsonObject.get("amountCount")); // 订单总额
            map.put("day", jsonObject.get("day")); //对帐日期
            String body = JSONObject.toJSONString(map);
            body = DesUtil.encrypt(body, "hcr2016%$1008");
            String retBody = HttpPostClient.getPostData(CommConstant.yunCheckUrl, body, "UTF-8");
            MapGetterTool retMap = new MapGetterTool(AppDesUtil.decryptDataToMap(retBody, CommConstant.yunRecYunDataDesKey));
            retVo.setStatusCode(retMap.getInteger("statusCode"));
            retVo.setMessage(retMap.getString("message"));
            return retVo;
        } catch (Exception e) {
            e.printStackTrace();
            retVo.setMessage("程序运行异常");
            retVo.setStatusCode(10016);
            return retVo;
        }
    }

    /**
     * 人工对账
     * @param checkRecord
     * @return
     */
    @Override
    public Integer restartCheck(CheckRecord checkRecord) {
        Integer state = 0;
        CommAppVo retVo = new CommAppVo();
        FtpUtil ftp = new FtpUtil();

        try{
            String check_unique = "";
            String fileName = CommConstant.file_h + checkRecord.getCheckDayStr() + ".txt";
            String str = ftp.readFile(fileName);
            if(checkRecord.getType() == 1){
                /**
                 * 系统自动
                 */
                check_unique = FunUtil.randNumID();
            }else{
                /**
                 * 重新对账
                 */
                check_unique = checkRecord.getCheckUnique();
            }
            String checkDay = checkRecord.getCheckDay();
            JSONObject object = JSONObject.parseObject(str);
            Integer orderMoneyCount = Integer.parseInt(object.get("orderMoneyCount").toString());
            Integer orderNumber = Integer.parseInt(object.get("orderNumber").toString());

            if (FunUtil.isNull(str)) {
                retVo.setMessage("读取FTP拉卡拉对账单失败!");
                retVo.setStatusCode(10014);
                if(checkRecord.getType() == 1){
                    orderCheckDaoMapper.insertCheckOrderRecord(0, 0, retVo.getStatusCode(), retVo.getMessage(), check_unique, checkDay);
                }else {
                    orderCheckDaoMapper.updateYZRestartCheck(orderMoneyCount, orderNumber,retVo.getStatusCode(), retVo.getMessage(), check_unique, checkDay);
                }
                return state;
            }

            CommAppVo retVo1 = getYzDataByCheckDay(1, checkDay);//获取订单类型为刷卡，而且是指定日期的数据
            if (retVo1.getStatusCode() == 1) {
                CommAppVo commAppVo = checkPosOrder(orderMoneyCount, orderNumber, retVo1.getTotalData().toJSONString());
                if (commAppVo.getStatusCode() == 10000) {
                    if(checkRecord.getType() == 1){
                        orderCheckDaoMapper.insertCheckOrderRecord(orderMoneyCount, orderNumber, commAppVo.getStatusCode(), commAppVo.getMessage(), check_unique, checkDay);
                    }else{
                        orderCheckDaoMapper.updateYZRestartCheck(orderMoneyCount,orderNumber,commAppVo.getStatusCode(), commAppVo.getMessage(), check_unique, checkDay);
                    }
                    CommAppVo retVo2 = getYzDataByCheckDay(2, checkDay);//获取订单类型为扫码，而且是指定日期的数据
                    if(retVo2.getStatusCode() ==1) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("orderNum",(Integer.parseInt(retVo1.getTotalData().get("orderNum").toString()) +  Integer.parseInt(retVo2.getTotalData().get("orderNum").toString())));
                        jsonObject.put("amountCount", (Integer.parseInt(retVo1.getTotalData().get("amountCount").toString()) +  Integer.parseInt(retVo2.getTotalData().get("amountCount").toString())));
                        jsonObject.put("day",new SimpleDateFormat("yyyy-MM-dd").parse(checkDay).getTime());
                        CommAppVo retVo3 = checkYunPayData(jsonObject.toJSONString());//验证云智所有的订单
                        if(retVo3.getStatusCode() ==10010){
                            orderCheckDaoMapper.updateCheckOrderRecord(retVo2.getTotalData().getInteger("amountCount"),retVo2.getTotalData().getInteger("orderNum") ,retVo3.getStatusCode(),retVo3.getMessage(),check_unique);
                            /**
                             * 代理人收益
                             */
                            state = 1;
                            HashMap<Object, Object> map = new HashMap<Object, Object>();
                            map.put("checkDay", checkDay);
                            List<HashMap<Object,Object>> listMap = orderCheckDaoMapper.getTotalCountByDay(map);
                            Integer counterAgentFee = 0;//统计所有代理人收益
                            if(listMap.size() != 0){
                                for (HashMap map1 : listMap) {
                                    int totalfee = Integer.parseInt(map1.get("TOTALFEE").toString());
                                    double incomeMoney = ((totalfee) * CommConstant.auIncomeScale);
                                    Integer counterFee = FunUtil.doubleToInt(incomeMoney);
                                    counterAgentFee += counterFee;
                                    String agent_unique = map1.get("AGENT_UNIQUE") + "";
                                    orderCheckDaoMapper.insertAgentIncomeRecord((counterFee), agent_unique,checkDay,"1");//1 代理人收益下发
                                    agencyPersonDaoMapper.updateAgencyPersonMoney((counterFee), agent_unique);
                                }
                            }
                            /**
                             * 对账成功之后统计
                             */
                            Integer result = getYesterdayStatistics(checkDay, counterAgentFee);
                            if (result == 0) {
                                throw new RuntimeException();
                            }
                            return state;
                        }else{
                            orderCheckDaoMapper.updateCheckOrderRecord(retVo2.getTotalData().getInteger("amountCount"), retVo2.getTotalData().getInteger("orderNum"), retVo3.getStatusCode(),retVo3.getMessage(),check_unique);
                            return state;
                        }
                    }else{
                        orderCheckDaoMapper.updateYZRestartCheck(orderMoneyCount,orderNumber,retVo2.getStatusCode(), retVo2.getMessage(), check_unique, checkDay);
                        return state;
                    }
                } else {
                    if(checkRecord.getType() == 1){
                        orderCheckDaoMapper.insertCheckOrderRecord(orderMoneyCount, orderNumber, commAppVo.getStatusCode(), commAppVo.getMessage(), check_unique, checkDay);
                    }else{
                        orderCheckDaoMapper.updateYZRestartCheck(orderMoneyCount,orderNumber,commAppVo.getStatusCode(), commAppVo.getMessage(), check_unique, checkDay);
                    }
                    return state;
                }
            }else{
                return state;
            }
        }catch (Exception e){
            e.printStackTrace();
            retVo.setMessage("程序运行异常");
            retVo.setStatusCode(10016);
            return state;
        }
    }

    @Override
    public CheckRecord getorderCheckByDay(String day) {
        return orderCheckDaoMapper.getorderCheckByDay(day);
    }


    /**
     * 根据根据具体的日期查询
     * @param orderType  订单类型
     * @param checkDay   要查询的时间
     * @return
     */
    public CommAppVo getYzDataByCheckDay(int orderType, String checkDay){
        CommAppVo retVo = new CommAppVo();
        JSONObject obj = new JSONObject();
        int toalFee = 0;
        int count = 0;
        HashMap<Object, Object> map = new HashMap<Object, Object>();
        map.put("orderType", orderType);
        map.put("checkDay", checkDay);

        List<HashMap<Object,Object>> listMap = orderCheckDaoMapper.checkPosByDay(map);
        if(listMap.size()!= 0){
            for (HashMap map1 : listMap) {
                toalFee += Integer.parseInt(map1.get("TOTALFEE") + "");
                count += Integer.parseInt(map1.get("SYSID") + "");
            }
        }else{

        }
        retVo.setStatusCode(1);
        retVo.setTotalData(obj);
        obj.put("amountCount", toalFee);
        obj.put("orderNum", count);
        return retVo;
    }

    /**
     * 统计昨天的交易
     * @param day
     * @param counterAgentFee
     * @return
     */
    public Integer getYesterdayStatistics(String day, Integer counterAgentFee) {
        Integer state = 0;
        try {
            Statistics statistics = statisticsDaoMapper.getYesterdayStatistics(day);
            double double_yzInComeMoney = statistics.getYzCount() * 0.001;//云智账号收益
            Integer yzInComeMoney = FunUtil.doubleToInt(double_yzInComeMoney);
            HashMap<Object, Object> map = new HashMap<Object, Object>();
            map.put("checkDay", day);
            statistics.setYzCount(yzInComeMoney);
            statistics.setYzCountQFZY(yzInComeMoney - counterAgentFee);
            statistics.setAgentCount(counterAgentFee);
            statistics.setAddTime(new Date());
            //获取刷卡,扫码信息
            List<Statistics> statistics1 = statisticsDaoMapper.getYesterdayCount(day);
            for (int i = 0; i < statistics1.size(); i++) {
                if (statistics1.get(i).getTypeCount() == 1) {
                    statistics.setSkCount(statistics1.get(i).getMoneyCount());
                    statistics.setSkCountBS(statistics1.get(i).getBsCount());
                }
                if (statistics1.get(i).getTypeCount() == 2) {
                    statistics.setSmCount(statistics1.get(i).getMoneyCount());
                    statistics.setSmCountBS(statistics1.get(i).getBsCount());
                }
            }
            statistics.setStatisticsTime(day);
            //插入统计信息
            statisticsDaoMapper.insertSelective(statistics);
            state = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return state;
    }

    @Override
    public Integer insertYzIssuedAgentInCome(String day){
        Integer state = 0;
        List<InComeRecord> inComeRecord = new ArrayList<InComeRecord>();
        try{
            //统计符合条件的代理人下发的金额
            List<AgencyPerson> list1 = agencyPersonDaoMapper.getCountAgent();
            if(list1.size() > 0){
                for(int i = 0;i<list1.size();i++){
                    //修改代理人的金额
                    agencyPersonDaoMapper.updateYzIsSendAgentInCome(list1.get(i).getAgentUnique(),list1.get(i).getCountAgent());
                    InComeRecord inComeRecord1 = new InComeRecord();
                    inComeRecord1.setMoney(list1.get(i).getCountAgent());
                    inComeRecord1.setAgentUnique(list1.get(i).getAgentUnique());
                    inComeRecord1.setCheckDay(day);
                    inComeRecord1.setType("2");
                    inComeRecord1.setTime(new Date());
                    inComeRecord.add(inComeRecord1);
                }
                //插入收益记录
                agencyPersonDaoMapper.inserBatchInCome(inComeRecord);
                state = 1;
            }else{
                state = 0;
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
        return state;
    }

    @Override
    public Integer getSendByDay(String day) {
        return agencyPersonDaoMapper.getSendByDay(day);
    }
}

