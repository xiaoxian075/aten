package com.aten.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aten.function.CatFuc;
import com.aten.function.SysconfigFuc;
import com.aten.model.bean.ZtreeBean;
import com.aten.model.orm.*;
import com.aten.service.*;
import com.communal.constants.Constant;
import com.communal.util.AmountUtil;
import com.communal.util.JsonUtil;
import com.communal.util.R;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

@Controller
@RequestMapping("admin/goodsclass")
public class GoodsClassController extends BaseController {

    private static final Logger logger = Logger.getLogger(GoodsClassController.class);

    @Autowired
    private GoodsClassService goodsClassService;
    @Autowired
    private BrandService brandService;
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

    @ModelAttribute
    public void populateModel(HttpServletRequest request, Model model) {

        initialHiddenVal(request, model);
        //顶级地区
        model.addAttribute("cfg_goods_top", SysconfigFuc.getSysValue("cfg_goods_top"));
    }

    @RequestMapping("list")
    public String list(HttpServletRequest request, Model model) {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("module", "goods");
        //搜索封装
        paraMap = searchMap(request, paraMap, model);

        int count = this.catService.getCount(paraMap);
        //分页工具
        paraMap = pageTool(request, count, model, paraMap);
        List<Cat> goodsclassList = this.catService.getListAndDivideRate(paraMap);
        for (int i=0;i<goodsclassList.size();i++) {
            List<CatRate> catRateList=catRateService.getByCatId(goodsclassList.get(i).getCat_id());
            if(catRateList!=null&&catRateList.size()>0){
                goodsclassList.get(i).setManual_fee(AmountUtil.formatMoney(catRateList.get(0).getManual_fee()));
            }

            goodsclassList.get(i).setParent_level_name(CatFuc.getCatNameStr(goodsclassList.get(i).getLevel_cat(),22,Constant.POS));
        }
        model.addAttribute("goodsclassList", goodsclassList);
        return "goodsclass/list";
    }

    /**
     * 选择分类
     */
    @RequestMapping("/select")
    public void select(HttpServletResponse response) {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("module", "goods");
        //查询列表数据
        List<Cat> catList = catService.getList(paraMap);
        //将数据转为 ztree格式
        List<ZtreeBean> ztreeBeans = new ArrayList<>();
        for (Cat cat : catList) {
            ZtreeBean ztreeBean = new ZtreeBean();
            ztreeBean.setId(cat.getCat_id());
            ztreeBean.setName(cat.getCat_name());
            ztreeBean.setpId(cat.getParent_cat_id());
            ztreeBeans.add(ztreeBean);
        }
        String listJson = JsonUtil.object2json(R.ok().put("page", ztreeBeans));

        outPrint(response, listJson);
    }

    //获取父类中文串
    private String setParent_level_name(Cat cat) {
        String parentIdStr = CatFuc.getParentIdStr(SysconfigFuc.getSysValue("cfg_goods_top"), cat.getLevel_cat());
        String parent_level_name = CatFuc.getCatNameStr(parentIdStr);
        return parent_level_name;
    }

    @RequestMapping("add")
    public String add(HttpServletRequest request, Model model) {
        return "goodsclass/insert";
    }

