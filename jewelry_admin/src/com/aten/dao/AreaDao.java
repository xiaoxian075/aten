package com.aten.dao;

import com.aten.model.AreaShort;
import com.aten.model.orm.Area;

import java.util.List;

public interface AreaDao extends CommonDao<Area, String>{

    List<Area> getAllArea();

    List<Area> getSon(String pId);

    List<Area> getParent(String area_id);

    List<Area> autoLimitParent(String area_id);

    List<AreaShort> getParentArea(String parent_area_id);

	List<Area> getCityArea();
}


