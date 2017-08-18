package com.aten.service;

import javax.servlet.http.HttpServletRequest;

import com.aten.model.orm.FullIndex;

public interface FullIndexService extends CommonService<FullIndex, String> {

	void updateUse(HttpServletRequest request, String parameter_id);

}
