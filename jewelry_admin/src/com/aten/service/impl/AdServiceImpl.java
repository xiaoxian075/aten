package com.aten.service.impl;

import java.util.HashMap;
import java.util.List;

import com.aten.model.orm.Ad;
import com.aten.model.orm.Adv;

import org.hamcrest.core.Is;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.AdDao;
import com.aten.dao.AdvDao;
import com.aten.service.AdService;

@Service("adService")
public class AdServiceImpl extends CommonServiceImpl<Ad,String> implements AdService{

	private AdDao adDao;
	@Autowired
	private AdvDao advDao;
	
	@Autowired
	public AdServiceImpl(AdDao adDao) {
		super(adDao);
		this.adDao = adDao;
	}

	public void insertAffair(Ad ad) {
		//插入广告
		this.adDao.insert(ad);
		//更新广告位的值
		Adv adv = this.advDao.get(ad.getAdv_id());
		adv.setIs_add_ads("0");//已加入
		this.advDao.update(adv);
	}

	public void deleteAffair(String id) {
		//获取广告位对象
		Ad ad = this.adDao.get(id);
		Adv adv = this.advDao.get(ad.getAdv_id());
		//删除广告
		this.adDao.delete(id.split(","));
		//判断广告位底下是否还有广告
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("adv_id", ad.getAdv_id());
		List<Ad> adList = this.adDao.getList(paraMap);
		if(adList!=null && adList.size()==0){
			adv.setIs_add_ads("1");
			this.advDao.update(adv);
		}
	}
}




