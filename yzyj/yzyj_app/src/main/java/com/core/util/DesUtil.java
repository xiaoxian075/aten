package com.core.util;

import org.junit.Test;
import org.springframework.util.Base64Utils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.SecureRandom;

public class DesUtil {
	
	private final static String DES = "DES";
    
	/**
	 * Description 根据键值进行加密
	 * @param data
	 * @param key  加密键byte数组
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data, String key) throws Exception {
		byte[] bt = encrypt(data.getBytes("UTF-8"), key.getBytes());
		String strs = new BASE64Encoder().encode(bt);
		return Base64Utils.encodeToString(bt);
	}
   
	/**
	 * Description 根据键值进行加密
	 * @param data
	 * @param key  加密键byte数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);
		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(DES);
		// 用密钥初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		return cipher.doFinal(data);
	}
   
	/**
	 * Description 根据键值进行解密
	 * @param data
	 * @param key  加密键byte数组
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String decrypt(String data, String key) throws IOException, Exception {
		if (data == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] buf = decoder.decodeBuffer(data);
		byte[] bt = decrypt(buf,key.getBytes());
		return new String(bt, "UTF-8");
	}
    
    
	/**
	 * Description 根据键值进行解密
	 * @param data
	 * @param key  加密键byte数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);
		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(DES);
		// 用密钥初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		return cipher.doFinal(data);
	}
	@Test
	public static void main(String[] args) throws Exception {
//		System.out.println(encrypt("gKnsf5bMlk7j0ADF", "12345678"));
//		System.out.println(encrypt("jdbc:oracle:thin:@10.27.142.42:1521:yunpay", "12345678"));
//		System.out.println(encrypt("sa", "12345678"));
		System.out.println(encrypt("{token:'5hujDK3d284'}", "YZYJ#LFJSF#93%FE@3"));
		System.out.println(decrypt("ntAeV3aprcKZpi+5hT+eGAJpBD4LmRj+", "YZYJ#LFJSF#93%FE@3"));
		//System.out.println(decrypt("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", "12345678"));
	}
}
