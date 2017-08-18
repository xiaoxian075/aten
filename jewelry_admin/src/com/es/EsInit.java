package com.es;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.collections.set.SynchronizedSet;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.aten.function.SysconfigFuc;

public class EsInit {
	
	// transport客户端
	private static TransportClient client = null;
	// es实例名称
	private static String clusterName = "elasticsearch";
	// 远程地址
	private static String address = SysconfigFuc.getSysValue("cfg_es_host");//"192.168.1.239";
	// private String address = "localhost";
	// 端口
	private static int port = Integer.parseInt(SysconfigFuc.getSysValue("cfg_es_port"));//9300;
	
	
	public synchronized static TransportClient getClient() {
		try {
			if (client == null) {
				 //设置集群名称
		        Settings settings = Settings.builder()
		        		.put("cluster.name", clusterName) //设置ES实例的名称
		        		.put("client.transport.sniff", true)//自动嗅探整个集群的状态，把集群中其他ES节点的ip添加到本地的客户端列表中
		        		.build();
				client = new PreBuiltTransportClient(settings)
				        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(address), port));
			}
		}catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return client;
	}

}
