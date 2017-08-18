package com.aten.controller;

import com.aten.model.bean.OrgZtreeVo;
import com.aten.model.orm.Account;
import com.aten.model.orm.AccountBill;
import com.aten.service.OrganizeService;
import com.communal.util.AmountUtil;
import com.communal.util.JsonUtil;
import com.communal.util.R;
import com.communal.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("iframe")
public class IframeController extends BaseController{
    @Autowired
    private OrganizeService organizeService;
    
    
    @RequestMapping("customizedPage/linkTools/{parentId}/{moduleType}")
    public String customizedPageLinkTools(Model model,@PathVariable String parentId,@PathVariable String moduleType) {
        model.addAttribute("parentId",parentId);
        model.addAttribute("moduleType",moduleType);
        return "customizedPage/linkTools";
    }
    @RequestMapping("brandList")
    public String brandList() {
        return "goodsclass/brandList";
    }
    
    @RequestMapping("upList/{acid}")
    public String upList(@PathVariable String acid,Model model) {
    		model.addAttribute("acid",acid);
        return "user/upList";
    }

    @RequestMapping("supplyList")
    public String supplyList() {
        return "goodsclass/supplyList";
    }

    @RequestMapping("appraisalList")
    public String appraisalList() {
        return "goodsclass/appraisalList";
    }

    @RequestMapping("skuAttrList")
    public String skuAttrList() {
        return "goodsclass/skuAttrList";
    }

    @RequestMapping("keyAttrList")
    public String keyAttrList() {
        return "goodsclass/keyAttrList";
    }

    @RequestMapping("goodsActivityList/{activity_type}/{discount:.+}/{activity_id}")
    public String goodsActivityList(Model model, @PathVariable String activity_type, @PathVariable String discount, @PathVariable String activity_id) {
        model.addAttribute("activity_type", activity_type);
        model.addAttribute("discount", discount);
        if (!activity_id.equals("null")) {
            model.addAttribute("activity_id", activity_id);
        }
        //如果是黄金特惠
        if ("2".equals(activity_type)) {
            model.addAttribute("discount_money", discount);
        }
        return "goodsActivity/goodsList";
    }

    @RequestMapping("goodsclassList")
    public String goodsclassList() {
        return "pre/goodsclassList";
    }

    @RequestMapping("goodsList")
    public String goodsList() {
        return "coupon/goodsList";
    }

    @RequestMapping("sendgoods")
    public String sendgoods(HttpServletRequest request, Model model) {
        String order_id = request.getParameter("order_id");
        if (!StringUtil.isBlank(order_id)) {
            model.addAttribute("order_id", order_id);
        }
        return "order/sendgoods";
    }

    @RequestMapping("lookupship")
    public String lookupship(HttpServletRequest request, Model model) {
        String order_number = request.getParameter("order_number");
        if (!StringUtil.isBlank(order_number)) {
            model.addAttribute("order_number", order_number);
        }
        return "order/ship";
    }

    @RequestMapping({"organize/select"})
    public void  select(HttpServletResponse response) {
        List<OrgZtreeVo> list = this.organizeService.getAllOrg();
        String listJson = JsonUtil.object2json(R.ok().put("page", list));
        outPrint(response,listJson);
    }
}
