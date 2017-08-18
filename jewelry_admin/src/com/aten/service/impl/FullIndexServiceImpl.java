package com.aten.service.impl;

import com.aten.model.orm.FullIndex;
import com.aten.model.orm.Manager;
import com.aten.model.orm.Sysconfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.lucene.search.similarities.Similarity;
import org.quartz.impl.calendar.DailyCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aten.client.RedisClient;
import com.aten.dao.FullIndexDao;
import com.aten.dao.SysconfigDao;
import com.aten.service.FullIndexService;
import com.communal.constants.Constant;
import com.communal.constants.EsContant;
import com.communal.constants.RedisConstants;

@Service("fullIndexService")
public class FullIndexServiceImpl extends CommonServiceImpl<FullIndex, String> implements FullIndexService {

	private FullIndexDao fullIndexDao;

	@Autowired
	public FullIndexServiceImpl(FullIndexDao fullIndexDao) {
		super(fullIndexDao);
		this.fullIndexDao = fullIndexDao;
	}

	@Autowired
	private SysconfigDao sysconfigDao;

	@Override
	@Transactional
	public void updateUse(HttpServletRequest request, String parameter_id) {
		FullIndex fi = fullIndexDao.get(parameter_id);

		FullIndex ffi = new FullIndex();
		ffi.setModule(fi.getModule());
		ffi.setUse_version("0");
		fullIndexDao.updateUse(ffi);

		String user_id = request.getSession().getAttribute(Constant.USER_ID).toString();
		fi.setFull_index_id(parameter_id);
		fi.setOper_man(user_id);
		fi.setUse_version("1");
		fullIndexDao.update(fi);

		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		if (fi.getModule().equals(EsContant.GOODS)) {
			paraMap.put("var_name", EsContant.CFG_GOODS_INDEX);
		} else if (fi.getModule().equals(EsContant.CATATTRTYPE)) {
			paraMap.put("var_name", EsContant.CFG_CATATTR_INDEX);
		}
		List<Sysconfig> scfList = sysconfigDao.getList(paraMap);
		Sysconfig scf = scfList.get(0);
		scf.setVar_value(fi.getIndex_version());
		sysconfigDao.update(scf);
		//更新redis索引缓存
		if (fi.getModule().equals(EsContant.GOODS)) {
			RedisClient.addStr(RedisConstants.SYSCONFIG+EsContant.CFG_GOODS_INDEX,fi.getIndex_version());
		} else if (fi.getModule().equals(EsContant.CATATTRTYPE)) {
			RedisClient.addStr(RedisConstants.SYSCONFIG+EsContant.CFG_CATATTR_INDEX,fi.getIndex_version());
		}
	}

}
