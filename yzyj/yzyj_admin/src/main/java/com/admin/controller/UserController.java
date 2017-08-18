package com.admin.controller;

import com.admin.model.*;
import com.admin.service.AgencyPersonService;
import com.admin.service.RoleService;
import com.admin.service.UserService;
import com.admin.vo.UserVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.core.entity.JSONResult;
import com.core.mybatis.DataTablesPage;
import com.core.util.AppFunUtil;
import com.core.util.CommDictList;
import com.core.util.EncryptUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 */
@Controller
@RequestMapping(value = "/admin/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private AgencyPersonService agencyPersonService;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String getUserList(Model model) {
        return "admin/user/index";
    }
    @RequestMapping(value = "getList",method = RequestMethod.GET)
    @ResponseBody
    public String getList(DataTablesPage<UserVo> page, Model model, User user, String pid) {
        try{
            UserExample userExample=new UserExample();
            UserExample.Criteria criteria = userExample.createCriteria();
//            if (!StringUtils.isEmpty(user.getRid())){
//                criteria
//            }
//            if (!StringUtils.isEmpty(pid)){
//                List<ProductType> ls=typeService.selectByPid(pid);
//                List<String> typeIds=new ArrayList<>();
//                for (ProductType productType:ls){
//                    typeIds.add(productType.getTypeId());
//                }
//                criteria.andTypeIdIn(typeIds);
//            }
//            typeService.selectByTypeAndPage(page, productTypeExample);
            userService.selectByUserAndPage(page,userExample);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model, User user, HttpServletRequest request) {
        if(user.getId()==null){
            model.addAttribute("info", new User());
        }else{
            User user1=userService.selectById(user.getId()+"");
            model.addAttribute("info",user1);
        }
        return "admin/user/edit";
    }

    @RequestMapping(value = "edit",method = RequestMethod.POST)
    public String edit(User user, RedirectAttributes model, String confirmpass) {
        try {
            String msg="";
            if (user.getId()==null){
                if (user.getPassword().equals(confirmpass)){
                    user.setCreatetime(new Date());
                    String pass= EncryptUtil.generatePassword(user.getPassword());
                    user.setPassword(pass);
                    user.setStatus("1");
                    userService.insertSelective(user);
                    msg="添加帐号成功";
                }else{
                    msg="两次密码输入不一样";
                }
            }else{
                userService.update(user);
                msg="修改帐号成功";
            }
            model.addFlashAttribute("msg", msg);
        } catch (Exception e) {
            e.printStackTrace();
            model.addFlashAttribute("msg", "未知异常！");
        }
        return "redirect:/rest/admin/user/index";
    }
    @RequestMapping(value = "editRole", method = RequestMethod.GET)
    public String editRole(Model model, User user, HttpServletRequest request) {
        RoleExample roleExample=new RoleExample();
        RoleExample.Criteria criteria=roleExample.createCriteria();
        criteria.andEnabledEqualTo("1");
        List<Role> ls=roleService.selectByExample(roleExample);
        User user1=userService.selectById(user.getId());
        model.addAttribute("info",user1);
        model.addAttribute("ls",ls);
        return "admin/user/editRole";
    }

    @RequestMapping(value = "editRole",method = RequestMethod.POST)
    public String editRole(User user, RedirectAttributes model) {
        try {
            String msg="";
                userService.update(user);
                msg="操作成功";
            model.addFlashAttribute("msg", msg);
        } catch (Exception e) {
            e.printStackTrace();
            model.addFlashAttribute("msg", "未知异常！");
        }
        return "redirect:/rest/admin/user/index";
    }
    @RequestMapping(value = "editStatus",method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<String> editStatus(User user, RedirectAttributes model) {
        try {
            userService.update(user);
            return new JSONResult(null,"操作成功",true);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult(null,"操作失败",false);
        }
    }
    @RequestMapping(value = "updatePass", method = RequestMethod.GET)
    public String updatePass(Model model, HttpServletRequest request) {
        User user = AppFunUtil.getUser(request);
        if(user.getId()==null){
            model.addAttribute("info", new User());
        }else{
            model.addAttribute("info",user);
        }
        AgencyPerson agencyPerson = AppFunUtil.getAgencyPerson(request);
        if(agencyPerson.getAgentUnique()==null){
            model.addAttribute("agencyPerson", new AgencyPerson());
        }
        return "admin/common/pass";
    }


    @RequestMapping(value = "updatePass", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<User> updatePass(User user, RedirectAttributes model, String confirmPass, String oldPass, String newPass) {
        try {
            if (user.getId()==null){
                return new JSONResult<User>(null, "帐号不存在", false);
            }else{
                if(confirmPass == null || oldPass == null || newPass == null){
                    return new JSONResult<User>(null, "填写信息不完整", false);
                }
                if (newPass.equals(confirmPass)){
                    User user1=userService.selectById(user.getId());
                    String old= EncryptUtil.generatePassword(oldPass);
                    if (user1.getPassword().equals(old)){
                        String pass= EncryptUtil.generatePassword(newPass);
                        user.setPassword(pass);
                        user.setStatus("1");
                        userService.update(user);
                        return new JSONResult<User>(null, "修改成功,请重新登录！", true);
                    }else{
                        return new JSONResult<User>(null, "输入的旧密码有误", false);
                    }
                }else{
                    return new JSONResult<User>(null, "两次新密码不一样", false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult<User>(null, "未知异常", false);
        }
    }

    @RequestMapping(value = "initPass", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<User> initPass(User user, RedirectAttributes model) {
        try {
            if (user.getId()==null){
                return new JSONResult<User>(null, "帐号不存在", false);
            }else{
                    User user1=userService.selectById(user.getId());
                    String pwd= CommDictList.getDictVal ("init_user_pwd","pwd");
                    String pass= EncryptUtil.generatePassword(pwd);
                    user1.setPassword(pass);
                    userService.update(user1);
                    return new JSONResult<User>(null, "初始化密码成功,请重新登录修改密码！", true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult<User>(null, "未知异常", false);
        }
    }

}
