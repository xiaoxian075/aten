package com.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/11.
 */
public class StringUtil {
    public static String convertStreamToString(MultipartFile file) {
        /*
          * To convert the InputStream to String we use the BufferedReader.readLine()
          * method. We iterate until the BufferedReader return null which means
          * there's no more data to read. Each line will appended to a StringBuilder
          * and returned as String.
          */
        StringBuilder sb = new StringBuilder();
        InputStream is = null;
        try {
            is = file.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is!=null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (sb!=null && sb.length()>0)
            sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static Timestamp strToDate(String tsStr) {
        Timestamp ts = null;
       // String tsStr = "2011-05-09 11:49:45";
        try {
            ts = Timestamp.valueOf(tsStr);
        } catch (Exception e) {
            return null;
        }
        return ts;
    }



    private final static String NUMBER_PHONE = "0123456789";    //数字的匹配模式
    public final static boolean isNumeric(String str) {
        if (str==null || str.length()==0)
            return false;
        for (int i=0;i<str.length();i++) {
            if (NUMBER_PHONE.indexOf(str.charAt(i))==-1)
                return false;
        }
        return true;
    }

    public final static boolean isPhone(String str) {
        if (str==null || str.length()==0)
            return false;
        if (NUMBER_PHONE.indexOf(str.charAt(0))==-1) {
            if (str.charAt(0)!='+')
                return false;
        }
        int count = str.length();
        for (int i=1;i<count;i++) {
            if (NUMBER_PHONE.indexOf(str.charAt(i))==-1)
                return false;
        }
        return true;
    }


}
