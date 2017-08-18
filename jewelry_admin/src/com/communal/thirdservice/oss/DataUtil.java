package com.communal.thirdservice.oss;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

public class DataUtil {
    //算法名称 
    public static final String KEY_ALGORITHM = "DES";
    //算法名称/加密模式/填充方式 
    //DES共有四种工作模式-->>ECB：电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式
    public static final String CIPHER_ALGORITHM = "DES/ECB/NoPadding";
    public static final String KEY = "A5d8wd$3jdknxm,3oi98765h";
    
    public static String md5(String str,String next){
    	String md5 = md5(str);
    	return md5(new StringBuilder(md5).append(next).toString());
    }
    
    public static String md5(String str){
		StringBuffer buf = new StringBuffer(""); 
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes()); 
			byte b[] = md.digest(); 
			int i; 
			for (int offset = 0; offset < b.length; offset++) { 
			i = b[offset]; 
			if(i<0) i+= 256; 
			if(i<16) 
			buf.append("0"); 
			buf.append(Integer.toHexString(i)); 
			} 
		} catch (NoSuchAlgorithmException e) { 
			e.printStackTrace(); 
		}
		return buf.toString();
	}
    
    /**
     * 生成密钥key对象
     * @param KeyStr 密钥字符串 
     * @return 密钥对象 
     * @throws InvalidKeyException   
     * @throws NoSuchAlgorithmException   
     * @throws InvalidKeySpecException   
     * @throws Exception 
     */
    private static SecretKey keyGenerator(String keyStr) throws Exception {
        byte input[] = HexString2Bytes(keyStr);
        DESKeySpec desKey = new DESKeySpec(input);
        //创建一个密匙工厂，然后用它把DESKeySpec转换成
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(desKey);
        return securekey;
    }

    private static int parse(char c) {
        if (c >= 'a') return (c - 'a' + 10) & 0x0f;
        if (c >= 'A') return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }

    // 从十六进制字符串到字节数组转换 
    public static byte[] HexString2Bytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }

    private static String to8M(String text){
		int len;
		if((len = text.length())%8!=0){
			final int min = (((len>>3) + 1)<<3);
			StringBuilder str = new StringBuilder(min);
			str.append(text);
			for(int i=0,l=min-len;i<l;++i){
				str.append(" ");
			}
			text = str.toString();
		}
		return text;
	}
    /** 
     * 加密数据
     * @param data 待加密数据
     * @param key 密钥
     * @return 加密后的数据 
     */
    public static String encrypt(String data){
    	data = to8M(data);
        try {
        	Key deskey = keyGenerator(KEY);
            // 实例化Cipher对象，它用于完成实际的加密操作
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            SecureRandom random = new SecureRandom();
            // 初始化Cipher对象，设置为加密模式
            cipher.init(Cipher.ENCRYPT_MODE, deskey, random);
            byte[] results = cipher.doFinal(data.getBytes());
            // 该部分是为了与加解密在线测试网站（http://tripledes.online-domain-tools.com/）的十六进制结果进行核对
//            for (int i = 0; i < results.length; i++) {
//                System.out.print(results[i] + " ");
//            }
//            System.out.println();
            // 执行加密操作。加密后的结果通常都会用Base64编码进行传输 
            return Base64.encodeBase64String(results);
		} catch (Exception e) {
			return "";
		}
    }

    /** 
     * 解密数据 
     * @param data 待解密数据 
     * @param key 密钥 
     * @return 解密后的数据 
     */
    public static String decrypt(String data){
    	data = to8M(data);
        try {
        	Key deskey = keyGenerator(KEY);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            //初始化Cipher对象，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, deskey);
            // 执行解密操作
            return new String(cipher.doFinal(Base64.decodeBase64(data))).trim();
		} catch (Exception e) {
			return "";
		}
    }
    
    /**
     * yunpay_test
     * 
     * 
     * LTAI2FOw39dKtQa2
     * sFV6Mh8d3cBBwUGj2jPivi3F6Ngt2p
     * acs:ram::1402106515026015:role/yunpay-test
     * 
     * 
     * @param args
     * @throws Exception
     */
    
    public static void main(String[] args) throws Exception {
    	String[] strs = {"LTAIerPo9fJ0DMyR","pU9tiMd31kNSx1LOHOPjyeT6W8kNTo","acs:ram::1402106515026015:role/yunpay-test"};
    	for(int i=0,l=strs.length;i<l;i++){
    		System.out.println(encrypt(strs[i]));
    	}
//        String source = "acs:ram::1485599082333184:role/aliyuncloudmonitordefaultrole";
//        System.out.println("原文: " + source);
//        String encryptData = encrypt(source);
//        System.out.println("加密后: " + encryptData);
//        String decryptData = decrypt(encryptData);
//        System.out.println("解密后: " + decryptData);
    }
}