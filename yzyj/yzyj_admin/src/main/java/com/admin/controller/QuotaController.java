package com.admin.controller;

import com.admin.model.BaseExample;
import com.admin.model.Quota;
import com.admin.service.DevicePayCardQuotaService;
import com.admin.service.QuotaService;
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

/**
 * Created by Administrator on 2016/11/29.
 */
@Controller
@RequestMapping(value = "/admin/quota")
public class QuotaController {
    @Resource
    private QuotaService quotaService;
    @Resource
    private DevicePayCardQuotaService devicePayCardQuotaService;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model) {
        return "admin/quota/index";
    }

    @RequestMapping(value = "getList",method = RequestMethod.GET)
    @ResponseBody
    public String getList(DataTablesPage<Quota> page, Model model, Quota quota) {
        try{
            BaseExample baseExample = new BaseExample();
            if(!StringUtils.isEmpty(quota.getQueryBody())){
                baseExample.or().andLike("DEVICE_UNIQUE",quota.getQueryBody());
            }
            quotaService.selectByExampleAndPage(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }

        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model, Quota quota) {
        if(StringUtils.isEmpty(String.valueOf(quota.getId()))){
            model.addAttribute("info",new Quota());
        }else{
            Quota quota1 = quotaService.selectByQuotaId(quota.getId());
            model.addAttribute("info",quota1);
        }
        return "admin/quota/edit";
    }

    @RequestMapping(value = "edit",method = RequestMethod.POST)
    public String edit(Quota quota, RedirectAttributes model ) {
        try {
            String msg="";
            if (StringUtils.isEmpty(String.valueOf(quota.getId()))){
                quotaService.insertQuotaInfo(quota);
                msg="新增成功";
            }else{
                quotaService.updateQuotaInfo(quota);
                msg="修改成功";
            }
            model.addFlashAttribute("msg", msg);
        } catch (Exception e) {
            e.printStackTrace();
            model.addFlashAttribute("msg", "操作失败！");
        }
        return "redirect:/rest/admin/quota/index";
    }

    @RequestMapping(value = "init", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<String> init(RedirectAttributes model) {
        try{
            devicePayCardQuotaService.initPayCardQuotaData();
            return new JSONResult<String>(null, "操作成功", false);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new JSONResult<String>(null, "删除失败", false);
    }

    @RequestMapping(value = "addJsp", method = RequestMethod.GET)
    public String add(Model model, Quota quota) {
        return "admin/quota/add";
    }

    @RequestMapping(value = "saveQuota", method = RequestMethod.POST)
    public String addQuota(Quota quota,RedirectAttributes model) {
        try {
            String msg="";
            if (quota.getId() == 0){
                Integer minGroup = quotaService.selectMinGroup();
                quota.setQuotaGroup(minGroup-1);
                quotaService.insertQuotaInfo(quota);
                msg="新增成功";
            }else{
                msg="新增失败";
            }
            model.addFlashAttribute("msg", msg);
        } catch (Exception e) {
            e.printStackTrace();
            model.addFlashAttribute("msg", "操作失败！");
        }
        return "redirect:/rest/admin/quota/index";
    }
}
