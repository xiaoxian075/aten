package com.aten.service.impl;

import com.aten.model.orm.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.VersionDao;
import com.aten.service.VersionService;

@Service("versionService")
public class VersionServiceImpl extends CommonServiceImpl<Version,String> implements VersionService{

	private VersionDao versionDao;

	@Autowired
	public VersionServiceImpl(VersionDao versionDao) {
		super(versionDao);
		this.versionDao=versionDao;
	}

}




