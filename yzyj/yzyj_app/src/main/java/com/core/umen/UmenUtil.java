package com.core.umen;

import com.core.umen.android.AndroidBroadcast;
import com.core.umen.android.AndroidUnicast;
import com.core.umen.ios.IOSBroadcast;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Administrator on 2016/7/6.
 */
public class UmenUtil {
    private String appkey = null;
    private String appMasterSecret = null;
    private String timestamp = null;
    private PushClient client = new PushClient();
    public UmenUtil(String key, String secret) {
        try {
            appkey = key;
            appMasterSecret = secret;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    public void sendAndroidUnicast(String deviceToken,String title, String content)throws Exception {
        AndroidUnicast unicast = new AndroidUnicast(appkey,appMasterSecret);
        // TODO Set your device token
        unicast.setDeviceToken(deviceToken);
        unicast.setTicker("云智硬件");
        unicast.setTitle(title);
        unicast.setText(content);
        unicast.setCustomField(content);
        unicast.goAppAfterOpen();
        unicast.setDisplayType(AndroidNotification.DisplayType.MESSAGE);
        // TODO Set 'production_mode' to 'false' if it's a test device.
        // For how to register a test device, please see the developer doc.
        unicast.setProductionMode();
        // Set customized fields
        unicast.setExtraField("test", "helloworld");
        client.send(unicast);
    }
    public void sendAndroidBroadcast(String title, String content, Integer type, String parmenter, Date date,String name) throws Exception {
        AndroidBroadcast broadcast = new AndroidBroadcast(appkey,appMasterSecret);
        broadcast.setTicker( "云返服饰");
        broadcast.setTitle(title);
        broadcast.setText(content);
        broadcast.setDescription(title);
        JSONObject object=new JSONObject();
        object.put("type",type);
        object.put("parmenter",parmenter);
        object.put("date",date);
        object.put("name",name);
        broadcast.goCustomAfterOpen(object);
        broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        // TODO Set 'production_mode' to 'false' if it's a test device.
        // For how to register a test device, please see the developer doc.
        broadcast.setProductionMode(true);
        // Set customized fields
        broadcast.setExtraField("test", "helloworld");
        client.send(broadcast);
    }
    public void sendIOSBroadcast(String title,String content, Integer type, String parmenter, Date date,String name) throws Exception {
        IOSBroadcast broadcast = new IOSBroadcast(appkey,appMasterSecret);
        broadcast.setAlert(content);
        broadcast.setBadge( 0);
        broadcast.setSound( "default");
        broadcast.setDescription(title);
        JSONObject object=new JSONObject();
        object.put("type",type);
        object.put("parmenter",parmenter);
        object.put("date",date.getTime());
        object.put("name",name);
        object.put("title",title);
        object.put("content",content);
        broadcast.setCustomizedField("data",object.toString());
        // TODO set 'production_mode' to 'true' if your app is under production mode
//        broadcast.setTestMode();
        broadcast.setProductionMode();
        // Set customized fields
        client.send(broadcast);
    }
}
