package com.admin.controller;

import com.admin.model.Advert;
import com.admin.model.BaseExample;
import com.admin.model.DictTable;
import com.admin.service.AdvertService;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.core.entity.JSONResult;
import com.core.mybatis.DataTablesPage;
import com.core.util.CommConstant;
import com.core.util.CommDictList;
import com.core.util.FileUploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2016/4/11.
 */
@Controller
@RequestMapping(value = "/admin/advert")
public class AdvertController extends BaseController {
    @Resource
    private AdvertService advertService;
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model) {
        return "admin/advert/index";
    }
    @RequestMapping(value = "getList",method = RequestMethod.GET)
    @ResponseBody
    public String getList(DataTablesPage<Advert> page, Model model, Advert advert) {
        try{
            BaseExample baseExample=new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
            advertService.selectByTypeAndPage(page, baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }

        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model, Advert advert, HttpServletRequest request) {
        String[] requrl = request.getRequestURL().toString().split("/");
        String path = "http://" + requrl[2] + request.getContextPath() + CommConstant.ADVERT_PATH;
        //初始字典
        List<DictTable> advertType= CommDictList.getDictOptionList("advert_type");
        model.addAttribute("advertType", advertType);
        List<DictTable> advertCategory= CommDictList.getDictOptionList("advert_category");
        model.addAttribute("advertCategory", advertCategory);

        if(StringUtils.isEmpty(advert.getId())){
            model.addAttribute("info", new Advert());
        }else{
            Advert Advert1=advertService.selectById(advert.getId());
            model.addAttribute("info",Advert1);
            model.addAttribute("path",path);
        }
        return "admin/advert/edit";
    }
    @RequestMapping(value = "edit",method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<Advert> edit(Advert advert, RedirectAttributes model, HttpServletRequest request, @RequestParam(value = "file1", required = false) MultipartFile file) {
        try {
            String msg="";
            String path = request.getRealPath("/") + CommConstant.ADVERT_PATH;
            if (StringUtils.isEmpty(advert.getId())){
                if (file.getSize()>0) {
                    String picPath = FileUploadUtil.upLoadFile(file, path);
                    advert.setPath(picPath);
                }
                advert.setStatus(1);
                advertService.insertSelective(advert);
                msg="添加品牌成功";
            }else{
                advertService.updateAdvert(file,path,advert);
                msg="修改品牌成功";
            }
            return new JSONResult<Advert>(advert, msg, true);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult<Advert>(null, "未知异常", false);
        }

    }
    @RequestMapping(value = "editAdvert", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<Advert> editAdvert(Advert advert) {
        if (!org.springframework.util.StringUtils.isEmpty(advert.getId())) {
            try {
                advertService.update(advert);
                return new JSONResult<Advert>(advert, "操作成功", true);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<Advert>(null, "操作失败", false);
            }
        }else {
            return new JSONResult<Advert>(null, "操作失败", false);
        }
    }
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<Advert> delete(Advert advert) {
        if (!org.springframework.util.StringUtils.isEmpty(advert.getId())) {
            try {
                advertService.delete(advert.getId());
                return new JSONResult<Advert>(advert, "操作成功", true);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<Advert>(null, "操作失败", false);
            }
        }else {
            return new JSONResult<Advert>(null, "操作失败", false);
        }
    }
}
