package com.aten.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aten.codebuild.bean.CodeBuildBean;
import com.aten.codebuild.bean.FileBean;
import com.aten.codebuild.bean.MethodBean;
import com.aten.codebuild.bean.TableField;
import com.aten.codebuild.bean.TxtStringBean;
import com.aten.codebuild.code.Constants;
import com.aten.codebuild.code.DbCodeUtil;
import com.communal.util.FileUtil;
import com.communal.util.StringUtil;


/**
 * @author linjunqin
 * @Description 代码生成管理
 * @param
 * @date 2016-12-31 上午8:44:12
 */
@Controller
@RequestMapping("/")
public class CodeBuildController extends BaseController{

	private Logger logger = Logger.getLogger(this.getClass());
	
	
	private String CLASS_NAME;
	private String DEFINE_CLASS_NAME;
	private String URL_NAME;
	private String tip_msg;
	private String field_note ;
	private String field_name ;
	private String fieldName;
	private String field_type ;
	private String field_length ;
	private String data_type ;
	private String default_value;
	private String MODEL;
	private String FUNCTION;
	private String is_must ;
	
	private String add_edit_field;
	private String add_edit_field_sort ;
	private String show_list_field;
	private String show_list_field_sort ;
	private String search_list_field ;
	private String search_list_field_sort ;
	private String project_path;
	private String nowDate;
	private String TABLENAME;
	private String TABLEKEY;
	
	private MethodBean mb;
	
	/**
	 * @author linjunqin
	 * @Description 跳转系统菜单列表页
	 * @param
	 * @date 2016-12-31 上午8:44:08
	 */
	@RequestMapping("codepage")
	public String codepage(){
		return "codebuild";
	}
	
	@RequestMapping("dbSelect")
	public String dbSelect(Model model){
		HttpServletRequest request = this.getRequest();
		String table_name = request.getParameter("table_name");
		String table_key = request.getParameter("table_key");
		if(table_name==null) return codepage();
		List<HashMap<String,String>> tbList = DbCodeUtil.getTableMsgByTableName(table_name);
		System.out.println(tbList);
		model.addAttribute("tbList", tbList);
		model.addAttribute("table_name", table_name);
		model.addAttribute("table_key", table_key);

		return "codebuild";
	}
	
