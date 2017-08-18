package com.app;

import com.admin.model.DeviceInfo;
import com.admin.service.DeviceInfoService;
import com.admin.service.OrderService;
import com.alibaba.fastjson.JSONObject;
import com.core.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/10/7.
 */
@Controller
@RequestMapping(value = "/app/pos")
public class AppPosController {
    @Resource
    private DeviceInfoService deviceInfoService;
    @Resource
    private OrderService orderService;

    /**
     * app付款状态更新 同时开通往云支付那边充钱
     */
    @RequestMapping(value = "appPayStateUpdate", method = RequestMethod.POST)
    @ResponseBody
    public String appPayStateUpdate(HttpServletRequest request,String data) {
        System.out.println("=======================接收设备APP状态成功信息,针对用户刷卡==========================");
        try{
            /**
             * 获取 Map 对象
             */
            MapGetterTool hashMap = new MapGetterTool(AppFunUtil.getDataMap(data));
            if(hashMap == null){
                return AppDesUtil.posAppDesEncrypt(null,"app付款状态更新失败",0);
            }
            /**
             * 验证是否登入 , 并返回对象
             */
            DeviceInfo deviceInfo = AppFunUtil.checkToken(request,deviceInfoService,hashMap.getString("token"));
            if(deviceInfo.getState() != 1 ){
                return AppDesUtil.posAppDesEncrypt(null,deviceInfo.getNote(),deviceInfo.getState());
            }
            /**
             * 验证有没有收到拉卡拉服务端的信息
             */
            String orderNumber = hashMap.getString("orderNumber");
            Integer countPay = orderService.getCountPay(orderNumber);
            if(countPay == 0 || countPay == 2){//如果没收到，或者有两条记录
                return AppDesUtil.posAppDesEncrypt(null,"拉卡拉服务端确认信息失败",0);
            }
            /**
             * 更新订单 , APP 付款成功状态更新
             */
            Integer state = orderService.updateAppPayState(hashMap,deviceInfo);
            if(state ==1){
                /**
                 *  只能收到pos机发过来的请求，才开始扫描
                 */
                System.out.println("=======================更新APP付款成功，开启定时扫描==========================");
                CommRedisFun.setHKey("staticData","blnOrderJob","1");
                return AppDesUtil.posAppDesEncrypt(null,"开启定时成功",1);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return AppDesUtil.posAppDesEncrypt(null,"app付款状态更新失败",0);
    }

    /**
     * 生成付款二维码
     */
    @RequestMapping(value = "buildPayCode", method = RequestMethod.POST)
    @ResponseBody
    public String buildPayCode(HttpServletRequest request,String data) {
        System.out.println("=======================开始生成付款二维码==========================");
        try{
            /**
             * 获取 Map 对象
             */
            MapGetterTool hashMap = new MapGetterTool(AppFunUtil.getDataMap(data));
            if(hashMap == null){
                return AppDesUtil.posAppDesEncrypt(null,"生成付款二维码失败",0);
            }
            /**
             * 验证是否登入 , 并返回对象
             */
            DeviceInfo deviceInfo = AppFunUtil.checkToken(request,deviceInfoService,hashMap.getString("token"));
            if(deviceInfo.getState() != 1 ){
                return AppDesUtil.posAppDesEncrypt(null,deviceInfo.getNote(),deviceInfo.getState());
            }
            /**
             * 新增二维码付款订单
             */
            CommAppVo commAppVo = orderService.insertScanCodeOrder(hashMap,deviceInfo);
            System.out.println("=======================付款二维码生成成功==========================");
            if(commAppVo.getStatusCode() ==1){
                return AppDesUtil.posAppDesEncrypt(commAppVo.getHashMap(),"付款二维码生成成功",1);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return AppDesUtil.posAppDesEncrypt(null,"生成付款二维码失败",0);
    }


    /**
     * 生成订单号
     */
    @RequestMapping(value = "buildOrderNo", method = RequestMethod.POST)
    @ResponseBody
    public String buildOrderNo() {
        System.out.println("=====================生成订单号====================");
        return AppDesUtil.posAppDesEncrypt(FunUtil.randNumID(),"生成订单号成功",1);
    }

    /**
     * 获取云支付帐户银行卡信息
     * token
     * yunId
     */
    @RequestMapping(value = "getYunAccountInfo", method = RequestMethod.POST)
    @ResponseBody
    public String getYunAccountInfo(HttpServletRequest request,String data) {
        try{
            /**
             * 获取 Map 对象
             */
            MapGetterTool hashMap = new MapGetterTool(AppFunUtil.getDataMap(data));
            if(hashMap == null){
                return AppDesUtil.posAppDesEncrypt(null,"获取云支付帐户银行卡信息失败",0);
            }
            /**
             * 验证是否登入 , 并返回对象
             */
            DeviceInfo deviceInfo = AppFunUtil.checkToken(request,deviceInfoService,hashMap.getString("token"));
            if(deviceInfo.getState() != 1 ){
                return AppDesUtil.posAppDesEncrypt(null,deviceInfo.getNote(),deviceInfo.getState());
            }

            /**
             * 验证 云支付帐号 获取信息
             */
            java.util.HashMap yunInfoMap = getYunAccountInfo(hashMap.getString("yunId"));
            if(yunInfoMap == null){
                return AppDesUtil.posAppDesEncrypt(null,"获取云支付帐户银行卡信息失败",0);
            }

            CommAppVo yunCardVo = getYunAccountCardInfo(hashMap.getString("yunId"));
            if(yunCardVo.getStatusCode() != 1){
                return AppDesUtil.posAppDesEncrypt(null,yunCardVo.getMessage(),0);
            }

            //云支付数据转 MAP 获取
            MapGetterTool mapInfo = new MapGetterTool(yunInfoMap);
            MapGetterTool mapCard = new MapGetterTool(yunCardVo.getHashMap());
            //云支付返回失败
            if(mapInfo.getInteger("is") == 1 && mapCard.getInteger("is") == 1 ){
                java.util.HashMap map = new java.util.HashMap();
                MapGetterTool userInfo = new MapGetterTool(mapInfo.getMap("data"));
                map.put("yunName",userInfo.getString("userName"));
                map.put("yunId",userInfo.getString("loginName"));
                map.put("headPic",userInfo.getString("headPic"));
                map.put("lev",userInfo.getInteger("lev"));
                //循环获取银行卡号 , 封装 List 用于返回
                List<java.util.HashMap> cardList = mapCard.getList("data");
                java.util.List list = new java.util.ArrayList();
                for(java.util.HashMap cardMap :cardList){
                    java.util.HashMap tmpMap = new java.util.HashMap();
                    tmpMap.put("cardNo",cardMap.get("cardNo"));
                    tmpMap.put("cardName",cardMap.get("cardName"));
                    list.add(tmpMap);
                }
                map.put("cardList",list);

                /**
                 * 更新 设备 当前绑定用户
                 */
                DeviceInfo deviceInfo1 = new DeviceInfo();
                deviceInfo1.setMachineCode(deviceInfo.getMachineCode());
                deviceInfo1.setYunId(hashMap.getString("yunId"));
                deviceInfo1.setYunIdLev(hashMap.getInteger("lev"));
                deviceInfoService.update(deviceInfo1);
                return AppDesUtil.posAppDesEncrypt(map,"成功",1);
            }else{
                //云支付接口调用失败
                return AppDesUtil.posAppDesEncrypt(null,"获取云支付帐户银行卡信息失败",0);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return AppDesUtil.posAppDesEncrypt(null,"获取云支付帐户银行卡信息失败",0);
    }

    /**
     * 检查付款信息
     * String token,String orderNo,String payYunId,String cardNo ,Integer money , level , sign=MD5(token+&+card+&+cardType+&+money)
     */
    @RequestMapping(value = "checkPayInfo", method = RequestMethod.POST)
    @ResponseBody
    public String checkPayInfo(HttpServletRequest request,String data) {
        System.out.println("==========================开始验证刷卡付款信息=========================");
        try{
            /**
             * 获取 Map 对象
             */
            MapGetterTool hashMap = new MapGetterTool(AppFunUtil.getDataMap(data));
            if(hashMap == null){
                return AppDesUtil.posAppDesEncrypt(null,"验证刷卡参数失败",0);
            }

            /**
             * 验证是否登入 , 并返回对象
             */
            DeviceInfo deviceInfo = AppFunUtil.checkToken(request,deviceInfoService,hashMap.getString("token"));
            if(deviceInfo.getState() != 1 ){
                return AppDesUtil.posAppDesEncrypt(null,deviceInfo.getNote(),deviceInfo.getState());
            }
            /**
             * 商户自己刷卡
             */
            String ypId = hashMap.getString("payYunId");
            if(ypId.equals(deviceInfo.getMerchantYunId())){
                return AppDesUtil.posAppDesEncrypt(null,"商户不能给自己刷卡",0);
            }

            /**
             * 验证 云支付帐号 获取信息
             */
            java.util.HashMap yunInfoMap = getYunAccountInfo(ypId);
            if(yunInfoMap == null){
                return AppDesUtil.posAppDesEncrypt(null,"没有该云付通账号",0);
            }

            /**
             * 验证云支付账号是否异常
             */
            JSONObject object = JSONObject.parseObject(yunInfoMap.get("data").toString());
            if(!"1".equals(object.getString("state"))){
                return AppDesUtil.posAppDesEncrypt(null,"云付通账号异常,请联系客服",0);
            }

            /**
             * 验证云支付是否实名
             */
            if(!"1".equals(object.getString("statusRealname"))){
                return AppDesUtil.posAppDesEncrypt(null,"云付通账号未实名,请先实名",0);
            }

            /**
             * 刷卡至少要刷1块（100分）
             */
            if(hashMap.getInteger("money") < 100){
                return AppDesUtil.posAppDesEncrypt(null,"消费金额不能少于1.00元",0);
            }

            /**
             * 验证签名
             */
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(hashMap.getString("token")).append("&");
            stringBuffer.append(hashMap.getString("payYunId")).append("&");
            stringBuffer.append(hashMap.getString("orderNo")).append("&");
            stringBuffer.append(hashMap.getString("money")).append("&");
            stringBuffer.append(hashMap.getString("cardNo")).append("&").append(CommConstant.appPostDesKey);
            String sign = MD5Util.encodeByMD5(stringBuffer.toString());

            if (!sign.equals(hashMap.getString("sign"))){
                return AppDesUtil.posAppDesEncrypt(null,"检查付款信息验签失败",0);
            }
            // 检查订单号
            if(StringUtils.isEmpty(hashMap.getString("orderNo"))){
                return AppDesUtil.posAppDesEncrypt(null,"订单号有误",0);
            }

            System.out.println("==========================调用云支付获取用户卡号是否绑定=========================");
            String[] msg = checkYZFCardInfo(hashMap.getString("payYunId"),hashMap.getString("cardNo"));
            /**
             *  0	借记卡(企业卡基本户)
             *  1	贷记卡(企业卡一般户)
             */
            if(!"OK".equals(msg[0])){
                //检查不成功
                return AppDesUtil.posAppDesEncrypt(null,msg[0],0);
            }
            //设置 获取到的银行卡类型
            hashMap.setMapVal("cardType",msg[1]);

            System.out.print("用户刷卡卡号："+hashMap.getString("cardNo"));

            /**
             * 检查商户会员等级
             * lev          2是金钻
             * isMerchant   1存在商家资料
             */
            System.out.println("==========================调用云支付获取商家等级=========================");
            String quotaType = getYunAccountLev(deviceInfo.getMerchantYunId());
            if("ERR".equals(quotaType)){
                return AppDesUtil.posAppDesEncrypt(null,"失败,商家不是商户或金钻会员!",0);
            }
            hashMap.setMapVal("quotaType",quotaType);

            /**
             * 新增订单
             */
            CommAppVo commAppVo = orderService.insertOrderCheck(hashMap,deviceInfo);
            if(commAppVo.getStatusCode() == 1){
                return AppDesUtil.posAppDesEncrypt(commAppVo.getHashMap(),commAppVo.getMessage(),commAppVo.getStatusCode());
            }else{
                return AppDesUtil.posAppDesEncrypt(commAppVo.getHashMap(),commAppVo.getMessage(),commAppVo.getStatusCode());
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return AppDesUtil.posAppDesEncrypt(null," 检查付款信息失败",0);
    }

    /**
     * 获取商户推荐二维码地址
     * String token
     */
    @RequestMapping(value = "getMerchantYunId", method = RequestMethod.POST)
    @ResponseBody
    public String getMerchantYunId(HttpServletRequest request,String data) {
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
            DeviceInfo deviceInfo = AppFunUtil.checkToken(request,deviceInfoService,hashMap.getString("token"));
            if(deviceInfo.getState() != 1 ){
                return AppDesUtil.posAppDesEncrypt(null,deviceInfo.getNote(),deviceInfo.getState());
            }
            String link = CommDictList.getDictVal("app_pos","register_link");
            Format fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String code = fmt.format(new Date())+deviceInfo.getMerchantYunId();
            link = link + deviceInfo.getMerchantYunId()+"&code="+code;
            return AppDesUtil.posAppDesEncrypt(link,"成功",1);
        }catch (Exception e ){
            e.printStackTrace();
        }
        return AppDesUtil.posAppDesEncrypt(null,"获取商户推荐二维码地址失败",0);
    }

    /**
     * 检查云支付帐号卡号信息
     * @param yunId
     * @param cardNo
     * @return
     */
    private String[] checkYZFCardInfo(String yunId,String cardNo){
        String retMsg []= {"银行卡未绑定,请先到云付通绑定","0"};
        try{
            /**
             * 获取 银行卡号
             */
            CommAppVo yunCardCommAppVo = getYunAccountCardInfo(yunId);
            if(yunCardCommAppVo.getStatusCode() != 1){
                retMsg[0] = yunCardCommAppVo.getMessage();
                return retMsg;
            }
            //循环获取银行卡号 , 封装 List 用于返回
            MapGetterTool mapCard = new MapGetterTool(yunCardCommAppVo.getHashMap());
            List<java.util.HashMap> cardList = mapCard.getList("data");
            if(cardList != null && cardList.size()>0){
                for(java.util.HashMap<String,Object> cardMap :cardList){
                    //检查是否有绑定当前银行卡
                    String listCardNo = cardMap.get("cardNo")+"";
                    if(cardNo.equals(listCardNo)){
                        retMsg[0] = "OK";
                        retMsg[1]= cardMap.get("cardType")+"";
                        return retMsg;
                    }
                }
            }else{
                retMsg[0] = "银行卡未绑定!";
                return retMsg;
            }

        }catch (Exception e ){
            e.printStackTrace();
        }
        return retMsg;
    }

    /**
     * 获取 云支付帐号银行卡信息
     * @param yuId
     * @return
     */
    private CommAppVo getYunAccountCardInfo(String yuId){
        CommAppVo commAppVo1 = new CommAppVo() ;
        try{
            /**
             * 调用 云支付接口 获取银行卡信息
             */
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("?terminal=").append("001");
            stringBuffer.append("&loginName=").append(yuId);
            stringBuffer.append("&startIndex=").append("1");
            stringBuffer.append("&endIndex=").append("1000");
            commAppVo1 = HttpPostClient.getCommData(CommConstant.yunGetCartUrl+stringBuffer.toString());
            if(commAppVo1.getStatusCode() == 1){
                commAppVo1 = AppDesUtil.desDecrypt(commAppVo1.getMessage(),CommConstant.yunGetCartDesKey);
                if(commAppVo1.getSuccess()){
                    return commAppVo1;
                }
            }else{
                switch(commAppVo1.getStatusCode()){
                    case 0:
                        commAppVo1.setMessage("获取帐户网络错误");
                        break;
                    case 2:
                        commAppVo1.setMessage("获取帐户网络错误");
                        break;
                }
            }

        }catch (Exception e ){
            e.printStackTrace();
        }
        return commAppVo1;
    }

    /**
     * 获取云支付账号信息
     * @param yunId
     * @return
     */
    private java.util.HashMap getYunAccountInfo(String yunId){
        try{
            /**
             * 调用 云支付接口 云支付账号信息
             */
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("?loginName=").append(yunId);
            String yunData = HttpPostClient.getData(CommConstant.yunGetUserDetail+stringBuffer.toString());
            CommAppVo commAppVo = AppDesUtil.desDecrypt(yunData,CommConstant.yunGetCartDesKey);
            if(commAppVo.getSuccess()){
                if("1".equals(commAppVo.getHashMap().get("is").toString())){
                    return commAppVo.getHashMap();
                }else{
                    return null;
                }
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取商户等级
     * @param yunId
     * @return
     */
    private String getYunAccountLev(String yunId){
        String retStr = "ERR";
        try{
            java.util.HashMap map = getYunAccountInfo(yunId);
            java.util.Map data = (java.util.Map)map.get("data");
            String lev  =data.get("lev")+"" ;
            String isMerchant  =data.get("isMerchant")+"" ;
            /**
             *  0	不存在商户资料
             *  1	存在商户资料
             */
            //检查是否为商户 0 存在商户资料
            if("1".equals(isMerchant)){
                retStr = "10";
            }else if ("2".equals(lev)){ //检查是否金钻会员 , 2金钻
                retStr = "11";
            }else{
                retStr = "ERR";
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return retStr;
    }
}
