package com.aten.service.impl;

import java.util.HashMap;
import java.util.List;

import com.aten.model.orm.Annex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.AnnexDao;
import com.aten.service.AnnexService;

@Service("annexService")
public class AnnexServiceImpl extends CommonServiceImpl<Annex,String> implements AnnexService{

	private AnnexDao annexDao;

	@Autowired
	public AnnexServiceImpl(AnnexDao annexDao) {
		super(annexDao);
		this.annexDao=annexDao;
	}

	/**
	 * @author linjunqin
	 * @Description 根据标识获取图片路径
	 * @param
	 * @date 2017-7-1 下午8:08:09
	 */
	@Override
	public String getAnnexByInfoId(String info_id) {
		String annex_url = "";
		List<Annex> annexList = getAnnexListByInfoId(info_id);
		if(annexList!=null && annexList.size()>0){
			annex_url = annexList.get(0).getAnnex_url();
		}
		return annex_url;
	}
	
	/**
	 * @author linjunqin
	 * @Description 通过标识获取返回图片数组
	 * @param
	 * @date 2017-7-1 下午8:09:15
	 */
	@Override
	public List<Annex> getAnnexListByInfoId(String info_id) {
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("info_id", info_id);
		List<Annex> annexList = this.annexDao.getList(paraMap);
 		return annexList;
	}

}




