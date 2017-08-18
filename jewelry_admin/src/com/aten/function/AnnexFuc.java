package com.aten.function;

import com.aten.model.orm.Annex;
import com.aten.service.AnnexService;

public class AnnexFuc extends SpringContextFuc{

	private static AnnexService annexService = (AnnexService) getContext().getBean("annexService");
	
	
	/**
	 * @author linjunqin
	 * @Description 上传图片到附件表
	 * @param
	 * @date 2017-7-1 下午5:33:55
	 */
	public static void insertImgAnnex(String up_file_name,String annex_url,
			 long file_size,String info_id){
		insertAnnex("0",up_file_name,annex_url,
				 file_size,"0",info_id,"0");
	}
	
	/**
	 * @author linjunqin
	 * @Description 上传附件到附件表
	 * @param
	 * @date 2017-7-1 下午5:27:59
	 */
	 public static void insertAnnex(String annex_type,String up_file_name,String annex_url,
			 long file_size,String back_id,String info_id,String back_type){
		 Annex annex = new Annex();
		 annex.setAnnex_type(annex_type);
		 annex.setAnnex_url(annex_url);
		 annex.setBack_id(back_id);
		 annex.setBack_type(back_type);
		 annex.setFile_size(String.valueOf(file_size));
		 annex.setInfo_id(info_id);
		 annex.setIs_del("0");
		 annex.setSort_no("0");
		 annex.setThe_cat("-");
		 annex.setRemark("-");
		 annex.setIn_date("now");
		 annex.setUp_file_name(up_file_name);
		 annexService.insert(annex);
	 }
	 
	 /**
	 * @author linjunqin
	 * @Description 根据图片信息标识获取单张图片路径
	 * @param
	 * @date 2017-7-1 下午7:40:12
	 */
	 public static String getSingleImageUrl(String info_id){
		 String oss_url = SysconfigFuc.getSysValue("oss_imgurl");
		 String annex_url = annexService.getAnnexByInfoId(info_id);
		 return oss_url + annex_url;
	 }
	 
	 
	 
	 
	
}
