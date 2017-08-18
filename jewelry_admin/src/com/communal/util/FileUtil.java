package com.communal.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import org.apache.log4j.Logger;

import com.communal.util.FileUtil;


/**
 * @author linjunqin
 * @Description: 用户文件的 读 写 目录的创建等
 * @param tags
 * @date 2017-1-31 下午2:17:06
 */
public class FileUtil {

	private static Logger logger = Logger.getLogger(FileUtil.class);

	private static String FILESPARA = "/";

	public FileUtil() {
	}


	/**
	 * @author linjunqin
	 * @Description: 用UTF-8 编码读取文件 
	 * @param tags
	 * @date 2017-1-31 下午2:16:59
	 */
	public static String readTxt(String filePathAndName) {
		return readTxt(filePathAndName, "UTF-8");
	}

	/**
	 * @author linjunqin
	 * @Description: 读取文本文件内容
	 * @param tags
	 * @date 2017-1-31 下午2:17:42
	 */
	public static String readTxt(String filePathAndName, String encoding) {
		encoding = encoding.trim();
		StringBuffer str = new StringBuffer("");
		String st = "";
		FileInputStream fs = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fs = new FileInputStream(filePathAndName);

			if (encoding.equals("")) {
				isr = new InputStreamReader(fs);
			} else {
				isr = new InputStreamReader(fs, encoding);
			}
			br = new BufferedReader(isr);
			try {
				String data = "";
				while ((data = br.readLine()) != null) {
					str.append(data + "\n");
				}
			} catch (Exception e) {
				str.append(e.toString());
			}
			st = str.toString();
			fs.close();
			isr.close();
			br.close();
		} catch (IOException es) {
			logger.info(filePathAndName + "文件路径读取错误");
		} finally {
			if (fs != null) {
				try {
					fs.close();
				} catch (IOException e1) {
				}
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e1) {
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e1) {
				}
			}
		}
		return st;
	}


	/**
	 * @author linjunqin
	 * @Description: 用UTF-8 编码保存文件
	 * @param tags
	 * @date 2017-1-31 下午2:17:54
	 */
	public static void writeTxt(String path, String filename, String fileContent) {
		writeTxt(path, filename, fileContent, "UTF-8");

	}

	/**
	 * @author linjunqin
	 * @Description: 有编码方式的文件创建
	 * @param tags
	 * @date 2017-1-31 下午2:18:14
	 */
	public static void writeTxt(String path, String filename, String fileContent,
			String encoding) {
		PrintWriter pwrite = null;
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			file = new File(path + FILESPARA + filename);
			if (encoding != null && !"".equals(encoding)) {
				pwrite = new PrintWriter(file, encoding);
			} else {
				pwrite = new PrintWriter(file);
			}
			pwrite.println(fileContent);
			pwrite.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (pwrite != null) {
				pwrite.close();
			}
		}
	}


	/**
	 * @author linjunqin
	 * @Description: 拷贝目录树 把一个目录下的所有东西包括子目录 同时拷贝到 另外一个目录
	 * @param tags
	 * @date 2017-1-31 下午2:18:40
	 */
	public static void copyDir(String sourceDir, String targetDir) throws Exception {
		FileInputStream input = null;
		FileOutputStream output = null;
		String url1 = sourceDir;
		String url2 = targetDir;
		if (!(new File(url2)).exists()) {
			(new File(url2)).mkdirs();
		}
		File[] file = (new File(url1)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				file[i].toString();
				input = new FileInputStream(file[i]);
				output = new FileOutputStream(url2
						+ System.getProperty("file.separator")
						+ (file[i].getName()).toString());
				byte[] b = new byte[1024 * 5];
				int len;
				while ((len = input.read(b)) != -1) {
					output.write(b, 0, len);
				}
				output.flush();
				output.close();
				input.close();
			} else if (file[i].isDirectory()) {
				String url_2_dir = url2 + System.getProperty("file.separator")
						+ file[i].getName();
				copyDir(file[i].getPath(), url_2_dir);
			}
		}
		if (input != null) {
			input.close();
		}
		if (output != null) {
			output.close();
		}
	}


	/**
	 * @author linjunqin
	 * @Description: 创建目录并返回目录创建后的路径
	 * @param tags
	 * @date 2017-1-31 下午2:18:52
	 */
	public static String createFolder(String folderPath) {
		String txt = folderPath;
		try {
			File myFilePath = new File(txt);
			txt = folderPath;
			if (!myFilePath.exists()) {
				myFilePath.mkdirs();
			}
		} catch (Exception e) {
			logger.info("创建目录操作出错");
		}
		return txt;
	}

	/**
	 * @author linjunqin
	 * @Description: 通过绝对路径删除文件
	 * @param tags
	 * @date 2017-1-31 下午2:19:17
	 */
	public static boolean delFile(String filePathAndName) {
		boolean bea = false;
		try {
			String filePath = filePathAndName;
			File myDelFile = new File(filePath);
			if (myDelFile.exists()) {
				myDelFile.delete();
				bea = true;
			} else {
				bea = false;
				logger.info(filePathAndName + "<br>删除文件操作出错");
			}
		} catch (Exception e) {
			logger.info(e.toString());
		}
		return bea;
	}

	
	/**
	 * @author linjunqin
	 * @Description: 删除文件夹
	 * @param tags
	 * @date 2017-1-31 下午2:19:49
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			logger.info("删除文件夹操作出错");
		}
	}


	/**
	 * @author linjunqin
	 * @Description: 通过文件夹的绝对路径删除指定文件夹下所有文件
	 * @param tags
	 * @date 2017-1-31 下午2:20:10
	 */
	public static boolean delAllFile(String path) {
		boolean bea = false;
		File file = new File(path);
		if (!file.exists()) {
			return bea;
		}
		if (!file.isDirectory()) {
			return bea;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				bea = true;
			}
		}
		return bea;
	}

	
	/**
	 * @author linjunqin
	 * @Description: 复制单个文件拷贝到新绝对路径,带文件名
	 * @param tags
	 * @date 2017-1-31 下午2:20:38
	 */
	public static void copyFile(String oldPathFile, String newPathFile) {
		InputStream is = null;
		FileOutputStream fs = null;
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPathFile);
			if (oldfile.exists()) { // 文件存在时
				//分离文件夹路径与文件名
				String newPath = newPathFile.substring(0,newPathFile.lastIndexOf("\\"));
				if (!(new File(newPath)).exists()) {
					(new File(newPath)).mkdirs();
				}
				is = new FileInputStream(oldPathFile); // 读入原文件
				fs = new FileOutputStream(newPathFile);
				byte[] buffer = new byte[1444];
				while ((byteread = is.read(buffer)) != -1) {
					bytesum += byteread; // 字节  数 文件大小
					fs.write(buffer, 0, byteread);
				}
				is.close();
				fs.close();
			}
		} catch (Exception e) {
			logger.info("复制单个文件操作出错");
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fs!=null){
				try {
					fs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @author linjunqin
	 * @Description: 移动文件
	 * @param tags
	 * @date 2017-1-31 下午2:20:59
	 */
	public static void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	/**
	 * @author linjunqin
	 * @Description: 移动目录
	 * @param tags
	 * @date 2017-1-31 下午2:21:07
	 */
	public static void moveFolder(String sourceDir, String targetDir) throws Exception {
		copyDir(sourceDir, targetDir);
		delFolder(sourceDir);
	}


	/**
	 * @author linjunqin
	 * @Description: 获取文件的扩展名
	 * @param tags
	 * @date 2017-1-31 下午2:21:19
	 */
	public String getFileExt(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf('.');

			if ((i > 0) && (i < (filename.length() - 1))) {
				return filename.substring(i + 1);
			}
		}
		return "";
	}
	
	
	/**
	 * @author linjunqin
	 * @Description: 获取文件的大小
	 * @param tags
	 * @date 2017-1-31 下午1:08:38
	 */
	public static Double getFilesize(String filepath){
		File backupath = new File(filepath);
		return Double.valueOf(backupath.length())/1000.000;
	}
	
	
}