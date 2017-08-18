package com.aten.service;

import java.util.HashMap;
import java.util.List;

import com.aten.model.orm.Fastmail;

public  interface FastmailService extends CommonService<Fastmail, String>{

	List<Fastmail> getAllList();

	List<Fastmail> getlistByPage(HashMap<String, Object> paraMap);

	int getcountByPage(HashMap<String, Object> paraMap);

	Fastmail selectoneByFastname(String fast_name);

	Fastmail selectoneByFastcode(String fast_code);
}

