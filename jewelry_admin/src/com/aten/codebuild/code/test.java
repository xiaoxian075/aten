package com.aten.codebuild.code;

import com.communal.util.FileUtil;
import com.communal.util.StringUtil;

public class test {
	public static void main(String[] args) {
		String incremental_add_field = "note";
		String table_name = "t_brand";
		
		table_name = StringUtil.toUpCaseFirstChar(table_name.replace("t_", ""));
		String up_incremental_add_field = StringUtil.toUpCaseFirstChar(incremental_add_field);
		String model_save_path = Constants.CODE_PATH +"\\model\\orm\\"+table_name+".java";
		String xml_save_path = Constants.CODE_PATH +"\\dao\\mapping"+table_name+"Mapper.xml";
		//save_path = save_path.replace("{DEFINE_CLASS_NAME}", DEFINE_CLASS_NAME);//替换路径
		model_save_path = FileUtil.readTxt(model_save_path);//替换内容
		String incremental_add_field_str = "/*[incremental_add_field]*/";
		model_save_path = model_save_path.replace(incremental_add_field_str, "private String "+incremental_add_field+";\n	"+incremental_add_field_str);
		//替换getset
		String replaceGetSet = FileUtil.readTxt(model_save_path);;
		model_save_path = model_save_path.replace(incremental_add_field_str, "private String "+incremental_add_field+";\n	"+incremental_add_field_str);
		
		
		//最后再替换标签
		//replaceContent = replaceTemContent(replaceContent);
		//FileUtil.writeTxt(save_path,file_name, replaceContent);//写入文件
		//System.out.println(replaceContent);
	}

}
