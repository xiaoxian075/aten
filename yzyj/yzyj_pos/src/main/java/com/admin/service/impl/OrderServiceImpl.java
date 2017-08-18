package com.admin.service.impl;

import com.admin.dao.OrderDaoMapper;
import com.admin.model.*;
import com.admin.service.DevicePayCardQuotaService;
import com.admin.service.OrderService;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import com.core.umen.CommUmen;
import com.core.util.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
    @Resource
    private DevicePayCardQuotaService devicePayCardQuotaService;

    @Override
    public GenericDao<Order, String> getDao() {
        return orderDaoMapper;
    }


    public List<Order> selectByExampleAndPage(DataTablesPage<Order> page, BaseExample baseExample){
        return orderDaoMapper.selectByExampleAndPage(page,baseExample);
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
                retVo.setMessage("订单信息不存在");
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
                order1.setTotalFee(realAmount);
                System.out.println("=======================扫码付款成功 , 进行推送=======================");
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
                        map.put("clientYunId",FunUtil.dealStrHide(order1.getYunId(),4));//顾客云ID
                        map.put("payTime",FunUtil.getFormatDate(order1.getPayTime(),"yyyy-MM-dd HH:mm:ss"));//付款时间
                        map.put("realAmount", realAmount);//实付金额
                        //特殊情况下，如果商户使用优惠券
                        map.put("orderAmount", order.getTotalFee());//订单金额
                        map.put("preferentialPrice", (order.getTotalFee() > realAmount  ) ? order.getTotalFee()-realAmount:0);//优惠金额
                        map.put("payState", 1);
                        CommUmen.sendAndroidUnicast(order.getUmengToken(), map);
                        order1.setOrderState(2);
                    }
                    Integer state = updateOrderPayStateBySm(order1);
                }
                System.out.println("=======================友盟信息推送成功=======================");
                retVo.setStatusCode(1);
            }else if("cancelPayNotice".equals(dataMap.getString("noticeType"))){
                System.out.println("=======================用户取消扫码支付=======================");
                Order order1 = new Order();
                order1.setOrderState(10);// 订单取消
                order1.setOrderNumber(dataMap.getString("orderNumber"));
                order1.setYunId(dataMap.getString("payYunId"));
                Integer state = updateOrderPayState(order1);
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
    public List<Order> getOrderByNumber(String orderNumber) {
        return orderDaoMapper.getOrderByNumber(orderNumber);
    }

    @Override
    public List<Order> getOrderByNumberAndOrderType(String orderNumber,int type) {
        return orderDaoMapper.getOrderByNumberAndOrderType(orderNumber,type);
    }


    @Override
    public Integer getCountPay(String orderNumber) {
        return orderDaoMapper.getCountPay(orderNumber);
    }


    @Override
    public List<OrderPosDetail> getOrderDetailListByMap(HashMap<String, String> map) {
        return orderDaoMapper.getOrderDetailListByMap(map);
    }

    @Override
    public List<OrderPosDetail> getOrderDetailDayListByMap(HashMap<String, String> map) {
        return orderDaoMapper.getOrderDetailDayListByMap(map);
    }


    /**
     * APP接收成功，修改订单状态
     */
    public Integer updateAppPayState(MapGetterTool dataMap, DeviceInfo deviceInfo) {
        Integer state = 0;
        try {
            Order order = new Order();
            order.setOrderNumber(dataMap.getString("orderNumber"));
            order.setAppPayState(1);
            order.setAppPayTime(new Date());
            order.setAgentUnique(deviceInfo.getAgentUnique());
            order.setPayState(1);
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
            String code = "100:{\"mYId\":\""+deviceInfo.getMerchantYunId()+"\",\"fee\":"+FunUtil.fenToYuan(order.getTotalFee())+",\"oId\":\""+order.getOrderNumber()+"\"}";
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
            orderMap.put("fee",FunUtil.fenToYuan(order.getTotalFee()));//以元方式存储
            orderMap.put("timeout",order.getCreateTime().getTime()+120000);
            orderMap.put("createTime",order.getCreateTime());
            System.out.println("二维码订单信息："+orderMap);
            CommRedisFun.setKeyExpire(order.getOrderNumber(),orderMap,120);
            System.out.println("===================把二维码订单信息存在REDIS,过期时间120秒=================");
        }catch (Exception e ){
            e.printStackTrace();
        }
        return retVo;
    }

    /**
     * 新增订单验证
     */
    public CommAppVo insertOrderCheck(MapGetterTool dataMap, DeviceInfo deviceInfo){
        CommAppVo retVo = new CommAppVo();
        try{
            /**
             * 获取分组额度
             */
            DevicePayCardQuota devicePayCardQuota = getDevicePayCardQuota(deviceInfo.getQuotaGroup());
            if(devicePayCardQuota == null){
                retVo.setMessage("设备未在系统配置额度内");
                retVo.setStatusCode(0);
                return retVo;
            }
            /**
             * 获取卡类型的额度
             */
            PayCardQuota payCardQuota = getPayCardQuota(dataMap.getInteger("cardType"),dataMap.getInteger("quotaType"),devicePayCardQuota);
            if(payCardQuota == null){
                retVo.setMessage("设备未在卡类型限额内");
                retVo.setStatusCode(0);
                return retVo;
            }
            /**
             * 查询免费额度的当天,当月总额
             */
            HashMap tmpMap = new HashMap();
            tmpMap.put("deviceUnique",deviceInfo.getDeviceUnique());
            tmpMap.put("cardType",dataMap.getString("cardType"));
            /**
             * 统计免费额度的使用情况
             */
            MapGetterTool mapGetterTool = new MapGetterTool(devicePayCardQuotaService.selectDevicePaySumFee(tmpMap));
            Integer monthTotalRealAmount = mapGetterTool.getInteger("MONTH_TOTAL_REAL_AMOUNT");
            Integer dayTotalRealAmount  = mapGetterTool.getInteger("DAY_TOTAL_REAL_AMOUNT");

            System.out.println("当月已经用免费额度="+monthTotalRealAmount);
            System.out.println("当天已经用免费额度="+dayTotalRealAmount);
            /**
             * isCounterFee 是否收取手续费
             * 0  不同意  1  同意
             */
            Integer isCounterFee = dataMap.getInteger("isCounterFee");
            /**
             * 如果是商户不限额，直接刷 并且是借记卡
             */
            if("10".equals(dataMap.getString("quotaType")) && "0".equals(dataMap.getString("cardType"))){
                if(payCardQuota.getQuotaDay() == 0 || payCardQuota.getQuotaMonth() == 0){
                    CommAppVo tmpVo = checkOnePayQuota(dataMap,payCardQuota);
                    if(tmpVo.getStatusCode() != 1){
                        return tmpVo;
                    }
                    retVo = checkNoDayMonthQuota();
                    if( retVo.getStatusCode() != 1 && isCounterFee == 0){
                        return retVo;
                    }
                }
            }else{
                /**
                 *  其他属性 判断免费额度是否大于配置额度
                 */
                if(dayTotalRealAmount >= payCardQuota.getQuotaDay() || monthTotalRealAmount >= payCardQuota.getQuotaMonth()) {
                    /**
                     * 统计超额的使用情况
                     */
                    mapGetterTool = new MapGetterTool(devicePayCardQuotaService.selectDeviceExcessPaySumFee(tmpMap));
                    Integer dayTotalExcessAmount = mapGetterTool.getInteger("DAY_TOTAL_EXCESS_AMOUNT");
                    Integer monthTotalExcessAmount = mapGetterTool.getInteger("MONTH_TOTAL_EXCESS_AMOUNT");
                    if("1".equals(dataMap.getString("cardType"))) {
                        //贷记卡 重新定义额度
                        payCardQuota.setQuotaOne(devicePayCardQuota.getXykExcessOne());
                        payCardQuota.setQuotaDay(devicePayCardQuota.getXykExcessDay());
                        payCardQuota.setQuotaMonth(devicePayCardQuota.getXykExcessMonth());
                    }else{
                        if("11".equals(dataMap.getString("quotaType"))){
                            //金钻 重新定义额度
                            payCardQuota.setQuotaOne(devicePayCardQuota.getCxkExcessOne());
                            payCardQuota.setQuotaDay(devicePayCardQuota.getCxkExcessDay());
                            payCardQuota.setQuotaMonth(devicePayCardQuota.getCxkExcessMonth());
                        }
                    }
                    CommAppVo tmpVo = checkOnePayQuota(dataMap,payCardQuota);
                    if(tmpVo.getStatusCode() != 1){
                        return tmpVo;
                    }
                    retVo = checkExcessDayMonthQuota(dataMap,payCardQuota,dayTotalExcessAmount,monthTotalExcessAmount);
                    if( retVo.getStatusCode() != 1 && isCounterFee == 0){
                        return retVo;
                    }
                }else{
                    //检查 当天未超额
                    CommAppVo tmpVo = checkOnePayQuota(dataMap,payCardQuota);
                    if(tmpVo.getStatusCode() != 1){
                        return tmpVo;
                    }
                    retVo = checkDayMonthQuota(dataMap,payCardQuota,dayTotalRealAmount,monthTotalRealAmount);
                    if( retVo.getStatusCode() != 1 && isCounterFee == 0){
                        return retVo;
                    }
                }
            }
            dataMap.setMapVal("realAmount",retVo.getIntData());
            retVo = insertOrder(dataMap,deviceInfo,isCounterFee);
            if(retVo.getStatusCode() != 1){
                retVo.setStatusCode(100);
                retVo.setMessage("创建订单失败!");
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return retVo;
    }

    /**
     * 验证通过，插入订单
     * @param dataMap
     * @param deviceInfo
     * @return
     */
    public CommAppVo insertOrder(MapGetterTool dataMap,DeviceInfo deviceInfo,int isCounterFee){
        CommAppVo commAppVo = new CommAppVo();
        HashMap map = new HashMap();
        Integer counterFee = 0;
        Order order = new Order();
        try{
            if(isCounterFee == 1 ){
                /**
                 * 以分为单位转换成元* 0.006
                 * 2位小数点后有值 ,直接进 1
                 */
                double money = dataMap.getInteger("money")/100*0.006;
                BigDecimal bigDecimal = new BigDecimal(money);
                float amount = bigDecimal.setScale(2,BigDecimal.ROUND_UP).floatValue()*100;
                //再转成分为单位
                counterFee = Math.round(amount);
                order.setExcessAmount(dataMap.getInteger("realAmount"));
                order.setRealAmount(dataMap.getInteger("money") - dataMap.getInteger("realAmount"));
            }else{
                order.setExcessAmount(0);
                order.setRealAmount(dataMap.getInteger("money"));
            }
            /**
             * 创建订单
             */
            order.setCreateTime(new Date());
            order.setOrderNumber(dataMap.getString("orderNo"));
            order.setYunId(dataMap.getString("payYunId"));
            order.setMachineCode(deviceInfo.getMachineCode());
            order.setTotalFee(dataMap.getInteger("money"));//订单金额
            order.setCardNo(dealCard(dataMap.getString("cardNo")));
            order.setCardType(dataMap.getString("cardType"));
            order.setOrderType(1);//订单类型 POS 机付款
            order.setOrderState(0);//订单状态 未付款
            order.setPayState(0);//支付状态 未支付
            order.setPayType("0"+dataMap.getString("cardType"));//支付类型 00 借记卡 01 贷记卡   91 微信 92 支付宝 93 百度钱包 96 拉卡拉钱包
            order.setDeviceUnique(deviceInfo.getDeviceUnique());//设备唯一编码
            order.setAgentUnique(deviceInfo.getAgentUnique());//代理唯一性
            order.setMerchantYunId(deviceInfo.getMerchantYunId());//商户 云ID
            order.setCounterFee(counterFee);//收取手续费
            insertSelective(order);
            map.put("orderNumber",order.getOrderNumber());
            map.put("totalFee",order.getTotalFee());
            commAppVo.setHashMap(map);
            commAppVo.setStatusCode(1);
            commAppVo.setSuccess(true);
            commAppVo.setMessage("创建订单成功");
            System.out.print("=========================结束创建刷卡订单=============================");
        }catch (Exception e){
            e.printStackTrace();
            commAppVo.setStatusCode(100);
        }
        return commAppVo;
    }

    /**
     * 获取分组额度
     * @param quotaGroup
     * @return
     */
    public DevicePayCardQuota getDevicePayCardQuota(String quotaGroup){
        String quotaKey;
        if("99".equals(quotaGroup)){
            quotaKey = "CardQuota"+quotaGroup+"&"+quotaGroup;
        }else{
            quotaKey = "CardQuota"+quotaGroup;
        }
        DevicePayCardQuota devicePayCardQuota = CommConstant.devicePayCardQuotaMapList.get(quotaKey);
        if(devicePayCardQuota == null){
            return devicePayCardQuota;
        }
        return devicePayCardQuota;
    }

    /**
     * 设置卡类型额度
     * @param cardType
     * @param quotaType
     * @param devicePayCardQuota
     * @return
     */
    public PayCardQuota getPayCardQuota(Integer cardType,Integer quotaType, DevicePayCardQuota devicePayCardQuota){
        PayCardQuota payCardQuota = new PayCardQuota();
        payCardQuota.setCardType(cardType);
        payCardQuota.setCardTypeMc(CommDictList.getDictMc("t_pay_card_quota_card_type",cardType.toString()));
        payCardQuota.setQuotaType(quotaType);
        if("0".equals(cardType.toString())){
            //借记卡
            payCardQuota.setQuotaOne(devicePayCardQuota.getCxkOne());
            payCardQuota.setQuotaDay(devicePayCardQuota.getCxkDay());
            payCardQuota.setQuotaMonth(devicePayCardQuota.getCxkMonth());
        }else if("1".equals(cardType.toString())){
            //贷记卡
            payCardQuota.setQuotaOne(devicePayCardQuota.getXykOne());
            payCardQuota.setQuotaDay(devicePayCardQuota.getXykDay());
            payCardQuota.setQuotaMonth(devicePayCardQuota.getXykMonth());
        }else{
          return payCardQuota;
        }
        return payCardQuota;
    }
    /**
     * 检查云支付帐号 是否超额
     * @param dataMap
     * @return
     */
    public CommAppVo checkExcessDayMonthQuota(MapGetterTool dataMap,PayCardQuota payCardQuota,Integer dayTotalExcessAmount,Integer monthTotalExcessAmount){
        CommAppVo commAppVo = new CommAppVo();
        try{
            Integer money = dataMap.getInteger("money");//金额以分为单位 , 转换成元
            //检查 当前 用户金额 , 是否超过商户限额
            Integer state = 0;
            state = checkExcessCardQuota(money,payCardQuota,dayTotalExcessAmount,monthTotalExcessAmount);
            HashMap map = new HashMap();
            //储蓄卡超额 , 直接结束交易
            if(state == 1 ){
                //成功 , 未超过限额
                commAppVo.setStatusCode(21);
                commAppVo.setIntData(money);
                commAppVo.setMessage(payCardQuota.getCardTypeMc()+"超过今日额度"+FunUtil.fenToYuan(money)+"元,需收6‰手续费！是否同意继续.");
                return commAppVo;
            }else{
                commAppVo.setStatusCode(0);
                commAppVo.setMessage("失败");
                //储蓄卡 处理
                if(0 == payCardQuota.getCardType()){
                    if(state == 2){
                        //检查 当天已使用免费额度  是否 大于等于 限定额度
                        if(dayTotalExcessAmount >= payCardQuota.getQuotaDay()){
                            commAppVo.setStatusCode(20);
                            commAppVo.setMessage(payCardQuota.getCardTypeMc()+"今日已超出全部限额,请明天再使用！");
                            return commAppVo;
                        }else if((money + dayTotalExcessAmount) > payCardQuota.getQuotaDay()){
                            //今日 尚有免费余额
                            Integer fee =  payCardQuota.getQuotaDay() - dayTotalExcessAmount;
                            commAppVo.setStatusCode(22);
                            commAppVo.setMessage(payCardQuota.getCardTypeMc()+"今日超额刷卡剩余额度"+FunUtil.fenToYuan(fee)+"元,请不要超过！");
                            map.put("fee",fee);
                            commAppVo.setHashMap(map);
                            return commAppVo;
                        }else if((money + dayTotalExcessAmount) < payCardQuota.getQuotaDay()){
                            commAppVo.setStatusCode(21);
                            commAppVo.setMessage(payCardQuota.getCardTypeMc()+"超过今日额度"+FunUtil.fenToYuan(money)+"元,需收6‰手续费！是否同意继续.");
                        }
                    }else if(state == 3){
                        //检查 当月已使用免费额度  是否 大于等于 限定额度
                        if(monthTotalExcessAmount >= payCardQuota.getQuotaMonth()){
                            commAppVo.setStatusCode(20);
                            commAppVo.setMessage(payCardQuota.getCardTypeMc()+"当前月已超出全部限额,请下个月再使用！");
                            return commAppVo;
                        }else{
                            Integer fee =  payCardQuota.getQuotaMonth() - monthTotalExcessAmount;
                            commAppVo.setStatusCode(22);
                            commAppVo.setMessage(payCardQuota.getCardTypeMc()+"当前月超额刷卡剩余额度"+FunUtil.fenToYuan(fee)+"元,请不要超过！");
                            map.put("fee",fee);
                            commAppVo.setHashMap(map);
                            return commAppVo;
                        }
                    }
                }
                //信用卡 处理
                else if(1 == payCardQuota.getCardType()){
                    if(state == 2){
                        //检查 当天已使用免费额度  是否 大于等于 限定额度
                        if(dayTotalExcessAmount >= payCardQuota.getQuotaDay()){
                            commAppVo.setStatusCode(20);
                            commAppVo.setMessage(payCardQuota.getCardTypeMc()+"今日已超出全部限额,请明天再使用！");
                            return commAppVo;
                        }else if((money + dayTotalExcessAmount) > payCardQuota.getQuotaDay()){
                            //今日 尚有免费余额
                            Integer fee =  payCardQuota.getQuotaDay()-dayTotalExcessAmount;
                            commAppVo.setStatusCode(22);
                            commAppVo.setMessage(payCardQuota.getCardTypeMc()+"今日超额刷卡剩余额度"+FunUtil.fenToYuan(fee)+"元,请不要超过！");
                            map.put("fee",fee);
                            commAppVo.setHashMap(map);
                            return commAppVo;
                        }else if((money + dayTotalExcessAmount) < payCardQuota.getQuotaDay()){
                            commAppVo.setStatusCode(21);
                            commAppVo.setMessage(payCardQuota.getCardTypeMc()+"超过今日额度"+FunUtil.fenToYuan(money)+"元,需收6‰手续费！是否同意继续.");
                        }
                    }else if(state == 3){
                        //检查 当月已使用免费额度  是否 大于等于 限定额度
                        if(monthTotalExcessAmount >= payCardQuota.getQuotaMonth()){
                            commAppVo.setStatusCode(20);
                            commAppVo.setMessage(payCardQuota.getCardTypeMc()+"当前月已超出全部限额,请下个月再使用！");
                            return commAppVo;
                        }else if((money + monthTotalExcessAmount) > payCardQuota.getQuotaMonth()){
                            //当月 尚有免费余额
                            Integer fee =  payCardQuota.getQuotaMonth() - monthTotalExcessAmount;
                            commAppVo.setStatusCode(22);
                            commAppVo.setMessage(payCardQuota.getCardTypeMc()+"当前月超额刷卡剩余额度"+FunUtil.fenToYuan(fee)+"元,请不要超过！");
                            map.put("fee",fee);
                            commAppVo.setHashMap(map);
                            return commAppVo;
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 检查正常免费额度
     * dataMap：消费金额
     * payCardQuota：卡类型的额度
     * dayTotalRealAmount：当天已经消费多少
     * monthTotalRealAmount：当月已经消费多少
     * @param dataMap
     * @return
     */
    public CommAppVo checkDayMonthQuota(MapGetterTool dataMap,PayCardQuota payCardQuota,Integer dayTotalRealAmount,Integer monthTotalRealAmount ){
        CommAppVo commAppVo = new CommAppVo();
        try{
            Integer money = dataMap.getInteger("money");//金额以分为单位 , 转换成元
            //检查 当前 用户金额 , 是否超过商户限额
            Integer state = 0;
            HashMap map = new HashMap();
            state = checkCardQuota(money,payCardQuota,dayTotalRealAmount,monthTotalRealAmount);
            //剩余当天免费额度
            Integer feeDayMoney = payCardQuota.getQuotaDay() - dayTotalRealAmount;
            //剩余当月免费额度
            Integer feeMonthMoney = payCardQuota.getQuotaMonth() - monthTotalRealAmount;
            //如果用户刷的这笔金额加上当天已经消费过的金额大于当天卡类型的额度就超额，超额提示先把免费额度用完
            if(state == 2 ){
                commAppVo.setStatusCode(22);//
                commAppVo.setMessage(payCardQuota.getCardTypeMc()+"今日免费额度剩余"+FunUtil.fenToYuan(feeDayMoney)+"元,请先使用！");
                map.put("fee",feeDayMoney);
                commAppVo.setHashMap(map);
                return commAppVo;
            }else if(state == 3){
                commAppVo.setStatusCode(22);
                commAppVo.setMessage(payCardQuota.getCardTypeMc()+"本月免费额度剩余"+FunUtil.fenToYuan(feeMonthMoney)+"元,请先使用！");
                map.put("fee",feeMonthMoney);
                commAppVo.setHashMap(map);
                return commAppVo;
            }else{
                commAppVo.setStatusCode(1);
                commAppVo.setMessage("成功");
                return commAppVo;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return commAppVo;
    }
    /**
     * 商户储蓄卡  不限额
     * @return
     */
    public CommAppVo checkNoDayMonthQuota(){
        CommAppVo commAppVo = new CommAppVo();
        try{
            commAppVo.setStatusCode(1);
            commAppVo.setMessage("成功");
            return commAppVo;
        }catch (Exception e){
            e.printStackTrace();
        }
        return commAppVo;
    }

    /**
     * 超额之后当天当月是否超额
     * @return
     */
    private Integer checkExcessCardQuota(Integer money,PayCardQuota payCardQuota,Integer dayTotalExcessAmount,Integer monthTotalExcessAmount){
        Integer state =0;
        try{
            /**
             * 验证当日是否超额
             */
            if( (money + dayTotalExcessAmount) > payCardQuota.getQuotaDay()){
                //当日超过限额
                state = 2;
                return state;
            }
            /**
             * 验证当月是否超额
             */
            if( (money + monthTotalExcessAmount) > payCardQuota.getQuotaMonth()){
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
    /**
     * 验证免费额度是否超额
     * @return
     */
    private Integer checkCardQuota(Integer money,PayCardQuota payCardQuota,Integer dayTotalRealAmount, Integer monthTotalRealAmount){
        Integer state =0;
        try{
            /**
             * 验证当日是否超额
             */
            if( (money + dayTotalRealAmount) > payCardQuota.getQuotaDay()){
                //当日超过限额
                state = 2;
                return state;
            }
            /**
             * 验证当月是否超额
             */
            if( (money + monthTotalRealAmount) > payCardQuota.getQuotaMonth()){
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
    /**
     * 验证单笔是否超额
     * @param dataMap
     * @param payCardQuota
     * @return
     */
    public CommAppVo checkOnePayQuota(MapGetterTool dataMap,PayCardQuota payCardQuota) {
        CommAppVo commAppVo = new CommAppVo();
        try {
            Integer money = dataMap.getInteger("money");//金额以分为单位 , 转换成元
            if( money > payCardQuota.getQuotaOne()){
                //单笔超过限额
                commAppVo.setStatusCode(20);
                commAppVo.setMessage(payCardQuota.getCardTypeMc()+"单笔限额"+FunUtil.fenToYuan(payCardQuota.getQuotaOne())+"元");
            }else{
                commAppVo.setStatusCode(1);
                commAppVo.setMessage("成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return commAppVo;
    }

    /**
     * 设备卡的保密
     * @param card
     * @return
     */
    private String dealCard(String card){
        try{
            if(!StringUtils.isEmpty(card)){
                card = card.substring(0,6)+"******"+card.substring(card.length()-4,card.length());
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return card;
    }
}
