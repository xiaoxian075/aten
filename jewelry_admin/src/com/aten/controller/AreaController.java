package com.aten.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aten.function.CommparaFuc;
import com.aten.function.SysconfigFuc;
import com.aten.model.bean.OrgZtreeVo;
import com.aten.model.orm.Commpara;
import com.aten.model.orm.Manager;
import com.communal.util.ChineseToEnglishUtil;
import com.communal.util.StringUtil;

import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aten.model.bean.TreeTableBean;
import com.aten.model.orm.Area;
import com.aten.model.orm.Cat;
import com.aten.service.AreaService;
import com.communal.util.JsonUtil;
import com.communal.util.RandomCharUtil;


@Controller
@RequestMapping("admin/area")
public class AreaController extends BaseController{

	private static final Logger logger = Logger.getLogger(AreaController.class);
	
	@Autowired
	private AreaService areaService;

	/**
	 * @author linjunqin
	 * @Description 初始方法
	 * @param
	 * @date 2017-1-5 下午2:37:11
	 */
	@ModelAttribute
	public void populateModel(HttpServletRequest request,Model model) {
		initialHiddenVal(request,model);
		//默认的地区节点
		model.addAttribute("cfg_top_area", SysconfigFuc.getSysValue("cfg_top_area"));
		//获取所属区域
		model.addAttribute("cfg_area_region", CommparaFuc.getParaList("cfg_area_region"));
	}
	/**
	 * @author linjunqin
	 * @Description 地区列表页
	 * @param
	 * @date 2017-1-4 上午9:14:56
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		//属性类型
		List<Commpara> areaLevelList = CommparaFuc.getParaList("cfg_area_level");
		model.addAttribute("areaLevelList", areaLevelList);
		//搜索封装
		String name=request.getParameter("area_name");
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		paraMap = searchMap(request,paraMap,model);
		int count = this.areaService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<Area> areaList = this.areaService.getList(paraMap);
		model.addAttribute("areaList", areaList);
		return "area/list";
	}

	@RequestMapping("view")
	public String view(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		Area area = this.areaService.get(parameter_id);
		model.addAttribute("area", area);
		return "area/view";
	}

	/*删除地区*/
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.areaService.deleteOne(parameter_id);
		//删除下级地区
		deleteSon(parameter_id);
		model.addAttribute("promptmsg","删除成功！");
		return list(request, model);
	}
	/**
	 * @author chenyi
	 * @Description 批量删除地区
	 * @param
	 * @date 2017-5-3 下午4:08:03
	 */
	@RequestMapping("batchDelete")
	public String batchDelete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		if (ids!=null){
			for(String id:ids){
				this.areaService.deleteOne(id);
				//删除下级地区
				deleteSon(id);
			}
		}

		model.addAttribute("promptmsg","地区批量删除成功！");
		return list(request, model);
	}
	//删除下级
	private void deleteSon(String pId) {
		List<Area> areaList = this.areaService.getSon(pId);
		if (areaList != null) {
			for (Area area:areaList) {
				this.areaService.deleteOne(area.getArea_id());
				//禁用下级地区
				deleteSon(area.getArea_id());
			}
		}
	}
	/*禁用地区*/
	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		Area area =areaService.get(parameter_id);
		area.setState("0");
		area.setArea_id(parameter_id);
		this.areaService.update(area);
        //果当前操作的节点及其兄弟节点均禁用时，自动禁用其上级
        autoLimitParent(area.getParent_area_id());
		//禁用下级地区
		updateSon(parameter_id,"0");
		model.addAttribute("promptmsg","禁用成功！");
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 批量禁用地区
	 * @param
	 * @date 2017-5-3 下午4:08:03
	 */
	@RequestMapping("batchLimitState")
	public String batchLimitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		if (ids!=null){
			for(String id:ids){
				Area area = areaService.get(id);
				area.setState("0");
				area.setArea_id(id);
				this.areaService.update(area);
                //果当前操作的节点及其兄弟节点均禁用时，自动禁用其上级
                autoLimitParent(area.getParent_area_id());
				//禁用下级地区
				updateSon(id,"0");
			}
		}

		model.addAttribute("promptmsg","地区批量禁用成功！");
		return list(request, model);
	}
	//禁用或启用下级
	private void updateSon(String pId,String state) {
		List<Area> areaList = this.areaService.getSon(pId);
		if (areaList != null) {
			for (int i = 0; i < areaList.size(); i++) {
				Area area = areaList.get(i);
				area.setState(state);
				this.areaService.update(area);
				//禁用下级地区
				updateSon(area.getArea_id(),state);
			}
		}
	}
    //禁用或启用上级
	private void updateParent(String area_id,String state) {
		List<Area> areaList = this.areaService.getParent(area_id);
		if (areaList != null&&areaList.size()>0) {
			Area area = areaList.get(0);
			area.setState(state);
			this.areaService.update(area);
			//启用上级地区
			updateParent(area.getParent_area_id(),state);
		}
	}
    //果当前操作的节点及其兄弟节点均禁用时，自动禁用其上级
    private void autoLimitParent(String parent_area_id) {
                 Area parent=areaService.get(parent_area_id);
                List<Area> areaSonList = areaService.autoLimitParent(parent.getArea_id());
                 if(areaSonList==null||areaSonList.size()==0){
                     //禁用上级
                     areaService.get(parent_area_id).setState("0");
                     areaService.update(areaService.get(parent_area_id));
                 }

    }
	//
	/**
	 * @author linjunqin
	 * @Description 启用地区
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		Area area =areaService.get(parameter_id);
		area.setState("1");
		area.setArea_id(parameter_id);
		this.areaService.update(area);
		//启用下级地区
		updateSon(parameter_id,"1");
		//启用上级
		updateParent(area.getParent_area_id(),"1");
		model.addAttribute("promptmsg","启用成功！");
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 批量启用地区
	 * @param
	 * @date 2017-5-3 下午4:08:03
	 */
	@RequestMapping("batchEnableState")
	public String batchEnableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			Area area = areaService.get(id);
			area.setState("1");
			area.setArea_id(id);
			this.areaService.update(area);
			//递归启用下级地区
			updateSon(id,"1");
			//启用上级
			updateParent(area.getParent_area_id(),"1");
			//启用上级
		}
		model.addAttribute("promptmsg","批量启用成功！");
		return list(request, model);
	}
	/**
	 * @author linjunqin
	 * @Description 获取地区菜单列表数据json字符串返回
	 * @param
	 * @date 2017-1-4 上午9:15:33
	 */
	@RequestMapping("getChildList")
	public void getChildList(HttpServletRequest request,HttpServletResponse response){
		HashMap<String,Object> paraMap = new HashMap<String,Object>();
		String up_id = request.getParameter("up_id");  
		if(up_id!=null && !up_id.equals("")){
			paraMap.put("parent_area_id", up_id);
		}
		HashMap<String,Object> jsonMap = new HashMap<String,Object>();
		//获取菜单对象
		Area area = this.areaService.get(up_id);
		jsonMap.put("title", area.getArea_name());
		jsonMap.put("title_id",  area.getArea_id());
		//返回子列表
		List<Area> list = this.areaService.getList(paraMap);
		//String listJson = JsonUtil.list2json(list);
		jsonMap.put("list",list);
		String listJson = JsonUtil.map2json(jsonMap);
		outPrint(response, listJson);
	}
	
	/**
	 * @author linjunqin
	 * @Description 跳转到新增页面
	 * @param
	 * @date 2017-1-4 上午9:16:39
	 */
	@RequestMapping("add")
	public String add(Model model){
		List<Commpara> areaLevelList = CommparaFuc.getParaList("cfg_area_level");
		model.addAttribute("areaLevelList", areaLevelList);
		return "area/insert";
	}
	/**
	 * @author linjunqin
	 * @Description 获取正常显示数据的地区
	 * @param
	 * @date 2017-1-10 上午10:43:33
	 */
	@RequestMapping("normalList")
	public void normalList(HttpServletRequest request,HttpServletResponse response){
		//上一级分类标识
		String parent_area_id = request.getParameter("id");
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		if(parent_area_id!=null && !parent_area_id.equals("")){
			Area pa = this.areaService.get(parent_area_id);
			HashMap<String,Object> jsonMap = new HashMap<String,Object>();
			if(pa!=null && pa.getIs_city().equals("0")){
				//获取下一级数据
				paraMap.put("parent_area_id", parent_area_id);
				List<Area> list = this.areaService.getAreaListByIsShow(paraMap);
				jsonMap.put("list",list);
			}
			String listJson = JsonUtil.map2json(jsonMap);
			outPrint(response, listJson);
		}
	}

	/**
	 * @author linjunqin
	 * @Description 获取所有地区
	 * @param
	 * @date 2017-1-10 上午10:43:33
	 */
	@RequestMapping("getList")
	public void getList(HttpServletRequest request,HttpServletResponse response){
		//上一级分类标识
		String parent_area_id = request.getParameter("id");
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		if(parent_area_id!=null && !parent_area_id.equals("")){
			paraMap.put("parent_area_id", parent_area_id);
			List<Area> list = this.areaService.getList(paraMap);
			HashMap<String,Object> jsonMap = new HashMap<String,Object>();
			jsonMap.put("list",list);
			String listJson = JsonUtil.map2json(jsonMap);
			outPrint(response, listJson);
		}
	}

	/**
	 * @author linjunqin
	 * @Description 添加地区
	 * @param
	 * @date 2017-1-4 上午9:17:49
	 */
	@RequestMapping("insert")
	public String insert(Area area,Model model){
		// 行政编码只能是数字
		String regEx = "^[0-9]*$";
		//String regEx = "[a-zA-Z0-9]+";
		if (!area.getXz_code().matches(regEx)) {
			model.addAttribute("promptmsg", "行政编码只能是数字！");
			return add(model);
		}
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("area_name", area.getArea_name());
		int count = this.areaService.getCount(paraMap);
		if(count>0){
			model.addAttribute("promptmsg","地区名称已存在！");
			return add(model);
		}
		paraMap.clear();
		paraMap.put("xz_code", area.getXz_code());
		count = this.areaService.getCount(paraMap);
		if(count>0){
			model.addAttribute("promptmsg","行政编码已存在！");
			return add(model);
		}
		paraMap.clear();
//		paraMap.put("post_code", area.getPost_code());
//		count = this.areaService.getCount(paraMap);
//		if(count>0){
//			model.addAttribute("promptmsg","邮政编码已存在！");
//			return add(model);
//		}
		//随机生成十位字符做为菜单标识
		String area_id =RandomCharUtil.getNumberRand();
		//算出子菜单的等级
		area.setArea_id(area_id);
		//设置拼音
		String pinyin= ChineseToEnglishUtil.getPingYin(area.getArea_name().trim()).trim();
		area.setEn_name(pinyin);
		area.setWord_index(pinyin.substring(0, 1).toUpperCase());
		//设置排序
		area.setSort_no("0");
		//设置 area_level;//lev+1
		area.setLevel_area("0");
		area.setIs_city("0");
		String parent_id=area.getParent_area_id();
		area.setParent_area_id(parent_id.substring(parent_id.length()-10,parent_id.length()));
		//设置行政级别（上级行政级别+1）
		Area parent=areaService.get(parent_id.substring(parent_id.length()-10,parent_id.length()));
		int parent_level=Integer.parseInt(parent.getArea_level());
		if (parent_level<4){
			parent_level=parent_level+1;
		}
		area.setArea_level(parent_level+"");
		this.areaService.insert(area);
		model.addAttribute("promptmsg","地区添加成功！");
		return add(model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 跳转到更新页面
	 * @param
	 * @date 2017-1-4 上午9:33:03
	 */
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取当前菜单的对象
		Area area = this.areaService.get(parameter_id);
		model.addAttribute("area",area);
		return "area/update";
	}
	
	/**
	 * @author linjunqin
	 * @Description 更新地区
	 * @param
	 * @date 2017-1-4 上午9:35:17
	 */
	@RequestMapping("update")
	public String update(HttpServletRequest request,Area area,Model model){
		Area oldArea=areaService.get(area.getArea_id());
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		int count=0;
		if (!area.getArea_name().equals(oldArea.getArea_name())){
			paraMap.put("area_name", area.getArea_name());
			 count = this.areaService.getCount(paraMap);
			if(count>0){
				model.addAttribute("promptmsg","地区名称已存在！");
				return edit(request,model);
			}
		}
	   if (!area.getXz_code().equals(oldArea.getXz_code())){
		   paraMap.clear();
		   paraMap.put("xz_code", area.getXz_code());
		   count = this.areaService.getCount(paraMap);
		   if(count>0){
			   model.addAttribute("promptmsg","行政编码已存在！");
			   return edit(request,model);
		   }
	   }
//		if (!area.getPost_code().equals(oldArea.getPost_code())){
//		paraMap.clear();
//		paraMap.put("post_code", area.getPost_code());
//		count = this.areaService.getCount(paraMap);
//			if(count>0){
//				model.addAttribute("promptmsg","邮政编码已存在！");
//				return edit(request,model);
//			}
//		}

		this.areaService.update(area);
		model.addAttribute("promptmsg","地区修改成功！");
		return list(request,model);
	}

	
	/**
	 * @author linjunqin
	 * @Description  地区的排序
	 * @param
	 * @date 2017-1-4 上午9:37:04
	 */
	@RequestMapping("sort")
	public String sort(TreeTableBean treeBean,Model model,HttpServletRequest request){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.areaService.updateBatch(sortMapList);
		return list((HttpServletRequest) treeBean, model);
	}


	/**
	 * @param
	 * @author chenyi
	 * @Description 设置地区串
	 * @date 2017-6-23
	 */
	@RequestMapping("setLevelArea")
	public String setLevelArea(HttpServletRequest request, Model model) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("parent_area_id","1111111111");
		//返回子列表
		List<Area> areaList = this.areaService.getList(map);
		setLevelArea(areaList);
		return list(request, model);
	}
	public void setLevelArea(List<Area> list){
		for (int i=0;i<list.size();i++){
			setStr(list.get(i),list.get(i).getArea_id());
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("parent_area_id",list.get(i).getArea_id());
			List<Area> areaList = this.areaService.getList(map);
			setLevelArea(areaList);
		}

	}

	public void setStr(Area area,String level_area){
		String areaId=level_area.substring(0,10);
		String parentId= areaService.get(areaId).getParent_area_id();
		if ("1111111111".equals(parentId)){
			area.setLevel_area("1111111111,"+level_area);
			areaService.update(area);
		} else{
			Area parentArea=areaService.get(parentId);
			if (parentArea!=null||!"".equals(parentArea)){
				level_area=parentArea.getArea_id()+","+level_area;
				setStr(area,level_area);
			}

		}

	}
}

