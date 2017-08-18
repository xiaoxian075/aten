package com.communal.util;

import com.communal.util.ProjectUtil;


public class ProjectUtil {
	
	 public static void main(String[] args) {
		 
		 System.out.println(getProjectPath());
		 
	 }
	 
	 /**
	 * @author linjunqin
	 * @Description: 获取项目的名称
	 * @param tags
	 * @date 2017-1-31 下午1:44:22
	 */
	 public static String getProjectName(){
		 String projectName = getProjectPath();
		 projectName = projectName.substring(projectName.lastIndexOf("\\")+1, projectName.length());
		 return projectName;
	 }
	 
	 /**
	 * @author linjunqin
	 * @Description: TODO
	 * @param tags
	 * @date 2017-1-31 下午1:30:52
	 */
	 public static String getProjectPath(){
		 String projectPath=System.getProperty("user.dir"); 
		 return projectPath;
	 }
	 
	 /**
	 * @author linjunqin
	 * @Description: 获取项目的webroot目录
	 * @param tags
	 * @date 2017-1-31 下午1:37:28
	 */
	 public static String getProjectWebRootPath(){
		String webRootPath =getDeployClassPath().replace("/WEB-INF/classes/", "");
		return webRootPath;
	 }
	 
	 /**
	 * @author linjunqin
	 * @Description: 获取项目部署的类文件目录
	 * @param tags
	 * @date 2017-1-31 下午1:53:04
	 */
	 public static String getDeployClassPath(){
		 String deployClassPath = ProjectUtil.class.getClassLoader().getResource("").getPath();
		 return deployClassPath;
	 }
	 
}
