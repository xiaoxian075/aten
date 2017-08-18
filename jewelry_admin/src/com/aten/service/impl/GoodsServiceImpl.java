package com.aten.service.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aten.dao.AdvancelSaleDao;
import com.aten.dao.FullSalesDao;
import com.aten.dao.GoodsCustomAttrDao;
import com.aten.dao.GoodsCustomAttrValueDao;
import com.aten.dao.GoodsCustomSkuDao;
import com.aten.dao.GoodsDao;
import com.aten.model.bean.CustomAttrListBean;
import com.aten.model.bean.CustomSkuAttrListBean;
import com.aten.model.bean.CustomSkuListBean;
import com.aten.model.orm.AdvancelSale;
import com.aten.model.orm.FullSales;
import com.aten.model.orm.Goods;
import com.aten.model.orm.GoodsCustomAttr;
import com.aten.model.orm.GoodsCustomAttrValue;
import com.aten.model.orm.GoodsCustomSku;
import com.aten.model.orm.IncIndex;
import com.aten.service.GoodsService;
import com.aten.service.IncIndexService;
import com.communal.node.ReqMsg;
import com.communal.util.AmountUtil;
import com.communal.util.Query;
import com.communal.util.StringUtil;
import com.mongodb.util.Hash;

@SuppressWarnings("all")
@Service("goodsService")
@Transactional
public class GoodsServiceImpl extends CommonServiceImpl<Goods, String> implements GoodsService {

	private GoodsDao goodsDao;

	@Autowired
	public GoodsServiceImpl(GoodsDao goodsDao) {
		super(goodsDao);
		this.goodsDao = goodsDao;
	}

	@Autowired
	private FullSalesDao fullSalesDao;
	@Autowired
	private AdvancelSaleDao advancelSaleDao;
	@Autowired
	private GoodsCustomAttrDao goodsCustomAttrDao;
	@Autowired
	private GoodsCustomAttrValueDao goodsCustomAttrValueDao;
	@Autowired
	private GoodsCustomSkuDao goodsCustomSkuDao;
	
	@Autowired
	private IncIndexService incIndexService;

//===========================================添加商品============================================
	@Override
	@Transactional
	public ReqMsg insertGoods(Goods goods, 
			AdvancelSale advancelSale, 
			FullSales fullSales,
			CustomAttrListBean customAttrList, 
			CustomSkuListBean customSkuList,
			CustomSkuAttrListBean customSkuAttrList) {

		String msg = "商品发布成功！";
		// 设置一些默认值
		goods.setInfo_state("1");
		goods.setAudit_state("1");
		goods.setMana_id("1");
		goods.setCom_id("0");
		goods.setIs_del("1");
		goods.setWeight("0");
		goods.setVolume("0");
		goods.setTotal_sales("0");
		goods.setStock_type("0");
		goods.setManual_fee(AmountUtil.yuanToFen(goods.getManual_fee()));
		
		// 插入第一张主图
		String listImg[] = goods.getShow_imgs().split(",");
		goods.setList_img(listImg[0]);

		// 插入数据，并返回主键 主键从对象中获取
		goodsDao.insertGetPk(goods);

		String goods_id = goods.getGoods_id();
		if (StringUtil.isEmpty(goods_id)) {
			msg = "商品发布异常！";
			return new ReqMsg<>(1, msg, null);
		}

		// 销售模式
		if ("0".equals(goods.getSale_mode())) {// 如果售卖方式是一口价模式，则默认预售模式为0

		} else {// 否则售卖方式是预售模式
			if ("1".equals(goods.getPresale_model())) {// 全额预售
				fullSales.setGoods_id(goods_id);
				this.fullSalesDao.insertGetPk(fullSales);
			} else {// 定金预售
				advancelSale.setGoods_id(goods_id);
				this.advancelSaleDao.insertGetPk(advancelSale);
			}
		}

		// 商品属性
		if (StringUtil.isNullOrEmpty(customAttrList.getCustomAttrList())) {
			msg = "获取商品属性信息异常！";
			return new ReqMsg<>(1, msg, null);
		}

		// 添加属性
		int state = 0;
		state = addCustomAttrMsg(customAttrList, goods_id, "0");//0 商品属性     
		if (state == 0) {
			msg = "添加商品属性异常！";
			return new ReqMsg<>(1, msg, null);
		}

		if(StringUtil.isNullOrEmpty(customSkuList.getCustomSkuList())){
			msg = "添加商品规格属性没数据！";
			return new ReqMsg<>(1, msg, null);
		}
		
		if(StringUtil.isNullOrEmpty(customSkuAttrList.getCustomSkuAttrList())){
			msg = "添加商品规格值没数据！";
			return new ReqMsg<>(1, msg, null);
		}
		
		// 添加SKU属性
		int state1 = 0;
		state1 = addCustomSkuAttrMsg(customSkuAttrList, goods_id, "1");//sku属性
		if (state1 == 0) {
			msg = "添加商品规格属性异常！";
			return new ReqMsg<>(1, msg, null);
		}

		// 添加商品SKU数据
		HashMap<Object, Object> mapResult = new HashMap<Object, Object>();
		mapResult = addCustomSkuValueMsg(customSkuList, goods_id, "1");
		// 修改商品价格信息
		if ("1".equals(mapResult.get("state"))) {
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("lower_price", mapResult.get("lower_price"));
			param.put("height_price", mapResult.get("height_price"));
			param.put("total_stock", mapResult.get("total_stock"));
			param.put("goods_id", goods_id);
			goodsDao.updateGoodsPrice(param);
		} else {
			msg = "添加商品规格值异常！";
			return new ReqMsg<>(1, msg, null);
		}
		//如果是立即发布状态
		if("1".equals(goods.getState())){
			addIncIndex("insert",goods_id);
		}
		
		return new ReqMsg<>(0, msg, goods);
		
	}
//=======================================添加商品结束==============================================
	
	
	// 插入属性
	protected int addCustomAttrMsg(CustomAttrListBean calb, String goods_id, String attr_type) {
		int state = addAllAttrMsg(calb.getCustomAttrList(), goods_id,attr_type);
		return state;
	}

