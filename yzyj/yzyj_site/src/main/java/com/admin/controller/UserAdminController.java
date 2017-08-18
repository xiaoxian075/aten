package com.admin.controller;

/**
 * Created by Administrator on 2016/3/24.
 */

import com.admin.model.BaseExample;
import com.admin.model.User;
import com.admin.model.UserExample;
import com.admin.model.UserHandle;
import com.admin.service.UserHandleService;
import com.admin.service.UserService;
import com.admin.vo.MenuVo;
import com.admin.vo.UserVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.core.mybatis.DataTablesPage;
import com.core.util.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * 功能描述：
 * 作者： huangdw
 * 时间：2016/3/26
 */
@Controller
@RequestMapping(value = "/admin")
public class UserAdminController {
    @Resource
    private UserService userService;
    @Resource
    private UserHandleService userHandleService;
    @RequestMapping(method = RequestMethod.GET)
    public String init(HttpServletRequest request) {
        return "admin/login";
    }
    @RequestMapping(value = "login",method = RequestMethod.GET)
    public String login() {
        return "admin/login";
    }
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String dologin(User user, RedirectAttributes model, HttpServletRequest request) {
        try {
            // 身份验证
            // 验证成功在Session中保存用户信息
            final User authUserInfo = userService.selectByUserName(user.getUsername());
            String password = MD5Util.encodeByMD5(user.getPassword());
            if (authUserInfo != null) {
                if ("1".equals(authUserInfo.getStatus())) {
                    if (authUserInfo.getPassword().equals(password)) {
                        java.util.HashMap map = userService.selectUserMenu(authUserInfo.getRid());
                        List<MenuVo> menuVoList = (List<MenuVo>)map.get("menuVoList");
                        authUserInfo.setLastTime(new Date());
                        request.getSession().setAttribute("userInfo", authUserInfo);
                        request.getSession().setAttribute("menuVoList", menuVoList);
                        request.getSession().setAttribute("handleModule", map.get("handleModule"));
                        request.setAttribute("menuPid","1");
                        request.setAttribute("currentpage",null);
                        return "admin/index";
                    } else {
                        model.addFlashAttribute("error", "密码错误");
                        return "redirect:/rest/admin/login";
                    }
                }else{
                    model.addFlashAttribute("error", "帐号已被禁用");
                    return "redirect:/rest/admin/login";
                }
            } else {
                model.addFlashAttribute("error", "帐号错误");
                return "redirect:/rest/admin/login";
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 身份验证失败
            model.addFlashAttribute("error", "未知异常");
            return "redirect:/rest/admin/login";
        }
    }
    @RequestMapping(value = "indexInit",method = RequestMethod.GET)
    public String indexInit() {
        return "admin/index";
    }
    @RequestMapping(value = "/getUserList",method = RequestMethod.GET)
    public String getUserList() {
        return "admin/user/index";
    }
    @RequestMapping(value = "getList",method = RequestMethod.GET)
    @ResponseBody
    public String getList(DataTablesPage<UserVo> page, Model model, User user) {
        UserExample usersExample=new UserExample();
        UserExample.Criteria criteria = usersExample.createCriteria();
        if (user.getUsername()!=null){
            criteria.andUserNameLike("%"+user.getUsername()+"%");
        }
        if (!StringUtils.isEmpty(user.getNickname())) {
            criteria.andNicknameLike("%"+user.getNickname()+"%");
        }
//        usersExample.createCriteria().andIDEqualTo("1");
        List<UserVo> ls=userService.selectByUserAndPage(page,usersExample);
//            String ww="qwe";

        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }
    /**
     * 用户登出
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute("userInfo");
        return "admin/login";
    }
}
