package com.core.filter;

import com.admin.vo.MenuVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2016/3/26.
 */
public class RequestFilter implements Filter {
    protected FilterConfig filterConfig;
    protected boolean enabled;

    /**
     * 构造
     */
    public RequestFilter() {
        filterConfig = null;
        enabled = true;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String value = filterConfig.getInitParameter("enabled");
        if (StringUtils.isEmpty(value)) {
            this.enabled = true;
        } else if (value.equalsIgnoreCase("true")) {
            this.enabled = true;
        } else {
            this.enabled = false;
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //设置request字符编码
        request.setCharacterEncoding("UTF-8");
        //设置response字符编码
        response.setContentType("text/html;charset=UTF-8");
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        String loginUrl=basePath+"/rest/admin/login";

        String url = request.getRequestURI();
        String [] arrKey = {"login","doLogin","logout"};
        if(request.getRequestURI().indexOf("/admin/")!=-1 && checkExistence(url,arrKey) == 1) {
            String requestType = request.getHeader("X-Requested-With");
            if (StringUtils.isEmpty(request.getSession().getAttribute("userInfo"))) {
                response.getWriter().write(
                        "<script language='javascript'>"
                                + "window.top.location.href='"
                                + loginUrl
                                + "';</script>"
                );
                response.getWriter().flush();
                response.getWriter().close();
                request.getInputStream().available();
                return;

            }
            if (request.getRequestURI().indexOf("index")!=-1){
                String menuId=request.getParameter("menuId");
                String menuPid=request.getParameter("menuPid");
                if(!StringUtils.isEmpty(menuId)&&!StringUtils.isEmpty(menuPid)) {
                    request.getSession().setAttribute("menuId", menuId);
                    request.getSession().setAttribute("menuPid", menuPid);
                }
            }
            /**
             * 过滤每个连接 , 检查是否有对应权限
             */

            if(checkLink(request) == 0){
                return ;
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    /**
     * 检查存在
     */
    private Integer checkExistence(String url , String... arrKey){
        Integer state = 1;
        for(String key:arrKey){
            //检查不等于
            if(url.indexOf(key) != -1){
                state ++;
            }
        }
        return state;
    }
    @Override
    public void destroy() {

    }
    private Integer checkLink(HttpServletRequest request){
        Integer state = 0;
        try{

            String url = request.getRequestURI();
            String path = request.getContextPath();
            url = url.substring(path.length()+1,url.length());
            String handleModule  = (String) request.getSession().getAttribute("handleModule");
            if(StringUtils.isEmpty(handleModule)){
                //检查空 , 不进行模块操作验证
                state = 1;
                return state;
            }
//            //检查当前模块是否存在
//            String [] arrModule =  url.split("/");
//            if(arrModule.length>3){
//                String module = arrModule[2];
//                //检查总权限中 , 该模块未配置
//                if(handleModule.indexOf(module) == -1){
//                    state = 1;
//                    return state;
//                }
//            }
            //进行模块操作验证
            if(handleModule.indexOf(url) != -1){
                // 验证成功
                state = 1;
            }else{
                state = 0;
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return state;
    }
}