	// 插入规格属性
	protected int addCustomSkuAttrMsg(CustomSkuAttrListBean csalb, String goods_id, String attr_type) {
		int state = addAllAttrMsg(csalb.getCustomSkuAttrList(), goods_id,attr_type);
		return state;
	}

	// 添加商品属性跟属性值的具体方法
	public int addAllAttrMsg(List<GoodsCustomAttr> list, String goods_id,String attr_type) {
		int state = 0;
		String pos = "###";
		String uuid_ = "uuid+";// 自定义属性值标识
		for (GoodsCustomAttr customAttr : list) {
			GoodsCustomAttr gca = new GoodsCustomAttr();
			GoodsCustomAttrValue gcav = new GoodsCustomAttrValue();
			if (!StringUtil.isNullOrEmpty(customAttr)) {
				// 插入自定义属性跟规格属性
				if (!StringUtil.isNullOrEmpty(customAttr.getAttr_id())) {
					if (customAttr.getAttr_id().indexOf(pos) > -1) {
						String[] attr_id = customAttr.getAttr_id().split(pos);
						gca.setAttr_id(attr_id[0]);
						gca.setCustom_alias(attr_id[1]);
						gca.setGoods_id(goods_id);
						gca.setAttr_type(attr_type);
						goodsCustomAttrDao.insertGetPk(gca);
						if (customAttr.getAttr_value_id().indexOf(pos) > -1) {
							String[] custom_value_id = customAttr.getAttr_value_id().split(pos);
							String[] custom_value = customAttr.getCustom_attr_value().split(pos);
							for (int i = 0; i < custom_value_id.length; i++) {
								if (custom_value_id[i].indexOf(uuid_) > -1) {
									String[] av_id = custom_value_id[i].split(uuid_);
									gcav.setAttr_id(gca.getAttr_id());
									gcav.setAv_id(av_id[1].substring(1, av_id[1].length()));
									gcav.setCustom_attr_id(gca.getCustom_attr_id());
									gcav.setCustom_av_id(av_id[1].substring(1, av_id[1].length()));
									gcav.setCustom_attr_value(custom_value[i]);
									goodsCustomAttrValueDao.insertGetPk(gcav);
								} else {
									gcav.setAttr_id(StringUtil.flushLeft(10, gca.getAttr_id()));
									gcav.setAv_id(StringUtil.flushLeft(10, custom_value_id[i]));
									gcav.setCustom_attr_id(gca.getCustom_attr_id());
									gcav.setCustom_av_id(StringUtil.flushLeft(10, custom_value_id[i]));
									gcav.setCustom_attr_value(custom_value[i]);
									goodsCustomAttrValueDao.insertGetPk(gcav);
								}
							}
						} else {
							gcav.setAttr_id(gca.getAttr_id());
							if (customAttr.getAttr_value_id().indexOf(uuid_) > -1) {
								gcav.setAv_id(customAttr.getAttr_value_id().substring(5,
										customAttr.getAttr_value_id().length()));
								gcav.setCustom_av_id(customAttr.getAttr_value_id().substring(5,
										customAttr.getAttr_value_id().length()));
							} else {
								gcav.setAv_id(StringUtil.flushLeft(10, customAttr.getAttr_value_id()));
								gcav.setCustom_av_id(StringUtil.flushLeft(10, customAttr.getAttr_value_id()));
							}
							gcav.setCustom_attr_id(gca.getCustom_attr_id());
							gcav.setCustom_attr_value(customAttr.getCustom_attr_value());
							goodsCustomAttrValueDao.insertGetPk(gcav);
						}
					}
				}
			}
			state = 1;
		}
		return state;
	}

