package com.core.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/3/26.
 */
public class RequestFilter implements Filter {
    private Log log = LogFactory.getLog(RequestFilter.class);
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
        if(request.getRequestURI().indexOf("/admin/")!=-1&&request.getRequestURI().indexOf("login")==-1&&request.getRequestURI().indexOf("doLogin")==-1) {
            String requestType = request.getHeader("X-Requested-With");
//            if (requestType==null) {
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
//            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
