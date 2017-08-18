package com.aten.test;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;

import java.util.HashMap;
import java.util.Map;

public class StsServiceSample {
    // 目前只有"cn-hangzhou"这个region可用, 不要使用填写其他region的值
    public static final String REGION_CN_HANGZHOU = "cn-hangzhou";
    // 当前 STS API 版本
    public static final String STS_API_VERSION = "2015-04-01";

    static AssumeRoleResponse assumeRole(String accessKeyId, String accessKeySecret,
                                         String roleArn, String roleSessionName, String policy,
                                         ProtocolType protocolType) throws ClientException {
        try {
            // 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
            IClientProfile profile = DefaultProfile.getProfile(REGION_CN_HANGZHOU, accessKeyId, accessKeySecret);
            DefaultAcsClient client = new DefaultAcsClient(profile);

            // 创建一个 AssumeRoleRequest 并设置请求参数
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setVersion(STS_API_VERSION);
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
    public static Map getOss(){
        Map map=new HashMap();
        // 只有 RAM用户（子账号）才能调用 AssumeRole 接口
        // 阿里云主账号的AccessKeys不能用于发起AssumeRole请求
        // 请首先在RAM控制台创建一个RAM用户，并为这个用户创建AccessKeys
        String accessKeyId = "LTAIKllzhsEUg7Hh";
//        String accessKeyId = "LTAISC6HjqbxhBNP";
        String accessKeySecret = "sKBPizJEyTCO6IyqvfJNQ3Rg5OAm5T";

        // RoleArn 需要在 RAM 控制台上获取
        String roleArn = "acs:ram::1309990942368875:role/yszb-full";
        String bucket = "yszb-dev";

        // RoleSessionName 是临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁
        // 但是注意RoleSessionName的长度和规则，不要有空格，只能有'-' '.' '@' 字母和数字等字符
        // 具体规则请参考API文档中的格式要求
        String roleSessionName = "yfly-test";
        String policy ="{"+
            " \"Statement\": ["+
            "{"+
                " \"Action\": \"oss:*\","+
                   " \"Effect\": \"Allow\","+
                   " \"Resource\": [   \"acs:oss:*:*:yfly-dev\",\n" +
                "        \"acs:oss:*:*:yfly-dev/*\"]"+
            "} ],"+
            "\"Version\": \"1\""+
        "}";

//        sts:AssumeRole
//        String policy = "{\n" +
//                "    \"Version\": \"1\", \n" +
//                "    \"Statement\": [\n" +
//                "        {\n" +
//                "            \"Action\": [\n" +
//                "                \"oss:*\"\n" +
//                "            ], \n" +
//                "            \"Resource\": [\n" +
//                "                \"acs:oss:*:*:*\" \n" +
//                "            ], \n" +
//                "            \"Effect\": \"Allow\"\n" +
//                "        }\n" +
//                "    ]\n" +
//                "}";

        // 此处必须为 HTTPS
        ProtocolType protocolType = ProtocolType.HTTPS;

        try {
            final AssumeRoleResponse response = assumeRole(accessKeyId, accessKeySecret,
                    roleArn, roleSessionName, policy, protocolType);
            map.put("accessKey",response.getCredentials().getExpiration());
            map.put("accessKeyId",response.getCredentials().getAccessKeyId());
            map.put("accessKeySecret",response.getCredentials().getAccessKeySecret());
            map.put("securityToken",response.getCredentials().getSecurityToken());
            map.put("region",StsServiceSample.REGION_CN_HANGZHOU);
            map.put("bucket",bucket);
            map.put("domain","oss-cn-shanghai.aliyuncs.com");

//            System.out.println("Expiration: " + response.getCredentials().getExpiration());
//            System.out.println("Access Key Id: " + response.getCredentials().getAccessKeyId());
//            System.out.println("Access Key Secret: " + response.getCredentials().getAccessKeySecret());
//            System.out.println("Security Token: " + response.getCredentials().getSecurityToken());
        } catch (ClientException e) {
            System.out.println("Failed to get a token.");
            System.out.println("Error code: " + e.getErrCode());
            System.out.println("Error message: " + e.getErrMsg());
        }
        return map;
    }
    public static void main(String[] args) {
        // 只有 RAM用户（子账号）才能调用 AssumeRole 接口
        // 阿里云主账号的AccessKeys不能用于发起AssumeRole请求
        // 请首先在RAM控制台创建一个RAM用户，并为这个用户创建AccessKeys
        String accessKeyId = "LTAISC6HjqbxhBNP";
        String accessKeySecret = "G1vxEOPj82hRm9V1ChPNQgWAGqgbpO";

        // RoleArn 需要在 RAM 控制台上获取
        String roleArn = "acs:ram::1751693273293286:role/yfly-full";

        // RoleSessionName 是临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁
        // 但是注意RoleSessionName的长度和规则，不要有空格，只能有'-' '.' '@' 字母和数字等字符
        // 具体规则请参考API文档中的格式要求
        String roleSessionName = "yfly";

//        String policy = "{\n" +
//                "    \"Version\": \"1\", \n" +
//                "    \"Statement\": [\n" +
//                "        {\n" +
//                "            \"Action\": [\n" +
//                "                \"oss:*\"\n" +
//                "            ], \n" +
//                "            \"Resource\": [\n" +
//                "                \"acs:oss:*:*:*\" \n" +
//                "            ], \n" +
//                "            \"Effect\": \"Allow\"\n" +
//                "        }\n" +
//                "    ]\n" +
//                "}";
        
        String policy = "{\"Statement\": [{\"Action\": \"sts:AssumeRole\",\"Effect\": \"Allow\",\"Resource\": \"acs:ram::1309990942368875:role/yszb-full\"}],\"Version\": \"1\"}";
        
      
        
//        {
//        	  "Statement": [
//        	    {
//        	      "Action": "sts:AssumeRole",
//        	      "Effect": "Allow",
//        	      "Resource": "acs:ram::1309990942368875:role/yszb-full"
//        	    }
//        	  ],
//        	  "Version": "1"
//        	}

        // 此处必须为 HTTPS
        ProtocolType protocolType = ProtocolType.HTTPS;

        try {
            final AssumeRoleResponse response = assumeRole(accessKeyId, accessKeySecret,
                    roleArn, roleSessionName, policy, protocolType);

            System.out.println("Expiration: " + response.getCredentials().getExpiration());
            System.out.println("Access Key Id: " + response.getCredentials().getAccessKeyId());
            System.out.println("Access Key Secret: " + response.getCredentials().getAccessKeySecret());
            System.out.println("Security Token: " + response.getCredentials().getSecurityToken());
        } catch (ClientException e) {
            System.out.println("Failed to get a token.");
            System.out.println("Error code: " + e.getErrCode());
            System.out.println("Error message: " + e.getErrMsg());
        }

    }
}