	/**
	 * @author linjunqin
	 * @Description: 生成代码
	 * @param tags
	 * @date 2017-1-23 上午1:25:17
	 */
	@RequestMapping("codeBuild")
	public String codeBuild(CodeBuildBean cbb,FileBean fb,MethodBean methodBean,Model model){
		HttpServletRequest request = this.getRequest();
		//初始数据
		CLASS_NAME = cbb.getClass_name();
		DEFINE_CLASS_NAME = StringUtil.toLowerCaseFirstChar(CLASS_NAME);//首字母小写
		URL_NAME = CLASS_NAME.toLowerCase();//全部小写
		MODEL = this.getRequest().getParameter("model");
		FUNCTION = this.getRequest().getParameter("function");
		nowDate = getDate();
		TABLENAME = request.getParameter("table_name").toLowerCase();
		TABLEKEY = request.getParameter("table_key").toLowerCase();
		//方法bean
		mb = methodBean;
		if(mb==null) mb =new MethodBean();
		//文件bean
		if(fb==null) fb = new FileBean();
		//创建共用页面
		if(fb.getFile_common_page()!=null && fb.getFile_common_page().equals("yes")){
			createFile(Constants.WEB_COMMON_TEM_PATH,Constants.WEB_COMMON_SAVE_PATH,"common.jsp",cbb);
		}
		//创建添加页面
		if(fb.getFile_insert_page()!=null && fb.getFile_insert_page().equals("yes")){
			createFile(Constants.WEB_INSERT_TEM_PATH,Constants.WEB_INSERT_SAVE_PATH,"insert.jsp",cbb);
		}
		//创建修改页面
		if(fb.getFile_update_page()!=null && fb.getFile_update_page().equals("yes")){
			createFile(Constants.WEB_UPDATE_TEM_PATH,Constants.WEB_UPDATE_SAVE_PATH,"update.jsp",cbb);
		}
		//创建列表页面
		if(fb.getFile_list_page()!=null && fb.getFile_list_page().equals("yes")){
			createFile(Constants.WEB_LIST_TEM_PATH,Constants.WEB_LIST_SAVE_PATH,"list.jsp",cbb);
		}
		//创建model 类
		if(fb.getFile_model()!=null && fb.getFile_model().equals("yes")){
			createFile(Constants.ORM_TEM_PATH,Constants.ORM_SAVE_PATH,CLASS_NAME+".java",cbb);
		}
		//创建mybaits sql文件 
		if(fb.getFile_sql()!=null && fb.getFile_sql().equals("yes")){
			createFile(Constants.XML_TEM_PAth,Constants.XML_SAVE_PAth,CLASS_NAME+"Mapper.xml",cbb);
		}
		//创建controller 类
		if(fb.getFile_controller()!=null && fb.getFile_controller().equals("yes")){
			createFile(Constants.CONTROLLER_TEM_PATH,Constants.CONTROLLER_SAVE_PATH,CLASS_NAME+"Controller.java",cbb);
		}
		//创建sevice文件 
		if(fb.getFile_service()!=null && fb.getFile_service().equals("yes")){
			createFile(Constants.SERVICE_TEM_PATH,Constants.SERVICE_SAVE_PATH,CLASS_NAME+"Service.java",cbb);
		}
		//创建sevice 实现类文件 
		if(fb.getFile_service_impl()!=null && fb.getFile_service_impl().equals("yes")){
			createFile(Constants.SERVICEIMPL_TEM_PATH,Constants.SERVICEIMPL_SAVE_PATH,CLASS_NAME+"ServiceImpl.java",cbb);
		}
		//创建dao文件 
		if(fb.getFile_dao()!=null && fb.getFile_dao().equals("yes")){
			createFile(Constants.DAO_TEM_PATH,Constants.DAO_SAVE_PATH,CLASS_NAME+"Dao.java",cbb);
		}
		return dbSelect(model);
	}
	
	/**
	 * @author linjunqin
	 * @Description: 生成文件
	 * @param tags
	 * @date 2017-1-23 上午1:29:43
	 */
	private void createFile(String tem_path,String save_path,String file_name,CodeBuildBean cbb){
		save_path = save_path.replace("{DEFINE_CLASS_NAME}", DEFINE_CLASS_NAME);//替换路径
		String  replaceContent =replaceTemplate(tem_path,cbb);//替换内容
		FileUtil.writeTxt(save_path,file_name, replaceContent);//写入文件
	}
	
	/**
	 * @author linjunqin
	 * @Description 更新文件
	 * @param
	 * @date 2017-7-3 上午8:23:57
	 */
	private void updateFile(String tem_path,String save_path,String file_name,CodeBuildBean cbb){
		save_path = save_path.replace("{DEFINE_CLASS_NAME}", DEFINE_CLASS_NAME);//替换路径
		String  replaceContent = FileUtil.readTxt(save_path);//替换内容
		//最后再替换标签
		replaceContent = replaceTemContent(replaceContent);
		FileUtil.writeTxt(save_path,file_name, replaceContent);//写入文件
	}
	