    @RequestMapping("edit")
    public String edit(HttpServletRequest request, Model model) {
        String parameter_id = request.getParameter("parameter_id");
        if (parameter_id == null || parameter_id.equals("")) return list(request, model);
        Cat cat = catService.get(parameter_id);
        cat.setParent_cat_id(CatFuc.getCatNameStr(cat.getLevel_cat(),22,Constant.POS));
        model.addAttribute("cat", cat);
        //分类关联的分成比例
        List<CatRate> catRateList = getCatRateList(request, model, parameter_id);
        if (catRateList != null && catRateList.size() > 0) {
//            cat.setDivide_rate(catRateList.get(0).getDivide_rate());
            catRateList.get(0).setManual_fee(AmountUtil.formatMoney(catRateList.get(0).getManual_fee()));
            model.addAttribute("catRate", catRateList.get(0));
        }
        //分类关联的品牌
        List<CatBrand> catBrandList = getCatBrandList(request, model, parameter_id);
        //分类关联的供应商
        List<CatSupply> catSupplyList = getCatSupplyList(request, model, parameter_id);
        //分类关联的鉴定机构
        List<CatAppraisal> catAppraisalList = getAppraisalList(request, model, parameter_id);
        //分类关联的规格
        List<CatAttr> catSkuAttrList = getSkuAttrList(request, model, parameter_id);
        //分类关联的规格
        List<CatAttr> catKeyAttrList = getKeyAttrList(request, model, parameter_id);
        model.addAttribute("catBrandList", catBrandList);
        model.addAttribute("catSupplyList", catSupplyList);
        model.addAttribute("catAppraisalList", catAppraisalList);
        model.addAttribute("catSkuAttrList", catSkuAttrList);
        model.addAttribute("catKeyAttrList", catKeyAttrList);
        model.addAttribute("isEdit", "true");
        model.addAttribute("parameter_id",parameter_id);
        //	model.addAttribute("cfg_goods_top", SysconfigFuc.getSysValue("cfg_goods_top"));
        return "goodsclass/update";
    }

    @RequestMapping("info")
    public String info(HttpServletRequest request, Model model) {
        String parameter_id = request.getParameter("parameter_id");
        if (parameter_id == null || parameter_id.equals("")) return list(request, model);
        Cat cat = catService.get(parameter_id);
        //上级id串
        cat.setParent_cat_id(CatFuc.getCatNameStr(cat.getLevel_cat(),22,Constant.POS));
        model.addAttribute("cat", cat);
        //分类关联的分成比例
        List<CatRate> catRateList = getCatRateList(request, model, parameter_id);
        if (catRateList != null && catRateList.size() > 0) {
//            cat.setDivide_rate(catRateList.get(0).getDivide_rate());
            catRateList.get(0).setManual_fee(AmountUtil.formatMoney(catRateList.get(0).getManual_fee()));
            model.addAttribute("catRate", catRateList.get(0));
        }
        //分类关联的品牌
        List<CatBrand> catBrandList = getCatBrandList(request, model, parameter_id);
        //分类关联的供应商
        List<CatSupply> catSupplyList = getCatSupplyList(request, model, parameter_id);
        //分类关联的鉴定机构
        List<CatAppraisal> catAppraisalList = getAppraisalList(request, model, parameter_id);
        //分类关联的规格
        List<CatAttr> catSkuAttrList = getSkuAttrList(request, model, parameter_id);
        //分类关联的规格
        List<CatAttr> catKeyAttrList = getKeyAttrList(request, model, parameter_id);
        model.addAttribute("catBrandList", catBrandList);
        model.addAttribute("catSupplyList", catSupplyList);
        model.addAttribute("catAppraisalList", catAppraisalList);
        model.addAttribute("catSkuAttrList", catSkuAttrList);
        model.addAttribute("catKeyAttrList", catKeyAttrList);
        return "goodsclass/info";
    }

    private List<CatRate> getCatRateList(HttpServletRequest request, Model model, String cat_id) {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        //搜索封装
        paraMap = searchMap(request, paraMap, model);
        paraMap.put("cat_id", cat_id);
        //分页工具
        List<CatRate> catRateList = catRateService.getList(paraMap);
        return catRateList;
    }

    /**
     * @param
     * @author chenyi
     * @Description 获取分类与品牌关联的列表
     * @date 2017/6/30 18:25
     **/
    public List<CatBrand> getCatBrandList(HttpServletRequest request, Model model, String cat_id) {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        //搜索封装
        paraMap = searchMap(request, paraMap, model);
        paraMap.put("cat_id", cat_id);
        //分页工具
        List<CatBrand> catBrandList = catBrandService.getList(paraMap);
        return catBrandList;
    }

    /**
     * @param
     * @author chenyi
     * @Description 获取分类与供应商关联的列表
     * @date 2017/6/30 18:25
     **/
    public List<CatSupply> getCatSupplyList(HttpServletRequest request, Model model, String cat_id) {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        //搜索封装
        paraMap = searchMap(request, paraMap, model);
        paraMap.put("cat_id", cat_id);
        //分页工具
        List<CatSupply> catSupplyList = catSupplyService.getList(paraMap);
        return catSupplyList;
    }

