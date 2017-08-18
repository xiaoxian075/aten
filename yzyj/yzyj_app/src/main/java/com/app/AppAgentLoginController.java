package com.app;

import com.admin.model.*;
import com.admin.service.*;
import com.core.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by wjf on 2016/11/7.
 */

@Controller
@RequestMapping(value = "/app/agent")
public class AppAgentLoginController {
    @Resource
    private UserService userService;
    @Resource
    private CarouselService carouselService;
    @Resource
    private DeviceListService deviceListService;
    @Resource
    private OrderService orderService;

    /**
     * 用户获取  信息
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public String login(HttpServletRequest request, String data) {
        Integer state = 0;
        try{
            //获取数据 , 解密 , 转换对象
            User retUser = AppDesUtil.posAppDesDecrypt(data,User.class);
            if(StringUtils.isEmpty(retUser.getUsername())){
                return AppDesUtil.posAppDesEncrypt(null,"登录名必填",2);
            }
            if(StringUtils.isEmpty(retUser.getPassword())){
                return AppDesUtil.posAppDesEncrypt(null,"密码必填",3);
            }

            User dbUser = userService.selectAgentByUserName(retUser.getUsername());
            if(dbUser != null){
                //检查设备是否已激活
                if("0".equals(dbUser.getStatus())){
                    return AppDesUtil.posAppDesEncrypt(null,"帐户异常",5);
                }

                String pwdDevice = FunUtil.dealString(retUser.getPassword());
                //验证设备 传入密码为空!
                if("".equals(pwdDevice)){
                    return AppDesUtil.posAppDesEncrypt(null,"密码为空",3);
                }
                String pwdDB =FunUtil.dealString(dbUser.getPassword());
                if(!pwdDB.equals(pwdDevice)){
                    return AppDesUtil.posAppDesEncrypt(null,"密码错误",4);
                }
                User user2 = new User();
                String token = UuidUtil.generateShortUuid()+dbUser.getId();
                user2.setId(dbUser.getId());
                user2.setToken(token);
                user2.setDeviceNo(retUser.getDeviceNo());
                user2.setIp(AppFunUtil.getIpAddr(request));
                user2.setLastTime(new Date());
                user2.setLon(retUser.getLon());
                user2.setLat(retUser.getLat());
                java.util.HashMap map = new java.util.HashMap();
                map.put("token",token);
                map.put("realName",dbUser.getRealname());
                map.put("expiresIn","7200");
                //map.put("versionInfo",getVersion());
                userService.update(user2);
                return AppDesUtil.posAppDesEncrypt(map,"成功",1);
            }else{
                return AppDesUtil.posAppDesEncrypt(null,"当前账号不是云智硬件代理商!",6);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return AppDesUtil.posAppDesEncrypt(null,"失败",0);
    }

    /**
     * 获取轮播信息
     * @param request
     * @param data   type代理类型   property代理人还是商家
     * @return
     */
    @RequestMapping(value = "getInfoByType", method = RequestMethod.POST)
    @ResponseBody
    private String getInfoByType(HttpServletRequest request,String data){
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
        Integer type = hashMap.getInteger("type");
        java.util.HashMap map = new java.util.HashMap();
        List<Carousel> list  = carouselService.getInfoList(type);

        for(int i=0;i<list.size();i++){
            list.get(i).setImgUrl(CommConstant.baseUrl+list.get(i).getImgUrl());
        }

        map.put("infoList",list);
        return AppDesUtil.posAppDesEncrypt(map,"成功",1);
    }

    /**
     * 商家信息统计
     * @param request
     * @param data
     * @return
     */
    @RequestMapping(value = "getMerchantInfo",method = RequestMethod.POST)
    @ResponseBody
    private String getMerchantInfo(HttpServletRequest request,String data){
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

        java.util.HashMap map = new HashMap();
        map.put("agentUnique",user.getAgentUnique());//代理人下的商户信息
        map.put("pageStart",hashMap.getInteger("pageStart"));
        map.put("pageEnd",hashMap.getInteger("pageEnd"));

        //urldecode解码
        String account = hashMap.getString("account");
        try {
			account = URLDecoder.decode(account, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

        if(!StringUtils.isEmpty(account)){
            map.put("account",account);
        }

        List<DeviceList> list = deviceListService.getRepeatMerchant(map);
        java.util.HashMap map1 = new HashMap();
        List<MerchantInfoModel> miList = new ArrayList<MerchantInfoModel>();
        if(list.size() != 0 ) {
            map.put("type", 1);//当天
            List<Order> listDay = orderService.getMerchantMoneyCount(map);
            map.put("type", 2);//当月
            List<Order> listMonth = orderService.getMerchantMoneyCount(map);
            map.put("type", 3);//累计
            List<Order> listHistory = orderService.getMerchantMoneyCount(map);

            //当天
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < listDay.size(); j++) {
                    if (list.get(i).getMerchantYunPayAccount().equals(listDay.get(j).getMerchantYunId())) {
                        list.get(i).setmDayCount(listDay.get(j).getTotalFee());
                    }
                }
            }
            //当月
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < listMonth.size(); j++) {
                    if (list.get(i).getMerchantYunPayAccount().equals(listMonth.get(j).getMerchantYunId())) {
                        list.get(i).setmMonthCount(listMonth.get(j).getTotalFee());
                    }
                }
            }
            //历史累计
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < listHistory.size(); j++) {
                    if (list.get(i).getMerchantYunPayAccount().equals(listHistory.get(j).getMerchantYunId())) {
                        list.get(i).setmHistoryCount(listHistory.get(j).getTotalFee());
                    }
                }
            }

            //组装新的model
            if (!StringUtils.isEmpty(list)) {
                for (Iterator<DeviceList> iterator = list.iterator(); iterator.hasNext(); ) {
                    DeviceList deviceList = (DeviceList) iterator.next();
                    MerchantInfoModel merchantInfoModel = ClassInstanceUtil.createMerchantInfoModel(deviceList);
                    miList.add(merchantInfoModel);
                }
            }
        }
        map1.put("miList", miList);
        return AppDesUtil.posAppDesEncrypt(map1, "成功", 1);
    }

    /**
     * 生成版本号
     * @return
     */
    @RequestMapping(value = "getVersionInfo",method = RequestMethod.POST)
    @ResponseBody
    private String getVersionInfo(HttpServletRequest request,String data){

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
        /*User user = AppFunUtil.checkToken(request,userService,hashMap.getString("token"));
        if(user.getState() != 1 ){
            return AppDesUtil.posAppDesEncrypt(null,user.getNote(),user.getState());
        }*/
        java.util.HashMap map = new java.util.HashMap();
        try{
            /**
             * updateState
             * 强更版本号 , 检查小于等于就进行强更
             * downLink 下载连接
             */
            map.put("version",CommDictList.getDictVal("hardware_app","version"));
            map.put("aversion",FunUtil.stringToInteger(CommDictList.getDictVal("hardware_app","aversion")));
            map.put("downLink",CommDictList.getDictVal("hardware_app","downLink"));
            map.put("isPass",FunUtil.stringToInteger(CommDictList.getDictVal("hardware_app","isPass")));
            map.put("updateMsg",CommDictList.getDictVal("hardware_app","updateMsg"));
            return AppDesUtil.posAppDesEncrypt(map,"成功",1);
        }catch (Exception e ){
            e.printStackTrace();
        }
        return AppDesUtil.posAppDesEncrypt(null,"获取更新失败",0);
    }
}
