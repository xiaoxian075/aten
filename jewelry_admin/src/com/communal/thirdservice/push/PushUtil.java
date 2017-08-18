package com.communal.thirdservice.push;
import java.util.Date;
import java.util.HashMap;
import com.communal.util.JsonUtil;

public class PushUtil {
	private String appkey = null;
	private String appMasterSecret = null;
	private PushClient client = new PushClient();

	public PushUtil(String key, String secret) {
		try {
			appkey = key;
			appMasterSecret = secret;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

/*	public void sendAndroidUnicast(String title, String content, Integer type,
			String parmenter, Date date, String name,String tokenDevice)
			throws Exception {
		AndroidUnicast unicast = new AndroidUnicast(appkey, appMasterSecret);
		unicast.setDeviceToken(tokenDevice);
		unicast.setTicker(title);
		unicast.setTitle(title);
		unicast.setText(content);
		JSONObject object = new JSONObject();
		object.put("type", type);
		object.put("parmenter", parmenter);
		object.put("date", date);
		object.put("name", name);
		unicast.goCustomAfterOpen(object);
		unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		unicast.setProductionMode(true);
		unicast.setExtraField("data", object.toString());
		client.send(unicast);
	}

	public void sendIOSUnicast(String title, String content, Integer type,
			String parmenter, Date date, String name,String tokenDevice) throws Exception {
		IOSUnicast unicast = new IOSUnicast(appkey, appMasterSecret);
		unicast.setDeviceToken(tokenDevice);
		unicast.setAlert(content);
		unicast.setBadge(0);
		unicast.setSound("default");
        JSONObject object=new JSONObject();
        object.put("type",type);
        object.put("parmenter",parmenter);
        object.put("date",date.getTime());
        object.put("name",name);
        object.put("title",title);
        object.put("content",content);
		unicast.setCustomizedField("data", object.toString());
		unicast.setProductionMode(true);
		client.send(unicast);
	}*/

	
    public void sendAndroidBroadcast(String title, String content, String img_path, String msg_url,Date date) throws Exception {
        AndroidBroadcast broadcast = new AndroidBroadcast(appkey,appMasterSecret);
        broadcast.setTicker(content);
        broadcast.setTitle(title);
        broadcast.setText(content);
        broadcast.setDescription(title);
        HashMap<String, String> hMap = new HashMap<String, String>();
        hMap.put("msg_url", msg_url);
        hMap.put("img_path", img_path);
        hMap.put("title", title);
        String jsonStr = JsonUtil.map2json(hMap);
        broadcast.goCustomAfterOpen(jsonStr);
        broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        broadcast.setProductionMode(true);
        client.send(broadcast);
    }
    
    public void sendIOSBroadcast(String title,String content,String img_path, String msg_url,Date date) throws Exception {
        IOSBroadcast broadcast = new IOSBroadcast(appkey,appMasterSecret);
        broadcast.setAlert(content);
        broadcast.setBadge(0);
        broadcast.setSound("default");
        broadcast.setDescription(title);
        broadcast.setCustomizedField("msg_url",msg_url);
        broadcast.setCustomizedField("img_path",img_path);
        broadcast.setCustomizedField("title",title);
        broadcast.setProductionMode(true);//false测试模式   true 生产模式
        client.send(broadcast);
    }
}
