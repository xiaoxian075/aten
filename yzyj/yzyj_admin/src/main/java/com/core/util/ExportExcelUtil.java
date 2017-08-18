package com.core.util;

import com.admin.model.DeviceListExcel;
import com.admin.model.InComeRecordExcel;
import com.admin.model.OrderExcel;
import com.admin.model.PosWithdrawRecordExcel;
import com.alibaba.druid.util.StringUtils;
import jxl.Cell;
import jxl.Sheet;
import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class ExportExcelUtil {
	public static final String exportOrderExcel(String fileName,
			String[] Title, List<OrderExcel> listContent,
			HttpServletResponse response) {
		String result = "系统提示：Excel文件导出成功！";
		try {
			OutputStream os = response.getOutputStream();

			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("GB2312"), "ISO8859-1"));

			response.setContentType("application/msexcel");

			WritableWorkbook workbook = Workbook.createWorkbook(os);

			WritableSheet sheet = workbook.createSheet("Sheet1", 0);

			SheetSettings sheetset = sheet.getSettings();
			sheetset.setProtected(false);

			WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD);

			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
			wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE);
			wcf_center.setAlignment(Alignment.CENTRE);
			wcf_center.setWrap(false);

			WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
			wcf_left.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE);
			wcf_left.setAlignment(Alignment.LEFT);
			wcf_left.setWrap(false);

			WritableCellFormat wcf_heji = new WritableCellFormat(NormalFont);
			wcf_left.setBorder(Border.ALL, BorderLineStyle.THIN);
			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE);
			wcf_left.setAlignment(Alignment.LEFT);
			wcf_left.setWrap(false);

			for (int i = 0; i < Title.length; i++) {
				sheet.addCell(new Label(i, 0, Title[i], wcf_center));
			}

			Field[] fields = null;
			int i = 1;
			for (Object obj:listContent) {
				fields = obj.getClass().getDeclaredFields();
				int j = 0;
				sheet.addCell(new Label(0, i, i+"", wcf_left));
				for (Field v : fields) {
					v.setAccessible(true);
					Object va = v.get(obj);
					if (va == null) {
						va = "";
					}
					sheet.setColumnView(0, 20);
					sheet.setColumnView(1, 40);
					sheet.setColumnView(2, 40);
					sheet.setColumnView(3, 20);
					sheet.setColumnView(4, 20);
					sheet.setColumnView(5, 20);
					sheet.setColumnView(6, 20);
					sheet.setColumnView(7, 20);
					sheet.setColumnView(8, 30);
					sheet.setColumnView(9, 30);
					sheet.setColumnView(10, 20);
					sheet.setColumnView(11, 20);
					sheet.setColumnView(12, 20);
					sheet.setColumnView(13, 20);
					sheet.setColumnView(14, 20);
					sheet.setColumnView(15, 20);
					sheet.setColumnView(16, 30);
					sheet.addCell(new Label(j + 1, i, va.toString(), wcf_left));
					j++;
				}
				i++;
			}
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			result = "系统提示：Excel文件导出失败，原因：" + e.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		return result;
	}

	public static final String exportInComeExcel(String fileName,
												String[] Title, List<InComeRecordExcel> listContent,
												HttpServletResponse response) {
		String result = "系统提示：Excel文件导出成功！";
		try {
			OutputStream os = response.getOutputStream();

			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("GB2312"), "ISO8859-1"));

			response.setContentType("application/msexcel");

			WritableWorkbook workbook = Workbook.createWorkbook(os);

			WritableSheet sheet = workbook.createSheet("Sheet1", 0);

			SheetSettings sheetset = sheet.getSettings();
			sheetset.setProtected(false);

			WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD);

			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
			wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE);
			wcf_center.setAlignment(Alignment.CENTRE);
			wcf_center.setWrap(false);

			WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
			wcf_left.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE);
			wcf_left.setAlignment(Alignment.LEFT);
			wcf_left.setWrap(false);

			WritableCellFormat wcf_heji = new WritableCellFormat(NormalFont);
			wcf_left.setBorder(Border.ALL, BorderLineStyle.THIN);
			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE);
			wcf_left.setAlignment(Alignment.LEFT);
			wcf_left.setWrap(false);

			for (int i = 0; i < Title.length; i++) {
				sheet.addCell(new Label(i, 0, Title[i], wcf_center));
			}

			Field[] fields = null;
			int i = 1;
			for (Object obj:listContent) {
				fields = obj.getClass().getDeclaredFields();
				int j = 0;
				sheet.addCell(new Label(0, i, i+"", wcf_left));
				for (Field v : fields) {
					v.setAccessible(true);
					Object va = v.get(obj);
					if (va == null) {
						va = "";
					}
					sheet.setColumnView(0, 20);
					sheet.setColumnView(1, 20);
					sheet.setColumnView(2, 20);
					sheet.setColumnView(3, 20);
					sheet.setColumnView(4, 20);
					sheet.setColumnView(5, 20);
					sheet.setColumnView(6, 20);
					sheet.addCell(new Label(j + 1, i, va.toString(), wcf_left));
					j++;
				}
				i++;
			}
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			result = "系统提示：Excel文件导出失败，原因：" + e.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		return result;
	}


	public static final String exportSubsidyExcel(String fileName,
												  String[] Title, List<DeviceListExcel> listContent,
												  HttpServletResponse response) {
		String result = "系统提示：Excel文件导出成功！";
		try {
			OutputStream os = response.getOutputStream();

			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("GB2312"), "ISO8859-1"));

			response.setContentType("application/msexcel");

			WritableWorkbook workbook = Workbook.createWorkbook(os);

			WritableSheet sheet = workbook.createSheet("Sheet1", 0);

			SheetSettings sheetset = sheet.getSettings();
			sheetset.setProtected(false);

			WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD);

			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
			wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE);
			wcf_center.setAlignment(Alignment.CENTRE);
			wcf_center.setWrap(false);

			WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
			wcf_left.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE);
			wcf_left.setAlignment(Alignment.LEFT);
			wcf_left.setWrap(false);

			WritableCellFormat wcf_heji = new WritableCellFormat(NormalFont);
			wcf_left.setBorder(Border.ALL, BorderLineStyle.THIN);
			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE);
			wcf_left.setAlignment(Alignment.LEFT);
			wcf_left.setWrap(false);

			for (int i = 0; i < Title.length; i++) {
				sheet.addCell(new Label(i, 0, Title[i], wcf_center));
			}

			Field[] fields = null;
			int i = 1;
			for (Object obj:listContent) {
				fields = obj.getClass().getDeclaredFields();
				int j = 0;
				sheet.addCell(new Label(0, i, i+"", wcf_left));
				for (Field v : fields) {
					v.setAccessible(true);
					Object va = v.get(obj);
					if (va == null) {
						va = "";
					}
					sheet.setColumnView(0, 20);
					sheet.setColumnView(1, 20);
					sheet.setColumnView(2, 20);
					sheet.setColumnView(3, 20);
					sheet.setColumnView(4, 40);
					sheet.setColumnView(5, 20);
					sheet.setColumnView(6, 30);
					sheet.addCell(new Label(j + 1, i, va.toString(), wcf_left));
					j++;
				}
				i++;
			}
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			result = "系统提示：Excel文件导出失败，原因：" + e.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		return result;
	}

	public static final String exportSendExcel(String fileName,
											   String[] Title, List<PosWithdrawRecordExcel> listContent,
											   HttpServletResponse response) {
		String result = "系统提示：Excel文件导出成功！";
		try {
			OutputStream os = response.getOutputStream();

			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("GB2312"), "ISO8859-1"));

			response.setContentType("application/msexcel");

			WritableWorkbook workbook = Workbook.createWorkbook(os);

			WritableSheet sheet = workbook.createSheet("Sheet1", 0);

			SheetSettings sheetset = sheet.getSettings();
			sheetset.setProtected(false);

			WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD);

			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
			wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE);
			wcf_center.setAlignment(Alignment.CENTRE);
			wcf_center.setWrap(false);

			WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
			wcf_left.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE);
			wcf_left.setAlignment(Alignment.LEFT);
			wcf_left.setWrap(false);

			WritableCellFormat wcf_heji = new WritableCellFormat(NormalFont);
			wcf_left.setBorder(Border.ALL, BorderLineStyle.THIN);
			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE);
			wcf_left.setAlignment(Alignment.LEFT);
			wcf_left.setWrap(false);

			for (int i = 0; i < Title.length; i++) {
				sheet.addCell(new Label(i, 0, Title[i], wcf_center));
			}

			Field[] fields = null;
			int i = 1;
			for (Object obj:listContent) {
				fields = obj.getClass().getDeclaredFields();
				int j = 0;
				sheet.addCell(new Label(0, i, i+"", wcf_left));
				for (Field v : fields) {
					v.setAccessible(true);
					Object va = v.get(obj);
					if (va == null) {
						va = "";
					}
					sheet.setColumnView(0, 20);
					sheet.setColumnView(1, 20);
					sheet.setColumnView(2, 20);
					sheet.setColumnView(3, 20);
					sheet.setColumnView(4, 40);
					sheet.setColumnView(5, 20);
					sheet.setColumnView(6, 30);
					sheet.setColumnView(7, 30);
					sheet.setColumnView(8, 30);
					sheet.setColumnView(9, 30);
					sheet.setColumnView(10, 30);
					sheet.setColumnView(11, 40);
					sheet.setColumnView(12, 40);
					sheet.setColumnView(13, 30);
					sheet.addCell(new Label(j + 1, i, va.toString(), wcf_left));
					j++;
				}
				i++;
			}
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			result = "系统提示：Excel文件导出失败，原因：" + e.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 读取上传Excel文件
	 * @param fieldArray
	 * @param is
	 * @return
	 */
	public final static List<java.util.HashMap> ReadUpLoadExcel(String [] fieldArray,java.io.InputStream is){
		List<java.util.HashMap> list = null;
		try{
			list = new ArrayList();
			Workbook workbook = Workbook.getWorkbook(is); // 获得原始文档
			//获取第一张Sheet表
			Sheet readsheet = workbook.getSheet(0);

			//获取Sheet表中所包含的总列数
			int rsColumns = readsheet.getColumns();
			int columnNum = rsColumns - 1;
			while(columnNum > 1){
				Cell cell = readsheet.getCell(columnNum, 0);
				String contents = FunUtil.dealString(cell.getContents());
				if(!StringUtils.isEmpty(contents)){
					break;
				}else{
					columnNum --;
				}
			}
			//获取Sheet表中所包含的总行数
			int rsRows = readsheet.getRows();

			//获取指定单元格的对象引用,跳过第一行标题
			for (int i = 1; i < rsRows; i++)
			{
				java.util.HashMap<String,String> map = new java.util.HashMap();
				int contentsNum = 0;
				for (int j = 0; j <= columnNum; j++)
				{
					Cell cell = readsheet.getCell(j, i);
					//内容null转换, 并去左右空格
					String contents = FunUtil.dealString(cell.getContents());
					if(ArrayVal(fieldArray,j)!= null){
						map.put(fieldArray[j],contents);
					}else{
						map.put("key"+j,contents);
					}
					contentsNum +=contents.length();
				}
				if(contentsNum > 0) {
					list.add(map);
					map = null;
					map = new java.util.HashMap();
				}

			}
		}catch (Exception e ){
			e.printStackTrace();
		}
		return list;
	}

	private static String ArrayVal(String [] arrStr,int index){
		try {
			//不越界
			if(index < arrStr.length ){
				return arrStr[index];
			}else{
				return null;
			}
		}catch (Exception e ){
			e.printStackTrace();
		}
		return null;
	}
}