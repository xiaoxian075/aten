package com.core.util;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 多文件上传
 *
 * @author liangrui
 */
public class FileUploadUtil {
    /**
     * 获取上传的文件信息
     *
     * @param request
     * @return
     */
    public static List<MultipartFile> getUploadFile(HttpServletRequest request) {
        //上传文件的解析器
        CommonsMultipartResolver mutiparRe = new CommonsMultipartResolver();
        //保存文件信息
        List<MultipartFile> fileList = new ArrayList<MultipartFile>();
        if (mutiparRe.isMultipart(request)) {//如果是文件类型的请求
            MultipartHttpServletRequest mhr = (MultipartHttpServletRequest) request;
            //跌带文件名称
            Iterator<String> it = mhr.getFileNames();
            while (it.hasNext()) {
                //获取一个文件
                MultipartFile mf = mhr.getFile(it.next());
                fileList.add(mf);
            }
        }
        return fileList;

    }
    /**
     * 上传验证,并初始化文件目录
     *
     * @param
     */
    public static String upLoadFile(MultipartFile file, String path) throws IOException {
        Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date());
        Format fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String filename = fmt.format(new Date());
        String oldname = file.getOriginalFilename();
        String ext = oldname.substring(oldname.indexOf("."));
        if (ext.equals(".MOV")||ext.equals(".mov")){
            ext=".mp4";
        }
        String filePath = filename + ext;
        // 文件写入磁盘
        File file1 = new File(path);
        if(file1.mkdirs()){
            Log.out("FileUpLoadUtil","创建文件夹"+path);
        }
        SaveFileFromInputStream(file.getInputStream(),path,filePath);
        return filePath;
    }
    public static void SaveFileFromInputStream(InputStream stream, String path, String filename) throws IOException
    {
        FileOutputStream fs=new FileOutputStream( path + "/"+ filename);
        byte[] buffer =new byte[1024*1024];
        int bytesum = 0;
        int byteread = 0;
        while ((byteread=stream.read(buffer))!=-1)
        {
            bytesum+=byteread;
            fs.write(buffer,0,byteread);
            fs.flush();
        }
        fs.close();
        stream.close();
    }
    /**
     * 删除图片
     */
    public static void delFile(String path){
        File file = new File(path);
        if(file != null){
            if(file.exists()){
                file.delete();
            }
        }
    }
}