package com.core.single;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * Created by Administrator on 2016/7/15. 物流接口
 */
public class SingleHttpClientUtil {
    private static final Log log = LogFactory
            .getLog(SingleHttpClientUtil.class);
    /**
     * get方式
     *
     * @param com
     * @param no
     * @return
     */
    public static String getSingle(String com, String no) {
        String responseMsg = "";

        // 1.构造HttpClient的实例
        HttpClient httpClient = new HttpClient();

        // 用于测试的http接口的url
        String url= "";
        // 2.创建GetMethod的实例
        GetMethod getMethod = new GetMethod(url);

        // 使用系统系统的默认的恢复策略
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler());

        try {
            //3.执行getMethod,调用http接口
            httpClient.executeMethod(getMethod);

            //4.读取内容
            byte[] responseBody = getMethod.getResponseBody();

            //5.处理返回的内容
            responseMsg = new String(responseBody);
            log.info(responseMsg);

        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //6.释放连接
            getMethod.releaseConnection();
        }
        return responseMsg;
    }
}