    /**
     * @param
     * @author chenyi
     * @Description 获取分类与鉴定机构关联的列表
     * @date 2017/6/30 18:25
     **/
    public List<CatAppraisal> getAppraisalList(HttpServletRequest request, Model model, String cat_id) {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        //搜索封装
        paraMap = searchMap(request, paraMap, model);
        paraMap.put("cat_id", cat_id);
        List<CatAppraisal> catAppraisalList = catAppraisalService.getList(paraMap);
        return catAppraisalList;
    }

    /**
     * @param
     * @author chenyi
     * @Description 获取分类与规格关联的列表
     * @date 2017/6/30 18:25
     **/
    public List<CatAttr> getSkuAttrList(HttpServletRequest request, Model model, String cat_id) {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        //搜索封装
        paraMap = searchMap(request, paraMap, model);
        paraMap.put("cat_id", cat_id);
        paraMap.put("is_sku", "1");
        //分页工具
        List<CatAttr> catAttrList = catAttrService.getList(paraMap);
        return catAttrList;
    }

    /**
     * @param
     * @author chenyi
     * @Description 获取分类属性关联的列表
     * @date 2017/6/30 18:25
     **/
    public List<CatAttr> getKeyAttrList(HttpServletRequest request, Model model, String cat_id) {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        //搜索封装
        paraMap = searchMap(request, paraMap, model);
        paraMap.put("cat_id", cat_id);
        paraMap.put("is_key", "1");
        //分页工具
        List<CatAttr> catAttrList = catAttrService.getList(paraMap);
        return catAttrList;
    }

    @RequestMapping("insert")
    public String insert(Cat cat, HttpServletRequest request, Model model ,RedirectAttributesModelMap modelMap) {
        String regEx = "^(((\\d|[1-9]\\d)(\\.\\d{1,2})?)|100|100.0|100.00)$";
        String divede_rate = cat.getDivide_rate();
        if (!divede_rate.matches(regEx)) {
            model.addAttribute("promptmsg", "请输入正确的分成收益比例(1-100之间,精确到小数点后两位)！");
            return add(request, model);
        }
        //验证金额
        regEx="(^[1-9]([0-9]+)?(\\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\\.[0-9]([0-9])?$)";
        if(!cat.getManual_fee().matches(regEx)){
            model.addAttribute("promptmsg","请输入有效的手工费金额！");
            return add(request,model);
        }
        goodsClassService.saveInfo(request, cat);
        modelMap.addAttribute("promptmsg", "商品分类添加成功！");
        //顶级地区
        // model.addAttribute("cfg_goods_top", SysconfigFuc.getSysValue("cfg_goods_top"));
        //return add(request, model);
        return goUrl("goodsclass/add");
    }

    @RequestMapping("update")
    public String update(Cat cat, HttpServletRequest request, Model model) {
        String regEx = "^(((\\d|[1-9]\\d)(\\.\\d{1,2})?)|100|100.0|100.00)$";
        String divede_rate = cat.getDivide_rate();
        if (!divede_rate.matches(regEx)) {
            model.addAttribute("promptmsg", "请输入正确的分成收益比例(1-100之间,精确到小数点后两位)！");
            return edit(request, model);
        }
        //验证金额
        regEx="(^[1-9]([0-9]+)?(\\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\\.[0-9]([0-9])?$)";
        if(!cat.getManual_fee().matches(regEx)){
            model.addAttribute("promptmsg","请输入有效的手工费金额！");
            return edit(request,model);
        }
        //上级分类
//        String parent_id = SysconfigFuc.getSysValue("cfg_goods_top");
//        String parent_cat_id = cat.getParent_cat_id();
//        if (!"".equals(parent_cat_id) && parent_cat_id.length() >= 10) {
//            parent_id = parent_cat_id.substring(parent_cat_id.length() - 10, parent_cat_id.length());
//        }
        //选择的上级是否为子孙
//        Boolean flag = catService.isSon(cat.getCat_id(), parent_id);
//        //不能移动到子孙目录中
//        if (flag || cat.getCat_id().equals(parent_id)) {
//            model.addAttribute("promptmsg", "修改失败！(上级分类不能为自身或下级分类！)");
//            return edit(request, model);
//        }
        goodsClassService.updateInfo(request, cat);
        model.addAttribute("promptmsg", "商品分类修改成功！");
        return list(request, model);
    }


