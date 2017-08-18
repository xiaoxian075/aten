package com.aten.client;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.*;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.alibaba.fastjson.JSONObject;
import com.aten.function.SpringContextFuc;
import com.aten.model.bean.JPushBean;
import com.aten.model.orm.Sysmsg;
import com.aten.service.SysmsgService;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static cn.jpush.api.push.model.notification.PlatformNotification.ALERT;

/**
 * Created by 陈熠
 * 2017/7/21.
 */

public class JiguangClient extends SpringContextFuc{
    private static SysmsgService sysmsgService = (SysmsgService) getContext().getBean("sysmsgService");
    private static final Logger LOG = Logger.getLogger(JiguangClient.class);
    private static final String masterSecret = "b851ff5b2054af57c84f9263";
    private static final String appKey = "189c49597dffefea4feac553";

    public static void main(String args[]) {
        JPushBean msg = new JPushBean();
       // msg.setAlias("0c000615864745d683d24ed89c908baf");
        msg.setAlias("ios4f0ea412330149438011c66084d248d5");
        msg.setType("3");
        msg.setTitle("title_标题测试");
        msg.setContent("content_内容内容内容内容内容内容内容内容");
        msg.setId("960");
        msg.setUserId("70");

        send("ios", msg);
        //send("android", msg);
    }
    
    public static boolean send(JPushBean msg) {
    	String alias = msg.getAlias();
    	if (alias==null || alias.length()<3) {
    		return false;
    	}
    	String device = alias.substring(0,3);
    	String newAlias = alias;// alias.substring(3);
    	msg.setAlias(newAlias);
    	if (device.equals("ios")) {
    		send("ios",msg);
    	} else if (device.equals("ard")) {
    		send("ard",msg);
    	}
    	return true;
    }
    

    public static void send(String pushPlatform, JPushBean msg) {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
        PushPayload payload = "ios".equals(pushPlatform) ? builderIosPayLoad(msg) : builderAndroidPayLoad(msg);
        //PushPayload payload = buildPushObject_all_all_alert ();
        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error, should retry later", e);
        } catch (APIRequestException e) {
            LOG.error("Should review the error, and fix the request", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
        //插入数据库
        Sysmsg sysmsg = new Sysmsg();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sysmsg.setIn_date(sdf.format(new Date()));
        sysmsg.setAccount_id(msg.getUserId());
        sysmsg.setIs_read("0");
        sysmsg.setRelation_id(msg.getId());
        sysmsg.setSkip_type(msg.getType());
        sysmsg.setSysmsg_content(msg.getContent());
        sysmsg.setSysmsg_title(msg.getTitle());
        sysmsgService.insert(sysmsg);
    }

    //自定义ios
    private static PushPayload builderIosPayLoad(JPushBean msg) {

        JSONObject obj = new JSONObject();
        obj.put("title", msg.getTitle());
        obj.put("type", msg.getType());
        obj.put("content", msg.getContent());
        obj.put("id", msg.getId());

        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.alias(msg.getAlias()))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(msg.getContent())
                                .setBadge(1)
                                .setSound("happy")
                                .addExtra("data", obj.toJSONString())
                                .build())
                        .build())
                .setMessage(Message.content(obj.toJSONString()))
                .setOptions(Options.newBuilder()
                        .setApnsProduction(false)
                        .build())
                .build();
    }

    //自定义Android
    private static PushPayload builderAndroidPayLoad(JPushBean msg) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("title", msg.getTitle());
        map.put("content", msg.getContent());
        map.put("type", msg.getType());
        map.put("id", msg.getId());
//        JSONObject obj = new JSONObject();
//        obj.put("title", msg.getTitle());
//        obj.put("type", msg.getType());
//        obj.put("content", msg.getContent());
//        obj.put("id", msg.getId());
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.alias(msg.getAlias()))
                .setNotification(Notification.android(msg.getContent(), msg.getTitle(), map))
                .build();
    }


    public static PushPayload buildPushObject_all_all_alert() {
        return PushPayload.alertAll(ALERT);
    }

    public static PushPayload buildPushObject_android_tag_alertWithTitle() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.alias("14"))
                .setNotification(Notification.android("内容简介安徽省都爱家的厚爱京东爱激动跟氨基酸都跟安静的圣地加速度", "通知测试(标题)", null))
                .build();
    }

    public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage(JPushBean msg) {

        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.alias("14"))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(msg.getTitle())
                                .setBadge(5)
                                .setSound("happy")
                                .addExtra("from", "JPush")
                                .build())
                        .build())
                .setMessage(Message.content("内容内容"))
                .setOptions(Options.newBuilder()
                        .setApnsProduction(false)
                        .build())
                .build();
    }
}
