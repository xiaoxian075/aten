package com.admin.controller;

import com.admin.model.ApplyMerchants;
import com.admin.model.AttractMerchants;
import com.admin.model.BaseExample;
import com.admin.service.ApplyMerchantsService;
import com.admin.service.AttractMerchantsService;
import com.admin.service.DictTableService;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.core.entity.JSONResult;
import com.core.mybatis.DataTablesPage;
import com.core.util.FunUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by wjf on 2016/6/8.
 */
@Controller
@RequestMapping(value = "/admin/applyMerchants")
public class ApplyMerchantsController extends BaseController {
    @Resource
    private ApplyMerchantsService applyMerchantsService;
    @Resource
    private DictTableService dictTableService;
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model) {
        return "admin/applyMerchants/index";
    }
    @RequestMapping(value = "getList",method = RequestMethod.GET)
    @ResponseBody
    public String getList(DataTablesPage<ApplyMerchants> page, Model model, ApplyMerchants applyMerchants) {

        try{
            BaseExample baseExample = new BaseExample();
            if(!StringUtils.isEmpty(applyMerchants.getCompany())){
                baseExample.createCriteria().andEqualTo("company",applyMerchants.getCompany());
            }
            applyMerchantsService.selectByExampleAndPage(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }

        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model, ApplyMerchants applyMerchants) {
        if(FunUtil.isEmpty(applyMerchants.getId())){
            model.addAttribute("info",new ApplyMerchants());
        }else{
            ApplyMerchants attractMerchants1=applyMerchantsService.selectById(applyMerchants.getId()+"");
            model.addAttribute("info",attractMerchants1);
        }
        return "admin/applyMerchants/edit";
    }

    @RequestMapping(value = "edit",method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<String> edit(ApplyMerchants applyMerchants, RedirectAttributes model ) {
        try {
            String msg="";
            if (FunUtil.isEmpty(applyMerchants.getId())){
                applyMerchantsService.insertSelective(applyMerchants);
                msg="新增成功";
            }else{
                applyMerchantsService.update(applyMerchants);
                msg="修改成功";
            }
            return new JSONResult<String>(null, "成功", true);
        } catch (Exception e) {
            e.printStackTrace();
            model.addFlashAttribute("msg", "操作失败！");
        }
        return new JSONResult<String>(null, "失败", false);
    }
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<ApplyMerchants> delete(ApplyMerchants applyMerchants) {
        if (!org.springframework.util.StringUtils.isEmpty(applyMerchants.getId())) {
            try {
                applyMerchantsService.delete(applyMerchants.getId());
                return new JSONResult<ApplyMerchants>(applyMerchants, "删除成功", true);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<ApplyMerchants>(null, "删除失败", false);
            }
        }else {
            return new JSONResult<ApplyMerchants>(null, "删除失败", false);
        }
    }

}
