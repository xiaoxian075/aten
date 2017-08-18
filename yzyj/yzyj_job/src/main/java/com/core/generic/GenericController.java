package com.core.generic;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2016/7/2.
 */
public class GenericController {
      public void outPrintSuccess(HttpServletResponse response, String msg, Object data) {
        String m = parseJson(true, msg, data);
        outPrint(response, m);
    }
    public void outPrintFail(HttpServletResponse response) {
        outPrintFail(response, "操作失败");
    }
    /**
     * 格式化json
     *
     * @param is
     * @param msg
     * @param data
     * @return
     */
    public String parseJson(boolean is, String msg, Object data) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"is\":");
        sb.append(is);
        if (null != msg && !msg.isEmpty()) {
            sb.append(",\"msg\":");
            sb.append("\"" + msg + "\"");
        }
        if (null != data) {
            sb.append(",\"data\":");
            sb.append(JSON.toJSONString(data));
        }
        sb.append("}");
        return sb.toString();
    }
    /**
     * 输出消息
     *
     * @param response
     * @param msg
     */
    public void outPrint(HttpServletResponse response, String msg) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(msg);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void outPrintFail(HttpServletResponse response, String msg) {
        outPrintFail(response, msg, null);
    }
    public void outPrintFail(HttpServletResponse response, String msg, Object data) {
        String m = parseJson(false, msg, data);
        outPrint(response, m);
    }
}
