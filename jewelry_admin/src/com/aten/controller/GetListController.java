package com.aten.controller;

import com.aten.client.RedisClient;
import com.aten.function.CatFuc;
import com.aten.model.orm.*;
import com.aten.service.*;
import com.communal.util.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 陈熠 2017/6/30.
 */
@Controller
@RequestMapping("/")
public class GetListController extends BaseController {

	private static final Logger logger = Logger.getLogger(GoodsClassController.class);
	@Autowired
	private AccountBillService accountBillService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private GoodsClassService goodsClassService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private SupplyService supplyService;
	@Autowired
	private AppraisalService appraisalService;
	@Autowired
	private AttrService attrService;
	@Autowired
	private CatService catService;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private CouponService couponService;
	@Autowired
	GoodsActivityMapService goodsActivityMapService;

	/*
	 * @RequestMapping("brandList") public String brandList() { return
	 * "goodsclass/brandList"; }
	 * 
	 * @RequestMapping("supplyList") public String supplyList() { return
	 * "goodsclass/supplyList"; }
	 * 
	 * @RequestMapping("appraisalList") public String appraisalList() { return
	 * "goodsclass/appraisalList"; }
	 * 
	 * @RequestMapping("skuAttrList") public String skuAttrList() { return
	 * "goodsclass/skuAttrList"; }
	 * 
	 * @RequestMapping("keyAttrList") public String keyAttrList() { return
	 * "goodsclass/keyAttrList"; }
	 * 
	 * @RequestMapping("goodsclassList") public String goodsclassList() { return
	 * "pre/goodsclassList"; }
	 * 
	 * @RequestMapping("goodsList") public String goodsList() { return
	 * "coupon/goodsList"; }
	 */
	@RequestMapping("/getUpList/{acid}")
	public void getUpList(@RequestParam Map<String, Object> params, @PathVariable String acid,
			HttpServletResponse response) {

		params.put("account_id", acid);
		params.put("bill_type", "5");
		Query query = new Query(params);
		
		List<Map<String, Object>> billList = new ArrayList<Map<String, Object>>();
		List<AccountBill> bList = accountBillService.queryList(query);
		for (AccountBill ab : bList) {
			Map<String, Object> m = new HashMap<String, Object>();
			Account account = accountService.getById(ab.getAccount_id());
			m.put("account", account.getLogin_name());
			m.put("billTime", StringUtil.getStandDate(ab.getCreate_time().toString()));
			m.put("ioType", "付费升级");
			m.put("amount", "￥" + AmountUtil.fenToYuan(ab.getAmount()));
			billList.add(m);
		}
		int count = billList.size();

		PageUtils pageUtil = new PageUtils(billList, count, query.getLimit(), query.getPage());

		String listJson = JsonUtil.object2json(R.ok().put("page", pageUtil));
		outPrint(response, listJson);
	}

	@RequestMapping("getGoodsList")
	public void getGoodsList(@RequestParam Map<String, Object> params, HttpServletResponse response) {
		params.put("is_del", "1");
		// 不包括已下架
		params.put("is_off_the_shelf", "3");
		// 查询列表数据
		Query query = new Query(params);
		List<Goods> goodsList = goodsService.queryList(query);

		for (Goods goods : goodsList) {
			goods.setLower_price(FunUtil.fenToYuan(new BigDecimal(goods.getLower_price())));
			goods.setList_img(ImageUtil.getRealImgPath(goods.getList_img()));
		}
		int count = this.goodsService.getCount(query);

		PageUtils pageUtil = new PageUtils(goodsList, count, query.getLimit(), query.getPage());

		String listJson = JsonUtil.object2json(R.ok().put("page", pageUtil));

		outPrint(response, listJson);
	}

	/* 获取商品列表，不包括已经关联活动的商品 */
	@RequestMapping("getGoodsListNotRelative/{activity_id}")
	public void getGoodsListNotRelative(@RequestParam Map<String, Object> params, @PathVariable String activity_id,
			HttpServletResponse response) {
		params.put("is_del", "1");
		// 不包括已下架
		params.put("is_off_the_shelf", "3");
		// 不包括预售
		params.put("sale_mode_not", "1");
		String goodsIdsStr = "";
		// 获取已经关联活动的商品
		List<GoodsActivityMap> maps = goodsActivityMapService.findAll(activity_id);
		for (int i = 0; i < maps.size(); i++) {
			String goodsId = maps.get(i).getGoods_id();
			if (i == 0) {
				goodsIdsStr = goodsIdsStr + goodsId;
			} else {
				goodsIdsStr = goodsIdsStr + "," + goodsId;
			}

		}
		String[] goodsIds = goodsIdsStr.split(",");
		params.put("goodsIds", goodsIds);
		// 查询列表数据
		Query query = new Query(params);
		List<Goods> goodsList = goodsService.queryList(query);

		for (Goods goods : goodsList) {
			goods.setPrice(AmountUtil.formatMoney(goods.getLower_price()));
		}
		int count = this.goodsService.getCount(query);

		PageUtils pageUtil = new PageUtils(goodsList, count, query.getLimit(), query.getPage());

		String listJson = JsonUtil.object2json(R.ok().put("page", pageUtil));

		outPrint(response, listJson);
	}

