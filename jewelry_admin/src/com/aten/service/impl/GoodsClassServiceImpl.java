package com.aten.service.impl;

import com.aten.function.SysconfigFuc;
import com.aten.model.orm.*;
import com.aten.service.*;
import com.communal.constants.Constant;
import com.communal.util.AmountUtil;
import com.communal.util.ChineseToEnglishUtil;
import com.communal.util.RandomCharUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aten.dao.CommonDao;
import com.aten.dao.GoodsClassDao;
import com.aten.dao.SmsTemporaryDao;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("GoodsClassService")
@Transactional
public class GoodsClassServiceImpl  extends CommonServiceImpl<GoodsClass,String> implements GoodsClassService {

	private GoodsClassDao goodsClassDao;
	@Autowired
	private CatRateService catRateService;
	@Autowired
	private CatService catService;
	@Autowired
	private CatBrandService catBrandService;
	@Autowired
	private CatSupplyService catSupplyService;
	@Autowired
	private CatAppraisalService catAppraisalService;
	@Autowired
	private DivideRecordService divideRecordService;
	@Autowired
	private CatAttrService catAttrService;
	@Autowired
	private IncIndexService incIndexService;
	@Autowired
	public GoodsClassServiceImpl(GoodsClassDao goodsClassDao) {
		super(goodsClassDao);
	}

