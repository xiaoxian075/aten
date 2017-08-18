package com.app;

import com.admin.service.InitDataService;
import com.core.entity.JSONResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/10/7.
 */
@Controller
@RequestMapping(value = "/app/dict")
public class AppDictComtroller {
    @Resource
    private InitDataService initDataService;

    @RequestMapping(value = "init", method = RequestMethod.GET)
    @ResponseBody
    public JSONResult<String> init(HttpServletRequest request) {
        try{
            initDataService.InitMemoryData();
            return new JSONResult<String>("OK", "成功", true, 1);
        }catch (Exception e ){

        }
        return new JSONResult<String>("ERROR", "失败", false, 2);
    }
}
