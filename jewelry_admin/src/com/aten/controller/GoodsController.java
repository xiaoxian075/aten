package com.aten.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.aten.function.CommparaFuc;
import com.aten.function.SysconfigFuc;
import com.aten.model.bean.CustomAttrListBean;
import com.aten.model.bean.CustomSkuAttrListBean;
import com.aten.model.bean.CustomSkuListBean;
import com.aten.model.orm.AdvancelSale;
import com.aten.model.orm.Appraisal;
import com.aten.model.orm.AttrValue;
import com.aten.model.orm.AttrVo;
import com.aten.model.orm.Brand;
import com.aten.model.orm.Cat;
import com.aten.model.orm.CustomAttrNode;
import com.aten.model.orm.CustomAttrValueNode;
import com.aten.model.orm.FullSales;
import com.aten.model.orm.Goods;
import com.aten.model.orm.GoodsCustomSku;
import com.aten.model.orm.Job;
import com.aten.model.orm.ShipTemplate;
import com.aten.model.orm.SkuNode;
import com.aten.model.orm.SkuVo;
import com.aten.model.orm.Supply;
import com.aten.service.AdvancelSaleService;
import com.aten.service.AppraisalService;
import com.aten.service.AttrService;
import com.aten.service.BrandService;
import com.aten.service.CatService;
import com.aten.service.CustomAttrService;
import com.aten.service.CustomAttrValueService;
import com.aten.service.FullSalesService;
import com.aten.service.GoodsService;
import com.aten.service.JobService;
import com.aten.service.LogitempService;
import com.aten.service.SkuService;
import com.aten.service.SupplyService;
import com.communal.node.ReqMsg;
import com.communal.util.AmountUtil;
import com.communal.util.JsonUtil;
import com.communal.util.StringUtil;
import com.google.gson.Gson;

