package com.aten.service;

import java.util.List;

import com.aten.model.orm.Annex;

public  interface AnnexService extends CommonService<Annex, String>{
	
	/**
	 * @author linjunqin
	 * @Description 根据标识获取图片路径
	 * @param
	 * @date 2017-7-1 下午8:08:09
	 */
	public String getAnnexByInfoId(String info_id);

	
	/**
	 * @author linjunqin
	 * @Description 通过标识获取返回图片数组
	 * @param
	 * @date 2017-7-1 下午8:09:15
	 */
	public List<Annex> getAnnexListByInfoId(String info_id);
	
}

