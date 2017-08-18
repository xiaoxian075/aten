package com.aten.service.impl;

import com.aten.model.orm.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.RoleDao;
import com.aten.service.RoleService;
import com.hp.hpl.sparta.xpath.ThisNodeTest;

@Service("roleService")
public class RoleServiceImpl extends CommonServiceImpl<Role,String> implements RoleService{
	
	private RoleDao roleDao;

	@Autowired
	public RoleServiceImpl(RoleDao roleDao) {
		super(roleDao);
		this.roleDao = roleDao;
	}

	public void deleteByCode(String id) {
		roleDao.deleteByCode(id);
	}

}




