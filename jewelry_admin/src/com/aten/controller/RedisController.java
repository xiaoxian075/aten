package com.aten.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aten.client.RedisClient;
import com.aten.function.SysconfigFuc;
import com.aten.function.SysmenuFuc;
import com.aten.function.ValidateFuc;
import com.aten.model.orm.Area;
import com.aten.model.orm.Attr;
import com.aten.model.orm.AttrValue;
import com.aten.model.orm.Cat;
import com.aten.model.orm.Commpara;
import com.aten.model.orm.Power;
import com.aten.model.orm.PreGoodscat;
import com.aten.model.orm.Sysconfig;
import com.aten.service.AreaService;
import com.aten.service.AttrService;
import com.aten.service.AttrValueService;
import com.aten.service.CatService;
import com.aten.service.CommparaService;
import com.aten.service.PowerService;
import com.aten.service.PreGoodscatService;
import com.aten.service.SysconfigService;
import com.communal.constants.Constant;
import com.communal.constants.EsContant;
import com.communal.constants.RedisConstants;
import com.communal.constants.SysconfigContant;
import com.communal.util.JsonUtil;
import com.communal.util.PropertiesUtil;
import com.communal.util.StringUtil;
import com.es.CatAttrEsIndex;
import com.es.DeleteIndex;
import com.es.GoodsEsIndex;

@Controller
@RequestMapping("admin/redis")
public class RedisController extends BaseController{

	private static final Logger logger = Logger.getLogger(RedisController.class);
	
	@Autowired
	private PowerService powerService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private CatService catService;
	@Autowired
	private AttrService attrService;
	@Autowired
	private AttrValueService attrValueService;
	@Autowired
	private CommparaService commparaService;
	@Autowired
	private SysconfigService sysconfigService;
	@Autowired
	private PreGoodscatService preGoodscatService;
	
	
	/**
	 * @author linjunqin
	 * @Description 跳转redis更新页面
	 * @param
	 * @date 2017-3-7 下午7:32:04
	 */
	@RequestMapping(value="index")  
	public String index(Model model){
		return "redis/index";
	}
	
