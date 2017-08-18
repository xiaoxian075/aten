package com.aten.controller;

import com.aten.client.JiguangClient;
import com.aten.model.bean.JPushBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 陈熠
 * 2017/7/31.
 */
@Controller
@RequestMapping("admin/push")
public class PushController extends BaseController {
    @RequestMapping("ios")
    public void getAccountMsg(HttpServletRequest request, Model model) {
        JPushBean msg = new JPushBean();
        msg.setAlias("14");
        msg.setType("3");
        msg.setTitle("title_标题测试");
        msg.setContent("content_内容内容内容内容内容内容内容内容");
        msg.setId("960");
        msg.setUserId("chenyi");
        JiguangClient.send("ios", msg);
    }
}
