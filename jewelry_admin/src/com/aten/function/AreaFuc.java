package com.aten.function;

import java.util.HashMap;
import java.util.List;
import com.aten.model.orm.Area;
import com.aten.service.AreaService;
import com.communal.util.StringUtil;


public class AreaFuc extends SpringContextFuc{
	
	private static AreaService areaService = (AreaService) getContext().getBean("areaService");
	/**
	 * @author linjunqin
	 * @Description 根据条件获取地区列表
	 * @param
	 * @date 2017-1-4 上午10:46:01
	 */
	public static List<Area> getAreaList(){
		HashMap<String,Object> paraMap = new HashMap<String, Object>();
		paraMap.put("area_level", "4");
		List<Area> areaList  =areaService.getList(paraMap);
		System.out.println(areaList.size());
		for(int i=0;i<areaList.size();i++){
			
		}
		return areaList;
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 根据分类ID中转成分类名称串
	 * @param
	 * @date 2017-2-16 下午11:51:01
	 */
	public static String getAreaNameStr(String area_id_str){
		String area_name_str="";
		if(StringUtil.isEmpty(area_id_str)) return area_name_str;
		if(area_id_str.indexOf(",")>-1){
			String[] menu_id = area_id_str.split(",");
			for(int i=0;i<menu_id.length;i++){
				area_name_str+=getAreaName(menu_id[i]);
				if(i!=menu_id.length-1){
					area_name_str+=",";
				}
			}
		}else{
			area_name_str+=getAreaName(area_id_str);
		}
		return area_name_str;
	}
	
	/**
	 * @author linjunqin
	 * @Description 根据菜单ID获取菜单名称
	 * @param
	 * @date 2017-2-16 上午11:27:49
	 */
	public static String getAreaName(String area_id){
		Area area =  areaService.get(area_id);
		if(area==null) return "";
		return area.getArea_name();
	}

//	public static void getAreaData(){
//		HashMap<String,Object> paraMap = new HashMap<String, Object>();
//		List<Tarea> tareaList  =tareaService.getList(paraMap);
//		for (Tarea tarea:tareaList){
//				Area area=new Area();
//				//id 要转10位
//				String tarea_id=tarea.getId();
//				//判断是否id已存在
//			//id长度
//				int tarea_id_length=tarea_id.length();
//				int diff_length=10-tarea_id_length;
//				for (int i=0;i<diff_length;i++){
//					tarea_id="1"+tarea_id;
//				}
//			Area area1 = areaService.get(tarea_id);
//			if (area1!=null){
//				area1.setXz_code(tarea.getId());
//				areaService.update(area1);
//				continue;
//			}
//				//设置id
//				area.setArea_id(tarea_id);
//			    //设置地区名称
//				area.setArea_name(tarea.getArea_name().trim());
//				//设置拼音
//				String pinyin=ChineseToEnglishUtil.getPingYin(tarea.getArea_name().trim()).trim();
//				area.setEn_name(pinyin);
//				//设置拼音首字母
//				System.out.println("-----------------------"+tarea_id+"---gg----" +pinyin.substring(0,1).toUpperCase() +"-----------------------------");
//			     area.setWord_index(pinyin.substring(0, 1).toUpperCase());
//			     //设置上级id
//			String parent_id=tarea.getParent_id();
//			//id长度
//			int parent_id_length=parent_id.length();
//			int parent_diff_length=10-parent_id_length;
//			for (int i=0;i<parent_diff_length;i++){
//				parent_id="1"+parent_id;
//			}
//				 area.setParent_area_id(parent_id);
//				 //设置排序
//				area.setSort_no("0");
//				//设置 area_level;//lev+1
//			     area.setLevel_area("0");
//			     area.setArea_level(tarea.getLev());
//			     area.setIs_city("0");
//			     area.setState("1");
//			     areaService.insert(area);
//		}
//	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*Area areaList  =areaService.get("1111111111");
		areaList.setLevel_area("9999999999,1111111111");
		areaService.update(areaList);*/
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		//System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		 getAreaList();
//		getAreaData();
	}
	
}
