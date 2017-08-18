package com.aten.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.aten.function.SysconfigFuc;
import com.communal.constants.Constant;
import com.communal.util.DateUtil;
import com.communal.util.ImageUtil;
import com.communal.util.StringUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("admin/upload")
public class UploadController extends BaseController {

	private static final Logger logger = Logger.getLogger(UploadController.class);

	/**
	 * @author linjunqin
	 * @Description 初始方法
	 * @param
	 * @date 2017-1-9 下午1:20:20
	 */
	@ModelAttribute
	public void populateModel(HttpServletRequest request, Model model) {

	}

	/**
	 * @author linjunqin
	 * @Description 上传图片的方法
	 * @param
	 * @date 2017-1-9 下午1:20:54
	 */
	@RequestMapping(value = { "/image" }, method = RequestMethod.POST)
	public void uploadImage(HttpServletRequest request, HttpServletResponse response) {
		logger.info("OSS图片上传");
		// 上传附件
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("myFile");
		// 获取服务器地址
		ServletContext sc = request.getSession().getServletContext();
		// 服务器的存放路径
		String imgPath = "/UploadUtil/image/" + DateUtil.getYmd() + "/";
		// 附件存放服务器路径
		String dir = sc.getRealPath(imgPath);
		// 如果文件夹不存在，则创建文件夹
		if (!new File(dir).exists()) {
			new File(dir).mkdirs();
		}
		// 获取上传的图片文件名
		String fileName = file.getOriginalFilename();
		// 防止文件被覆盖，以纳秒生成图片名
		Long nanoTime = System.nanoTime();
		// 获取扩展名
		String _extName = fileName.substring(fileName.indexOf("."));
		fileName = nanoTime + _extName;
		// 上传后的图片路径名
		String uploadPathName = imgPath + fileName;
		// String uploadPath = request.getContextPath() + "/UploadUtil/image/"+
		// fileName;
		// 存储格式为：/项目名/UploadUtil/image/43038800303864.jpg
		String responseStr = uploadPathName;
		try {
			FileUtils.writeByteArrayToFile(new File(dir, fileName), file.getBytes());// 服务器中生成文件
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// responseStr = "上传失败";
		}
		outPrint(response, responseStr);
	}

	// 目前只有"cn-hangzhou"这个region可用, 不要使用填写其他region的值
	public static final String REGION_CN_HANGZHOU = "cn-hangzhou";
	// 当前 STS API 版本
	public static final String STS_API_VERSION = "2015-04-01";

	static AssumeRoleResponse assumeRole(String accessKeyId, String accessKeySecret, String roleArn,
			String roleSessionName, String policy, ProtocolType protocolType) throws ClientException {
		try {
			// 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
			IClientProfile profile = DefaultProfile.getProfile(REGION_CN_HANGZHOU, accessKeyId, accessKeySecret);
			DefaultAcsClient client = new DefaultAcsClient(profile);

			// 创建一个 AssumeRoleRequest 并设置请求参数
			final AssumeRoleRequest request = new AssumeRoleRequest();
			request.setVersion(STS_API_VERSION);
			request.setMethod(MethodType.POST);
			request.setProtocol(protocolType);

			request.setRoleArn(roleArn);
			request.setRoleSessionName(roleSessionName);
			request.setPolicy(policy);

			// 发起请求，并得到response
			final AssumeRoleResponse response = client.getAcsResponse(request);

			return response;
		} catch (ClientException e) {
			throw e;
		}
	}

	/**
	 * @author linjunqin
	 * @Description 上传图片至oss
	 * @param
	 * @date 2017-5-24 下午5:45:20
	 */
	@RequestMapping(value = { "/oss" })
	public void uploadOss(HttpServletRequest request, HttpServletResponse response) {
		String resultImgUrl = SysconfigFuc.getSysValue("oss_imgurl");// "http://yszb-dev.oss-cn-shanghai.aliyuncs.com/";
		String endpoint = SysconfigFuc.getSysValue("oss_endpoint");// "oss-cn-shanghai.aliyuncs.com";
		//String endpoint = "yszb-dev.vpc100-oss-cn-shanghai.aliyuncs.com";
		String accessKeyId = SysconfigFuc.getSysValue("oss_accesskeyid");// "LTAIKllzhsEUg7Hh";
		String accessKeySecret = SysconfigFuc.getSysValue("oss_accesskeysecret");// "sKBPizJEyTCO6IyqvfJNQ3Rg5OAm5T";
		String bucket = SysconfigFuc.getSysValue("oss_bucket");// "yszb-dev";
		// logger.info("Started");
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		/*if (!ossClient.doesBucketExist(bucket)) {
			ossClient.createBucket(bucket);
		}*/
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile mpfile = multipartRequest.getFile("myFile");// 上传附件
		ObjectMetadata objectMeta = new ObjectMetadata();// oss属性设置
		objectMeta.setContentLength(mpfile.getSize());// 标记长度
		objectMeta.setContentType("image/jpeg");// 可以在metadata中标记文件类型
		try {
			String fileName = mpfile.getOriginalFilename();// 获取上传的图片文件名
			Long nanoTime = System.nanoTime();// 防止文件被覆盖，以纳秒生成图片名
			String _extName = fileName.substring(fileName.indexOf("."));// 获取扩展名
			fileName = nanoTime + _extName;
			fileName = DateUtil.getYmd() + "/" + fileName;
			ossClient.putObject(bucket, fileName, mpfile.getInputStream(), objectMeta);
			resultImgUrl += fileName;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ossClient.shutdown();
		}
		outPrint(response, resultImgUrl);
	}

	/**
	 * 通过文件名判断并获取OSS服务文件上传时文件的contentType
	 * 
	 * @param fileName
	 *            文件名
	 * @return 文件的contentType
	 */
	public static String getContentType(String fileName) {
		// 文件的后缀名
		String fileExtension = fileName.substring(fileName.lastIndexOf("."));
		if (".bmp".equalsIgnoreCase(fileExtension)) {
			return "image/bmp";
		}
		if (".gif".equalsIgnoreCase(fileExtension)) {
			return "image/gif";
		}
		if (".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension)
				|| ".png".equalsIgnoreCase(fileExtension)) {
			return "image/jpeg";
		}
		if (".html".equalsIgnoreCase(fileExtension)) {
			return "text/html";
		}
		if (".txt".equalsIgnoreCase(fileExtension)) {
			return "text/plain";
		}
		if (".vsd".equalsIgnoreCase(fileExtension)) {
			return "application/vnd.visio";
		}
		if (".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
			return "application/vnd.ms-powerpoint";
		}
		if (".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
			return "application/msword";
		}
		if (".xml".equalsIgnoreCase(fileExtension)) {
			return "text/xml";
		}
		// 默认返回类型
		return "image/jpeg";
	}

	/**
	 * @author linjunqin
	 * @Description 根据base64图片串转成图片
	 * @param
	 * @throws IOException
	 * @date 2017-3-6 下午5:55:10
	 */
	@RequestMapping(value = { "/uploadBase64Image" }, method = RequestMethod.POST)
	public void uploadBase64Image(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String portrait = request.getParameter("portrait");
		if (StringUtil.isEmpty(portrait)) {
			outPrint(response, null);
			return;
		}
		// 不为空时则转成图片
		String portraitPath = ImageUtil.uploadImageByBase64(request, portrait);
		// return portraitPath;
		outPrint(response, portraitPath);
		// response.getWriter().print("ok3333333333333333333333");
		// HashMap<String,String> map = new HashMap<String,String>();
	}

}
