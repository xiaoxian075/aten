package com.aten.dao;

import com.aten.model.orm.Role;


public interface RoleDao extends CommonDao<Role, String>{
	
	public void deleteByCode(String id);
	
}


