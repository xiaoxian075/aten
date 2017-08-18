package com.aten.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginBaidu {
	
	public static void main(String[] args){
		sss();
		 try { 
			    //String url = "http://www.baidu.com"; 
			    String url = "http://www.jb51.net/"; 
			    java.net.URI uri = java.net.URI.create(url); 
			    // 获取当前系统桌面扩展 
			    java.awt.Desktop dp = java.awt.Desktop.getDesktop(); 
			    // 判断系统桌面是否支持要执行的功能 
			    if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) { 
			     //File file = new File("D:\\aa.txt"); 
			     //dp.edit(file);// 　编辑文件 
			      dp.browse(uri);// 获取系统默认浏览器打开链接 
			     // dp.open(file);// 用默认方式打开文件 
			     // dp.print(file);// 用打印机打印文件 
			    } 
			   } catch (java.lang.NullPointerException e) { 
			    // 此为uri为空时抛出异常 
			    e.printStackTrace(); 
			   } catch (java.io.IOException e) { 
			    // 此为无法获取系统默认浏览器 
			    e.printStackTrace(); 
			   } 
	}
	
	private static void sss(){
		URL url = null;
		  HttpURLConnection httpurlconnection = null;
		  try {
		   url = new URL("http://www.baidu.com/");
		   httpurlconnection = (HttpURLConnection) url.openConnection();
		   httpurlconnection.setRequestProperty("User-Agent",
		     "Internet Explorer");
		   httpurlconnection.setRequestProperty("Host", "www.baidu.com");
		   httpurlconnection.connect();
		   
		   String cookie0 = httpurlconnection.getHeaderField("Set-Cookie");
		   
		   System.out.println(cookie0);//打印出cookie
		   httpurlconnection.disconnect();
		   // String cookie0 =
		   // "BAIDUID=8AF5EA24DBF1275CE15C02B5FF65A265:FG=1;BDSTAT=61a1d3a7118ce8a7ce1b9d16fdfaaf51f3deb48f8e5494eef01f3a292cf5b899;
		   // BDUSE=deleted";
		   url = new URL("http://passport.baidu.com/?login");
		   String strPost = "username=15959371663&password=a55180121&mem_pass=on";
		   httpurlconnection = (HttpURLConnection) url.openConnection();
		   httpurlconnection.setFollowRedirects(true);
		   httpurlconnection.setInstanceFollowRedirects(true);
		   httpurlconnection.setDoOutput(true); // 需要向服务器写数据
		   httpurlconnection.setDoInput(true); // 
		   httpurlconnection.setUseCaches(false); // 获得服务器最新的信息
		   httpurlconnection.setAllowUserInteraction(false);
		   httpurlconnection.setRequestMethod("POST");
		   httpurlconnection
		     .addRequestProperty(
		       "Accept",
		       "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/msword, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/x-silverlight, */*");
		   httpurlconnection
		     .setRequestProperty("Referer",
		       "http://passport.baidu.com/?login&tpl=mn&u=http%3A//www.baidu.com/");
		   httpurlconnection.setRequestProperty("Accept-Language", "zh-cn");
		   httpurlconnection.setRequestProperty("Content-Type",
		     "application/x-www-form-urlencoded");
		   httpurlconnection.setRequestProperty("Accept-Encoding",
		     "gzip, deflate");
		   httpurlconnection
		     .setRequestProperty(
		       "User-Agent",
		       "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Foxy/1; .NET CLR 2.0.50727;MEGAUPLOAD 1.0)");
		   httpurlconnection.setRequestProperty("Host", "passport.baidu.com");
		   httpurlconnection.setRequestProperty("Content-Length", strPost
		     .length()
		     + "");
		   httpurlconnection.setRequestProperty("Connection", "Keep-Alive");
		   httpurlconnection.setRequestProperty("Cache-Control", "no-cache");
		   httpurlconnection.setRequestProperty("Cookie", cookie0);
		   httpurlconnection.getOutputStream().write(strPost.getBytes());
		   httpurlconnection.getOutputStream().flush();
		   httpurlconnection.getOutputStream().close();
		   httpurlconnection.connect();
		   int code = httpurlconnection.getResponseCode();
		   System.out.println("code   " + code);
		   String cookie1 = httpurlconnection.getHeaderField("Set-Cookie");
		   System.out.print(cookie0 + "; " + cookie1);
		   httpurlconnection.disconnect();
		   url = new URL("http://www.baidu.com/");
		   httpurlconnection = (HttpURLConnection) url.openConnection();
		   httpurlconnection.setRequestProperty("User-Agent",
		     "Internet Explorer");
		   httpurlconnection.setRequestProperty("Host", "www.baidu.com");
		   httpurlconnection.setRequestProperty("Cookie", cookie0 + "; "
		     + cookie1);
		   httpurlconnection.connect();
		   InputStream urlStream = httpurlconnection.getInputStream();
		   BufferedInputStream buff = new BufferedInputStream(urlStream);
		   Reader r = new InputStreamReader(buff, "gbk");
		   BufferedReader br = new BufferedReader(r);
		   StringBuffer strHtml = new StringBuffer("");
		   String strLine = null;
		   while ((strLine = br.readLine()) != null) {
		    strHtml.append(strLine + "\r\n");
		   }
		   System.out.print(strHtml.toString());
		  } catch (Exception e) {
		   e.printStackTrace();
		  } finally {
		   if (httpurlconnection != null)
		    httpurlconnection.disconnect();
		  }
		 }
	}

