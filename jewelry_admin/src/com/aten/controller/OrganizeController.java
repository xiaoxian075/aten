package com.aten.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aten.model.bean.OrgZtreeVo;
import com.aten.model.orm.Area;
import com.aten.model.orm.Manager;
import com.aten.service.ManagerService;
import com.communal.util.JsonUtil;
import com.communal.util.R;
import com.communal.util.RandomCharUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aten.model.orm.Organize;
import com.aten.service.OrganizeService;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author linjunqin
 * @Function 部门管理功能  controller
 * @date 2017-05-10 19:21:01
 */
@Controller
@RequestMapping("admin/organize")
public class OrganizeController extends BaseController{

	private static final Logger logger = Logger.getLogger(OrganizeController.class);

	@Autowired
	private OrganizeService organizeService;
	@Autowired
	private ManagerService managerService;
	/**
	 * @author linjunqin
	 * @Description 初始方法
	 * @param
	 * @date 2017-1-5 下午2:37:11
	 */
	@ModelAttribute
    public void populateModel(HttpServletRequest request,Model model) {
		initialHiddenVal(request,model);
    }


	/**
	 * @author linjunqin
	 * @Description 部门列表页方法
	 * @param
	 * @date 2017-1-4 上午11:58:06
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.organizeService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<Organize> organizeList = this.organizeService.getList(paraMap);
		model.addAttribute("organizeList", organizeList);
		String parent_org_id=(String)paraMap.get("parent_org_id");
		//如果通过上级部门查询 需返回上级部门名称给treeTool
		if(!"".equals(parent_org_id)&&parent_org_id!=null&&!"undefined".equals(parent_org_id)){
			Organize organize=organizeService.get(parent_org_id);
			if(organize!=null){
				model.addAttribute("parent_org_name",organize.getOrg_name());
			}

		}
		return "organize/list";
	}


	/**
	 * 获取所有部门列表
	 */
	@RequestMapping({"getAllOrg"})
	public void  getAllOrg(HttpServletResponse response) {
		List<OrgZtreeVo> list = this.organizeService.getAllOrg();
		String listJson = JsonUtil.object2json(list);
		outPrint(response,listJson);
	}
	@RequestMapping({"select"})
	public void  select(HttpServletResponse response) {
		List<OrgZtreeVo> list = this.organizeService.getAllOrg();
		String listJson = JsonUtil.object2json(R.ok().put("page", list));
		outPrint(response,listJson);
	}

	/**
	 * @author linjunqin
	 * @Description 跳转到添加部门页面方法
	 * @param
	 * @date 2017-1-4 下午4:53:04
	 */
	@RequestMapping("add")
	public String add(Model model){
		return "organize/insert";
	}


	/**
	 * @author linjunqin
	 * @Description 添加部门方法
	 * @param
	 * @date 2017-1-4 下午4:53:58
	 */
	@RequestMapping("insert")
	public String insert(Organize organize,Model model,HttpServletRequest request){
		String org_code_check = organize.getOrg_code();
		HashMap<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("org_code_check",org_code_check);
		int count = this.organizeService.getCount(paraMap);
		if(count>0){
			model.addAttribute("promptmsg","部门编号已存在！");
			return add(model);
		}

		//随机生成十位字符做为标识
		String organize_id = RandomCharUtil.getNumberRand();
		//如果是跟目录
		if (organize.getParent_org_id()==null||"".equals(organize.getParent_org_id())){
			organize.setParent_org_id("1111111111");
		}
		organize.setOrg_id(organize_id);
		organize.setIs_show("1");
		organize.setLevel_org("0");
		organize.setSort_no("0");
		//设置上级id与名称
		String parent_org_id=organize.getParent_org_id();
		if("".equals(parent_org_id)||parent_org_id==null){
			model.addAttribute("promptmsg","请选择上级部门！)");
			return add(model);
		}
		if(!"".equals(parent_org_id)&&parent_org_id!=null){
			Organize parent=organizeService.get(parent_org_id);
			if(parent!=null){
				organize.setParent_org_code(parent.getOrg_code());
				organize.setParent_org_name(parent.getOrg_name());

			}
		}
		this.organizeService.insert(organize);
		model.addAttribute("promptmsg","部门添加成功！");
		return add(model);
	}



