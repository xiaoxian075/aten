package com.core.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**  
* 对密码进行加密和验证的类 
*/  
public class EncryptUtil {
	//十六进制下数字到字符的映射数组  
    private static final  String[] hexDigits = {"0", "1", "2", "3", "4",
        "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};  
      
    /** * 把inputString加密     */  
    public static String generatePassword(String inputString){
        return encodeByMD5(inputString);  
    }  
      
    /**
       * 验证输入的密码是否正确
     * @param password    加密后的密码
     * @param inputString    输入的字符串
     * @return    验证结果，TRUE:正确 FALSE:错误
     */
    public static boolean validatePassword(String password, String inputString){
        if(password.equals(encodeByMD5(inputString))){
            return true;
        } else{
            return false;
        }
    }
    /**  对字符串进行MD5加密     */
    private static String encodeByMD5(String originString){
        if (originString != null){
            try{
                //创建具有指定算法名称的信息摘要 
                MessageDigest md = MessageDigest.getInstance("MD5");
                //使用指定的字节数组对摘要进行最后更新，然后完成摘要计算 
                byte[] results = md.digest(originString.getBytes());
                //将得到的字节数组变成字符串返回 
                String resultString = byteArrayToHexString(results);
                return resultString.toUpperCase();
            } catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return null;
    }
    
    /**  
     * 转换字节数组为十六进制字符串 
     * @param
     * @return    十六进制字符串 
     */  
    private static String byteArrayToHexString(byte[] b){
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++){  
            resultSb.append(byteToHexString(b[i]));  
        }  
        return resultSb.toString();  
    }  
      
    /** 将一个字节转化成十六进制形式的字符串     */  
    private static String byteToHexString(byte b){
        int n = b;  
        if (n < 0)  
            n = 256 + n;  
        int d1 = n / 16;  
        int d2 = n % 16;  
        return hexDigits[d1] + hexDigits[d2];  
    }  
    /*
     * MD5加密方法调用
     */
//    public static void main(String[] args) {
//    	String pwd1="123";  
//        String pwd2="";  
//        EncryptUtil cipher = new EncryptUtil();  
//        System.out.println("未加密的密码:"+pwd1);  
//        //将123加密  
//        pwd2 = cipher.generatePassword(pwd1);  
//        System.out.println("加密后的密码:"+pwd2);  
//          
//        System.out.print("验证密码是否下确:");  
//        if(cipher.validatePassword(pwd2, pwd1)) {  
//            System.out.println("正确");  
//        }  
//        else {  
//            System.out.println("错误");  
//        }  
//	}
    
    /**  
     * 用MD5算法进行加密  
     * @param str 需要加密的字符串  
     * @return MD5加密后的结果  
     */    
    public static String encodeMD5String(String str) {
        return encode(str, "MD5");    
    }    
    
    /**  
     * 用SHA算法进行加密  
     * @param str 需要加密的字符串  
     * @return SHA加密后的结果  
     */    
    public static String encodeSHAString(String str) {
        return encode(str, "SHA");    
    }    
    
    /**  
     * 用base64算法进行加密  
     * @param str 需要加密的字符串  
     * @return base64加密后的结果  
     */    
    public static String encodeBase64String(String str) {
        BASE64Encoder encoder =  new BASE64Encoder();
        return encoder.encode(str.getBytes());    
    }    
        
    /**  
     * 用base64算法进行解密  
     * @param str 需要解密的字符串  
     * @return base64解密后的结果  
     * @throws java.io.IOException
     */    
    public static String decodeBase64String(String str) throws IOException {
        BASE64Decoder encoder =  new BASE64Decoder();
        return new String(encoder.decodeBuffer(str));
    }    
        
    private static String encode(String str, String method) {
        MessageDigest md = null;
        String dstr = null;
        try {    
            md = MessageDigest.getInstance(method);
            md.update(str.getBytes());    
            dstr = new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();    
        }    
        return dstr;    
    }    
        
//    public static void main(String[] args) throws IOException {    
//        String user = "oneadmin";    
//        System.out.println("原始字符串 " + user);    
//        System.out.println("MD5加密 " + encodeMD5String(user));    
//        System.out.println("SHA加密 " + encodeSHAString(user));    
//        String base64Str = encodeBase64String(user);    
//        System.out.println("Base64加密 " + base64Str);    
//        System.out.println("Base64解密 " + decodeBase64String(base64Str));    
//    }   
}
