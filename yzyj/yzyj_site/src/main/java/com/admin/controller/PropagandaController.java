package com.admin.controller;

import com.admin.model.*;
import com.admin.service.PropagandaService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.core.entity.JSONResult;
import com.core.mybatis.DataTablesPage;
import com.core.util.CommConstant;
import com.core.util.CommDictList;
import com.core.util.FileUploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/1/13.
 */
@Controller
@RequestMapping(value = "/admin/propaganda")
public class PropagandaController extends BaseController{
    @Resource
    private PropagandaService propagandaService;
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String getUserList(Model model) {
        return "admin/propaganda/index";
    }
    @RequestMapping(value = "getList",method = RequestMethod.GET)
    @ResponseBody
    public String getList(DataTablesPage<Propaganda> page, Model model, String title) {
        try{
            BaseExample baseExample=new BaseExample();
            if (!StringUtils.isEmpty(title)){
                baseExample.createCriteria().andLike("title",title);
            }
            baseExample.setOrderByClause("px desc");
            propagandaService.selectByExampleAndPage(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model,Propaganda propaganda, HttpServletRequest request) {
        String[] requrl = request.getRequestURL().toString().split("/");
        String path = "http://" + requrl[2] + request.getContextPath() +CommConstant.PROPAGANDA_PATH;
        if(StringUtils.isEmpty(propaganda.getId())){
            model.addAttribute("info", new Propaganda());
        }else{
            Propaganda propaganda1=propagandaService.selectById(propaganda.getId());
            model.addAttribute("info",propaganda1);
        }
        model.addAttribute("path",path);
        List<DictTable> videoType= CommDictList.getDictOptionList("videoType");
        model.addAttribute("videoType", videoType);
        return "admin/propaganda/edit";
    }
    @RequestMapping(value = "edit",method = RequestMethod.POST)
    public String edit(Propaganda propaganda, RedirectAttributes model,HttpServletRequest request, @RequestParam(value = "file1", required = false) MultipartFile file1, @RequestParam(value = "file2", required = false) MultipartFile file2) throws IOException {
        try {
            String msg="";
            String path = request.getRealPath("/") + CommConstant.PROPAGANDA_PATH;
            if (StringUtils.isEmpty(propaganda.getId())) {
                if (file1.getSize() > 0) {
                    String picPath = FileUploadUtil.upLoadFile(file1, path);
                    propaganda.setPicPath(picPath);
//                    brand.setLogo(picPath);
                }
                if (file2.getSize() > 0) {
                    String picPath = FileUploadUtil.upLoadFile(file2, path);
                    propaganda.setPath(picPath);
//                    brand.setAdvertPath(picPath);
                }
                propaganda.setCreateTime(new Date());
                propagandaService.insertSelective(propaganda);
            }else{
                propagandaService.updatePropaganda(propaganda,path,file1,file2);
            }
            model.addFlashAttribute("msg", msg);
        } catch (Exception e) {
            e.printStackTrace();
            model.addFlashAttribute("msg", "未知异常！");
        }
        return "redirect:/rest/admin/propaganda/index";
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<Propaganda> delete(Propaganda propaganda,HttpServletRequest request) {
        if (!org.springframework.util.StringUtils.isEmpty(propaganda.getId())) {
            try {
               Propaganda propaganda1= propagandaService.selectById(propaganda.getId());
                propagandaService.delete(propaganda.getId());
                String path = request.getRealPath("/") + CommConstant.PROPAGANDA_PATH;
                FileUploadUtil.delFile(path+propaganda1.getPath());
                FileUploadUtil.delFile(path+propaganda1.getPicPath());
                return new JSONResult<Propaganda>(propaganda, "删除成功", true);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<Propaganda>(null, "删除失败", false);
            }
        }else {
            return new JSONResult<Propaganda>(null, "删除失败", false);
        }
    }
}
