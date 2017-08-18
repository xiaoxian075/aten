package com.admin.controller;

import com.admin.model.*;
import com.admin.service.AgencyPersonService;
import com.admin.service.BusinessService;
import com.admin.service.DeviceListService;
import com.admin.service.OrderService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.core.entity.JSONResult;
import com.core.mybatis.DataTablesPage;
import com.core.util.ExportExcelUtil;
import com.core.util.FunUtil;
import com.core.util.UploadUtil;
import com.core.util.YunPayUtil;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/11/30.
 * 商户信息
 */
@Controller
@RequestMapping(value = "/admin/business")
public class BusinessController {
    @Resource
    private BusinessService businessService;
    @Resource
    private AgencyPersonService agencyPersonService;
    @Resource
    private DeviceListService deviceListService;
    @Resource
    private OrderService orderService;

    /**
     * 跳转页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) {
        return "admin/business/index";
    }

    /**
     * 获取列表(可根据条件查询)
     * MERCHANT_NO：商户号
     * MERCHANT_NAME：商户名
     * YUN_PAY_ACCOUNT：商户云支付账号
     * CREATE_TIME：添加时间
     * AGENCY_NAME：代理人姓名
     * @param page
     * @param model
     * @param business
     * @return
     */
    @RequestMapping(value = "getList", method = RequestMethod.GET)
    @ResponseBody
    public String getList(DataTablesPage<Business> page, Model model, Business business) {
        try {
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
            //搜索条件
            if (!StringUtils.isEmpty(business.getMerchantNo())) {
                criteria.andEqualTo("MERCHANT_NO", business.getMerchantNo());
            }
            if (!StringUtils.isEmpty(business.getMerchantName())) {
                criteria.andLike("MERCHANT_NAME", business.getMerchantName());
            }
            if (!StringUtils.isEmpty(business.getYunPayAccount())) {
                criteria.andEqualTo("YUN_PAY_ACCOUNT", business.getYunPayAccount());
            }
            if (business.getSdate() != null) {
                String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(business.getSdate());
                criteria.andGreaterThanOrEqualTo("CREATE_TIME", s);
            }
            if (business.getEdate() != null) {
                String e = new SimpleDateFormat("yyyy-MM-dd").format(business.getEdate());
                criteria.andLessThanOrEqualTo("CREATE_TIME", e + " 23:59:59");
            }
            if (!StringUtils.isEmpty(business.getAgencyName())) {
                criteria.andEqualTo("AGENCY_NAME", business.getAgencyName());
            }
            criteria.andEqualTo("1", "1");
            baseExample.setOrderByClause("CREATE_TIME desc ");
            businessService.selectByExampleAndPage(page, baseExample);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    /**
     *
     * @param page
     * @param model
     * @param business
     * @return
     */
    @RequestMapping(value = "getBusinessInfo", method = RequestMethod.GET)
    @ResponseBody
    public String getBusinessInfo(DataTablesPage<Business> page, Model model, Business business) {
        try {
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
            if (!StringUtils.isEmpty(business.getYunPayAccount())) {
                criteria.andEqualTo("YUN_PAY_ACCOUNT", business.getYunPayAccount());
            }
            criteria.andEqualTo("1", "1");
            baseExample.setOrderByClause("CREATE_TIME desc ");
            businessService.selectByExampleAndPage(page, baseExample);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 跳转添加或者修改商户信息页面
     * @param model
     * @param business
     * @return
     */
    @RequestMapping(value = "editBusiness", method = RequestMethod.GET)
    public String edit(Model model, Business business) {
        try {
            List<AgencyPerson> agencyPerson1 = agencyPersonService.getAgencyList();
            //添加
            if (business.getId() == 0) {
                model.addAttribute("info", business);
                model.addAttribute("agencyList", agencyPerson1);
            } else {
                //修改
                Business business1 = businessService.selectByBId(business.getId());
                List<BusinessPicture> businessPicture = businessService.selectByPId(business.getId());
                model.addAttribute("info", business1);//商户信息
                model.addAttribute("infoP", businessPicture);//商户上传照片
                model.addAttribute("agencyList", agencyPerson1);//代理人
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "admin/business/edit";
    }

    /**
     * 添加或者编辑商户信息
     * @param request
     * @param business
     * @param model
     * @param file1
     * @param file2
     * @param file3
     * @param file4
     * @param file5
     * @return
     */
    @RequestMapping(value = "editBusiness", method = RequestMethod.POST)
    public String edit(HttpServletRequest request, Business business, RedirectAttributes model,
                       @RequestParam("file1") List<MultipartFile> file1,
                       @RequestParam("file2") List<MultipartFile> file2,
                       @RequestParam("file3") List<MultipartFile> file3,
                       @RequestParam("file4") List<MultipartFile> file4,
                       @RequestParam("file5") List<MultipartFile> file5) {
        String path1 = request.getRealPath("/") + "picCommon/license/";
        String path2 = request.getRealPath("/") + "picCommon/idCard/";
        String path3 = request.getRealPath("/") + "picCommon/bankCard/";
        String path4 = request.getRealPath("/") + "picCommon/site/";
        String path5 = request.getRealPath("/") + "picCommon/other/";
        //添加
        if (business.getId() == 0) {
            //获取商户云支付账号信息
            YunIdInfo yunIdInfo = YunPayUtil.getYunAccountInfo(business.getYunPayAccount());
            if (!StringUtils.isEmpty(yunIdInfo)) {
                //判断是否符合代理条件(商家或者金钻)
                if ("1".equals(yunIdInfo.getIsMerchant()) || "2".equals(yunIdInfo.getLev())) {
                        BaseExample baseExample = new BaseExample();
                        baseExample.createCriteria().andEqualTo("YUN_PAY_ACCOUNT", business.getYunPayAccount());
                        List<Business> list = businessService.selectByExample(baseExample);
                        //判断是否已存在该商家
                        if (list.size() == 0) {
                            business.setIsMerchant(yunIdInfo.getIsMerchant());
                            business.setLev(yunIdInfo.getLev());
                            //添加商户，同时往云支付设置商户标识
                            Integer state = businessService.insertBusiness(business);
                            if (state == 1) {
                                //添加照片
                                try {
                                    BusinessPicture bp = new BusinessPicture();
                                    if (file1.size() > 0) {
                                        for (MultipartFile multipartFile : file1) {
                                            if (multipartFile.getSize() > 0L) {
                                                String fileName1 = UploadUtil.upLoadFile(multipartFile, path1);
                                                bp.setPicturePath(fileName1);
                                                bp.setType(1);
                                                bp.setBid(business.getId());
                                                businessService.insertBusinessPicture(bp);
                                            }
                                        }
                                    }
                                    if (file2.size() > 0) {
                                        for (MultipartFile multipartFile : file2) {
                                            if (multipartFile.getSize() > 0L) {
                                                String fileName2 = UploadUtil.upLoadFile(multipartFile, path2);
                                                bp.setPicturePath(fileName2);
                                                bp.setType(2);
                                                bp.setBid(business.getId());
                                                businessService.insertBusinessPicture(bp);
                                            }
                                        }
                                    }
                                    if (file3.size() > 0) {
                                        for (MultipartFile multipartFile : file3) {
                                            if (multipartFile.getSize() > 0L) {
                                                String fileName3 = UploadUtil.upLoadFile(multipartFile, path3);
                                                bp.setPicturePath(fileName3);
                                                bp.setType(3);
                                                bp.setBid(business.getId());
                                                businessService.insertBusinessPicture(bp);
                                            }
                                        }
                                    }
                                    if (file4.size() > 0) {
                                        for (MultipartFile multipartFile : file4) {
                                            if (multipartFile.getSize() > 0L) {
                                                String fileName4 = UploadUtil.upLoadFile(multipartFile, path4);
                                                bp.setPicturePath(fileName4);
                                                bp.setType(4);
                                                bp.setBid(business.getId());
                                                businessService.insertBusinessPicture(bp);
                                            }
                                        }
                                    }
                                    if (file5.size() > 0) {
                                        for (MultipartFile multipartFile : file5) {
                                            if (multipartFile.getSize() > 0L) {
                                                String fileName5 = UploadUtil.upLoadFile(multipartFile, path5);
                                                bp.setPicturePath(fileName5);
                                                bp.setType(5);
                                                bp.setBid(business.getId());
                                                businessService.insertBusinessPicture(bp);
                                            }
                                        }
                                    }
                                    model.addFlashAttribute("msg", "新增成功");
                                } catch (Exception e) {
                                    model.addFlashAttribute("msg", "图片上传失败");
                                }
                            } else {
                                model.addFlashAttribute("msg", "设置pos机商户标识失败");
                            }
                        } else {
                            model.addFlashAttribute("msg", "云付通账号已存在,请更换账号");
                        }
                } else {
                    model.addFlashAttribute("msg", "云付通账号不符合成为商户条件");
                }
            } else {
                model.addFlashAttribute("msg", "不存在云付通账号");
            }
        } else {
            YunIdInfo yunIdInfo = YunPayUtil.getYunAccountInfo(business.getYunPayAccount());
            if (!StringUtils.isEmpty(yunIdInfo)) {
                String result = business.getAgentUnique();
                String a[] = result.split(",");
                business.setAgentUnique(a[0]);
                business.setAgencyName(a[1]);

                deviceListService.updateByMId(a[0], a[2]);
                businessService.updateBusiness(business);
                try {
                    BusinessPicture bp = new BusinessPicture();
                    if (file1.size() > 0) {
                        for (MultipartFile multipartFile : file1) {
                            if (multipartFile.getSize() > 0L) {
                                String fileName1 = UploadUtil.upLoadFile(multipartFile, path1);
                                bp.setPicturePath(fileName1);
                                bp.setType(1);
                                bp.setBid(business.getId());
                                businessService.insertBusinessPicture(bp);
                            }
                        }
                    }
                    if (file2.size() > 0) {
                        for (MultipartFile multipartFile : file2) {
                            if (multipartFile.getSize() > 0L) {
                                String fileName2 = UploadUtil.upLoadFile(multipartFile, path2);
                                bp.setPicturePath(fileName2);
                                bp.setType(2);
                                bp.setBid(business.getId());
                                businessService.insertBusinessPicture(bp);
                            }
                        }
                    }
                    if (file3.size() > 0) {
                        for (MultipartFile multipartFile : file3) {
                            if (multipartFile.getSize() > 0L) {
                                String fileName3 = UploadUtil.upLoadFile(multipartFile, path3);
                                bp.setPicturePath(fileName3);
                                bp.setType(3);
                                bp.setBid(business.getId());
                                businessService.insertBusinessPicture(bp);
                            }
                        }
                    }
                    if (file4.size() > 0) {
                        for (MultipartFile multipartFile : file4) {
                            if (multipartFile.getSize() > 0L) {
                                String fileName4 = UploadUtil.upLoadFile(multipartFile, path4);
                                bp.setPicturePath(fileName4);
                                bp.setType(4);
                                bp.setBid(business.getId());
                                businessService.insertBusinessPicture(bp);
                            }
                        }
                    }
                    if (file5.size() > 0) {
                        for (MultipartFile multipartFile : file5) {
                            if (multipartFile.getSize() > 0L) {
                                String fileName5 = UploadUtil.upLoadFile(multipartFile, path5);
                                bp.setPicturePath(fileName5);
                                bp.setType(5);
                                bp.setBid(business.getId());
                                businessService.insertBusinessPicture(bp);
                            }
                        }
                    }
                    model.addFlashAttribute("msg", "修改成功");
                } catch (Exception e) {
                    model.addFlashAttribute("msg", "图片上传失败");
                }
            } else {
                model.addFlashAttribute("msg", "不存在云付通账号");
            }
        }
        return "redirect:/rest/admin/business/index";
    }

    @RequestMapping(value = "editApprovalStatus", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<Business> editApprovalStatus(Business business) {
        if (business.getId() != 0) {
            try {
                business.setStatus(2);
                businessService.updateApproval(business.getId(), business.getStatus());
                return new JSONResult<Business>(null, "操作成功", true);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<Business>(null, "操作失败", false);
            }
        } else {
            return new JSONResult<Business>(null, "操作失败", false);
        }
    }

    @RequestMapping(value = "showBrand", method = RequestMethod.GET)
    public String showBrand(HttpServletRequest request) {
        return "admin/business/showBrand";
    }

    /**
     * 导入商户Excel
     * @param business
     * @param model
     * @param file
     * @return
     */
    @RequestMapping(value = "upExcelRead", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<List<java.util.HashMap>> upExcelRead(Business business, RedirectAttributes model, @RequestParam(value = "file1", required = false) MultipartFile file) {
        try {
            String[] fieldArray = {"MERCHANT_NAME", "MERCHANT_ADDRESS", "MERCHANT_LEGAL_PERSON", "MERCHANT_IDENTITY_CARD", "BUSINESS_LICENSE", "MERCHANT_PERSON_NAME", "MERCHANT_PERSON_PHONE", "EMAIL", "ACCOUNT_NUMBER", "ACCOUNT_NAME", "OPEN_BANK", "YUN_PAY_ACCOUNT", "FOLLOW_UP_NAME", "AGENCY_NAME"};
            java.util.List<java.util.HashMap> list = ExportExcelUtil.ReadUpLoadExcel(fieldArray, file.getInputStream());
            if (list.size() > 0) {
                if (list.get(0).size() != fieldArray.length) {
                    return new JSONResult<List<java.util.HashMap>>(null, "导入模板错误,请检查或重新下载!", false);
                }
            }
            String mess = "";
            String temp1 = "";
            List<java.util.HashMap> messList = new ArrayList<>();

            //判断excel是否重复
            for (int i = 0; i < list.size() - 1; i++) {
                temp1 = list.get(i).get("YUN_PAY_ACCOUNT").toString();
                for (int j = i + 1; j < list.size(); j++) {
                    if (temp1.equals(list.get(j).get("YUN_PAY_ACCOUNT").toString())) {
                        java.util.HashMap<String, String> messMap = new java.util.HashMap();
                        messMap.put("mess", "第[ " + (i + 2) + " ]行跟第[ " + (j + 2) + " ]行云付通账号重复,云付通账号: " + temp1 + "");
                        messList.add(messMap);
                        break;
                    }
                }
            }

            //验证是否已经添加过
            List<Business> bList = businessService.getBusinessInfo();
            for (int i = 0; i < list.size(); i++) {
                String yunPayAccount = list.get(i).get("YUN_PAY_ACCOUNT").toString();
                for (int j = 0; j < bList.size(); j++) {
                    if (yunPayAccount.equals(bList.get(j).getYunPayAccount())) {
                        java.util.HashMap<String, String> messMap = new java.util.HashMap();
                        messMap.put("mess", "第[ " + (i + 2) + " ]行 , 云付通账号："+ yunPayAccount + "已添加过了！");
                        messList.add(messMap);
                        list.remove(i);
                        break;
                    }
                }
            }

            //验证代理人是否存在
            List<AgencyPerson> agencyPerson = agencyPersonService.getAgencyList();
            List<String> list1 = new ArrayList<String>();
            List<String> list2 = new ArrayList<String>();
            for (int i = 0; i < list.size(); i++) {
                list1.add(String.valueOf(list.get(i).get("AGENCY_NAME")));
            }
            for (int j = 0; j < agencyPerson.size(); j++) {
                list2.add(String.valueOf(agencyPerson.get(j).getRealName()));
            }
            for (String str1 : list1) {
                if (!list2.contains(str1)) {
                    java.util.HashMap<String, String> messMap = new java.util.HashMap();
                    messMap.put("mess", "代理人姓名 [" + str1 + "] 不存在,请先添加");
                    messList.add(messMap);
                    break;
                }
            }

            List<Business> lb = new ArrayList<Business>();
            String dateStr = FunUtil.fromDatStr(new Date());
            Integer no = businessService.returnMNo();
            if (messList.size() == 0) {
                for (int n = 0; n < list.size(); n++) {
                    int temp = no++;
                    Business b = new Business();
                    HashMap hm = list.get(n);
                    b.setMerchantName(hm.get("MERCHANT_NAME").toString());
                    b.setMerchantAddress(hm.get("MERCHANT_ADDRESS").toString());
                    b.setMerchantLegalPerson(hm.get("MERCHANT_LEGAL_PERSON").toString());
                    b.setMerchantIdentityCard(hm.get("MERCHANT_IDENTITY_CARD").toString());
                    b.setBusinessLicense(hm.get("BUSINESS_LICENSE").toString());
                    b.setMerchantPersonName(hm.get("MERCHANT_PERSON_NAME").toString());
                    b.setMerchantPersonPhone(hm.get("MERCHANT_PERSON_PHONE").toString());
                    b.setEmail(hm.get("EMAIL").toString());
                    b.setAccountNumber(hm.get("ACCOUNT_NUMBER").toString());
                    b.setAccountName(hm.get("ACCOUNT_NAME").toString());
                    b.setOpenBank(hm.get("OPEN_BANK").toString());
                    b.setYunPayAccount(hm.get("YUN_PAY_ACCOUNT").toString());
                    b.setFollowUpName(hm.get("FOLLOW_UP_NAME").toString());
                    b.setMerchantNo(dateStr + temp);
                    for (int i = 0; i < agencyPerson.size(); i++) {
                        if (agencyPerson.get(i).getRealName().equals(hm.get("AGENCY_NAME").toString())) {
                            b.setAgentUnique(agencyPerson.get(i).getAgentUnique());
                        }
                    }
                    b.setAgencyName(hm.get("AGENCY_NAME").toString());
                    b.setStatus(1);
                    b.setCreateTime(FunUtil.fromDateH(new Date()));
                    lb.add(n, b);
                }
                //设置商户标识
                for(int i = 0;i < lb.size();i++){
                    boolean resultBoolean = YunPayUtil.setMerchantIdentify(lb.get(i).getYunPayAccount(),1);
                    if(resultBoolean){
                    }else{
                        return new JSONResult<List<java.util.HashMap>>(null, "设置账号["+lb.get(i).getYunPayAccount()+"]pos商户标识失败", false);
                    }
                }
                businessService.inserBatchBusiness(lb);
                return new JSONResult<List<java.util.HashMap>>(null, "导入成功", true);
            } else {
                return new JSONResult<List<java.util.HashMap>>(messList, messList.get(0).get("mess").toString(), false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addFlashAttribute("msg", "未知异常！");
        }
        return new JSONResult<List<java.util.HashMap>>(null, "未知异常", false);
    }

    @RequestMapping(value = "/approveIndexLead", method = RequestMethod.GET)
    public String approveIndexLead(Model model, Business business) {
        model.addAttribute("info", business);
        return "admin/business/approveLead/index";
    }

    @RequestMapping(value = "getApproveListLead", method = RequestMethod.GET)
    @ResponseBody
    public String getApproveListLead(DataTablesPage<Business> page, Model model, Business business) {
        try {
            BaseExample baseExample = new BaseExample();
            if (!StringUtils.isEmpty(business.getQueryBody())) {
                baseExample.or().andLike("DEVICE_CODE", business.getQueryBody());
                baseExample.or().andLike("MERCHANT_YUN_PAY_ACCOUNT", business.getQueryBody());
            }
            baseExample.createCriteria().andEqualTo("STATUS", "2");
            businessService.selectAllByExampleAndPage(page, baseExample);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    @RequestMapping(value = "delPic", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<BusinessPicture> delPic(BusinessPicture businessPicture) {
        if (businessPicture.getId() != 0) {
            try {
                businessService.deletePic(businessPicture.getId());
                //DelAllFile.delFolder(FunUtil.getClasspath()+ Const.FILEPATHIMG); //删除图片
                return new JSONResult<BusinessPicture>(businessPicture, "删除成功", true);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<BusinessPicture>(null, "删除失败", false);
            }
        } else {
            return new JSONResult<BusinessPicture>(null, "删除失败", false);
        }
    }

    @RequestMapping(value = "getBusinessNameByAgentUnique", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<java.util.List<Business>> getBusinessNameByAgentUnique(Business business) {
        try {
            if (org.springframework.util.StringUtils.isEmpty(business.getAgentUnique())) {
                return new JSONResult<java.util.List<Business>>(null, "参数错误", false);
            } else {
                List<Business> list = businessService.getBusinessNameByAgentUnique(business.getAgentUnique());
                return new JSONResult<java.util.List<Business>>(list, "获取成功", true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult<java.util.List<Business>>(null, "获取商家信息失败", false);
        }
    }

    @RequestMapping(value = "getBusinessNameById", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<Business> getBusinessNameById(Business business) {
        try {
            if (org.springframework.util.StringUtils.isEmpty(business.getMerchantName())) {
                return new JSONResult<Business>(null, "参数错误", false);
            } else {
                String[] aResult = business.getMerchantName().split(",");
                Business business1 = businessService.getBusinessNameById(aResult[0]);
                return new JSONResult<Business>(business1, "获取成功", true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult<Business>(null, "获取商家信息失败", false);
        }
    }

    @RequestMapping(value = "/updateBusinessYunIdIndex", method = RequestMethod.GET)
    public String updateBussinessYunIdIndex(Model model) {
        return "admin/business/updateYunId";
    }

    @RequestMapping(value = "getBusinessInfoByYunId", method = RequestMethod.GET)
    @ResponseBody
    public String getBusinessInfoByYunId(DataTablesPage<Business> page, Model model, Business business) {
        try {
            BaseExample baseExample = new BaseExample();
            //订单类型  POS
            BaseExample.Criteria criteria = baseExample.createCriteria();
            if (!com.alibaba.druid.util.StringUtils.isEmpty(business.getYunPayAccount())) {
                criteria.andEqualTo("YUN_PAY_ACCOUNT", business.getYunPayAccount());
                businessService.selectByExampleAndPage(page, baseExample);
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 修改商户信息
     * @param business
     * @return
     */
    @RequestMapping(value = "editBusinessInfo", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<Business> editBusinessInfo(Business business) {
        if (business.getId() != 0) {
            try {
                Business business1 = businessService.getBusinessInfoByYId(business.getYunPayAccount());
                if(StringUtils.isEmpty(business1)){
                    Business business2 = businessService.getBusinessNameById(String.valueOf(business.getId()));
                        if(!StringUtils.isEmpty(business2)){
                            /**
                             * 先取消原来商户标识
                             */
                            boolean resultBoolean = YunPayUtil.setMerchantIdentify(business2.getYunPayAccount(),2);
                            if(!resultBoolean){
                                return new JSONResult<Business>(null, "取消商户标识失败", false);
                            }else{
                                /**
                                 * 添加新的商户标识
                                 */
                                boolean resultBoolean1 = YunPayUtil.setMerchantIdentify(business.getYunPayAccount(),1);
                                if(resultBoolean1){
                                    //修改商户云付通账号
                                    businessService.updateBusiness(business);
                                    //修改设备的商户号
                                    deviceListService.updateYunId(business.getYunPayAccount(),business2.getYunPayAccount());
                                    //修改订单的商户号
                                    //orderService.updateMId(business.getYunPayAccount(),business2.getYunPayAccount());
                                    return new JSONResult<Business>(null, "操作成功", true);
                                }else{
                                    return new JSONResult<Business>(null, "设置商户标识失败", true);
                                }
                            }
                        }else{
                            return new JSONResult<Business>(null, "不存该商户信息", false);
                        }
                }else{
                    return new JSONResult<Business>(null, "要修改的商户云付通账号已存在", false);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<Business>(null, "操作失败", false);
            }
        } else {
            return new JSONResult<Business>(null, "操作失败", false);
        }
    }
}