	public void saveInfo(HttpServletRequest request,Cat cat) {
		String uuid= RandomCharUtil.getNumberRand();
		cat.setCat_id(uuid);
		cat.setModule("goods");
		cat.setIs_sys("0");
		cat.setWord_index(ChineseToEnglishUtil.getPinYinHeadChar(cat.getCat_name()));
		//上级分类
		String parent_id= SysconfigFuc.getSysValue("cfg_goods_top");
		String parent_cat_id=cat.getParent_cat_id();
		if(!"".equals(parent_cat_id)&&parent_cat_id.length()>=10){
			parent_id=parent_cat_id.substring(parent_cat_id.length()-10,parent_cat_id.length());
		}
		cat.setParent_cat_id(parent_id);

		Cat parent_cat=catService.get(parent_id);
		if (parent_cat!=null){
			//级别串
			cat.setLevel_cat(parent_cat.getLevel_cat()+","+uuid);
			//级别
			cat.setCat_level(Integer.parseInt(parent_cat.getCat_level())+1+"");
		}
		catService.insert(cat);
		//添加分成比例
		CatRate catRate=new CatRate();
		catRate.setCat_id(cat.getCat_id());
		catRate.setDivide_rate(cat.getDivide_rate());
		catRate.setManual_fee(AmountUtil.amountToBranch(cat.getManual_fee()));
		catRateService.insertGetPk(catRate);
		//分成记录流
		DivideRecord divideRecord=new DivideRecord();
		divideRecord.setDivide_rate(cat.getDivide_rate());
		divideRecord.setOper_man((String)request.getSession().getAttribute(Constant.USER_NAME));
		divideRecord.setOper_man_id((String)request.getSession().getAttribute(Constant.USER_ID));
		Date now=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		divideRecord.setOper_time(sdf.format(now));
		divideRecord.setRate_id(catRate.getRate_id());
		divideRecordService.insert(divideRecord);
		//保存关联的数据
		SaveRelationData(cat);
	}
	//添加索引记录
	public void addIncIndex(String type,String cat_attr_id){
		IncIndex index=new IncIndex();
		index.setModule("catattr");
		index.setModule_id(cat_attr_id);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		index.setOper_time(sdf.format(new Date()));
		index.setOper_method(type);
		incIndexService.insert(index);

	}
	public void SaveRelationData(Cat cat){
		//添加分类关联的品牌
		String[] brandIds=cat.getBrandIds();
		if (brandIds!=null&&brandIds.length>0){
			for (String brandId:brandIds){
				CatBrand catBrand=new CatBrand();
				catBrand.setCat_id(cat.getCat_id());
				catBrand.setBrand_id(brandId);
				catBrandService.insert(catBrand);
			}
		}
		//添加分类关联的供应商
		String[] supplyIds=cat.getSupplyIds();
		if (supplyIds!=null&&supplyIds.length>0){
			for (String supplyId:supplyIds){
				CatSupply catSupply=new CatSupply();
				catSupply.setCat_id(cat.getCat_id());
				catSupply.setSupply_id(supplyId);
				catSupplyService.insert(catSupply);
			}
		}
		//添加分类关联的鉴定机构
		String[] appraisalIds=cat.getAppraisalIds();
		if(appraisalIds!=null&&appraisalIds.length>0){
			for (String appraisalId:appraisalIds){
				CatAppraisal catAppraisal=new CatAppraisal();
				catAppraisal.setCat_id(cat.getCat_id());
				catAppraisal.setAppraisal_id(appraisalId);
				catAppraisalService.insert(catAppraisal);
			}
		}
		//添加分类关联的商品规格
		List<CatAttr> catSkuAttrList=cat.getCatSkuAttrList();
		if(catSkuAttrList!=null&&catSkuAttrList.size()>0){
			for (CatAttr catAttr:catSkuAttrList){
				catAttr.setCat_id(cat.getCat_id());
				catAttr.setIs_key("0");
				catAttr.setIs_sku("1");
				catAttr.setIs_alisa("0");
				catAttr.setShow_type("0");
				catAttrService.insertGetPk(catAttr);
				//添加索引记录
				addIncIndex("insert",catAttr.getIdent_id());
			}
		}
		//添加分类关联的商品属性
		List<CatAttr> catKeyAttrList=cat.getCatKeyAttrList();
		if(catKeyAttrList!=null&&catKeyAttrList.size()>0){
			for (CatAttr catAttr:catKeyAttrList){
				catAttr.setCat_id(cat.getCat_id());
				catAttr.setIs_key("1");
				catAttr.setIs_sku("0");
				catAttr.setIs_alisa("0");
				catAttr.setShow_type("0");
				catAttrService.insertGetPk(catAttr);
				//添加索引记录
				addIncIndex("insert",catAttr.getIdent_id());
			}
		}
	}
	@Override
	public void updateInfo(HttpServletRequest request, Cat cat) {
		//上级分类
//		String parent_id=SysconfigFuc.getSysValue("cfg_goods_top");
//		String parent_cat_id=cat.getParent_cat_id();
//
//		if(",".equals(parent_cat_id.substring(parent_cat_id.length()-1,parent_cat_id.length()))){
//			parent_cat_id=parent_cat_id.substring(0,parent_cat_id.length()-1);
//		}
//		if(!"".equals(parent_cat_id)&&parent_cat_id.length()>=10){
//			parent_id=parent_cat_id.substring(parent_cat_id.length()-10,parent_cat_id.length());
//		}
	//	cat.setParent_cat_id(parent_id);
		//Cat parent_cat=catService.get(parent_id);
		//级别串
	//	cat.setLevel_cat(parent_cat.getLevel_cat()+","+cat.getCat_id());
		//级别
	//	cat.setCat_level(Integer.parseInt(parent_cat.getCat_level())+1+"");
		cat.setWord_index(ChineseToEnglishUtil.getPinYinHeadChar(cat.getCat_name()));
		//修改分成比例
		List<CatRate> catRateList= catRateService.getByCatId(cat.getCat_id());
		if(catRateList!=null&&catRateList.size()>0){
			catRateList.get(0).setDivide_rate(cat.getDivide_rate());
			catRateList.get(0).setManual_fee(AmountUtil.amountToBranch(cat.getManual_fee()));
			catRateService.update(catRateList.get(0));
			//添加分成记录流
			DivideRecord divideRecord=new DivideRecord();
			divideRecord.setDivide_rate(cat.getDivide_rate());
			divideRecord.setOper_man((String)request.getSession().getAttribute(Constant.USER_NAME));
			divideRecord.setOper_man_id((String)request.getSession().getAttribute(Constant.USER_ID));
			Date now=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			divideRecord.setOper_time(sdf.format(now));
			divideRecord.setRate_id(catRateList.get(0).getRate_id());
			divideRecordService.insert(divideRecord);
		}
		if(catRateList==null||catRateList.size()==0){
			//添加分成比例
			CatRate catRate=new CatRate();
			catRate.setCat_id(cat.getCat_id());
			catRate.setDivide_rate(cat.getDivide_rate());
			catRateService.insertGetPk(catRate);
			//添加分成记录流
			DivideRecord divideRecord=new DivideRecord();
			divideRecord.setDivide_rate(cat.getDivide_rate());
			divideRecord.setOper_man((String)request.getSession().getAttribute(Constant.USER_NAME));
			divideRecord.setOper_man_id((String)request.getSession().getAttribute(Constant.USER_ID));
			Date now=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			divideRecord.setOper_time(sdf.format(now));
			divideRecord.setRate_id(catRate.getRate_id());
			divideRecordService.insert(divideRecord);
		}
		//添加索引记录
		List<CatAttr> catAttrList =catAttrService.getByCatId(cat.getCat_id());
		for(CatAttr catAttr:catAttrList){
			addIncIndex("delete",catAttr.getIdent_id());
		}
		//先删除关联品牌
		catBrandService.deleteCatBrand(cat.getCat_id());
		//先删除关联供应商
		catSupplyService.deleteCatSupply(cat.getCat_id());
		//先删除关联鉴定机构
		catAppraisalService.deleteCatAppraisal(cat.getCat_id());
		//先删除关联规格
		catAttrService.deleteCatSkuAttr(cat.getCat_id());
		//先删除关联属性
		catAttrService.deleteCatKeyAttr(cat.getCat_id());
		SaveRelationData(cat);
		this.catService.update(cat);
	}
}
