package com.communal.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author linjunqin
 * @Description 鏃ユ湡澶勭悊
 * @param
 * @date 2017-1-9 涓嬪崍2:00:55
 */
public class DateUtil {

	// 鏃堕棿鏍煎紡
	private static SimpleDateFormat SDF_YMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat SDF_Y_M_D = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat SDF_YMDHMS_FNAME = new SimpleDateFormat("yyyyMMddHHmmss");
	private static SimpleDateFormat SDF_YMD = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat SDF_MD = new SimpleDateFormat("MMdd");
	private static SimpleDateFormat SDF_CHINA_MD = new SimpleDateFormat("MM鏈坉d鏃�");

	/**
	 * @author linjunqin
	 * @Description 鑾峰彇YYYY-MM-DD HH:mm:ss鏃堕棿鏍煎紡
	 * @param
	 * @date 2017-1-9 涓嬪崍2:02:37
	 */
	public static String getYmdhms() {
		return SDF_YMDHMS.format(new Date());
	}

	/**
	 * @author linjunqin
	 * @Description: 浠ユ椂闂村懡鍚嶇殑鏂囦欢澶规牸寮�
	 * @param tags
	 * @date 2017-1-31 涓嬪崍3:02:33
	 */
	public static String getYmdhmsFName() {
		return SDF_YMDHMS_FNAME.format(new Date());
	}

	/**
	 * @author linjunqin
	 * @Description 鑾峰彇YYYYMMDD鏃ユ湡鏍煎紡
	 * @param
	 * @date 2017-1-9 涓嬪崍2:02:21
	 */
	public static String getYmd() {
		return SDF_YMD.format(new Date());
	}

	/**
	 * @author linjunqin
	 * @Description: 鑾峰彇MMDD鏃堕棿鏍煎紡
	 * @param tags
	 * @date 2017-1-31 涓嬪崍1:14:19
	 */
	public static String getMd() {
		return SDF_MD.format(new Date());
	}

	/**
	 * @author linjunqin
	 * @Description 鑾峰彇涓枃鐨勬湀鏃ユ牸寮�
	 * @param
	 * @date 2017-6-2 涓嬪崍3:19:23
	 */
	public static String getChinaMd(String dateStr) {
		if (StringUtil.isEmpty(dateStr))
			return "";
		Date date = null;
		try {
			date = SDF_Y_M_D.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SDF_CHINA_MD.format(date);
	}

	/**
	 * @author linjunqin
	 * @Description 鑾峰彇鍓嶅悗鍑犲垎閽熺殑鏃堕棿
	 * @param
	 * @date 2017-3-3 涓嬪崍2:11:14
	 */
	public static String getTimeByMinute(int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, minute);
		return SDF_YMDHMS.format(calendar.getTime());

	}

