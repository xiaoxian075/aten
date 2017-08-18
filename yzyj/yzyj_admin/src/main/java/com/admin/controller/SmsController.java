package com.admin.controller;

import com.admin.model.*;
import com.admin.service.DeviceListService;
import com.admin.service.SmsService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.core.entity.JSONResult;
import com.core.mybatis.DataTablesPage;
import com.core.util.ExportExcelUtil;
import com.inter.ReqMsg;
import com.util.JsonUtil;
import com.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;


@Controller
@RequestMapping(value = "/admin/sms")
public class SmsController {

    @Resource
    private SmsService smsService;





    @RequestMapping(value = "/model",method = RequestMethod.GET)
    public String model() {
        return "admin/sms/model";
    }

    @RequestMapping(value = "addModelPage", method = RequestMethod.GET)
    public String addModelPage() {
        return "admin/sms/AddModel";
    }

    @RequestMapping(value = "addModel", method = RequestMethod.POST)
    @ResponseBody
    public String addModel(RedirectAttributes model, SmsModel smsModel) {
        String title = smsModel.getTitle();
        String sign = smsModel.getSign();
        String content = smsModel.getContent();
        if (StringUtils.isBlank(title) || StringUtils.isBlank(sign) || StringUtils.isBlank(content) || title.length()>20 || sign.length()>15 || content.length()>499) {
            return JsonUtil.toString(new ReqMsg<Object>(1001,"参数错误",null));
        }
        smsModel.setSign("【"+sign+"】");

        try {
            if (!smsService.checkModelName(title)) {
                return JsonUtil.toString(new ReqMsg<Object>(1002,"主题已存在",null));
            }
            if (!smsService.insertModel(smsModel)) {
                return JsonUtil.toString(new ReqMsg<Object>(1003,"插入失败",null));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return JsonUtil.toString(new ReqMsg<Object>(0,"添加成功",null));
    }

    @RequestMapping(value = "delModel", method = RequestMethod.POST)
    @ResponseBody
    public String delModel(Long id) {
        if (id==null || id <= 0) {
            return JsonUtil.toString(new ReqMsg<Object>(1,"参数错误",null));
        }

        boolean result = smsService.delModel(id);
        if (!result)
            return JsonUtil.toString(new ReqMsg<Object>(2,"执行错误",null));

        return JsonUtil.toString(new ReqMsg<Object>(0,"succ",null));
    }


    @RequestMapping(value = "editModelPage", method = RequestMethod.GET)
    public String editModelPage(Model model, Long id) {
        if(id!=null && id>0){
            SmsModel smsModel = smsService.selectOne(id);
            if (smsModel!=null) {
                String sign = smsModel.getSign();
                if (StringUtils.isNotBlank(sign)) {
                    sign = sign.replace("【","").replace("】","");
                    smsModel.setSign(sign);
                    model.addAttribute("smsModel", smsModel);
                }
            }
        }
        return "admin/sms/EditModel";
    }

    @RequestMapping(value = "editModel", method = RequestMethod.POST)
    @ResponseBody
    public String editModel(SmsModel smsModel) {
        String title = smsModel.getTitle();
        String sign = smsModel.getSign();
        String content = smsModel.getContent();
        if (StringUtils.isBlank(title)|| StringUtils.isBlank(sign) || StringUtils.isBlank(content) || title.length()>20 || sign.length()>15 || content.length()>499) {
            return JsonUtil.toString(new ReqMsg<Object>(1001,"参数错误",null));
        }
        smsModel.setSign("【"+sign+"】");
        ReqMsg<Object> reqmsg = null;
        try {
            reqmsg = smsService.editModel(smsModel);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (reqmsg==null) {
            return JsonUtil.toString(new ReqMsg<Object>(1005,"执行出错",null));
        }

        return JsonUtil.toString(reqmsg);
    }


    @RequestMapping(value = "synModel", method = RequestMethod.POST)
    @ResponseBody
    public String synModel(Long id) {
        if (id==null || id <= 0) {
            return JsonUtil.toString(new ReqMsg<Object>(1,"参数错误",null));
        }

        boolean result = smsService.synModel(id);
        if (!result)
            return JsonUtil.toString(new ReqMsg<Object>(2,"执行错误",null));

        return JsonUtil.toString(new ReqMsg<Object>(0,"succ",null));
    }

    @RequestMapping(value = "getList", method = RequestMethod.GET)
    @ResponseBody
    public String getList(DataTablesPage<SmsModel> page, Model model, SmsModel smsModel) {
        String content = smsModel.getContent();
        int state = smsModel.getState();

        try {
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
            //搜索条件
            if (StringUtils.isNotBlank(content)) {
                criteria.andLike("CONTENT", content);
            }
            if (state > 0 && state <= 3) {
                criteria.andEqualTo("STATE", String.valueOf(state));
            }

            baseExample.setOrderByClause("ID desc ");
            smsService.selectList(page, baseExample);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }






    @RequestMapping(value = "sms",method = RequestMethod.GET)
    public String sms() {
        return "admin/sms/sms";
    }

    /**
     * 获取有效签名列表
     * @return
     */
    @RequestMapping(value = "signList", method = RequestMethod.POST)
    @ResponseBody
    public String signList() {
        List<SmsModel> list = smsService.signList();
        if (list==null)
            return JsonUtil.toString(new ReqMsg<List<SmsModel>>(2,"执行错误",null));

        return JsonUtil.toString(new ReqMsg<List<SmsModel>>(0,"succ",list));
    }

    @RequestMapping(value = "getSmsModel", method = RequestMethod.POST)
    @ResponseBody
    public String getSmsModel(Long id) {
        if (id==null || id<=0)
            return JsonUtil.toString(new ReqMsg<SmsModel>(1,"参数错误",null));

        SmsModel smsModel = smsService.selectOne(id);
        if (smsModel==null)
            return JsonUtil.toString(new ReqMsg<SmsModel>(2,"执行错误",null));

        return JsonUtil.toString(new ReqMsg<SmsModel>(0,"succ",smsModel));
    }

    /**
     * 短信群发
     * @param content
     * @param mobile
     * @return
     */
    @RequestMapping(value = "batchSendSms", method = RequestMethod.POST)
    @ResponseBody
    public String batchSendSms(String content, String mobile, Integer sendType, String sendTime) {
        if (    StringUtils.isBlank(content) ||
                StringUtils.isBlank(mobile) ||
                sendType==null || sendType<1 || sendType>2
                ) {
            return JsonUtil.toString(new ReqMsg<Object>(1001,"参数错误",null));
        }
        if (sendType==2) {
            if (StringUtils.isBlank(sendTime) || sendTime.length()!=19) {
                return JsonUtil.toString(new ReqMsg<Object>(1001,"参数错误",null));
            }
        }

        List<String> arrMobile = null;
        try {
            String[] mobiles = mobile.split(",");
            arrMobile = Arrays.asList(mobiles);
        } catch (Exception e) {
            return JsonUtil.toString(new ReqMsg<Object>(1001,"参数错误",null));
        }
        if (arrMobile==null || arrMobile.size()==0) {
            return JsonUtil.toString(new ReqMsg<Object>(1001,"参数错误",null));
        }

        boolean result = false;
        if (sendType==1) {
            result = smsService.batchSendSms(arrMobile,content);
        } else {
            Timestamp sendT = StringUtil.strToDate(sendTime);
            Timestamp curTime = new Timestamp(new Date().getTime());
            if (curTime.getTime()>sendT.getTime()) {
                return JsonUtil.toString(new ReqMsg<Object>(1003,"定时时间不能小于当前时间",null));
            }
            result = smsService.saveBatchSendSms(arrMobile,content,sendT,curTime);
        }

        if (!result)
            return JsonUtil.toString(new ReqMsg<Object>(1002,"异常",null));

        return JsonUtil.toString(new ReqMsg<Object>(0,"succ",null));
    }


    /**
     * 导入电话号码文件
     * @param model
     * @param file
     * @return
     */
    @RequestMapping(value = "uploadMobileFile", method = RequestMethod.POST)
    @ResponseBody
    public String uploadMobileFile(RedirectAttributes model, @RequestParam(value = "file1", required = false) MultipartFile file) {
        if (file==null)
            return JsonUtil.toString(new ReqMsg<List<String>>(1001,"参数错误",null));
        String fileName = file.getOriginalFilename();
        if (StringUtils.isBlank(fileName) || fileName.length()<4)
            return JsonUtil.toString(new ReqMsg<List<String>>(1001,"参数错误",null));

        //String[] arrMobile = null;
        List<String> tempMobile = null;
        try {
            if (fileName.endsWith(".txt")) {
                String strMobile = StringUtil.convertStreamToString(file);
                String[] arrMobile = strMobile.split("\n");
                tempMobile = Arrays.asList(arrMobile);
            } else if (fileName.endsWith(".xls")) {
                String[] fieldArray = {"MOBILE"};
                java.util.List<java.util.HashMap> list = ExportExcelUtil.ReadUpLoadExcel(fieldArray, file.getInputStream());
                if (list.size() > 0) {
                    if (list.get(0).size() != fieldArray.length) {
                        JsonUtil.toString(new ReqMsg<List<String>>(1006,"导入模板错误,请检查或重新下载!",null));
                    }
                }

                tempMobile = new ArrayList<String>();
                String temp1 = null;
                for (int i = 0; i < list.size(); i++) {
                    temp1 = list.get(i).get("MOBILE").toString();
                    tempMobile.add(temp1);
                }
            }
        } catch (Exception e) {
            return JsonUtil.toString(new ReqMsg<List<String>>(1004,"导入出错",null));
        }
        if (tempMobile==null) {
            return JsonUtil.toString(new ReqMsg<List<String>>(1002,"导入文件格式有误",null));
        } else if (tempMobile.size()==0) {
            return JsonUtil.toString(new ReqMsg<List<String>>(1003,"导入有误，内容不能为空",null));
        }

        List<String> listMobile = new ArrayList<String>();
        try {
            for (String mobile : tempMobile) {
                if (mobile==null)
                    continue;
                mobile = mobile.trim();
                if (mobile.length()==0)
                    continue;

                if (!StringUtil.isPhone(mobile)) {
                    return JsonUtil.toString(new ReqMsg<List<String>>(1005,"导入有误，内容不符电话号码格式",null));
                }
                if (mobile.length()<6 || mobile.length()>16) {
                    return JsonUtil.toString(new ReqMsg<List<String>>(1005,"导入有误，每行内容6~16个字符",null));
                }
                listMobile.add(mobile);
            }
        } catch (Exception e) {
            return JsonUtil.toString(new ReqMsg<List<String>>(1004,"导入出错",null));
        }
        return JsonUtil.toString(new ReqMsg<List<String>>(0,"succ",listMobile));
    }


    /************* 群组接口 *************/

    @RequestMapping(value = "group",method = RequestMethod.GET)
    public String group() {
        return "admin/sms/group";
    }

    @RequestMapping(value = "addGroupPage",method = RequestMethod.GET)
    public String addGroupPage() {
        return "admin/sms/AddGroup";
    }


    @RequestMapping(value = "getGroupList", method = RequestMethod.GET)
    @ResponseBody
    public String getGroupList(DataTablesPage<SmsGroup> page, String accName) {
        try {
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
            //搜索条件
            if (StringUtils.isNotBlank(accName)) {
                criteria.andLike("NAME", accName);
            }
            baseExample.setOrderByClause("ID desc ");
            smsService.selectGroupList(page, baseExample);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }


    @RequestMapping(value = "getAllGroup", method = RequestMethod.POST)
    @ResponseBody
    public String getAllGroup(Integer state) {
        if (state==null || state<0 || state>1) {
            return JsonUtil.toString(new ReqMsg<List<SmsGroup>>(1,"参数错误",null));
        }
        List<SmsGroup> listGroup = smsService.selectAllGroup(state);
        if (listGroup==null)
            return JsonUtil.toString(new ReqMsg<List<SmsGroup>>(2,"执行错误",null));

        return JsonUtil.toString(new ReqMsg<List<SmsGroup>>(0,"succ",listGroup));
    }

//    @RequestMapping(value = "addGroup", method = RequestMethod.POST)
//    public String addGroup(RedirectAttributes model, String accName) {
//
//        if (StringUtils.isBlank(accName) || accName.length()>32) {
//            model.addFlashAttribute("msg", "参数错误");
//            return "redirect:/rest/admin/sms/group";
//        }
//
//        try {
//            smsService.insertGroup(accName);
//        } catch (Exception e) {
//            model.addFlashAttribute("msg", "未知异常！");
//        }
//
//        model.addFlashAttribute("msg", "插入成功");
//        return "redirect:/rest/admin/sms/group";
//    }

    @RequestMapping(value = "addGroup", method = RequestMethod.POST)
    @ResponseBody
    public String addGroup(RedirectAttributes model, String accName) {

        if (StringUtils.isBlank(accName) || accName.length()>32) {
            return JsonUtil.toString(new ReqMsg<Object>(1,"参数错误",null));
        }

        try {
            if (!smsService.checkGroupName(accName)){
                return JsonUtil.toString(new ReqMsg<Object>(2,"群名已存在",null));
            }
            smsService.insertGroup(accName);
        } catch (Exception e) {
            model.addFlashAttribute("msg", "未知异常！");
        }

        return JsonUtil.toString(new ReqMsg<Object>(0,"succ",null));
    }


    @RequestMapping(value = "checkGroupName", method = RequestMethod.POST)
    @ResponseBody
    public String checkGroupName(String groupName) {
        if (StringUtils.isBlank(groupName) || groupName.length()>32) {
            return JsonUtil.toString(new ReqMsg<Object>(1,"参数错误",null));
        }

        boolean result = smsService.checkGroupName(groupName);
        if (result)
            return JsonUtil.toString(new ReqMsg<Object>(2,"群名已存在",null));

        return JsonUtil.toString(new ReqMsg<Object>(0,"succ",null));
    }

    @RequestMapping(value = "delGroup", method = RequestMethod.POST)
    @ResponseBody
    public String delGroup(Long id) {
        if (id==null || id <= 0) {
            return JsonUtil.toString(new ReqMsg<Object>(1,"参数错误",null));
        }

        boolean result = smsService.delGroup(id);
        if (!result)
            return JsonUtil.toString(new ReqMsg<Object>(2,"执行错误",null));

        return JsonUtil.toString(new ReqMsg<Object>(0,"succ",null));
    }

    @RequestMapping(value = "enableGroup", method = RequestMethod.POST)
    @ResponseBody
    public String enableGroup(Long id) {
        if (id==null || id <= 0) {
            return JsonUtil.toString(new ReqMsg<Object>(1,"参数错误",null));
        }

        boolean result = smsService.updateStateGroup(id,1);
        if (!result)
            return JsonUtil.toString(new ReqMsg<Object>(2,"执行错误",null));

        return JsonUtil.toString(new ReqMsg<Object>(0,"succ",null));
    }

    @RequestMapping(value = "limitGroup", method = RequestMethod.POST)
    @ResponseBody
    public String limitGroup(Long id) {
        if (id==null || id <= 0) {
            return JsonUtil.toString(new ReqMsg<Object>(1,"参数错误",null));
        }

        boolean result = smsService.updateStateGroup(id,0);
        if (!result)
            return JsonUtil.toString(new ReqMsg<Object>(2,"执行错误",null));

        return JsonUtil.toString(new ReqMsg<Object>(0,"succ",null));
    }

    @RequestMapping(value = "account",method = RequestMethod.GET)
    public String account(Model model,String groupId, String groupName) {
        model.addAttribute("groupId",groupId);
        model.addAttribute("groupName",groupName);
        return "admin/sms/account";
    }

    @RequestMapping(value = "addAccountPage",method = RequestMethod.GET)
    public String addAccountPage(Model model,Long groupId,String groupName) {
        if (groupId!=null && groupId>0) {
            SmsGroup smsGroup = smsService.getGroup(groupId);
            if (smsGroup!=null) {
                model.addAttribute("groupId", smsGroup.getId());
                model.addAttribute("groupName", smsGroup.getName());
            }
        }
        return "admin/sms/AddAccount";
    }

    @RequestMapping(value = "editAccountPage",method = RequestMethod.GET)
    public String editAccountPage(Model model,Long id) {
        if (id==null || id<=0) {
            return "admin/sms/account";
        }
        SmsAccount smsAccount = smsService.getAccount(id);
        model.addAttribute("smsAccount",smsAccount);
        SmsGroup smsGroup = smsService.getGroup(smsAccount.getGroupId());
        if (smsGroup!=null) {
            model.addAttribute("groupName",smsGroup.getName());
        }
        return "admin/sms/EditAccount";
    }

    @RequestMapping(value = "addAccount", method = RequestMethod.POST)
    public String addAccount(RedirectAttributes model, String accName, Integer addSex, String phone, Long groupId, String groupName) {

        if (StringUtils.isBlank(accName) || accName.length()>32 || addSex==null || addSex<1 || addSex>2 || StringUtils.isBlank(phone) || accName.length()>15 || groupId==null || groupId<=0) {
            model.addAttribute("groupId",groupId);
            model.addAttribute("groupName",groupName);
            return "redirect:/rest/admin/sms/account";
        }

        try {
            smsService.insertAccount(accName, addSex, phone, groupId);
        } catch (Exception e) {
            //model.addFlashAttribute("msg", "未知异常！");
        }

        model.addAttribute("groupId",groupId);
        model.addAttribute("groupName",groupName);
        return "redirect:/rest/admin/sms/account";
    }

    @RequestMapping(value = "editAccount", method = RequestMethod.POST)
    public String editAccount(RedirectAttributes model, Long id, String accName, Integer addSex, String phone, Long groupId, String groupName) {

        if (id==null || id<=0 || StringUtils.isBlank(accName) || accName.length()>32 || addSex==null || addSex<1 || addSex>2 || StringUtils.isBlank(phone) || accName.length()>15 || groupId==null || groupId<=0) {
            model.addAttribute("groupId",groupId);
            model.addAttribute("groupName",groupName);
            return "redirect:/rest/admin/sms/account";
        }

        try {
            smsService.editAccount(id, accName, addSex, phone, groupId);
        } catch (Exception e) {
            //model.addFlashAttribute("msg", "未知异常！");
        }

        model.addAttribute("groupId",groupId);
        model.addAttribute("groupName",groupName);
        return "redirect:/rest/admin/sms/account";
    }



    @RequestMapping(value = "delAccount", method = RequestMethod.POST)
    @ResponseBody
    public String delAccount(Long id) {
        if (id==null || id <= 0) {
            return JsonUtil.toString(new ReqMsg<Object>(1,"参数错误",null));
        }

        boolean result = smsService.delAccount(id);
        if (!result)
            return JsonUtil.toString(new ReqMsg<Object>(2,"执行错误",null));

        return JsonUtil.toString(new ReqMsg<Object>(0,"succ",null));
    }

    @RequestMapping(value = "getAccountList", method = RequestMethod.GET)
    @ResponseBody
    public String getAccountList(DataTablesPage<SmsAccount> page, Long groupId, String accName) {
        try {
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
            //搜索条件
            if (groupId!=null && groupId>0) {
                criteria.andEqualTo("GROUP_ID", String.valueOf(groupId));
            }
            if (StringUtils.isNotBlank(accName)) {
                criteria.andLike("NAME", accName);
            }
            baseExample.setOrderByClause("ID desc ");
            smsService.selectAccountList(page, baseExample);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }


    @RequestMapping(value = "getAllAccount", method = RequestMethod.POST)
    @ResponseBody
    public String getAllAccount(Long id) {
        if (id==null || id<=0) {
            return JsonUtil.toString(new ReqMsg<List<SmsAccount>>(1,"参数错误",null));
        }
        List<SmsAccount> listAccount = smsService.selectAccountByGroupId(id);
        if (listAccount==null)
            return JsonUtil.toString(new ReqMsg<List<SmsAccount>>(2,"执行错误",null));

        return JsonUtil.toString(new ReqMsg<List<SmsAccount>>(0,"succ",listAccount));
    }

    @RequestMapping(value = "record",method = RequestMethod.GET)
    public String record() {
        return "admin/sms/record";
    }

    @RequestMapping(value = "getSmsRecordList", method = RequestMethod.GET)
    @ResponseBody
    public String getSmsRecordList(DataTablesPage<SmsRecord> page, String paraMobile, Integer paraState, String paraStartTime, String paraEndTime) {
        try {
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
            //搜索条件
            if (StringUtils.isNotBlank(paraMobile)) {
                criteria.andLike("MOBILE", paraMobile);
            }
            if (paraState!=null && paraState>=1 && paraState<=2) {

                if (paraState==1)
                    criteria.andEqualTo("STATE", "0");
                else
                    criteria.andNotEqualTo("STATE", "0");
            }
            if (StringUtils.isNotBlank(paraStartTime) && paraStartTime.length()==19) {
                criteria.andGreaterThanOrEqualTo("TO_CHAR(CREATE_TIME,'yyyy-mm-dd hh24:mi:ss')",paraStartTime);
            }
            if (StringUtils.isNotBlank(paraEndTime) && paraEndTime.length()==19) {
                criteria.andLessThanOrEqualTo("TO_CHAR(CREATE_TIME,'yyyy-mm-dd hh24:mi:ss')",paraEndTime);
            }
            baseExample.setOrderByClause("ID desc");
            smsService.selectSmsRecordList(page, baseExample);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }
}

//    String content = smsModel.getContent();
//    int status = smsModel.getStatus();
//
//try {
//        BaseExample baseExample = new BaseExample();
//        BaseExample.Criteria criteria = baseExample.createCriteria();
//        //搜索条件
//        if (StringUtils.isNotBlank(content)) {
//        criteria.andLike("CONTENT", content);
//        }
//        if (status > 0 && status <= 3) {
//        criteria.andEqualTo("STATUS", String.valueOf(status));
//        }
//
//        baseExample.setOrderByClause("CREATE_TIME desc ");
//        smsService.selectList(page, baseExample);
//        } catch (Exception e) {
//        e.printStackTrace();
//        }
//        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);

//    @RequestMapping(value = "getList", method = RequestMethod.GET)
//    @ResponseBody
//    public Map<String,Object> getList(Model model, SmsModel smsModel) {
//        String content = smsModel.getContent();
//        int status = smsModel.getStatus();
////        if (StringUtils.isBlank(content) || status<0 || status>3) {
////            return null;
////        }
//        Map<String,Object> result = new HashMap<String,Object>();
//        result.put("total",0);
//        result.put("rows",new ArrayList<SmsModel>());
//        return result;
//    }