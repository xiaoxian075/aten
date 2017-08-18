package com.communal.thirdservice.push;

public class IOSBroadcast extends IOSNotification {
	public IOSBroadcast(String appkey, String appMasterSecret) throws Exception {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		setPredefinedKeyValue("type", "broadcast"); // unicast broadcast
		// setPredefinedKeyValue("device_tokens","93428e4061562229698f666a7090bc5b14bea92bf1069072ed2b0489edd1d646");//测试单个设备
	}
}
