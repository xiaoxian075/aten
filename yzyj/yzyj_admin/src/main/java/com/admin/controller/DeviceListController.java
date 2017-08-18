package com.admin.controller;

import com.admin.model.*;
import com.admin.service.*;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.core.entity.JSONResult;
import com.core.mybatis.DataTablesPage;
import com.core.util.AppFunUtil;
import com.core.util.FunUtil;
import com.core.util.YunPayUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adminstrator on 2016/6/8.
 * 设备信息
 */
@Controller
@RequestMapping(value = "/admin/deviceList")
public class DeviceListController {
    @Resource
    private DeviceListService deviceListService;
    @Resource
    private DeviceInfoService deviceInfoService;
    @Resource
    private DictTableService dictTableService;
    @Resource
    private AgencyPersonService agencyPersonService;
    @Resource
    private BusinessService businessService;
    @Resource
    private DevicePayCardQuotaService devicePayCardQuotaService;
    @Resource
    private OrderService orderService;

    /**
     * 跳转页面
     * @param model
     * @param deviceList
     * @return
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model,DeviceList deviceList) {
        model.addAttribute("info",deviceList);
        return "admin/deviceList/index";
    }

    /**
     * 获取列表
     * DEVICE_CODE：设备编码
     * MERCHANT_YUN_PAY_ACCOUNT：商户云支付账号
     * @param page
     * @param model
     * @param deviceList
     * @return
     */
    @RequestMapping(value = "getList",method = RequestMethod.GET)
    @ResponseBody
    public String getList(DataTablesPage<DeviceList> page, Model model, DeviceList deviceList) {
        try{
            BaseExample baseExample = new BaseExample();
            if(!StringUtils.isEmpty(deviceList.getQueryBody())){
                baseExample.or().andLike("device.DEVICE_CODE",deviceList.getQueryBody());
                baseExample.or().andLike("device.MERCHANT_YUN_PAY_ACCOUNT",deviceList.getQueryBody());
            }
            baseExample.createCriteria().andEqualTo("device.agent_unique",deviceList.getAgentUnique());
            baseExample.setOrderByClause("CREATE_TIME desc ");
            deviceListService.selectByExampleAndPage(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 跳转添加或编辑页面
     * @param model
     * @param deviceList
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model, DeviceList deviceList) {
        try{
            List<DictTable> deviceType=dictTableService.selectList("device_type");
            List<DictTable> deviceName=dictTableService.selectList("pos_factory");
            model.addAttribute("deviceType", deviceType);//设备类型
            model.addAttribute("deviceName", deviceName);//设备名称
            if(StringUtils.isEmpty(deviceList.getId())){
                model.addAttribute("info",deviceList);
            }else{
                DeviceList deviceList1=deviceListService.selectById(deviceList.getId());
                model.addAttribute("info",deviceList1);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return "admin/deviceList/edit";
    }

    @RequestMapping(value = "edit",method = RequestMethod.POST)
    public String edit(HttpServletRequest request,DeviceList deviceList, RedirectAttributes model ) {
        try {
            String msg="";
            if (StringUtils.isEmpty(deviceList.getId())){
                User user = new User();
                YunIdInfo yunIdInfo = YunPayUtil.getYunAccountInfo(deviceList.getMerchantYunPayAccount());
                if(org.springframework.util.StringUtils.isEmpty(yunIdInfo)){
                    msg = "不存在该云付通帐号";
                }else {
                    if (yunIdInfo.getIsMerchant().equals("1")) {//商家
                        deviceList.setQuotaGroup(97);
                    }else if(yunIdInfo.getLev().equals("2") && yunIdInfo.getIsMerchant().equals("1")) {//是商家又是金钻
                        deviceList.setQuotaGroup(97);
                    }else if(yunIdInfo.getLev().equals("2") && yunIdInfo.getIsMerchant().equals("0")){ //不是商家是金钻
                        deviceList.setQuotaGroup(98);
                    }else if(yunIdInfo.getLev().equals("2")){//金钻
                        deviceList.setQuotaGroup(98);
                    }else{
                        msg = "该云付通帐号既不是商家也不是金钻会员";
                    }
                    deviceList.setApprovalStatus(1);//待审批
                    user.setAgentUnique(deviceList.getAgentUnique());
                    deviceListService.insertAgentDevice(user,deviceList);
                    msg="新增成功";
                }
            }else{
                deviceListService.update(deviceList);
                msg="修改成功";
            }
            model.addFlashAttribute("msg", msg);
        } catch (Exception e) {
            e.printStackTrace();
            model.addFlashAttribute("msg", "操作失败！");
        }
        return "redirect:/rest/admin/deviceList/index?agentUnique="+deviceList.getAgentUnique();
    }

    @RequestMapping(value = "/agentIndex",method = RequestMethod.GET)
    public String agentIndex(HttpServletRequest request,Model model) {
        User user = AppFunUtil.getUser(request);
        if(org.springframework.util.StringUtils.isEmpty(user.getAgentUnique())){
            return "admin/deviceList/agent/index";
        }
        //统计代理人所有的设备交易总和
        Long historyCount = deviceListService.getCountMoneyByAgentUnique(user.getAgentUnique());
        model.addAttribute("historyCount",FunUtil.fenToYuanL(historyCount));

        List<AgencyPerson> list = agencyPersonService.getMoneyByAgentUnique(user.getAgentUnique());
        if(list.size() == 1){
            model.addAttribute("totalFee", FunUtil.fenToYuan(list.get(0).getTotalFee()));
            model.addAttribute("remainingSum", FunUtil.fenToYuan(list.get(0).getRemainingSum()));
        }
        return "admin/deviceList/agent/index";
    }


    /**
     * 代理人查看的代理设备交易总记录
     */
    @RequestMapping(value = "getAgentList",method = RequestMethod.GET)
    @ResponseBody
    public String getAgentList(HttpServletRequest request, DataTablesPage<DeviceList> page, Model model,DeviceList deviceList) {
        try{
            BaseExample baseExample = new BaseExample();
            User user = AppFunUtil.getUser(request);
            if(!StringUtils.isEmpty(deviceList.getMerchantYunPayAccount())){
                baseExample.createCriteria().andEqualTo("device.MERCHANT_YUN_PAY_ACCOUNT",deviceList.getMerchantYunPayAccount());
            }
            if(!StringUtils.isEmpty(deviceList.getLklMerchantCode())){
                baseExample.createCriteria().andEqualTo("device.LKL_MERCHANT_CODE",deviceList.getLklMerchantCode());
            }
            if(!StringUtils.isEmpty(deviceList.getState())){
                baseExample.createCriteria().andEqualTo("device.STATE",deviceList.getState());
            }
            baseExample.createCriteria().andEqualTo("device.agent_unique",user.getAgentUnique());
            baseExample.setOrderByClause("device.CREATE_TIME desc ");
            deviceListService.selectByExampleAndPage(page,baseExample);
            List<DeviceList> list = page.getResult();

            //分别统计代理人代理设备的各个累计总和
            //1表示全部都统计（刷卡跟扫码）
            List<DeviceList> list1 = deviceListService.getMoneyByAgentUnique(user.getAgentUnique(),"1");
            for(int i=0;i<list.size();i++){
                for(int j=0;j<list1.size();j++){
                    if(list1.get(j).getMachineCode().equals(list.get(i).getDeviceCode())){
                        list.get(i).setMoneyCount(list1.get(j).getMoneyCount());
                    }
                }
            }
            //分别统计代理人代理设备的各个当天总和
            //1表示全部都统计（刷卡跟扫码）
            List<DeviceList> list2 = deviceListService.getMoneyByAgentUniqueAndDay(user.getAgentUnique(),"1");
            for(int i=0;i<list.size();i++){
                for(int j=0;j<list2.size();j++){
                    if(list2.get(j).getMachineCode().equals(list.get(i).getDeviceCode())){
                        list.get(i).setDayCount(list2.get(j).getMoneyCount());
                    }
                }
            }
            page.setResult(list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    @RequestMapping(value = "agentEdit", method = RequestMethod.GET)
    public String agentEdit(Model model, DeviceList deviceList) {
        try{
            List<DictTable> deviceType=dictTableService.selectList("device_type");
            model.addAttribute("deviceType", deviceType);
            if(StringUtils.isEmpty(deviceList.getId())){
                model.addAttribute("info",new DeviceList());
            }else{
                DeviceList deviceList1=deviceListService.selectById(deviceList.getId());
                model.addAttribute("info",deviceList1);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return "admin/deviceList/agent/edit";
    }

    @RequestMapping(value = "agentEdit",method = RequestMethod.POST)
    public String agentEdit(HttpServletRequest request,DeviceList deviceList, RedirectAttributes model ) {
        try {
            String msg = "";
            if (StringUtils.isEmpty(deviceList.getId())) {
                User user = AppFunUtil.getUser(request);
                deviceListService.insertAgentDevice(user, deviceList);
                msg = "新增成功";
            } else {
                deviceListService.update(deviceList);
                msg = "修改成功";
            }
            model.addFlashAttribute("msg", msg);
        } catch (Exception e) {
            e.printStackTrace();
            model.addFlashAttribute("msg", "操作失败！");
        }
        return "redirect:/rest/admin/deviceList/agentIndex";
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<DeviceList> delete(HttpServletRequest request,DeviceList deviceList) {
        if (!org.springframework.util.StringUtils.isEmpty(deviceList.getDeviceUnique())) {
            try {
                User user = AppFunUtil.getUser(request);
                if(org.springframework.util.StringUtils.isEmpty(user)){
                    return new JSONResult<DeviceList>(deviceList, "删除失败", false);
                }
                deviceListService.deleteAgentDevice(deviceList);
                deviceInfoService.deleteInfoDevice(deviceList.getDeviceUnique());
                return new JSONResult<DeviceList>(deviceList, "删除成功", true);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<DeviceList>(null, "删除失败", false);
            }
        }else {
            return new JSONResult<DeviceList>(null, "删除失败", false);
        }
    }

    @RequestMapping(value = "editStatus", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<DeviceList> editStatus(DeviceList deviceList) {
        if (!org.springframework.util.StringUtils.isEmpty(deviceList.getId())) {
            try {
                DeviceList deviceList1 = new DeviceList();
                deviceList1.setId(deviceList.getId());
                deviceList1.setState(deviceList.getState());
                deviceListService.update(deviceList1);
                return new JSONResult<DeviceList>(deviceList1, "操作成功", true);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<DeviceList>(null, "操作失败", false);
            }
        }else {
            return new JSONResult<DeviceList>(null, "操作失败", false);
        }
    }

    @RequestMapping(value = "/approveIndex",method = RequestMethod.GET)
    public String approveIndex(Model model,DeviceList deviceList) {
        model.addAttribute("info",deviceList);
        return "admin/deviceList/approve/index";
    }

    @RequestMapping(value = "getApproveList",method = RequestMethod.GET)
    @ResponseBody
    public String getApproveList(DataTablesPage<DeviceList> page, Model model, DeviceList deviceList) {
        try{
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
            //搜索条件
            if(!StringUtils.isEmpty(deviceList.getDeviceCode())){
                criteria.andEqualTo("device.DEVICE_CODE",deviceList.getDeviceCode());
            }
            if(!StringUtils.isEmpty(deviceList.getLklMachineCode())){
                criteria.andEqualTo("device.LKL_MACHINE_CODE",deviceList.getLklMachineCode());
            }
            if(!StringUtils.isEmpty(deviceList.getLklMerchantCode())){
                criteria.andEqualTo("device.LKL_MERCHANT_CODE",deviceList.getLklMerchantCode());
            }
            if(!StringUtils.isEmpty(deviceList.getLklTerminalCode())){
                criteria.andEqualTo("device.LKL_TERMINAL_CODE",deviceList.getLklTerminalCode());
            }
            if(!StringUtils.isEmpty(deviceList.getMerchantYunPayAccount())){
                criteria.andEqualTo("device.MERCHANT_YUN_PAY_ACCOUNT",deviceList.getMerchantYunPayAccount());
            }
            criteria.andEqualTo("1","1");
            baseExample.setOrderByClause("device.CREATE_TIME desc ");
            deviceListService.selectAllByExampleAndPage(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    @RequestMapping(value = "editApproval", method = RequestMethod.GET)
    public String editApproval(Model model, DeviceList deviceList) {
        try{
            List<DictTable> deviceType = dictTableService.selectList("device_type");
            model.addAttribute("deviceType", deviceType);
            List<DictTable> deviceName=dictTableService.selectList("pos_factory");
            model.addAttribute("deviceName", deviceName);

            List<AgencyPerson> agencyPerson1 = agencyPersonService.getAgencyList();
            if(StringUtils.isEmpty(deviceList.getId())){
                model.addAttribute("info",new DeviceList());
                model.addAttribute("agencyList",agencyPerson1);
            }else{
                DeviceList deviceList1 = deviceListService.selectById(deviceList.getId());
                List<Business> business = businessService.getBusinessNameByAgentUnique(deviceList1.getAgentUnique());
                model.addAttribute("info",deviceList1);
                model.addAttribute("business",business);
                model.addAttribute("agencyList",agencyPerson1);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return "admin/deviceList/approve/edit";
    }

    @RequestMapping(value = "editApproval",method = RequestMethod.POST)
    public String editApproval(HttpServletRequest request,DeviceList deviceList, RedirectAttributes model ) {
        try {
            String msg="";
            if (StringUtils.isEmpty(deviceList.getId())){
                User user = new User();
                YunIdInfo yunIdInfo = YunPayUtil.getYunAccountInfo(deviceList.getMerchantYunPayAccount());
                if(org.springframework.util.StringUtils.isEmpty(yunIdInfo)){
                    msg = "不存在该云付通帐号";
                }else{
                    if(yunIdInfo.getIsMerchant().equals("1")){//商家
                        deviceList.setQuotaGroup(97);
                    }else if(yunIdInfo.getLev().equals("2") && yunIdInfo.getIsMerchant().equals("1")){//是商家又是金钻
                        deviceList.setQuotaGroup(97);
                    }else if(yunIdInfo.getLev().equals("2")){//金钻
                        deviceList.setQuotaGroup(98);
                    }else{
                        msg = "该云付通帐号既不是商家也不是金钻会员";
                    }
                    deviceList.setApprovalStatus(1);//待审批
                    String[] mResult = deviceList.getMerchantName().split(",");
                    deviceList.setMerchantId(mResult[0]);
                    deviceList.setMerchantName(mResult[1]);
                    user.setAgentUnique(deviceList.getAgentUnique());
                    deviceListService.insertAgentDevice(user,deviceList);
                    msg="新增成功";
                }
            }else{
                String[] mResult = deviceList.getMerchantName().split(",");
                deviceList.setMerchantId(mResult[0]);
                deviceList.setMerchantName(mResult[1]);
                deviceListService.update(deviceList);
                msg="修改成功";
            }
            model.addFlashAttribute("msg", msg);
        } catch (Exception e) {
            e.printStackTrace();
            model.addFlashAttribute("msg", "操作失败！");
        }
        return "redirect:/rest/admin/deviceList/approveIndex";
    }

    @RequestMapping(value = "editApprovalStatus", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<DeviceList> editApprovalStatus(DeviceList deviceList) {
        if (!org.springframework.util.StringUtils.isEmpty(deviceList.getId())) {
            try {
                deviceList.setApprovalStatus(2);
                deviceListService.updateApproval(Integer.parseInt(deviceList.getId()),deviceList.getApprovalStatus(),0);
                return new JSONResult<DeviceList>(null, "操作成功", true);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<DeviceList>(null, "操作失败", false);
            }
        }else {
            return new JSONResult<DeviceList>(null, "操作失败", false);
        }
    }

    @RequestMapping(value = "/approveIndexLead",method = RequestMethod.GET)
    public String approveIndexLead(Model model,DeviceList deviceList) {
        model.addAttribute("info",deviceList);
        return "admin/deviceList/approveLead/index";
    }

    @RequestMapping(value = "getApproveListLead",method = RequestMethod.GET)
    @ResponseBody
    public String getApproveListLead(DataTablesPage<DeviceList> page, Model model, DeviceList deviceList) {
        try{
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
            //搜索条件
            if(!StringUtils.isEmpty(deviceList.getDeviceCode())){
                criteria.andEqualTo("device.DEVICE_CODE",deviceList.getDeviceCode());
            }
            if(!StringUtils.isEmpty(deviceList.getLklMachineCode())){
                criteria.andEqualTo("device.LKL_MACHINE_CODE",deviceList.getLklMachineCode());
            }
            if(!StringUtils.isEmpty(deviceList.getLklMerchantCode())){
                criteria.andEqualTo("device.LKL_MERCHANT_CODE",deviceList.getLklMerchantCode());
            }
            if(!StringUtils.isEmpty(deviceList.getLklTerminalCode())){
                criteria.andEqualTo("device.LKL_TERMINAL_CODE",deviceList.getLklTerminalCode());
            }
            if(!StringUtils.isEmpty(deviceList.getMerchantYunPayAccount())){
                criteria.andEqualTo("device.MERCHANT_YUN_PAY_ACCOUNT",deviceList.getMerchantYunPayAccount());
            }
            criteria.andEqualTo("APPROVAL_STATUS","2");
            baseExample.setOrderByClause("device.CREATE_TIME desc ");
            deviceListService.selectAllByExampleAndPage(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    @RequestMapping(value = "/leadIndex",method = RequestMethod.GET)
    public String leadIndex(Model model,DeviceList deviceList) {
        model.addAttribute("info",deviceList);
        return "admin/deviceList/approveLead/alIndex";
    }


    @RequestMapping(value = "/deviceAlSubsidyIndex",method = RequestMethod.GET)
    public String deviceAlIndex(Model model,DeviceList deviceList) {
        model.addAttribute("info",deviceList);
        return "admin/deviceList/approveLead/deviceAlIndex";
    }

    /**
     * 已经通过审批的设备列表
     * @param page
     * @param model
     * @param deviceList
     * @return
     */
    @RequestMapping(value = "getalLeadList",method = RequestMethod.GET)
    @ResponseBody
    public String getalLeadList(DataTablesPage<DeviceList> page, Model model, DeviceList deviceList) {
        try{
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
            //搜索条件
            if(!StringUtils.isEmpty(deviceList.getDeviceCode())){
                criteria.andEqualTo("device.DEVICE_CODE",deviceList.getDeviceCode());
            }
            if(!StringUtils.isEmpty(deviceList.getLklMachineCode())){
                criteria.andEqualTo("device.LKL_MACHINE_CODE",deviceList.getLklMachineCode());
            }
            if(!StringUtils.isEmpty(deviceList.getLklMerchantCode())){
                criteria.andEqualTo("device.LKL_MERCHANT_CODE",deviceList.getLklMerchantCode());
            }
            if(!StringUtils.isEmpty(deviceList.getLklTerminalCode())){
                criteria.andEqualTo("device.LKL_TERMINAL_CODE",deviceList.getLklTerminalCode());
            }
            if(!StringUtils.isEmpty(deviceList.getMerchantYunPayAccount())){
                criteria.andEqualTo("device.MERCHANT_YUN_PAY_ACCOUNT",deviceList.getMerchantYunPayAccount());
            }
            if(!StringUtils.isEmpty(deviceList.getAgentName())){
                criteria.andEqualTo("agent.REALNAME",deviceList.getAgentName());
            }
            if(!StringUtils.isEmpty(deviceList.getState())){
                criteria.andEqualTo("device.STATE",deviceList.getState());
            }
            if(!StringUtils.isEmpty(deviceList.getSubsidyStatus())){
                criteria.andEqualTo("device.SUBSIDY_STATUS",deviceList.getSubsidyStatus());
            }
            criteria.andEqualTo("APPROVAL_STATUS","3");
            baseExample.setOrderByClause("device.CREATE_TIME desc ");
            deviceListService.selectAllByExampleAndPage(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 已经提交返现的设备列表
     * @param page
     * @param model
     * @param deviceList
     * @return
     */
    @RequestMapping(value = "getWaitSubsidyList",method = RequestMethod.GET)
    @ResponseBody
    public String getWaitSubsidyList(DataTablesPage<DeviceList> page, Model model, DeviceList deviceList) {
        try{
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
            //搜索条件
            if(!StringUtils.isEmpty(deviceList.getLklMerchantCode())){
                criteria.andEqualTo("device.LKL_MERCHANT_CODE",deviceList.getLklMerchantCode());
            }
            if(!StringUtils.isEmpty(deviceList.getLklTerminalCode())){
                criteria.andEqualTo("device.LKL_TERMINAL_CODE",deviceList.getLklTerminalCode());
            }
            if(!StringUtils.isEmpty(deviceList.getMerchantYunPayAccount())){
                criteria.andEqualTo("device.MERCHANT_YUN_PAY_ACCOUNT",deviceList.getMerchantYunPayAccount());
            }
            if(!StringUtils.isEmpty(deviceList.getRealName())){
                criteria.andEqualTo("agent.REALNAME",deviceList.getRealName());
            }

            criteria.andEqualTo("SUBSIDY_STATUS","1");
            baseExample.setOrderByClause("device.CREATE_TIME desc ");
            deviceListService.selectAllByExampleAndPage(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 已经审批返现的设备列表
     * @param page
     * @param model
     * @param deviceList
     * @return
     */
    @RequestMapping(value = "getDeviceAlSubsidyList",method = RequestMethod.GET)
    @ResponseBody
    public String getDeviceAlSubsidyList(DataTablesPage<DeviceList> page, Model model, DeviceList deviceList) {
        try{
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
            //搜索条件
            if(!StringUtils.isEmpty(deviceList.getLklMerchantCode())){
                criteria.andEqualTo("device.LKL_MERCHANT_CODE",deviceList.getLklMerchantCode());
            }
            if(!StringUtils.isEmpty(deviceList.getLklTerminalCode())){
                criteria.andEqualTo("device.LKL_TERMINAL_CODE",deviceList.getLklTerminalCode());
            }
            if(!StringUtils.isEmpty(deviceList.getMerchantYunPayAccount())){
                criteria.andEqualTo("device.MERCHANT_YUN_PAY_ACCOUNT",deviceList.getMerchantYunPayAccount());
            }
            if(!StringUtils.isEmpty(deviceList.getRealName())){
                criteria.andLike("agent.REALNAME",deviceList.getRealName());
            }

            criteria.andEqualTo("SUBSIDY_STATUS","2");
            baseExample.setOrderByClause("device.CREATE_TIME desc ");
            deviceListService.selectAllByExampleAndPage(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }



    /**
     * 设备详情页
     * @param model
     * @param deviceList
     * @return
     */
    @RequestMapping(value = "deviceDetail", method = RequestMethod.GET)
    public String deviceDetail(Model model, DeviceList deviceList) {
        try {
            List<DeviceList> list = new ArrayList<DeviceList>();
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria =  baseExample.createCriteria();
            criteria.andEqualTo("1","1");
            if(StringUtils.isEmpty(deviceList.getId())){
                model.addAttribute("detail",list);
            }else{
                DeviceList deviceList1 = deviceListService.getDeviceListByDId(deviceList.getId());
                deviceList1.setStrCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(deviceList1.getCreateTime()));
                List<DevicePayCardQuota> devicePayCardQuota = devicePayCardQuotaService.selectByExample(baseExample);
                model.addAttribute("detail",deviceList1);
                model.addAttribute("qGroup",devicePayCardQuota);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "admin/deviceList/approveLead/detail";
    }

    /**
     * 审批设备
     * @param deviceList
     * @return
     */
    @RequestMapping(value = "editApprovalStatusLead", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<DeviceList> editApprovalStatusLead(DeviceList deviceList) {
        if (!org.springframework.util.StringUtils.isEmpty(deviceList.getId())) {
            try {
                deviceListService.updateApproval(Integer.parseInt(deviceList.getId()),deviceList.getApprovalStatus(),0);
                return new JSONResult<DeviceList>(null, "操作成功", true);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<DeviceList>(null, "操作失败", false);
            }
        }else {
            return new JSONResult<DeviceList>(null, "操作失败", false);
        }
    }

    /**
     * 一键操作  1：一键启用  0：一键禁用
     * @param deviceList
     * @return
     */
    @RequestMapping(value = "oneKey", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<DeviceList> oneKey(DeviceList deviceList) {
        if (!org.springframework.util.StringUtils.isEmpty(deviceList.getState())) {
            try {
                if("1".equals(deviceList.getState())){
                    deviceListService.updateOneKey(1);
                }else if("0".equals(deviceList.getState())){
                    deviceListService.updateOneKey(0);
                }else{
                    return new JSONResult<DeviceList>(null, "操作失败", true);
                }
                return new JSONResult<DeviceList>(null, "操作成功", true);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<DeviceList>(null, "操作失败", false);
            }
        }else {
            return new JSONResult<DeviceList>(null, "操作失败", false);
        }
    }

    /**
     * 保存额度分组
     * @param deviceList
     * @return
     */
    @RequestMapping(value = "saveGroup", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<DeviceList> saveGroup(DeviceList deviceList) {
        if (!org.springframework.util.StringUtils.isEmpty(deviceList.getDeviceUnique())) {
            try {
                deviceListService.updateGroup(deviceList.getDeviceUnique(),String.valueOf(deviceList.getQuotaGroup()));
                return new JSONResult<DeviceList>(null, "操作成功", true);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<DeviceList>(null, "操作失败", false);
            }
        }else {
            return new JSONResult<DeviceList>(null, "操作失败", false);
        }
    }

    /**
     * 提交设备返现审批
     * @param deviceList
     * @return
     */
    @RequestMapping(value = "submitSubsidy", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<DeviceList> submitSubsisy(DeviceList deviceList) {
        if (!StringUtils.isEmpty(deviceList.getAgentUnique())) {
            try {
                DeviceList deviceList1 = deviceListService.getDeviceListByDId(deviceList.getId());
                if(!StringUtils.isEmpty(deviceList1)){
                    if("2".equals(deviceList1.getSubsidyStatus()) || "1".equals(deviceList1.getSubsidyStatus())){
                        return new JSONResult<DeviceList>(null, "该设备已返现给商户，不能重复提交", false);
                    }
                    deviceListService.updateSubsidyStatus(deviceList.getId(),"1");
                }else{
                    return new JSONResult<DeviceList>(null, "没有找到返现设备", false);
                }
                return new JSONResult<DeviceList>(null, "提交成功，等待审批", true);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<DeviceList>(null, "操作失败", false);
            }
        }else {
            return new JSONResult<DeviceList>(null, "提交返现失败", false);
        }
    }

    /**
     * 跳转到设备返现审批页面
     * @param model
     * @param deviceList
     * @return
     */
    @RequestMapping(value = "/waitSubsidy",method = RequestMethod.GET)
    public String waitSubsidy(Model model,DeviceList deviceList) {
        model.addAttribute("info",deviceList);
        return "admin/deviceList/approveLead/waitSubsidy";
    }

    /**
     * 驳回返现审批
     * @param request
     * @param deviceList
     * @return
     */
    @RequestMapping(value = "rejectSubsidy", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<DeviceList> rejectSubsidy(HttpServletRequest request,DeviceList deviceList) {
        if (!org.springframework.util.StringUtils.isEmpty(deviceList.getAgentUnique())) {
            try {
                DeviceList deviceList1 = deviceListService.getDeviceListByDId(deviceList.getId());
                if(!org.springframework.util.StringUtils.isEmpty(deviceList1)){
                    if(deviceList1.getAgentUnique().equals(deviceList.getAgentUnique())){
                        deviceListService.updateSubsidyStatus(deviceList.getId(),"3");
                    }else{
                        return new JSONResult<DeviceList>(null, "未找到设备代理人", false);
                    }
                }else{
                    return new JSONResult<DeviceList>(null, "没有找到设备信息", false);
                }
                return new JSONResult<DeviceList>(null, "退回成功", true);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<DeviceList>(null, "操作失败", false);
            }
        }else {
            return new JSONResult<DeviceList>(null, "操作失败", false);
        }
    }

    /**
     * 通过返现审批
     * @param request
     * @param deviceList
     * @return
     */
    @RequestMapping(value = "passSubsidy", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<DeviceList> passSubsidy(HttpServletRequest request,DeviceList deviceList) {
        //判断参数是否为空
        if (!StringUtils.isEmpty(deviceList.getAgentUnique())) {
            try {
                //获取设备列表
                DeviceList deviceList1 = deviceListService.getDeviceListByDId(deviceList.getId());
                //判断是否有提交的设备信息
                if(!StringUtils.isEmpty(deviceList1)){
                    if(deviceList1.getAgentUnique().equals(deviceList.getAgentUnique())){
                        if("2".equals(deviceList1.getSubsidyStatus())){
                            return new JSONResult<DeviceList>(null, "该设备已返现给商户，不能重复提交", false);
                        }else{
                            //获取商户金额
                            List<DictTable> shfxMoney = dictTableService.selectList("shfx_money");
                            //商户下发金额
                            deviceList.setSubsidyStatus("2");
                            Integer state = deviceListService.updateStatusAndMoney(deviceList,Integer.parseInt(shfxMoney.get(0).getDictBh()));
                            if(state == 0){
                                return new JSONResult<DeviceList>(null, "操作失败", false);
                            }
                        }
                        return new JSONResult<DeviceList>(null, "操作成功，商户已返现", true);
                    }else{
                        return new JSONResult<DeviceList>(null, "未找到设备代理人", false);
                    }
                }else{
                    return new JSONResult<DeviceList>(null, "没有找到设备信息", false);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<DeviceList>(null, "操作失败", false);
            }
        }else {
            return new JSONResult<DeviceList>(null, "操作失败", false);
        }
    }

    /**
     * 跳转到激活码重置页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/restCodeIndex", method = RequestMethod.GET)
    public String restCode(Model model) {
        return "admin/deviceList/restCodeIndex";
    }

    /**
     * 获取激活码重置列表
     * @param page
     * @param model
     * @param deviceList
     * @return
     */
    @RequestMapping(value = "/getRestCodeList",method = RequestMethod.GET)
    @ResponseBody
    public String getRestCodeList(DataTablesPage<DeviceList> page, Model model, DeviceList deviceList) {
        try{
            BaseExample baseExample = new BaseExample();
            //订单类型  POS
            BaseExample.Criteria criteria = baseExample.createCriteria();
            if(!StringUtils.isEmpty(deviceList.getLklMerchantCode())){
                criteria.andEqualTo("device.LKL_MERCHANT_CODE",deviceList.getLklMerchantCode());
                deviceListService.selectByExampleAndPage(page,baseExample);
            }else{

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 把state设置为0
     * 重置设备状态  deviceInfo  state 1:已激活  0:未激活
     * @param deviceInfo
     * @return
     */
    @RequestMapping(value = "editDeviceListState", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<DeviceInfo> editDeviceListState(DeviceInfo deviceInfo) {
        if (!org.springframework.util.StringUtils.isEmpty(deviceInfo.getDeviceUnique())) {
            try {
                deviceInfoService.updateState(0,deviceInfo.getDeviceUnique());
                return new JSONResult<DeviceInfo>(null, "操作成功", true);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<DeviceInfo>(null, "操作失败", false);
            }
        }else {
            return new JSONResult<DeviceInfo>(null, "操作失败", false);
        }
    }

    /**
     * 跳转到设备退押金审批页面
     * @param model
     * @param deviceList
     * @return
     */
    @RequestMapping(value = "/waitTyj",method = RequestMethod.GET)
    public String waitTyj(Model model,DeviceList deviceList) {
        model.addAttribute("info",deviceList);
        return "admin/deviceList/approveLead/waitTyj";
    }

    /**
     * 提交设备退押金审批
     * @param deviceList
     * @return
     */
    @RequestMapping(value = "submitTyj", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<DeviceList> submitTyj(DeviceList deviceList) {
        if (!StringUtils.isEmpty(deviceList.getDeviceUnique())) {
            try {
                DeviceList deviceList1 = deviceListService.getDeviceListByDId(deviceList.getId());
                if(!StringUtils.isEmpty(deviceList1) && deviceList.getDeviceUnique().equals(deviceList1.getDeviceUnique())){
                    if("2".equals(deviceList1.getTyjStatus()) || "1".equals(deviceList1.getTyjStatus())){
                        return new JSONResult<DeviceList>(null, "已审批或者审批通过,不能重复提交", false);
                    }
                    deviceListService.updateTyjStatus(deviceList1,"1");
                }else{
                    return new JSONResult<DeviceList>(null, "没有找到设备", false);
                }
                return new JSONResult<DeviceList>(null, "提交成功，等待审批", true);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<DeviceList>(null, "操作失败", false);
            }
        }else {
            return new JSONResult<DeviceList>(null, "提交退押金失败", false);
        }
    }

    /**
     * 已经提交返现的设备列表
     * @param page
     * @param model
     * @param deviceList
     * @return
     */
    @RequestMapping(value = "getWaitTyjList",method = RequestMethod.GET)
    @ResponseBody
    public String getWaitTyjList(DataTablesPage<DeviceList> page, Model model, DeviceList deviceList) {
        try{
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
            //搜索条件
            if(!StringUtils.isEmpty(deviceList.getLklMerchantCode())){
                criteria.andEqualTo("device.LKL_MERCHANT_CODE",deviceList.getLklMerchantCode());
            }
            if(!StringUtils.isEmpty(deviceList.getLklTerminalCode())){
                criteria.andEqualTo("device.LKL_TERMINAL_CODE",deviceList.getLklTerminalCode());
            }
            if(!StringUtils.isEmpty(deviceList.getMerchantYunPayAccount())){
                criteria.andEqualTo("device.MERCHANT_YUN_PAY_ACCOUNT",deviceList.getMerchantYunPayAccount());
            }
            criteria.andEqualTo("device.TYJ_STATUS","1");
            baseExample.setOrderByClause("device.TYJ_TIME desc ");
            deviceListService.selectAllByExampleAndPage(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 驳回退押金审批
     * @param request
     * @param deviceList
     * @return
     */
    @RequestMapping(value = "rejectTyj", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<DeviceList> rejectTyj(HttpServletRequest request,DeviceList deviceList) {
        if (!StringUtils.isEmpty(deviceList.getDeviceUnique())) {
            try {
                DeviceList deviceList1 = deviceListService.getDeviceListByDId(deviceList.getId());
                if(!StringUtils.isEmpty(deviceList1)){
                    if(deviceList1.getDeviceUnique().equals(deviceList.getDeviceUnique())){
                        deviceListService.updateTyjStatus(deviceList1,"0");
                    }
                }else{
                    return new JSONResult<DeviceList>(null, "没有找到设备信息", false);
                }
                return new JSONResult<DeviceList>(null, "退回成功", true);
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<DeviceList>(null, "操作失败", false);
            }
        }else {
            return new JSONResult<DeviceList>(null, "操作失败", false);
        }
    }

    /**
     * 通过退押金审批
     * @param request
     * @param deviceList
     * @return
     */
    @RequestMapping(value = "passTyj", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<DeviceList> passTyj(HttpServletRequest request,DeviceList deviceList) {
        //判断参数是否为空
        if (!StringUtils.isEmpty(deviceList.getDeviceUnique())) {
            try {
                //获取设备列表
                DeviceList deviceList1 = deviceListService.getDeviceListByDId(deviceList.getId());
                //判断是否有提交的设备信息
                if(!StringUtils.isEmpty(deviceList1) && deviceList1.getDeviceUnique().equals(deviceList.getDeviceUnique())){
                    if("2".equals(deviceList1.getTyjStatus())){
                        return new JSONResult<DeviceList>(null, "该设备已退过押金，不能重复提交", false);
                    }else{
                        //获取商户金额
                        List<DictTable> returnMoney = dictTableService.selectList("return_money");
                        //商户下发金额
                        Integer state = deviceListService.updateTyjStatusAndMoney(deviceList1,Integer.parseInt(returnMoney.get(0).getDictBh()));
                        if(state == 0){
                            return new JSONResult<DeviceList>(null, "操作失败", false);
                        }
                    }
                    return new JSONResult<DeviceList>(null, "操作成功，商户已收到退押金", true);
                }else{
                    return new JSONResult<DeviceList>(null, "没有找到设备信息", false);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONResult<DeviceList>(null, "操作失败", false);
            }
        }else {
            return new JSONResult<DeviceList>(null, "操作失败", false);
        }
    }

    /**
     * 已经审批返现的设备列表
     * @param page
     * @param model
     * @param deviceList
     * @return
     */
    @RequestMapping(value = "getDeviceAlTyjList",method = RequestMethod.GET)
    @ResponseBody
    public String getDeviceAlTyjList(DataTablesPage<DeviceList> page, Model model, DeviceList deviceList) {
        try{
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
            //搜索条件
            if(!StringUtils.isEmpty(deviceList.getLklMerchantCode())){
                criteria.andEqualTo("device.LKL_MERCHANT_CODE",deviceList.getLklMerchantCode());
            }
            if(!StringUtils.isEmpty(deviceList.getLklTerminalCode())){
                criteria.andEqualTo("device.LKL_TERMINAL_CODE",deviceList.getLklTerminalCode());
            }
            if(!StringUtils.isEmpty(deviceList.getMerchantYunPayAccount())){
                criteria.andEqualTo("device.MERCHANT_YUN_PAY_ACCOUNT",deviceList.getMerchantYunPayAccount());
            }
            if(!StringUtils.isEmpty(deviceList.getRealName())){
                criteria.andLike("agent.REALNAME",deviceList.getRealName());
            }

            criteria.andEqualTo("device.TYJ_STATUS","2");
            baseExample.setOrderByClause("device.CREATE_TIME desc ");
            deviceListService.selectAllByExampleAndPage(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    @RequestMapping(value = "/deviceAlTyjIndex",method = RequestMethod.GET)
    public String deviceAlTyjIndex(Model model,DeviceList deviceList) {
        model.addAttribute("info",deviceList);
        return "admin/deviceList/approveLead/deviceAlTyjIndex";
    }
}
