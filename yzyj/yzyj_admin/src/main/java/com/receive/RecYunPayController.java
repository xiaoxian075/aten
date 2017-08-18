package com.receive;

import com.admin.service.OrderService;
import com.core.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wjf on 2016/10/8.
 */
@Controller
@RequestMapping(value = "/receive/yunPay")
public class RecYunPayController {
    @Resource
    private OrderService orderService;

    /**
     * 代理提现 云支付 确认
     */
    @RequestMapping(value = "agentExtractConfirm")
    public void agentExtractConfirm(HttpServletRequest request,HttpServletResponse response) {
        try{
            java.util.HashMap map = getClientPostMap(request);
            if(map == null){
                returnDataClient(response,null,"失败",0);
                return ;
            }

            MapGetterTool mapGetterTool = new MapGetterTool(map);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(mapGetterTool.getString("loginName")).append("&");
            stringBuffer.append(mapGetterTool.getInteger("amount")).append("&");
            stringBuffer.append(mapGetterTool.getString("orderId")).append("&").append(CommConstant.YZ_SINGDES);
            String sign = MD5Util.encodeByMD5Base64(stringBuffer.toString());
            if(sign.equals(mapGetterTool.getString("sign"))){
                /**
                 * 验证 是否存在该提现记录
                 * 验证代理提现帐号是否正确
                 * 验证金额是否正确
                 */
                String mSign = CommRedisFun.getKey(sign);
                if("2".equals(mSign)){
                    returnDataClient(response,null,"成功",1);
                    return ;
                }else{
                    returnDataClient(response,null,"失败",0);
                }
            }else{
                returnDataClient(response,null,"验签不通过",0);
                return ;
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    public static java.util.HashMap getClientPostMap(HttpServletRequest request) {
        try{
            return AppFunUtil.getClientPostMap(request, CommConstant.YZ_DATADES);
        }catch (Exception e ){

        }
        return null;
    }

    public static void returnDataClient(HttpServletResponse response,Object obj,String message,Integer statusCode) {
        try{
            AppFunUtil.returnDataClient(response,obj,message,statusCode, CommConstant.YZ_DATADES);
        }catch (Exception e ){

        }
    }

}
