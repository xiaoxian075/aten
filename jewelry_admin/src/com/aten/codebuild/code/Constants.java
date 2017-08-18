package com.aten.codebuild.code;

import com.communal.util.ProjectUtil;


public class Constants {

	
	//根据需要自己配置
	//作者名称
	public static String AUTHOR  ="linjunqin";
	//项目路径
	public static final String PROJECT_PATH = "E:\\myworkspace\\yszb_admin";
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//模板路径
	public static final String TEM_PATH = PROJECT_PATH+"/src/com/aten/codebuild/template";
	//生成类路径
	public static final String CODE_PATH = PROJECT_PATH+"/src/com/aten";
	//生成JSP路径
	public static final String CODE_JSP_PATH = PROJECT_PATH+"/WebRoot/WEB-INF/jsp";
	//组件模板路径
	public static final String COMPONENT_TEM_PAth = PROJECT_PATH+"/src/com/aten/codebuild/template/component/";
	
	//模板路径+模板名
	public static final String XML_TEM_PAth = TEM_PATH+"/classtem/mybatisSqlTemplate-mysql.txt";
	public static final String ORM_TEM_PATH = TEM_PATH+"/classtem/modelTemplate.txt";
	public static final String DAO_TEM_PATH = TEM_PATH+"/classtem/dao.txt";
	public static final String SERVICE_TEM_PATH = TEM_PATH+"/classtem/service.txt";
	public static final String SERVICEIMPL_TEM_PATH = TEM_PATH+"/classtem/serviceimpl.txt";
	public static final String CONTROLLER_TEM_PATH = TEM_PATH+"/classtem/controller.txt";
	
	public static final String WEB_COMMON_TEM_PATH = TEM_PATH+"/pagetem/common.txt";
	public static final String WEB_INSERT_TEM_PATH = TEM_PATH+"/pagetem/insert.txt";
	public static final String WEB_UPDATE_TEM_PATH = TEM_PATH+"/pagetem/update.txt";
	public static final String WEB_LIST_TEM_PATH = TEM_PATH+"/pagetem/list.txt";
	
	//生成路径
	public static final String XML_SAVE_PAth = CODE_PATH+"/dao/mapping";
	public static final String ORM_SAVE_PATH = CODE_PATH+"/model/orm";
	public static final String DAO_SAVE_PATH = CODE_PATH+"/dao";
	public static final String SERVICE_SAVE_PATH = CODE_PATH+"/service";
	public static final String SERVICEIMPL_SAVE_PATH = CODE_PATH+"/service/impl";
	public static final String CONTROLLER_SAVE_PATH = CODE_PATH+"/controller";
	
	//生成页面路径
	public static final String WEB_COMMON_SAVE_PATH  = CODE_JSP_PATH+"/{DEFINE_CLASS_NAME}";
	public static final String WEB_INSERT_SAVE_PATH  = CODE_JSP_PATH+"/{DEFINE_CLASS_NAME}";
	public static final String WEB_UPDATE_SAVE_PATH  = CODE_JSP_PATH+"/{DEFINE_CLASS_NAME}";
	public static final String WEB_LIST_SAVE_PATH  = CODE_JSP_PATH+"/{DEFINE_CLASS_NAME}";
	

	//结束 新配置

}
