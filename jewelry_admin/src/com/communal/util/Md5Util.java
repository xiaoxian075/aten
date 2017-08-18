package com.communal.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author linjunqin
 * @Description MD5工具类
 * @param
 * @date 2016-12-13 上午9:29:33
 */
public class Md5Util {

	/**
	 * @author linjunqin
	 * @Description: 使用MD5编码加密
	 * @param tags
	 * @date 2017-2-1 下午12:26:09
	 */
	public static String getMD5(String content) {
		String s = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] source = content.getBytes();
			md.update(source);
			byte tmp[] = md.digest();
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];

				str[k++] = hexDigits[byte0 & 0xf];
			}
			s = new String(str);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s.toUpperCase();
	}

	/**
	 * @author linjunqin
	 * @Description: 计算文件的MD5码
	 * @param tags
	 * @date 2017-2-1 下午12:29:36
	 */
	public static String getFileMD5(File file) {
		String value = null;
		FileInputStream in;
		try {
			in = new FileInputStream(file);
			try {
				MappedByteBuffer byteBuffer = in.getChannel().map(
						FileChannel.MapMode.READ_ONLY, 0, file.length());
				MessageDigest md5 = MessageDigest.getInstance("MD5");
				md5.update(byteBuffer);
				BigInteger bi = new BigInteger(1, md5.digest());
				value = bi.toString(16);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != in) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return value;
	}

	public static void main(String[] args){
		System.out.println(getMD5("1"));
	}
}

