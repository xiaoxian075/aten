/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  Tue Jan 03 16:26:02 CST 2017  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: Cat.java 
 * Author:linjunqin
 */
package com.aten.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aten.function.SysconfigFuc;
import com.aten.model.bean.TreeTableBean;
import com.aten.model.orm.Cat;
import com.aten.service.CatService;
import com.communal.util.JsonUtil;
import com.communal.util.RandomCharUtil;


@Controller
@RequestMapping("/admin/cat")
public class CatController extends BaseController{

	private static final Logger logger = Logger.getLogger(CatController.class);
	
	@Autowired
	private CatService catService;
	
    /**
     * @param
     * @author linjunqin
     * @Description 初始方法
     * @date 2017-1-5 下午2:37:11
     */
    @ModelAttribute
    public void populateModel(HttpServletRequest request, Model model) {
        initialHiddenVal(request, model);
        //默认的地区节点
        model.addAttribute("cfg_top_cat", SysconfigFuc.getSysValue("cfg_top_cat"));
    }
	
	
	/**
	 * @author linjunqin
	 * @Description 获取分类列表页
	 * @param
	 * @date 2017-1-3 下午4:27:50
	 */
	@RequestMapping("list")
	public String list(TreeTableBean treeBean,Model model){
		model.addAttribute("ZtreeBean",treeBean);
		return "cat/list";
	}
	
	/**
	 * @author linjunqin
	 * @Description 获取系统分类列表数据json字符串返回
	 * @param
	 * @date 2017-1-3 下午4:28:32
	 */
	@RequestMapping("getChildList")
	public void getChildList(HttpServletRequest request,HttpServletResponse response){
		HashMap<String,Object> paraMap = new HashMap<String,Object>();
		String up_id = request.getParameter("up_id");  
		if(up_id!=null && !up_id.equals("")){
			paraMap.put("parent_cat_id", up_id);
		}
		HashMap<String,Object> jsonMap = new HashMap<String,Object>();
		//获取菜单对象
		Cat cat = this.catService.get(up_id);
		jsonMap.put("title", cat.getCat_name());
		jsonMap.put("title_id",  cat.getCat_id());
		//返回子列表
		List<Cat> list = this.catService.getList(paraMap);
		jsonMap.put("list",list);
		String listJson = JsonUtil.map2json(jsonMap);
		outPrint(response, listJson);
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 获取正常显示数据的分类
	 * @param
	 * @date 2017-1-10 上午10:43:33
	 */
	@RequestMapping("normalList")
	public void normalList(HttpServletRequest request,HttpServletResponse response){
		//上一级分类标识
		String parent_cat_id = request.getParameter("id");
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		if(parent_cat_id!=null && !parent_cat_id.equals("")){
			paraMap.put("parent_cat_id", parent_cat_id);
			List<Cat> list = this.catService.getList(paraMap);
			HashMap<String,Object> jsonMap = new HashMap<String,Object>();
			jsonMap.put("list",list);
			String listJson = JsonUtil.map2json(jsonMap);
			outPrint(response, listJson);
		}
	}
	
	/**
	 * @author linjunqin
	 * @Description 跳转新增分类列表页
	 * @param
	 * @date 2017-1-3 下午4:29:59
	 */
	@RequestMapping("add")
	public String add(TreeTableBean treeBean,Model model){
		String up_id =treeBean.getUp_id();  
		if(up_id==null) return null;
		//获取菜单对象
		Cat cat = this.catService.get(up_id);
		String parent_name = cat.getCat_name();
		model.addAttribute("parent_name", parent_name);
		model.addAttribute("up_id", up_id);
		model.addAttribute("ZtreeBean", treeBean);
		return "cat/insert";
	}
	
	/**
	 * @author linjunqin
	 * @Description 添加系统分类
	 * @param
	 * @date 2017-1-3 下午4:31:33
	 */
	@RequestMapping("insert")
	public String insert(TreeTableBean treeBean,Cat cat,Model model){
		Cat parent_cat = catService.get(cat.getParent_cat_id());
		//随机生成十位字符做为标识
		String cat_id =RandomCharUtil.getNumberRand();
		//算出子菜单的等级
		String child_level =  String.valueOf(Integer.parseInt(parent_cat.getCat_level().trim())+1);
		String level_cat = parent_cat.getLevel_cat()+","+cat_id;
		cat.setCat_id(cat_id);
		cat.setLevel_cat(level_cat);
		cat.setCat_level(child_level);
		this.catService.insert(cat);
		model.addAttribute("promptmsg","系统分类添加成功！");
		return add(treeBean,model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 跳转到更新分类页面
	 * @param
	 * @date 2017-1-3 下午4:34:21
	 */
	@RequestMapping("edit")
	public String edit(TreeTableBean treeBean,Model model){
		String id = treeBean.getId();
		if(id==null) return null;
		//获取当前菜单的对象
		Cat cat = this.catService.get(id);
		model.addAttribute("cat",cat);
		//获取父节点菜单名称
		Cat parent_cat = this.catService.get(cat.getParent_cat_id().trim());
		String parent_name = parent_cat.getCat_name();
		model.addAttribute("parent_name", parent_name);
		treeBean.setUp_id(cat.getParent_cat_id());
		model.addAttribute("ZtreeBean", treeBean);
		return "cat/update";
	}
	
	/**
	 * @author linjunqin
	 * @Description 更新分类页面
	 * @param
	 * @date 2017-1-3 下午4:35:53
	 */
	@RequestMapping("update")
	public String update(TreeTableBean treeBean,Cat cat,Model model){
		String cat_id = treeBean.getId();
		cat.setCat_id(cat_id);
		this.catService.update(cat);
		return list(treeBean,model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 删除分类信息
	 * @param
	 * @date 2017-1-3 下午4:37:13
	 */
	@RequestMapping("delete")
	public String delete(TreeTableBean treeBean,Model model){
		if(treeBean.getId()!=null){
			this.catService.deleteOne(treeBean.getId().trim());
		}
		return list(treeBean, model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 分类批量排序
	 * @param
	 * @date 2017-1-3 下午4:37:47
	 */
	@RequestMapping("sort")
	public String sort(TreeTableBean treeBean,Model model,HttpServletRequest request){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.catService.updateBatch(sortMapList);
		return list(treeBean, model);
	}
	
}

