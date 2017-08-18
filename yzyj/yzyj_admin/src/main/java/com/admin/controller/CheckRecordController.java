package com.admin.controller;

import com.admin.model.BaseExample;
import com.admin.model.CheckRecord;
import com.admin.service.CheckRecordService;
import com.admin.service.OrderCheckService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.core.entity.JSONResult;
import com.core.mybatis.DataTablesPage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/9.
 * 对账记录
 */
@Controller
@RequestMapping(value = "/admin/check")
public class CheckRecordController {
    @Resource
    private CheckRecordService checkRecordService;
    @Resource
    private OrderCheckService  orderCheckService;

    /**
     * 跳转页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model) {
        return "admin/check/index";
    }

    /**
     * 获取列表
     * CHECK_DAY：结算时间
     * @param page
     * @param model
     * @param checkRecord
     * @return
     */
    @RequestMapping(value = "getList",method = RequestMethod.GET)
    @ResponseBody
    public String getList(DataTablesPage<CheckRecord> page, Model model, CheckRecord checkRecord) {
        try{
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
            if(!StringUtils.isEmpty(checkRecord.getCheckDay())){
                criteria.andEqualTo("CHECK_DAY",checkRecord.getCheckDay());
            }
            baseExample.setOrderByClause("CHECK_DAY DESC");
            checkRecordService.selectByExampleAndPage(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 重新对账
     * @param page
     * @param model
     * @param checkRecord
     * @return
     */
    @RequestMapping(value = "restartCheck",method = RequestMethod.POST)
    @ResponseBody
    public JSONResult<CheckRecord> restartCheck(DataTablesPage<CheckRecord> page, Model model, CheckRecord checkRecord) {
        if(!StringUtils.isEmpty(checkRecord.getCheckDay())){
            try{
                BaseExample baseExample = new BaseExample();
                BaseExample.Criteria criteria = baseExample.createCriteria();
                //数据库对账成功的状态码
                criteria.andEqualTo("T.LKL_CHECK_STATUS","10000");
                criteria.andEqualTo("T.YP_CHECK_STATUS","10010");
                criteria.andEqualTo("T.CHECK_DAY",checkRecord.getCheckDay());
                criteria.andEqualTo("1","1");
                CheckRecord checkRecord1 =  checkRecordService.getCheckRecordByDay(baseExample);
                if(!StringUtils.isEmpty(checkRecord1)){
                    return new JSONResult<CheckRecord>(null, "该日期已通过对账", false);
                }else{
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(checkRecord.getCheckDay());
                    checkRecord.setCheckDayStr(new SimpleDateFormat("yyyyMMdd").format(date));
                    //type未2  表示重新对账
                    checkRecord.setType(2);
                    Integer state = orderCheckService.restartCheck(checkRecord);
                    if(state == 1){
                        return new JSONResult<CheckRecord>(null, "重新对账成功", true);
                    }else{
                        return new JSONResult<CheckRecord>(null, "重新对账失败", false);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                return new JSONResult<CheckRecord>(null, "重新对账失败", false);
            }
        }else{
            return new JSONResult<CheckRecord>(null, "对账参数不过，重新对账失败", false);
        }
    }
}
