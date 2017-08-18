package com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtil {
	
	 public static String sendGet(String url, String param,int timeout) {
	        String result = "";
	        BufferedReader in = null;
	        try {
	            String urlNameString = url;
	            if (param!=null)
	            	urlNameString += "?" + param;
	            URL realUrl = new URL(urlNameString);
	            URLConnection connection = realUrl.openConnection();
	            connection.setConnectTimeout(timeout);
	            connection.setReadTimeout(timeout);
	            connection.setRequestProperty("accept", "*/*");
	            connection.setRequestProperty("connection", "Keep-Alive");
	            connection.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            connection.connect();
	            in = new BufferedReader(new InputStreamReader(
	                    connection.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	        }
	        finally {
	            try {
	                if (in != null) {
	                    in.close();
	                }
	            } catch (Exception e2) {
	                e2.printStackTrace();
	            }
	        }
	        return result;
	    }

	    public static String sendPost(String url, String param,int timeout) {
	        PrintWriter out = null;
	        BufferedReader in = null;
	        String result = null;
	        try {
	            URL realUrl = new URL(url);
	            URLConnection conn = realUrl.openConnection();
	            conn.setConnectTimeout(timeout);
	            conn.setReadTimeout(timeout);
	            conn.setRequestProperty("accept", "*/*");
	            conn.setRequestProperty("connection", "Keep-Alive");
	            conn.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            
	            conn.setRequestProperty("Accept-Charset","UTF-8");
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            out = new PrintWriter(conn.getOutputStream());
	            out.print(param);
	            out.flush();
	            in = new BufferedReader(
	                    new InputStreamReader(conn.getInputStream()));
	            String line;
	            result = "";
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	        	result = null;
	        }
	        finally {
	            try {
	                if (out != null) {
	                    out.close();
	                }
	                if (in != null) {
	                    in.close();
	                }
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }
	        return result;
	    }
}
