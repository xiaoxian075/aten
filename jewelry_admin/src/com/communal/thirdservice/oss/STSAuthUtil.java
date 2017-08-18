package com.communal.thirdservice.oss;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse.Credentials;
import com.aten.client.SmsClientOld;
import com.aten.client.UmenClient;
import com.aten.function.SysconfigFuc;


/**
 * 
 * 
Access Key ID
LTAIBiqGd2EaD6lF

Access Key Secret
eY6OvNhxi7JBYziKn82z0prmXodhbV

Bucket
yunpay

OSS外网域名: yunpay.oss-cn-shanghai.aliyuncs.com
OSS内网域名:yunpay.oss-cn-shanghai-internal.aliyuncs.com
自定义绑定域名:img.ipaye.cn
 * 
 * 
 * @author zms
 * @date 2017年2月16日 下午5:05:20
 * @description
 */
public class STSAuthUtil {
	
	private static final Logger log = LoggerFactory.getLogger(STSAuthUtil.class);
	
	// 目前只有"cn-hangzhou"这个region可用, 不要使用填写其他region的值
	public static final String REGION_CN_HANGZHOU = "cn-hangzhou";
	// 当前 STS API 版本
	public static final String STS_API_VERSION = "2015-04-01";
	public static final String ERROR;
	
	//设置超时时间
	private static final long TIME_OUT = 1800*1000L;
	private static final Executor EXECUTOR = Executors.newSingleThreadExecutor();
	
	private static ConcurrentHashMap<String, Auth> CONFIG = new ConcurrentHashMap<>();
	private static ConcurrentHashMap<String, Object[]> STSKEYS = new ConcurrentHashMap<>();
	
	public static class CredentialsExt extends Credentials{
		
		public CredentialsExt(){}
		
		public CredentialsExt(Credentials credentials){
			super.setAccessKeyId(credentials.getAccessKeyId());
			super.setAccessKeySecret(credentials.getAccessKeySecret());
			super.setExpiration(credentials.getExpiration());
			super.setSecurityToken(credentials.getSecurityToken());
		}
		
		private String domain;
		private String bucket;
		public String getDomain() {
			return domain;
		}
		public void setDomain(String domain) {
			this.domain = domain;
		}
		public String getBucket() {
			return bucket;
		}
		public void setBucket(String bucket) {
			this.bucket = bucket;
		}
		
		
	}
	
	static{
		CredentialsExt credentials = new CredentialsExt();
		credentials.setAccessKeyId("");
		credentials.setAccessKeySecret("");
		credentials.setExpiration("");
		credentials.setSecurityToken("");
		credentials.setDomain("");
		credentials.setBucket("");
		ERROR = JSON.toJSONString(credentials);
		loadConfig();
	}
	
	public static void main(String[] args) {
//		String user = "yunpay_test";
//		String key = UUID.randomUUID().toString();
//		StringBuilder willSign = new StringBuilder("^10086$");
//		willSign.append(user).append("&=").append(key).append("$00987!");
//		String sign = DataUtil.md5(willSign.toString(),"thisIsMd5Key");
//		System.out.println(getAuth(user, key, sign));
	}
	
	/**
	 * 从OSS服务器取得上传文件授权
	 * @param accessKeyId			阿里云颁发给用户的访问服务所用的密钥ID。
	 * @param accessKeySecret		
	 * @param roleArn				指定角色的全局资源描述符(Aliyun Resource Name，简称Arn)
	 * @param roleSessionName		用户自定义参数。此参数用来区分不同的Token，可用于用户级别的访问审计。
	 * @param policy				授权策略Policy，Policy长度限制为1024字节；您可以通过此参数限制生成的Token的权限，不指定则返回的Token将拥有指定角色的所有权限。
	 * @param protocolType
	 * @return
	 * @throws ClientException
	 */
	static AssumeRoleResponse assumeRole(String accessKeyId, String accessKeySecret, String roleArn,
			String roleSessionName, String policy, ProtocolType protocolType) throws ClientException {
		try {
			// 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
			IClientProfile profile = DefaultProfile.getProfile(REGION_CN_HANGZHOU, accessKeyId, accessKeySecret);
			DefaultAcsClient client = new DefaultAcsClient(profile);

			// 创建一个 AssumeRoleRequest 并设置请求参数
			final AssumeRoleRequest request = new AssumeRoleRequest();
			request.setVersion(STS_API_VERSION);//API版本号
			request.setMethod(MethodType.POST);
			request.setProtocol(protocolType);

			request.setRoleArn(roleArn);
			request.setRoleSessionName(roleSessionName);
			request.setPolicy(policy);

			// 发起请求，并得到response
			final AssumeRoleResponse response = client.getAcsResponse(request);
			return response;
		} catch (ClientException e) {
			throw e;
		}
	}
	
	public static void loadConfig(){
		//加载配置文件
    	try(InputStream is = STSAuthUtil.class.getResourceAsStream("sts.json")){
    		BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"));
			String line = "";
			StringBuilder sb = new StringBuilder();
			while((line = reader.readLine()) != null){
				sb.append(line);
			}
			JSONObject auth = JSON.parseObject(sb.toString().replaceAll(" ", ""));
			Set<String> users = auth.keySet();
			for (Iterator iterator = users.iterator(); iterator.hasNext();) {
				String user = (String) iterator.next();
				JSONObject data = auth.getJSONObject(user);
				CONFIG.put(user, data.toJavaObject(Auth.class));
			}
			log.info("---加载json配置完成");
    	}catch (Exception e) {
    		throw new RuntimeException(e);
		}
	}
	
