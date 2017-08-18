package com.aten.test;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import com.communal.util.StringUtil;

import oracle.net.aso.a;
import oracle.net.aso.s;

public class Test003 {    
	public static void main(String[] args) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Calendar calendar = Calendar.getInstance();  
		int now = calendar.get(Calendar.MINUTE);  
		calendar.set(Calendar.MINUTE, now-30);  
		System.out.println(df.format(calendar.getTime()));  
	
	}
    
}    