	/**
	 * @author linjunqin
	 * @Description 跳转到修改部门页面方法
	 * @param
	 * @date 2017-1-5 下午2:22:57
	 */
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		Organize organize = this.organizeService.get(parameter_id);
		model.addAttribute("organize", organize);
		model.addAttribute("parameter_id",parameter_id);
		return "organize/update";
	}

	/**
	 * @author chenyi
	 * @Description 查询上级编码
	 * @param
	 * @date 2017-1-5 下午2:22:57
	 */
	@RequestMapping("getParentOrgCode")
	public void getParentOrgCode(HttpServletRequest request,HttpServletResponse response,Model model){
		String org_id = request.getParameter("org_id");
		//获取对象
		Organize organize = this.organizeService.get(org_id);
		String  parentCode =this.organizeService.get(organize.getParent_org_id()).getOrg_name();
		outPrint(response,JsonUtil.string2json(parentCode));
	}
	/**
	 * @author linjunqin
	 * @Description 修改部门方法
	 * @param
	 * @date 2017-1-5 下午2:29:42
	 */
	@RequestMapping("update")
	public String update(HttpServletRequest request,Organize organize,Model model){
		String oldCode=organizeService.get(organize.getOrg_id()).getOrg_code();
		String newCode=organize.getOrg_code();
		//如果修改了cat_code  则验证cat_code是否已存在
		if (!oldCode.equals(newCode)){
			HashMap<String,Object> paraMap = new HashMap<String,Object>();
			paraMap.put("org_code",organize.getOrg_code());
			int count = this.organizeService.getCount(paraMap);
			if(count>0){
				model.addAttribute("promptmsg","编码已存在！");
				return edit(request,model);
			}
		}
         //设置上级id与名称
		String parent_id=organize.getParent_org_id();
        if(!"".equals(parent_id)&&parent_id!=null){
        	Organize parent=organizeService.get(parent_id);
        	if(parent!=null){
				organize.setParent_org_name(parent.getOrg_name());
				organize.setParent_org_code(parent.getOrg_code());
			}
		}
		this.organizeService.update(organize);
		model.addAttribute("promptmsg","部门修改成功！");
		return list(request,model);
	}

	public boolean isSon(String id,String parentId){
		List<Organize> organizeList=this.organizeService.getSon(id);
		if(organizeList!=null&&organizeList.size()>0){
			for (Organize organize:organizeList){
				//如果选择的上级部门是下级部门  返回true
				if(parentId.equals(organize.getOrg_id())){
					//是子孙目录
					return true;
				}else{
					//递归继续查找下级部门
					isSon(organize.getOrg_id(),parentId );
				}

			}
		}
		return false;
	}
	
	/**
	 * @author linjunqin
	 * @Description 获取正常显示的部门
	 * @param
	 * @date 2017-5-5 下午7:21:15
	 */
	@RequestMapping("normalList")
	public void normalList(HttpServletRequest request,HttpServletResponse response){
		//上一级分类标识
		String parent_org_id = request.getParameter("id");
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		if(parent_org_id!=null && !parent_org_id.equals("")){
			paraMap.put("parent_org_id", parent_org_id);
			paraMap.put("is_show", "1");
			List<Organize> list = this.organizeService.getList(paraMap);
			HashMap<String,Object> jsonMap = new HashMap<String,Object>();
			jsonMap.put("list",list);
			String listJson = JsonUtil.map2json(jsonMap);
			outPrint(response, listJson);
		}
	}
	
	/**
	 * @author linjunqin
	 * @Description 获取部门列表
	 * @param
	 * @date 2017-5-5 下午6:29:22
	 */
	@RequestMapping("getChildList")
	public void getChildList(HttpServletRequest request,HttpServletResponse response){
		HashMap<String,Object> paraMap = new HashMap<String,Object>();
		//上一级ID
		String up_id = request.getParameter("up_id");  
		if(up_id!=null && !up_id.equals("")){
			paraMap.put("parent_org_id", up_id);
		}
		HashMap<String,Object> jsonMap = new HashMap<String,Object>();
		//获取菜单对象
		Organize organize = this.organizeService.get(up_id);
		jsonMap.put("title", organize.getOrg_name());
		jsonMap.put("title_id",  organize.getOrg_id());
		//返回子列表
		List<Organize> list = this.organizeService.getList(paraMap);
		//String listJson = JsonUtil.list2json(list);
		jsonMap.put("list",list);
		String listJson = JsonUtil.map2json(jsonMap);
		outPrint(response, listJson);
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 删除部门方法
	 * @param
	 * @date 2017-1-5 下午2:36:30
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//验证是否可以删除部门
		if(checkOrg(parameter_id)){
			model.addAttribute("promptmsg","该部门或下级部门已关联用户,不可删除！");
			return list(request, model);
		}
		this.organizeService.deleteOne(parameter_id);
		//先删除下级部门
		deleteSon(parameter_id);
		model.addAttribute("promptmsg","部门删除成功！");
		return list(request, model);
	}
	/**
	 * @author linjunqin
	 * @Description 验证删除部门时是否被引用
	 * @param
	 * @date 2017-5-9 下午8:53:58
	 */
	private boolean checkOrg(String the_org){
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("the_org",the_org);
		List<Manager> managerList = this.managerService.getList(paraMap);
		if(managerList!=null && managerList.size()>0){
			return true;
		}
		return false;
	}
	/**
	 * @author chenyi
	 * @Description 递归删除下级部门
	 * @param
	 */
	private Boolean deleteSon(String id) {
		List<Organize> organizeList = this.organizeService.getSon(id);
		if (organizeList != null&&organizeList.size()>0) {
			for (int i = 0; i < organizeList.size(); i++) {
				Organize organize = organizeList.get(i);
				//递归删除下级部门
				this.organizeService.deleteOne(organize.getOrg_id());
				deleteSon(organize.getOrg_id());

			}
		}
		return true;
	}
	/**
	 * @author chenyi
	 * @Description 批量删除
	 * @param
	 */
	@RequestMapping("batchDelete")
	public String batchDelete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		boolean falg=false;
		for(String id:ids){
			//验证是否可以删除部门
			if(checkOrg(id)){
				falg=true;
			}else{
				//先删除下级部门
				deleteSon(id);
				this.organizeService.deleteOne(id);
			}
		}
		//验证提示信息
		if(falg){
			model.addAttribute("promptmsg","该部门或下级部门已关联用户,不可删除！");
		}else{
			model.addAttribute("promptmsg","批量删除成功！");
		}
		return list(request, model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 部门排序方法
	 * @param
	 * @date 2017-1-5 下午2:41:02
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request,Model model){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.organizeService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
}

