package com.admin.controller;

import com.admin.model.*;
import com.admin.service.*;
import com.admin.vo.CommAppVo;
import com.admin.vo.xml.ReadXml;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.core.entity.JSONResult;
import com.core.mybatis.DataTablesPage;
import com.core.util.*;
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
 * Created by Administrator on 2016/11/15.
 */
@Controller
@RequestMapping(value = "/admin/withdraw")
public class WithdrawRecordController {
    @Resource
    private WithdrawRecordService withdrawRecordService;
    @Resource
    private PosWithdrawService posWithdrawService;
    @Resource
    private BusinessService businessService;
    @Resource
    private DeviceListService deviceListService;
    @Resource
    private DictTableService dictTableService;

    @RequestMapping(value = "/agency/index",method = RequestMethod.GET)
    public String agencyIndex(HttpServletRequest request,Model model) {
        User user = AppFunUtil.getUser(request);
        int type = 0;
        Integer count = null;
        if(!StringUtils.isEmpty(user.getAgentUnique())){
            count = withdrawRecordService.getCountWithdrawByAgent(user.getAgentUnique());
        }else{
            count = withdrawRecordService.getCountWithdraw();
            type = 1;
        }
        model.addAttribute("count", FunUtil.fenToYuan(count));
        model.addAttribute("type", type);
        return "admin/withdraw/agency/index";
    }

