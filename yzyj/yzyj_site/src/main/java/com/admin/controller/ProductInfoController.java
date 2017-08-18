package com.admin.controller;

import com.admin.model.BaseExample;
import com.admin.model.DictTable;
import com.admin.model.ProductInfo;
import com.admin.service.DictTableService;
import com.admin.service.ProductInfoService;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.core.entity.JSONResult;
import com.core.mybatis.DataTablesPage;
import com.core.util.CommConstant;
import com.core.util.CommDictList;
import com.core.util.FunUtil;
import com.core.util.UploadUtil;
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
@RequestMapping(value = "/admin/productInfo")
public class ProductInfoController extends BaseController {
    @Resource
    private ProductInfoService productInfoService;
    @Resource
    private DictTableService dictTableService;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model) {
        List<DictTable> product_info_type=dictTableService.selectList("product_info_type");
        model.addAttribute("product_info_type", product_info_type);
        return "admin/productInfo/index";
    }
    @RequestMapping(value = "getList",method = RequestMethod.GET)
    @ResponseBody
    public String getList(DataTablesPage<ProductInfo> page, Model model, ProductInfo productInfo) {
        try{
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
            if(!StringUtils.isEmpty(productInfo.getTitle())){
                criteria.andLike("title",productInfo.getTitle());
            }
            if(!FunUtil.isEmpty(productInfo.getType())){
                criteria.andEqualTo("type",productInfo.getType()+"");
            }
            baseExample.setOrderByClause("px desc");
            productInfoService.selectByExampleAndPage(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }

        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model, ProductInfo productInfo) {
        List<DictTable> product_info_type= CommDictList.getDictOptionList("product_info_type");
        model.addAttribute("product_info_type", product_info_type);
        if(StringUtils.isEmpty(productInfo.getId())){
            model.addAttribute("info",new ProductInfo());
        }else{
            ProductInfo productInfo1 =productInfoService.selectById(productInfo.getId());
            String[] arr = productInfo1.getImg().split(",");
            model.addAttribute("info",productInfo1);
            model.addAttribute("imgList",arr);
        }
        return "admin/productInfo/edit";
    }

    @RequestMapping(value = "edit",method = RequestMethod.POST)
    public String edit(HttpServletRequest request,ProductInfo productInfo, RedirectAttributes model , @RequestParam(value = "file1", required = false) List<MultipartFile> file) {
        try {
            String msg="";
            String path = request.getRealPath("/") + CommConstant.PRODUCT_PATH;
            String picPath = "";
            if (StringUtils.isEmpty(productInfo.getId())){
                if (file.size() > 0) {
                    for (MultipartFile multipartFile : file) {
                        if (multipartFile.getSize() > 0L) {
                           String  oneImgPath = UploadUtil.upLoadFile(multipartFile, path);
                           picPath+=oneImgPath+",";
                        }
                    }
                    if(picPath.length()>1&& picPath.endsWith(",")){
                        picPath=picPath.substring(0,picPath.length()-1);
                    }
                    productInfo.setImg(picPath);
                }
                productInfo.setCreateTime(new Date());
                productInfoService.insertSelective(productInfo);
                msg="新增成功";
            }else{
                if (file.size() > 0) {
                    for (MultipartFile multipartFile : file) {
                        if (multipartFile.getSize() > 0L) {
                            String  oneImgPath = UploadUtil.upLoadFile(multipartFile, path);
                            picPath+=oneImgPath+",";
                        }
                    }
                }
                if(picPath.length()>1&& picPath.endsWith(",")){
                    picPath=picPath.substring(0,picPath.length()-1);
                    productInfo.setImg(picPath);
                }
                productInfoService.update(productInfo);
                msg="修改成功";
            }
            model.addFlashAttribute("msg", msg);
        } catch (Exception e) {
            e.printStackTrace();
            model.addFlashAttribute("msg", "操作失败！");
        }
        return "redirect:/rest/admin/productInfo/index";
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<ProductInfo> delete(ProductInfo productInfo) {
        if (!org.springframework.util.StringUtils.isEmpty(productInfo.getId())) {
            try {
                productInfoService.delete(productInfo.getId());
                return new JSONResult<ProductInfo>(productInfo, "删除成功", true);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<ProductInfo>(null, "删除失败", false);
            }
        }else {
            return new JSONResult<ProductInfo>(null, "删除失败", false);
        }
    }

}
