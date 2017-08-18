package com.aten.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aten.model.orm.Cat;
import com.aten.service.CatService;
import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aten.function.CommparaFuc;
import com.aten.function.SysconfigFuc;
import com.aten.model.orm.Commpara;
import com.aten.model.orm.Sysconfig;
import com.aten.model.vo.GoodsUpdateVo;
import com.aten.service.SysconfigService;
import com.communal.util.AmountUtil;
import com.communal.util.StringUtil;


@Controller
@RequestMapping("admin/sysconfig")
public class SysconfigController extends BaseController{

	private static final Logger logger = Logger.getLogger(SysconfigController.class);
	
	@Autowired
	private SysconfigService sysconfigService;
	@Autowired
    private CatService catService;
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
	 * @Description 系统变量列表页
	 * @param
	 * @date 2017-1-4 上午11:58:06
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//paraMap = searchMap(request,paraMap,model);
		String var_type = request.getParameter("var_type_s");
		if(var_type==null||var_type.equals("")){
			var_type="0";
		}
		paraMap.put("var_type", var_type);
		model.addAttribute("var_type_s", var_type);
		int count = this.sysconfigService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<Sysconfig> sysconfigList = this.sysconfigService.getList(paraMap);
		model.addAttribute("sysconfigList", sysconfigList);
		//获取系统参数类型
		model.addAttribute("commparaList", CommparaFuc.getParaList("cfg_sysconfig_type"));
		return "sysconfig/list";
	}
	
	
	
	/**
	 * @author linjunqin
	 * @Description 跳转到添加系统变量页面
	 * @param
	 * @date 2017-1-4 下午4:53:04
	 */
	@RequestMapping("add")
	public String add(Sysconfig sysconfig,Model model){
		//获取系统参数类型
		List<Commpara> commparaList = CommparaFuc.getParaList("cfg_sysconfig_type");
		model.addAttribute("commparaList", commparaList);
		//判断变量类型为空则赋值
		if(sysconfig==null || sysconfig.getVar_type()==null 
				|| sysconfig.getVar_type().equals("") ){
			sysconfig = new Sysconfig();
			sysconfig.setVar_type("0");
			model.addAttribute("sysconfig", sysconfig);
		}
		return "sysconfig/insert";
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 添加系统变量
	 * @param
	 * @date 2017-1-4 下午4:53:58
	 */
	@RequestMapping("insert")
	public String insert(Sysconfig sysconfig,Model model){
		String var_name = sysconfig.getVar_name();
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("var_name", var_name);
		List<Sysconfig> sysconfigList = this.sysconfigService.getList(paraMap);
		//判断是否存在
		if(sysconfigList!=null && sysconfigList.size()>0){
			model.addAttribute("promptmsg","系统变量名已存在！");
			return add(sysconfig,model);
		}
		this.sysconfigService.insert(sysconfig);
		model.addAttribute("promptmsg","系统变量添加成功！");
		return add(sysconfig,model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 跳转到修改系统变量页面
	 * @param
	 * @date 2017-1-5 下午2:22:57
	 */
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,Model model,Sysconfig sysconfig){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		Sysconfig dbSysconfig = this.sysconfigService.get(parameter_id);
		if(sysconfig!=null && !StringUtil.isEmpty(sysconfig.getVar_type())){
			dbSysconfig.setVar_type(sysconfig.getVar_type());
		}
		model.addAttribute("sysconfig", dbSysconfig);
		//获取系统参数类型
		List<Commpara> commparaList = CommparaFuc.getParaList("cfg_sysconfig_type");
		model.addAttribute("commparaList", commparaList);
		return "sysconfig/update";
	}
	
	/**
	 * @author linjunqin
	 * @Description 修改系统变量
	 * @param
	 * @date 2017-1-5 下午2:29:42
	 */
	@RequestMapping("update")
	public String update(HttpServletRequest request,Sysconfig sysconfig,Model model){
		this.sysconfigService.update(sysconfig);
		model.addAttribute("promptmsg","系统变量修改成功！");
		return list(request,model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 更新系统变量
	 * @param
	 * @date 2017-5-17 上午12:08:41
	 */
	@RequestMapping("sysupdate")
	public void sysupdate(HttpServletRequest request,HttpServletResponse response){
		String cfg_id = request.getParameter("cfg_id");
		String cfg_value = request.getParameter("cfg_value");
		if(StringUtil.isEmpty(cfg_id) || StringUtil.isEmpty(cfg_value)){
			return ;
		}
		//查询数据
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("var_name", cfg_id);
		List<Sysconfig> sysconfigList = this.sysconfigService.getList(paraMap);
		if(sysconfigList!=null && sysconfigList.size() >0){
			Sysconfig sysconfig = sysconfigList.get(0);
			sysconfig.setVar_name(cfg_id);
			sysconfig.setVar_value(cfg_value);
			this.sysconfigService.update(sysconfig);
			outPrint(response, "ok");
		}
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 删除系统变量
	 * @param
	 * @date 2017-1-5 下午2:36:30
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.sysconfigService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","系统变量删除成功！");
		return list(request, model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 系统变量排序
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
		this.sysconfigService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 快速设置
	 * @param
	 * @date 2017-5-16 下午1:59:43
	 */
	@RequestMapping("fast")
	public String fast(HttpServletRequest request,Model model,Sysconfig sysconfig){
	    model.addAttribute("cfg_peripheral_radius",SysconfigFuc.getSysValue("cfg_peripheral_radius"));
	    model.addAttribute("cfg_order_autoclose",SysconfigFuc.getSysValue("cfg_order_autoclose"));
	    model.addAttribute("cfg_order_overtime",SysconfigFuc.getSysValue("cfg_order_overtime"));
	    model.addAttribute("cfg_agent_divide",SysconfigFuc.getSysValue("cfg_agent_divide"));
	    model.addAttribute("cfg_charge_divide",SysconfigFuc.getSysValue("cfg_charge_divide"));
	    model.addAttribute("cfg_order_claim",SysconfigFuc.getSysValue("cfg_order_claim"));
		return "sysconfig/fast";
	}

	/**
	 * @author chenyi
	 * @Description 积分设置页面
	 * @param
	 * @date 2017-1-4 上午11:58:06
	 */
	@RequestMapping("integral")
	public String integral(Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//paraMap = searchMap(request,paraMap,model);
		//每日签到
		paraMap.put("var_name", "cfg_sign_integral");
		List<Sysconfig> signList = this.sysconfigService.getList(paraMap);
		if (signList!=null&&signList.size()>0){
			model.addAttribute("cfg_sign_integral", signList.get(0));
		}
		paraMap.clear();
		//分享送积分
		paraMap.put("var_name", "cfg_share_integral");
		List<Sysconfig> shareList = this.sysconfigService.getList(paraMap);
		if (shareList!=null&&shareList.size()>0){
			model.addAttribute("cfg_share_integral", shareList.get(0));
		}
		return "sysconfig/integral";
	}
	/**
	 * @author chenyi
	 * @Description 积分设置更新
	 * @param
	 * @date date(2017-7-20
	 */
	@RequestMapping("updateIntegral")
	public String updateIntegral(HttpServletRequest request,Model model){
		String sign_id=request.getParameter("sign_id");
		String sign_value=request.getParameter("sign_value");
		String share_id=request.getParameter("share_id");
		String share_value=request.getParameter("share_value");
		if(sign_id==null ||"".equals(sign_id)) return integral(model);
		if(share_id==null ||"".equals(share_id)) return integral(model);
		if(!sign_value.matches("^[0-9]*[1-9][0-9]*$")){
			model.addAttribute("promptmsg","签到的积分必须为正整数！");
			return integral(model);
		}
		if(!share_value.matches("^[0-9]*[1-9][0-9]*$")){
			model.addAttribute("promptmsg","分享的积分必须为正整数！");
			return integral(model);
		}
		sysconfigService.SaveIntegral(sign_id,sign_value,share_id,share_value);
		model.addAttribute("promptmsg","积分设置成功！");
		return integral(model);
	}
    /**
     * @author chenyi
     * @Description 黄金设置页面
     * @param
     * @date 2017-1-4 上午11:58:06
     */
    @RequestMapping("gold")
    public String gold(Model model){
        HashMap<String, Object> paraMap = new HashMap<String,Object>();
        //paraMap = searchMap(request,paraMap,model);
        //当日金价
        paraMap.put("var_name", "cfg_gold_price");
        List<Sysconfig> priceList = this.sysconfigService.getList(paraMap);
        if (priceList!=null&&priceList.size()>0){
            model.addAttribute("cfg_gold_price", priceList.get(0));
        }
        paraMap.clear();
        //手工费
        paraMap.put("var_name", "cfg_gold_manual");
        List<Sysconfig> manualList = this.sysconfigService.getList(paraMap);
        if (manualList!=null&&manualList.size()>0){
            model.addAttribute("cfg_gold_manual", manualList.get(0));
        }
        paraMap.clear();
        //手工费
        paraMap.put("var_name", "cfg_gold_id");
        List<Sysconfig> idList = this.sysconfigService.getList(paraMap);
        if (idList!=null&&idList.size()>0){
            model.addAttribute("cfg_gold_id", idList.get(0));
        }
        //获取顶级商品分类
        paraMap.clear();
        paraMap.put("parent_cat_id", SysconfigFuc.getSysValue("cfg_goods_top"));
        List<Cat> catList=  catService.getList(paraMap);
        model.addAttribute("catList",catList);
        return "sysconfig/gold";
    }
    /**
     * @author chenyi
     * @Description 黄金设置更新
     * @param
     * @date date(2017-7-20
     */
    @RequestMapping("updateGold")
    public String updateGold(HttpServletRequest request,Model model){
        String price_id=request.getParameter("price_id");
        String price_value=request.getParameter("price_value");
        String gold_id=request.getParameter("gold_id");
        String gold_value=request.getParameter("gold_value");
        if(price_id==null ||"".equals(price_id)) return gold(model);
        if(gold_id==null ||"".equals(gold_id)) return gold(model);
        if(!price_value.matches("(^[1-9]([0-9]+)?(\\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\\.[0-9]([0-9])?$)")){
            model.addAttribute("promptmsg","请输入有效的金额！");
            return gold(model);
        }

        sysconfigService.SaveGold(price_id,price_value,gold_id,gold_value);
        
        //批量设置当日金价
        List<GoodsUpdateVo> list = sysconfigService.getGoldValueList(gold_value);
        if(list.size() >0){
        	for(GoodsUpdateVo goodsUpdateVo : list){
            	List<GoodsUpdateVo> listAttr = sysconfigService.getCustomValue(goodsUpdateVo.getGoods_id());
            	if(listAttr.size() > 0){
            		for(GoodsUpdateVo goodsUpdateVo2 : listAttr){
            			int value = Integer.parseInt(goodsUpdateVo2.getCustom_attr_value().substring(0, goodsUpdateVo2.getCustom_attr_value().length()-1));
            			sysconfigService.updateGoldsPrice(goodsUpdateVo2.getCustom_attr_value(),AmountUtil.yuanToFen(value * Integer.parseInt(price_value)),goodsUpdateVo2.getGoods_id());
            		}
            	}
            }
        }
        
        model.addAttribute("promptmsg","黄金设置并且更新成功！");
        return gold(model);
    }
}