	// 添加商品sku
	HashMap<Object, Object> addCustomSkuValueMsg(CustomSkuListBean cslb, String goods_id, String attr_type) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		int total = 0;
		List list = new ArrayList();
		for (GoodsCustomSku customSku : cslb.getCustomSkuList()) {
			if (!StringUtil.isNullOrEmpty(customSku)) {
				GoodsCustomSku gcs = assembleSku(customSku, goods_id);
				// 统计总库存
				total += gcs.getStock();
				list.add(Integer.parseInt(gcs.getSale_price().trim()));
				goodsCustomSkuDao.insert(gcs);
				map.put("state", "1");
			} else {
				map.put("state", "0");
			}
		}
		map.put("total_stock", total);
		map.put("lower_price", Collections.min(list));// 取出商品价格最小值
		map.put("height_price", Collections.max(list));
		return map;
	}
	
//=========================以上共用添加属性，规格值方法====================================	
	
	
	@Override
	public boolean updateGoodsPublicIsdel(String goods_id, int is_del) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("goods_id", goods_id);
		param.put("is_del", is_del);// 状态=0为删除
		if (1 == goodsDao.updateOneIsdel(param)) {
			addIncIndex("delete",goods_id);
			return true;
		}
		return false;
	}

	@Override
	public boolean batchUpdateGoodsPublicIsdel(List<BigInteger> arr_goodsid, int is_del) {
		for (BigInteger goods_id : arr_goodsid) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("goods_id", goods_id);
			param.put("is_del", is_del); // 状态=0为删除
			if (1 != goodsDao.updateOneIsdel(param)){
				throw new RuntimeException();
			}
			addIncIndex("delete",goods_id.toString());
		}
		return true;
	}

	@Override
	public boolean updateGoodsPublicState(BigInteger goods_id, int state) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("goods_id", goods_id);
		param.put("state", state);
		if(state == 1){
			param.put("in_date", new Timestamp(new Date().getTime())); // 置为当前系统时间*///不需要修改当前发布时间
		}
		
		if (1 == goodsDao.updateOneState(param)){
			if(state == 3){
				addIncIndex("delete",goods_id.toString());
			}else{
				addIncIndex("update",goods_id.toString());
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean batchUpdateGoodsPublicState(List<BigInteger> arr_goodsid, int state) {
		Timestamp curtime = new Timestamp(new Date().getTime());
		for (BigInteger goods_id : arr_goodsid) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("goods_id", goods_id);
			param.put("state", state);
			param.put("in_date", curtime); // 置为当前系统时间
			if (1 != goodsDao.updateOneState(param)) {
				throw new RuntimeException();
			}
			if(state == 3){
				addIncIndex("delete",goods_id.toString());
			}else{
				addIncIndex("update",goods_id.toString());
			}
		}
		return true;
	}


//==================================修改商品=======================================
	@Override
	@Transactional
	public ReqMsg updateGoods(Goods goods, 
			AdvancelSale advancelSale, 
			FullSales fullSales,
			CustomAttrListBean customAttrList, 
			CustomSkuListBean customSkuList,
			CustomSkuAttrListBean customSkuAttrList) {
		
		String msg = "商品修改成功！";
		// 设置一些默认值
		goods.setInfo_state("1");
		goods.setAudit_state("1");
		goods.setMana_id("1");
		goods.setCom_id("0");
		goods.setIs_del("1");
		goods.setWeight("0");
		goods.setVolume("0");
		goods.setTotal_sales("0");
		goods.setStock_type("0");
		goods.setManual_fee(AmountUtil.yuanToFen(goods.getManual_fee()));
		
		// 插入第一张主图
		String listImg[] = goods.getShow_imgs().split(",");
		goods.setList_img(listImg[0]);
		int updateResult = goodsDao.updateGoodsInfo(goods);
		
		if(updateResult != 1){
			msg = "修改商品信息失败！";
			return new ReqMsg<>(1, msg, null);
		}
		
		// 销售模式
		if ("0".equals(goods.getSale_mode())) {// 如果售卖方式是一口价模式，则默认预售模式为0
			advancelSaleDao.deleteByGoodsId(goods.getGoods_id());
			fullSalesDao.deleteByGoodsId(goods.getGoods_id());
		} else {// 否则售卖方式是预售模式
			if ("1".equals(goods.getPresale_model())) {// 全额预售
				FullSales fullSales2 = fullSalesDao.getByGoodsid(goods.getGoods_id());
				if(fullSales2 != null){//原来就是全额销售模式
					this.fullSalesDao.updateInfoByGoodsId(fullSales);
				}else{//从一口价模式变成全额销售模式
					this.fullSalesDao.insert(fullSales);
				}
				/*advancelSaleDao.deleteByGoodsId(goods.getGoods_id());
				this.fullSalesDao.insert(fullSales);*/
			} else {// 定金预售
				/*fullSalesDao.deleteByGoodsId(goods.getGoods_id());
				this.advancelSaleDao.insert(advancelSale);*/
				
				AdvancelSale advancelSale2 = advancelSaleDao.getByGoodsid(goods.getGoods_id());
				if(advancelSale2 != null){
					this.advancelSaleDao.updateInfoByGoodsId(advancelSale);
				}else{
					this.advancelSaleDao.insert(advancelSale);
				}
			}
		}
		
		// 商品属性
		if (StringUtil.isNullOrEmpty(customAttrList.getCustomAttrList())) {
			msg = "获取商品属性信息异常！";
			return new ReqMsg<>(1, msg, null);
		}
		
		//先删除原来的属性值跟sku 顺序不能换，要先删除自定义属性值，才能删除自定义属性
		goodsCustomAttrValueDao.deleteByGoodsId(goods.getGoods_id());
		goodsCustomAttrDao.deleteByGoodsId(goods.getGoods_id());
		
		// 添加新的属性直接调用添加的方法
		int state = 0;
		state = addCustomAttrMsg(customAttrList, goods.getGoods_id(), "0");//0 商品属性     
		if (state == 0) {
			msg = "修改商品属性异常！";
			return new ReqMsg<>(1, msg, null);
		}
		
		if(StringUtil.isNullOrEmpty(customSkuList.getCustomSkuList())){
			msg = "修改商品规格属性没数据！";
			return new ReqMsg<>(1, msg, null);
		}
		
		if(StringUtil.isNullOrEmpty(customSkuAttrList.getCustomSkuAttrList())){
			msg = "修改商品规格值没数据！";
			return new ReqMsg<>(1, msg, null);
		}
		
		// 添加SKU属性
		int state1 = 0;
		state1 = addCustomSkuAttrMsg(customSkuAttrList, goods.getGoods_id(), "1");//sku属性
		if (state1 == 0) {
			msg = "修改商品规格属性异常！";
			return new ReqMsg<>(1, msg, null);
		}
		
		// 添加商品SKU值数据
		HashMap<Object, Object> mapResult = new HashMap<Object, Object>();
		mapResult = updateCustomSkuValueMsg(customSkuList, goods.getGoods_id(), "1");
		// 修改商品价格信息
		if ("1".equals(mapResult.get("state"))) {
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("lower_price", mapResult.get("lower_price"));
			param.put("height_price", mapResult.get("height_price"));
			param.put("total_stock", mapResult.get("total_stock"));
			param.put("goods_id", goods.getGoods_id());
			goodsDao.updateGoodsPrice(param);
		} else {
			msg = "修改商品规格值异常！";
			return new ReqMsg<>(1, msg, null);
		}
		//如果是立即发布状态
		if("1".equals(goods.getState())){
			addIncIndex("update",goods.getGoods_id());
		}
		return new ReqMsg<>(0, msg, goods);
	}

	
	// 修改商品sku
	HashMap<Object, Object> updateCustomSkuValueMsg(CustomSkuListBean cslb, String goods_id, String attr_type) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		int total = 0;
		List list = new ArrayList();
		List<Object> listSkuId = new ArrayList<Object>();
		for (GoodsCustomSku customSku : cslb.getCustomSkuList()) {
			GoodsCustomSku gcs = new GoodsCustomSku();
			//直接就是页面新增的sku值
			if("0".equals(customSku.getSku_id()) && "0".equals(customSku.getGoods_id())){
				gcs = assembleSku(customSku, goods_id);
				goodsCustomSkuDao.insertGetPk(gcs);
				listSkuId.add(gcs.getSku_id());//放入suk_id组成新的数组
			}else{
				//修改原来的sku值
				GoodsCustomSku goodsCustomSku  = goodsCustomSkuDao.getOneById(customSku.getSku_id(), customSku.getGoods_id());
				if(!StringUtil.isNullOrEmpty(goodsCustomSku)){
					gcs = assembleSku(customSku, goods_id);
					gcs.setSku_id(customSku.getSku_id());
					goodsCustomSkuDao.updateCustomSku(gcs);
					listSkuId.add(customSku.getSku_id());//放入suk_id组成新的数组
				}
			}
			total += gcs.getStock();
			list.add(Integer.parseInt(gcs.getSale_price().trim()));
		}
		
		//删除不包含listSkuId里面原来的sku值
		
		goodsCustomSkuDao.batchDeleteNotIncludeOldSku(listSkuId,goods_id);
		
		map.put("total_stock", total);
		map.put("state", "1");
		map.put("lower_price", Collections.min(list));// 取出商品价格最小值
		map.put("height_price", Collections.max(list));
		return map;
	}
