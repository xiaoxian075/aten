package com.admin.controller;

import com.admin.model.BaseExample;
import com.admin.model.Carousel;
import com.admin.model.DictTable;
import com.admin.service.CarouselService;
import com.admin.service.DictTableService;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.core.entity.JSONResult;
import com.core.mybatis.DataTablesPage;
import com.core.util.UploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2016/12/7.
 * 后台APP轮播图
 */
@Controller
@RequestMapping(value = "/admin/carousel")
public class CarouselController {

    @Resource
    private CarouselService carouselService;
    @Resource
    private DictTableService dictTableService;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model) {
        return "admin/carousel/index";
    }

    @RequestMapping(value = "getList",method = RequestMethod.GET)
    @ResponseBody
    public String getList(DataTablesPage<Carousel> page, Model model, Carousel carousel) {
        try{
            BaseExample baseExample = new BaseExample();
            if(!StringUtils.isEmpty(carousel.getQueryBody())){
                baseExample.or().andLike("TITLE",carousel.getQueryBody());
                baseExample.or().andLike("TYPE",carousel.getQueryBody());
            }
            carouselService.selectByExampleAndPage(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }


    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model, Carousel carousel) {
        try{
            List<DictTable> carouselType = dictTableService.selectList("carousel_type");
            model.addAttribute("carouselType", carouselType);
            if(carousel.getId()==0){
                model.addAttribute("info",new Carousel());
            }else{
                Carousel carousel1 = carouselService.getCarouselByCId(carousel.getId());
                model.addAttribute("info",carousel1);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return "admin/carousel/edit";
    }

    @RequestMapping(value = "edit",method = RequestMethod.POST)
    public String edit(HttpServletRequest request,
                       Carousel carousel,
                       Model model,
                       @RequestParam("file") MultipartFile file) {
        try {
            String msg="";
            String path = request.getRealPath("/")+"picCommon/infoPic/";
            if (carousel.getId() == 0){
                String fileName = UploadUtil.upLoadFile(file, path);
                carousel.setImgUrl(fileName);
                carouselService.insertCarousel(carousel);
                msg="新增成功";
            }else{
                if (file.getSize() > 0L) {
                    String fileName = UploadUtil.upLoadFile(file, path);
                    carousel.setImgUrl(fileName);
                }
                carouselService.updateCarousel(carousel);
                msg="修改成功";
            }
            model.addAttribute("msg", msg);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg", "操作失败！");
        }
        return "redirect:/rest/admin/carousel/index";
    }

    @RequestMapping(value = "editStatus", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<Carousel> editStatus(Carousel carousel) {
        if (carousel.getId() != 0) {
            try {
                carouselService.updateStatus(carousel.getId(),carousel.getStatus());
                return new JSONResult<Carousel>(null, "操作成功", true);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<Carousel>(null, "操作失败", false);
            }
        }else {
            return new JSONResult<Carousel>(null, "操作失败", false);
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<Carousel> delete(Carousel carousel) {
        if (carousel.getId() !=0) {
            try {
                carouselService.deleteById(carousel.getId());
                return new JSONResult<Carousel>(carousel, "删除成功", true);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<Carousel>(null, "删除失败", false);
            }
        }else {
            return new JSONResult<Carousel>(null, "删除失败", false);
        }
    }
}
