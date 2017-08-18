package com.admin.controller;

import com.admin.model.BaseExample;
import com.admin.model.DictTable;
import com.admin.model.DynamicInfo;
import com.admin.model.ServiceMark;
import com.admin.service.DictTableService;
import com.admin.service.ServiceMarkService;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.core.entity.JSONResult;
import com.core.mybatis.DataTablesPage;
import com.core.util.CommConstant;
import com.core.util.CommDictList;
import com.core.util.FileUploadUtil;
import com.core.util.FunUtil;
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
import java.util.Date;
import java.util.List;

/**
 * Created by ${huangdw} on 2017/3/25.
 */
@Controller
@RequestMapping(value = "/admin/service")
public class ServiceMarkController {
    @Resource
    private ServiceMarkService serviceMarkService;
    @Resource
    private DictTableService dictTableService;
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model) {
        return "admin/serviceMark/index";
    }
    @RequestMapping(value = "getList",method = RequestMethod.GET)
    @ResponseBody
    public String getList(DataTablesPage<ServiceMark> page, Model model, ServiceMark serviceMark) {

        try{
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
//            if(!StringUtils.isEmpty(dynamicInfo.getTitle())){
//                criteria.andLike("title",dynamicInfo.getTitle());
//            }
//            if(!FunUtil.isEmpty(dynamicInfo.getType())){
//                criteria.andEqualTo("type",dynamicInfo.getType()+"");
//            }
            serviceMarkService.selectByExampleAndPage(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }

        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model, ServiceMark dynamicInfo) {
        List<DictTable> dynamic_info_type= CommDictList.getDictOptionList("ServiceType");
        model.addAttribute("serviceType", dynamic_info_type);
        if(StringUtils.isEmpty(dynamicInfo.getId())){
            model.addAttribute("info",new ServiceMark());
        }else{
            ServiceMark serviceMark=serviceMarkService.selectById(dynamicInfo.getId());
            model.addAttribute("info",serviceMark);
        }
        return "admin/serviceMark/edit";
    }
    @RequestMapping(value = "edit",method = RequestMethod.POST)
    public String edit(HttpServletRequest request, ServiceMark serviceMark, RedirectAttributes model , @RequestParam(value = "file1", required = false) MultipartFile file) {
        try {
            String msg="";
            String path = request.getRealPath("/") + CommConstant.SERVICE_PATH;
            if (StringUtils.isEmpty(serviceMark.getId())){
                if (file.getSize()>0) {
                    String picPath = FileUploadUtil.upLoadFile(file, path);
                    serviceMark.setLogo(picPath);
                }
                serviceMark.setCreateTime(new Date());
                serviceMarkService.insertSelective(serviceMark);
                msg="新增成功";
            }else{
                if (file.getSize()>0) {
                    String picPath = FileUploadUtil.upLoadFile(file, path);
                    String oldPath=serviceMark.getLogo();
                    FileUploadUtil.delFile(path+oldPath);
                    serviceMark.setLogo(picPath);
                }
                serviceMarkService.update(serviceMark);
                msg="修改成功";
            }
            model.addFlashAttribute("msg", msg);
        } catch (Exception e) {
            e.printStackTrace();
            model.addFlashAttribute("msg", "操作失败！");
        }
        return "redirect:/rest/admin/service/index";
    }
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<DynamicInfo> delete(DynamicInfo dynamicInfo) {
        if (!org.springframework.util.StringUtils.isEmpty(dynamicInfo.getId())) {
            try {
                serviceMarkService.delete(dynamicInfo.getId());
                return new JSONResult<DynamicInfo>(dynamicInfo, "删除成功", true);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<DynamicInfo>(null, "删除失败", false);
            }
        }else {
            return new JSONResult<DynamicInfo>(null, "删除失败", false);
        }
    }
}
