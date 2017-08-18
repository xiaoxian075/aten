package com.html;

import com.admin.model.*;
import com.admin.service.*;
import com.admin.vo.DictVo;
import com.core.entity.JSONResult;
import com.core.mybatis.DataTablesPage;
import com.core.util.AppFunUtil;
import com.core.util.FunUtil;
import com.core.util.Sign;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Administrator on 2017/3/22.
 */
@Controller
@RequestMapping(value = "/html/index")
public class HtmlController {

    @Resource
    private AttractMerchantsService attractMerchantsService;
    @Resource
    private DynamicInfoService dynamicInfoService;
    @Resource
    private PropagandaService propagandaService;
    @Resource
    private RecruitmentService recruitmentService;
    @Resource
    private ProductInfoService productInfoService;

    @Resource
    private ApplyMerchantsService applyMerchantsService;
    @Resource
    private AdvertService advertService;
    @Resource
    private NavigationService navigationService;
    @Resource
    private ServiceMarkService serviceMarkService;
    @Resource
    private PlanService planService;

    /**
     * 申请入驻
     */
    @RequestMapping(value = "applyMerchants", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<HashMap> applyMerchants(HttpServletRequest request, ApplyMerchants applyMerchants) throws Exception {
        try {
            java.util.HashMap map = new java.util.HashMap<>();
            applyMerchants.setCreateTime(new Date());
            applyMerchants.setIp(AppFunUtil.getIpAddr(request));
            applyMerchantsService.insertSelective(applyMerchants);
            return new JSONResult<java.util.HashMap>(map, "成功", true, 1);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult<java.util.HashMap>(null, "失败", false, 0);
        }
    }

    /**
     * 获取 首页数据
     */
    @RequestMapping(value = "getIndexData", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<java.util.HashMap> getIndexData(HttpServletRequest request) throws Exception {
        try {
            BaseExample baseExample = new BaseExample();
            java.util.HashMap map = new java.util.HashMap<>();
            /**
             * 获取 图片新闻
             */
            baseExample.createCriteria().andLessThan("rownum", "3").andEqualTo("type", "12"); // 获取2条 图片新闻
            baseExample.setOrderByClause("create_time desc ,px ");
            List<DynamicInfo> imgNewsList = dynamicInfoService.selectByExample(baseExample);

            map.put("imgNewsList", imgNewsList);

            baseExample = new BaseExample();
            /**
             * 获取新闻数据
             * 获取最近添加的6条记录
             */
            List<DynamicInfo> list = dynamicInfoService.selectByExample(baseExample);
            map.put("newsList", list);
            /**
             * 获取视频数据
             */
            baseExample = new BaseExample();
            baseExample.createCriteria().andEqualTo("rownum", "1");
            List<Propaganda> videoList = propagandaService.selectByExample(baseExample);
            if (videoList.size() > 0) {
                Propaganda propaganda = videoList.get(0);
                map.put("videoData", propaganda);
            }

            /**
             * 获取广告位
             */
            baseExample = new BaseExample();
            baseExample.createCriteria().andEqualTo("type", "1").andEqualTo("STATUS", "1"); // 获取首页广告位
            baseExample.setOrderByClause("px ");
            List<Advert> advertList = advertService.selectByExample(baseExample);
            map.put("advertList", advertList);
            /**
             * 获取首页导航
             */
            baseExample = new BaseExample();
            baseExample.createCriteria().andEqualTo("status", "1").andIsNull("pid");
            baseExample.setOrderByClause("px");
            List<Navigation> navigationList=navigationService.selectByExample(baseExample);
            map.put("navigationList",navigationList);
            return new JSONResult<java.util.HashMap>(map, "成功", true, 1);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult<java.util.HashMap>(null, "失败", false, 0);
        }
    }

    /**
     * 获取 公司动态 列表
     */
    @RequestMapping(value = "getGSDynamicList", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<DataTablesPage<DynamicInfo>> getGSDynamicList(HttpServletRequest request, DataTablesPage<DynamicInfo> page) throws Exception {
        try {
            BaseExample baseExample = new BaseExample();
            baseExample.createCriteria().andEqualTo("type", "11");
            baseExample.setOrderByClause("px");
            dynamicInfoService.selectByExampleAndPage(page, baseExample);
            java.util.List<DynamicInfo> list = new java.util.ArrayList<>();
            for (DynamicInfo dynamicInfo : page.getData()) {
                dynamicInfo.setBody("");
                list.add(dynamicInfo);
            }
            page.setData(list);
            page.setResult(list);
            return new JSONResult<DataTablesPage<DynamicInfo>>(page, "成功", true, 1);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult<DataTablesPage<DynamicInfo>>(null, "失败", false, 0);
        }
    }

    /**
     * 获取 集团动态 列表
     */
    @RequestMapping(value = "getJTDynamicList", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<DataTablesPage<DynamicInfo>> getJTDynamicList(HttpServletRequest request, DataTablesPage<DynamicInfo> page) throws Exception {
        try {
            BaseExample baseExample = new BaseExample();
            baseExample.createCriteria().andEqualTo("type", "10");
            baseExample.setOrderByClause("px");
            dynamicInfoService.selectByExampleAndPage(page, baseExample);
            java.util.List<DynamicInfo> list = new java.util.ArrayList<>();
            for (DynamicInfo dynamicInfo : page.getData()) {
                dynamicInfo.setBody("");
                list.add(dynamicInfo);
            }
            page.setData(list);
            page.setResult(list);
            return new JSONResult<DataTablesPage<DynamicInfo>>(page, "成功", true, 1);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult<DataTablesPage<DynamicInfo>>(null, "失败", false, 0);
        }
    }

    /**
     * 获取 产品 列表
     */
    @RequestMapping(value = "getProductInfoList", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<DataTablesPage<ProductInfo>> getProductInfoList(HttpServletRequest request, DataTablesPage<ProductInfo> page,String type) throws Exception {
        try {
            BaseExample baseExample = new BaseExample();
            baseExample.createCriteria().andEqualTo("type", type);
            baseExample.setOrderByClause("px");
            productInfoService.selectByExampleAndPage(page, baseExample);
            java.util.List<ProductInfo> list = new java.util.ArrayList<>();
            for (ProductInfo productInfo : page.getData()) {
                String[] one_img = productInfo.getImg().split(",");
                productInfo.setBody("");
                productInfo.setSub_img(one_img[0]);
                list.add(productInfo);
            }
            page.setData(list);
            page.setResult(list);
            return new JSONResult<DataTablesPage<ProductInfo>>(page, "成功", true, 1);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult<DataTablesPage<ProductInfo>>(null, "失败", false, 0);
        }
    }

    /**
     * 获取动态详情
     */
    @RequestMapping(value = "getProductInfoDetail", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<java.util.HashMap> getProductInfoDetail(HttpServletRequest request, String id) throws Exception {
        try {
            if (!FunUtil.isEmpty(id)) {
                ProductInfo productInfo = productInfoService.selectById(id);
                java.util.HashMap map = new java.util.HashMap<>();
                String str_img[] = productInfo.getImg().split(",");
                productInfo.setStr_img(str_img);
                map.put("data", productInfo);
                return new JSONResult<java.util.HashMap>(map, "成功", true, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult<java.util.HashMap>(null, "失败", false, 0);
        }
        return new JSONResult<java.util.HashMap>(null, "失败", false, 0);
    }

    /**
     * 获取动态详情
     */
    @RequestMapping(value = "getDynamicInfo", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<java.util.HashMap> getDynamicInfo(HttpServletRequest request, String id) throws Exception {
        try {
            if (!FunUtil.isEmpty(id)) {
                DynamicInfo dynamicInfo = dynamicInfoService.selectById(id);
                BaseExample baseExample = new BaseExample();
                baseExample.createCriteria().andEqualTo("type", dynamicInfo.getType()).andLessThan("px", dynamicInfo.getPx()).andEqualTo("rownum", "1");
                List<DynamicInfo> upList = dynamicInfoService.selectByExample(baseExample);
                baseExample = new BaseExample();
                baseExample.createCriteria().andEqualTo("type", dynamicInfo.getType()).andGreaterThan("px", dynamicInfo.getPx()).andEqualTo("rownum", "1");
                List<DynamicInfo> downList = dynamicInfoService.selectByExample(baseExample);
                java.util.HashMap map = new java.util.HashMap<>();
                if (upList.size() > 0) {
                    DynamicInfo upData = upList.get(0);
                    map.put("upId", upData.getId());
                    map.put("upTitle", upData.getTitle());
                }
                if (downList.size() > 0) {
                    DynamicInfo downData = downList.get(0);
                    map.put("downId", downData.getId());
                    map.put("downTitle", downData.getTitle());
                }
                map.put("data", dynamicInfo);
                return new JSONResult<java.util.HashMap>(map, "成功", true, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult<java.util.HashMap>(null, "失败", false, 0);
        }
        return new JSONResult<java.util.HashMap>(null, "失败", false, 0);
    }

    /**
     * 招商合作标准
     */
    @RequestMapping(value = "attractMerchantsStandard", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<List<AttractMerchants>> attractMerchantsStandard(HttpServletRequest request) throws Exception {
        try {
            BaseExample baseExample = new BaseExample();
            baseExample.createCriteria().andEqualTo("type", "1");
            baseExample.setOrderByClause("px");
            List<AttractMerchants> list = attractMerchantsService.selectByExample(baseExample);
            return new JSONResult<List<AttractMerchants>>(list, "成功", true, 1);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult<List<AttractMerchants>>(null, "失败", false, 0);
        }
    }

    /**
     * 招商合作优势
     */
    @RequestMapping(value = "attractMerchantsBeneficial", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<List<AttractMerchants>> attractMerchantsBeneficial(HttpServletRequest request) throws Exception {
        try {
            BaseExample baseExample = new BaseExample();
            baseExample.createCriteria().andEqualTo("type", "2");
            baseExample.setOrderByClause("px");
            List<AttractMerchants> list = attractMerchantsService.selectByExample(baseExample);
            return new JSONResult<List<AttractMerchants>>(list, "成功", true, 1);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult<List<AttractMerchants>>(null, "失败", false, 0);
        }
    }

    /**
     * 获取视频
     */
    @RequestMapping(value = "getVideo", method = RequestMethod.GET)
    @ResponseBody
    public JSONResult<Map> getPropaganda(Recruitment recruitment, HttpServletRequest request) {
        try {
            List<DictVo> ls = propagandaService.selectVideo();
            Map map = new HashMap();
            map.put("video", ls);
            return new JSONResult<Map>(map, "查询成功", true, 1);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult<Map>(null, "查询失败", false, 0);
        }
    }

    /**
     * 根据类型获取视频
     */
    @RequestMapping(value = "getVideoByType", method = RequestMethod.GET)
    @ResponseBody
    public JSONResult<DataTablesPage<Propaganda>> getVideoByType(Recruitment recruitment, HttpServletRequest request, DataTablesPage<Propaganda> page, String type) {
        try {
            if (!StringUtils.isEmpty(type)) {
                BaseExample baseExample = new BaseExample();
                baseExample.createCriteria().andEqualTo("type", type);
                List<Propaganda> ls = propagandaService.selectByExampleAndPage(page, baseExample);
                return new JSONResult<DataTablesPage<Propaganda>>(page, "查询成功", true, 1);
            } else {
                return new JSONResult<DataTablesPage<Propaganda>>(null, "类型不能为空", false, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult<DataTablesPage<Propaganda>>(null, "查询失败", false, 0);
        }
    }

    /**
     * 获取招聘列表
     */
    @RequestMapping(value = "getRecruitment", method = RequestMethod.GET)
    @ResponseBody
    public JSONResult<Map> getRecruitment(Recruitment recruitment, HttpServletRequest request) {
        try {
            List<DictVo> ls = recruitmentService.selectRecruitment();
            List<DictVo> list = new ArrayList<>();
            for (DictVo dictVo : ls) {
                if (dictVo.getList() != null) {
                    if (dictVo.getList().size() > 0) {
                        list.add(dictVo);
                    }
                }

            }
            Map map = new HashMap();
            map.put("recruitment", list);
            ls = null;
            return new JSONResult<Map>(map, "查询成功", true, 1);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult<Map>(null, "查询失败", false, 0);
        }
    }

    /**
     * 获取招聘信息
     */
    @RequestMapping(value = "getRecruitmentById", method = RequestMethod.GET)
    @ResponseBody
    public JSONResult<Map> getRecruitmentById(HttpServletRequest request, String id) {
        try {
            if (!StringUtils.isEmpty(id)) {
                Recruitment recruitment1 = recruitmentService.selectById(id);
                Map map = new HashMap();
                map.put("recruitment", recruitment1);
                return new JSONResult<Map>(map, "查询成功", true, 1);
            } else {
                return new JSONResult<Map>(null, "ID不能为空", false, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult<Map>(null, "查询失败", false, 0);
        }
    }
    /**
     * 获取服务详情
     */
    @RequestMapping(value = "getServiceDetail", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<ServiceMark> getServiceDetail(HttpServletRequest request, String id) throws Exception {
        try {
            if (!FunUtil.isEmpty(id)) {
                ServiceMark serviceMark=serviceMarkService.selectById(id);
                return new JSONResult<ServiceMark>(serviceMark, "成功", true, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult<ServiceMark>(null, "失败", false, 0);
        }
        return new JSONResult<ServiceMark>(null, "失败", false, 0);
    }
    /**
     * 获取服务列表
     */
    @RequestMapping(value = "getService", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult< List<DictVo>> getService(HttpServletRequest request) throws Exception {
        try {
           List<DictVo> ls= serviceMarkService.selectService();
            return new JSONResult< List<DictVo>>(ls, "成功", true, 1);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult< List<DictVo>>(null, "失败", false, 0);
        }
    }
    /**
     * 获取解决方案详情
     */
    @RequestMapping(value = "getPlanDetail", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<Plan> getPlanDetail(HttpServletRequest request, String id) throws Exception {
        try {
            if (!FunUtil.isEmpty(id)) {
                Plan plan=planService.selectById(id);
                return new JSONResult<Plan>(plan, "成功", true, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult<Plan>(null, "失败", false, 0);
        }
        return new JSONResult<Plan>(null, "失败", false, 0);
    }
    /**
     * 获取解决方案列表
     */
    @RequestMapping(value = "getPlan", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult< List<Plan>> getPlan(HttpServletRequest request) throws Exception {
        try {
            BaseExample baseExample=new BaseExample();
            baseExample.setOrderByClause("px asc");
            List<Plan> ls= planService.selectByExample(baseExample);
            return new JSONResult< List<Plan>>(ls, "成功", true, 1);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult< List<Plan>>(null, "失败", false, 0);
        }
    }

    @RequestMapping(value = "getSign", method = RequestMethod.GET)
    @ResponseBody
    public JSONResult<Map> getSign(HttpServletRequest request){
        try {
            String url = request.getParameter("url");
            if (!StringUtils.isEmpty(url)) {
                HashMap<String, String> map = Sign.getSign(url);
                return new JSONResult<Map>(map, "操作成功", true, 1);
            }else{
                return new JSONResult<Map>(null, "URL不能为空", false,0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult<Map>(null, "操作失败", false,0);
        }
    }
}