	/**
	 * @author linjunqin
	 * @Description: 替换模板的
	 * @param tags
	 * @date 2017-1-23 上午1:19:22
	 */
	private String replaceTemplate(String tem_path_name,CodeBuildBean cbb){
		
		boolean is_img_control=false;//页面是否有图片控件
		boolean is_ueditor=false;//页面是否有百度编辑器
		//循环列表找出是否有需要引用文件的内容
		List<TableField> tableFieldList = cbb.getTableFieldList();
		for(int i=0;i<tableFieldList.size();i++){
			TableField tField = tableFieldList.get(i);
			String field_type = tField.getField_type();
			if(field_type!=null && field_type.equals("imgcontrol")) is_img_control=true;
			if(field_type!=null && field_type.equals("com/baidu/ueditor")) is_ueditor=true;
		}
		
		//获取模板中的内容
		String temPathName = tem_path_name;
		String fileCon = FileUtil.readTxt(temPathName);
		//解析添加,修改公用页面内容
		if(fileCon.indexOf("{component}")>-1){
			String temString = replaceWebContent(cbb);
			//替换页面组件
			fileCon = fileCon.replace("{component}", temString);
		}
		if(fileCon.indexOf("{fieldlist}")>-1){
			//替换列表
			fileCon=recursionFieldList(fileCon,cbb,"fieldlist");
		}
		if(fileCon.indexOf("{refer_img_control}")>-1){//引用组件js,css等
			TxtStringBean txb = getTagContent(fileCon,"refer_img_control");
			if(is_img_control){
				fileCon = fileCon.replace(txb.getTagBody(), txb.getTagContent());
			}else{
				fileCon = fileCon.replace(txb.getTagBody(), "");
			}
		}
		if(fileCon.indexOf("{refer_ueditor}")>-1){//引用组件js,css等
			TxtStringBean txb = getTagContent(fileCon,"refer_ueditor");
			if(is_ueditor){
				fileCon = fileCon.replace(txb.getTagBody(), txb.getTagContent());
			}else{
				fileCon = fileCon.replace(txb.getTagBody(), "");
			}
		}
		if(fileCon.indexOf("{listpage_field}")>-1){//
			fileCon = recursionListPageField(fileCon, cbb,"listpage_field");
		}
		//最后再替换标签
		fileCon = replaceTemContent(fileCon);
		return fileCon;
	}
	
	/**
	 * @author linjunqin
	 * @Description: TODO
	 * @param tags
	 * @date 2017-1-30 下午12:00:21
	 */
	private String recursionListPageField(String fileCon,CodeBuildBean cbb,String tagName){
		TxtStringBean txb = getTagContent(fileCon,tagName);
		String tagBody = txb.getTagBody();//标签体
		String tagContent = txb.getTagContent();//标签内容
		String bodyContent = txb.getBodyContent();
		String tagGroupContent ="";
		List<TableField> tableFieldList = cbb.getTableFieldList();
		//列表排序
		//tableFieldList =ListSortUtil.sort(tableFieldList, "show_list_field_sort", "desc");
		//循环列表处理
		for(int i=0;i<tableFieldList.size();i++){
			TableField tField = tableFieldList.get(i);
			if(tField.getShow_list_field()!=null && tField.getSearch_list_field().equals("yes")){
				//初始值
				setValue(tField);
				//替换后内容
				String replaceContent = replaceTemContent(tagContent);
				tagGroupContent+=replaceContent;
			}
		}
		bodyContent=bodyContent.replace(tagBody, tagGroupContent);
		if(bodyContent.indexOf("{"+tagName+"}")>0){
			bodyContent=recursionListPageField(bodyContent,cbb,tagName);
		}
		return bodyContent;
	}
	
