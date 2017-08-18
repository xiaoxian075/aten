package com.aten.function;

import java.util.HashMap;
import java.util.List;
import com.aten.model.orm.Sysmenu;
import com.aten.service.SysmenuService;
import com.communal.constants.Constant;
import com.communal.util.StringUtil;


public class SysmenuFuc extends SpringContextFuc{
	
	private static SysmenuService sysmenuService = (SysmenuService) getContext().getBean("sysmenuService");

	/**
	 * @author linjunqin
	 * @Description 鏍规嵁鏉′欢鑾峰彇鑿滃崟鍒楄〃
	 * @param
	 * @date 2017-1-3 涓嬪崍3:08:12
	 */
	public static List<Sysmenu> getMenuList(String parent_menu_id,String level,String menu_url){
		return getMenuList(parent_menu_id,level,menu_url,null);
	}
	public static List<Sysmenu> getMenuList(String parent_menu_id,String level,String menu_url,String plat_role){
		HashMap<String,Object> paraMap = new HashMap<String, Object>();
		if(parent_menu_id!=null && !parent_menu_id.equals("")){
			paraMap.put("parent_menu_id", parent_menu_id);
		}
		if(level!=null && !level.equals("")){
			paraMap.put("menu_level", level);
		}
		if(menu_url!=null && !menu_url.equals("")){
			paraMap.put("menu_url", menu_url);
		}
		if(plat_role!=null && !plat_role.equals("")){
			paraMap.put("plat_role", plat_role);
		}
		
		List<Sysmenu> sysmenuList  = sysmenuService.getSysmenuListByManageState(paraMap);
		System.out.println(sysmenuList);
		return sysmenuList;
	}
	
	/**
	 * @author linjunqin
	 * @Description 閫氳繃鐖惰彍鍗曪紝绛夌骇杩斿洖鏁版嵁
	 * @param
	 * @date 2017-1-3 涓嬪崍3:42:19
	 */
	public static List<Sysmenu> getMenuList(String parent_menu_id,String level){
		return getMenuList(parent_menu_id,level,null);
	}
	
	/**
	 * @author linjunqin
	 * @Description 鏍规嵁璇锋眰鐨勫湴鍧�杩斿洖鑿滃崟涓�
	 * @param
	 * @date 2017-1-3 涓嬪崍3:42:46
	 */
	public static String getMenuList(String menu_url){
		List<Sysmenu> sysmenuList = getMenuList(null,null,menu_url);
		String level_menu ="";
		if(sysmenuList!=null && sysmenuList.size()>0){
			Sysmenu sysmenu = sysmenuList.get(0);
			//褰撴槸绗笁绾ф椂杩斿洖鏁版嵁
			if(sysmenu.getMenu_level().equals("3")){
				level_menu = sysmenu.getLevel_menu();
			}
		}
		return level_menu;
	}
	
	/**
	 * @author linjunqin
	 * @Description 鏍规嵁鎵�灞炲钩鍙拌鑹茬被鍨嬶紝绾у埆锛屼笂涓�绾D鑾峰彇鏁版嵁
	 * @param
	 * @date 2017-5-16 涓婂崍11:12:39
	 */
	public static List<Sysmenu> getMenuListByRole(String parent_menu_id,String level, String plat_role){
		return getMenuList(parent_menu_id,level,null,plat_role);
	}
	
