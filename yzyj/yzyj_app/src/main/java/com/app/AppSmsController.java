package com.app;

import com.admin.service.AgencyPersonService;
import com.core.util.AppDesUtil;
import com.core.util.AppFunUtil;
import com.core.util.MapGetterTool;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/12/17.
 */
@Controller
@RequestMapping(value = "/app/sms")
public class AppSmsController {
    @Resource
    private AgencyPersonService agencyPersonService;

    /**
     * 发送验证码
     * @param request
     * @param data
     * @return
     */
    @RequestMapping(value = "forgetPassSms" ,method = RequestMethod.POST)
    @ResponseBody
    private String forgetPassSms(HttpServletRequest request, String data){
        /**
         * 获取参数值
         */
        MapGetterTool hashMap = new MapGetterTool(AppFunUtil.getDataMap(data));
        if(hashMap == null){
            return AppDesUtil.posAppDesEncrypt(null,"参数失败",0);
        }

        String mobile = hashMap.getString("mobile");
        if(mobile == null){
            return AppDesUtil.posAppDesEncrypt(null,"请输入手机号码",0);
        }
        String result = agencyPersonService.forgetPassSms(request,mobile);
        String[] resultArr = result.split(",");
        if(resultArr[0].equals("1")){
            return AppDesUtil.posAppDesEncrypt(null,resultArr[1],1);
        }else{
            return AppDesUtil.posAppDesEncrypt(null,resultArr[1],0);
        }
    }
}

