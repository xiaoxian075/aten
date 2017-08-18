package com.core.util;

import com.admin.vo.CommAppVo;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * Created by wjf on 2016/10/8.
 */
public class HttpPostClient {

    /**
     * http请求
     * @param postUrl
     * @param data
     * @param charset
     * @return
     */
    public static String getPostData(String postUrl,String data,String charset){
        String res = "";
        try{
            HttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(postUrl);
            StringEntity entity = new StringEntity(data);
            entity.setContentEncoding(charset);
            entity.setContentType("text/html");//设置为 json数据
            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            HttpEntity resEntity = response.getEntity();
            res = EntityUtils.toString(resEntity);
            System. out .println(res);
        }catch (Exception e ){
            e.printStackTrace();
        }
        return res;
    }

    /**
     * https请求
     * @param postUrl
     * @param data
     * @param charset
     * @return
     */
    public static String getHttpsPostData(String postUrl,String data,String charset){
        String result = null;
        try{
            HttpClient httpClient = new SSLClient();
            HttpPost httpPost = new HttpPost(postUrl);
            StringEntity entity = new StringEntity(data);
            entity.setContentEncoding(charset);
            entity.setContentType("text/html");//设置为 json数据
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
            System. out .println(result);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    public static CommAppVo getCommData(String postUrl){
        CommAppVo commAppVo = new CommAppVo();
        try{
            Log.out("postUrl",postUrl);
            HttpClient httpclient=getHttpClient(443);
            HttpPost httpPost = new HttpPost(postUrl);
            HttpResponse response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200){
                String retStr = EntityUtils.toString(response.getEntity(),"utf-8");
                commAppVo.setMessage(retStr);
                commAppVo.setStatusCode(1);
            } else {
                EntityUtils.consume(response.getEntity());
                commAppVo.setStatusCode(2);
                throw new RuntimeException("response status error: " + response.getStatusLine().getStatusCode());
            }
        }catch (Exception e ){
            e.printStackTrace();
            commAppVo.setStatusCode(0);
        }
        return commAppVo;
    }
    public static String getData(String postUrl){
        String retStr = "";
        try{
            Log.out("postUrl",postUrl);
            HttpClient httpclient=getHttpClient(443);
            HttpPost httpPost = new HttpPost(postUrl);
            HttpResponse response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200){
                retStr = EntityUtils.toString(response.getEntity(),"utf-8");

            } else {
                EntityUtils.consume(response.getEntity());
                throw new RuntimeException("response status error: " + response.getStatusLine().getStatusCode());
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return retStr;
    }

    public static String postData(String postUrl, java.util.HashMap map){
        String retStr = "";
        try{
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Iterator iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String,String> entry = (Map.Entry) iter.next();
                String key = entry.getKey();
                String val = entry.getValue();
                nvps.add(new BasicNameValuePair(key, val));
            }


            HttpClient httpclient=getHttpClient(443);
            HttpPost httpPost = new HttpPost(postUrl);
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
            HttpResponse response = httpclient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == 200){
                retStr = EntityUtils.toString(response.getEntity(),"utf-8");
                System.out.println("返回包:"+retStr);
            } else {
                EntityUtils.consume(response.getEntity());
                throw new RuntimeException("response status error: " + response.getStatusLine().getStatusCode());
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return retStr;
    }
    private static HttpClient getHttpClient(int port){
        PoolingClientConnectionManager pcm = new PoolingClientConnectionManager();
        SSLContext ctx=null;
        try{
            ctx = SSLContext.getInstance("TLS");
            X509TrustManager x509=new X509TrustManager(){
                public void checkClientTrusted(X509Certificate[] xcs, String string)
                        throws CertificateException {
                }
                public void checkServerTrusted(X509Certificate[] xcs, String string)
                        throws CertificateException {
                }
                public X509Certificate[] getAcceptedIssuers(){
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{x509}, null);
        }catch(Exception e){
            e.printStackTrace();
        }

        SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Scheme sch = new Scheme("https", port, ssf);
        pcm.getSchemeRegistry().register(sch);
        return new DefaultHttpClient(pcm);
    }

}
