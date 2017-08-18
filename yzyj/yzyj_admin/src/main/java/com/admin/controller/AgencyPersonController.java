package com.admin.controller;

import com.admin.model.AgencyPerson;
import com.admin.model.BaseExample;
import com.admin.model.DeviceList;
import com.admin.model.YunIdInfo;
import com.admin.service.AgencyPersonService;
import com.admin.service.BusinessService;
import com.admin.service.UserService;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.core.entity.JSONResult;
import com.core.mybatis.DataTablesPage;
import com.core.util.YunPayUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * Created by Administrator on 2016/11/30.
 * 代理人控制器
 */
@Controller
@RequestMapping(value = "/admin/agencyPerson")
public class AgencyPersonController {
    @Resource
    private AgencyPersonService agencyPersonService;
    @Resource
    private UserService userService;
    @Resource
    private BusinessService businessService;

    /**
     * 跳转代理人页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model) {
        return "admin/agencyPerson/index";
    }

    /**
     * 获取代理人列表(可根据条件查询) REALNAME：真实姓名
     * YUNPAYLOGINNAME：云支付账号
     * @param page
     * @param model
     * @param agencyPerson
     * @return
     */
    @RequestMapping(value = "getList",method = RequestMethod.GET)
    @ResponseBody
    public String getList(DataTablesPage<AgencyPerson> page, Model model, AgencyPerson agencyPerson) {
        try{
            BaseExample baseExample = new BaseExample();
            if(!StringUtils.isEmpty(agencyPerson.getQueryBody())){
                baseExample.or().andLike("REALNAME",agencyPerson.getQueryBody());
                baseExample.or().andLike("YUNPAYLOGINNAME",agencyPerson.getQueryBody());
            }
            agencyPersonService.selectByExampleAndPage(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }

        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 跳转添加页面
     * @param model
     * @param agencyPerson
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model, AgencyPerson agencyPerson) {
        model.addAttribute("info",new AgencyPerson());
        return "admin/agencyPerson/add";
    }

    /**
     * 跳转编辑或者修改页面
     * @param model
     * @param agencyPerson
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model, AgencyPerson agencyPerson) {
        //添加页面
        if(StringUtils.isEmpty(agencyPerson.getAgentUnique())){
            model.addAttribute("info",new AgencyPerson());
        }else{
            //编辑页面
            AgencyPerson agencyPerson1=agencyPersonService.selectByAgencyUnique(agencyPerson.getAgentUnique());
            model.addAttribute("info",agencyPerson1);
        }
        return "admin/agencyPerson/edit";
    }

    /**
     * 添加或者编辑功能
     * @param agencyPerson
     * @param model
     * @return
     */
    @RequestMapping(value = "edit",method = RequestMethod.POST)
    public String edit(AgencyPerson agencyPerson, RedirectAttributes model ) {
        try {
            String msg="";
            YunIdInfo yunIdInfo = YunPayUtil.getYunAccountInfo(agencyPerson.getYunPayLoginName());
            if(yunIdInfo == null){
                msg = "云付通账号不存在，请确认是否填写正确!";
            }else{
                BaseExample baseExample = new BaseExample();
                baseExample.or().andEqualTo("YUNPAYLOGINNAME",agencyPerson.getYunPayLoginName());
                List<AgencyPerson> list = agencyPersonService.selectByExample(baseExample);
                if(list.size() > 0){
                    msg="代理人云付通账号已存在!";
                }else{
                    if (StringUtils.isEmpty(agencyPerson.getAgentUnique())){
                        //检查用户云支付是否重复
                        if(list.size() == 0){
                            agencyPersonService.insertAgentInfo(agencyPerson);
                            msg="新增成功";
                        }
                    }else{
                        //修改代理人信息
                        agencyPersonService.updateAgencyPerson(agencyPerson);
                        //修改用户账号
                        userService.updateUserName(agencyPerson);
                        msg="修改成功";
                    }
                }
            }
            model.addFlashAttribute("msg", msg);
        } catch (Exception e) {
            e.printStackTrace();
            model.addFlashAttribute("msg", "操作失败！");
        }
        return "redirect:/rest/admin/agencyPerson/index";
    }

    /**
     * 删掉功能(暂时没用到)
     * @param agencyPerson
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<AgencyPerson> delete(AgencyPerson agencyPerson) {
        if (!StringUtils.isEmpty(agencyPerson.getAgentUnique())) {
            try {
                List<DeviceList> count = agencyPersonService.getDeviceListByAgentUnique(agencyPerson.getAgentUnique());
                if(count.size() != 0){
                    return new JSONResult<AgencyPerson>(null, "代理人有代理设备,不能被删除", false);
                }
                userService.deleteAgentUser(agencyPerson.getAgentUnique());
                agencyPersonService.delete(agencyPerson.getAgentUnique());
                businessService.updateBusAnegcy(agencyPerson.getAgentUnique());
                return new JSONResult<AgencyPerson>(agencyPerson, "删除成功", true);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<AgencyPerson>(null, "删除失败", false);
            }
        }else {
            return new JSONResult<AgencyPerson>(null, "删除失败", false);
        }
    }

    /**
     * 修改代理人状态
     * 0：禁用  1：启用
     * @param agencyPerson
     * @return
     */
    @RequestMapping(value = "editStatus", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<AgencyPerson> editStatus(AgencyPerson agencyPerson) {
        if (!StringUtils.isEmpty(agencyPerson.getAgentUnique())) {
            try {
                AgencyPerson agencyPerson1 = new AgencyPerson();
                agencyPerson1.setAgentUnique(agencyPerson.getAgentUnique());
                agencyPerson1.setStatus(agencyPerson.getStatus());
                agencyPersonService.updateAgencyPerson(agencyPerson1);
                return new JSONResult<AgencyPerson>(agencyPerson, "操作成功", true);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<AgencyPerson>(null, "操作失败", false);
            }
        }else {
            return new JSONResult<AgencyPerson>(null, "操作失败", false);
        }
    }

    /**
     * 调云支付的接口，获取用户的信息
     * loginName:云支付账号
     * @param request
     * @return
     */
    @RequestMapping(value = "getInfo", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<YunIdInfo> getInfo(HttpServletRequest request) {
        String loginName = request.getParameter("loginName");
        if (!StringUtils.isEmpty(loginName)) {
            try {
                YunIdInfo yunIdInfo = YunPayUtil.getYunAccountInfo(loginName);
                if(org.springframework.util.StringUtils.isEmpty(yunIdInfo)){
                    return new JSONResult<YunIdInfo>(yunIdInfo, "没找到该云支付账号", false);
                }else{
                    return new JSONResult<YunIdInfo>(yunIdInfo, "获取成功", true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<YunIdInfo>(null, "操作失败", false);
            }
        }else {
            return new JSONResult<YunIdInfo>(null, "请输入云支付账号", false);
        }
    }
}
