package com.communal.util;

import java.io.File;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;


public class ZipCompressUtil {
	
    /**
	 * @author linjunqin
	 * @Description: 执行压缩操作 ,需要被压缩的文件/文件夹 
	 * @param tags
	 * @date 2017-1-31 下午3:40:29
	 */
    public static void fileToZip(String sourcePath,String zipCreatePath) {    
        File srcdir = new File(sourcePath);    
        if (!srcdir.exists()){  
            throw new RuntimeException(sourcePath + "不存在！");    
        }   
            
        Project prj = new Project();    
        Zip zip = new Zip();    
        zip.setProject(prj); 
        zip.setDestFile(new File(zipCreatePath));    
        FileSet fileSet = new FileSet();    
        fileSet.setProject(prj);    
        fileSet.setDir(srcdir);    
        zip.addFileset(fileSet);    
        zip.execute();    
    }    
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
