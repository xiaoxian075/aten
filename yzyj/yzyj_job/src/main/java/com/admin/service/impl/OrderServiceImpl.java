package com.admin.service.impl;

import com.admin.dao.OrderDaoMapper;
import com.admin.model.*;
import com.admin.service.OrderService;
import com.admin.vo.CommAppVo;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import com.core.umen.CommUmen;
import com.core.util.AppFunUtil;
import com.core.util.CommRedisFun;
import com.core.util.FunUtil;
import com.core.util.MapGetterTool;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
@Service
public class OrderServiceImpl extends GenericServiceImpl<Order, String> implements OrderService {
    @Resource
    private OrderDaoMapper orderDaoMapper;
    @Override
    public GenericDao<Order, String> getDao() {
        return orderDaoMapper;
    }


    public List<Order> selectByExampleAndPage(DataTablesPage<Order> page, BaseExample baseExample){
        return orderDaoMapper.selectByExampleAndPage(page,baseExample);
    }
    public List<Order> selectPosPage(DataTablesPage<Order> page, BaseExample baseExample){
        return orderDaoMapper.selectPosPage(page,baseExample);
    }

    @Override
    public List<RepairOrder> selectRepairOrderPage(DataTablesPage<Order> page, BaseExample baseExample) {
        return orderDaoMapper.selectRepairOrderPage(page,baseExample);
    }

    public List<Order> selectByExample(BaseExample baseExample){
        return orderDaoMapper.selectByExample(baseExample);
    }
    public List<Order> selectScanCodeOrder(BaseExample baseExample){
        return orderDaoMapper.selectScanCodeOrder(baseExample);
    }

    public Integer updateOrderPayState(Order order){
        return orderDaoMapper.updateOrderPayState(order);
    }

    public Integer updateOrderPayStateBySm(Order order){
        return orderDaoMapper.updateOrderPayStateBySm(order);
    }

    public Integer updateOrderPushState(Order order){
        return orderDaoMapper.updateOrderPushState(order);
    }


    /**
     * 更新 二维码 付款单 状态
     */
    public CommAppVo updateScanCodeOrder(MapGetterTool dataMap) {
        CommAppVo retVo = new CommAppVo();
        try {

            BaseExample baseExample = new BaseExample();
            baseExample.createCriteria().andEqualTo("order_number", dataMap.getString("orderNumber")).andEqualTo("order_type", "2").andEqualTo("order_state", "0");
            List<Order> list = selectScanCodeOrder(baseExample);
            if (list.size() == 0) {
                retVo.setStatusCode(0);
                retVo.setMessage("订单未找到");
                return retVo;
            }
            Order order = list.get(0);
            if ("payNotice".equals(dataMap.getString("noticeType"))){
                boolean bln = false;
                Order order1 = new Order();
                if("10020".equals(dataMap.getString("statusCode"))){
                    //扣款成功
                    order1.setPayState(1);
                    order1.setOrderState(1);
                    Date payTime = FunUtil.timeStampToDate(dataMap.getLong("payTime"),"yyyy-MM-dd HH:mm:ss");
                    order1.setPayTime(payTime);
                    bln = true;
                }else{
                    order1.setPayState(dataMap.getInteger("statusCode"));
                }

                order1.setOrderNumber(dataMap.getString("orderNumber"));
                order1.setYunId(dataMap.getString("payYunId"));
                Integer realAmount = FunUtil.bd2Integer(dataMap.getBigDecimal("realAmount"),100);
                order1.setRealAmount(realAmount);

                //付款成功 , 进行推送
                if (bln) {
                    //推送至POS消息
                    //安卓
                    HashMap map = new HashMap();
                    map.put("orderNumber", order.getOrderNumber());
                    //获取 商户信息
                    BaseExample baseExample1 = new BaseExample();
                    baseExample1.createCriteria().andEqualTo("order_number",order.getOrderNumber());
                    List<Order> list1 =  orderDaoMapper.selectMerchantOrder(baseExample1);
                    if(list1.size() == 1){
                        Order order2 = list1.get(0);
                        map.put("merchantName",order2.getMerchantName());//商户名称
                        map.put("lklMerchantCode",order2.getLklMerchantCode());//商户编码
                        map.put("lklTerminalCode",order2.getLklTerminalCode());//设备编码
                        map.put("merchantYunId",order2.getMerchantYunId()); //商户 帐号
                        map.put("clientYunId", FunUtil.dealStrHide(order1.getYunId(),4));//顾客云ID
                        map.put("payTime", FunUtil.getFormatDate(order1.getPayTime(),"yyyy-MM-dd HH:mm:ss"));//付款时间
                        map.put("realAmount", realAmount);//实付金额
                        map.put("orderAmount", order.getTotalFee());//订单金额
                        map.put("preferentialPrice", (order.getTotalFee() > realAmount  ) ? order.getTotalFee()-realAmount:0);//优惠金额
                        map.put("payState", 1);
                        CommUmen.sendAndroidUnicast(order.getUmengToken(), map);
                        order1.setOrderState(2);
                    }

                    Integer state = updateOrderPayStateBySm(order1);
                }
                retVo.setStatusCode(1);
            }else if("cancelPayNotice".equals(dataMap.getString("noticeType"))){
                Order order1 = new Order();
                order1.setOrderState(10);// 订单取消
                order1.setOrderNumber(dataMap.getString("orderNumber"));
                order1.setYunId(dataMap.getString("payYunId"));
                Integer state = updateOrderPayStateBySm(order1);
                if (state == 1) {
                    //推送至POS消息
                    //安卓
                    HashMap map = new HashMap();
                    map.put("orderNumber", order.getOrderNumber());
                    map.put("payState", 2);
                    CommUmen.sendAndroidUnicast(order.getUmengToken(), map);
                }
                retVo.setStatusCode(1);
            }else{
                retVo.setMessage("类型失败");
                retVo.setStatusCode(0);
            }


        }catch (Exception e ){
            e.printStackTrace();
        }
        return retVo;
    }