	/**
	 * @author linjunqin
	 * @Description: 替换添加，更新公用页面组件的列表
	 * @param tags
	 * @date 2017-1-23 下午1:04:54
	 */
	public String replaceWebContent(CodeBuildBean cbb){
		String temString="";
		List<TableField> tableFieldList = cbb.getTableFieldList();
		//列表排序
		//tableFieldList =ListSortUtil.sort(tableFieldList, "add_edit_field_sort", "desc");
		//隐藏域组件内容
		String hiddenStrComtent="";
		//其它组件组合
		String comStrComtent="";
		//循环列表处理
		for(int i=0;i<tableFieldList.size();i++){
			TableField tField = tableFieldList.get(i);
			setValue(tField);
			//获取组件模板路径
			String componentPath = Constants.COMPONENT_TEM_PAth;
			//组件生成内容
			String comComtent="";
			//根据字段类型开始判断
			//文本框
			if(field_type.equals("text")){
				componentPath+="text.txt";//模板文件路径
				comComtent = FileUtil.readTxt(componentPath);
				//select框
			}else if(field_type.equals("select")){
				componentPath+="select.txt";//模板文件路径
				comComtent = FileUtil.readTxt(componentPath);
				if(default_value!=null && !default_value.equals("")){
					String[] key_values_str = default_value.split("\\|");
					//获取select标签体内容
					TxtStringBean txb = getTagContent(comComtent,"optionlist");
					String bodyContent = txb.getBodyContent();
					String tagBody = txb.getTagBody();
					String tagContent = txb.getTagContent();
					String optionString="";
					//option的值
					for(int j=0;j<key_values_str.length;j++){
						String option="";
						String[] key_values = key_values_str[j].split(":");
						if(key_values.length==2){
							String key = key_values[0];
							String value = key_values[1];
							//替换默认值
							option=replaceTemContent(tagContent);
							option=option.replace("[key]",key).replace("[value]", value);
						}
						optionString+=option+"\n";
					}
					//替换标识
					bodyContent=bodyContent.replace(tagBody, optionString);
					comComtent=bodyContent;
				}else{
					TxtStringBean txb = getTagContent(comComtent,"optionlist");
					comComtent=comComtent.replace(txb.getTagBody(), "");
				}
				//隐藏域
			}else if(field_type.equals("textarea")){
				componentPath+="textarea.txt";//模板文件路径
				comComtent = FileUtil.readTxt(componentPath);
				//图片控件
			}else if(field_type.equals("imgcontrol")){
				componentPath+="imgcontrol.txt";//模板文件路径
				comComtent = FileUtil.readTxt(componentPath);
				//编辑器
			}else if(field_type.equals("com/baidu/ueditor")){
				componentPath+="com.baidu.ueditor.txt";//模板文件路径
				comComtent = FileUtil.readTxt(componentPath);
				//隐藏域
			}else if(field_type.equals("hidden")){
				componentPath+="hidden.txt";//模板文件路径
				comComtent= FileUtil.readTxt(componentPath);
				//表主键
			}else if(field_type.equals("tbkey")){
				componentPath+="tbkey.txt";//模板文件路径
				comComtent= FileUtil.readTxt(componentPath);
			}
			//判断这个字段是否显示在公用页面中
			if(add_edit_field!=null && add_edit_field.equals("yes")){
				//拼接成串
				if(field_type.equals("hidden") || field_type.equals("tbkey")){
					hiddenStrComtent += replaceTemContent(comComtent);
				}else{
					comStrComtent += replaceTemContent(comComtent);
				}
			}
		}
		temString=comStrComtent+ hiddenStrComtent;
		return temString;
	}
	
