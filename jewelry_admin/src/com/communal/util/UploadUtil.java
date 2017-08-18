package com.communal.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aten.function.SysconfigFuc;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Created by 陈熠
 * 2017/7/29.
 */
public class UploadUtil {
    public static String  uploadImage(String fileName, InputStream inputStream) {
        String resultImgUrl = SysconfigFuc.getSysValue("oss_imgurl");//"http://yszb-dev.oss-cn-shanghai.aliyuncs.com/";
        String endpoint = SysconfigFuc.getSysValue("oss_endpoint");//"oss-cn-shanghai.aliyuncs.com";
        String accessKeyId = SysconfigFuc.getSysValue("oss_accesskeyid");//"LTAIKllzhsEUg7Hh";
        String accessKeySecret = SysconfigFuc.getSysValue("oss_accesskeysecret");//"sKBPizJEyTCO6IyqvfJNQ3Rg5OAm5T";
        String bucket = SysconfigFuc.getSysValue("oss_bucket");//"yszb-dev";
        //logger.info("Started");

        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        if (!ossClient.doesBucketExist(bucket)) {
            ossClient.createBucket(bucket);
        }

        ObjectMetadata objectMeta = new ObjectMetadata();//oss属性设置
        //objectMeta.setContentLength(inputStream.getSize());//标记长度
        objectMeta.setContentType("image/jpeg");// 可以在metadata中标记文件类型
        try {

            //获取上传的图片文件名
            Long nanoTime = System.nanoTime();// 防止文件被覆盖，以纳秒生成图片名
            String _extName = fileName.substring(fileName.indexOf("."));//获取扩展名
            fileName = nanoTime + _extName;
            fileName = DateUtil.getYmd()+"/"+fileName;
            ossClient.putObject(bucket,fileName, inputStream,objectMeta);
            resultImgUrl+=fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            ossClient.shutdown();
        }
        return resultImgUrl;
    }
}
