package com.aten.controller;

import com.aten.function.CatFuc;
import com.aten.function.SysconfigFuc;
import com.aten.model.orm.*;
import com.aten.service.*;
import com.communal.constants.Constant;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/pre")
public class PreController extends BaseController {

    private static final Logger logger = Logger.getLogger(PreController.class);

    @Autowired
    private CatService catService;
    @Autowired
    private PreService preService;
    @Autowired
    private PreGoodscatService preGoodscatService;


    @ModelAttribute
    public void populateModel(HttpServletRequest request, Model model) {

        initialHiddenVal(request, model);
        //顶级地区
        model.addAttribute("cfg_pre_top", SysconfigFuc.getSysValue("cfg_pre_top"));
    }

    @RequestMapping("list")
    public String list(HttpServletRequest request, Model model) {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("module", "pre");
        //paraMap.put("default", SysconfigFuc.getSysValue("cfg_pre_default"));
        //搜索封装
        paraMap = searchMap(request, paraMap, model);
        int count = this.catService.getCount(paraMap);
        //分页工具
        paraMap = pageTool(request, count, model, paraMap);
        List<Cat> preList = this.catService.getList(paraMap);
        for (Cat cat : preList) {
           // String str = setParent_level_name(cat);
           // str = str.replace(",", " > ");
        	String catNameStr = CatFuc.getCatNameStr(cat.getLevel_cat(), 22, Constant.POS);
            cat.setParent_level_name(catNameStr);
        }
        model.addAttribute("preList", preList);
        return "pre/list";
    }

    //获取父类中文串
/*    private String setParent_level_name(Cat cat) {
        String parentIdStr = CatFuc.getParentIdStr(SysconfigFuc.getSysValue("cfg_pre_top"), cat.getLevel_cat());
        String parent_level_name = CatFuc.getCatNameStr(parentIdStr);
        return parent_level_name;
    }*/

    @RequestMapping("add")
    public String add(HttpServletRequest request, Model model) {
        return "pre/insert";
    }

    @RequestMapping("edit")
    public String edit(HttpServletRequest request, Model model) {
        //分类关联的品牌
        String parameter_id = request.getParameter("parameter_id");
        if (parameter_id == null || parameter_id.equals("")) return list(request, model);
        Cat cat = catService.get(parameter_id);
        //上级id串
        cat.setParent_cat_id( CatFuc.getCatNameStr(cat.getLevel_cat(), 22, Constant.POS));
        model.addAttribute("cat", cat);
        model.addAttribute("isEdit", "true");
        List<PreGoodscat> preGoodsList = preGoodsList(request, model, parameter_id);
        model.addAttribute("preGoodsList", preGoodsList);
        //	model.addAttribute("cfg_pre_top", SysconfigFuc.getSysValue("cfg_pre_top"));
        return "pre/update";
    }

    private List<PreGoodscat> preGoodsList(HttpServletRequest request, Model model, String precat_id) {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        //搜索封装
        paraMap = searchMap(request, paraMap, model);
        paraMap.put("precat_id", precat_id);
        //分页工具
        List<PreGoodscat> preGoodscatList = preGoodscatService.getList(paraMap);
        return preGoodscatList;
    }

    @RequestMapping("info")
    public String info(HttpServletRequest request, Model model) {
        String parameter_id = request.getParameter("parameter_id");
        if (parameter_id == null || parameter_id.equals("")) return list(request, model);
        Cat cat = catService.get(parameter_id);
        //上级id串
        String parentIdStr = CatFuc.getParentIdStr(SysconfigFuc.getSysValue("cfg_pre_top"), cat.getLevel_cat());
        cat.setParent_cat_id(parentIdStr);
        String catNameStr = CatFuc.getCatNameStr(cat.getLevel_cat(), 22, Constant.POS);
        cat.setParent_name(catNameStr);
        model.addAttribute("cat", cat);
        List<PreGoodscat> preGoodsList = preGoodsList(request, model, parameter_id);
        model.addAttribute("preGoodsList", preGoodsList);
        return "pre/info";
    }


    @RequestMapping("insert")
    public String insert(Cat cat, HttpServletRequest request, Model model,RedirectAttributesModelMap modelMap) {
        preService.saveInfo(request, cat);
        modelMap.addAttribute("promptmsg", "前台分类添加成功！");
        //顶级地区
        // model.addAttribute("cfg_pre_top", SysconfigFuc.getSysValue("cfg_pre_top"));
        //return add(request, model);
        return goUrl("pre/add");
    }

    @RequestMapping("update")
    public String update(Cat cat, HttpServletRequest request, Model model) {
        //上级分类
       // String parent_id = SysconfigFuc.getSysValue("cfg_pre_top");
        //String parent_cat_id = cat.getParent_cat_id();
//        if(parent_cat_id.length()==10){
//            parent_id=parent_cat_id;
//        }
//        if (parent_cat_id.length() > 10) {
//            if (",".equals(parent_cat_id.substring(parent_cat_id.length() - 1, parent_cat_id.length()))) {
//                parent_cat_id = parent_cat_id.substring(0, parent_cat_id.length() - 1);
//            }
//            if (!"".equals(parent_cat_id) && parent_cat_id.length() >= 10) {
//                parent_id = parent_cat_id.substring(parent_cat_id.length() - 10, parent_cat_id.length());
//            }
//        }
//       cat.setParent_cat_id(parent_id);
//        String level_cat=catService.get(cat.getCat_id()).getLevel_cat();
//        //选择的上级是否为子孙
//       Boolean flag = catService.isSon(cat.getCat_id(), parent_id);
//        //Boolean flag = catService.isSon(level_cat,parent_id);
//        //不能移动到子孙目录中
//        if (flag || cat.getCat_id().equals(parent_id)) {
//            model.addAttribute("promptmsg", "修改失败！(上级分类不能为自身或下级分类！)");
//            return edit(request, model);
//        }
        preService.updateInfo(request, cat);
        model.addAttribute("promptmsg", "前台分类修改成功！");
        return list(request, model);
    }


    /*禁用分类*/
    @RequestMapping("limitState")
    public String limitState(HttpServletRequest request, Model model) {
        String parameter_id = request.getParameter("parameter_id");
        if (parameter_id.equals("") || parameter_id == null) return list(request, model);
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
        this.catService.deletePre(parameter_id);
        model.addAttribute("promptmsg", "删除成功！");
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
        model.addAttribute("promptmsg","分类排序成功！");
        return list(request, model);
    }
}
