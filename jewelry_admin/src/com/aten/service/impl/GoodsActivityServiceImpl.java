package com.aten.service.impl;

import com.aten.dao.GoodsActivityDao;
import com.aten.model.orm.GoodsActivity;
import com.aten.model.orm.GoodsActivityMap;
import com.aten.model.orm.IncIndex;
import com.aten.service.GoodsActivityMapService;
import com.aten.service.GoodsActivityService;
import com.aten.service.IncIndexService;
import com.communal.util.AmountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("goodsActivityService")
@Transactional
public class GoodsActivityServiceImpl extends CommonServiceImpl<GoodsActivity,String> implements GoodsActivityService {

	private GoodsActivityDao goodsActivityDao;
	@Autowired
	private GoodsActivityMapService goodsActivityMapService;
	@Autowired
	private IncIndexService incIndexService;
	@Autowired
	public GoodsActivityServiceImpl(GoodsActivityDao goodsActivityDao) {
		super(goodsActivityDao);
		this.goodsActivityDao=goodsActivityDao;
	}

	@Override
	public void saveInfo(GoodsActivity goodsActivity) {
		goodsActivity.setActivity_state("0");
		if("3".equals(goodsActivity.getActivity_type())){
			goodsActivity.setDiscount(null);
		}
		//如果是黄金特惠 金额*100
		if("2".equals(goodsActivity.getActivity_type())){
			goodsActivity.setDiscount(AmountUtil.amountToBranch(goodsActivity.getDiscount()));
		}
		goodsActivity.setActivity_state("0");
		if("".equals(goodsActivity.getDiscount())){
			goodsActivity.setDiscount(null);
		}
		insertGetPk(goodsActivity);
		//保存关联的数据
		SaveRelationDate(goodsActivity);
	}

	@Override
	public void updateInfo(GoodsActivity goodsActivity) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		long now=new Date().getTime();
		try {
			//Date start=sdf.parse(goodsActivity.getStart_time());
			//结束时间小于当前时间  更改状态为 已过期
			Date end=sdf.parse(goodsActivity.getEnd_time());
			if(end.getTime()<now){
				goodsActivity.setActivity_state("3");
			}
			//结束时间大于当前时间  更改状态为 未开始
			if(end.getTime()>=now){
				goodsActivity.setActivity_state("0");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		List<GoodsActivityMap> goodsActivityMaps=goodsActivityMapService.findByActivityId(goodsActivity.getActivity_id());
		for(GoodsActivityMap goodsActivityMap:goodsActivityMaps){
			addIncIndex("update",goodsActivityMap.getGoods_id());
		}
		goodsActivityMapService.deleteGoodsActivity(goodsActivity.getActivity_id());
		if("".equals(goodsActivity.getDiscount())){
			goodsActivity.setDiscount(null);
		}
		SaveRelationDate(goodsActivity);
		update(goodsActivity);
	}

	@Override
	public void delete(String activity_id) {
		List<GoodsActivityMap> goodsActivityMaps=goodsActivityMapService.findByActivityId(activity_id);
		for(int i=0;i<goodsActivityMaps.size();i++){
			addIncIndex("update",goodsActivityMaps.get(i).getGoods_id());
		}
		//删除关联的商品
		goodsActivityMapService.deleteGoodsActivity(activity_id);
		//删除该条记录
		deleteOne(activity_id);

	}

	@Override
	public void enableState(GoodsActivity goodsActivity) {
		update(goodsActivity);
		List<GoodsActivityMap> goodsActivityMaps=goodsActivityMapService.findByActivityId(goodsActivity.getActivity_id());
		for(GoodsActivityMap goodsActivityMap:goodsActivityMaps){
			addIncIndex("update",goodsActivityMap.getGoods_id());
		}
	}

	@Override
	public void limitState(GoodsActivity goodsActivity) {
		update(goodsActivity);
		List<GoodsActivityMap> goodsActivityMaps=goodsActivityMapService.findByActivityId(goodsActivity.getActivity_id());
		for(GoodsActivityMap goodsActivityMap:goodsActivityMaps){
			addIncIndex("update",goodsActivityMap.getGoods_id());
		}
	}

	//添加索引记录
	public void addIncIndex(String type,String goods_id){
		IncIndex index=new IncIndex();
		index.setModule("goods");
		index.setModule_id(goods_id);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		index.setOper_time(sdf.format(new Date()));
		index.setOper_method(type);
		incIndexService.insert(index);

	}
	public void SaveRelationDate(GoodsActivity goodsActivity){
		//添加前台分类关联的商品分类
		String[] goodsIds=goodsActivity.getGoodsIds();
		if (goodsIds!=null&&goodsIds.length>0){
			for (String goodsId:goodsIds){
				GoodsActivityMap goodsActivityMap =new GoodsActivityMap();
				goodsActivityMap.setActivity_id(goodsActivity.getActivity_id());
				goodsActivityMap.setGoods_id(goodsId);
				goodsActivityMapService.insert(goodsActivityMap);
				addIncIndex("update",goodsId);
			}
		}

	}
}