	@RequestMapping("getGoodsClassList")
	public void getGoodsClassList(@RequestParam Map<String, Object> params, HttpServletResponse response) {
		// 查询列表数据
		Query query = new Query(params);
		List<Cat> catList = catService.lastLevelList(query);
		for (Cat cat : catList) {
			String str = CatFuc.getCatNameStr(cat.getLevel_cat().substring(11, cat.getLevel_cat().length()));
			str = str.replace(",", " &gt; ");
			cat.setParent_level_name(str);
		}
		int count = this.catService.lastLevelListCount(query);

		PageUtils pageUtil = new PageUtils(catList, count, query.getLimit(), query.getPage());

		String listJson = JsonUtil.object2json(R.ok().put("page", pageUtil));

		outPrint(response, listJson);
	}

	@RequestMapping("getBrandList")
	public void getBrandList(@RequestParam Map<String, Object> params, HttpServletResponse response) {
		params.put("state", "1");
		// 查询列表数据
		Query query = new Query(params);
		List<Brand> brandList = brandService.queryList(query);
		int count = this.brandService.getCount(query);

		PageUtils pageUtil = new PageUtils(brandList, count, query.getLimit(), query.getPage());

		String listJson = JsonUtil.object2json(R.ok().put("page", pageUtil));

		outPrint(response, listJson);
	}

	@RequestMapping("getAppraisalList")
	public void getAppraisalList(@RequestParam Map<String, Object> params, HttpServletResponse response) {
		params.put("state", "1");
		// 查询列表数据
		Query query = new Query(params);

		List<Appraisal> appraisalList = appraisalService.queryList(query);
		int count = this.appraisalService.getCount(query);

		PageUtils pageUtil = new PageUtils(appraisalList, count, query.getLimit(), query.getPage());

		String listJson = JsonUtil.object2json(R.ok().put("page", pageUtil));

		outPrint(response, listJson);
	}

	@RequestMapping("getSupplyList")
	public void getSupplyList(@RequestParam Map<String, Object> params, HttpServletResponse response) {
		params.put("state", "1");
		// 查询列表数据
		Query query = new Query(params);

		List<Supply> supplyList = supplyService.queryList(query);
		int count = this.supplyService.getCount(query);

		PageUtils pageUtil = new PageUtils(supplyList, count, query.getLimit(), query.getPage());

		String listJson = JsonUtil.object2json(R.ok().put("page", pageUtil));

		outPrint(response, listJson);
	}

	@RequestMapping("getAttrList")
	public void getAttrList(@RequestParam Map<String, Object> params, HttpServletResponse response) {
		params.put("state", "1");
		// 查询列表数据
		Query query = new Query(params);

		List<Attr> skuAttrList = attrService.queryList(query);
		int count = this.attrService.getCount(query);

		PageUtils pageUtil = new PageUtils(skuAttrList, count, query.getLimit(), query.getPage());

		String listJson = JsonUtil.object2json(R.ok().put("page", pageUtil));

		outPrint(response, listJson);
	}

	@RequestMapping("setSkuAttrIframe")
	public String setSkuAttrIframe(Model model) throws UnsupportedEncodingException {
		String attr_id = getRequest().getParameter("attr_id");
		String option_type = getRequest().getParameter("option_type");
		String is_index = getRequest().getParameter("is_index");
		String is_must = getRequest().getParameter("is_must");
		String is_custom_value = getRequest().getParameter("is_custom_value");
		String manual_fee = getRequest().getParameter("manual_fee");
		CatAttr catAttr = new CatAttr();
		catAttr.setAttr_id(attr_id);
		catAttr.setOption_type(option_type);
		catAttr.setIs_index(is_index);
		catAttr.setIs_must(is_must);
		catAttr.setIs_custom_value(is_custom_value);
		catAttr.setManual_fee(manual_fee);
		model.addAttribute("catAttr", catAttr);
		return "goodsclass/setSkuAttrIframe";
	}

	@RequestMapping("setKeyAttrIframe")
	public String setKeyAttrIframe(Model model) throws UnsupportedEncodingException {
		String attr_id = getRequest().getParameter("attr_id");
		String option_type = getRequest().getParameter("option_type");
		String is_index = getRequest().getParameter("is_index");
		String is_must = getRequest().getParameter("is_must");
		String is_custom_value = getRequest().getParameter("is_custom_value");
		String manual_fee = getRequest().getParameter("manual_fee");
		CatAttr catAttr = new CatAttr();
		catAttr.setAttr_id(attr_id);
		catAttr.setOption_type(option_type);
		catAttr.setIs_index(is_index);
		catAttr.setIs_must(is_must);
		catAttr.setManual_fee(manual_fee);
		catAttr.setIs_custom_value(is_custom_value);
		model.addAttribute("catAttr", catAttr);
		return "goodsclass/setKeyAttrIframe";
	}

	@RequestMapping("getCouponList")
	public void getCouponList(@RequestParam Map<String, Object> params, HttpServletResponse response) {
		params.put("state", "1");
		// 查询列表数据
		Query query = new Query(params);
		List<Coupon> couponList = couponService.queryList(query);
		int count = this.couponService.getCount(query);

		PageUtils pageUtil = new PageUtils(couponList, count, query.getLimit(), query.getPage());

		String listJson = JsonUtil.object2json(R.ok().put("page", pageUtil));

		outPrint(response, listJson);
	}
}