	/**
	 * @author linjunqin
	 * @Description 鏍规嵁鑿滃崟URL鍜屾墍灞炶鑹茬紪鐮佽幏鍙栦覆ID
	 * @param
	 * @date 2017-5-15 涓嬪崍5:46:00
	 */
	public static String getMenuPlatCodeList(String menu_url,String plat_role){
		HashMap<String,Object> paraMap = new HashMap<String, Object>();
		if(menu_url!=null && !menu_url.equals("")){
			paraMap.put("menu_url_vague", menu_url+"/");
		}
		if(plat_role!=null && !plat_role.equals("")){
			paraMap.put("plat_role", plat_role);
		}
		List<Sysmenu> sysmenuList  = sysmenuService.getSysmenuListByManageState(paraMap);
		String level_menu ="";
		if(sysmenuList!=null && sysmenuList.size()>0){
			Sysmenu sysmenu = sysmenuList.get(0);
			//褰撴槸绗笁绾ф椂杩斿洖鏁版嵁
			if(sysmenu.getMenu_level().equals("3")){
				level_menu = sysmenu.getLevel_menu();
			}
		}
		return level_menu;
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 鑾峰彇璇锋眰鐨勫湴鍧�杩斿洖涓�绾ц彍鍗曠殑ID
	 * @param
	 * @date 2017-5-9 涓婂崍9:43:12
	 */
	public static String getFirstMenuID(String menu_url,String plat_code){
		List<Sysmenu> sysmenuList = getMenuList(null,null,menu_url);
		String menu_id ="";
		if(sysmenuList!=null && sysmenuList.size()>0){
			Sysmenu sysmenu = sysmenuList.get(0);
			//鑾峰彇鑿滃崟涓�
			String level_menu = sysmenu.getLevel_menu();
			if(!StringUtil.isEmpty(level_menu)){
				menu_id = level_menu.replace(Constant.UP_AREA_ID, "");//鍘婚櫎棣栫骇鑿滃崟
				//鑾峰彇绗竴绾ц彍鍗旾D
				if(menu_id.indexOf(",")>0){
					menu_id = menu_id.substring(0, menu_id.indexOf(","));
				}
			}
			
		}
		return menu_id;
	}

	/**
	 * @author linjunqin
	 * @Description 鑾峰彇绗竴绾ц彍鍗�
	 * @param
	 * @date 2017-1-3 涓嬪崍3:11:28
	 */
	public static List<Sysmenu> getFirstMenuList(String level){
		return getMenuList(null,level,null,null);
	}
	
	/**
	 * @author linjunqin
	 * @Description 鏍规嵁绾у埆璺熸墍灞炶鑹茬被鍨嬫悳绱�
	 * @param
	 * @date 2017-1-3 涓嬪崍3:11:28
	 */
	public static List<Sysmenu> getFirstMenuList(String level,String plat_role){
		return getMenuList(null,level,null,plat_role);
	}
	
	/**
	 * @author linjunqin
	 * @Description 鏍规嵁鑿滃崟ID涓茶繑鍥炶彍鍗曞悕绉颁覆,浠�,鍙烽�楀紑
	 * @param
	 * @date 2017-2-16 涓婂崍11:29:44
	 */
	public static String getMenuNameStr(String menu_id_str){
		return getMenuNameStr(menu_id_str,",");
	}
	
	/**
	 * @author linjunqin
	 * @Description 鏍规嵁鑿滃崟ID涓茶繑鍥炶彍鍗曞悕绉颁覆,浠ュ垎闅旂鍙烽�楀紑
	 * @param
	 * @date 2017-2-16 涓婂崍11:29:44
	 */
	public static String getMenuNameStr(String menu_id_str,String pos){
		String menu_name_str="";
		if(StringUtil.isEmpty(menu_id_str)) return null;
		//鏄惁鏄彍鍗曚覆
		if(menu_id_str.indexOf(",")>-1){
			String[] menu_id = menu_id_str.split(",");
			for(int i=0;i<menu_id.length;i++){
				menu_name_str+=getMenuName(menu_id[i]);
				if(i!=menu_id.length-1){
					menu_name_str+=pos;
				}
			}
		}else{
			menu_name_str+=getMenuName(menu_id_str);
		}
		return menu_name_str;
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 鏍规嵁鑿滃崟ID鑾峰彇鑿滃崟鍚嶇О
	 * @param
	 * @date 2017-2-16 涓婂崍11:27:49
	 */
	public static String getMenuName(String menu_id){
		Sysmenu sysmenu =  sysmenuService.get(menu_id);
		if(sysmenu==null) return "";
		return sysmenu.getMenu_name();
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		getMenuList(null,null);
	}
	
}
