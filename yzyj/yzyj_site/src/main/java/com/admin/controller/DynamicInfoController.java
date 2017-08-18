package com.admin.controller;

import com.admin.model.BaseExample;
import com.admin.model.DictTable;
import com.admin.model.DynamicInfo;
import com.admin.service.DictTableService;
import com.admin.service.DynamicInfoService;
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
 * Created by wjf on 2016/6/8.
 */
@Controller
@RequestMapping(value = "/admin/dynamicInfo")
public class DynamicInfoController extends BaseController {
    @Resource
    private DynamicInfoService dynamicInfoService;
    @Resource
    private DictTableService dictTableService;
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model) {
        List<DictTable> dynamic_info_type=dictTableService.selectList("dynamic_info_type");
        model.addAttribute("dynamic_info_type", dynamic_info_type);
        return "admin/dynamicInfo/index";
    }
    @RequestMapping(value = "getList",method = RequestMethod.GET)
    @ResponseBody
    public String getList(DataTablesPage<DynamicInfo> page, Model model, DynamicInfo dynamicInfo) {

        try{
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
            if(!StringUtils.isEmpty(dynamicInfo.getTitle())){
                criteria.andLike("title",dynamicInfo.getTitle());
            }
            if(!FunUtil.isEmpty(dynamicInfo.getType())){
                criteria.andEqualTo("type",dynamicInfo.getType()+"");
            }
            baseExample.setOrderByClause("px desc");
            dynamicInfoService.selectByExampleAndPage(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }

        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model, DynamicInfo dynamicInfo) {
        List<DictTable> dynamic_info_type= CommDictList.getDictOptionList("dynamic_info_type");
        model.addAttribute("dynamic_info_type", dynamic_info_type);
        if(StringUtils.isEmpty(dynamicInfo.getId())){
            model.addAttribute("info",new DynamicInfo());
        }else{
            DynamicInfo dynamicInfo1=dynamicInfoService.selectById(dynamicInfo.getId());
            model.addAttribute("info",dynamicInfo1);
        }
        return "admin/dynamicInfo/edit";
    }
    @RequestMapping(value = "edit",method = RequestMethod.POST)
    public String edit(HttpServletRequest request,DynamicInfo dynamicInfo, RedirectAttributes model , @RequestParam(value = "file1", required = false) MultipartFile file) {
        try {
            String msg="";
            String path = request.getRealPath("/") + CommConstant.ADVERT_PATH;
            if (StringUtils.isEmpty(dynamicInfo.getId())){
                if (file.getSize()>0) {
                    String picPath = FileUploadUtil.upLoadFile(file, path);
                    dynamicInfo.setImg(picPath);
                }
                dynamicInfo.setCreateTime(new Date());
                dynamicInfoService.insertSelective(dynamicInfo);
                msg="新增成功";
            }else{
                if (file.getSize()>0) {
                    String picPath = FileUploadUtil.upLoadFile(file, path);
                    String oldPath=dynamicInfo.getImg();
                    FileUploadUtil.delFile(path+oldPath);
                    dynamicInfo.setImg(picPath);
                }
                dynamicInfoService.update(dynamicInfo);
                msg="修改成功";
            }
            model.addFlashAttribute("msg", msg);
        } catch (Exception e) {
            e.printStackTrace();
            model.addFlashAttribute("msg", "操作失败！");
        }
        return "redirect:/rest/admin/dynamicInfo/index";
    }
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<DynamicInfo> delete(DynamicInfo dynamicInfo) {
        if (!org.springframework.util.StringUtils.isEmpty(dynamicInfo.getId())) {
            try {
                dynamicInfoService.delete(dynamicInfo.getId());
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
