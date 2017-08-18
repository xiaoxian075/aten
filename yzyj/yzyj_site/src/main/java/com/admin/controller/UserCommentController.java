package com.admin.controller;

import com.admin.model.BaseExample;
import com.admin.model.UserComment;
import com.admin.service.UserCommentService;
import com.alibaba.druid.util.StringUtils;
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
import java.util.Date;

/**
 * Created by Administrator on 2016/6/8.
 */
@Controller
@RequestMapping(value = "/admin/usercomment")
public class UserCommentController {
    @Resource
    private UserCommentService userCommentService;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model) {
        return "admin/usercomment/index";
    }
    @RequestMapping(value = "getList",method = RequestMethod.GET)
    @ResponseBody
    public String getList(DataTablesPage<UserComment> page, Model model, UserComment userComment) {

        try{
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
            userCommentService.selectByExampleAndPage(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }

        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model, UserComment userComment) {
        if(StringUtils.isEmpty(userComment.getSysId())){
            model.addAttribute("info",new UserComment());
        }else{
            UserComment userComment1=userCommentService.selectById(userComment.getSysId());
            model.addAttribute("info",userComment1);
        }
        return "admin/usercomment/edit";
    }
    @RequestMapping(value = "edit",method = RequestMethod.POST)
    public String edit(UserComment userComment, RedirectAttributes model ) {
        try {
            String msg="";
            if (StringUtils.isEmpty(userComment.getSysId())){
                userComment.setCreateDate(new Date());
                userCommentService.insertSelective(userComment);
                msg="新增成功";
            }else{
                userCommentService.update(userComment);
                msg="修改成功";
            }
            model.addFlashAttribute("msg", msg);
        } catch (Exception e) {
            e.printStackTrace();
            model.addFlashAttribute("msg", "操作失败！");
        }
        return "redirect:/rest/admin/usercomment/index";
    }
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<UserComment> delete(UserComment userComment) {
        if (!org.springframework.util.StringUtils.isEmpty(userComment.getSysId())) {
            try {
                userCommentService.delete(userComment.getSysId());
                return new JSONResult<UserComment>(userComment, "删除成功", true);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<UserComment>(null, "删除失败", false);
            }
        }else {
            return new JSONResult<UserComment>(null, "删除失败", false);
        }
    }
}
