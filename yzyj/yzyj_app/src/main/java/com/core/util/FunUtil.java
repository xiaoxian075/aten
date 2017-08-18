package com.core.util;

import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class FunUtil {

    public static BigDecimal doubleToBigDecimal(double d){
        BigDecimal bigDecimal = null;
        try{
            bigDecimal =new BigDecimal(d);
            bigDecimal =  bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        }catch (Exception e ){
            e.printStackTrace();
        }
        return bigDecimal;
    }
    public static String signBase64Md5(String desKey,String... arrStr){
        String sign = "";
        try{
            StringBuffer stringBuffer = new StringBuffer();
            for(String str:arrStr){
                stringBuffer.append(str).append("&");
            }
            stringBuffer.append(desKey);
            sign = MD5Util.encodeByMD5Base64(stringBuffer.toString());
        }catch (Exception e ){
            e.printStackTrace();
        }
        return sign;
    }

    public static String sign(java.util.HashMap map,String desKey){
        String sign = "";
        try{
            StringBuffer stringBuffer = new StringBuffer();
            Iterator iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
//                Object key = entry.getKey();
                Object val = entry.getValue();
                stringBuffer.append(val).append("&");
            }
            stringBuffer.append(desKey);
            sign = MD5Util.encodeByMD5(stringBuffer.toString());
        }catch (Exception e ){
            e.printStackTrace();
        }
        return sign;
    }

    public static String getMapVal(HashMap map ,String key){
        String retStr = "";
        try{
            Object obj = map.get(key);
            if(obj != null){
                retStr = obj.toString();
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return retStr;
    }

    public static List<HashMap> formatMapKeyList(List<HashMap> list ){
        List<HashMap> retList = null;
        try{
            retList = new ArrayList<>();
            for (HashMap<String,Object> map:list){
                HashMap<String,Object> newMap =new HashMap();
                for (String key : map.keySet()) {
                    newMap.put(formatMapKeyName(key),map.get(key));

                }
                retList.add(newMap);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return retList;
    }

    /**
     * 格式化map key 名称
     * 应用于 数据库字段名称格式化
     */
    public static String formatMapKeyName(String key){
        String retStr = "";
        try{
            //全部转小写
            key = key.toLowerCase();
            //检查下划线
            String [] arrayStr = key.split("_");
            String newName = "";
            int num = 0;
            for (String str :arrayStr){

                if(num == 0){
                    num ++;
                    newName = str;
                    continue;
                }
                if(str.length() > 1){
                    newName += str.substring(0,1).toUpperCase();
                    newName += str.substring(1,str.length());
                }else{
                    newName+=str;
                }

            }
            retStr = newName;
        }catch (Exception e ){

        }
        return retStr;
    }
    /**
     * 删除图片
     */
    public static void delFile(String path){
        //检查是否为文件
        if(path.indexOf(".") == -1){
            return ;
        }
        File file = new File(path);
        if(file != null){
            if(file.exists()){
                file.delete();
            }
        }
    }

    /**
     * 时间比较
     */
    public static int compareDate(Date dt1,Date dt2){
        int state = 0;
        try{
            if (dt1.getTime() > dt2.getTime()) {
                //System.out.println("dt1 在dt2前");
                state = 1 ;
            } else if (dt1.getTime() < dt2.getTime()) {
                //System.out.println("dt1在dt2后");
                state = -1 ;
            } else {//相等
                state = 0 ;
            }
            dt1 = null;
            dt2 = null;
        }catch (Exception e ){
            e.printStackTrace();
        }
        return state;
    }
//    public static void main(String [] ar){
//        try{
//            delFile("d:/aa/1.aa");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
    /**
     * 时间, 按天数添加
     * @param strDate
     * @param day
     * @return
     */
    public static Date dateDayAdd(String strDate, int day) {
        Date retDate = null;
        try
        {
            Calendar fromCal=Calendar.getInstance();
            DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
            Date date=dateFormat.parse(strDate);
            fromCal.setTime(date);
            fromCal.add(Calendar.DATE, day);
            retDate = fromCal.getTime();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return retDate;
    }
    public static Date dateAddSecond(Date inDate, int second) {
        Date retDate = null;
        try
        {
            Calendar fromCal=Calendar.getInstance();
            fromCal.setTime(inDate);
            fromCal.add(Calendar.SECOND, second);
            retDate = fromCal.getTime();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return retDate;
    }
    public static Date getSysDate(String format) {
        Date retDate = null;
        try{
            DateFormat dateFormat=new SimpleDateFormat(format);
            String regdate = dateFormat.format(new Date());
            retDate=dateFormat.parse(regdate);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return retDate;
    }

    /**
     * 单位元值 转换 成 分
     */
    public static String fenToYuan(Integer num){
        String decimalStr = "";
        try{
            BigDecimal bigDecimal =new BigDecimal(num);
            bigDecimal = bigDecimal.divide(new BigDecimal(100));
            DecimalFormat df = new DecimalFormat("####.00");
            decimalStr = df.format(bigDecimal).equals(".00")?"0.00":df.format(bigDecimal);
            if(decimalStr.startsWith(".")){
                decimalStr = "0"+decimalStr;
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return decimalStr;
    }

    public static String dealString(String str){
        String retStr = "";
        try{
            if(str == null){
                str = "";
            }
            if( "null".equals(str)){
                str = "";
            }
            retStr = str.trim();
        }catch (Exception e ){
            e.printStackTrace();
        }
        return retStr;
    }

    /**
     * 取得当前系统时间字符串
     *
     * @return String
     */

    public static String getFormatDate(Date date, String dateformat) {
        String strdate;
        if(date == null){
            return "";
        }
        if (dateformat == null) {
            dateformat = "";
        }
        if ("".equals(dateformat)) {
            dateformat = "yyyy-MM-dd HH:mm";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
        strdate = sdf.format(date);
        return strdate;
    }

    public static String getFormatDate(String dateformat) {
        String strdate;
        Date date = new Date();
        if (dateformat == null) {
            dateformat = "";
        }
        if ("".equals(dateformat)) {
            dateformat = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
        strdate = sdf.format(date);
        return strdate;
    }


    /**************************************************************
     * 方 法 名：randNumID
     * 名     称： 随机ID
     * ver :1.0
     * 参数说明：
     * <p/>
     * 返 回 值：
     * retStr
     * 作用/用途：
     * 唯一性关键字
     ****************************************************/
    public static final String randNumID() {
        String rand = "";
        //产生随机六位数字
        for (int i = 0; i < 6; i++) {
            int j = (int) (Math.random() * (9 - i)) + i;
            rand = rand + String.valueOf(j);
        }
        //时间算到毫秒
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String regdate = formatter.format(new Date());
        String retStr = regdate + rand;
        return "100" + retStr;
    }

    /**
     * 取得当前系统时间字符串
     *
     * @return String
     */
    public static String getOrderOn(int num) {
        String strdate;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        strdate = sdf.format(date);
        return strdate + getRandom(num);
    }
    public static Integer getRangeRandom(int num){
        int number = new Random().nextInt(num);
        return number;
    }
    public static String getRandom(int num) {
        Set set = new HashSet();
        StringBuffer sb = new StringBuffer("");
        while (true) {
            int i = (int) (Math.random() * num);
            set.add(new Integer(i));
            if (set.size() == num) {
                break;
            }
        }
        Object[] array = set.toArray();
        Collections.shuffle(Arrays.asList(array));
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i].toString());
        }
        return new String(sb);
    }

    public static final String getSysDate() {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        String regdate;
        regdate = formatter.format(new Date());
        return regdate;
    }

    public static final String getSysDateLong() {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String regdate;
        regdate = formatter.format(new Date());
        return regdate;
    }


    public void destroy() {
        //Clean up resources
    }

    public static String creatPathDir(String path) {
        String strPath = path;
        File f = new File(strPath);
        if (f.exists()) {
            return "目录已存在";
        } else {
            f.mkdirs();
        }
        return "DirOK";
    }

    //新建 目录
    public static String creatDir(String strDirName) {
        String strPath = "D:/doc" + strDirName;
        File f = new File(strPath);
        if (f.exists()) {
            return "目录已存在";
//                        f.delete() ;
        } else {
            f.mkdirs();
        }
        return "DirOK";
    }

    public static Integer stringToInteger(String strval) {
        int num = 0;
        try {
            num = Integer.parseInt(strval);
        } catch (Exception e) {

        }
        return num;
    }

    public static String integerTostring(Integer intval) {
        String retStr="";
        try {
            if(intval == null){
                return retStr ;
            }
            retStr = String.valueOf(intval);

        } catch (Exception e) {

        }
        return retStr;
    }
    public static String doubleTostring(Double intval) {
        String retStr="";
        try {
            if(intval == null){
                return retStr ;
            }
            retStr = String.valueOf(intval);

        } catch (Exception e) {

        }
        return retStr;
    }
    public static Double stringToDouble(String strval) {
        Double dval;
        dval = 0.0;
        try {
            dval = Double.parseDouble(strval);
        } catch (Exception e) {

        }
        return dval;
    }

    public static Date stringToDateFormat(String strdate,String format) {
        SimpleDateFormat formatter;
        try {
            if ("".equals(strdate) || strdate == null) {
                return null;
            }
            formatter = new SimpleDateFormat(format);
            Date date = formatter.parse(strdate);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 载入文件
     *
     * @param fileName
     * @return
     */
    public static String loadFile(String fileName) {
        InputStream fis;
        try {
            fis = new FileInputStream(fileName);
            byte[] bs = new byte[fis.available()];
            fis.read(bs);
            String res = new String(bs, "utf8");
            fis.close();
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * base64(MD5)
     *
     * @param str
     * @return
     */
    public static String md5EncryptAndBase64(String str) {
        return encodeBase64(md5Encrypt(str));
    }

    /**
     * 字符串 MD5
     *
     * @param encryptStr
     * @return
     */
    private static byte[] md5Encrypt(String encryptStr) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(encryptStr.getBytes("utf8"));
            return md5.digest();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String encodeBase64(byte[] b) {
        BASE64Encoder base64Encode = new BASE64Encoder();
        String str = base64Encode.encode(b);
        return str;
    }

    //首字母转小写
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    //首字母转大写
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }


    public static void main(String[] args) {
        System.out.println(FunUtil.fenToYuan(5511));
    }

}
