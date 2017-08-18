package com.communal.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class AmountUtil {
	
    
    /**
	 * @author linjunqin
	 * @Description 金额元转分
	 * @param
	 * @date 2017-5-17 下午4:31:40
	 */
    public static String amountToBranch(String amount) {
    	if(StringUtil.isEmpty(amount)) return "0";
    	BigDecimal bigAmount = new BigDecimal(amount);
    	BigDecimal divisor = new BigDecimal(100);
    	DecimalFormat df =new DecimalFormat("#0.0"); 
    	String newAmount = df.format(bigAmount.multiply(divisor));
    	//截取两位
    	if(newAmount.length()>1){
    		newAmount = newAmount.substring(0,newAmount.length()-2);
    	}
    	return newAmount;
    }
    
	/**
	 * @author linjunqin
	 * @Description 金额分转元
	 * @param
	 * @date 2017-5-17 下午4:37:39
	 */
	public static String amountToPrimary(String amount) {
		if(StringUtil.isEmpty(amount)) return "0";
		BigDecimal bigAmount = new BigDecimal(amount);
		BigDecimal divisor = new BigDecimal(100);
		DecimalFormat df =new DecimalFormat("#0.000");
		String newAmount  = df.format(bigAmount.divide(divisor));
		//截取两位
    	if(newAmount.length()>1){
    		newAmount = newAmount.substring(0,newAmount.length()-1);
    	}
		return newAmount;
	}

    /**
	 * @author chenyi
	 * @Description 金额格视化
	 * @param
	 * @date 2017-5-17 下午4:37:39
	 * 
	 * 
	 */
    public static String formatMoney(String amount) {
    	if(StringUtil.isEmpty(amount)) return "0";
    	BigDecimal bigAmount = new BigDecimal(amount);
    	BigDecimal divisor = new BigDecimal(100);
    	DecimalFormat df =new DecimalFormat("#0.000");
    	String newAmount =  df.format(bigAmount.divide(divisor));
    	//截取两位
    	if(newAmount.length()>1){
    		newAmount = newAmount.substring(0,newAmount.length()-1);
    	}
		String moneyStr=newAmount;
		//截取金额前半部分
		String moneyLeft=moneyStr.substring(0,moneyStr.length()-3);
		//截取金额后半部分
		String moneyRight=moneyStr.substring(moneyStr.length()-3,moneyStr.length());
		//金额格式化
		String formatMoney=DecimalFormat.getNumberInstance().format(Integer.parseInt(moneyLeft));
    	return formatMoney+moneyRight;
    }

	/**
	 * @author chenyi
	 * @Description 余额格视化
	 * @param
	 * @date 2017-5-17 下午4:37:39
	 *
	 *
	 */
	public static String formatCash(String amount) {
		if(StringUtil.isEmpty(amount)) return "0";
		BigDecimal bigAmount = new BigDecimal(amount);
		BigDecimal divisor = new BigDecimal(1);
		DecimalFormat df =new DecimalFormat("#0.000");
		String newAmount =  df.format(bigAmount.divide(divisor));
		//截取两位
		if(newAmount.length()>1){
			newAmount = newAmount.substring(0,newAmount.length()-1);
		}
		String moneyStr=newAmount;
		//截取金额前半部分
		String moneyLeft=moneyStr.substring(0,moneyStr.length()-3);
		//截取金额后半部分
		String moneyRight=moneyStr.substring(moneyStr.length()-3,moneyStr.length());
		//金额格式化
		String formatMoney=DecimalFormat.getNumberInstance().format(Integer.parseInt(moneyLeft));
		return formatMoney+moneyRight;
	}
	
	
	/**  
	 *   
	 * 功能描述：去除字符串首部为"0"字符  
	    
	 * @param str 传入需要转换的字符串  
	 * @return 转换后的字符串  
	 */  
	public static String removeZero(String str){     
	    char  ch;    
	    String result = "";  
	    if(str != null && str.trim().length()>0 && !str.trim().equalsIgnoreCase("null")){                  
	        try{              
	            for(int i=0;i<str.length();i++){  
	                ch = str.charAt(i);  
	                if(ch != '0'){                        
	                    result = str.substring(i);  
	                    break;  
	                }  
	            }  
	        }catch(Exception e){  
	            result = "";  
	        }     
	    }else{  
	        result = "";  
	    }  
	    return result;  
	          
	}  
	/**  
	 *   
	 * 功能描述：金额字符串转换：单位分转成单元  
	    
	 * @param str 传入需要转换的金额字符串  
	 * @return 转换后的金额字符串  
	 */   
	public static String fenToYuan(Object o) {  
	    if(o == null)  
	        return "0.00";  
	    String s = o.toString();  
	    int len = -1;     
	    StringBuilder sb = new StringBuilder();  
	    if (s != null && s.trim().length()>0 && !s.equalsIgnoreCase("null")){  
	        s = removeZero(s);  
	        if (s != null && s.trim().length()>0 && !s.equalsIgnoreCase("null")){  
	            len = s.length();  
	            int tmp = s.indexOf("-");  
	            if(tmp>=0){  
	                if(len==2){  
	                    sb.append("-0.0").append(s.substring(1));  
	                }else if(len==3){  
	                    sb.append("-0.").append(s.substring(1));  
	                }else{  
	                    sb.append(s.substring(0, len-2)).append(".").append(s.substring(len-2));                  
	                }                         
	            }else{  
	                if(len==1){  
	                    sb.append("0.0").append(s);  
	                }else if(len==2){  
	                    sb.append("0.").append(s);  
	                }else{  
	                    sb.append(s.substring(0, len-2)).append(".").append(s.substring(len-2));                  
	                }                     
	            }  
	        }else{  
	            sb.append("0.00");  
	        }  
	    }else{  
	        sb.append("0.00");  
	    }  
	    return sb.toString();         
	}  
	/**  
	 *   
	 * 功能描述：金额字符串转换：单位元转成单分  
	   
	 * @param str 传入需要转换的金额字符串  
	 * @return 转换后的金额字符串  
	 */       
	public static String yuanToFen(Object o) {  
	    if(o == null)  
	        return "0";  
	    String s = o.toString();  
	    int posIndex = -1;  
	    String str = "";  
	    StringBuilder sb = new StringBuilder();  
	    if (s != null && s.trim().length()>0 && !s.equalsIgnoreCase("null")){  
	        posIndex = s.indexOf(".");  
	        if(posIndex>0){  
	            int len = s.length();  
	            if(len == posIndex+1){  
	                str = s.substring(0,posIndex);  
	                if(str == "0"){  
	                    str = "";  
	                }  
	                sb.append(str).append("00");  
	            }else if(len == posIndex+2){  
	                str = s.substring(0,posIndex);  
	                if(str == "0"){  
	                    str = "";  
	                }  
	                sb.append(str).append(s.substring(posIndex+1,posIndex+2)).append("0");  
	            }else if(len == posIndex+3){  
	                str = s.substring(0,posIndex);  
	                if(str == "0"){  
	                    str = "";  
	                }  
	                sb.append(str).append(s.substring(posIndex+1,posIndex+3));  
	            }else{  
	                str = s.substring(0,posIndex);  
	                if(str == "0"){  
	                    str = "";  
	                }  
	                sb.append(str).append(s.substring(posIndex+1,posIndex+3));  
	            }  
	        }else{  
	            sb.append(s).append("00");  
	        }  
	    }else{  
	        sb.append("0");  
	    }  
	    str = removeZero(sb.toString());  
	    if(str != null && str.trim().length()>0 && !str.trim().equalsIgnoreCase("null")){  
	        return str;  
	    }else{  
	        return "0";  
	    }  
	} 
	public static void main(String[] args){
		System.out.println(amountToPrimary("123.97"));
		//System.out.println(formatMoney("123103300.512"));
		//System.out.println(DecimalFormat.getNumberInstance().format(1245600000));
		//System.out.println(DecimalFormat.getNumberInstance().format(Integer.parseInt(formatMoney("1231000.512"))));
	}
    
    
}
