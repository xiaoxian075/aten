package com.core.yunPay;

import com.admin.vo.CommAppVo;
import com.core.util.AppDesUtil;
import com.core.util.CommConstant;
import com.core.util.HttpPostClient;
import com.core.util.MapGetterTool;

/**
 * Created by wjf on 2016/11/8.
 */
public class YunPayFun {
    public static MapGetterTool getYunAccountInfo(String yunId){
        java.util.HashMap map = new java.util.HashMap();
        try{
            /**
             * 调用 云支付接口 获取银行卡信息
             */
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("?loginName=").append(yunId);
            String yunData = HttpPostClient.getData(CommConstant.yunGetUserDetail+stringBuffer.toString());
            CommAppVo commAppVo = AppDesUtil.desDecrypt(yunData,CommConstant.yunGetCartDesKey);
            if(commAppVo.getSuccess()){
                return new MapGetterTool(commAppVo.getHashMap());
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return new MapGetterTool(map);
    }
}
