package com.admin.controller;

import com.admin.model.*;
import com.admin.service.CheckRecordService;
import com.admin.service.DeviceListService;
import com.admin.service.OrderService;
import com.admin.service.PosWithdrawService;
import com.core.util.ClassInstanceUtil;
import com.core.util.ExportExcelUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/11/15.
 */
@Controller
@RequestMapping(value = "/admin/export")
public class ExportOrderController {
    @Resource
    private OrderService orderService;
    @Resource
    private DeviceListService deviceListService;
    @Resource
    private CheckRecordService checkRecordService;
    @Resource
    private PosWithdrawService posWithdrawService;

    /**
     * 导出订单excel
     * @param response
     * @param order
     * @return
     */
    //后台管理员导出订单Excel
    @RequestMapping(value = "exportOrderExcel",method = RequestMethod.GET)
    public String exportOrderExcel(HttpServletResponse response, Order order) {
        try {
            int type = 1;//后台系统管理员
            if(order.getSdate()!= null){
                String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getSdate());
                order.setStrSdate(s);
            }
            if(order.getEdate() != null){
               String e = new SimpleDateFormat("yyyy-MM-dd").format(order.getEdate())+" 23:59:59";
               order.setStrEdate(e);
            }
            order.setSearchType(type);//管理员
            List<Order> orderList = orderService.searchOrder(order);
            List<OrderExcel> list = new ArrayList<OrderExcel>();
            if (!StringUtils.isEmpty(orderList)) {
                for (Iterator<Order> iterator = orderList.iterator(); iterator.hasNext();) {
                    Order iOrder = (Order) iterator.next();
                    OrderExcel orderExcel = ClassInstanceUtil
                            .createOrderExcelModel(iOrder,type);
                    list.add(orderExcel);
                }
            }

            String[] Title = { "序号", "订单编号", "创建订单时间", "设备编码", "设备名称", "商户名称",
                    "商户云付通帐号", "付款者云付通帐号", "实付金额(元)", "交易卡号", "订单类型", "交易状态",
                    "支付时间", "支付方式","代理人","手续费","终端号"};
            String result = ExportExcelUtil.exportOrderExcel("订单统计情况.xls",
                    Title, list, response);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 代理人导出订单Excel
     * @param response
     * @param order
     * @return
     */
    @RequestMapping(value = "exportAgentOrderExcel",method = RequestMethod.GET)
    public String exportAgentOrderExcel(HttpServletResponse response, Order order) {
        try {
            int type = 2;//代理人
            if(order.getSdate()!= null){
                String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getSdate());
                order.setStrSdate(s);
            }
            if(order.getEdate() != null){
                String e = new SimpleDateFormat("yyyy-MM-dd").format(order.getEdate())+" 23:59:59";
                order.setStrEdate(e);
            }
            order.setAgentUnique(order.getAgentUnique());
            order.setSearchType(type);//代理人
            List<Order> orderList = orderService.searchOrder(order);
            List<OrderExcel> list = new ArrayList<OrderExcel>();
            if (!StringUtils.isEmpty(orderList)) {
                for (Iterator<Order> iterator = orderList.iterator(); iterator.hasNext();) {
                    Order iOrder = (Order) iterator.next();
                    OrderExcel orderExcel = ClassInstanceUtil
                            .createOrderExcelModel(iOrder,type);
                    list.add(orderExcel);
                }
            }

            String[] Title = { "序号", "订单编号", "创建订单时间", "设备编码", "设备名称", "商户名称",
                    "商户云付通帐号", "","实付金额(元)", "","订单类型", "交易状态",
                    "支付时间", "支付方式"};
            String result = ExportExcelUtil.exportOrderExcel("订单统计情况.xls",
                    Title, list, response);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返现的Excel
     * @param response
     * @param deviceList
     * @return
     */
    @RequestMapping(value = "exportSubsidyExcel",method = RequestMethod.GET)
    public String exportSubsidyExcel(HttpServletResponse response, DeviceList deviceList) {
        try {
            List<DeviceList> deviceList1 = deviceListService.searchDeviceList(deviceList);
            List<DeviceListExcel> list = new ArrayList<DeviceListExcel>();
            if (!StringUtils.isEmpty(deviceList1)) {
                for (Iterator<DeviceList> iterator = deviceList1.iterator(); iterator.hasNext();) {
                    DeviceList deviceList2 = (DeviceList) iterator.next();
                    DeviceListExcel deviceListExcel = ClassInstanceUtil
                            .createDeviceListExcelModel(deviceList2);
                    list.add(deviceListExcel);
                }
            }
            String[] Title = { "序号", "代理人","商户号","终端号","商户名称",
                    "商户云付通帐号", "返现时间"};
            String result = ExportExcelUtil.exportSubsidyExcel("返现记录情况.xls",
                    Title, list, response);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 代理人收益的Excel
     * @param response
     * @param inComeRecord
     * @return
     */
    @RequestMapping(value = "exportInComeExcel",method = RequestMethod.GET)
    public String exportInComeExcel(HttpServletResponse response, InComeRecord inComeRecord) {
        try {
            List<InComeRecord> inComeList = checkRecordService.searchInComeList(inComeRecord);
            List<InComeRecordExcel> list = new ArrayList<InComeRecordExcel>();
            if (!StringUtils.isEmpty(inComeList)) {
                for (Iterator<InComeRecord> iterator = inComeList.iterator(); iterator.hasNext();) {
                    InComeRecord inComeRecord2 = (InComeRecord) iterator.next();
                    InComeRecordExcel inComeRecordExcel = ClassInstanceUtil
                            .createInComeExcelModel(inComeRecord2);
                    list.add(inComeRecordExcel);
                }
            }
            String[] Title = { "序号", "收益时间","结算日期","金额(元)","收益人",
                    "云付通帐号", "收益类型"};
            String result = ExportExcelUtil.exportInComeExcel("收益记录情况.xls",
                    Title, list, response);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 提现下发的Excel
     * @param response
     * @param posWithdrawRecord
     * @return
     */
    @RequestMapping(value = "exportSendExcel",method = RequestMethod.GET)
    public String exportSendExcel(HttpServletResponse response, PosWithdrawRecord posWithdrawRecord) {
        try {
            if(posWithdrawRecord.getSdate()!= null){
                String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(posWithdrawRecord.getSdate());
                posWithdrawRecord.setStrSdate(s);
            }
            if(posWithdrawRecord.getEdate() != null){
                String e = new SimpleDateFormat("yyyy-MM-dd").format(posWithdrawRecord.getEdate())+" 23:59:59";
                posWithdrawRecord.setStrEdate(e);
            }

            List<PosWithdrawRecord> posWithdrawRecordList = posWithdrawService.searchSendRecordList(posWithdrawRecord);
            List<PosWithdrawRecordExcel> list = new ArrayList<PosWithdrawRecordExcel>();
            if (!StringUtils.isEmpty(posWithdrawRecordList)) {
                for (Iterator<PosWithdrawRecord> iterator = posWithdrawRecordList.iterator(); iterator.hasNext();) {
                    PosWithdrawRecord posWithdrawRecor2 = (PosWithdrawRecord) iterator.next();
                    PosWithdrawRecordExcel posWithdrawRecordExcel = ClassInstanceUtil.createSendExcelModel(posWithdrawRecor2);
                    list.add(posWithdrawRecordExcel);
                }
            }
            String[] Title = { "序号","云付通提现编号","云付通账号","提现金额(元)","账户号","账号名称",
                    "开户行","云付通提现申请时间","云付通审批时间","下发时间","下发金额(元)","流水号","商户号","终端号"};
            String result = ExportExcelUtil.exportSendExcel("下发列表情况.xls",
                    Title, list, response);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
