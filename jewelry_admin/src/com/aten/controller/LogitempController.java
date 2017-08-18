package com.aten.controller;
/**
 * @Description 运费模板类
 * @author cjx
 * @date 2017-7-4
 */

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aten.function.AreaFuc;
import com.aten.function.CommparaFuc;
import com.aten.model.AreaShort;
import com.aten.model.bean.StrStrBean;
import com.aten.model.bean.logiAreaList;
import com.aten.model.orm.Area;
import com.aten.model.orm.AreasetPa;
import com.aten.model.orm.AreasetVo;
import com.aten.model.orm.Commpara;
import com.aten.model.orm.LogiInit;
import com.aten.model.orm.ReachArea;
import com.aten.model.orm.ReachAreaPa;
import com.aten.model.orm.ShipTemplate;
import com.aten.model.orm.ShipTemplatePa;
import com.aten.model.orm.ShipTemplateVo;
import com.aten.service.AreaService;
import com.aten.service.LogitempService;
import com.communal.node.ReqMsg;
import com.communal.util.AmountUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("admin/logitemp")
public class LogitempController extends BaseController {
	private static final Logger logger = Logger.getLogger(LogitempController.class);
	
	@Autowired
	private LogitempService logitempService;
	@Autowired
	private AreaService areaService;
	
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
    }
	
	
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		//搜索封装
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		paraMap = searchMap(request,paraMap,model);
		String ship_name = (String)paraMap.get("ship_name");
		if (ship_name!=null) {
			ship_name = "%"+ship_name+"%";
			paraMap.put("ship_name", ship_name);
		}
		int count = this.logitempService.selectcountShipTemplateByShipname(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<ShipTemplate> shiptemplateList = logitempService.getlist(paraMap);
		for (ShipTemplate node : shiptemplateList) {
			String start_area = node.getStart_area();
			String[] listStartArea = start_area.split(",");
			String _areaname = "";
			for (String str : listStartArea) {
				Area area = areaService.get(str);
				if (area!=null) {
					_areaname += area.getArea_name();
				}
			}
			_areaname = _areaname.substring(0,_areaname.length()-1);
			node.setStart_area(_areaname);
		}
		model.addAttribute("shiptemplateList", shiptemplateList);
		return "logitemp/list";
	}
	
	@RequestMapping("insert")
	public String insert(){
		return "logitemp/insert";
	}
	
	@RequestMapping("update")
	public String update(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id==null) {
			return list(request,model);
		}
		model.addAttribute("ship_id",parameter_id);
		BigInteger _ship_id = new BigInteger(parameter_id);
		ShipTemplate shipTemplate = logitempService.getShipTemplate(_ship_id);
		if (shipTemplate!=null) {
			model.addAttribute("start_area",shipTemplate.getStart_area());
		}
		return "logitemp/update";
	}
	
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) 
			return list(request,model);
		BigInteger ship_id = new BigInteger(parameter_id);
		if (!logitempService.setShipTemplateStatus(ship_id,1))
			model.addAttribute("promptmsg","出错");
		model.addAttribute("promptmsg","启用成功");
		return list(request, model);
	}
	

	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) 
			return list(request,model);
		BigInteger ship_id = new BigInteger(parameter_id);
		if (logitempService.setShipTemplateStatus(ship_id,0))
			model.addAttribute("promptmsg","禁用成功");
		else
			model.addAttribute("promptmsg","运费模板已被引用,禁用失败");
		return list(request, model);
	}
	
	
	@RequestMapping(value="getinit",produces="application/json;charset=UTF-8;")
	@ResponseBody
	public String getinit() {
		List<logiAreaList> logiArea = new ArrayList<logiAreaList>();
		List<Area> area = areaService.getCityArea();
		
		for (Area node : area) {
			if (node.getArea_level().equals("1")) {	//省级
				logiArea.add(new logiAreaList(node.getArea_id(),node.getArea_name(),new ArrayList<AreaShort>(),node.getRegion()));
			}
		}
		
		for (Area node : area) {
			if (node.getArea_level().equals("2")) {	//市级
				String parent_id = node.getParent_area_id();
				//找出父级
				logiAreaList logi = null;
				for (logiAreaList parent : logiArea) {
					if (parent.getArea_id().equals(parent_id)) {
						logi = parent;
						break;
					}
				}
				if (logi!=null) {
					List<AreaShort> list = logi.getChild_area();
					list.add(new AreaShort(node.getArea_id(),node.getArea_name()));
				}
			}
		}
		
		List<StrStrBean> sendtime = new ArrayList<StrStrBean>();
		List<Commpara> list = CommparaFuc.getParaList("cfg_sendgoodstime");
		if (list!=null) {
			for (Commpara node : list) {
				sendtime.add(new StrStrBean(node.getPara_key(),node.getPara_name()));
			}
		}

		return new Gson().toJson(new ReqMsg<LogiInit>(0,"succ",new LogiInit(logiArea,sendtime)));
	}
	
	
	
	@RequestMapping(value="getlist",produces="application/json;charset=UTF-8;")
	@ResponseBody
	public String getlist(String com_id, int goods_id) {
		BigInteger _com_id = new BigInteger(com_id);

		List<ShipTemplateVo> shipTemplateVoList = logitempService.getlist2(_com_id);
		
		return new Gson().toJson(new ReqMsg<List<ShipTemplateVo>>(0,"succ",shipTemplateVoList));
	}
	
	private Map<String,String> getMapRegion() {
		Map<String,String> result = new HashMap<String,String>();
		List<Commpara> list = CommparaFuc.getParaList("cfg_area_region");
		if (list!=null) {
			for (Commpara node : list) {
				result.put(node.getPara_key(),node.getPara_name());
			}
		}
		return result;
	}
	private Map<String,Map<String,List<Area>>> getMapArea() {
		List<Area> arrArea = areaService.getCityArea();
		Map<String,Map<String,List<Area>>> mapList = new HashMap<String,Map<String,List<Area>>>();
		for (Area area : arrArea) {
			String region = area.getRegion();
			Map<String,List<Area>> mapProvide = mapList.get(region);
			if (mapProvide==null) {
				mapProvide = new HashMap<String,List<Area>>();
				mapList.put(region, mapProvide);
			}
			String area_id = "";
			if (area.getArea_level().equals("2")) {	//如果是市
				area_id = area.getParent_area_id();
			} else if (area.getArea_level().equals("1")) {//如果是省
				area_id = area.getArea_id();
			} else {
				continue;
			}
			
			List<Area> listArea = mapProvide.get(area_id);
			if (listArea==null) {
				listArea = new ArrayList<Area>();
				mapProvide.put(area_id, listArea);
			}
			
			listArea.add(area);
		}
		
		return mapList;
	}

	
	@RequestMapping(value="getone",produces="application/json;charset=UTF-8;")
	@ResponseBody
	public String getone(BigInteger ship_id) {
		if (ship_id==null)
			return new Gson().toJson(new ReqMsg<Object>(1,"参数错误",null));
		System.out.println(ship_id);
		
		ShipTemplateVo vo = logitempService.getShipTemplateVo(ship_id);
		if (vo==null)
			return new Gson().toJson(new ReqMsg<Object>(2,"对象为空",null));
		
		//将地区归类，便于下面使用
		//Map<String,Area> mapArea = getMapAreaTo();
		Map<String,Map<String,List<Area>>> mapList = getMapArea();
		Map<String,String> mapRegion = getMapRegion();
		
		//转换成ShipTemplatePa给前端
		List<AreasetVo> listAreasetVo = vo.getArea_info();
		List<AreasetPa> area_info = new ArrayList<AreasetPa>();
		for (AreasetVo avo : listAreasetVo) {
			List<ReachAreaPa> listPa = new ArrayList<ReachAreaPa>();
			List<ReachArea> listRA = avo.getReach_area();
			for (ReachArea ra : listRA) {
				Area area = areaService.get(ra.getEnd_area());
				listPa.add(new ReachAreaPa(ra.getRea_id(),ra.getAs_id(),ra.getEnd_area(),area.getArea_name()));
			}
			
			area_info.add(new AreasetPa(
					avo.getAs_id(),
					avo.getShip_id(),
					avo.getExpress_start(),
					new BigDecimal(AmountUtil.fenToYuan(avo.getExpress_price())),
					avo.getExpress_plus(),
					new BigDecimal(AmountUtil.fenToYuan(avo.getExpress_priceplus())),
					avo.getExpress_id(),
					avo.getDefault_ship(),
					listPa));
		}
		
		
		ShipTemplatePa pa = new ShipTemplatePa(
				vo.getShip_id(),
				vo.getCom_id(),
				vo.getGoods_id(),
				vo.getShip_name(),
				vo.getStart_area(),
				vo.getSend_time_id(),
				vo.getSend_time(),
				vo.getValuation_mode(),
				vo.getExpress_id_str(),
				vo.getFree_ship(),
				vo.getState(),
				vo.getTem_modify_time().toString(),
				area_info );
		
		
						
		List<AreasetPa> listAreaset = pa.getArea_info();
		for (AreasetPa avo : listAreaset) {
			List<ReachAreaPa> listRA = avo.getReach_area();
			Map<String,Map<String,List<Area>>> mapListTemp = new HashMap<String,Map<String,List<Area>>>();
			for (ReachAreaPa ra : listRA) {
				Area area = areaService.get(ra.getEnd_area());
				Map<String,List<Area>> mapProvice = mapListTemp.get(area.getRegion());
				if (mapProvice==null) {
					mapProvice = new HashMap<String,List<Area>>();
					mapListTemp.put(area.getRegion(), mapProvice);
				}
				List<Area> listCity = mapProvice.get(area.getParent_area_id());
				if (listCity==null) {
					listCity = new ArrayList<Area>();
					mapProvice.put(area.getParent_area_id(), listCity);
				}
				listCity.add(area);
			}
			
			List<ReachAreaPa> newArrRA = new ArrayList<ReachAreaPa>();
			for (Entry<String,Map<String,List<Area>>> entry : mapListTemp.entrySet()) {
				String region = entry.getKey();
				Map<String,List<Area>> mapProviceTemp = entry.getValue();
				Map<String,List<Area>> mapProvice = mapList.get(region);
				
				//大区域级别处理
				int countTemp = 0, count = 0;
				for (Entry<String,List<Area>> entry2 : mapProviceTemp.entrySet()) {
					countTemp += entry2.getValue().size();
				}
				for (Entry<String,List<Area>> entry2 : mapProvice.entrySet()) {
					count += entry2.getValue().size();
				}
				if (countTemp==count) {
					String _regionName = mapRegion.get(region);
					newArrRA.add(new ReachAreaPa(new BigInteger("0"),new BigInteger("0"),region,_regionName));
					continue;
				}
				
				
				for (Entry<String,List<Area>> entry3 : mapProviceTemp.entrySet()) {
					String proviceId = entry3.getKey();
					List<Area> listAreaTemp = entry3.getValue();
					List<Area> listArea = mapProvice.get(proviceId);
					if (listAreaTemp==null || listArea==null)
						continue;
					//省级级别处理
					if (listAreaTemp.size()+1==listArea.size()) {	//因为要扣除省级
						String _area_id = region+"_"+proviceId;
						Area area = areaService.get(proviceId);
						String _area_name = area.getArea_name();
						newArrRA.add(new ReachAreaPa(new BigInteger("0"),new BigInteger("0"),_area_id,_area_name));
						continue;
					}
					
					//市级处理
					for (Area area : listAreaTemp) {
						String _area_id = region+"_"+proviceId+"_"+area.getArea_id();
						newArrRA.add(new ReachAreaPa(new BigInteger("0"),new BigInteger("0"),_area_id,area.getArea_name()));
					}
				}
				
			}
			
			avo.setReach_area(newArrRA);		//重新赋值
		}
				
		return new Gson().toJson(new ReqMsg<ShipTemplatePa>(0,"succ",pa));
	}
	
	
	
	@RequestMapping(value="insertone",produces="application/json;charset=UTF-8;")
	@ResponseBody
	public String insertone(String data) {
		if (data==null || data.length()==0) {
			return new Gson().toJson(new ReqMsg(1,"参数错误",""));
		}
		
		System.out.println(data);
		
		//将地区归类，便于下面使用
		//Map<String,Area> mapArea = getMapAreaTo();
		Map<String,Map<String,List<Area>>> mapList = getMapArea();

				
		
		/*验证数据		开始*/
		ShipTemplatePa vo = new Gson().fromJson(data, ShipTemplatePa.class);
		if (vo==null) {
			return new Gson().toJson(new ReqMsg(1,"参数错误",""));
		}
		
		//判断 ship_name
		String ship_name = vo.getShip_name();
		//int nn = ship_name.getBytes().length;
		if (ship_name==null || ship_name.length()==0 || ship_name.getBytes().length>63) {
			return new Gson().toJson(new ReqMsg(1,"参数错误",""));
		}
		
		//判断宝贝地址是否存在
		String start_area = vo.getStart_area();
		if (start_area==null || start_area.length()==0) {
			return new Gson().toJson(new ReqMsg(1,"参数错误",""));
		}
		List<String> arrArea = new ArrayList<String>();
		while (true) {
			Area area = areaService.get(start_area);
			if (area==null) {
				return new Gson().toJson(new ReqMsg(1,"参数错误",""));
			}
			arrArea.add(area.getArea_id());
			int level = Integer.valueOf(area.getArea_level());
			if (level<=1)
				break;
			start_area = area.getParent_area_id();
		}
		if (arrArea.size()==0) {
			return new Gson().toJson(new ReqMsg(1,"参数错误",""));
		}
		start_area = "";
		ListIterator<String> iter = arrArea.listIterator();
		while (iter.hasNext()) {
			iter.next();
		}
		while (iter.hasPrevious()) {
			start_area += iter.previous() + ",";
		}
		start_area = start_area.substring(0,start_area.length()-1);

		String send_time_id = vo.getSend_time_id();
		String send_time = null;
		List<Commpara> list = CommparaFuc.getParaList("cfg_sendgoodstime");
		if (list!=null) {
			for (Commpara node : list) {
				if (node.getPara_key().equals(send_time_id)) {
					send_time = node.getPara_name();
					break;
				}
			}
		}
		if (send_time==null) {
			return new Gson().toJson(new ReqMsg(1,"参数错误",""));
		}
		
		
		int valuation_mode = vo.getValuation_mode();
		if (valuation_mode!=0 && valuation_mode!=1 && valuation_mode!=2) {
			return new Gson().toJson(new ReqMsg(1,"参数错误",""));
		}
		String express_id_str = vo.getExpress_id_str();
		if (!express_id_str.equals("0") && !express_id_str.equals("1") && !express_id_str.equals("2")) {
			return new Gson().toJson(new ReqMsg(1,"参数错误",""));
		}
		int free_ship = vo.getFree_ship();
		if (free_ship!=0 && free_ship!=1) {
			return new Gson().toJson(new ReqMsg(1,"参数错误",""));
		}
		
		List<AreasetPa> area_info = vo.getArea_info();
		if (area_info==null) {
			area_info = new ArrayList<AreasetPa>();
		}
		if (area_info.size()>0) {
			for (AreasetPa areaset : area_info) {
				
				String express_id = areaset.getExpress_id();
				if (express_id==null) {
					areaset.setExpress_id(" ");
				}
				int default_ship = areaset.getDefault_ship();
				if (default_ship!=0 && default_ship!=1) {
					return new Gson().toJson(new ReqMsg(1,"参数错误",""));
				}
				areaset.setAs_id(new BigInteger("0"));
				areaset.setShip_id(new BigInteger("0"));
				List<ReachAreaPa> reach_area = areaset.getReach_area();
				if (reach_area==null) {
					reach_area = new ArrayList<ReachAreaPa>();
				}
				if (reach_area.size()>0) {
					List<ReachAreaPa> arrReachAreaVo = new ArrayList<ReachAreaPa>();
					for (ReachAreaPa reachArea : reach_area) {
						
						//按前端规则遍历
						String end_area = reachArea.getEnd_area();
						if (end_area==null || end_area.length()==0) {
							return new Gson().toJson(new ReqMsg(1,"参数错误",""));
						}
						String[] arrStr = end_area.split("_");
						int count = arrStr.length;
						if (count==1) {	//区级
							String region = arrStr[0];
							Map<String,List<Area>> mapProvice = mapList.get(region);
							if (mapProvice==null || mapProvice.size()==0)
								continue;
							for (Entry<String,List<Area>> entry : mapProvice.entrySet()) {
								//String provice = entry.getKey();
								List<Area> listCity = entry.getValue();
								if (listCity==null || listCity.size()==0)
									continue;
								for (Area area : listCity) {
									arrReachAreaVo.add(new ReachAreaPa(new BigInteger("0"),new BigInteger("0"),area.getArea_id(),""));
								}
							}
						} else if (count==2) {	//省级
							String region = arrStr[0];
							String provice = arrStr[1];
							Map<String,List<Area>> mapProvice = mapList.get(region);
							if (mapProvice==null || mapProvice.size()==0)
								continue;
							List<Area> listCity = mapProvice.get(provice);
							if (listCity==null || listCity.size()==0)
								continue;
							for (Area area : listCity) {
								if (area.getArea_level().equals("2"))
									arrReachAreaVo.add(new ReachAreaPa(new BigInteger("0"),new BigInteger("0"),area.getArea_id(),""));
							}
							
						} else if (count == 3) {	//市级
							arrReachAreaVo.add(new ReachAreaPa(new BigInteger("0"),new BigInteger("0"),arrStr[2],""));
						} else {
							return new Gson().toJson(new ReqMsg(1,"参数错误",""));
						}

					}
					
					areaset.setReach_area(arrReachAreaVo);//重新赋值
				}
			}
		}
		
		vo.setShip_id(new BigInteger("0"));
		vo.setCom_id(new BigInteger("0"));
		vo.setGoods_id(new BigInteger("0"));
		vo.setStart_area(start_area);
		vo.setSend_time(send_time);
		vo.setState(1);
		vo.setTem_modify_time(new Timestamp(new Date().getTime()).toString());

		
		//如果是自定义模板，要验证是否有默认模板
		if (vo.getFree_ship()==0) {
			int countDefault = 0;
			for (AreasetPa node : area_info) {
				int defaultShip = node.getDefault_ship();
				if (defaultShip!=0 && defaultShip!=1) {
					return new Gson().toJson(new ReqMsg(1,"参数错误",""));
				}
				if (defaultShip==1) {
					countDefault++;
					continue;
				}
			}
			if (countDefault!=1) {
				return new Gson().toJson(new ReqMsg(1,"参数错误",""));
			}
		} else {
			vo.setArea_info(new ArrayList<AreasetPa>());
		}
		/*验证数据		结束*/
		
		ShipTemplateVo voo = new ShipTemplateVo(vo);

		try {
			if (!logitempService.insertShipTemplate(voo)) {
				return new Gson().toJson(new ReqMsg(2,"插入失败",""));
			}
		} catch (Exception e) {
			logger.error("insertShiptemplate error",e);
			return new Gson().toJson(new ReqMsg(2,"插入失败",""));
		}
		return new Gson().toJson(new ReqMsg(0,"succ",""));
	}
	
	@RequestMapping(value="updateone",produces="application/json;charset=UTF-8;")
	@ResponseBody
	public String updateone(String data) {
		if (data==null || data.length()==0) {
			return new Gson().toJson(new ReqMsg(1,"参数错误",""));
		}
		
		System.out.println(data);
		
		//将地区归类，便于下面使用
		Map<String,Map<String,List<Area>>> mapList = getMapArea();
		
		/*验证数据		开始*/
		ShipTemplatePa vo = new Gson().fromJson(data, ShipTemplatePa.class);
		if (vo==null) {
			return new Gson().toJson(new ReqMsg(1,"参数错误",""));
		}
		
		//判断 ship_name
		String ship_name = vo.getShip_name();
		if (ship_name==null || ship_name.length()==0 || ship_name.length()>32) {
			return new Gson().toJson(new ReqMsg(1,"参数错误",""));
		}
		
		//判断宝贝地址是否存在
		String start_area = vo.getStart_area();
		if (start_area==null || start_area.length()==0) {
			return new Gson().toJson(new ReqMsg(1,"参数错误",""));
		}
		List<String> arrArea = new ArrayList<String>();
		while (true) {
			Area area = areaService.get(start_area);
			if (area==null) {
				return new Gson().toJson(new ReqMsg(1,"参数错误",""));
			}
			arrArea.add(area.getArea_id());
			int level = Integer.valueOf(area.getArea_level());
			if (level<=1)
				break;
			start_area = area.getParent_area_id();
		}
		if (arrArea.size()==0) {
			return new Gson().toJson(new ReqMsg(1,"参数错误",""));
		}
		start_area = "";
		ListIterator<String> iter = arrArea.listIterator();
		while (iter.hasNext()) {
			iter.next();
		}
		while (iter.hasPrevious()) {
			start_area += iter.previous() + ",";
		}
		start_area = start_area.substring(0,start_area.length()-1);
		vo.setStart_area(start_area);

		String send_time_id = vo.getSend_time_id();
		String send_time = null;
		List<Commpara> list = CommparaFuc.getParaList("cfg_sendgoodstime");
		if (list!=null) {
			for (Commpara node : list) {
				if (node.getPara_key().equals(send_time_id)) {
					send_time = node.getPara_name();
					break;
				}
			}
		}
		if (send_time==null) {
			return new Gson().toJson(new ReqMsg(1,"参数错误",""));
		}
		
		
		int valuation_mode = vo.getValuation_mode();
		if (valuation_mode!=0 && valuation_mode!=1 && valuation_mode!=2) {
			return new Gson().toJson(new ReqMsg(1,"参数错误",""));
		}
		String express_id_str = vo.getExpress_id_str();
		if (!express_id_str.equals("0") && !express_id_str.equals("1") && !express_id_str.equals("2")) {
			return new Gson().toJson(new ReqMsg(1,"参数错误",""));
		}
		int free_ship = vo.getFree_ship();
		if (free_ship!=0 && free_ship!=1) {
			return new Gson().toJson(new ReqMsg(1,"参数错误",""));
		}
		
		List<AreasetPa> area_info = vo.getArea_info();
		if (area_info==null) {
			area_info = new ArrayList<AreasetPa>();
		}
		if (area_info.size()>0) {
			for (AreasetPa areaset : area_info) {
				String express_id = areaset.getExpress_id();
				if (express_id==null) {
					areaset.setExpress_id(" ");
				}
				int default_ship = areaset.getDefault_ship();
				if (default_ship!=0 && default_ship!=1) {
					return new Gson().toJson(new ReqMsg(1,"参数错误",""));
				}
//				areaset.setAs_id(new BigInteger("0"));
//				areaset.setShip_id(new BigInteger("0"));
				List<ReachAreaPa> reach_area = areaset.getReach_area();
				if (reach_area==null) {
					reach_area = new ArrayList<ReachAreaPa>();
				}
				if (reach_area.size()>0) {
					List<ReachAreaPa> arrReachAreaVo = new ArrayList<ReachAreaPa>();
					for (ReachAreaPa reachArea : reach_area) {
						
						//按前端规则遍历
						String end_area = reachArea.getEnd_area();
						if (end_area==null || end_area.length()==0) {
							return new Gson().toJson(new ReqMsg(1,"参数错误",""));
						}
						String[] arrStr = end_area.split("_");
						int count = arrStr.length;
						if (count==1) {	//区级
							String region = arrStr[0];
							Map<String,List<Area>> mapProvice = mapList.get(region);
							if (mapProvice==null || mapProvice.size()==0)
								continue;
							for (Entry<String,List<Area>> entry : mapProvice.entrySet()) {
								//String provice = entry.getKey();
								List<Area> listCity = entry.getValue();
								if (listCity==null || listCity.size()==0)
									continue;
								for (Area area : listCity) {
									arrReachAreaVo.add(new ReachAreaPa(new BigInteger("0"),new BigInteger("0"),area.getArea_id(),""));
								}
							}
						} else if (count==2) {	//省级
							String region = arrStr[0];
							String provice = arrStr[1];
							Map<String,List<Area>> mapProvice = mapList.get(region);
							if (mapProvice==null || mapProvice.size()==0)
								continue;
							List<Area> listCity = mapProvice.get(provice);
							if (listCity==null || listCity.size()==0)
								continue;
							for (Area area : listCity) {
								if (area.getArea_level().equals("2"))
									arrReachAreaVo.add(new ReachAreaPa(new BigInteger("0"),new BigInteger("0"),area.getArea_id(),""));
							}
							
						} else if (count == 3) {	//市级
							arrReachAreaVo.add(new ReachAreaPa(new BigInteger("0"),new BigInteger("0"),arrStr[2],""));
						} else {
							return new Gson().toJson(new ReqMsg(1,"参数错误",""));
						}
					}
					
					areaset.setReach_area(arrReachAreaVo);//重新赋值
				}
			}
		}

		
		//验证是否有默认模板
		if (vo.getFree_ship()==0) {
			int countDefault = 0;
			for (AreasetPa node : area_info) {
				int defaultShip = node.getDefault_ship();
				if (defaultShip!=0 && defaultShip!=1) {
					return new Gson().toJson(new ReqMsg(1,"参数错误",""));
				}
				if (defaultShip==1) {
					countDefault++;
					continue;
				}
			}
			if (countDefault!=1) {
				return new Gson().toJson(new ReqMsg(1,"参数错误",""));
			}
		} else {
			vo.setArea_info(new ArrayList<AreasetPa>());
		}
		/*验证数据		结束*/
		
		ShipTemplateVo voo = new ShipTemplateVo(vo);
		
		try {
			if (!logitempService.updateShipTemplate(voo)) {
				return new Gson().toJson(new ReqMsg(1,"更新失败",null));
			}
		} catch (Exception e) {
			logger.error("insertShipTemplate",e);
			return new Gson().toJson(new ReqMsg(1,"更新失败",null));
		}
		return new Gson().toJson(new ReqMsg(0,"succ",null));
	}
	
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		BigInteger ship_id = new BigInteger(parameter_id);
		if (this.logitempService.deleteShipTemplateStatus(ship_id)) {
			model.addAttribute("promptmsg","删除成功！");
		} else {
			model.addAttribute("promptmsg","运费模板已被引用,删除失败");
		}
		return list(request, model);
	}
	
	@RequestMapping("batchdelete")
	public String batchDelete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		List<BigInteger> arr_shipid = new ArrayList<BigInteger>();
		for(String id:ids){
			BigInteger ship_id = new BigInteger(id);
			arr_shipid.add(ship_id);
		}

		if(logitempService.batchShipTemplateDelete(arr_shipid)){
			model.addAttribute("promptmsg","批量删除成功");
		} else {
			model.addAttribute("promptmsg","部分运费模板已被引用,被引用的运费模板删除失败");
		}
		return list(request, model);
	}
	
	
	@RequestMapping("batchenablestate")
	public String batchEnableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) {
			return list(request,model);
		}
		//转成数组更新
		String[] ids = parameter_id.split(",");
		List<BigInteger> arr_shipid = new ArrayList<BigInteger>();
		for(String id:ids){
			BigInteger ship_id = new BigInteger(id);
			arr_shipid.add(ship_id);
		}
		if(logitempService.batchShipTemplateUpdateState(arr_shipid,1)){
			model.addAttribute("promptmsg","批量启用成功");
		}else{
			model.addAttribute("promptmsg","批量启用失败");
		}
		return list(request, model);
	}
	
	@RequestMapping("batchlimitstate")
	public String batchLimitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		List<BigInteger> arr_shipid = new ArrayList<BigInteger>();
		for(String id:ids){
			BigInteger ship_id = new BigInteger(id);
			arr_shipid.add(ship_id);
		}
		if(logitempService.batchShipTemplateUpdateState(arr_shipid,0)){
			model.addAttribute("promptmsg","批量禁用成功");
		}else{
			model.addAttribute("promptmsg","部分运费模板已被引用,被引用的运费模板禁用失败");
		}
		return list(request, model);
	}
}