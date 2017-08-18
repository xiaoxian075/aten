package com.admin.controller;

import com.admin.model.BaseExample;
import com.admin.model.InComeRecord;
import com.admin.model.User;
import com.admin.service.CheckRecordService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.core.mybatis.DataTablesPage;
import com.core.util.AppFunUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/11/9.
 * 收益记录
 */
@Controller
@RequestMapping(value = "/admin/income")
public class InComeRecordController {
    @Resource
    private CheckRecordService checkRecordService;

    /**
     * 跳转到首页
     * @param model
     * @return
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model) {return "admin/income/index";}

    /**
     * 获取列表（可根据条件搜索）
     * YUNPAYLOGINNAME：云支付账号
     * CHECKDAY：结算日期
     * REALNAME：真实姓名
     * @param page
     * @param model
     * @param inComeRecord
     * @return
     */
    @RequestMapping(value = "getList",method = RequestMethod.GET)
    @ResponseBody
    public String getList(DataTablesPage<InComeRecord> page, Model model, InComeRecord inComeRecord) {
        try{
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
            //搜索条件
            if(!StringUtils.isEmpty(inComeRecord.getAgentYPLoginName())){
                criteria.andEqualTo("z.YUNPAYLOGINNAME",inComeRecord.getAgentYPLoginName());
            }
            if(inComeRecord.getSdate() != null){
                criteria.andGreaterThanOrEqualTo("to_date(t.CHECKDAY,'yyyy-mm-dd')",inComeRecord.getSdate());
            }
            if(inComeRecord.getEdate() != null){
                criteria.andLessThanOrEqualTo("to_date(t.CHECKDAY,'yyyy-mm-dd')",inComeRecord.getEdate());
            }
            if(!StringUtils.isEmpty(inComeRecord.getAgentName())){
                criteria.andEqualTo("z.REALNAME",inComeRecord.getAgentName());
            }
            if(!StringUtils.isEmpty(inComeRecord.getType())){
                criteria.andEqualTo("t.TYPE",inComeRecord.getType());
            }
            criteria.andEqualTo("1","1");
            baseExample.setOrderByClause("CHECKDAY DESC");
            checkRecordService.selectByExampleAndPage1(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 代理人角色页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/agentIndex",method = RequestMethod.GET)
    public String agentIndex(Model model) {return "admin/income/agentIndex";}

    /**
     * 获取代理人收益列表
     * CHECKDAY：结算日期
     * AGENT_UNIQUE：代理人唯一性来关联表
     * @param request
     * @param page
     * @param model
     * @param inComeRecord
     * @return
     */
    @RequestMapping(value = "getinComeAgentList",method = RequestMethod.GET)
    @ResponseBody
    public String getinComeAgentList(HttpServletRequest request, DataTablesPage<InComeRecord> page, Model model, InComeRecord inComeRecord) {
        try{
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
            User user = AppFunUtil.getUser(request);
            if(inComeRecord.getSdate() != null){
                criteria.andGreaterThanOrEqualTo("to_date(t.CHECKDAY,'yyyy-mm-dd')",inComeRecord.getSdate());
            }
            if(inComeRecord.getEdate() != null){
                criteria.andLessThanOrEqualTo("to_date(t.CHECKDAY,'yyyy-mm-dd')",inComeRecord.getEdate());
            }
            if(!StringUtils.isEmpty(inComeRecord.getType())){
                criteria.andEqualTo("t.TYPE",inComeRecord.getType());
            }
            criteria.andEqualTo("1","1");
            criteria.andEqualTo("t.AGENT_UNIQUE",user.getAgentUnique());
            baseExample.setOrderByClause("CHECKDAY DESC");
            checkRecordService.selectByExampleAndPage1(page,baseExample);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }
}
