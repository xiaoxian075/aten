package com.aten.service;

import com.aten.model.orm.Role;

public  interface RoleService extends CommonService<Role, String>{
	
	public void deleteByCode(String id);
}

