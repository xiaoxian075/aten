package com.core.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.namespace.QName;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;


public class ServicesTool {
    public ServicesTool() {
    }

    private Object locksock = new Object(); // 用来同步sock操作
    /**
     * Apache Commons Logging 日志处理
     */
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * 公共提交数据,接收返回数据
     *  param sendBodyXML 发送Xml字符
     *  return String
     */

    public String getBodyXML(String urlString, String charsetEncoder) {
        String result = "";
        try {
            synchronized (locksock) {
                log.info("PostURL:" + urlString);
                HttpURLConnection con = (HttpURLConnection)new URL(urlString).
                        openConnection();
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setUseCaches(false);
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.
                        getInputStream(), charsetEncoder));
                String line = null;
                char tagChar[];
                tagChar = new char[1024];
                int len;
                String temp = "";
                while ( (len = reader.read(tagChar)) != -1) {
                    temp = new String(tagChar, 0, len);
                    result += temp;
                    temp = null;
                }
                reader.close();
            }
        }
        catch (MalformedURLException e) {
            result = e.getMessage();
            System.out.println("Unable to connect to RL: " + urlString);
        }
        catch (IOException e) {
            result = e.getMessage();
            System.out.println("IOException when connecting to URL: " //
                    + urlString + e.getMessage());
        }
        return result;
    }

    public String getSendBodyXML(String SendXML, String urlString,
                                 String charsetEncoder) {
        String result = "";
        try {
            synchronized (locksock) {
                log.info("PostURL:" + urlString);
                log.info("SendXML:" + SendXML);
                HttpURLConnection con = (HttpURLConnection)new URL(urlString).
                        openConnection();
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setUseCaches(false);
                con.setRequestProperty("Content-Type", "text/xml");
                con.setRequestMethod("POST");

                OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream(),
                        charsetEncoder);
                out.write(SendXML);
                out.flush();
                out.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(con.
                        getInputStream(), charsetEncoder));
                String line = null;
                char tagChar[];
                tagChar = new char[1024];
                int len;
                String temp = "";
                while ( (len = reader.read(tagChar)) != -1) {
                    temp = new String(tagChar, 0, len);
                    result += temp;
                    temp = null;
                }
                reader.close();
            }
        }
        catch (MalformedURLException e) {
            System.out.println("Unable to connect to RL: " + urlString);
        }
        catch (IOException e) {
            System.out.println("IOException when connecting to URL: " //
                    + urlString + e.getMessage());
        }
        // String s= new String(document.toString().getBytes("GBK"));
        log.info("回应包：" + result);
        return result;
    }

    public String getSendBodyXML(String SendXML, String urlString) {

        String result = "";

        try {
            synchronized (locksock) {
                log.info("PostURL:" + urlString);
                log.info("SendXML:" + SendXML);
                HttpURLConnection con = (HttpURLConnection)new URL(urlString).
                        openConnection();
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setUseCaches(false);
                con.setRequestProperty("Content-Type", "text/xml");
                con.setRequestMethod("POST");
                //8月14日  修改 编码 GB2312 修改成 utf-8 跟来信田水利确定
                OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream(),
                        "utf-8");
                out.write(SendXML);
                out.flush();
                out.close();
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.
                        getInputStream(), "UTF-8"));
                String line = null;
                char tagChar[];
                tagChar = new char[1024];
                int len;
                String temp = "";
                while ( (len = reader.read(tagChar)) != -1) {
                    temp = new String(tagChar, 0, len);
                    result += temp;
                    temp = null;
                }
                reader.close();
            }
        }
        catch (MalformedURLException e) {
            System.out.println("Unable to connect to RL: " + urlString);
        }
        catch (IOException e) {
            System.out.println("IOException when connecting to URL: " //
                    + urlString + e.getMessage());
        }
        return result;
    }

}
