package com.admin.controller;

import com.admin.model.AttractMerchants;
import com.admin.model.BaseExample;
import com.admin.model.DictTable;
import com.admin.model.DynamicInfo;
import com.admin.service.AttractMerchantsService;
import com.admin.service.DictTableService;
import com.admin.service.DynamicInfoService;
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
import java.util.List;

/**
 * Created by wjf on 2016/6/8.
 */
@Controller
@RequestMapping(value = "/admin/attractMerchants/hz")
public class AttractMerchantsHZController extends BaseController {
    @Resource
    private AttractMerchantsService attractMerchantsService;
    @Resource
    private DictTableService dictTableService;
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model) {
        return "admin/attractMerchants/hz/index";
    }
    @RequestMapping(value = "getList",method = RequestMethod.GET)
    @ResponseBody
    public String getList(DataTablesPage<AttractMerchants> page, Model model, AttractMerchants attractMerchants) {

        try{
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
            if(!StringUtils.isEmpty(attractMerchants.getTitle())){
                criteria.andLike("title",attractMerchants.getTitle());
            }
            if(!StringUtils.isEmpty(attractMerchants.getNote())){
                criteria.andLike("note",attractMerchants.getNote());
            }
            criteria.andEqualTo("type","1");
            attractMerchantsService.selectByExampleAndPage(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }

        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model, AttractMerchants attractMerchants) {
        if(FunUtil.isEmpty(attractMerchants.getId())){
            model.addAttribute("info",new AttractMerchants());
        }else{
            AttractMerchants attractMerchants1=attractMerchantsService.selectById(attractMerchants.getId()+"");
            model.addAttribute("info",attractMerchants1);
        }
        return "admin/attractMerchants/hz/edit";
    }

    @RequestMapping(value = "edit",method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<String> edit(AttractMerchants attractMerchants, RedirectAttributes model ) {
        try {
            String msg="";
            if (FunUtil.isEmpty(attractMerchants.getId())){
                attractMerchants.setCreateTime(new Date());
                attractMerchants.setType(1);
                attractMerchantsService.insertSelective(attractMerchants);
                msg="新增成功";
            }else{
                attractMerchants.setLastTime(new Date());
                attractMerchantsService.update(attractMerchants);
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
    public JSONResult<AttractMerchants> delete(AttractMerchants attractMerchants) {
        if (!org.springframework.util.StringUtils.isEmpty(attractMerchants.getId())) {
            try {
                attractMerchantsService.delete(attractMerchants.getId());
                return new JSONResult<AttractMerchants>(attractMerchants, "删除成功", true);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<AttractMerchants>(null, "删除失败", false);
            }
        }else {
            return new JSONResult<AttractMerchants>(null, "删除失败", false);
        }
    }

}
