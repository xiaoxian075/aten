package com.aten.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aten.model.AreaShort;
import com.aten.model.orm.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.AreaDao;
import com.aten.service.AreaService;

@Service("areaService")
public class AreaServiceImpl extends BaseCommonServiceImpl<Area,String> implements AreaService{
	
	private AreaDao areaDao;

	@Autowired
	public AreaServiceImpl(AreaDao areaDao) {
		super(areaDao);
		this.areaDao = areaDao;
	}

	public List<Area> getAreaListByIsShow(HashMap<String, Object> paraMap) {
		paraMap.put("state", "1");
		return areaDao.getList(paraMap);
	}

	public List<Area> getSon(String pId) {
		return areaDao.getSon(pId);
	}

	@Override
	public List<Area> getParent(String area_id) {
		return areaDao.getParent(area_id);
	}

	@Override
	public List<Area> autoLimitParent(String area_id) {
		return areaDao.autoLimitParent(area_id);
	}

	@Override
	public List<Area> getAllArea() {
		return areaDao.getAllArea();
	}

	@Override
	public List<AreaShort> getParentArea(String parent_area_id) {
		return areaDao.getParentArea(parent_area_id);
	}

	@Override
	public List<Area> getCityArea() {
		return areaDao.getCityArea();
	}

	/**
	 * @author linjunqin
	 * @Description 根据父ID获取底下的子列表
	 * @param
	 * @date 2017-7-3 下午2:58:27
	 */
	@Override
	public List<Area> getChildAreaList(String parent_area_id) {
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("parent_area_id", parent_area_id);
		return getAreaListByIsState(paraMap);
	}


}




