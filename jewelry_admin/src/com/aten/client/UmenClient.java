package com.aten.client;

import com.communal.constants.Constant;
import com.communal.thirdservice.push.AndroidNotification;
import com.communal.thirdservice.push.AndroidUnicast;
import com.communal.thirdservice.push.IOSUnicast;
import com.communal.thirdservice.push.PushClient;
import com.communal.thirdservice.push.PushUtil;


public class UmenClient {
	
	/**
	 * @author linjunqin
	 * @Description 安卓发送
	 * @param
	 * @date 2017-5-23 下午3:28:47
	 */
	public static void androidSend(String deviceToken,String title,String content,String text){
		try {
			AndroidUnicast unicast = new AndroidUnicast(Constant.androidPushKey,Constant.androidAppMarst);
			unicast.setDeviceToken(deviceToken); //DeviceToken
			unicast.setTicker(title); //通知栏提示文字
			unicast.setTitle(title); //通知标题
			unicast.setText(text); //通知文字描述  
			unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);//显示类型
			/*if(type == 1){ //点击消息跳转账单界面
				unicast.goActivityAfterOpen("com.aiten.yunticketing.ui.user.activity.BillDetailActivity");
			} else if(type == 2){ //点击消息跳转消息界面
				unicast.goActivityAfterOpen("com.aiten.yunticketing.ui.home.activity.MessageActivity");
			} else if(type == 3){ //验证用户在另一台设备登录
				unicast.setDisplayType(AndroidNotification.DisplayType.MESSAGE);
				unicast.goCustomAfterOpen("error_login");
			}*/
			unicast.setProductionMode(true);
			PushClient client = new PushClient();
			client.send(unicast);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	/**
	 * @author linjunqin
	 * @Description IOS发送
	 * @param
	 * @date 2017-5-23 下午3:29:32
	 */
	public static void iosSend(String deviceToken,String title,String content,String text,String order_id){
		IOSUnicast unicast;
		try {
			unicast = new IOSUnicast(Constant.iodPushKey,Constant.iosAppMarst);
			unicast.setDeviceToken(deviceToken);
			unicast.setAlert(title);
			unicast.setBadge(0);
			unicast.setSound("default");
			unicast.setTestMode();
			unicast.setCustomizedField("sortId","6");//酒店模块
			unicast.setCustomizedField("orderId",order_id);
			unicast.setCustomizedField("type","1");//手动传入
			unicast.setCustomizedField("act","1");
			PushClient client = new PushClient();
			client.send(unicast);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		androidSend("11","1","2","3");
		//iosSend("1","2","3");
	}
	
}