/**
 * 商品发布
 *
 * @author Administrator
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("admin/goods")
public class GoodsController extends BaseController {
    @Resource
    private CatService catService;
    @Resource
    private BrandService brandService;
    @Resource
    private SupplyService supplyService;
    @Resource
    private AppraisalService appraisalService;
    @Resource
    private LogitempService logitempService;
    @Resource
    private AttrService attrService;
    @Resource
    private GoodsService goodsService;
    @Autowired
    private JobService jobService;
    @Autowired
    private FullSalesService fullSalesService;
    @Autowired
    private AdvancelSaleService advancelSaleService;
    @Autowired
    private CustomAttrService customAttrService;
    @Autowired
    private CustomAttrValueService customAttrValueService;
    @Autowired
    private SkuService skuService;
    

    /**
     * @param
     * @author linjunqin
     * @Description 初始方法
     * @date 2017-1-5 下午2:37:11
     */
    @ModelAttribute
    public void populateModel(HttpServletRequest request, Model model) {
        initialHiddenVal(request, model);
        model.addAttribute("cfg_goods_top", SysconfigFuc.getSysValue("cfg_goods_top"));
    }

    /**
     * 商品列表
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="list")
    public String list(HttpServletRequest request, Model model) {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        // 搜索封装
        paraMap = searchMap(request, paraMap, model);
        String parent_cat_id = (String) paraMap.get("parent_cat_id");
        String catIdsStr = "";
        String[] catIds={};
        if (parent_cat_id != null || "".equals(parent_cat_id)) {

            //截取后十位
            parent_cat_id = parent_cat_id.substring(parent_cat_id.length() - 10, parent_cat_id.length());
            //获取该分类的所有下级商品分类
            List<Cat> goodscats = catService.findGoodsCatsByOne(parent_cat_id);
            for (int i = 0; i < goodscats.size(); i++) {
                String cat_id = goodscats.get(i).getCat_id();
                if (i == 0) {
                    catIdsStr = catIdsStr+cat_id;
                } else {
                    catIdsStr = catIdsStr + "," + cat_id;
                }
            }
        }
        
        if (parent_cat_id!=null&&!"".equals(parent_cat_id)) {
            catIds=catIdsStr.split(",");
            paraMap.put("catIds", catIds);
        }
        paraMap.put("is_del", "1");
        int count = this.goodsService.getCount(paraMap);
        // 分页工具
        paraMap = pageTool(request, count, model, paraMap);
        List<Goods> goodsList = this.goodsService.getList(paraMap);

        for (Goods goods : goodsList) {
            StringBuffer sbf = new StringBuffer();
            goods.setFixed_price(AmountUtil.fenToYuan(goods.getFixed_price()));

            Cat cat = catService.getWithRate(goods.getCat_id());
            Cat cat1 = catService.getWithRate(cat.getParent_cat_id());
            sbf.append(cat1.getParent_name() + ">" + cat.getParent_name() + ">" + cat.getCat_name());
            goods.setCat_name(sbf.toString());
        }

        model.addAttribute("goodsList", goodsList);
        return "goods/list";
    }

    /**
     * 跳转类目页面
     *
     * @param model
     * @return
     */
    @RequestMapping("add")
    public String add(Model model) {
        List<Cat> catList = catService.selectCatByPid(SysconfigFuc.getSysValue("cfg_goods_top"));
        model.addAttribute("catList", catList);
        return "goods/cat";
    }

    @RequestMapping("delete")
    public String delete(HttpServletRequest request, Model model) {
        String parameter_id = request.getParameter("parameter_id");
        if (parameter_id == null || parameter_id.equals("")) {
            return list(request, model);
        }
        Goods goods = goodsService.get(parameter_id);

        if (goods == null) {
            return list(request, model);
        } else {
            if ("1".equals(goods.getState())) {
                model.addAttribute("promptmsg", "请先下架该商品,才能删除！");
                return list(request, model);
            }
        }

        if (goodsService.updateGoodsPublicIsdel(parameter_id, 0/* 0表示删除状态 */)) {
            model.addAttribute("promptmsg", "删除成功！");
        } else {
            model.addAttribute("promptmsg", "删除失败！");
        }
        return list(request, model);
    }

    @RequestMapping("batchDelete")
    public String batchDelete(HttpServletRequest request, Model model) {
        String parameter_id = request.getParameter("parameter_id");
        if (parameter_id == null || parameter_id.equals(""))
            return list(request, model);
        // 转成数组更新
        String[] ids = parameter_id.split(",");
        List<BigInteger> arr_goodsid = new ArrayList<BigInteger>();
        for (String id : ids) {
            BigInteger goods_id = new BigInteger(id);
            arr_goodsid.add(goods_id);
        }

        if (goodsService.batchUpdateGoodsPublicIsdel(arr_goodsid, 0/* 0表示删除状态 */)) {
            model.addAttribute("promptmsg", "批量删除成功");
        } else {
            model.addAttribute("promptmsg", "批量删除失败");
        }
        return list(request, model);
    }

    @RequestMapping("enableState")
    public String enableState(HttpServletRequest request, Model model) {
        String parameter_id = request.getParameter("parameter_id");
        if (parameter_id == null || parameter_id.equals(""))
            return list(request, model);
        BigInteger goods_id = new BigInteger(parameter_id);
        Goods goods = goodsService.get(parameter_id);
        if("0".equals(goods.getTotal_stock())){
        	model.addAttribute("promptmsg", "当前商品没有库存，请先补充库存");
        }else{
        	 if (goodsService.updateGoodsPublicState(goods_id, 1/* 1为上架 */)) {
                 model.addAttribute("promptmsg", "上架成功！");
             } else {
                 model.addAttribute("promptmsg", "上架失败！");
             }
        }
        return list(request, model);
    }

    @RequestMapping("batchEnableState")
    public String batchEnableState(HttpServletRequest request, Model model) {
        String parameter_id = request.getParameter("parameter_id");
        if (parameter_id == null || parameter_id.equals(""))
            return list(request, model);
        // 转成数组更新
        String[] ids = parameter_id.split(",");
        List<BigInteger> arr_goodsid = new ArrayList<BigInteger>();
        for (String id : ids) {
            BigInteger goods_id = new BigInteger(id);
            arr_goodsid.add(goods_id);
        }

        if (goodsService.batchUpdateGoodsPublicState(arr_goodsid, 1/* 1为上架 */)) {
            model.addAttribute("promptmsg", "批量上架成功");
        } else {
            model.addAttribute("promptmsg", "批量上架失败");
        }
        return list(request, model);
    }

    @RequestMapping("limitState")
    public String limitState(HttpServletRequest request, Model model) {
        String parameter_id = request.getParameter("parameter_id");
        if (parameter_id == null || parameter_id.equals(""))
            return list(request, model);
        BigInteger goods_id = new BigInteger(parameter_id);
        if (goodsService.updateGoodsPublicState(goods_id, 3/* 3下架 */)) {
            model.addAttribute("promptmsg", "下架成功！");
        } else {
            model.addAttribute("promptmsg", "下架失败！");
        }
        return list(request, model);
    }

    @RequestMapping("batchLimitState")
    public String batchLimitState(HttpServletRequest request, Model model) {
        String parameter_id = request.getParameter("parameter_id");
        if (parameter_id == null || parameter_id.equals(""))
            return list(request, model);
        // 转成数组更新
        String[] ids = parameter_id.split(",");
        List<BigInteger> arr_goodsid = new ArrayList<BigInteger>();
        for (String id : ids) {
            BigInteger goods_id = new BigInteger(id);
            arr_goodsid.add(goods_id);
        }

        if (goodsService.batchUpdateGoodsPublicState(arr_goodsid, 3/* 0为待发布 */)) {
            model.addAttribute("promptmsg", "批量下架成功");
        } else {
            model.addAttribute("promptmsg", "批量下架失败");
        }
        return list(request, model);
    }

    /**
     * 商品发布
     *
     * @param request
     * @param goodsPublic
     * @param model
     * @param advancelSale
     * @param fullSales
     * @param customAttrList
     * @return
     */
    @RequestMapping("insert")
    public String insert(HttpServletRequest request,
                         Goods goods, 
                         Model model,
                         AdvancelSale advancelSale,//定金销售
                         FullSales fullSales, //全额付款
                         CustomAttrListBean customAttrList,//商品属性
                         CustomSkuListBean customSkuList,//规格属性
                         CustomSkuAttrListBean customSkuAttrListBean,
                         RedirectAttributesModelMap modelMap) {

        ReqMsg msg = new ReqMsg();

        if (StringUtil.isEmpty(goods.getGoods_name())) {
            msg.setDesc("商品名称不能为空！");
            model.addAttribute("promptmsg", msg.getDesc());
            return addGoodsFail(request, model, goods.getCat_id(), goods.getCatName());
        }
       
        if(StringUtil.isEmpty(goods.getDivide_rate())){
        	goods.setDivide_rate(null);
        }else{
        	//验证收益比例
        	String regEx = "^(((\\d|[1-9]\\d)(\\.\\d{1,2})?)|100|100.0|100.00)$";
        	if(!goods.getDivide_rate().matches(regEx)){
        		model.addAttribute("promptmsg", "请输入正确的分成收益比例(1-100之间,精确到小数点后两位)!");
        		 return addGoodsFail(request, model, goods.getCat_id(), goods.getCatName());
        	}
        }

        if (StringUtil.isNullOrEmpty(customAttrList)) {
            msg.setDesc("请选择商品属性信息！");
            model.addAttribute("promptmsg", msg.getDesc());
            return addGoodsFail(request, model, goods.getCat_id(), goods.getCatName());
        }

        if (StringUtil.isNullOrEmpty(customSkuList.getCustomSkuList())) {
            msg.setDesc("请选择商品规格信息！");
            model.addAttribute("promptmsg", msg.getDesc());
            return addGoodsFail(request, model, goods.getCat_id(), goods.getCatName());
        }

        if (StringUtil.isNullOrEmpty(customSkuAttrListBean.getCustomSkuAttrList())) {
            msg.setDesc("请选择商品规格值！");
            model.addAttribute("promptmsg", msg.getDesc());
            return addGoodsFail(request, model, goods.getCat_id(), goods.getCatName());
        }

        // 判断是否是那种类型的商品金额
        String fp_1 = request.getParameter("fixed_price_1");
        String fp_a = request.getParameter("fixed_price_a");
        String fp_f = request.getParameter("fixed_price_f");

        // 如果售卖方式是一口价,则销售模式默认为0
        if ("0".equals(goods.getSale_mode())) {
            goods.setFixed_price(AmountUtil.yuanToFen(fp_1));
            goods.setPresale_model("0");
        } else {
            if ("1".equals(goods.getPresale_model())) {// 预售模式 全额
                goods.setFixed_price(AmountUtil.yuanToFen(fp_a));
            } else { // 订金
                goods.setFixed_price(AmountUtil.yuanToFen(fp_f));
                if ("0".equals(advancelSale.getPre_send_time_type())) {
                    msg.setDesc("请选择定金销售预付款比例");
                    model.addAttribute("promptmsg", msg.getDesc());
                    return addGoodsFail(request, model, goods.getCat_id(), goods.getCatName());
                }
            }
        }
        
        //验证金额是否符合最大值跟最小值
        String result = isTrueMoney(customSkuList.getCustomSkuList(),Integer.parseInt(goods.getFixed_price().trim()));
        if("false".equals(result.split(",")[0])){
        	msg.setDesc("售价金额应该介于SKU价格的最大值"+AmountUtil.fenToYuan(result.split(",")[1])+"----------最小值"+AmountUtil.fenToYuan(result.split(",")[2])+"之间!");
            model.addAttribute("promptmsg", msg.getDesc());
            return addGoodsFail(request, model, goods.getCat_id(), goods.getCatName());        	
        }
        
        //验证金额
        String reg = "(^[1-9]([0-9]+)?(\\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\\.[0-9]([0-9])?$)";
        if ("0".equals(goods.getFixed_price()) || !goods.getFixed_price().matches(reg)) {
            msg.setDesc("商品价格有误，重新填写！");
            model.addAttribute("promptmsg", msg.getDesc());
            return addGoodsFail(request, model, goods.getCat_id(), goods.getCatName());
        }

        // 判断发布状态
        if ("1".equals(goods.getState())) {//立即上架
            goods.setIn_date(StringUtil.fromDateH(new Date()));
        } else if ("0".equals(goods.getState())) {//待发布
            goods.setIn_date(null);
        }

        // 插入数据
        msg = this.goodsService.insertGoods(goods, advancelSale, fullSales, customAttrList, customSkuList,
                customSkuAttrListBean);

        // 插入定时发布任务
        if (msg.getCode() == 0) {
            if ("2".equals(goods.getState())) {
                Job job = new Job();
                job.setInfo_id(goods.getGoods_id());
                job.setModoule("goods");
                job.setIn_date(goods.getIn_date());
                jobService.insert(job);
            }
        } else {
            model.addAttribute("promptmsg", "发布商品失败");
            return addGoods(request, model);
        }

        modelMap.addFlashAttribute("promptmsg", msg.getDesc());
        model.addAttribute("catId", goods.getCat_id());
        //return addGoods(request, model);
        //return "forward:/admin/goods/list"; 
        return goUrl("goods/list"); 
    }

    /**
     * 商品信息更新
     *
     * @param request
     * @param goodsPublic
     * @param model
     * @param advancelSale
     * @param fullSales
     * @param customAttrList
     * @return
     */
    @RequestMapping("update")
    public String update(HttpServletRequest request,
                         Goods goods,
                         Model model,
                         AdvancelSale advancelSale,
                         FullSales fullSales,
                         CustomAttrListBean customAttrList,
                         CustomSkuListBean customSkuList,
                         CustomSkuAttrListBean customSkuAttrListBean) {

        ReqMsg msg = new ReqMsg();
        
        Goods oldGoods = goodsService.get(goods.getGoods_id());
        if (oldGoods == null) {
            return addGoodsFail(request, model, goods.getCat_id(), goods.getCatName());
        }
        if ("1".equals(oldGoods.getState())) {
            model.addAttribute("promptmsg", "请先下架该商品，才能修改！");
            return list(request, model);
        }
        
        if (StringUtil.isEmpty(goods.getGoods_name())) {
            msg.setDesc("商品名称不能为空！");
            model.addAttribute("promptmsg", msg.getDesc());
            return addGoodsFail(request, model, goods.getCat_id(), goods.getCatName());
        }
        //如果收益成分比例不填，则设置未null
        if(StringUtil.isEmpty(goods.getDivide_rate())){
        	goods.setDivide_rate(null);
        }else{
        	//验证收益比例
        	String regEx = "^(((\\d|[1-9]\\d)(\\.\\d{1,2})?)|100|100.0|100.00)$";
        	if(!goods.getDivide_rate().matches(regEx)){
        		model.addAttribute("promptmsg", "请输入正确的分成收益比例(1-100之间,精确到小数点后两位)!");
        		 return addGoodsFail(request, model, goods.getCat_id(), goods.getCatName());
        	}
        }

        if (StringUtil.isNullOrEmpty(customAttrList)) {
            msg.setDesc("请选择商品属性信息！");
            model.addAttribute("promptmsg", msg.getDesc());
            return addGoodsFail(request, model, goods.getCat_id(), goods.getCatName());
        }

        if (StringUtil.isNullOrEmpty(customSkuList.getCustomSkuList())) {
            msg.setDesc("请选择商品规格信息！");
            model.addAttribute("promptmsg", msg.getDesc());
            return addGoodsFail(request, model, goods.getCat_id(), goods.getCatName());
        }

        if (StringUtil.isNullOrEmpty(customSkuAttrListBean.getCustomSkuAttrList())) {
            msg.setDesc("请选择商品规格值！");
            model.addAttribute("promptmsg", msg.getDesc());
            return addGoodsFail(request, model, goods.getCat_id(), goods.getCatName());
        }

     
        // 判断是否是那种类型的商品金额
        String fp_1 = request.getParameter("fixed_price_1");
        String fp_a = request.getParameter("fixed_price_a");
        String fp_f = request.getParameter("fixed_price_f");

      
        
        // 如果售卖方式是一口价,则销售模式默认为0
        if ("0".equals(goods.getSale_mode())) {
            goods.setFixed_price(AmountUtil.yuanToFen(fp_1));
            goods.setPresale_model("0");
        } else {
            if ("1".equals(goods.getPresale_model())) {// 预售模式 全额
                goods.setFixed_price(AmountUtil.yuanToFen(fp_a));
            } else { // 订金
                goods.setFixed_price(AmountUtil.yuanToFen(fp_f));
                if ("0".equals(advancelSale.getPre_send_time_type())) {
                    msg.setDesc("请选择定金销售预付款比例");
                    model.addAttribute("promptmsg", msg.getDesc());
                    return addGoodsFail(request, model, goods.getCat_id(), goods.getCatName());
                }
            }
        }
        
        //验证金额是否符合最大值跟最小值
        String result = isTrueMoney(customSkuList.getCustomSkuList(),Integer.parseInt(goods.getFixed_price().trim()));
        if("false".equals(result.split(",")[0])){
        	msg.setDesc("售价金额应该介于SKU价格的最大值"+result.split(",")[1]+"-------最小值"+result.split(",")[2]+"之间!");
            model.addAttribute("promptmsg", msg.getDesc());
            return addGoodsFail(request, model, goods.getCat_id(), goods.getCatName());        	
        }
        
        String reg = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$";
        if ("0".equals(goods.getFixed_price()) || !goods.getFixed_price().matches(reg)) {
            msg.setDesc("商品价格有误，重新填写！");
            model.addAttribute("promptmsg", msg.getDesc());
            return addGoodsFail(request, model, goods.getCat_id(), goods.getCatName());
        }


        // 判断发布状态
        if ("1".equals(goods.getState())) {
            goods.setIn_date(StringUtil.fromDateH(new Date()));
        } else if ("0".equals(goods.getState())) {
            goods.setIn_date(null);
        }

        // 修改数据
        msg = this.goodsService.updateGoods(goods, advancelSale, fullSales, customAttrList,
                customSkuList, customSkuAttrListBean);

        // 插入定时发布任务
        if (msg.getCode() == 0) {
            jobService.deleteOne(oldGoods.getGoods_id()); // 先删除原来的发布
            if (goods.getState().equals("2")) { // 如果状态为2，重新发布
                Job job = new Job();
                job.setInfo_id(goods.getGoods_id());
                job.setModoule("goods");
                job.setIn_date(goods.getIn_date());
                jobService.insert(job);
            }
        } else {
            model.addAttribute("promptmsg", "更新失败");
            return addGoods(request, model);
        }

        model.addAttribute("promptmsg", msg.getDesc());
        return addGoods(request, model);
    }

    /**
     * 跳转添加页面
     *
     * @param model
     * @return
     */
    @RequestMapping("addGoods")
    public String addGoods(HttpServletRequest request, Model model) {
        return list(request, model);
    }

    /**
     * 添加商品失败
     *
     * @param request
     * @param model
     * @param catId
     * @param catName
     * @return
     */
    public String addGoodsFail(HttpServletRequest request, Model model, String catId, String catName) {
        return addGoods(request,model, catId, catName);
    }

    /**
     * 根据上级查找下级分类 类目
     *
     * @param request
     * @param pid
     * @param response
     */
    @RequestMapping("getCatByPid")
    public void getChildList(HttpServletRequest request, String pid, HttpServletResponse response) {
        List<Cat> catList = catService.selectCatByPid(pid);
        String listJson = JsonUtil.object2json(catList);
        outPrint(response, listJson);
    }

    /**
     * 添加商品页面
     *
     * @param model
     * @param catId
     * @param catName
     * @return
     */
    @RequestMapping("addGoodsJsp")
    public String addGoods(HttpServletRequest request,Model model, String catId, String catName) {
    	if(StringUtil.isEmpty(catId)&&StringUtil.isEmpty(catName)){
    		 return list(request, model);
    	}
        Cat cat = catService.getWithRate(catId);// 获取当前分类信息
        List<Brand> brandList = brandService.selectByCatId(catId);// 品牌
        List<Supply> supplyList = supplyService.selectByCatId(catId);// 供应商
        List<Appraisal> appraisalList = appraisalService.selectByCatId(catId);// 鉴定机构

        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("state", 1);
        List<ShipTemplate> shipTemplateList = logitempService.getlist(paraMap);//物流模板

        String gold = SysconfigFuc.getSysValue("cfg_gold_id");// 黄金类目
        String gold_price = SysconfigFuc.getSysValue("cfg_gold_price");// 当日金价
        
        //显示当日金价
        String pre_hold_time = SysconfigFuc.getSysValue("cfg_pre_hold_time");// 持续时间
        String pre_send_time = SysconfigFuc.getSysValue("cfg_pre_send_time");// 多少天内发后
        
        model.addAttribute("gold", gold);
        model.addAttribute("gold_price", gold_price);
        model.addAttribute("catName", catName);
        model.addAttribute("catId", catId);
        model.addAttribute("pre_hold_time", pre_hold_time);
        model.addAttribute("pre_send_time", pre_send_time);
        model.addAttribute("divide_rate", cat.getDivide_rate());

        model.addAttribute("brandList", brandList);
        model.addAttribute("supplyList", supplyList);
        model.addAttribute("appraisalList", appraisalList);
        model.addAttribute("shipTemplateList", shipTemplateList);
        model.addAttribute("payment", CommparaFuc.getParaList("advance_payment"));// 预付款

        model.addAttribute("stockTypeList", CommparaFuc.getParaList("cfg_stock_type"));// 库存计数
        return "goods/insert";
    }

    /**
     * 修改商品页面
     *
     * @param model
     * @param catId
     * @param catName
     * @return
     */
    @RequestMapping("edit")
    public String edit(HttpServletRequest request, Model model) {
        String parameter_id = request.getParameter("parameter_id");
        if (parameter_id == null || parameter_id.equals("")) {
            return list(request, model);
        }

        Goods goods = goodsService.get(parameter_id);
        if("1".equals(goods.getState())){
        	  model.addAttribute("promptmsg", "请先下架该商品,才能修改!");
              return list(request, model);
        }
        goods.setFixed_price(AmountUtil.fenToYuan(goods.getFixed_price()));// 分转元

        if (goods.getSale_mode().equals("1")) { // 预售模式
            if (goods.getPresale_model().equals("1")) { // 全额
                FullSales fullSales = fullSalesService.getByGoodsid(goods.getGoods_id());
                fullSales.setFull_presale_endtime(fullSales.getFull_presale_endtime().substring(0, 10));
                model.addAttribute("fullSales", fullSales);
            } else if (goods.getPresale_model().equals("2")) { // 定金
                AdvancelSale advanceSale = advancelSaleService.getByGoodsid(goods.getGoods_id());
                advanceSale.setPresale_endtime(advanceSale.getPresale_endtime().substring(0, 10));
                model.addAttribute("advanceSale", advanceSale);
            }
        }

        String catId = goods.getCat_id();
        Cat cat = catService.getWithRate(catId);// 获取当前分类信息
        if (StringUtil.isNullOrEmpty(cat)) {
            return list(request, model);
        }

        List<Brand> brandList = brandService.selectByCatId(catId);// 品牌
        List<Supply> supplyList = supplyService.selectByCatId(catId);// 供应商
        List<Appraisal> appraisalList = appraisalService.selectByCatId(catId);// 鉴定机构

        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("state", 1);
        paraMap.put("com_id", goods.getCom_id());
        List<ShipTemplate> shipTemplateList = logitempService.getlist(paraMap);

        String gold = SysconfigFuc.getSysValue("cfg_gold_id");// 黄金类目
        String gold_price = SysconfigFuc.getSysValue("cfg_gold_price");// 当日金价
        String pre_hold_time = SysconfigFuc.getSysValue("cfg_pre_hold_time");// 持续时间
        String pre_send_time = SysconfigFuc.getSysValue("cfg_pre_send_time");// 多少天内发后

        StringBuffer sbf = new StringBuffer();
        Cat cat1 = catService.getWithRate(cat.getParent_cat_id());// 获取当前分类信息
        sbf.append(cat1.getParent_name() + ">" + cat.getParent_name() + ">" + cat.getCat_name());

        model.addAttribute("gold", gold);
        model.addAttribute("gold_price", gold_price);
        model.addAttribute("catName", sbf.toString());
        model.addAttribute("catId", catId);
        model.addAttribute("divide_rate", goods.getDivide_rate());

        model.addAttribute("pre_hold_time", pre_hold_time);
        model.addAttribute("pre_send_time", pre_send_time);
        model.addAttribute("brandList", brandList);
        model.addAttribute("supplyList", supplyList);
        model.addAttribute("appraisalList", appraisalList);
        model.addAttribute("shipTemplateList", shipTemplateList);
        model.addAttribute("payment", CommparaFuc.getParaList("advance_payment"));// 预付款
        model.addAttribute("stockTypeList", CommparaFuc.getParaList("cfg_stock_type"));// 库存计数
        model.addAttribute("goods", goods);

        return "goods/update";
    }
    
    /**
     * 查看商品页面
     *
     * @param model
     * @param catId
     * @param catName
     * @return
     */
    @RequestMapping("lookDetail")
    public String lookDetail(HttpServletRequest request, Model model) {
    	String parameter_id = request.getParameter("parameter_id");
    	if (parameter_id == null || parameter_id.equals("")) {
    		return list(request, model);
    	}
    	
    	Goods goods = goodsService.get(parameter_id);
    	goods.setFixed_price(AmountUtil.fenToYuan(goods.getFixed_price()));// 分转元
    	
    	if (goods.getSale_mode().equals("1")) { // 预售模式
    		if (goods.getPresale_model().equals("1")) { // 全额
    			FullSales fullSales = fullSalesService.getByGoodsid(goods.getGoods_id());
    			fullSales.setFull_presale_endtime(fullSales.getFull_presale_endtime().substring(0, 10));
    			model.addAttribute("fullSales", fullSales);
    		} else if (goods.getPresale_model().equals("2")) { // 定金
    			AdvancelSale advanceSale = advancelSaleService.getByGoodsid(goods.getGoods_id());
    			advanceSale.setPresale_endtime(advanceSale.getPresale_endtime().substring(0, 10));
    			model.addAttribute("advanceSale", advanceSale);
    		}
    	}
    	
    	String catId = goods.getCat_id();
    	Cat cat = catService.getWithRate(catId);// 获取当前分类信息
    	if (StringUtil.isNullOrEmpty(cat)) {
    		return list(request, model);
    	}
    	
    	List<Brand> brandList = brandService.selectByCatId(catId);// 品牌
    	List<Supply> supplyList = supplyService.selectByCatId(catId);// 供应商
    	List<Appraisal> appraisalList = appraisalService.selectByCatId(catId);// 鉴定机构
    	
    	HashMap<String, Object> paraMap = new HashMap<String, Object>();
    	paraMap.put("state", 1);
    	paraMap.put("com_id", goods.getCom_id());
    	List<ShipTemplate> shipTemplateList = logitempService.getlist(paraMap);
    	
    	String pre_hold_time = SysconfigFuc.getSysValue("cfg_pre_hold_time");// 持续时间
    	String pre_send_time = SysconfigFuc.getSysValue("cfg_pre_send_time");// 多少天内发后
    	
    	StringBuffer sbf = new StringBuffer();
    	Cat cat1 = catService.getWithRate(cat.getParent_cat_id());// 获取当前分类信息
    	sbf.append(cat1.getParent_name() + ">" + cat.getParent_name() + ">" + cat.getCat_name());
    	
    	model.addAttribute("catName", sbf.toString());
    	model.addAttribute("catId", catId);
    	model.addAttribute("divide_rate", goods.getDivide_rate());
    	
    	model.addAttribute("pre_hold_time", pre_hold_time);
    	model.addAttribute("pre_send_time", pre_send_time);
    	model.addAttribute("brandList", brandList);
    	model.addAttribute("supplyList", supplyList);
    	model.addAttribute("appraisalList", appraisalList);
    	model.addAttribute("shipTemplateList", shipTemplateList);
    	model.addAttribute("payment", CommparaFuc.getParaList("advance_payment"));// 预付款
    	model.addAttribute("stockTypeList", CommparaFuc.getParaList("cfg_stock_type"));// 库存计数
    	model.addAttribute("goods", goods);
    	
    	return "goods/look";
    }

    /**
     * @param request
     * @param response
     */
    @RequestMapping("getAttrByCatId")
    public void getAttrByCatId(HttpServletRequest request, String goods_id, String catId,
                               HttpServletResponse response) {

        Cat cat = catService.getWithRate(catId);
        Map map = null;
        Goods goods = null;
        //商品发布
        if (goods_id == null || goods_id.length() == 0) {
            map = attrService.getAttrByCatId(cat, goods_id);
        } else {//修改商品
            goods = goodsService.get(goods_id);
            if (goods == null){
            	 return;
            }
               
            catId = goods.getCat_id();
            map = attrService.getAttrByCatId(cat, goods_id);
            List<AttrVo> attrVoList = (List<AttrVo>) map.get("attrVoList");
            List<AttrVo> skuList = (List<AttrVo>) map.get("skuList");

            BigInteger b_goods_id = new BigInteger(goods_id);
            List<CustomAttrNode> listCustomAttr = customAttrService.getByGoodsid(b_goods_id);

            if (listCustomAttr != null && listCustomAttr.size() > 0) {

				/* ******* attrVoList begin ******* */
                Map<String, AttrVo> mapVoList = new HashMap<String, AttrVo>();
                for (AttrVo vo : attrVoList) {
                    mapVoList.put(vo.getAttr_id(), vo);
                }

                for (CustomAttrNode node : listCustomAttr) {
                    if (node.getAttr_type() != 0)
                        continue;
                    AttrVo vo = mapVoList.get(node.getAttr_id().toString());
                    if (vo != null) {
                        List<AttrValue> listAttrValue = vo.getData(); // 配表中的值
                        Map<String, AttrValue> mapAttrValue = new HashMap<String, AttrValue>();
                        for (AttrValue nodeAttr : listAttrValue) {
                            mapAttrValue.put(nodeAttr.getAttr_value_id(), nodeAttr);
                        }
                        List<CustomAttrValueNode> listCustomAttrValue = customAttrValueService
                                .getByCustomattridAndAttrid(node.getCustom_attr_id(), node.getAttr_id());// 商品表关联的值
                        for (CustomAttrValueNode cvn : listCustomAttrValue) {
                        	if(StringUtil.isNullOrEmpty(cvn.getCustom_attr_value())){
                        		
                        	}else{
                        		AttrValue av = mapAttrValue.get(cvn.getAv_id().toString());
                                if (av != null) { // 如果值是配表中有的，直接设置状态即可
                                    av.setState("100"); // 100表示选中
                                } else { // 否则的话，插入一条记录到配表
                                    AttrValue attrValue = new AttrValue("", "", cvn.getSav_id().toString(),
                                            cvn.getAttr_id().toString(), cvn.getCustom_attr_value(), "", "100");
                                    listAttrValue.add(attrValue);
                                }
                        	}
                        }
                    }
                }
                /* ******* attrVoList end ******* */

				/* ******* skuList begin ******* */
                Map<String, AttrVo> mapSkuList = new HashMap<String, AttrVo>();
                for (AttrVo vo : skuList) {
                    mapSkuList.put(vo.getAttr_id(), vo);
                }

                for (CustomAttrNode node : listCustomAttr) {
                    if (node.getAttr_type() != 1)
                        continue;
                    AttrVo vo = mapSkuList.get(node.getAttr_id().toString());
                    if (vo != null) {
                        List<AttrValue> listAttrValue = vo.getData(); // 配表中的值
                        Map<String, AttrValue> mapAttrValue = new HashMap<String, AttrValue>();
                        for (AttrValue nodeAttr : listAttrValue) {
                            mapAttrValue.put(nodeAttr.getAttr_value_id(), nodeAttr);
                        }
                        List<CustomAttrValueNode> listCustomAttrValue = customAttrValueService
                                .getByCustomattridAndAttrid(node.getCustom_attr_id(), node.getAttr_id());// 商品表关联的值
                        for (CustomAttrValueNode cvn : listCustomAttrValue) {
                        	if(StringUtil.isNullOrEmpty(cvn.getCustom_attr_value())){
                        		
                        	}else{
                        		AttrValue av = mapAttrValue.get(cvn.getAv_id().toString());
                                if (av != null) { // 如果值是配表中有的，直接设置状态即可
                                    av.setState("100"); // 100表示选中
                                } else { // 否则的话，插入一条记录到配表
                                    AttrValue attrValue = new AttrValue("", "", cvn.getAv_id().toString(),
                                            cvn.getAttr_id().toString(), cvn.getCustom_attr_value(), "", "100");
                                    listAttrValue.add(attrValue);
                                }
                        	}
                        }
                    }
                }
				/* ******* skuList end ******* */
            }
        }
        String isGold = "0";
        String isManualFee = "0";
        String manual_fee = null;
        String gold_price = SysconfigFuc.getSysValue("cfg_gold_price");
        //发布商品
        if(StringUtil.isNullOrEmpty(goods)){
        	if(!"0".equals(cat.getManual_fee())){
        		manual_fee = AmountUtil.fenToYuan(cat.getManual_fee());
        		isManualFee = "1";
        	}
        }else{
        	manual_fee = AmountUtil.fenToYuan(goods.getManual_fee());
        	if(!"0.00".equals(manual_fee)){
        		isManualFee = "1";
        	}
        }
        
        String gold = SysconfigFuc.getSysValue("cfg_gold_id");
        
        if (cat.getLevel_cat().indexOf(gold) != -1) {
            isGold = "1";
        }
        map.put("isGold", isGold);
        map.put("isManualFee", isManualFee);
        map.put("gold_price", gold_price);
        map.put("manual_fee", manual_fee);
        String listJson = JsonUtil.object2json(map);
        outPrint(response, listJson);
    }

    /**
     * 页面（重量、体积、手寸）回显时请求接口（sku)
     *
     * @param goods_id
     * @return
     */
    @RequestMapping(value = "getGoodsAttr", produces = "application/json;charset=UTF-8;")
    @ResponseBody
    public String getGoodsAttr(String goods_id) {

        List<SkuNode> listSku = skuService.getByGoodsid(goods_id);
        if (listSku == null || listSku.size() == 0) {
            return new Gson().toJson(new ReqMsg(0, "succ", new ArrayList<SkuVo>()));
        }

        // 将SkuNode（数据节点）转换成接口节点
        List<SkuVo> listVo = new ArrayList<SkuVo>();
        for (SkuNode node : listSku) {
            String attr_id = "";
            String[] arrStr = node.getSku_str().split(",");
            if (arrStr != null && arrStr.length > 0) {
                List<String> listBig = new ArrayList<String>();
                for (String str : arrStr) {
                    String newStr = str.replaceAll("^(0+)", ""); // 去除左边0
                    listBig.add(new String(newStr)); 
                }
                for (String b : listBig) {
                    attr_id += b + "#";
                }
                if (attr_id.length() > 0) {
                    attr_id = attr_id.substring(0, attr_id.length() - 1); // 去除最后一个"#"
                }
            }

            listVo.add(new SkuVo(node.getSku_id(), node.getGoods_id(), attr_id, AmountUtil.fenToYuan(node.getPrice()),
                    AmountUtil.fenToYuan(node.getManual_fee()),
                    AmountUtil.fenToYuan(node.getSale_price()), node.getStock()));
        }

        return new Gson().toJson(new ReqMsg(0, "succ", listVo));
    }

    //验证售价金额的最大值跟最小值
    public String isTrueMoney(List<GoodsCustomSku> list,Integer money){
    	
    	List list1 = new ArrayList();
    	for(GoodsCustomSku goodsCustomSku : list){
    		String arr[] = goodsCustomSku.getAttr_value().split("###");
    		list1.add(Integer.parseInt(AmountUtil.yuanToFen((arr[arr.length - 2]).trim())));//添加商品价格到list里面  固定值
    	}
	    Integer min = (Integer) Collections.min(list1);
	    Integer max = (Integer)Collections.max(list1);
	    
	    if(min <= money &&  max >= money){
	    	return "true"+","+max+","+min;
	    }else{
	    	return "false"+","+max+","+min;
	    }
    }
}
