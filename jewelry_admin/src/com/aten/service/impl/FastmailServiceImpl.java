package com.aten.service.impl;

import com.aten.model.orm.Fastmail;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.FastmailDao;
import com.aten.service.FastmailService;

@Service("fastmailService")
public class FastmailServiceImpl extends CommonServiceImpl<Fastmail,String> implements FastmailService{

	private FastmailDao fastmailDao;

	@Autowired
	public FastmailServiceImpl(FastmailDao fastmailDao) {
		super(fastmailDao);
		this.fastmailDao=fastmailDao;
	}

	@Override
	public List<Fastmail> getAllList() {
		return this.fastmailDao.getAllList();
	}

	@Override
	public List<Fastmail> getlistByPage(HashMap<String, Object> paraMap) {
		return this.fastmailDao.getlistByPage(paraMap);
	}

	@Override
	public int getcountByPage(HashMap<String, Object> paraMap) {
		return this.fastmailDao.getcountByPage(paraMap);
	}

	@Override
	public Fastmail selectoneByFastname(String fast_name) {
		return fastmailDao.selectoneByFastname(fast_name);
	}

	@Override
	public Fastmail selectoneByFastcode(String fast_code) {
		return fastmailDao.selectoneByFastcode(fast_code);
	}

}




