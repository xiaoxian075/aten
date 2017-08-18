package com.app;

import com.admin.model.AgencyPerson;
import com.admin.model.User;
import com.admin.service.AgencyPersonService;
import com.admin.service.UserService;
import com.alibaba.druid.util.StringUtils;
import com.core.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;

/**
 * Created by wjf on 2016/11/3.
 */

@Controller
@RequestMapping(value = "/app/yunHardware")
public class AppYunHardwareController {
    @Resource
    private UserService userService;
    @Resource
    private AgencyPersonService agencyPersonService;

    /**
     * 获取代理人相关信息
     * @param request
     * @param data
     * @return
     */
    @RequestMapping(value = "getAgentInfo",method = RequestMethod.POST)
    @ResponseBody
    public String getAgencyInfo(HttpServletRequest request,String data){
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
        User user = AppFunUtil.checkToken(request, userService, hashMap.getString("token"));
        if (user.getState() != 1) {
            return AppDesUtil.posAppDesEncrypt(null, user.getNote(), user.getState());
        }

        AgencyPerson agencyPerson = agencyPersonService.getAgencyInfo(user.getAgentUnique());
        if(org.springframework.util.StringUtils.isEmpty(agencyPerson)){
            return AppDesUtil.posAppDesEncrypt(null,"没有找到代理人相关信息",0);
        }

        agencyPerson.setDay(new SimpleDateFormat("yyyy-MM-dd").format(agencyPerson.getJoinTime()));
        agencyPerson.setHeadPicName(agencyPerson.getHeadUrl());
        agencyPerson.setHeadUrl(CommConstant.headUrl+agencyPerson.getHeadUrl());

        return AppDesUtil.posAppDesEncrypt(agencyPerson,"获取成功",1);
    }

    /**
     * 修改密码
     * @param request
     * @param data
     * @return
     */
    @RequestMapping(value = "forgetPass",method = RequestMethod.POST)
    @ResponseBody
    private String forgetPass(HttpServletRequest request,String data){
        /**
         * 获取 Map 对象
         */
        MapGetterTool hashMap = new MapGetterTool(AppFunUtil.getDataMap(data));
        if(hashMap == null){
            return AppDesUtil.posAppDesEncrypt(null,"失败",0);
        }

        String code = hashMap.getString("code");
        String mobile = hashMap.getString("mobile");
        String pass = hashMap.getString("pass");
        String confirmPass = hashMap.getString("confirmPass");

        AgencyPerson agencyPerson = agencyPersonService.getAgencyPersonByInfo(mobile);
        if(org.springframework.util.StringUtils.isEmpty(agencyPerson)){
            return AppDesUtil.posAppDesEncrypt(null,"没找到该手机号码的代理人信息",0);
        }

        if (!AppFunUtil.checkSmscodeAvailable(request, code,"forgetSmsCode", mobile)) {
            return AppDesUtil.posAppDesEncrypt(null,"验证码出错",0);
        }

        if (!confirmPass.matches("^(?![^a-zA-Z]+$)(?!\\\\D+$).{6,16}$")) {
            return AppDesUtil.posAppDesEncrypt(null,"密码要字母数字结合",0);
        }

        if(!pass.equals(confirmPass)){
            return AppDesUtil.posAppDesEncrypt(null,"密码跟确认密码不一样",0);
        }

        confirmPass  = EncryptUtil.generatePassword(confirmPass);
        Integer state = agencyPersonService.updatePass(agencyPerson.getAgentUnique(),confirmPass);
        if(state == 1){
            request.getSession().removeAttribute("forgetSmsCode");
            return AppDesUtil.posAppDesEncrypt(null,"修改成功，重新登录",1);
        }else{
            return AppDesUtil.posAppDesEncrypt(null,"修改密码失败",0);
        }
    }

    /**
     * 修改用户头像
     * @param request
     * @param data
     * @return
     */
    @RequestMapping(value = "updateHeadPic",method = RequestMethod.POST)
    @ResponseBody
    public String updateHeadPic(HttpServletRequest request,String data){
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
        User user = AppFunUtil.checkToken(request, userService, hashMap.getString("token"));
        if (user.getState() != 1) {
            return AppDesUtil.posAppDesEncrypt(null, user.getNote(), user.getState());
        }

        String headPic = hashMap.getString("headPic");
        String headPicName = user.getAgentUnique();
        try {
            String picPath = agencyPersonService.updatePic(request,headPic,user.getAgentUnique(),headPicName);
            if(StringUtils.isEmpty(picPath)){
                return AppDesUtil.posAppDesEncrypt(null,"修改头像失败",0);
            }else{
                return AppDesUtil.posAppDesEncrypt(null,"修改头像成功",1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return AppDesUtil.posAppDesEncrypt(null,"修改头像失败",0);
    }

}
