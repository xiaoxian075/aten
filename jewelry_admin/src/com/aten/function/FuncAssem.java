package com.aten.function;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.aten.model.bean.PageListBean;

/**
 * 公共函数
 * @author chenjx
 * @date	2017-07-19
 */
public class FuncAssem {
	
	/**
	 * 分页对象
	 * @param dpage  从第几条开始取
	 * @param dcount 取几条
	 * @param allcount 总共几条
	 * @author chenjx
	 * @date	2017-07-19
	 */
	public static PageListBean caclPage(int dpage, int dcount, int allcount) {
		if (dpage<=0) {
			dpage=1;
		} else if (dpage%dcount==0)
			dpage = dpage/dcount+1;
		else
			dpage = dpage/dcount+2;
		PageListBean page = new PageListBean();
		page.setCurrent_s(dpage);
		page.setPagesize_s(dcount);
		page.setTotalCount(allcount);
		
		return page;
	}
	
	public static String timeToStr(Timestamp ts) {
		String tsStr = ""; 
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
				//方法一 
				tsStr = sdf.format(ts);
//	            System.out.println(tsStr);   
//	            //方法二   
//	            tsStr = ts.toString();   
//	            System.out.println(tsStr);
		} catch (Exception e) {
				e.printStackTrace();
		}
		
		return tsStr;
	}
	
	//2017年07月5日13点33分
	public static String timeToStr2(Timestamp ts) {
		String tsStr = ""; 
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
				tsStr = sdf.format(ts);
		} catch (Exception e) {
				e.printStackTrace();
		}
		
		return tsStr.substring(0, 4) + "年" +
				tsStr.substring(5,6) + "月" + 
				tsStr.substring(7,8) + "日" + 
				tsStr.substring(9,10) + "点" + 
				tsStr.substring(11,12) + "分";
	}
	
}
