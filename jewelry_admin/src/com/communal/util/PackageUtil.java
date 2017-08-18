package com.communal.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PackageUtil {
	
	public static void main(String[] args) {  
        String packageName = "com.aten.controller";  
  
        List<String> classNames = getClassName(packageName);  
        for (String className : classNames) {  
            System.out.println(className);  
        }  
    }  
  
	/**
	 * @author linjunqin
	 * @Description 通过包名获取底下的所有类
	 * @param
	 * @date 2017-6-12 下午2:27:13
	 */
    public static List<String> getClassName(String packageName) {  
        String filePath = ClassLoader.getSystemResource("").getPath() + packageName.replace(".", "\\");  
        List<String> fileNames = getClassName(filePath, null);  
        return fileNames;  
    }  
  
    /**
	 * @author linjunqin
	 * @Description 通过包名获取底下的所有类
	 * @param
	 * @date 2017-6-12 下午2:27:13
	 */
    private static List<String> getClassName(String filePath, List<String> className) {  
        List<String> myClassName = new ArrayList<String>();  
        File file = new File(filePath);  
        File[] childFiles = file.listFiles();  
        for (File childFile : childFiles) {  
            if (childFile.isDirectory()) {  
                myClassName.addAll(getClassName(childFile.getPath(), myClassName));  
            } else {  
                String childFilePath = childFile.getPath();  
                childFilePath = childFilePath.substring(childFilePath.indexOf("\\classes") + 9, childFilePath.lastIndexOf("."));  
                childFilePath = childFilePath.replace("\\", ".");  
                myClassName.add(childFilePath);  
            }  
        }  
        return myClassName;  
    }  
}
