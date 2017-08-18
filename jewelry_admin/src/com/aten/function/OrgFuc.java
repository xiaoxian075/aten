package com.aten.function;

import com.aten.model.orm.Area;
import com.aten.model.orm.Organize;
import com.aten.service.AreaService;
import com.aten.service.OrganizeService;
import com.communal.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class OrgFuc extends SpringContextFuc{
	
	private static OrganizeService organizeService = (OrganizeService) getContext().getBean("organizeService");


	
	/**
	 * @author linjunqin
	 * @Description 根据菜单ID获取菜单名称
	 * @param
	 * @date 2017-2-16 上午11:27:49
	 */
	public static String getParentName(String area_id){
		Organize organize =  organizeService.get(area_id);
		if(organize==null) return "";
		return organize.getOrg_name();
	}
	

	
}