	/**
	 * @author linjunqin
	 * @Description 更新redis 前端验证框架缓存
	 * @param
	 * @date 2017-1-4 下午4:53:04
	 */
	@RequestMapping(value="validate")  
	public String validate(Model model){
		try {
			String validate_path = PropertiesUtil.getClassPath()+"/validate.xml";
			ValidateFuc.getValidateFieldXml(validate_path);
			model.addAttribute("promptmsg","更新验证缓存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("promptmsg","更新验证缓存失败！");
		}
		return index(model);
	}


	/**
	 * @author linjunqin
	 * @Description 菜单导航条存储
	 * @param
	 * @date 2017-3-23 上午9:46:45
	 */
	@RequestMapping(value="nav")  
	public String nav(Model model){
		try {
			List<Power> powerList = powerService.getList(null);
	    	for(Power power:powerList){
	    		//System.out.println(power.getUrl().trim()+"====="+SysmenuFuc.getMenuNameStr(power.getMenu_id()," > "));
	    		String pos = " > ";
	    		String menu_name_str = SysmenuFuc.getMenuNameStr(power.getMenu_id(),pos);
	    		if(!StringUtil.isEmpty(power.getPath_name())){
	    			menu_name_str = menu_name_str + pos + power.getPath_name();
	    		}
	    		RedisClient.addStr(RedisConstants.NAVPOST+power.getUrl().trim(),menu_name_str);
	    	}
			model.addAttribute("promptmsg","更新菜单导航缓存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("promptmsg","更新菜单导航缓存失败！");
		}
		return index(model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 更新redis地区键值缓存
	 * @param
	 * @date 2017-7-3 上午11:42:53
	 */
	@RequestMapping(value="area")  
	public String area(Model model){
		try {
			List<Area> areaList = areaService.getList(null);
	    	for(Area area:areaList){
	    		System.out.println(area.getArea_id()+"====");
	    		RedisClient.addStr(RedisConstants.AREA_PRE+area.getArea_id(),area.getArea_name());
	    	}
			model.addAttribute("promptmsg","更新地区缓存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("promptmsg","更新地区缓存失败！");
		}
		return index(model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 更新redis 平台分类的缓存
	 * @param
	 * @date 2017-7-3 上午11:57:12
	 */
	@RequestMapping(value="cat")  
	public String cat(Model model){
		try {
			List<Cat> catList = catService.getList(null);
	    	for(Cat cat:catList){
	    		System.out.println(cat.getCat_id()+"====");
	    		RedisClient.addStr(RedisConstants.CAT_PRE+cat.getCat_id(),cat.getCat_name());
	    	}
			model.addAttribute("promptmsg","更新分类缓存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("promptmsg","更新分类缓存失败！");
		}
		return index(model);
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 更新级联地区数据成功 
	 * @param
	 * @date 2017-7-3 下午2:33:15
	 */
	@RequestMapping(value="levelArea")  
	public String levelArea(Model model){
		try {
			HashMap<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("area_level", "1,2");
			List<Area> areaList = areaService.getAreaListByIsState(paraMap);
			//遍历父ID底下的地区
	    	for(Area area:areaList){
	    		System.out.println(area.getArea_id()+"====");
	    		//获取子地区列表的数据
	    		List<Area> childAreaList =  this.areaService.getChildAreaList(area.getArea_id());
	    		String childAreaJson = JsonUtil.list2json(childAreaList);
	    		RedisClient.addStr(RedisConstants.LEVELAREA_PRE+area.getArea_id(),childAreaJson);
	    	}
			model.addAttribute("promptmsg","更新级联地区缓存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("promptmsg","更新级联地区缓存失败！");
		}
		return index(model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 更新商品属性值缓存
	 * @param
	 * @date 2017-7-3 下午3:30:34
	 */
	@RequestMapping(value="attr")  
	public String attr(Model model){
		try {
			List<Attr> attrList = attrService.getList(null);
			//遍历父ID底下的地区
	    	for(Attr attr:attrList){
	    		System.out.println(attr.getAttr_id()+"====");
	    		RedisClient.addStr(RedisConstants.ATTR+attr.getAttr_id(),attr.getAttr_name());
	    	}
			model.addAttribute("promptmsg","更新级联地区缓存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("promptmsg","更新级联地区缓存失败！");
		}
		return index(model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 更新属性值缓存
	 * @param
	 * @date 2017-7-3 下午4:08:54
	 */
	@RequestMapping(value="attrValue")  
	public String attrValue(Model model){
		try {
			List<AttrValue> attrValueList = attrValueService.getList(null);
			//遍历父ID底下的地区
	    	for(AttrValue attrValue:attrValueList){
	    		System.out.println(attrValue.getAttr_value_id()+"====");
	    		//获取属性名称
	    		String attr_id = attrValue.getAttr_id();	 
	    		String attr_name="";
	    		Attr attr = this.attrService.get(attr_id);
	    		if(attr!=null){
	    			attr_name = attr.getAttr_name();
	    		}
	    		attrValue.setAttr_name(attr_name);
	    		//属性值对象转成json字符串
	    		String attrValueJson = JsonUtil.object2json(attrValue);
	    		//保存到redis中
	    		RedisClient.addStr(RedisConstants.ATTRVALUE+attrValue.getAttr_value_id(),attrValueJson);
	    	}
			model.addAttribute("promptmsg","更新级联地区缓存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("promptmsg","更新级联地区缓存失败！");
		}
		return index(model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 更新数字字典列表缓存
	 * @param
	 * @date 2017-7-3 下午4:59:54
	 */
	@RequestMapping(value="commparaList")  
	public String commparaList(Model model){
		try {
			List<Commpara> paraCodeList = commparaService.getParaCodeList(null);
			//遍历父ID底下的地区
	    	for(Commpara pc:paraCodeList){
	    		System.out.println(pc.getPara_code()+"====");
	    		//获取字典编码下对应的数据
	    		List<Commpara> commparaList = commparaService.getCommparaListByPara(pc.getPara_code());
	    		//针对前后台的命名不一样，导致取值为空，故在这边做特殊转化
	    		List<HashMap<String, String>> hMapList = new ArrayList<HashMap<String, String>>();
	    		for(Commpara commpara : commparaList){
	    			HashMap<String, String> hMap = new HashMap<String, String>();
	    			hMap.put("paraCode", commpara.getPara_code());
	    			hMap.put("paraId", commpara.getPara_id());
	    			hMap.put("paraKey", commpara.getPara_key());
	    			hMap.put("paraName", commpara.getPara_name());
	    			hMap.put("state", commpara.getState());
	    			hMapList.add(hMap);
	    		}
	    		String commparaJson = JsonUtil.object2json(hMapList);
	    		//保存到redis中
	    		RedisClient.addStr(RedisConstants.COMMPARALIST+pc.getPara_code(),commparaJson);
	    	}
			model.addAttribute("promptmsg","更新数字字典列表缓存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("promptmsg","更新数字字典列表缓存失败！");
		}
		return index(model);
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 更新数字字典缓存
	 * @param
	 * @date 2017-7-3 下午4:59:54
	 */
	@RequestMapping(value="commpara")  
	public String commpara(Model model){
		try {
			List<Commpara> commparaList = commparaService.getList(null);
			//遍历父ID底下的地区
	    	for(Commpara Commpara:commparaList){
	    		System.out.println(Commpara.getPara_code()+"====");
	    		//保存到redis中
	    		RedisClient.addStr(RedisConstants.COMMPARAKEY+Commpara.getPara_code()+"-"+Commpara.getPara_key(),Commpara.getPara_name());
	    	}
			model.addAttribute("promptmsg","更新数字字典缓存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("promptmsg","更新数字字典缓存失败！");
		}
		return index(model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 系统设置参数缓存
	 * @param
	 * @date 2017-7-3 下午5:26:40
	 */
	@RequestMapping(value="sysconfig")  
	public String sysconfig(Model model){
		try {
			List<Sysconfig> sysconfigList = sysconfigService.getList(null);
			//遍历父ID底下的地区
	    	for(Sysconfig sysconfig:sysconfigList){
	    		System.out.println(sysconfig.getVar_name()+"====");
	    		//保存到redis中
	    		RedisClient.addStr(RedisConstants.SYSCONFIG+sysconfig.getVar_name(),sysconfig.getVar_value());
	    	}
			model.addAttribute("promptmsg","更新系统设置参数缓存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("promptmsg","更新系统设置参数缓存失败！");
		}
		return index(model);
	}
	
	/**
	 * @author linjunqin
	 * @Description  前台分类对就后台分类串的标识
	 * @param
	 * @date 2017年7月19日 下午3:03:44 
	 */
	@RequestMapping(value="precatStr")  
	public String precatStr(Model model){
		try {
			//前台分类ID标识
			String preCatId = SysconfigFuc.getSysValue(SysconfigContant.CFG_PRE_DEFAULT);
			HashMap<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("module", "pre");//前台分类模块
			List<Cat> preCatList = catService.getList(paraMap);
			for(int i=0;i<preCatList.size();i++){
				Cat preCat = preCatList.get(i);
				//找出前台对应的后台分类数组
				HashMap<String, Object> backMap = new HashMap<String, Object>();
				backMap.put("precat_id", preCat.getCat_id());
				List<PreGoodscat> backCatList = preGoodscatService.getList(backMap);
				String backCatIds = "";
				int len = backCatList.size();
				for(int j=0;j<len;j++){
					PreGoodscat pgs = backCatList.get(j);
					backCatIds+=pgs.getCat_id()+ (j==len-1 ? "":",");
				}
				//保存到redis中
	    		RedisClient.addStr(RedisConstants.PRECAT+preCat.getCat_id(),backCatIds);
			}
			model.addAttribute("promptmsg","前台分类缓存更新成功！");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("promptmsg","前台分类缓存更新失败！");
		}
		return index(model);
	}
	
	/**
	 * @author linjunqin
	 * @Description  更新前台分类列表缓存
	 * @param
	 * @date 2017年7月19日 下午4:23:07 
	 */
	@RequestMapping(value="precatList")  
	public String precatList(Model model){
		try {
			//前台分类ID标识
			String preCatId = SysconfigFuc.getSysValue(SysconfigContant.CFG_PRE_DEFAULT);
			HashMap<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("module", "pre");//前台分类模块
			List<Cat> preCatList = catService.getList(paraMap);
			for(int i=0;i<preCatList.size();i++){
				Cat preCat = preCatList.get(i);
				//找出前台对应的后台分类数组
				HashMap<String, Object> backMap = new HashMap<String, Object>();
				backMap.put("precat_id", preCat.getCat_id());
				List<Cat> catList = catService.getList(backMap);
				//保存到redis中
	    		RedisClient.addStr(RedisConstants.PRECATLIST+preCat.getCat_id(),JsonUtil.list2json(catList));
			}
			model.addAttribute("promptmsg","前台分类缓存更新成功！");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("promptmsg","前台分类缓存更新失败！");
		}
		return index(model);
	}
	
	
	/**
	 * @author linjunqin
	 * @Description  更新商品属性索引
	 * @param
	 * @date 2017年7月11日 下午4:04:43 
	 */
	@RequestMapping(value="goodsIndex")  
	public String goodsIndex(Model model){
		try {
			//创建索引结构
			GoodsEsIndex.createGoodsMapping(EsContant.GOODSINDEX,EsContant.GOODS);
			//创建索引数据
			GoodsEsIndex.createGoodsIndex(EsContant.GOODSINDEX,EsContant.GOODS);
			model.addAttribute("promptmsg","更新商品索引成功！");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("promptmsg","更新商品索引失败！");
		}
		return index(model);
	}
	
	/**
	 * @author linjunqin
	 * @Description   更新分类属性索引
	 * @param
	 * @date 2017年7月11日 下午4:08:31 
	 */
	@RequestMapping(value="catAttrIndex")  
	public String catAttrIndex(Model model){
		try {
			//创建索引结构
			CatAttrEsIndex.createCatAttrMapping(EsContant.CATATTRINDEX,EsContant.CATATTRTYPE);
			//创建索引数据
			CatAttrEsIndex.createCatAttrIndex(EsContant.CATATTRINDEX,EsContant.CATATTRTYPE);
			model.addAttribute("promptmsg","更新分类属性索引成功！");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("promptmsg","更新分类属性索引失败！");
		}
		return index(model);
	}
	
	
	/**
	 * @author linjunqin
	 * @Description  删除索引
	 * @param
	 * @date 2017年7月14日 下午4:16:00 
	 */
	@RequestMapping(value="deleteIndex")  
	public String deleteIndex(Model model){
		try {
			DeleteIndex.deleteIndex();
			model.addAttribute("promptmsg","删除索引成功！");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("promptmsg","删除索引失败！");
		}
		return index(model);
	}
	
	
	
	
	
}

