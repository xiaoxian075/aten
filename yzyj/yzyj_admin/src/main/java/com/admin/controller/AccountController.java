package com.admin.controller;

import com.admin.service.AccountService;
import com.admin.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/12/14.
 */
@Controller
@RequestMapping(value = "/admin/account")
public class AccountController {
    @Resource
    private AccountService accountService;
    @Resource
    private UserService userService;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model) {
        return "admin/account/index1";
    }
}