//====================================修改商品结束========================================
	/**
	 * @param
	 * @author linjunqin
	 * @Description 通过传入goods_id获取对应的自定义属性值串
	 * @date 2017年7月10日 下午5:46:52
	 */
	@Override
	public String getAvIdstr(HashMap<String, Object> paraMap) {
		return goodsDao.getAvIdstr(paraMap);
	}

	@Override
	public List<Goods> queryList(Query query) {
		return goodsDao.queryList(query);
	}

	/**
	 * @param
	 * @author chenyi
	 * @Description 检查该id是否被商品引用
	 * @date 2017/7/12
	 */
	@Override
	public boolean checkGoodsClass(String cat_id) {
		List<Goods> goodsList = goodsDao.checkGoodsClass(cat_id);
		if (goodsList != null && goodsList.size() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 组装数据
	 * @param goodsCustomSku
	 * @param goods_id
	 * @return
	 */
	public GoodsCustomSku assembleSku(GoodsCustomSku goodsCustomSku,String goods_id){
		String pos = "###";
		String uuid_ = "uuid+";// 自定义属性值标识
		
		GoodsCustomSku gcs = new GoodsCustomSku();
		String arr[] = goodsCustomSku.getAttr_value().split(pos);
		// 固定值
		gcs.setStock(Integer.parseInt(arr[arr.length - 1].trim()));// 总数量
		// 转成分为单位
		gcs.setSale_price(AmountUtil.yuanToFen(arr[arr.length - 2].trim()));// 售价
		gcs.setManual_fee(AmountUtil.yuanToFen(arr[arr.length - 3].trim()));// 手工费
		gcs.setPrice(AmountUtil.yuanToFen(arr[arr.length - 4].trim()));// 商品价格
		gcs.setGoods_id(goods_id);

		// 拼装sku值ID
		String arr_id[] = goodsCustomSku.getAttr_value_id().split(pos);
		StringBuilder sbf = new StringBuilder();//线程非安全
		for (int i = 0; i < arr_id.length; i++) {// 如果是自定义
			if (arr_id[i].indexOf(uuid_) > -1) {
				sbf.append(arr_id[i].substring(5, arr_id[i].length()) + ",");
			} else {
				sbf.append(StringUtil.flushLeft(10, arr_id[i]) + ",");
			}
		}
		gcs.setSku_str(sbf.substring(0, sbf.length() - 1));
		
		//拼接值
		String[] copyResult = Arrays.copyOfRange(arr, 0,arr.length-4);
		sbf.delete(0, sbf.length());
		for(int i=0;i<copyResult.length;i++){
			sbf.append(copyResult[i]+",");
		}
		gcs.setSku_name(sbf.substring(0, sbf.length() - 1));
		
		return gcs;
	}
	
	//添加索引记录
	public void addIncIndex(String type,String goods_id){
		IncIndex index=new IncIndex();
		index.setModule("goods");
		index.setModule_id(goods_id);
		index.setOper_time(StringUtil.fromDateH(new Date()));
		index.setOper_method(type);
		incIndexService.insert(index);
	}
}
