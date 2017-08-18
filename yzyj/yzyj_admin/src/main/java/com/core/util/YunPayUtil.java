package com.core.util;

import com.admin.model.YunIdInfo;
import com.admin.vo.CommAppVo;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 2016/11/18.
 */
public class YunPayUtil {

    public static YunIdInfo getYunAccountInfo(String yunId){
        try{
            /**
             * 调用 云支付接口 获取银行卡信息
             */
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("?loginName=").append(yunId);
            String yunData = HttpPostClient.getData(CommConstant.yunGetUserDetail+stringBuffer.toString());
            CommAppVo commAppVo = AppDesUtil.desDecrypt(yunData,CommConstant.yunGetCartDesKey);
            YunIdInfo yunIdInfo = new YunIdInfo();
            if(commAppVo.getSuccess()){
                if(Integer.parseInt(commAppVo.getHashMap().get("is").toString()) == 1) {
                    JSONObject jsonObject = (JSONObject) commAppVo.getHashMap().get("data");
                    yunIdInfo.setLoginName(jsonObject.get("loginName").toString());
                    yunIdInfo.setMobile(jsonObject.get("mobile").toString());
                    yunIdInfo.setIsMerchant(jsonObject.get("isMerchant").toString());
                    yunIdInfo.setLev(jsonObject.get("lev").toString());
                    yunIdInfo.setUserName(jsonObject.get("userName").toString());
                    return yunIdInfo;
                }else{
                    return null;
                }
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String [] ar){
        try{
            getYunAccountInfo("668888");
        }catch (Exception e ){

        }
    }
    //设置商户pos机标识
    public static boolean setMerchantIdentify(String yunId,int type){
        try{
            String yunData = "";
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("?loginName=").append(yunId);
            stringBuffer.append("&regWay=").append(CommConstant.regWay);
            if(type == 1){
                yunData = HttpPostClient.getData(CommConstant.yunMerchantIdentifyUrl+stringBuffer.toString());
            }else{
                yunData = HttpPostClient.getData(CommConstant.cancelYunMerchantIdentifyUrl+stringBuffer.toString());
            }
            CommAppVo commAppVo = AppDesUtil.desDecrypt(yunData,CommConstant.yunGetCartDesKey);
            if(commAppVo.getSuccess()){
                if(Integer.parseInt(commAppVo.getHashMap().get("is").toString()) == 1){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
