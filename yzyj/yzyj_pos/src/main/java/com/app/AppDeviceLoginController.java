package com.app;

import com.admin.model.BaseExample;
import com.admin.model.DeviceInfo;
import com.admin.model.DeviceList;
import com.admin.service.DeviceInfoService;
import com.admin.service.DeviceListService;
import com.core.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;



/**
 * Created by Administrator on 2016/10/7.
 */
@Controller
@RequestMapping(value = "/app/device")
public class AppDeviceLoginController {

    @Resource
    private DeviceInfoService deviceInfoService;
    @Resource
    private DeviceListService deviceListService;

    /**
     * 绑定友盟token
     * token
     * yunId
     */
    @RequestMapping(value = "bindUmengToken", method = RequestMethod.POST)
    @ResponseBody
    public String bindUmengToken(HttpServletRequest request,String data) {
        try {
            /**
             * 获取 Map 对象
             */
            MapGetterTool hashMap = new MapGetterTool(AppFunUtil.getDataMap(data));
            if (hashMap == null) {
                return AppDesUtil.posAppDesEncrypt(null, "绑定友盟token失败", 0);
            }
            /**
             * 验证是否登入 , 并返回对象
             */
            DeviceInfo deviceInfo = AppFunUtil.checkToken(request, deviceInfoService, hashMap.getString("token"));
            if (deviceInfo.getState() != 1) {
                return AppDesUtil.posAppDesEncrypt(null, deviceInfo.getNote(), deviceInfo.getState());
            }
            DeviceInfo deviceInfo1 = new DeviceInfo();
            deviceInfo1.setUmengToken(hashMap.getString("umengToken"));
            deviceInfo1.setMachineCode(deviceInfo.getMachineCode());
            deviceInfoService.update(deviceInfo1);
            return AppDesUtil.posAppDesEncrypt(null,"成功",1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AppDesUtil.posAppDesEncrypt(null,"绑定友盟token失败",0);
    }

    /**
     * 用户获取信息
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public String login(HttpServletRequest request, String data) {
        Integer state = 0;
        System.out.println("====================用户开始执行登录设备======================");
        try{
            //获取数据 , 解密 , 转换对象
            DeviceInfo deviceInfo = AppDesUtil.posAppDesDecrypt(data,DeviceInfo.class);
            System.out.println("登录设备编码："+deviceInfo.getMachineCode());
            System.out.println("登录密码："+deviceInfo.getPwd());
            if(StringUtils.isEmpty(deviceInfo.getMachineCode())){
                return AppDesUtil.posAppDesEncrypt(null,"设备编码必填",2);
            }
            if(StringUtils.isEmpty(deviceInfo.getPwd())){
                return AppDesUtil.posAppDesEncrypt(null,"密码必填",3);
            }
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria =  baseExample.createCriteria();
            criteria.andEqualTo("t.machinecode",deviceInfo.getMachineCode());

            List<DeviceInfo> list = deviceInfoService.selectMachineInfo(baseExample);
            if(list.size()>0){
                DeviceInfo dbDeviceInfo = list.get(0);

                //检查设备是否已激活
                if(dbDeviceInfo.getState() == 0){
                    return AppDesUtil.posAppDesEncrypt(null,"设备未激活",5);
                }

                String pwdDevice = FunUtil.dealString(deviceInfo.getPwd());
                //验证设备 传入密码为空!
                if("".equals(pwdDevice)){
                    return AppDesUtil.posAppDesEncrypt(null,"密码为空",3);
                }
                String pwdDB = FunUtil.dealString(dbDeviceInfo.getPwd());
                if(!pwdDB.equals(pwdDevice)){
                    return AppDesUtil.posAppDesEncrypt(null,"密码错误",4);
                }
                DeviceInfo deviceInfo1 = new DeviceInfo();
                String token = UuidUtil.generateShortUuid()+dbDeviceInfo.getMachineCode();
                deviceInfo1.setMachineCode(deviceInfo.getMachineCode());
                deviceInfo1.setToken(token);

                deviceInfo1.setDeviceNo(deviceInfo.getDeviceNo());
                deviceInfo1.setIp(AppFunUtil.getIpAddr(request));
                deviceInfo1.setLastTime(new Date());
                deviceInfo1.setMerchantYunId(dbDeviceInfo.getMerchantYunId());
                java.util.HashMap map = new java.util.HashMap();

                map.put("token",token);
                map.put("expiresIn","2592000");//30天有效期
                map.put("merchantYunId",deviceInfo1.getMerchantYunId());
                map.put("versionInfo",getVersion());
                deviceInfoService.update(deviceInfo1);
                System.out.println("=======================用户执行设备登录完成==========================");
                return AppDesUtil.posAppDesEncrypt(map,"设备登录成功",1);
            }else{
                return AppDesUtil.posAppDesEncrypt(null,"设备编码,激活码不存在",6);
            }

        }catch (Exception e ){
            e.printStackTrace();
        }
        return AppDesUtil.posAppDesEncrypt(null,"设备登录失败",0);
    }

    /**
     * 用户获取信息激活设备
     * machineCode
     * activationCode
     */
    @RequestMapping(value = "activation", method = RequestMethod.POST)
    @ResponseBody
    public String activation(HttpServletRequest request, String data) {
        Integer state = 0;
        System.out.println("=======================用户开始执行激活设备==========================");
        try{
            //获取数据 , 解密 , 转换对象
            DeviceInfo deviceInfo = AppDesUtil.posAppDesDecrypt(data,DeviceInfo.class);
            if(deviceInfo == null){
                return AppDesUtil.posAppDesEncrypt(null,"参数获取设备信息失败",0);
            }
            if(StringUtils.isEmpty(deviceInfo.getMachineCode())){
                return AppDesUtil.posAppDesEncrypt(null,"填写设备码",3);
            }
            if(StringUtils.isEmpty(deviceInfo.getActivationCode())){
                return AppDesUtil.posAppDesEncrypt(null,"填写激活码",4);
            }
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria =  baseExample.createCriteria();
            criteria.andEqualTo("t.machinecode",deviceInfo.getMachineCode());
            criteria.andEqualTo("t.activation_code",deviceInfo.getActivationCode());
            List<DeviceInfo> list = deviceInfoService.selectMachineInfo(baseExample);
            if(list.size() > 0){
                DeviceInfo dbDI = list.get(0);
                if(dbDI.getState() == 1){
                    return AppDesUtil.posAppDesEncrypt(null,"设备码已被使用",3);
                }
                BaseExample baseExample1 = new BaseExample();
                BaseExample.Criteria criteria1 =  baseExample1.createCriteria();
                criteria1.andEqualTo("DEVICE_CODE",deviceInfo.getMachineCode());
                criteria1.andEqualTo("ACTIVATION_CODE",deviceInfo.getActivationCode());
                criteria1.andEqualTo("APPROVAL_STATUS","3");
                List<DeviceList> list1 = deviceListService.selectByExample(baseExample1);
                if(list1.size() == 0){
                    return AppDesUtil.posAppDesEncrypt(null,"设备还在审批中,请联系客服",3);
                }

                String token = UuidUtil.generateShortUuid()+deviceInfo.getMachineCode();
                DeviceInfo deviceInfo1= new DeviceInfo();
                deviceInfo1.setMachineCode(deviceInfo.getMachineCode());
                deviceInfo1.setToken(token);

                deviceInfo1.setIp(AppFunUtil.getIpAddr(request));
                deviceInfo1.setLastTime(new Date());
                deviceInfo1.setPwd(MD5Util.encodeByMD5(token));
                deviceInfo1.setState(1);//标志已激活
                deviceInfo1.setMerchantYunId(dbDI.getMerchantYunId());
                java.util.HashMap map = new java.util.HashMap();

                map.put("token",token);
                map.put("expiresIn","2592000");//30天有效期
                map.put("pwd", deviceInfo1.getPwd());
                map.put("merchantYunId",deviceInfo1.getMerchantYunId());
                deviceInfoService.update(deviceInfo1);
                deviceListService.updateState(deviceInfo.getMachineCode());
                System.out.println("=======================用户执行激活设备完成==========================");
                return AppDesUtil.posAppDesEncrypt(map,"设备激活成功",1);
            }else{
                return AppDesUtil.posAppDesEncrypt(null,"设备编码或激活码有误！",2);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return AppDesUtil.posAppDesEncrypt(null,"用户获取信息激活设备失败",0);
    }

    /**
     * 生成版本号
     * @return
     */
    private java.util.HashMap getVersion(){
        java.util.HashMap map = new java.util.HashMap();
        try{
            /**
             * updateState
             * 强更版本号 , 检查小于等于就进行强更
             * downLink 下载连接
             */
            map.put("version",FunUtil.stringToInteger(CommDictList.getDictVal("app_pos","version")));
            map.put("updateState",FunUtil.stringToInteger(CommDictList.getDictVal("app_pos","updateState")));
            map.put("downState",FunUtil.stringToInteger(CommDictList.getDictVal("app_pos","downState")));
            map.put("downLink",CommDictList.getDictVal("app_pos","downLink"));

        }catch (Exception e ){
            e.printStackTrace();
        }
        return map;
    }
}
