package com.aten.controller;
import com.communal.util.RandomCheckCodeUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aten.function.LoginLogFuc;
import com.aten.model.orm.Manager;
import com.aten.model.orm.Role;
import com.aten.service.LoginLogService;
import com.aten.service.ManagerService;
import com.aten.service.RoleService;
import com.communal.constants.Constant;
import com.communal.util.Md5Util;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.communal.util.RandomCheckCodeUtil.checkRandomCode;

@Controller
@RequestMapping("/")
public class IndexController extends BaseController{

	private static final Logger logger = Logger.getLogger(IndexController.class);
	
	@Autowired
	private ManagerService managerService;
	@Autowired
	private LoginLogService loginlogService;
	@Autowired
	private RoleService roleService;
	
	/**
	 * @author linjunqin
	 * @Description 跳转到登录页面
	 * @param
	 * @date 2017-1-4 下午4:53:04
	 */
	@RequestMapping(value="login")  
	public String login(Model model){
		return Constant.LOGIN_URL;
	}

	
	/**
	 * @author linjunqin
	 * @Description 验证登录
	 * @param
	 * @date 2017-1-20 下午2:40:15
	 */
	@RequestMapping(value="loginAccount")
	public String loginAccount(Model model,HttpServletResponse response){
		HttpServletRequest request = this.getRequest();
		String user_name = request.getParameter("user_name");
		String pass_word = request.getParameter("pass_word");
		String inputCode = request.getParameter("inputCode");
		if ("".equals(user_name)||user_name==null){
			model.addAttribute("loginMsg","用户名不能为空");
			return login(model);
		}
		if ("".equals(pass_word)||pass_word==null){
			model.addAttribute("loginMsg","密码不能为空");
			return login(model);
		}
		if ("".equals(inputCode)||inputCode==null){
			model.addAttribute("loginMsg","验证码不能为空");
			return login(model);
		}
		//验证验证码
		boolean checkCode = checkRandomCode(request, inputCode);
		if (checkCode==false){
			model.addAttribute("loginMsg","验证码错误");
			return login(model);
		}
		//用户名跟密码不为空，则开始验证数据
		HashMap<String, Object> loginMap = new HashMap<String, Object>();
		loginMap.put("mana_name", user_name);
		loginMap.put("state", "1");
		List<Manager> manaList = this.managerService.getList(loginMap);
		//判断用户名是否存在
		//如果不存在
		if (manaList.size() == 0) {
			model.addAttribute("loginMsg","用户名不存在");
			return login(model);
		}
		String pwd=manaList.get(0).getPassword();
		String pass_word_md5 = Md5Util.getMD5(pass_word);
		if(pass_word_md5.equals(pwd)){
			//登录成功
			logger.info("用户登录成功！");
			Manager manager = manaList.get(0);
			String back_id =  manager.getMana_id();
			//如果不是超级用户，则有权限控制
			if(manager.getMana_type().equals("1")){
				String role_code = manager.getRole_code();
				//获取角色对应的权限
				Role role = roleService.get(role_code);
				request.getSession().setAttribute(Constant.POWER_RIGHT, role.getPower_right());
				request.getSession().setAttribute(Constant.MENU_RIGHT, role.getMenu_right());
			}
			request.getSession().setAttribute(Constant.MANA_TYPE,manager.getMana_type());
			request.getSession().setAttribute(Constant.USER_ID,back_id);
			request.getSession().setAttribute(Constant.USER_NAME, user_name);
			request.getSession().setAttribute(Constant.USER_ROLE,manager.getRole_name());
			//加入登录日志
			LoginLogFuc.addBackLoginLog(back_id, user_name, request);
			return  "redirect:/admin/index";
		}else{
//			 if(request.getSession().getAttribute("errorCount")==null){
//			 	request.getSession().setAttribute("errorCount",1);
//			 }else{
//				 request.getSession().setAttribute("errorCount",(int)request.getSession().getAttribute("errorCount")+1);
//			 }
//			 //登录失败 超过三次    显示验证码
//			if ((int)request.getSession().getAttribute("errorCount")>=3){
//				getCheckCode(request,response);
//			}
			model.addAttribute("loginMsg", "密码不正确！");
		}
		return login(model);
	}
	@RequestMapping(value="getCheckCode")
	public void getCheckCode(HttpServletRequest request,HttpServletResponse response){
		//获取验证码 并返回给前端 session.setAttribute(RANDOM_CODE_KEY, sRand.toString());
		RandomCheckCodeUtil.createRandomCode(request,response);

}
	//Integer.parseInt(request.getSession().getAttribute("errorCount").toString())
	/**
	 * @author linjunqin
	 * @Description 无操作权限的跳转
	 * @param
	 * @date 2017-2-16 下午8:57:03
	 */
	@RequestMapping(value="nopower") 
	public String nopower(Model model){
		return  Constant.NOT_POWER_PAGE;
	}
	
	/**
	 * @author linjunqin
	 * @Description  500错误页面
	 * @param
	 * @date 2017年8月1日 下午9:00:58 
	 */
	@RequestMapping(value="error") 
	public String error(Model model){
		return  Constant.ERROR;
	}
	
	/**
	 * @author linjunqin
	 * @Description 后台首页
	 * @param
	 * @date 2017-1-20 下午2:15:17
	 */
	@RequestMapping("admin/index")
	public String adminindex(Model model){
		return Constant.BACK_INDEX_URL;
	}
	

	/**
	 * @author linjunqin
	 * @Description 退出登录
	 * @param
	 * @date 2017-2-3 上午11:58:47
	 */
	@RequestMapping("loginexit")
	public String loginexit(Model model){
		getSession().invalidate();
		return Constant.LOGIN_URL;
	}


	/**
	 * @author linjunqin
	 * @Description 重复提交跳转提示
	 * @param
	 * @date 2017-7-2 下午3:55:31
	 */
	@RequestMapping("recommit")
	public String recommit(Model model){
		return Constant.RECOMMIT_URL;
	}
}

