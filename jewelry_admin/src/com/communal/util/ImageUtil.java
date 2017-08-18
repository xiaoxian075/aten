package com.communal.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.aten.model.bean.ImgListBean;
import com.communal.constants.Constant;
import com.communal.util.Base64Util;
import com.communal.util.DateUtil;
import com.communal.util.StringUtil;

/**
 * @author linjunqin
 * @Description 图片处理
 * @param
 * @date 2017-2-15 下午3:33:38
 */
public class ImageUtil {

	/**
	 * @author linjunqin
	 * @Description 图片处理
	 * @param
	 * @date 2017-2-15 下午3:33:51
	 */
	public static List<ImgListBean> showImageList(String images_path){
		List<ImgListBean> imgList = new  ArrayList<ImgListBean>();
		if(images_path==null) images_path="";
		String[] image_path = images_path.split(",");
		//需要保存6张图片
		for(int i=0;i<6;i++){
			ImgListBean  ilb = new ImgListBean();
			if(i<image_path.length && !image_path[i].trim().equals("")){
				ilb.setImg_url(image_path[i]);
			}else{
				ilb.setImg_url("/include/admin/image/no_picture.png");
			}
			imgList.add(ilb);
		}
		return imgList;
	}
	
	/**
	 * @author linjunqin
	 * @Description 图片处理加上域名访问,如已经是真实地址，则直接返回
	 * @param
	 * @date 2017-2-22 下午4:26:38
	 */
	public static String getRealImgPath(String img_path){
		if(StringUtil.isEmpty(img_path)) return "";
		//有http图片则返回
		if(img_path.indexOf("http://")>-1) return img_path;
		String new_more_img_path="";
		if(img_path.indexOf(",")>0){
			String[] field_values = img_path.split(",");
			int len = field_values.length;
			for(int i=0;i<len;i++){
				new_more_img_path+=Constant.IMG_URL+field_values[i];
				new_more_img_path+=i!=len-1 ? ",":"";
			}
			img_path = new_more_img_path;
		}else{
			img_path = Constant.IMG_URL+img_path;
		}
		return img_path;
	}

	/**
	 * @author linjunqin
	 * @Description 图片串转成list列表
	 * @param
	 * @date 2017-3-1 下午2:40:50
	 */
	public static List<String> getImgListByImgPath(String img_path){
		if(StringUtil.isEmpty(img_path)) return new ArrayList<String>();
		List<String> imgList = new ArrayList<String>();
		if(img_path.indexOf(",")>0){
			String[] field_values = img_path.split(",");
			int len = field_values.length;
			for(int i=0;i<len;i++){
				imgList.add(Constant.IMG_URL+field_values[i]);
			}
		}else{
			imgList.add(Constant.IMG_URL+img_path);
		}
		return imgList;
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 根据base64图片串上传到汽车图片服务器
	 * @param
	 * @date 2017-3-6 下午3:40:58
	 */
	public static String uploadImageByBase64(HttpServletRequest request,String imgBase64){
		/*// 获取服务器地址
		ServletContext sc = request.getSession().getServletContext();
		//服务器的存放路径
		String imgPath = "/UploadUtil/image/"+DateUtil.getYmd()+"/";
		// 附件存放服务器路径
		String dir = sc.getRealPath(imgPath);
		// 防止文件被覆盖，以纳秒生成图片名
		Long nanoTime = System.nanoTime();
		// 如果文件夹不存在，则创建文件夹
		if (!new File(dir).exists()) {
			new File(dir).mkdirs();
		}
		//生成图片
		String fileName = nanoTime+".jpg";
		String save_real_path = dir+File.separator + fileName;
		Base64Util.imgStrToImage(save_real_path, imgBase64);
		return imgPath+fileName;*/
		return null;
	}
	
}
