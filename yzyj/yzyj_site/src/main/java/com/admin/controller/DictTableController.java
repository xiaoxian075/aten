package com.admin.controller;

import com.admin.model.BaseExample;
import com.admin.model.DictTable;
import com.admin.service.DictTableService;
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
 * Created by wjf on 2016/6/8.
 */
@Controller
@RequestMapping(value = "/admin/dicttable")
public class DictTableController {
    @Resource
    private DictTableService dictTableService;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model) {
        return "admin/dicttable/index";
    }
    @RequestMapping(value = "getList",method = RequestMethod.GET)
    @ResponseBody
    public String getList(DataTablesPage<DictTable> page, Model model, DictTable dictTable) {

        try{
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
            if (!StringUtils.isEmpty(dictTable.getDictTableBh())){
                criteria.andLike("dict_table_bh",dictTable.getDictTableBh());
            }
            if (!StringUtils.isEmpty(dictTable.getDictMc())){
                criteria.andLike("dict_mc",dictTable.getDictMc());
            }
            if (!StringUtils.isEmpty(dictTable.getDictTableMc())){
                criteria.andLike("dict_table_mc",dictTable.getDictTableMc());
            }
            baseExample.setOrderByClause("dict_table_bh , dict_bh ");
            dictTableService.selectByExampleAndPage(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }

        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model, DictTable dictTable) {
        if(StringUtils.isEmpty(dictTable.getSysId())){
            model.addAttribute("info",new DictTable());
        }else{
            DictTable dictTable1=dictTableService.selectById(dictTable.getSysId());
            model.addAttribute("info",dictTable1);
        }
        return "admin/dicttable/edit";
    }
    @RequestMapping(value = "edit",method = RequestMethod.POST)
    public String edit(DictTable dictTable, RedirectAttributes model ) {
        try {
            String msg="";
            if (StringUtils.isEmpty(dictTable.getSysId())){
                dictTableService.insertSelective(dictTable);
                msg="新增成功";
            }else{
                dictTableService.update(dictTable);
                msg="修改成功";
            }
            model.addFlashAttribute("msg", msg);
        } catch (Exception e) {
            e.printStackTrace();
            model.addFlashAttribute("msg", "操作失败！");
        }
        return "redirect:/rest/admin/dicttable/index";
    }
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<DictTable> delete(DictTable dictTable) {
        if (!org.springframework.util.StringUtils.isEmpty(dictTable.getSysId())) {
            try {
                dictTableService.delete(dictTable.getSysId());
                return new JSONResult<DictTable>(dictTable, "删除成功", true);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<DictTable>(null, "删除失败", false);
            }
        }else {
            return new JSONResult<DictTable>(null, "删除失败", false);
        }
    }
    @RequestMapping(value = "init", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<String> init(RedirectAttributes model) {
        try{
            dictTableService.initDictData();
            return new JSONResult<String>(null, "操作成功", false);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new JSONResult<String>(null, "删除失败", false);
    }
}
