package com.communal.thirdservice.push;

public class AndroidBroadcast extends AndroidNotification {
	public AndroidBroadcast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			setPredefinedKeyValue("type", "broadcast");	//  unicast
			//setPredefinedKeyValue("device_tokens","Ak2h3efMgqy8aL2qPBv1VgANLSTpxW1Wha6S1jTd40vD");//测试单个设备
	}
}
