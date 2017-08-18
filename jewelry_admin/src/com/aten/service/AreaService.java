package com.aten.service;

import java.util.HashMap;
import java.util.List;

import com.aten.model.AreaShort;
import com.aten.model.orm.Area;

public  interface AreaService extends CommonService<Area, String>{

	List<Area> getAreaListByIsShow(HashMap<String,Object> paraMap);
	
	/**
	 * @author linjunqin
	 * @Description 获取正常地区状态的列表
	 * @param
	 * @date 2017-7-3 下午2:57:02
	 */
	public List<Area> getAreaListByIsState(HashMap<String,Object> paraMap);
	
	/**
	 * @author linjunqin
	 * @Description 根据父ID获取底下的子列表
	 * @param
	 * @date 2017-7-3 下午2:58:27
	 */
	public List<Area> getChildAreaList(String parent_area_id);
	
	
	
	List<Area> getSon(String pId);
    List<Area> getParent(String area_id);
	List<Area> autoLimitParent(String area_id);
    List<Area> getAllArea();
    List<AreaShort> getParentArea(String parent_area_id);
    List<Area> getCityArea();	//搜索省市级的所有城市(level:1/2)
    
    
    
}

