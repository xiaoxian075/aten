package com.communal.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import com.communal.util.FileUtil;
import com.communal.util.PropertiesUtil;


/**
 * @author linjunqin
 * @Description 读取Properties文件类
 * @param
 * @date 2017-2-3 上午9:05:25
 */
public class PropertiesUtil {

	//配置文件对象
	private Properties props = null;

	//项目根路径
	private static String rootpath;
	
	private String filePath;
	
	private String thisFileName;

	/**
	 * @author linjunqin
	 * @Description 读取配置文件 
	 * @param fileName 文件名
	 * @date 2017-2-3 上午9:07:11
	 */
	public PropertiesUtil(String fileName) {
		this.thisFileName = fileName;
		this.filePath = getPath(PropertiesUtil.class) + fileName;
		System.out.println(filePath);
		props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					this.filePath));
			props.load(in);
			System.out.println(props+"++++");
			// 关闭资源
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author linjunqin
	 * @Description 根据key值读取配置的值
	 * @param
	 * @date 2017-2-3 上午9:07:02
	 */
	public String readValue(String key) throws IOException {
		return props.getProperty(key);
	}

	/**
	 * @author linjunqin
	 * @Description 修改或添加键值对 如果key存在，修改, 反之，添加。
	 * @param
	 * @date 2017-2-3 上午9:10:37
	 */
	public void writeData(String key, String value) {

		Properties prop = new Properties();
		
		try {
			File file = new File(this.filePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			InputStream fis = new FileInputStream(file);
			prop.load(fis);
			// 一定要在修改值之前关闭fis
			fis.close();
			OutputStream fos = new FileOutputStream(this.filePath);
			prop.setProperty(key, value);
			// 保存，并加入注释
			prop.store(fos, "Update '" + key + "' value");
			fos.close();
			
			FileUtil fileUtil = new FileUtil();
			String fileCon = fileUtil.readTxt(this.filePath);
			fileCon = fileCon.replace("\\", "");
			fileUtil.writeTxt(getPath(PropertiesUtil.class), this.thisFileName, fileCon);
			
		} catch (IOException e) {
			System.err.println("Visit " + this.filePath + " for updating " + value+ " value error");
		}
	}

	/**
	 * @author linjunqin
	 * @Description 读取配置文件的信息存入Map中
	 * @param
	 * @date 2017-2-3 上午9:09:53
	 */
	public Map readAllProperties()
			throws FileNotFoundException, IOException {
		// 保存所有的键值
		Map map = new HashMap();
		Enumeration en = props.propertyNames();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			String Property = props.getProperty(key);
			map.put(key, Property);
		}
		return map;
	}


	/**
	 * @author linjunqin
	 * @Description 类的路径
	 * @param
	 * @date 2017-2-3 上午9:09:07
	 */
	private static String getPath(Class name) {
		String strResult = null;
		if (System.getProperty("os.name").toLowerCase().indexOf("window") > -1) {
			strResult = name.getResource("/").toString().replace("file:/", "").replace("%20", " ");
		} else {
			strResult = name.getResource("/").toString().replace("file:", "").replace("%20", " ");
		}
		return strResult;
	}

	/**
	 * @author linjunqin
	 * @Description 获取根目录
	 * @param
	 * @date 2017-2-3 上午9:09:19
	 */
	public static String getRootpath() {
		if (rootpath != null) {
			return rootpath;
		} else {
			String root_path = getPath(PropertiesUtil.class).replace(
					"WEB-INF/classes/", "");
			setRootpath(root_path);
			return root_path;
		}
	}

	public static String getClassPath() {
		return getPath(PropertiesUtil.class);
	}

	public static void setRootpath(String rootpath) {
		PropertiesUtil.rootpath = rootpath;
	}

}
