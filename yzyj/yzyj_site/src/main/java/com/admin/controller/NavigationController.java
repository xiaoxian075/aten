package com.admin.controller;

import com.admin.model.Advert;
import com.admin.model.BaseExample;
import com.admin.model.DictTable;
import com.admin.model.Navigation;
import com.admin.service.NavigationService;
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
 * Created by Administrator on 2017/3/22.
 */
@Controller
@RequestMapping(value = "/admin/navigation")
public class NavigationController {
    @Resource
    private NavigationService navigationService;
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model) {
//        List<DictTable> dynamic_info_type=dictTableService.selectList("dynamic_info_type");
//        model.addAttribute("dynamic_info_type", dynamic_info_type);
        return "admin/navigation/index";
    }
    @RequestMapping(value = "getList",method = RequestMethod.GET)
    @ResponseBody
    public String getList(DataTablesPage<Navigation> page, Model model) {
        try{
            BaseExample baseExample=new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
            navigationService.selectByExampleAndPage(page, baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }

        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model, Navigation navigation, HttpServletRequest request) {
//        String[] requrl = request.getRequestURL().toString().split("/");
//        String path = "http://" + requrl[2] + request.getContextPath() + CommConstant.ADVERT_PATH;
//        //初始字典
//        List<DictTable> advertType= CommDictList.getDictOptionList("advert_type");
//        model.addAttribute("advertType", advertType);
//        List<DictTable> advertCategory= CommDictList.getDictOptionList("advert_category");
//        model.addAttribute("advertCategory", advertCategory);
//
//        if(StringUtils.isEmpty(advert.getId())){
//            model.addAttribute("info", new Advert());
//        }else{
//            Advert Advert1=advertService.selectById(advert.getId());
//            model.addAttribute("info",Advert1);
//            model.addAttribute("path",path);
//        }
        String[] requrl = request.getRequestURL().toString().split("/");
        String path = "http://" + requrl[2] + request.getContextPath() + CommConstant.NAVIGATION_PATH;
        if (StringUtils.isEmpty(navigation.getId())){
            model.addAttribute("info",new Navigation());
        }else{
            Navigation navigation1=navigationService.selectById(navigation.getId());
            model.addAttribute("info",navigation1);
        }
        BaseExample baseExample=new BaseExample();
        BaseExample.Criteria criteria = baseExample.createCriteria();
        criteria.andIsNull("pid").andEqualTo("status","1");
        List <Navigation> ls=navigationService.selectByExample(baseExample);
        model.addAttribute("navigation",ls);
        model.addAttribute("path",path);
        return "admin/navigation/edit";
    }
    @RequestMapping(value = "edit",method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<Navigation> edit(Navigation navigation, RedirectAttributes model, HttpServletRequest request, @RequestParam(value = "file1", required = false) MultipartFile file) {
        try {
            String msg="";
            String path = request.getRealPath("/") + CommConstant.NAVIGATION_PATH;
            if (StringUtils.isEmpty(navigation.getId())){
                if (file.getSize()>0) {
                    String picPath = FileUploadUtil.upLoadFile(file, path);
                    navigation.setLogo(picPath);
                }
                navigation.setStatus(1);
                navigationService.insertSelective(navigation);
                msg="添加成功";
            }else{
                navigationService.updateNavigation(file,path,navigation);
                msg="修改成功";
            }
            return new JSONResult<Navigation>(navigation, msg, true);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult<Navigation>(null, "未知异常", false);
        }

    }
    @RequestMapping(value = "editNavigation", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<Navigation> editNavigation(Navigation navigation) {
        if (!org.springframework.util.StringUtils.isEmpty(navigation.getId())) {
            try {
                navigationService.update(navigation);
                return new JSONResult<Navigation>(navigation, "操作成功", true);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<Navigation>(null, "操作失败", false);
            }
        }else {
            return new JSONResult<Navigation>(null, "操作失败", false);
        }
    }
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<Navigation> delete(Navigation navigation) {
        if (!org.springframework.util.StringUtils.isEmpty(navigation.getId())) {
            try {
                navigationService.delete(navigation.getId());
                return new JSONResult<Navigation>(navigation, "操作成功", true);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<Navigation>(null, "操作失败", false);
            }
        }else {
            return new JSONResult<Navigation>(null, "操作失败", false);
        }
    }
}
