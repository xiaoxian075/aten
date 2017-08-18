package com.aten.deploy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;
import com.communal.util.DateUtil;
import com.communal.util.FileUtil;
import com.communal.util.Md5Util;
import com.communal.util.ProjectUtil;
import com.communal.util.StringUtil;
import com.communal.util.ZipCompressUtil;

public class DeployFile {
	
	private static Logger logger = Logger.getLogger(DeployFile.class);
	//版本库所在盘
	private static String JAVA_PROJECT_LIB="D:/java_project_lib";
	//部署的web项目名称
	private static String DEPLOY_NAME="yszb_admin";
	//项目根目录
	private static String DEPLOY_WEBROOT="ROOT";
	//定义HashMap list列表
	private static List<HashMap<String,String>> filePathList = new ArrayList<HashMap<String,String>>();
	//设置不需要复制的文件或文件夹列表
	private static List<String> strList = new ArrayList<String>();
	//webRoot目录路径
	private static String wr_path;
	//版本库路径  
	private static String project_time_lib;
	//部署web名称路径
	private static String project_deployName;
	//项目打包路径
	private static String project_park_path;
	//项目打包的完整路径
	private static String project_park_all_path;
	//增量更新的文件夹名称
	private static String project_increFolderName;
	//每日打包版本记录
	private static int day_version = 1;
	//运行程序
	public static void main(String[] args) {
		
		setPath();//设置路径全局变量
		
		BulkPack();//全量打包，去除过滤条件
		
		//increPack();//增量打包，去除过滤条件
		
		//BulkWebRootPack();//打包整个项目，不过滤条件
		
		//getLibCatalog(); //生成过滤jar文件路径
		//System.out.println(new Date());
		System.exit(1);
	}
	
	/**
	 * @author linjunqin
	 * @Description: 设置路径
	 * @param tags
	 * @date 2017-2-1 上午11:31:28
	 */
	private static void setPath(){
		//webroot路径
		wr_path = ProjectUtil.getProjectWebRootPath();
		//版本库路径
		project_time_lib =JAVA_PROJECT_LIB+File.separator+ProjectUtil.getProjectName()+"_"+DateUtil.getYmd();
		//项目打包路径
		project_park_path = project_time_lib+File.separator+DEPLOY_NAME;
		//部署web名称
		project_deployName = project_park_path+File.separator+DEPLOY_WEBROOT;
		//增量更新的文件夹名称
		project_increFolderName = project_time_lib+File.separator+DEPLOY_WEBROOT+"-"+DateUtil.getYmdhmsFName();
	}
	