    /*禁用分类*/
    @RequestMapping("limitState")
    public String limitState(HttpServletRequest request, Model model) {
        String parameter_id = request.getParameter("parameter_id");
        if (parameter_id == null || parameter_id.equals("")) return list(request, model);
        //禁用下级
        catService.limitState(parameter_id);
        model.addAttribute("promptmsg", "禁用成功！");
        return list(request, model);
    }

    /**
     * @param
     * @author linjunqin
     * @Description 批量禁用
     * @date 2017-5-3 下午4:08:03
     */
    @RequestMapping("batchLimitState")
    public String batchLimitState(HttpServletRequest request, Model model) {
        String parameter_id = request.getParameter("parameter_id");
        if (parameter_id == null || parameter_id.equals("")) return list(request, model);
        catService.batchLimitState(parameter_id);
        model.addAttribute("promptmsg", "批量禁用成功！");
        return list(request, model);
    }

    /**
     * @param
     * @author chenyi
     * @Description 启用
     * @date 2017-7-4 下午4:04:28
     */
    @RequestMapping("enableState")
    public String enableState(HttpServletRequest request, Model model) {
        String parameter_id = request.getParameter("parameter_id");
        if (parameter_id == null || parameter_id.equals("")) return list(request, model);
        catService.enableState(parameter_id);
        model.addAttribute("promptmsg", "启用成功！");
        return list(request, model);
    }

    /**
     * @param
     * @author chenyi
     * @Description 批量启用
     * @date 2017-7-4 下午4:08:03
     */
    @RequestMapping("batchEnableState")
    public String batchEnableState(HttpServletRequest request, Model model) {
        String parameter_id = request.getParameter("parameter_id");
        if (parameter_id == null || parameter_id.equals("")) return list(request, model);
        catService.batchEnableState(parameter_id);
        model.addAttribute("promptmsg", "批量启用成功！");
        return list(request, model);
    }

    @RequestMapping("delete")
    public String delete(HttpServletRequest request, Model model) {
        String parameter_id = request.getParameter("parameter_id");
        if (parameter_id == null || parameter_id.equals("")) return list(request, model);
        this.catService.deleteGoodsClass(parameter_id);
        model.addAttribute("promptmsg","操作成功！（被引用的分类无法删除！）");

        return list(request, model);
    }
    @RequestMapping("setLevelCat")
    public String  setLevelCat(HttpServletRequest request, Model model){
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("module", "goods");
        List<Cat> catgoodsList=catService.getList(paraMap);
        for(Cat catgoods:catgoodsList){
           String str1 = catgoods.getLevel_cat();
           //str1="1111111111,3333333333,6666666666,ad68bf24c0,14aa54242a,b64f7408cc";
//            StringBuilder sb = new StringBuilder(str1);//构造一个StringBuilder对象
//            sb.insert(22, "6666666666,");//在指定的位置1，插入指定的字符串
//            str1 = sb.toString();
            String start=str1.substring(0,21);
            String end=str1.substring(32,str1.length());
            catgoods.setLevel_cat(start+end);
            catService.update(catgoods);
        }
        return list(request, model);
    }


    @RequestMapping("sort")
    public String sort(HttpServletRequest request,Model model){
        String sort_id = request.getParameter("sort_id");
        String sort_val = request.getParameter("sort_val");
        if(sort_id==null || sort_val==null) return null;
        // 转成maplist
        List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
        this.catService.updateBatch(sortMapList);
        model.addAttribute("promptmsg","排序成功！");
        return list(request, model);
    }
}
