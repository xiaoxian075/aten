package com.core.util;

import java.util.ArrayList;
import java.util.List;
import com.alibaba.druid.util.StringUtils;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;


public class ExportExcelUtil {

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