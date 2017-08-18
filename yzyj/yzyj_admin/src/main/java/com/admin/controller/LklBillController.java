package com.admin.controller;

import com.admin.model.BaseExample;
import com.admin.model.TiXian;
import com.admin.model.Txn;
import com.admin.service.LklBillService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.core.entity.JSONResult;
import com.core.mybatis.DataTablesPage;
import com.core.util.CommConstant;
import com.core.util.FtpUtilTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Administrator on 2017-03-31.
 * 拉卡拉账单
 * 提现记录
 * 分账跟入账
 */
@Controller
@RequestMapping(value = "/admin/lklBill")

public class LklBillController {

    @Resource
    private LklBillService lklBillService;

    /**
     * 跳转分账跟入账首页
     * @param model
     * @return
     */
    @RequestMapping(value = "/txnIndex",method = RequestMethod.GET)
    public String txnIndex(Model model) {
        return "admin/check/txnIndex";
    }

    /**
     * 跳转提现记录收益
     * @param model
     * @return
     */
    @RequestMapping(value = "/tixianIndex",method = RequestMethod.GET)
    public String tixianIndex(Model model) {
        return "admin/check/tixianIndex";
    }

    /**
     * 获取分账，入账列表
     * TXN_DATE：分账时间
     * @param page
     * @param model
     * @param txn
     * @return
     */
    @RequestMapping(value = "/getTxnList",method = RequestMethod.GET)
    @ResponseBody
    public String getTxnList(DataTablesPage<Txn> page, Model model, Txn txn) {
        try{
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
            if(!StringUtils.isEmpty(txn.getSdate())){
                criteria.andGreaterThanOrEqualTo("TXN_DATE",txn.getSdate());
            }
            if(!StringUtils.isEmpty(txn.getEdate())){
                criteria.andLessThanOrEqualTo("TXN_DATE",txn.getEdate());
            }
            baseExample.setOrderByClause("TXN_DATE DESC");
            lklBillService.selectTxnByExampleAndPage(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }

        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 获取提现列表
     * TIXIAN_DATE：提现时间
     * @param page
     * @param model
     * @param tiXian
     * @return
     */
    @RequestMapping(value = "/getTiXianList",method = RequestMethod.GET)
    @ResponseBody
    public String getTiXianList(DataTablesPage<TiXian> page, Model model, TiXian tiXian) {
        try{
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
            if(!StringUtils.isEmpty(tiXian.getSdate())){
                criteria.andGreaterThanOrEqualTo("TIXIAN_DATE",tiXian.getSdate());
            }
            if(!StringUtils.isEmpty(tiXian.getEdate())){
                criteria.andLessThanOrEqualTo("TIXIAN_DATE",tiXian.getEdate());
            }
            baseExample.setOrderByClause("TIXIAN_DATE DESC");
            lklBillService.selectTiXianByExampleAndPage(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }

        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 重新载入提现
     * @param page
     * @param model
     * @param tiXian
     * @return
     */
    @RequestMapping(value = "reloadTiXian",method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<TiXian> reloadTiXian(DataTablesPage<TiXian> page, Model model, TiXian tiXian) {
        if(!StringUtils.isEmpty(tiXian.getReloadDay())){
            try{
                BaseExample baseExample = new BaseExample();
                BaseExample.Criteria criteria = baseExample.createCriteria();
                criteria.andEqualTo("TIXIAN_DATE",tiXian.getReloadDay());
                criteria.andEqualTo("1","1");
                TiXian tiXian1 =  lklBillService.getTiXianByDay(baseExample);

                FtpUtilTo ftp = new FtpUtilTo();
                String fileName = CommConstant.file_tixian + tiXian.getReloadDay() + "01.csv";
                String str = ftp.readFile(fileName);
                if(StringUtils.isEmpty(str)){
                    return new JSONResult<TiXian>(null, "FTP没有当天账单!", false);
                }
                String str1[] = str.split(",");
                if(str1.length == 0 || str1.length <= 18){
                    return new JSONResult<TiXian>(null, "当天没有记录，载入失败!", false);
                }else{
                    TiXian tiXian2 = new TiXian();
                    tiXian2.setTixianDate(str1[5]);
                    tiXian2.setTixianCountBs(str1[6]);
                    tiXian2.setTixianMoney(str1[7]);
                    tiXian2.setTixianPtMoney(str1[8]);
                    tiXian2.setTixianShMoney(str1[9]);
                    tiXian2.setUpdateTime(new Date());
                    if(!StringUtils.isEmpty(tiXian1)){
                        lklBillService.updateTixian(tiXian2,tiXian1.getId());
                    }else{
                        lklBillService.insertTiXian(tiXian2);
                    }
                    return new JSONResult<TiXian>(null, "载入成功!", true);
                }
            }catch (Exception e){
                e.printStackTrace();
                return new JSONResult<TiXian>(null, "载入失败!", false);
            }
        }else{
            return new JSONResult<TiXian>(null, "请先选择载入时间,载入失败!", false);
        }
    }

    /**
     * 重新载入分账，入账记录
     * @param page
     * @param model
     * @param txn
     * @return
     */
    @RequestMapping(value = "reloadTxn",method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<Txn> reloadTxn(DataTablesPage<TiXian> page, Model model, Txn txn) {
        if(!StringUtils.isEmpty(txn.getReloadDay())){
            try{
                BaseExample baseExample = new BaseExample();
                BaseExample.Criteria criteria = baseExample.createCriteria();
                criteria.andEqualTo("TXN_DATE",txn.getReloadDay());
                criteria.andEqualTo("1","1");
                Txn txn1 =  lklBillService.getTxnByDay(baseExample);

                FtpUtilTo ftp = new FtpUtilTo();
                String fileName = CommConstant.file_txn + txn.getReloadDay() + "01.csv";
                String str = ftp.readFile(fileName);
                if(StringUtils.isEmpty(str)){
                    return new JSONResult<Txn>(null, "FTP没有当天账单!", false);
                }
                String str1[] = str.split(",");
                if(str1.length == 0 || str1.length <= 18){
                    return new JSONResult<Txn>(null, "当天没有记录，载入失败!", false);
                }else{
                    Txn txn2 = new Txn();
                    txn2.setTxnDate(str1[7]);
                    txn2.setTxnVirtualAccount(str1[8]);
                    txn2.setTxnCountBs(str1[9]);
                    txn2.setTxnJyMoney(str1[10]);
                    txn2.setTxnRzMoney(str1[11]);
                    txn2.setTxnHbMoney(str1[12]);
                    txn2.setTxnBfjMoney(str1[13]);
                    txn2.setUpdateTime(new Date());
                    if(!StringUtils.isEmpty(txn1)){
                        lklBillService.updateTxn(txn2,txn1.getId());
                    }else{
                        lklBillService.insertTxn(txn2);
                    }
                    return new JSONResult<Txn>(null, "载入成功!", true);
                }
            }catch (Exception e){
                e.printStackTrace();
                return new JSONResult<Txn>(null, "载入失败!", false);
            }
        }else{
            return new JSONResult<Txn>(null, "请先选择载入时间,载入失败!", false);
        }
    }

}