	/**
	 * @author linjunqin
	 * @Description: 复制项目的webroot目录到版本库中
	 * @param tags
	 * @date 2017-1-31 下午2:43:37
	 */
	private static void BulkWebRootPack(){
		try {
			logger.info("拷贝开始!");
			//拷贝
			FileUtil.copyDir(wr_path,project_deployName);
			logger.info("拷贝完成!");
			//压缩并打开文件目录
			openZipCompressPath();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author linjunqin
	 * @Description: 对符合的部署规则的文件移至打包库中，全量打包
	 * @param tags
	 * @date 2017-1-31 下午5:29:34
	 */
	private static void BulkPack(){
		logger.info("全量拷贝开始!");
	    List<HashMap<String, String>> filePathList = getWebRootFileList();
	    for(int i=0;i<filePathList.size();i++){
		   HashMap<String, String> hMap = filePathList.get(i);
		   String filePath = hMap.get("filePath");
		   String relaPath = filePath.substring(filePath.indexOf("WebRoot"),filePath.length()).replace("WebRoot", "");
		   copyLibFile(relaPath);
	    }
	    logger.info("全量拷贝完成!");
	    //压缩并打开文件目录
	    openZipCompressPath();
	}
	
	/**
	 * 
	 * @author linjunqin
	 * @Description: 对符合的部署规则的文件移至打包库中，增量更新打包
	 * @param tags
	 * @date 2017-2-1 上午11:51:33
	 */
	private static void increPack(){
		logger.info("增量拷贝开始!");
		//找出项目版本库中最新的版本号
		List<String> libList = getFolderList(JAVA_PROJECT_LIB);
		if(libList==null||libList.size()==0) {
			logger.info("未找到合适的版本!请选择别的方式打包方式或指定版本号");
			return;
		}
		//最新的版本号
		int versionNum = 0;
		String filePathPrefix = "";
		for(int i=0;i<libList.size();i++){
			String folderPath= libList.get(i);
			if(folderPath.lastIndexOf("_")>-1){
				//文件名名称
				String foldeerName = folderPath.substring(folderPath.lastIndexOf("_")+1, folderPath.length());
				if(versionNum<Integer.parseInt(foldeerName)){
					//更换版本号
					versionNum = Integer.parseInt(foldeerName);
					//文件名前缀路径
					filePathPrefix = folderPath.substring(0,folderPath.lastIndexOf("_")+1);
				}
			}
		}
		//增量更新的文件存放的文件夹路径与名称
		String increFolderNamePath = project_increFolderName+File.separator+DEPLOY_WEBROOT;
		//最新版本文件夹的路径
		String newFolderPath = filePathPrefix + versionNum+File.separator+DEPLOY_WEBROOT+File.separator+DEPLOY_WEBROOT;
		//符合条件的文件路径
	    List<HashMap<String, String>> filePathList = getWebRootFileList();
	    for(int i=0;i<filePathList.size();i++){
		   HashMap<String, String> hMap = filePathList.get(i);
		   String filePath = hMap.get("filePath");
		   String relaPath = filePath.substring(filePath.indexOf("WebRoot"),filePath.length()).replace("WebRoot", "");
		   //增量更新的文件路径
		   String increFilePath = increFolderNamePath +File.separator+relaPath;
		   //System.out.println(increFilePath);
		   //最新版本号文件路径
		   String newFilePath = newFolderPath+relaPath;
		   File file = new File(newFilePath);
		   if(!file.exists()) {//若版本库没有则直接复制
			   FileUtil.copyFile(filePath,increFilePath);
		   }else{
			   //开始计算文件的MD5
			   String libFileMD5 = Md5Util.getFileMD5(new File(newFilePath));
			   String projectFileMD5 = Md5Util.getFileMD5(new File(filePath));
			   //System.out.println(libFileMD5+"====="+projectFileMD5+"===="+projectFileMD5.length());
			   if(!libFileMD5.equals(projectFileMD5)){
				   FileUtil.copyFile(filePath,increFilePath);
			   }
		   }
	    }
	    logger.info("增量拷贝完成!");
	    //压缩并打开文件目录
	    String zipIncreFolderNamePath = project_increFolderName+".zip";
	    openZipCompressPath(project_increFolderName,zipIncreFolderNamePath);
	}
	
	/**
	 * @author linjunqin
	 * @Description: 拷贝文件
	 * @param tags
	 * @date 2017-1-31 下午5:03:57
	 */
	private static void copyLibFile(String relaPath){
		String oldFilePath = wr_path+relaPath;
		String newFilePath = project_deployName+relaPath;
		FileUtil.copyFile(oldFilePath,newFilePath);
	}
	
	/**
	 * @author linjunqin
	 * @Description 判断文件是否存在
	 * @param
	 * @date 2017-3-9 上午8:34:45
	 */
	private static void fileIsExists(String path){
		String newPath = path +"-"+StringUtil.flushLeft(3, String.valueOf(day_version))+".zip";
		File file = new File(newPath);
		if(file.exists()){
			day_version++;
			fileIsExists(path);
		}
		project_park_all_path  = newPath;
	}
	
	/**
	 * @author linjunqin
	 * @Description: 打包目录为zip格式,并打开所在的电脑文件目录
	 * @param tags
	 * @date 2017-2-1 上午11:36:51
	 */
	private static void openZipCompressPath(){
		openZipCompressPath(null,null);
	}
	private static void openZipCompressPath(String filePath,String zipFilePath){
		logger.info("打包zip开始!");
		if(filePath==null){
			filePath = project_park_path;
		}
		if(zipFilePath==null){
			fileIsExists(project_time_lib+File.separator+DEPLOY_NAME+"_"+DateUtil.getYmd());
			zipFilePath = project_park_all_path;
		}
		ZipCompressUtil.fileToZip(filePath,zipFilePath);
		logger.info("打包zip完成!");
		try {
			java.awt.Desktop.getDesktop().open(new File(project_time_lib));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @author linjunqin
	 * @Description: 读取过滤确定不需要的文件或文件夹
	 * @param tags
	 * @date 2017-1-31 下午4:02:46
	 */
	private static void readFiterFile(){
		String fiterContent = FileUtil.readTxt(ProjectUtil.getProjectPath()+"/src/deploy_filter_list.txt");
		//转成数组
		String[] lines = fiterContent.split("\n");
		for(int i=0;i<lines.length;i++){
			if(!lines[i].trim().equals("")){
				strList.add(lines[i]);
			}
		}
	}

	/**
	 * @author linjunqin
	 * @Description: 获取指定文件夹下的所有文件夹列表,并过滤掉不符合打包的条件
	 * @param tags
	 * @date 2017-1-31 下午2:09:16
	 */
	private static List<HashMap<String,String>> getWebRootFileList(){
		//读取需过滤的文件条件
		readFiterFile();
		//获取webroot路径
		String wr_path = ProjectUtil.getProjectWebRootPath();
		List<HashMap<String, String>> filePathList = getFileList(wr_path);
		for(int i=0;i<filePathList.size();i++){
			HashMap<String, String> listMap = filePathList.get(i);
			String filePath = listMap.get("filePath");
			//判断是否在过滤条件中
			if(strList!=null){
				for(int j=0;j<strList.size();j++){
					String lineFiterStr = strList.get(j);
					//System.out.println(filePath);
					//System.out.println(lineFiterStr);
					if(filePath.indexOf(lineFiterStr)>-1){
						filePathList.remove(i);
						i--;
					}
				}
			}
		}
		return filePathList;
	}
	
	/**
	 * @author linjunqin
	 * @Description: 递归调用
	 * @param tags
	 * @date 2017-1-31 下午1:04:51
	 */
	private static List<HashMap<String, String>> getFileList(String filePath){
		File dir = new File(filePath);
        File[] files = dir.listFiles(); //文件目录的所有文件或文件夹数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                //String fileName = files[i].getName();
                if(files[i].isDirectory()){//判断是文件夹还是文件 
                	getFileList(files[i].getAbsolutePath());
                }else{
                	HashMap<String,String> hMap = new HashMap<String,String>();
                	hMap.put("filePath",files[i].getAbsolutePath());
                	filePathList.add(hMap);
                }
            }
        }
        return filePathList;
	}

	/**
	 * @author linjunqin
	 * @Description: 找出指定目录下的文件夹名称
	 * @param tags
	 * @date 2017-2-1 下午12:03:56
	 */
	private static List<String> getFolderList(String filePath){
		File dir = new File(filePath);
        File[] files = dir.listFiles(); //文件目录的所有文件或文件夹数组
        List<String> folderList = new ArrayList<String>();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                //String fileName = files[i].getName();
                if(files[i].isDirectory()){//判断是文件夹还是文件 
                	folderList.add(files[i].getAbsolutePath());
                }
            }
        }
        return folderList;
	}
	
	/**
	 * @author linjunqin
	 * @Description: 获取lib下jar包的目录结构,用于建立过滤条件
	 * @param tags
	 * @date 2017-1-31 下午4:16:43
	 */
	private static void getLibCatalog(){
		//获取webroot路径
		String wr_path = ProjectUtil.getProjectWebRootPath();
		List<HashMap<String,String>> libList = getFileList(wr_path);
		for(int i=0;i<libList.size();i++){
			HashMap<String,String> libMap = libList.get(i);
			String filePath = libMap.get("filePath");
			if(filePath.indexOf(".jar")>-1){
				System.out.println(filePath);
			}
		}
	}
	
}