    @Override
    public List<Order> searchOrder(Order order) {
        return orderDaoMapper.searchOrder(order);
    }

    @Override
    public Integer getCountByType(HashMap<Object, Object> map) {
        return orderDaoMapper.getCountByType(map);
    }

    @Override
    public List<Order> getOrderByNumber(String orderNumber) {
        return orderDaoMapper.getOrderByNumber(orderNumber);
    }

    @Override
    public List<RepairOrder> getRepairOrderByNumber(String orderNumber) {
        return orderDaoMapper.getRepairOrderByNumber(orderNumber);
    }

    @Override
    public Integer insertRepairOrder(HttpServletRequest request, List<Order> order) {
        Integer state = 0;
        try{
            User user = AppFunUtil.getUser(request);
            RepairOrder repairOrder = new RepairOrder();
            if(order.size() == 0){
                return state;
            }else{
                repairOrder.setOrderNumber(order.get(0).getOrderNumber());
                repairOrder.setRepairTime(new Date());
                repairOrder.setAlStatus(0);
                repairOrder.setUserName(user.getUsername());
                repairOrder.setLklPayTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(order.get(0).getLklPayTime()));
                orderDaoMapper.insertRepairOrder(repairOrder);
                state = 1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return state;
    }

    @Override
    public Integer updateRepairOrderStatus(RepairOrder repairOrder,int status) {
        Integer state = 0;
        try {
            RepairOrder repairOrder1 = new RepairOrder();
            repairOrder1.setOrderNumber(repairOrder.getOrderNumber());
            repairOrder1.setAlStatus(status);
            repairOrder1.setAlTime(new Date());
            orderDaoMapper.updateRepairOrderStatus(repairOrder1);
            state = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return state;
    }

    @Override
    public Integer updateOrderStateByNumber(RepairOrder repairOrder) {
        Integer state = 0;
        try{
            Order order1 = new Order();
            order1.setOrderNumber(repairOrder.getOrderNumber());
            order1.setPayTime(repairOrder.getLklPayTime());
            order1.setPayState(1);
            order1.setOrderState(1);
            orderDaoMapper.updateOrderStateByNumber(order1);
            state = 1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return state;
    }

    /**
     * 新增 二维码 付款单
     */
    public Integer updateAppPayState(MapGetterTool dataMap, DeviceInfo deviceInfo) {
        Integer state = 0;
        try {
            Order order = new Order();
            order.setOrderNumber(dataMap.getString("orderNumber"));
            order.setAppPayState(1);
            order.setAppPayTime(new Date());
            order.setAgentUnique(deviceInfo.getAgentUnique());
            orderDaoMapper.updateOrderState(order);
            state = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return state;
    }
    /**
     * 新增 二维码 付款单
     */
    public CommAppVo insertScanCodeOrder(MapGetterTool dataMap, DeviceInfo deviceInfo) {
        CommAppVo retVo = new CommAppVo();
        try {
            retVo.setStatusCode(0);
            /**
             * 创建订单
             */
            Order order = new Order();
            order.setCreateTime(new Date());
            order.setOrderNumber(FunUtil.randNumID());
            order.setMachineCode(deviceInfo.getMachineCode());
            order.setTotalFee(dataMap.getInteger("money"));//实付金额分
            order.setOrderType(2);//订单类型 POS 机付款 , 2 云支付扫码
            order.setOrderState(0);//订单状态 未付款
            order.setPayState(0);//支付状态 未支付
            order.setPayType("Y01");//支付类型 00 借记卡 01 贷记卡   91 微信 92 支付宝 93 百度钱包 96 拉卡拉钱包 Y01 云支付扫码
            order.setDeviceUnique(deviceInfo.getDeviceUnique());//设备唯一编码
            order.setAgentUnique(deviceInfo.getAgentUnique());//代理唯一性
            order.setMerchantYunId(deviceInfo.getMerchantYunId());//商户 云ID
            insertSelective(order);
            String code = "100:{\"mYId\":\""+deviceInfo.getMerchantYunId()+"\",\"fee\":"+order.getTotalFee()/100.00+",\"oId\":\""+order.getOrderNumber()+"\"}";
            HashMap map = new HashMap();
            map.put("code",code);
            map.put("orderId",order.getOrderNumber());
            retVo.setHashMap(map);
            retVo.setStatusCode(1);
            /**
             * 添加到内存 , 用于确认订单信息
             */
            HashMap orderMap = new HashMap();
            orderMap.put("orderId",order.getOrderNumber());
            orderMap.put("mYId",order.getMerchantYunId());
            orderMap.put("fee",order.getTotalFee()/100.00);//以元方式存储
            orderMap.put("timeout",order.getCreateTime().getTime()+120000);
            CommRedisFun.setKeyExpire(order.getOrderNumber(),orderMap,180);
        }catch (Exception e ){
            e.printStackTrace();
        }
        return retVo;
    }


    public CommAppVo checkOnePayQuota(MapGetterTool dataMap, PayCardQuota payCardQuota) {
        CommAppVo commAppVo = new CommAppVo();
        try {
            Integer money = dataMap.getInteger("money");//金额以分为单位 , 转换成元
            /**
             * 验证单笔是否超额
             */
            if( money > payCardQuota.getQuotaOne()){
                //单笔超过限额
                commAppVo.setStatusCode(20);
                commAppVo.setMessage(payCardQuota.getCardTypeMc()+"单笔限额"+(payCardQuota.getQuotaOne())/100+"元");
            }else{
                commAppVo.setStatusCode(1);
                commAppVo.setMessage("成功");
            }

            //检查 当前 用户金额 , 是否超过商户限额
            Integer state = 0;
        } catch (Exception e) {

        }
        return commAppVo;
    }


    /**
     * 检查云支付帐号 是否超额
     * @param dataMap
     * @return
     */
    public CommAppVo checkDayMonthQuota(MapGetterTool dataMap, PayCardQuota payCardQuota, Integer dayTotalFee, Integer monthTotalFee){
        CommAppVo commAppVo = new CommAppVo();
        try{

            Integer money = dataMap.getInteger("money");//金额以分为单位 , 转换成元

            //检查 当前 用户金额 , 是否超过商户限额
            Integer state = 0;
            state = checkCardQuota(money,payCardQuota,dayTotalFee,monthTotalFee);
            HashMap map = new HashMap();
            //储蓄卡超额 , 直接结束交易
            if(state == 1 ){
                //成功 , 未超过限额
                commAppVo.setStatusCode(1);
                commAppVo.setMessage("成功");
                return commAppVo;
            }else{
                commAppVo.setStatusCode(0);
                commAppVo.setMessage("失败");
                //储蓄卡 处理
                if(0 == payCardQuota.getCardType()){
                    if(state == 2){
                        //检查 当天已使用免费额度  是否 大于等于 限定额度
                        if(dayTotalFee >= payCardQuota.getQuotaDay()){
                            //今日免费额度已用光
                            //返回 APP 确认是否收取手续费
                            commAppVo.setStatusCode(25);
                            commAppVo.setMessage(payCardQuota.getCardTypeMc()+"当日超出限额!");
                        }else{
                            //今日 尚有免费余额
                            Integer fee =  payCardQuota.getQuotaDay()-dayTotalFee;
                            map.put("fee",fee);
                            commAppVo.setStatusCode(26);
                            commAppVo.setMessage(payCardQuota.getCardTypeMc()+"今日剩余额度"+( fee/100.00)+"元,请先使用");
                        }

                    }else if(state == 3){
                        //检查 当月已使用免费额度  是否 大于等于 限定额度
                        if(monthTotalFee >= payCardQuota.getQuotaMonth()){
                            //当月免费额度已用光
                            //返回 APP 确认是否收取手续费
                            commAppVo.setStatusCode(27);
                            commAppVo.setMessage(payCardQuota.getCardTypeMc()+"当月超出限额!");
                        }else{
                            //当月 尚有免费余额
                            Integer fee =  payCardQuota.getQuotaDay()-dayTotalFee;
                            map.put("fee",fee);
                            commAppVo.setStatusCode(28);
                            commAppVo.setMessage(payCardQuota.getCardTypeMc()+"当月剩余额度"+( fee/100.00)+"元,请先使用");
                        }
                    }
                }
                //信用卡 处理
                else if(1 == payCardQuota.getCardType()){
                    if(state == 2){
                        //检查 当天已使用免费额度  是否 大于等于 限定额度
                        if(dayTotalFee >= payCardQuota.getQuotaDay()){
                            //今日免费额度已用光
                            //返回 APP 确认是否收取手续费
                            commAppVo.setStatusCode(21);
                            commAppVo.setMessage(payCardQuota.getCardTypeMc()+"超额"+(money/100.00)+"元,需收取千份六手续费,是否同意继续.");
                        }else{
                            //今日 尚有免费余额
                            Integer fee =  payCardQuota.getQuotaDay()-dayTotalFee;
                            map.put("fee",fee);
                            commAppVo.setStatusCode(22);
                            commAppVo.setMessage(payCardQuota.getCardTypeMc()+"今日免费额度"+( fee/100.00)+"元,请先使用");
                        }

                    }else if(state == 3){
                        //检查 当月已使用免费额度  是否 大于等于 限定额度
                        if(monthTotalFee >= payCardQuota.getQuotaMonth()){
                            //当月免费额度已用光
                            //返回 APP 确认是否收取手续费
                            commAppVo.setStatusCode(23);
                            commAppVo.setMessage(payCardQuota.getCardTypeMc()+"超额"+(money/100.00)+"元,需收取千份六手续费,是否同意继续.");
                        }else{
                            //当月 尚有免费余额
                            Integer fee =  payCardQuota.getQuotaDay()-dayTotalFee;
                            map.put("fee",fee);
                            commAppVo.setStatusCode(24);
                            commAppVo.setMessage(payCardQuota.getCardTypeMc()+"当月免费额度"+( fee/100.00)+"元,请先使用");
                        }
                    }

                }

            }
            commAppVo.setHashMap(map);
        }catch (Exception e){
            e.printStackTrace();
        }
        return commAppVo;
    }
    public CommAppVo checkDayMonthQuota2(MapGetterTool dataMap, PayCardQuota payCardQuota, Integer dayTotalFee, Integer monthTotalFee){
        CommAppVo commAppVo = new CommAppVo();
        try{

            Integer money = dataMap.getInteger("money");//金额以分为单位 , 转换成元

            //检查 当前 用户金额 , 是否超过商户限额
            Integer state = 0;
            state = checkCardQuota(money,payCardQuota,dayTotalFee,monthTotalFee);
            HashMap map = new HashMap();
            //储蓄卡超额 , 直接结束交易
            if(state == 1 ){
                //成功 , 未超过限额
                if(0 == payCardQuota.getCardType()){
                    commAppVo.setStatusCode(1);
                    commAppVo.setMessage("成功");
                }else if(1 == payCardQuota.getCardType()){
                    //提示语重写 , 提示用户是否同意收取手续费
                    commAppVo.setStatusCode(21);
                    commAppVo.setMessage(payCardQuota.getCardTypeMc()+"超额"+(money/100.00)+"元,需收取千份六手续费,是否同意继续.");
                }else{
                    commAppVo.setStatusCode(0);
                    commAppVo.setMessage("失败");
                }
                return commAppVo;
            }else{
                commAppVo.setStatusCode(0);
                commAppVo.setMessage("失败");
                //储蓄卡 处理
                if(0 == payCardQuota.getCardType()){
                    if(state == 2){
                        //检查 当天已使用免费额度  是否 大于等于 限定额度
                        if(dayTotalFee >= payCardQuota.getQuotaDay()){
                            //今日免费额度已用光
                            //返回 APP 确认是否收取手续费
                            commAppVo.setStatusCode(25);
                            commAppVo.setMessage(payCardQuota.getCardTypeMc()+"当日超出限额!");
                        }else{
                            //今日 尚有免费余额
                            Integer fee =  payCardQuota.getQuotaDay()-dayTotalFee;
                            map.put("fee",fee);
                            commAppVo.setStatusCode(26);
                            commAppVo.setMessage(payCardQuota.getCardTypeMc()+"今日剩余额度"+( fee/100.00)+"元,请先使用");
                        }

                    }else if(state == 3){
                        //检查 当月已使用免费额度  是否 大于等于 限定额度
                        if(monthTotalFee >= payCardQuota.getQuotaMonth()){
                            //当月免费额度已用光
                            //返回 APP 确认是否收取手续费
                            commAppVo.setStatusCode(27);
                            commAppVo.setMessage(payCardQuota.getCardTypeMc()+"当月超出限额!");
                        }else{
                            //当月 尚有免费余额
                            Integer fee =  payCardQuota.getQuotaDay()-dayTotalFee;
                            map.put("fee",fee);
                            commAppVo.setStatusCode(28);
                            commAppVo.setMessage(payCardQuota.getCardTypeMc()+"当月剩余额度"+( fee/100.00)+"元,请先使用");
                        }
                    }
                }
                //信用卡 处理
                else if(1 == payCardQuota.getCardType()){
                    if(state == 2){
                        //检查 当天已使用免费额度  是否 大于等于 限定额度
                        if(dayTotalFee >= payCardQuota.getQuotaDay()){
                            //今日免费额度已用光
                            //返回 APP 确认是否收取手续费
                            commAppVo.setStatusCode(0);
                            commAppVo.setMessage(payCardQuota.getCardTypeMc()+"当日超出限额!");
                        }else{
                            //今日 尚有免费余额
                            Integer fee =  payCardQuota.getQuotaDay()-dayTotalFee;
                            map.put("fee",fee);
                            commAppVo.setStatusCode(22);
                            commAppVo.setMessage(payCardQuota.getCardTypeMc()+"今日免费额度"+( fee/100.00)+"元,请先使用");
                        }

                    }else if(state == 3){
                        //检查 当月已使用免费额度  是否 大于等于 限定额度
                        if(monthTotalFee >= payCardQuota.getQuotaMonth()){
                            //当月免费额度已用光
                            //返回 APP 确认是否收取手续费
                            commAppVo.setStatusCode(0);
                            commAppVo.setMessage(payCardQuota.getCardTypeMc()+"当日超出限额!");
                        }else{
                            //当月 尚有免费余额
                            Integer fee =  payCardQuota.getQuotaDay()-dayTotalFee;
                            map.put("fee",fee);
                            commAppVo.setStatusCode(24);
                            commAppVo.setMessage(payCardQuota.getCardTypeMc()+"当月免费额度"+( fee/100.00)+"元,请先使用");
                        }
                    }

                }

            }
            commAppVo.setHashMap(map);
        }catch (Exception e){
            e.printStackTrace();
        }
        return commAppVo;
    }


    /**
     * 检查云支付帐号 是否超额
     * @param dataMap
     * @return
     */
    public CommAppVo checkExcessDayMonthQuota(MapGetterTool dataMap, PayCardQuota payCardQuota, Integer dayTotalFee, Integer monthTotalFee){
        CommAppVo commAppVo = new CommAppVo();
        try{

            Integer money = dataMap.getInteger("money");//金额以分为单位 , 转换成元

            //检查 当前 用户金额 , 是否超过商户限额
            Integer state = 0;
            HashMap map = new HashMap();
            commAppVo.setStatusCode(0);
            commAppVo.setMessage("失败");
            //储蓄卡 处理
            if(0 == payCardQuota.getCardType()){
                //检查超额 当天剩余额度
                if(dayTotalFee+money >= payCardQuota.getQuotaDay()){
                    //检查 , 今日额度使用超过 , 返回剩余额度并重新刷卡
                    //今日 尚有超额配额
                    Integer fee =  payCardQuota.getQuotaDay()-dayTotalFee;
                    map.put("fee",fee);
                    commAppVo.setStatusCode(22);
                    commAppVo.setMessage(payCardQuota.getCardTypeMc()+"今日剩余可用收费额度"+( fee/100.00)+"元!");
                }else{
                    //检查 , 今日未超额 , 提示是否同意收取手续费
                }
                //检查 当月已使用免费额度  是否 大于等于 限定额度
                if(monthTotalFee >= payCardQuota.getQuotaMonth()){
                    //当月免费额度已用光
                    //返回 APP 确认是否收取手续费
                    commAppVo.setStatusCode(27);
                    commAppVo.setMessage(payCardQuota.getCardTypeMc()+"当月超出限额!");
                }
            }
            //信用卡 处理
            else if(1 == payCardQuota.getCardType()){

                //检查超额 当天剩余额度
                if(dayTotalFee+money >= payCardQuota.getQuotaDay()){
                    //检查 , 今日额度使用超过 , 返回剩余额度并重新刷卡
                    //今日 尚有超额配额
                    Integer fee =  payCardQuota.getQuotaDay()-dayTotalFee;
                    map.put("fee",fee);
                    commAppVo.setStatusCode(22);
                    commAppVo.setMessage(payCardQuota.getCardTypeMc()+"今日剩余可用收费额度"+( fee/100.00)+"元!");
                }else{
                    //检查 , 今日未超额 , 提示是否同意收取手续费
                    commAppVo.setStatusCode(21);
                    commAppVo.setMessage(payCardQuota.getCardTypeMc()+"超额"+(money/100.00)+"元,需收取千份六手续费,是否同意继续.");
                }

                //检查超额 当月剩余额度
                if(monthTotalFee+money >= payCardQuota.getQuotaMonth()){
                    //检查 , 当月额度使用超过 , 返回剩余额度并重新刷卡
                    //当月 尚有超额配额
                    Integer fee =  payCardQuota.getQuotaMonth()-monthTotalFee;
                    map.put("fee",fee);
                    commAppVo.setStatusCode(24);
                    commAppVo.setMessage(payCardQuota.getCardTypeMc()+"当月剩余可用收费额度"+( fee/100.00)+"元!");
                }else{
                    //检查 , 当月未超额 , 提示是否同意收取手续费
                    commAppVo.setStatusCode(23);
                    commAppVo.setMessage(payCardQuota.getCardTypeMc()+"超额"+(money/100.00)+"元,需收取千份六手续费,是否同意继续.");
                }
            }

            commAppVo.setHashMap(map);
        }catch (Exception e){
            e.printStackTrace();
        }
        return commAppVo;
    }

    /**
     * 验证卡限额
     * @return
     */
    private Integer checkCardQuota(Integer money,PayCardQuota payCardQuota,Integer dayTotalFee, Integer monthTotalFee){
        Integer state =0;
        try{
            /**
             * 验证当日是否超额
             */
            if( (money+dayTotalFee) > payCardQuota.getQuotaDay() &&  payCardQuota.getQuotaDay() != -100 ){
                //当日超过限额
                state = 2;
                return state;
            }
            /**
             * 验证当月是否超额
             */
            if( (money+monthTotalFee) > payCardQuota.getQuotaMonth()  &&  payCardQuota.getQuotaDay() != -100){
                //当月超过限额
                state = 3;
                return state;
            }
            state = 1;
        }catch (Exception e ){
            e.printStackTrace();
        }
        return state;
    }

    private String dealCard(String card){
        try{
            if(!StringUtils.isEmpty(card)){
                card = card.substring(0,6)+"******"+card.substring(12,card.length());
            }
        }catch (Exception e ){

        }
        return card;
    }


}