	//替换内容方法
	private String replaceTemContent(String temContent){
		if(temContent==null || temContent.equals("")) return ""; 
		//类名
		if(CLASS_NAME!=null){
			temContent = temContent.replace("{CLASS_NAME}", CLASS_NAME);
		}
		//定义实例名
		if(DEFINE_CLASS_NAME!=null){
			temContent = temContent.replace("{DEFINE_CLASS_NAME}", DEFINE_CLASS_NAME);
		}
		//url请求名称
		if(URL_NAME!=null){
			temContent = temContent.replace("{URL_NAME}", URL_NAME);
		}
		//替换时间
		if(nowDate!=null){
			temContent = temContent.replace("{ydt}", nowDate);
		}
		//替换功能描述
		if(FUNCTION!=null){
			temContent = temContent.replace("{FUNCTION}", FUNCTION);
		}
		//替换表名
		if(TABLENAME!=null){
			temContent = temContent.replace("{TABLENAME}", TABLENAME);
		}
		//替换表主键
		if(TABLEKEY!=null){
			temContent = temContent.replace("{TABLEKEY}", TABLEKEY);
		}
		//替换作者
		temContent = temContent.replace("{AUTHOR}", Constants.AUTHOR);
		//替换模块名称
		if(MODEL!=null){
			temContent = temContent.replace("{MODEL}",MODEL);
		}
		//替换名称类型
		if(field_note!=null && !field_note.equals("")){
			temContent = temContent.replace("[field_note]", field_note);
		}else{
			temContent = temContent.replace("[field_note]", "替换名称");
		}
		//替换字段
		if(field_name!=null){
			temContent = temContent.replace("[field_name]", field_name);
		}else{
			temContent = temContent.replace("[field_name]", "");
		}
		//替换首字母大写字段
		if(fieldName!=null){
			temContent = temContent.replace("[fieldName]", fieldName);
		}else{
			temContent = temContent.replace("[fieldName]", "");
		}
		//替换字段类型
		if(field_type!=null){
			temContent = temContent.replace("[field_type]", field_type);
		}else{
			temContent = temContent.replace("[field_type]", "");
		}
		//替换字段长度
		if(field_length!=null &&!field_length.trim().equals("")){
			temContent = temContent.replace("[field_length]", "maxlength='"+field_length+"'  maxdatalength='"+field_length+"'");
		}else{
			temContent = temContent.replace("[field_length]", "");
		}
		//是否必填
		if(is_must!=null && is_must.equals("yes")){
			temContent = temContent.replace("[is_must]", "isrequired='yes'");
			temContent = temContent.replace("[validate]", "validate");
			temContent = temContent.replace("[is_must_span]", "<span class=\"must_span\">*</span>");
		}else{
			temContent = temContent.replace("[is_must]","");
			temContent = temContent.replace("[validate]","");
			temContent = temContent.replace("[is_must_span]", "<span class=\"sp_span\">:</span>");
		}
		//是否验证数据类型
		if(data_type!=null && !data_type.equals("")){
			temContent = temContent.replace("[validate]", "validate");
			if(data_type.equals("rmb")){
				 temContent = temContent.replace("[data_type]", " datatype='jsRmb'");
			}
			if(data_type.equals("int")){
				 temContent = temContent.replace("[data_type]", " datatype='jsInt'");
			}
		}else{
			temContent = temContent.replace("[data_type]","");
		}
		//替换提示信息
		if(tip_msg!=null && !tip_msg.equals("")){
			temContent = temContent.replace("[tip_msg]", tip_msg);
		}else{
			temContent = temContent.replace("[tip_msg]","");
		}

		//替换列表方法
		if(temContent.indexOf("{method_list}")>-1){
			TxtStringBean txb = getTagContent(temContent,"method_list");
			if(mb.getMethod_list()!=null && mb.getMethod_list().equals("yes")){
				temContent = temContent.replace(txb.getTagBody(), txb.getTagContent());
			}else{
				temContent = temContent.replace(txb.getTagBody(), "");
			}
		}
		//替换添加方法
		if(temContent.indexOf("{method_insert}")>-1){
			TxtStringBean txb = getTagContent(temContent,"method_insert");
			if(mb.getMethod_insert()!=null && mb.getMethod_insert().equals("yes")){
				temContent = temContent.replace(txb.getTagBody(), txb.getTagContent());
			}else{
				temContent = temContent.replace(txb.getTagBody(), "");
			}
		}
		//替换修改方法
		if(temContent.indexOf("{method_update}")>-1){
			TxtStringBean txb = getTagContent(temContent,"method_update");
			if(mb.getMethod_update()!=null && mb.getMethod_update().equals("yes")){
				temContent = temContent.replace(txb.getTagBody(), txb.getTagContent());
			}else{
				temContent = temContent.replace(txb.getTagBody(), "");
			}
		}
		//替换删除方法
		if(temContent.indexOf("{method_delete}")>-1){
			TxtStringBean txb = getTagContent(temContent,"method_delete");
			if(mb.getMethod_delete()!=null && mb.getMethod_delete().equals("yes")){
				temContent = temContent.replace(txb.getTagBody(), txb.getTagContent());
			}else{
				temContent = temContent.replace(txb.getTagBody(), "");
			}
		}	
		//替换排序方法
		if(temContent.indexOf("{method_sort}")>-1){//引用组件js,css等
			TxtStringBean txb = getTagContent(temContent,"method_sort");
			if(mb.getMethod_sort()!=null && mb.getMethod_sort().equals("yes")){
				temContent = temContent.replace(txb.getTagBody(), txb.getTagContent());
			}else{
				temContent = temContent.replace(txb.getTagBody(), "");
			}
		}
		return temContent;
	}
	
