package com.admin.controller;

import com.admin.model.BaseExample;
import com.admin.model.DictTable;
import com.admin.model.Recruitment;
import com.admin.service.DictTableService;
import com.admin.service.RecruitmentService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.core.entity.JSONResult;
import com.core.mybatis.DataTablesPage;
import com.core.util.CommDictList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/1/14.
 */
@Controller
@RequestMapping(value = "/admin/recruitment")
public class RecruitmentController extends BaseController {
    @Resource
    private RecruitmentService recruitmentService;
    @Resource
    private DictTableService dictTableService;
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String getUserList(Model model) {
        return "admin/recruitment/index";
    }
    @RequestMapping(value = "getList",method = RequestMethod.GET)
    @ResponseBody
    public String getList(DataTablesPage<Recruitment> page, Model model, String position) {
        try{
            BaseExample baseExample=new BaseExample();
            if (!StringUtils.isEmpty(position)){
                baseExample.createCriteria().andLike("position",position);
            }
            baseExample.setOrderByClause("px desc");
            recruitmentService.selectByExampleAndPage(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model,Recruitment recruitment, HttpServletRequest request) {
//        String[] requrl = request.getRequestURL().toString().split("/");
//        String path = "http://" + requrl[2] + request.getContextPath() +CommConstant.PROPAGANDA_PATH;
        if(StringUtils.isEmpty(recruitment.getId())){
            model.addAttribute("info", new Recruitment());
        }else{
            Recruitment recruitment1=recruitmentService.selectById(recruitment.getId());
            model.addAttribute("info",recruitment1);
        }
        List<DictTable> department= CommDictList.getDictOptionList("department");
        model.addAttribute("department", department);
//        model.addAttribute("path",path);
        return "admin/recruitment/edit";
    }
    @RequestMapping(value = "edit",method = RequestMethod.POST)
    public String edit(Recruitment recruitment, RedirectAttributes model ) {
        try {
            String msg="";
            if (StringUtils.isEmpty(recruitment.getId())){
                recruitment.setCreateTime(new Date());
                recruitmentService.insertSelective(recruitment);
                msg="新增成功";
            }else{
                recruitmentService.update(recruitment);
                msg="修改成功";
            }
            model.addFlashAttribute("msg", msg);
        } catch (Exception e) {
            e.printStackTrace();
            model.addFlashAttribute("msg", "操作失败！");
        }
        return "redirect:/rest/admin/recruitment/index";
    }
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<Recruitment> delete(Recruitment recruitment,HttpServletRequest request) {
        if (!org.springframework.util.StringUtils.isEmpty(recruitment.getId())) {
            try {
                recruitmentService.delete(recruitment.getId());
                return new JSONResult<Recruitment>(recruitment, "删除成功", true);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<Recruitment>(null, "删除失败", false);
            }
        }else {
            return new JSONResult<Recruitment>(null, "删除失败", false);
        }
    }
}
