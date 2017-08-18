package com.aten.function;

import com.aten.model.orm.Power;
import com.aten.model.orm.Syslog;
import com.aten.service.PowerService;
import com.aten.service.SyslogService;
import com.communal.constants.Constant;
import com.communal.util.IpUtil;
import com.communal.util.RandomCharUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 陈熠
 * 2017/5/26.
 */
public class SyslogFuc extends SpringContextFuc{

    private static SyslogService syslogService = (SyslogService) getContext().getBean("syslogService");
    private static PowerService powerService = (PowerService) getContext().getBean("powerService");
    
    
    public  void before_url(JoinPoint joinPoint) {
        System.out.println("被拦截方法调用之前调用此方法，输出此语句");
    }
    
    /**
	 * @author linjunqin
	 * @Description 插入操作日志
	 * @param
	 * @date 2017-6-9 上午11:28:55
	 */
    public static void operLog(HttpServletRequest request) {
        String url = request.getRequestURI();//请求地址
        HashMap<String,Object> paraMap = new HashMap<String,Object>();
        paraMap.put("url",url);
        List<Power> powerList= powerService.getList(paraMap);
        String oper_name ="";//操作名称
        if(powerList!=null && powerList.size()>0){
            oper_name = powerList.get(0).getPower_name();
        }
        String targetName = "-";//类名
        String methodName = "-";//方法
        String backName=(String)request.getSession().getAttribute(Constant.USER_NAME);//用户名
        String backId=(String)request.getSession().getAttribute(Constant.USER_ID);;
        String backType=(String)request.getSession().getAttribute(Constant.MANA_TYPE);//类型
        long startTimeMillis = System.currentTimeMillis();//时间
        //操作时间
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTimeMillis);
        System.out.println("方法调用后: " + methodName);
        System.out.println(" 操作人: "+backName+" 操作人类型"+backType+" 操作方法: "+targetName+"-->"+methodName+" 操作时间: "+time );
        //插入数据库
        if (!"".equals(oper_name)&&backId!=null&&backName!=null){
            Syslog syslog=new Syslog();
            syslog.setBack_id(backId);
            syslog.setBack_name(backName);
            syslog.setBack_type(backType);
            syslog.setMethod(url);
            syslog.setContent(oper_name);
            syslog.setSys_layer("0");
            syslog.setIp(IpUtil.getIpAddress(request));
            syslog.setIn_date(time);
            syslogService.insert(syslog);
        }
    }

    /**
     * @Description: 获取request
     * @author fei.lei
     * @date 2016年11月23日 下午5:10
     * @param
     * @return HttpServletRequest
     */
    public HttpServletRequest getHttpServletRequest(){
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes)ra;
        HttpServletRequest request = sra.getRequest();
        return request;
    }


}