	/**
	 * @author linjunqin
	 * @Description: 递归替换字段
	 * @param tags
	 * @date 2017-1-23 上午10:13:54
	 */
	private String recursionFieldList(String fileCon,CodeBuildBean cbb,String tagName){
		TxtStringBean txb = getTagContent(fileCon,tagName);
		String tagBody = txb.getTagBody();//标签体
		String tagContent = txb.getTagContent();//标签内容
		String bodyContent = txb.getBodyContent();
		String tagGroupContent ="";
		List<TableField> tableFieldList = cbb.getTableFieldList();
		//循环列表处理
		for(int i=0;i<tableFieldList.size();i++){
			TableField tField = tableFieldList.get(i);
			//初始值
			setValue(tField);
			//替换后内容
			String replaceContent = replaceTemContent(tagContent);
			if(i==tableFieldList.size()-1){//去掉最后一个逗号
				replaceContent=replaceContent.replace(",", "");
			}
			tagGroupContent+=replaceContent;
		}
		bodyContent=bodyContent.replace(tagBody, tagGroupContent);
		if(bodyContent.indexOf("{"+tagName+"}")>0){
			bodyContent=recursionFieldList(bodyContent,cbb,tagName);
		}
		return bodyContent;
	}
	
	//得到标签体内容
	private TxtStringBean getTagContent(String temContent,String tagname){
		TxtStringBean txb = new TxtStringBean();
		String startTag = "{"+tagname+"}";
		String enTag = "{/"+tagname+"}";
		txb.setBodyContent(temContent);
		if(temContent.indexOf(startTag) == -1) return txb;
		int i = temContent.indexOf(startTag);
		int j = temContent.indexOf(enTag)+enTag.length();
		//得到标签体
		String tagBody = temContent.substring(i,j);
		//标签体内容
		String bodyCon = tagBody.replace(startTag, "").replace(enTag, "");
		//模板内容
		//temContent = temContent.replace(startTag, "").replace(enTag, "");
		txb.setTagBody(tagBody);
		txb.setTagContent(bodyCon);
		txb.setBodyContent(temContent);
		return txb;
	}
	
	//得到当前的日期
	public String getDate(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		return df.format(new Date());
	}
		
	/**
	 * @author linjunqin
	 * @Description: 赋值
	 * @param tags
	 * @date 2017-1-23 上午10:28:53
	 */
	private void setValue(TableField tField){
		
		field_type = tField.getField_type();
		if(field_type==null) field_type="";
		
		field_note = tField.getField_note();
		if(field_note==null) field_note="";
		
		field_name = tField.getField_name();
		if(field_name==null) field_name="";
		
		fieldName = StringUtil.toUpCaseFirstChar(field_name);
		if(fieldName==null) fieldName="";
		
		field_length = tField.getField_length();
		if(field_length==null) field_length="";
		
		data_type = tField.getData_type();
		if(data_type==null) data_type="";
		
		default_value = tField.getDefault_value();
		if(default_value==null) default_value="";
		
		tip_msg = tField.getTip_msg();
		if(tip_msg==null) tip_msg="";
		
		is_must = tField.getIs_must();
		if(is_must==null) is_must="";
		
		add_edit_field = tField.getAdd_edit_field();
		if(is_must==null) add_edit_field="no";
		
		add_edit_field_sort = tField.getAdd_edit_field_sort();
		if(add_edit_field_sort==null) add_edit_field_sort="0";
		
		show_list_field = tField.getShow_list_field();
		if(show_list_field==null) show_list_field="no";
		
		show_list_field_sort = tField.getShow_list_field_sort();
		if(show_list_field_sort==null) show_list_field_sort="0";
		
		search_list_field = tField.getSearch_list_field();
		if(search_list_field==null) search_list_field="no";
		
		search_list_field_sort = tField.getSearch_list_field_sort();
		if(search_list_field_sort==null) search_list_field_sort="0";
	}
}

