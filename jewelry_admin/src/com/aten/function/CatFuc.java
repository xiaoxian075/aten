package com.aten.function;

import java.util.HashMap;
import java.util.List;

import com.aten.model.orm.Brand;
import com.aten.model.orm.Cat;
import com.aten.service.BrandService;
import com.aten.service.CatService;
import com.communal.util.ChineseToEnglishUtil;
import com.communal.util.StringUtil;


public class CatFuc extends SpringContextFuc{
	
	private static CatService catService = (CatService) getContext().getBean("catService");

	private static BrandService brandService = (BrandService) getContext().getBean("brandService");
	
	public static void main(String[] args){
//		System.out.println(112);
//		List<Brand> brandList = brandService.getList(null);
//		for(int i=0;i<brandList.size();i++){
//			Brand brand = brandList.get(i);
//			brand.setWord_index(ChineseToEnglishUtil.getPinYinHeadChar(brand.getBrand_name()));
//			//brand.setAdd_time(null);
//			brandService.update(brand);
//		}
//		setLevel("1111");

	}
  public  static  void setCatLevel() {
	  HashMap<String, Object> paraMap = new HashMap<String, Object>();
	  paraMap.put("module", "goods");
		List<Cat> catgoodsList=catService.getList(paraMap);
		for(Cat catgoods:catgoodsList){

		}
  }
	/**
	 * @author linjunqin
	 * @Description 根据分类ID中转成分类名称串
	 * @param
	 * @date 2017-2-16 下午11:51:01
	 */
  	public static String getCatNameStr(String cat_id_str,String pos){
		return getCatNameStr(cat_id_str,0," > ");
	}
	public static String getCatNameStr(String cat_id_str){
		return getCatNameStr(cat_id_str,0,",");
	}
	public static String getCatNameStr(String cat_id_str,int startPos,String pos){
		String cat_name_str="";
		if(StringUtil.isEmpty(cat_id_str)) return cat_name_str;
		if(startPos!=0){
			if(cat_id_str.length()>startPos){
				cat_id_str = cat_id_str.substring(startPos, cat_id_str.length());
			}
		}
		if(cat_id_str.indexOf(",")>-1){
			String[] menu_id = cat_id_str.split(",");
			for(int i=0;i<menu_id.length;i++){
				cat_name_str+=getCatName(menu_id[i]);
				if(i!=menu_id.length-1){
					cat_name_str+=",";
				}
			}
		}else{
			cat_name_str+=getCatName(cat_id_str);
		}
		cat_name_str = cat_name_str.replace(",", pos);
		return cat_name_str;
	}
	
	/**
	 * @author linjunqin
	 * @Description 根据菜单ID获取菜单名称
	 * @param
	 * @date 2017-2-16 上午11:27:49
	 */
	public static String getCatName(String cat_id){
		Cat cat =  catService.get(cat_id);
		if(cat==null) return "";
		return cat.getCat_name();
	}
	/**
	 * @author chenyi
	 * @Description 截取上级id串
	 * @param subStr：截取的标识
	 * @param level_cat ：需要截取的串
	 * @date 2017/7/1 19:32
	 **/
	public static String getParentIdStr(String subStr,String level_cat){
		int index=level_cat.indexOf(subStr);
		String parentIdStr=level_cat;
		if (index!=-1){
			if(index+11>=level_cat.length()-10){
				parentIdStr=subStr;
			}else{
				parentIdStr=level_cat.substring(index+11,level_cat.length()-11);
			}
		}else{
			parentIdStr="";
		}
		return parentIdStr;
	}

}