	public static String getAuth(final String user,String key,String sign){
		final Auth auth = CONFIG.get(user);
		if(auth == null){
			log.error("--用户不存在");
			return ERROR;
		}
		//签名校验
//		StringBuilder willSign = new StringBuilder("^10086$");
//		willSign.append(user).append("&=").append(key).append("$00987!");
//		String serverSign = DataUtil.md5(willSign.toString(),auth.getSign());
//		if(!serverSign.equals(sign)){
//			log.error("---签名错误，应{},但{},url:[{}]",serverSign,sign,willSign.toString());
//			return ERROR;
//		}
		 // 只有 RAM用户（子账号）才能调用 AssumeRole 接口
        // 阿里云主账号的AccessKeys不能用于发起AssumeRole请求
        // 请首先在RAM控制台创建一个RAM用户，并为这个用户创建AccessKeys
		Object[] data = STSKEYS.get(user);
		if(data != null){
			long timeOut = ((Long)data[1]).longValue();
			if(System.currentTimeMillis() - timeOut >= 1600*1000L){
				//超时,异步更新
				EXECUTOR.execute(new Runnable() {
					public void run() {
						requestAuth(user,auth);
					}
				});
			}
			return (String)data[0];
		}else{
			return requestAuth(user,auth);
		}
	}
	
	private static String requestAuth(String user,Auth auth){
        String accessKeyId = auth.getAccessKeyId();
        String accessKeySecret = auth.getAccessKeySecret();
        // RoleArn 需要在 RAM 控制台上获取
        String roleArn = auth.getRoleArn();
        // RoleSessionName 是临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁
        // 但是注意RoleSessionName的长度和规则，不要有空格，只能有'-' '.' '@' 字母和数字等字符
        // 具体规则请参考API文档中的格式要求
        String roleSessionName = auth.getRoleSessionName();
        System.out.println(">>" + accessKeyId);
        System.out.println(">>" + accessKeySecret);
        System.out.println(">>" + roleArn);
        String policy = "{\n" +
                "    \"Version\": \"1\", \n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Action\": [\n" +
                "                \"oss:*\"\n" +
                "            ], \n" +
                "            \"Resource\": [\n" +
                "                \"acs:oss:*:*:*\" \n" +
                "            ], \n" +
                "            \"Effect\": \"Allow\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        // 此处必须为 HTTPS
        ProtocolType protocolType = ProtocolType.HTTPS;

        try {
            final AssumeRoleResponse response = assumeRole(accessKeyId, accessKeySecret,
                    roleArn, roleSessionName, policy, protocolType);

            System.out.println("Expiration: " + response.getCredentials().getExpiration());
            System.out.println("Access Key Id: " + response.getCredentials().getAccessKeyId());
            System.out.println("Access Key Secret: " + response.getCredentials().getAccessKeySecret());
            System.out.println("Security Token: " + response.getCredentials().getSecurityToken());
            
            Credentials credentials = (Credentials) response.getCredentials();
            CredentialsExt ext = new CredentialsExt(credentials);
            ext.setDomain(auth.getDomain());
            ext.setBucket(auth.getBucket());
            String res = JSON.toJSONString(ext);
            STSKEYS.put(user, new Object[]{res,System.currentTimeMillis()+TIME_OUT});
            return res;
        } catch (ClientException e) {
            log.error("Failed to get a token.");
            log.error("Error code: {}", e.getErrCode());
            log.error("Error message: {}", e.getErrMsg());
        }
        return ERROR;
	}
	
	static class Auth{
		private String accessKeyId;
		private String accessKeySecret;
		private String roleArn;
		private String roleSessionName;
		private String bucket;
		private String domain;
		private String sign;
		public String getSign() {
			return sign;
		}
		public void setSign(String sign) {
			this.sign = sign;
		}
		public String getRoleSessionName() {
			return roleSessionName;
		}
		public void setRoleSessionName(String roleSessionName) {
			this.roleSessionName = roleSessionName;
		}
		public String getRoleArn() {
			return roleArn;
		}
		public void setRoleArn(String roleArn) {
			this.roleArn = DataUtil.decrypt(roleArn);
		}
		public String getAccessKeyId() {
			return accessKeyId;
		}
		public void setAccessKeyId(String accessKeyId) {
			this.accessKeyId = DataUtil.decrypt(accessKeyId);
		}
		public String getAccessKeySecret() {
			return accessKeySecret;
		}
		public void setAccessKeySecret(String accessKeySecret) {
			this.accessKeySecret = DataUtil.decrypt(accessKeySecret);
		}
		public String getBucket() {
			return bucket;
		}
		public void setBucket(String bucket) {
			this.bucket = bucket;
		}
		public String getDomain() {
			return domain;
		}
		public void setDomain(String domain) {
			this.domain = domain;
		}
	}
}


