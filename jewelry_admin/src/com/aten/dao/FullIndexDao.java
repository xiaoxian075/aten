package com.aten.dao;
import com.aten.model.orm.FullIndex;

public interface FullIndexDao extends CommonDao<FullIndex, String>{

	void updateUse(FullIndex ffi);
	
}


