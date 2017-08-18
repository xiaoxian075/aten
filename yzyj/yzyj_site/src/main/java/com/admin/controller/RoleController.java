package com.admin.controller;

import com.admin.model.Role;
import com.admin.model.RoleExample;
import com.admin.service.RoleService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.core.entity.JSONResult;
import com.core.mybatis.DataTablesPage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/5/25.
 */
@Controller
@RequestMapping(value = "/admin/role")
public class RoleController {
    @Resource
    private RoleService roleService;
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String getUserList(Model model) {
        return "admin/role/index";
    }
    @RequestMapping(value = "getList",method = RequestMethod.GET)
    @ResponseBody
    public String getList(DataTablesPage<Role> page, Model model, Role role, String pid) {
        try{
            RoleExample roleExample=new RoleExample();
            RoleExample.Criteria criteria = roleExample.createCriteria();
            roleService.selectByExampleAndPage(page,roleExample);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model, Role role, HttpServletRequest request) {
        if(role.getId()==null){
            model.addAttribute("info", new Role());
        }else{
            Role role1=roleService.selectById(role.getId());
            model.addAttribute("info",role1);
        }
        return "admin/role/edit";
    }
    @RequestMapping(value = "edit",method = RequestMethod.POST)
    public String edit(Role role, RedirectAttributes model) {
        try {
            String msg="";
            if (role.getId()==null){
                role.setEnabled("1");
                roleService.insertSelective(role);
                    msg="添加角色成功";
            }else{
            roleService.update(role);
                msg="修改角色成功";
            }
            model.addFlashAttribute("msg", msg);
        } catch (Exception e) {
            e.printStackTrace();
            model.addFlashAttribute("msg", "未知异常！");
        }
        return "redirect:/rest/admin/role/index";
    }
    @RequestMapping(value = "editStatus",method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<String> editStatus(Role role, RedirectAttributes model) {
        try {
            roleService.update(role);
            return new JSONResult(null,"操作成功",true);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult(null,"操作失败",false);
        }
    }

}
