package com.admin.controller;

import com.admin.model.Role;
import com.admin.model.RoleExample;
import com.admin.service.MenuService;
import com.admin.service.RoleService;
import com.admin.vo.NodeVo;
import com.alibaba.fastjson.JSONObject;
import com.core.entity.JSONResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/5/25.
 */
@Controller
@RequestMapping(value = "/admin/auth")
public class AuthController {
    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String getUserList(Model model) {
        RoleExample roleExample=new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andEnabledEqualTo("1");
        List<Role> ls=roleService.selectByExample(roleExample);
        model.addAttribute("roleList",ls);
        return "admin/auth/index";
    }
    /**
     * 展示类目树
     * @param model
     * @return
     */
    @RequestMapping(value = "getMenuTree",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult getMenuTree(Model model, String roleid){
        List<NodeVo> list = menuService.selectMenuNode(roleid);
        JSONResult<Object> result = new JSONResult<>(JSONObject.toJSON(list),"message",true);
        return result;
    }

    @RequestMapping(value = "auth",method = RequestMethod.POST)
    @ResponseBody
    public JSONResult auth(String roleId, String...menuIds){
//        List<RoleFunction> role = roleService.selectByFunction(roleId);
//        boolean success = roleFunctionService.authRoleFunctionByIdList(role,dto.getGndms());
        try {
            roleService.updateRoleFunction(roleId,menuIds);
            return new JSONResult(null,"操作成功",true);
        }catch ( Exception e){
            e.printStackTrace();
            return new JSONResult(null,"操作失败",false);
        }


    }
}