	/**
	 * @author linjunqin
	 * @Description 鑾峰彇鍓嶅悗鍑犲ぉ鐨勬棩鏈�
	 * @param
	 * @date 2017-3-3 涓嬪崍2:11:14
	 */
	public static String getDateByDay(int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, day);
		return SDF_Y_M_D.format(calendar.getTime());

	}

	/**
	 * @author linjunqin
	 * @Description 鏍规嵁浼犲叆鐨勬椂闂翠覆鏍煎紡鍖栨椂闂�
	 * @param
	 * @date 2017-3-20 涓嬪崍3:50:50
	 */
	public static String getYmdDate(Date date) {
		return SDF_Y_M_D.format(date);
	}

	/**
	 * @author linjunqin
	 * @Description 姣旇緝涓や釜鏃ユ湡鐨勫ぇ灏�
	 * @param
	 * @return 0:琛ㄧず鐩哥瓑 -1:DATE2鍦―ATE1涔嬪墠 1:DATE2鍦―ATE1涔嬪悗
	 * @date 2017-3-13 涓嬪崍4:25:47
	 */
	public static int compareDate(String DATE1, String DATE2) {
		try {
			Date dt1 = SDF_Y_M_D.parse(DATE1);
			Date dt2 = SDF_Y_M_D.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				return -1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return 1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * @author linjunqin
	 * @Description 璺熷綋鍓嶆椂闂存瘮杈冨ぇ灏�
	 * @param
	 * @date 2017-3-13 涓嬪崍4:50:35
	 */
	public static int compareDate(String DATE) {
		return compareDate(String.valueOf(SDF_Y_M_D.format(new Date())), DATE);
	}

	/**
	 * 比较两个时间大小（精确到时分秒）
	 * 
	 * @param timeone
	 *            格式（2017-02-07）
	 * @param timetow
	 *            格式（2017-02-07）
	 * @param type
	 *            (1比较大小有等于 2比较大小没等于)
	 * @return 1(timeone>timetow) -1(timeone<timetow) 2(timeone=timetow) 0错误
	 *         3(timeone>=timetow) -3(timeone<=timetow)
	 */
	public static int compare_dateTime(String timeone, String timetow, int type) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			Date dateone = sdf.parse(timeone);
			Date datetow = sdf.parse(timetow);
			if (type == 1) {
				if (dateone.getTime() >= datetow.getTime()) {
					return 3;
				} else if (dateone.getTime() <= datetow.getTime()) {
					return -3;
				} else if (dateone.getTime() == datetow.getTime()) {
					return 2;
				}
			} else if (type == 2) {
				if (dateone.getTime() > datetow.getTime()) {
					return 1;
				} else if (dateone.getTime() < datetow.getTime()) {
					return -1;
				} else if (dateone.getTime() == datetow.getTime()) {
					return 2;
				}
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 比较两个时间大小（精确到天）
	 * 
	 * @param timeone
	 *            格式（2017-02-07）
	 * @param timetow
	 *            格式（2017-02-07）
	 * @param type
	 *            (1比较大小有等于 2比较大小没等于)
	 * @return 1(timeone>timetow) -1(timeone<timetow) 2(timeone=timetow) 0错误
	 *         3(timeone>=timetow) -3(timeone<=timetow)
	 */
	public static int compare_date(String timeone, String timetow, int type) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dateone = sdf.parse(timeone);
			Date datetow = sdf.parse(timetow);
			if (type == 1) {
				if (dateone.getTime() >= datetow.getTime()) {
					return 3;
				} else if (dateone.getTime() <= datetow.getTime()) {
					return -3;
				} else if (dateone.getTime() == datetow.getTime()) {
					return 2;
				}
			} else if (type == 2) {
				if (dateone.getTime() > datetow.getTime()) {
					return 1;
				} else if (dateone.getTime() < datetow.getTime()) {
					return -1;
				} else if (dateone.getTime() == datetow.getTime()) {
					return 2;
				}
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * @author linjunqin
	 * @Description 时间字符串，转时间戳
	 * @param
	 * @date 2017年8月5日 下午3:49:53
	 */
	public static String getTime(String dateStr) {
		String re_time = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d;
		try {
			d = sdf.parse(dateStr);
			long l = d.getTime();
			String str = String.valueOf(l);
			re_time = str.substring(0, 10);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return re_time;
	}
	
	
	public static int compareDateTime(Date dt1, Date dt2){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return compareDate(dt1,dt2,sdf);
	}
	
	public static int compareDate(Date dt1, Date dt2){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return compareDate(dt1,dt2,sdf);
	}
	
	public static int compareDate(Date dt1, Date dt2,SimpleDateFormat sdf) {
		String dtStr1 = sdf.format(dt1);
		String dtStr2 = sdf.format(dt2);
		return compareDate(dtStr1,dtStr2,sdf);
	}
	
	public static int compareDate(String dtStr1, String dtStr2,SimpleDateFormat sdf) {
		try {
			Date dateone = sdf.parse(dtStr1);
			Date datetow = sdf.parse(dtStr2);
			if (dateone.getTime() > datetow.getTime()) {
				return 1;
			} else if (dateone.getTime() < datetow.getTime()) {
				return -1;
			} else if (dateone.getTime() == datetow.getTime()) {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}
	

	public static void main(String[] args) {
		// System.out.println(getYmdhms());
		// System.out.println(getDateByDay(5));
		// System.out.println(compareDate("2017-03-15"));
		// System.out.println(getYmd("2017-03-06 14:59:57"));
		System.out.println(getChinaMd("2017-03-06"));
	}

}
