package com.receive;

import com.admin.service.PayInfoService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.core.util.Log;
import com.lakala.sign.LKLApiException;
import com.lakala.sign.LKLSignature;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2016/10/8.
 */
@Controller
@RequestMapping(value = "/receive/lkl")
public class ReceiveLklController {
    private final static String publickeyFile = "rsa_public_key.pem";
    private final static String charset = "UTF-8";
    @Resource
    private PayInfoService payInfoService;
    /**
     * 用户获取   信息
     */
    @RequestMapping(value = "receiveMsg", method = RequestMethod.POST)
    @ResponseBody
    public String receiveMsg(HttpServletRequest request, String sign, String data) {
        java.util.HashMap <String,String> map = new java.util.HashMap();
        String json = "";
        try {
            /**
             * 验签接口 @param data 接收到的字符
             * sign 加密后的数字证书
             */
            map.put("return_code", "ERROR");
            map.put("return_msg", "失败");
            if(StringUtils.isEmpty(data)){
                return JSONObject.toJSONString(map);
            }
            java.util.Map<String, String> dataMap = toLinkedHashMap(data);
            String path = request.getServletContext().getRealPath("WEB-INF/" + publickeyFile); /* 获取到的公匙*/
            String publicKey = getPublicKey(path); /* 把数字证书放到map里准备验 */
            dataMap.put("sign", sign); /* 验签返回结果true或 false*/
            boolean unsignReslut = false;
            try {
                unsignReslut = LKLSignature.rsaCheckV1(dataMap, publicKey, charset);
            } catch (LKLApiException e) { /* TODO Auto-generated catch block*/
                Log.out("拉卡拉验证:"+e.getErrCode(),e.getErrMsg());
            }
            /**
             * 签名验证成功
             */
            if (unsignReslut) { /*客户业务*/
                map = payInfoService.updatePayInfo(dataMap);
            } else {
                //验签失败
                map.put("return_code", "FAIL");
                map.put("return_msg", "验签失败");
            }
            json =  JSONObject.toJSONString(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("===========================结束拉卡拉的确认刷卡信息==========================");
        return json;
    }


    /**
     * 读取公钥
     */
    private static String getPublicKey(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String publickey = br.readLine();
            System.out.println(publickey);
            return publickey;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * json字符串数据转化成Map
     *
     * @return json对应的map
     **/
    public static HashMap<String, String> toLinkedHashMap(String object) {
        HashMap<String, String> data = new LinkedHashMap<String, String>();
        // 将json字符串转换成jsonObject
        JSONObject jsonObject = JSON.parseObject(object);
        Iterator it = jsonObject.keySet().iterator();
        // 遍历jsonObject数据，添加到Map对象
        while (it.hasNext()) {
            String key = String.valueOf(it.next());
            String value = (String) jsonObject.get(key);
            data.put(key, value);
        }
        return data;
    }


}
