package com.communal.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class FunUtil {
    private static final Logger logger = Logger.getLogger(FunUtil.class);

    /**
     * 获取访问者IP
     *
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     *
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request){
        try{
            String ip = request.getHeader("X-Real-IP");
            if (!FunUtil.isNull(ip) && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
            if (!FunUtil.isNull(ip) && !"unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Forwarded-For");
                // 多次反向代理后会有多个IP值，第一个为真实IP。
                int index = ip.indexOf(',');
                if (index != -1) {
                    return ip.substring(0, index);
                } else {
                    return ip;
                }
            } else {
                return request.getRemoteAddr();
            }
        }catch (Exception e){
            logger.error("err",e);
        }
        return "0.0.0.0";
    }

    public static String getRandomCode() {
        String code = "";
        for (int i = 0; i < 6; i++) {
            int c = (int) (Math.random() * 10);
            code += c;
        }
        return code;
    }
    /**
     * Map 字段转 字符串
     */
    public static String mapToString(HashMap map){
        StringBuilder stringBuilder = new StringBuilder();
        try{
            for (Object key : map.keySet()) {
                String val =(String) map.get(key);
                /**
                 * 字符超过150
                 * 生成唯一串
                 * 写入日志
                 */
                if(val != null && val.getBytes().length > 64){
                    String logKey = FunUtil.randNumID();
                    stringBuilder.append(key).append(":").append(logKey).append(",");
                    logger.info(logKey+"&"+key+":"+val);
                }else{
                    stringBuilder.append(key).append(":").append(val).append(",");
                }
            }
        }catch (Exception e ){
            logger.error("mapToString",e);
        }
        return stringBuilder.toString();
    }

    /**
     *金额数字格式化
     */
    public static String formatDouble(Double d){
        DecimalFormat df = new DecimalFormat("########0.00");
        return df.format(d);
    }
    public static boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }
    public static boolean isEmpty(Integer value) {
        if(value == null){
            return true;
        }else{
            return false;
        }
    }
    public static boolean isEmpty(Long value) {
        if(value == null){
            return true;
        }else{
            return false;
        }
    }
    public static String dealStrHide(String str, Integer n){
        try{
            String hideStr = "";
            for (int i =0;i<n;i++){
                hideStr = hideStr + "*";
            }
            if(!org.springframework.util.StringUtils.isEmpty(str)){
                Integer index = (str.length() / 2) - (n/2);
                str = str.substring(0,index) + hideStr +str.substring(index+n,str.length());
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return str;
    }
    /**
     * 单位元值 转换 成 分
     */
    public static Integer yuanTofen(BigDecimal bigDecimal, Integer multiple){
        Integer retInt = 0;
        try{
            BigDecimal bigInt =new BigDecimal(multiple);
            retInt = bigDecimal.multiply(bigInt).intValue();
        }catch (Exception e ){
            logger.error("e",e);
        }
        return retInt;
    }
    /**
     * 单位元值 转换 成 分
     */
    public static String fenToYuan(BigDecimal bd){
        String decimalStr = "";
        try{
            if(bd == null){
                return decimalStr;
            }
            BigDecimal bigDecimal =new BigDecimal(bd.doubleValue());
            bigDecimal = bigDecimal.divide(new BigDecimal(100));
            DecimalFormat df = new DecimalFormat("###.00");
            decimalStr = df.format(bigDecimal).equals(".00")?"0.00":df.format(bigDecimal);
            if(decimalStr.startsWith(".")){
                decimalStr = "0"+decimalStr;
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return decimalStr;
    }

    public static String getMapVal(HashMap map , String key){
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
            String[] arrayStr = key.split("_");
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
    public static String base64Encode(String data) throws IOException {
        if(data == null)
            return "";
        return new BASE64Encoder().encode(data.getBytes());
    }
    public static boolean isNullOrEmpty(Object o){
        return o == null || "".equals(o.toString().trim());
    }

    public static String base64Decode(String data) throws IOException {
        if(isNullOrEmpty(data))
            return null;
        return new String(new sun.misc.BASE64Decoder().decodeBuffer(data));
    }
    /**
     * 删除图片
     */
    public static void delFile(String path){
        try{
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
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    /**
     * 时间比较
     */
    public static int compareDate(Date dt1, Date dt2){
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
    public static void main(String[] ar){
        try{
            delFile("d:/aa/1.aa");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
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
            Calendar fromCal= Calendar.getInstance();
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
    public static Date strDateAddSecond(String inDate, int second, String format) {
        Date retDate = null;
        try
        {
            DateFormat dateFormat=new SimpleDateFormat(format);
            Date date=dateFormat.parse(inDate);
            Calendar fromCal= Calendar.getInstance();
            fromCal.setTime(date);
            fromCal.add(Calendar.SECOND, second);
            retDate = fromCal.getTime();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return retDate;
    }
    public static Date dateAddSecond(Date inDate, int second) {
        Date retDate = null;
        try
        {
            Calendar fromCal= Calendar.getInstance();
            fromCal.setTime(inDate);
            fromCal.add(Calendar.SECOND, second);
            retDate = fromCal.getTime();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return retDate;
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
    public static Date timeStampToDate(Long timestamp, String dateformat) {
        Date date=new Date();
        try {
            DateFormat dateFormat = new SimpleDateFormat(dateformat);
            String dateStr = dateFormat.format(timestamp);
            date = dateFormat.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
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
        String retStr = "";
        String rand = "";
        //产生随机六位数字
        for (int i = 0; i < 6; i++) {
            int j = (int) (Math.random() * (9 - i)) + i;
            rand = rand + String.valueOf(j);
        }
        //时间算到毫秒
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String regdate;
        regdate = formatter.format(new Date());
        retStr = regdate + rand;
        return retStr;
    }
    public static final String randNumID(String format) {
        String retStr = "";
        String rand = "";
        //产生随机六位数字
        for (int i = 0; i < 6; i++) {
            int j = (int) (Math.random() * (9 - i)) + i;
            rand = rand + String.valueOf(j);
        }
        //时间算到毫秒
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat(format);
        String regdate;
        regdate = formatter.format(new Date());
        retStr = regdate + rand;
        return retStr;
    }
    public static double randDouble(final double min, final double max) throws Exception {
        if (max < min) {
            throw new Exception("min < max");
        }
        if (min == max) {
            return min;
        }
        return min + ((max - min) * new Random().nextDouble());
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

    /**
     * 获取前一天
     * @return
     */
    public static String getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1); //得到前一天
        Date date = calendar.getTime();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        return  df.format(date)+","+df1.format(date);
    }

    /**
     * 当前日期格式
     * @param date
     * @return
     */
    public static String fromDateY(Date date) {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        return format1.format(date);
    }

    public static String fromDateH(Date date) {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format1.format(date);
    }

    //获取上周星期六
    public static String getSaturdayOrSunday(String str) {
        //设置当前日期
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(new Date());
        // 取当前日期是星期几(week:星期几)
        int week = aCalendar.get(Calendar.DAY_OF_WEEK);
        if (week == 1) {
            week = 7;
        } else if (week == 0) {
            week = 6;
        } else {
            week -= 1;
        }
        // 取距离当前日期最近的周日与当前日期相差的天数（count：相差的天数。正数：之后的周日，负数：之前的周日）
        int count = 0;
        if (week <= 3) {
            count = -week;
        } else if (week >= 4) {
            count = 7 - week;
        }

        // 获取距离当前日期最近的周日日期
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();

        if(str.equals("Saturday")){
            c.add(Calendar.DAY_OF_WEEK,count-1);//星期六
            return df.format(c.getTime())+","+df1.format(c.getTime());
        }else{
            c.add(Calendar.DAY_OF_WEEK, count);//星期日
            return df.format(c.getTime())+","+df1.format(c.getTime());
        }

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
    public static final Date getSysDateRetDate(String format) {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat(format);
        String regdate;
        regdate = formatter.format(new Date());
        return stringToDateFormat(regdate,format);
    }
    public static final String getSysDate(String format) {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat(format);
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
    public static Long stringToLong(String strval) {
        Long num = 0l;
        try {
            num = Long.parseLong(strval);
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

    public static Date stringToDateFormat(String strdate, String format) {
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

    public static boolean isNull(String str) {
        boolean flag = false;
        if ((str == null) || (str.isEmpty()) || ("".equals(str.trim()))) {
            flag = true;
        }
        return flag;
    }

    /**
	 * @author linjunqin
     * @param <T>
	 * @Description  验证list是否为空
	 * @param
	 * @date 2017年7月4日 下午9:28:17 
	 */
    public static <T> boolean isEmpty(List<T> t){
    	return t==null || t.size()==0 ? true : false;
    }


    public static HashMap<String, Object> jsonDataToHashMap(String object) {
        HashMap<String, Object> data = new LinkedHashMap<String, Object>();
        try{
            data.put("mapCode","1");
            // 将json字符串转换成jsonObject
            JSONObject jsonObject = null;
            try{
                jsonObject = JSON.parseObject(object);
            }catch (Exception e ){
                data.put("mapCode","jsonErr");
                data.put("mapState","902");
                return data;
            }
            //最外层解析
            for(Object key : jsonObject.keySet()){
                Object val = jsonObject.get(key);
                if(val instanceof JSONArray){
                    JSONArray jsonArray = (JSONArray)val;
                    List list = new ArrayList();
                    for (Object o : jsonArray){
                        Map<String, Object> subData = new HashMap<String, Object>();
                        JSONObject sJson = JSON.parseObject(o.toString());
                        Iterator it = sJson.keySet().iterator();
                        // 遍历jsonObject数据，添加到Map对象
                        while (it.hasNext()) {
                            String subKey = String.valueOf(it.next());
                            Object subVal = sJson.get(subKey);
                            subData.put(subKey, subVal);
                        }
                        list.add(subData);
                    }
                    data.put(key.toString(),list);
                }else{
                    data.put(key.toString(), val);
                }
            }
        }catch (Exception e ){
            data.put("mapCode","mapErr");
            data.put("mapState","903");
        }
        return data;
    }
    
    public static String getWeekOfDate(Date date) {
        String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysCode[intWeek];
    }
}