    @RequestMapping(value = "/agency/getAgencyList",method = RequestMethod.GET)
    @ResponseBody
    public String getAgencyList(HttpServletRequest request, DataTablesPage<WithdrawRecord> page,WithdrawRecord withdrawRecord ) {
        try{
            BaseExample baseExample = new BaseExample();
            //订单类型  POS
            BaseExample.Criteria criteria = baseExample.createCriteria();
            User user = AppFunUtil.getUser(request);
            if(!StringUtils.isEmpty(withdrawRecord.getRealName())){
                criteria.andEqualTo("agent.REALNAME",withdrawRecord.getRealName());
            }
            if(withdrawRecord.getSdate() != null){
                String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(withdrawRecord.getSdate());
                criteria.andGreaterThanOrEqualTo("TO_CHAR(T.CREATE_TIME,'yyyy-mm-dd hh24:mi:ss')",s);
            }
            if(withdrawRecord.getEdate() != null){
                String e = new SimpleDateFormat("yyyy-MM-dd").format(withdrawRecord.getEdate());
                criteria.andLessThanOrEqualTo("TO_CHAR(T.CREATE_TIME,'yyyy-mm-dd hh24:mi:ss')",e+" 23:59:59");
            }
            if(!StringUtils.isEmpty(withdrawRecord.getYunPayLoginName())){
                criteria.andEqualTo("agent.YUNPAYLOGINNAME",withdrawRecord.getYunPayLoginName());
            }
            criteria.andEqualTo("1","1");
            if(!StringUtils.isEmpty(user.getAgentUnique())){
                criteria.andEqualTo("agent.AGENT_UNIQUE",user.getAgentUnique());
                baseExample.setOrderByClause("T.CREATE_TIME DESC");
                withdrawRecordService.selectByExampleAndPage1(page,baseExample);
            }else{
                baseExample.setOrderByClause("T.CREATE_TIME DESC");
                withdrawRecordService.selectByExampleAndPage1(page,baseExample);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 提现
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/posIndex",method = RequestMethod.GET)
    public String posIndex(HttpServletRequest request,Model model) {
        return "admin/withdraw/posIndex";
    }

    /**
     * 已下发
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/sendIndex",method = RequestMethod.GET)
    public String sendIndex(HttpServletRequest request,Model model) {
        HashMap<Object,Object> map = new HashMap<Object,Object>();
        //刷卡
        PosWithdrawRecord posWithdrawRecord = posWithdrawService.getCountWithdraw();
        model.addAttribute("allWithdraw", posWithdrawRecord.getAllWithdraw());
        model.addAttribute("allSend", posWithdrawRecord.getAllSend());
        return "admin/withdraw/sendIndex";
    }

    /**
     * pos商户云支付提现
     * @return
     */
    @RequestMapping(value = "/getPosWithdrawList",method = RequestMethod.GET)
    @ResponseBody
    public String getPosWithdrawList(HttpServletRequest request, DataTablesPage<PosWithdrawRecord> page, PosWithdrawRecord posWithdrawRecord) {
        try{
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
            //搜索条件
            if(!StringUtils.isEmpty(posWithdrawRecord.getLoginName())){
                criteria.andEqualTo("LOGIN_NAME",posWithdrawRecord.getLoginName());
            }
           if(posWithdrawRecord.getSdate() != null){
                String s = new SimpleDateFormat("yyyy-MM-dd").format(posWithdrawRecord.getSdate());
                criteria.andGreaterThanOrEqualTo("WITHDRAW_TIME",s);
            }
            if(posWithdrawRecord.getEdate() != null){
                String e = new SimpleDateFormat("yyyy-MM-dd").format(posWithdrawRecord.getEdate());
                criteria.andLessThanOrEqualTo("WITHDRAW_TIME",e+" 23:59:59");
            }
            if(!StringUtils.isEmpty(posWithdrawRecord.getAccountName())){
                criteria.andEqualTo("ACCOUNT_NAME",posWithdrawRecord.getAccountName());
            }
            criteria.andEqualTo("IS_SEND","0");
            criteria.andEqualTo("1","1");
            baseExample.setOrderByClause("AUDIT_TIME desc ");
            posWithdrawService.selectByExampleAndPage(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 获取已经下发的列表
     * @param request
     * @param page
     * @param posWithdrawRecord
     * @return
     */
    @RequestMapping(value = "/getIsSendList",method = RequestMethod.GET)
    @ResponseBody
    public String getIsSendList(HttpServletRequest request, DataTablesPage<PosWithdrawRecord> page, PosWithdrawRecord posWithdrawRecord) {
        try{
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
            //搜索条件
            //云付通账号
            if(!StringUtils.isEmpty(posWithdrawRecord.getLoginName())){
                criteria.andEqualTo("LOGIN_NAME",posWithdrawRecord.getLoginName());
            }
            //云付通编号
            if(!StringUtils.isEmpty(posWithdrawRecord.getYftNumber())){
                criteria.andEqualTo("YFT_NUMBER",String.valueOf(posWithdrawRecord.getYftNumber()));
            }
            //下发时间起
           if(posWithdrawRecord.getSdate() != null){
               String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(posWithdrawRecord.getSdate());
                criteria.andGreaterThanOrEqualTo("TO_CHAR(IS_SEND_TIME,'yyyy-mm-dd hh24:mi:ss')",s);
            }
            //下发时间止
            if(posWithdrawRecord.getEdate() != null){
                String e = new SimpleDateFormat("yyyy-MM-dd").format(posWithdrawRecord.getEdate());
                criteria.andLessThanOrEqualTo("TO_CHAR(IS_SEND_TIME,'yyyy-mm-dd hh24:mi:ss')",e+" 23:59:59");
            }
            //账号名称
            if(!StringUtils.isEmpty(posWithdrawRecord.getAccountName())){
                criteria.andEqualTo("ACCOUNT_NAME",posWithdrawRecord.getAccountName());
            }
            //流水号
            if(!StringUtils.isEmpty(posWithdrawRecord.getOrderLogNo())){
                criteria.andEqualTo("ORDER_LOG_NO",posWithdrawRecord.getOrderLogNo());
            }

            criteria.andEqualTo("IS_SEND","1");
            criteria.andEqualTo("1","1");
            baseExample.setOrderByClause("IS_SEND_TIME desc ");
            posWithdrawService.selectByExampleAndPage(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 下发操作
     * @param page
     * @param model
     * @param posWithdrawRecord
     * @return
     */
    @RequestMapping(value = "isSend",method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<PosWithdrawRecord> isSend(DataTablesPage<PosWithdrawRecord> page, Model model, PosWithdrawRecord posWithdrawRecord) {
        if(!StringUtils.isEmpty(posWithdrawRecord.getPid())){
            try{
                PosWithdrawRecord posWithdrawRecord1 = posWithdrawService.getPosWithdrawRecordById(posWithdrawRecord.getPid());
                if(StringUtils.isEmpty(posWithdrawRecord1)){
                    return new JSONResult<PosWithdrawRecord>(null, "无法找到提现记录", false);
                }else{
                    Business business = businessService.getBusinessInfoByYId(posWithdrawRecord1.getLoginName());
                    if(StringUtils.isEmpty(business)){
                        return new JSONResult<PosWithdrawRecord>(null, "该云付通账号不是pos机商户", false);
                    }else{
                        //因财务要求，取消掉设备禁用也能下发这个条件
                        List<DeviceList> deviceList = deviceListService.getDeviceListByYId(posWithdrawRecord1.getLoginName());
                        if(deviceList.size() != 0){
                            List<DictTable> lklSxf = dictTableService.selectList("lkl_xfed");
                            Integer sendMoney = posWithdrawRecord1.getAmount();
                            //组装需要的格式，以及数据
                            CommAppVo commAppVo = sendMap(sendMoney,deviceList.get(0).getLklMerchantCode(),deviceList.get(0).getLklTerminalCode(),lklSxf.get(0).getDictBh());
                            if(commAppVo.getStatusCode() == 1){
                                posWithdrawService.updateResult(posWithdrawRecord,commAppVo.getTotalData().get("sendTotalMoney").toString(),commAppVo.getTotalData().get("orderLogNo").toString(),deviceList.get(0).getLklMerchantCode(),deviceList.get(0).getLklTerminalCode());
                                return new JSONResult<PosWithdrawRecord>(null, "下发成功", true);
                            }else{
                                return new JSONResult<PosWithdrawRecord>(null, commAppVo.getMessage(), false);
                            }
                        }else{
                            return new JSONResult<PosWithdrawRecord>(null, "无法找到下发的pos机设备信息", false);
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                return new JSONResult<PosWithdrawRecord>(null, "下发失败", false);
            }
        }else{
            return new JSONResult<PosWithdrawRecord>(null, "下发失败", false);
        }
    }
    /**
     * 删除提现信息
     * @param posWithdrawRecord
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<PosWithdrawRecord> delete(PosWithdrawRecord posWithdrawRecord) {
        if(!StringUtils.isEmpty(posWithdrawRecord.getPid())){
            try{
                PosWithdrawRecord posWithdrawRecord1 = posWithdrawService.getPosWithdrawRecordById(posWithdrawRecord.getPid());
                if(StringUtils.isEmpty(posWithdrawRecord1)){
                    return new JSONResult<PosWithdrawRecord>(null, "无法找到提现记录", false);
                }else{
                    posWithdrawService.deleteSend(posWithdrawRecord.getPid());
                    return new JSONResult<PosWithdrawRecord>(null, "删除成功", true);
                }
            }catch (Exception e){
                e.printStackTrace();
                return new JSONResult<PosWithdrawRecord>(null, "删除失败", false);
            }
        }else{
            return new JSONResult<PosWithdrawRecord>(null, "删除失败", false);
        }
    }

    /**
     * 下发到拉卡拉那边的处理
     * @param sendMoney（下发金额）
     * @param lklMerchantCode (商户号)
     * @param LklTerminalCode (设备终端号)
     * @param lklSxf (手续费)
     * @return
     */
    private CommAppVo sendMap(Integer sendMoney, String lklMerchantCode,String LklTerminalCode,String lklSxf){
        CommAppVo retVo = new CommAppVo();
        String message = "";
        HashMap map = new HashMap();
        try{
            HashMap <Object,Object> map2 = new HashMap<Object,Object>();
            HashMap <Object,Object> map3 = new HashMap<Object,Object>();
            HashMap <Object,Object> map4 = new HashMap<Object,Object>();
            List<HashMap<Object,Object>> lMap = new ArrayList<HashMap<Object,Object>>();

            //对帐传参
            String orderDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String orderTime = new SimpleDateFormat("HHmmss").format(new Date());
            String orderLogNo = "YZ8" + FunUtil.tradeNo();
            String reqLogNo = "100"+FunUtil.tradeNo();
            String shopNo = "822581059630019";
            String termNo = "93801232";

            double yftMoney = Double.parseDouble(String.format("%.2f",sendMoney / 0.85 * Double.parseDouble(lklSxf)));
            double result1 = sendMoney + yftMoney;
            String result = String.format("%.2f", result1);

            map.put("FunCod",CommConstant.funCode);
            map.put("compOrgCode",CommConstant.compOrgCode);
            map.put("reqLogNo",reqLogNo);
            map.put("shopNo", shopNo); // 虚拟商户号(固定值)
            map.put("termNo", termNo); // 终端号(固定值)
            map.put("splitAmt", result); //分账总金额 （用户提现+用户提现*0.15）元
            map.put("tranAmt", "0"); //交易金额
            map.put("redMark", "00"); //垫资标识
            map.put("redAmt", "0"); //垫资金额
            map.put("orderType", "YZF"); //指令类型 固定值
            map.put("orderLogNo", orderLogNo); //流水号
            map.put("orderDate", orderDate); //日期
            map.put("orderTime", orderTime); //时间

            map2.put("sptShopNo", lklMerchantCode); //商户虚拟商户号
            map2.put("sptTermNo", LklTerminalCode); //设备终端号
            map2.put("sptAmt", sendMoney); //提现金额
            map2.put("feeMark", 0); //垫底

            map3.put("sptShopNo", "822581073990342"); //云付通商户号（固定值）
            map3.put("sptTermNo", "93777913"); //设备
            map3.put("sptAmt", yftMoney); //金额
            map3.put("feeMark", 0); //垫底

            map4.put("sptShopNo", shopNo); //拉卡拉总虚拟账户
            map4.put("sptTermNo", termNo); //设备
            map4.put("sptAmt", 0); //金额
            map4.put("feeMark", 0); //垫底

            lMap.add(map2);
            lMap.add(map3);
            lMap.add(map4);

            String splitAmt = result;
            String redMark = "00";
            String redAmt = "0";
            String orderType = "YZF";

            StringBuffer stringBuffer = new StringBuffer();
            //验签
            stringBuffer.append(CommConstant.compOrgCode).append(reqLogNo).append(shopNo).append(termNo)
                .append(splitAmt).append(redMark).append(redAmt).append(orderType)
                .append(orderLogNo).append(orderDate).append(orderTime).append(CommConstant.apilklShaKey);
            String mac = Sha1Util.encryptSHA(stringBuffer.toString());
            map.put("MAC", mac);
            //生成报文
            String body = com.admin.dto.CreateXml.CreateLklMoneySplit(map,lMap);
            System.out.println(body);
            //发送报文
            String retBody = HttpPostClient.getHttpsPostData(CommConstant.apilklSplit, body, "GBK");
            //接口返回信息报文
            HashMap map5 = ReadXml.DealReturnXML(retBody);
            Integer resultMap = (Integer) map5.get("state");
            message = map5.get("message").toString();
            if(resultMap == 1){
                retVo.setStatusCode(1);
            }else{
                retVo.setStatusCode(0);
            }
            JSONObject json = new JSONObject();
            json.put("sendTotalMoney",result);
            json.put("orderLogNo",orderLogNo);
            retVo.setTotalData(json);
        }catch ( Exception e){
            message = "程序异常";
            e.printStackTrace();
            retVo.setStatusCode(0);
        }
        retVo.setMessage(message);
        return  retVo;
    }


    @RequestMapping(value = "showBrand", method = RequestMethod.GET)
    public String showBrand(HttpServletRequest request) {
        return "admin/withdraw/showBrand";
    }

    /**
     * 财务导入提现Excel处理
     * @param model
     * @param file
     * @return
     */
    @RequestMapping(value = "upExcelSendMoneyRead", method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<List<java.util.HashMap>> upExcelSendMoneyRead(RedirectAttributes model, @RequestParam(value = "file1", required = false) MultipartFile file) {
        try {
            //读取excel内容
            String[] fieldArray = {"ID", "MUNBER", "WITHDRAW_TIME", "LOGIN_NAME", "AMOUNT", "BANK_NAME", "BANK_ACCOUNT", "ACCOUNT_NAME", "WITHDRAW_NOTE", "AUDIT_STATUS", "AUDIT_TIME"};
            java.util.List<java.util.HashMap> list = ExportExcelUtil.ReadUpLoadExcel(fieldArray, file.getInputStream());
            if (list.size() > 0) {
                if (list.get(0).size() != fieldArray.length) {
                    return new JSONResult<List<java.util.HashMap>>(null, "导入错误,请检查!", false);
                }
            }
            //批量插入到数据库
            List<java.util.HashMap> messList = new ArrayList<>();
            List<PosWithdrawRecord> lb = new ArrayList<PosWithdrawRecord>();
            if (messList.size() == 0) {
                for (int n = 0; n < list.size(); n++) {
                    PosWithdrawRecord b = new PosWithdrawRecord();
                    HashMap hm = list.get(n);
                    b.setYftNumber(hm.get("MUNBER").toString());
                    b.setAccountName(hm.get("ACCOUNT_NAME").toString());
                    b.setIsSend(0);
                    b.setAuditStatus(1);
                    b.setAmount(Integer.parseInt(hm.get("AMOUNT").toString()));
                    b.setBankAccount(hm.get("BANK_ACCOUNT").toString());
                    b.setBankName(hm.get("BANK_NAME").toString());
                    b.setLoginName(hm.get("LOGIN_NAME").toString());
                    b.setWithdrawNote(hm.get("WITHDRAW_NOTE").toString());
                    b.setAuditTime(hm.get("AUDIT_TIME").toString());
                    b.setWithdrawTime(hm.get("WITHDRAW_TIME").toString());
                    lb.add(n, b);
                }
                posWithdrawService.inserBatchPosWithdraw(lb);
                return new JSONResult<List<java.util.HashMap>>(null, "导入成功", true);
            } else {
                return new JSONResult<List<java.util.HashMap>>(messList, messList.get(0).get("mess").toString(), false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addFlashAttribute("msg", "导入错误！");
        }
        return new JSONResult<List<java.util.HashMap>>(null, "导入错误！", false);
    }

}
