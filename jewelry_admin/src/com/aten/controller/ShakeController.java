/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-08-03 16:37:16  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: ShakeController.java 
 * Author:chenyi
 */
package com.aten.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aten.annotation.TokenAnnotation;
import com.communal.util.StringUtil;
import com.aten.model.orm.Shake;
import com.aten.service.ShakeService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author chenyi
 * @Function 摇一摇活动  controller
 * @date 2017-08-03 16:37:16
 */
@Controller
@RequestMapping("admin/shake")
public class ShakeController extends BaseController {

    private static final Logger logger = Logger.getLogger(ShakeController.class);

    @Autowired
    private ShakeService shakeService;

    /**
     * @param
     * @author chenyi
     * @Description 初始方法
     * @date 2017-08-03 16:37:16
     */
    @ModelAttribute
    public void populateModel(HttpServletRequest request, Model model) {
        initialHiddenVal(request, model);
    }


    /**
     * @param
     * @author chenyi
     * @Description 摇一摇活动列表页方法
     * @date 2017-08-03 16:37:16
     */
    @RequestMapping("list")
    public String list(HttpServletRequest request, Model model) {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        //搜索封装
        paraMap = searchMap(request, paraMap, model);
        int count = this.shakeService.getCount(paraMap);
        //分页工具
        paraMap = pageTool(request, count, model, paraMap);
        List<Shake> shakeList = this.shakeService.getList(paraMap);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long now=new Date().getTime();
        try {
            //设置活动状态
            for (int i=0;i<shakeList.size();i++) {
                Date start = sdf.parse(shakeList.get(i).getStart_time());
                Date end = sdf.parse(shakeList.get(i).getEnd_time());
                if(start.getTime()>now){
                    shakeList.get(i).setShakeState("未开始");
                }
                if(start.getTime()<=now&&end.getTime()>=now){
                    shakeList.get(i).setShakeState("进行中");
                }
                if(end.getTime()<now){
                    shakeList.get(i).setShakeState("已过期");
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        model.addAttribute("shakeList", shakeList);
        return "shake/list";
    }


    /**
     * @param
     * @author chenyi
     * @Description 跳转到添加摇一摇活动页面方法
     * @date 2017-08-03 16:37:16
     */
    @RequestMapping("add")
    @TokenAnnotation(needSaveToken = true)
    public String add(Model model) {
        return "shake/insert";
    }


    /**
     * @param
     * @author chenyi
     * @Description 添加摇一摇活动方法
     * @date 2017-08-03 16:37:16
     */
    @RequestMapping("insert")
    @TokenAnnotation(needRemoveToken = true)
    public String insert(Shake shake, Model model) {
        String result = verifyForm(shake);
        if (!"success".equals(result)) {
            model.addAttribute("promptmsg", result);
            return add(model);
        }
        this.shakeService.insert(shake);
        model.addAttribute("promptmsg", "摇一摇活动添加成功！");
        return add(model);
    }

    /**
     * @param
     * @author chenyi
     * @Description 验证参数
     * @date 2017-08-03 16:37:16
     */
    private String verifyForm(Shake shake) {
        //验证中奖概率 0-100
        //验证是否是正整数
        if (!shake.getEveryone_draw_num().matches("^[0-9]*[1-9][0-9]*$")) {
            return "每人最大中奖次数必须为正整数！";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date start = sdf.parse(shake.getStart_time());
            Date end = sdf.parse(shake.getEnd_time());
//            if (start.getTime() < new Date().getTime()) {
//                return "活动开始时间要大于当前！";
//
//            }
            if (start.getTime() >= end.getTime()) {
                return "结束时间要大于开始时间！";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //验证是否是正整数
        if (!shake.getEveryone_draw_num().matches("^[0-9]*[1-9][0-9]*$")) {
            return "每人最大中奖次数必须为正整数！";
        }
        if (!shake.getDraw_num_day().matches("^[0-9]*[1-9][0-9]*$")) {
            return "每日抽奖次数必须为正整数！";
        }
    /*    if (!shake.getLottery_activity_num().matches("^[0-9]*[1-9][0-9]*$")) {
            return "预计活动参考人数必须为正整数！";
        }*/
        if (!shake.getDraw_out_time().matches("^[0-9]*[1-9][0-9]*$")) {
            return "中奖领取的过期时间必须为正整数！";
        }
        if (!shake.getProbability_winning().matches("^(^[1-9][0-9]$)|(^100&)|(^[1-9]$)$")) {
            return "中奖概率必须是1-99！";
        }
        return "success";

    }


    /**
     * @param
     * @author chenyi
     * @Description 跳转到修改摇一摇活动页面方法
     * @date 2017-08-03 16:37:16
     */
    @RequestMapping("edit")
    @TokenAnnotation(needSaveToken = true)
    public String edit(HttpServletRequest request, Model model) {
        String parameter_id = request.getParameter("parameter_id");
        if (parameter_id == null || parameter_id.equals("")) return list(request, model);
        //获取对象
        Shake shake = this.shakeService.get(parameter_id);
        model.addAttribute("shake", shake);
        return "shake/update";
    }


    /**
     * @param
     * @author chenyi
     * @Description 修改摇一摇活动方法
     * @date 2017-08-03 16:37:16
     */
    @RequestMapping("update")
    @TokenAnnotation(needRemoveToken = true)
    public String update(HttpServletRequest request, Shake shake, Model model) {
        String result = verifyForm(shake);
        if (!"success".equals(result)) {
            model.addAttribute("promptmsg", result);
            return edit(request, model);
        }
        this.shakeService.update(shake);
        model.addAttribute("promptmsg", "摇一摇活动修改成功！");
        return list(request, model);
    }


    /**
     * @param
     * @author chenyi
     * @Description 删除摇一摇活动方法
     * @date 2017-08-03 16:37:16
     */
    @RequestMapping("delete")
    public String delete(HttpServletRequest request, Model model) {
        String parameter_id = request.getParameter("parameter_id");
        if (parameter_id == null || parameter_id.equals("")) return list(request, model);
        this.shakeService.deleteOne(parameter_id);
        model.addAttribute("promptmsg", "摇一摇活动删除成功！");
        return list(request, model);
    }


    /**
     * @param
     * @author chenyi
     * @Description 摇一摇活动排序方法
     * @date 2017-08-03 16:37:16
     */
    @RequestMapping("sort")
    public String sort(HttpServletRequest request, Model model) {
        String sort_id = request.getParameter("sort_id");
        String sort_val = request.getParameter("sort_val");
        if (sort_id == null || sort_val == null) return null;
        // 转成maplist
        List<Map<String, String>> sortMapList = sortStrToMapList(sort_id, sort_val);
        this.shakeService.updateBatch(sortMapList);
        return list(request, model);
    }


    /**
     * @param
     * @author chenyi
     * @Description 摇一摇活动批量删除成功
     * @date 2017-5-3 下午4:09:55
     */
    @RequestMapping("batchDelete")
    public String batchDelete(HttpServletRequest request, Model model) {
        String parameter_id = request.getParameter("parameter_id");
        if (parameter_id == null || parameter_id.equals("")) return list(request, model);
        //转成数组更新
        boolean flag = false;
        String[] ids = parameter_id.split(",");
        for (String id : ids) {
            if (!StringUtil.isEmpty(id)) {
                if (checkBrand(id)) {
                    flag = true;
                } else {
                    this.shakeService.deleteOne(id);
                }
            }
        }
        //判断标志提示
        if (flag) {
            model.addAttribute("promptmsg", "部分摇一摇活动已被引用,被引用的摇一摇活动删除失败！");
        } else {
            model.addAttribute("promptmsg", "摇一摇活动删除成功！");
        }
        return list(request, model);
    }

    /**
     * @param
     * @author chenyi
     * @Description 启用摇一摇活动
     * @date 2017-5-3 下午4:04:28
     */
    @RequestMapping("enableState")
    public String enableState(HttpServletRequest request, Model model) {
        String parameter_id = request.getParameter("parameter_id");
        if (parameter_id == null || parameter_id.equals("")) return list(request, model);
        Shake shake = this.shakeService.get(parameter_id);
        shake.setState("1");
        this.shakeService.update(shake);
        model.addAttribute("promptmsg", "摇一摇活动启用成功！");
        return list(request, model);
    }


    /**
     * @param
     * @author chenyi
     * @Description 禁用摇一摇活动
     * @date 2017-5-3 下午4:04:28
     */
    @RequestMapping("limitState")
    public String limitState(HttpServletRequest request, Model model) {
        String parameter_id = request.getParameter("parameter_id");
        if (parameter_id == null || parameter_id.equals("")) return list(request, model);
        Shake shake = this.shakeService.get(parameter_id);
        shake.setState("0");
        this.shakeService.update(shake);
        model.addAttribute("promptmsg", "摇一摇活动禁用成功！");
        return list(request, model);
    }

    /**
     * @param
     * @author chenyi
     * @Description 批量启用摇一摇活动
     * @date 2017-08-03 16:37:16
     */
    @RequestMapping("batchEnableState")
    public String batchEnableState(HttpServletRequest request, Model model) {
        String parameter_id = request.getParameter("parameter_id");
        if (parameter_id == null || parameter_id.equals("")) return list(request, model);
        //转成数组更新
        String[] ids = parameter_id.split(",");
        for (String id : ids) {
            Shake shake = this.shakeService.get(id);
            shake.setState("1");
            this.shakeService.update(shake);
        }
        model.addAttribute("promptmsg", "摇一摇活动批量启用成功！");
        return list(request, model);
    }


    /**
     * @param
     * @author chenyi
     * @Description 批量禁用摇一摇活动
     * @date 2017-08-03 16:37:16
     */
    @RequestMapping("batchLimitState")
    public String batchLimitState(HttpServletRequest request, Model model) {
        String parameter_id = request.getParameter("parameter_id");
        if (parameter_id == null || parameter_id.equals("")) return list(request, model);
        //转成数组更新
        String[] ids = parameter_id.split(",");
        for (String id : ids) {
            Shake shake = this.shakeService.get(id);
            shake.setState("0");
            this.shakeService.update(shake);
        }
        model.addAttribute("promptmsg", "摇一摇活动批量禁用成功！");
        return list(request, model);
    }


    /**
     * @param
     * @return true 被引用  false 未引用
     * @author chenyi
     * @Description 验证删除摇一摇活动时是否被引用
     * @date 2017-5-9 下午8:53:58
     */
    private boolean checkBrand(String shake_id) {
        return false;
    }


}

