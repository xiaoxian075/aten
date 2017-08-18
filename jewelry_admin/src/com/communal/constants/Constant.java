package com.communal.constants;

import org.springframework.stereotype.Component;

import com.aten.function.SysconfigFuc;

public class Constant {

	public static final String androidPushKey = "581813484ad1564e1200178b";
	public static final String androidAppMarst = "h8evrhtwcxd3zuqk3lra4gnanraocegv";
	public static final String iodPushKey = "5818134ec62dca598d001ea8";
	public static final String iosAppMarst = "5zudtxo86qfssp0bbwk6yebqnpnhh5mp";
	
	//系统后台服务端地址
	public static String CFG_SERVER_URL=SysconfigFuc.getSysValue("cfg_server_url");
	//图片地址前缀
	public static String IMG_URL=SysconfigFuc.getSysValue("oss_imgurl");
	//OSS资源图片地址前缀
	public static String OSS_IMG_URL= SysconfigFuc.getSysValue("oss_imgurl");
	//无图的图片
	public static String NO_PICTURE=SysconfigFuc.getSysValue("cfg_no_picture");
	//上传图片服务器的接口地址
	public static String UPLOAD_IMG_URL = SysconfigFuc.getSysValue("cfg_imgserver_uploadpath");
	//存放导航的路径
	public static String NAV_PATH ="nav_path";
	//用户名
	public static String USER_NAME="username";
	//用户标识
	public static String USER_ID="user_id";
	//用户菜单权限串
	public static String MENU_RIGHT="menu_right";
	//用户第一级菜单标识
	public static String FIRST_MENU_ID="first_menu_id";
	//用户操作权限串
	public static String POWER_RIGHT="power_right";
	//用户无操作权限提示
	public static String NOT_POWER_PAGE="/nopower";
	//500错误页面
	public static String ERROR="/error";
	//DES密钥
	public static String DES_KEY="12345678";
	//用户管理员类型
	public static String MANA_TYPE="mana_type";
	//TOKEN_ID标识
	public static String TOKEN_ID ="token_id";
	//redis TOKEN_ID失效时间
	public static int EXPIRE_TOKEN_TIME =259200;//3天 
	//重复提交的地址
	public static String RECOMMIT_URL="/recommit";
	//登录地址
	public static String LOGIN_URL="/login";
	//后台首页
	public static String BACK_INDEX_URL="/index";
	//用户权限串
	public static String POWER_STR="user_power";
	//用户菜单串
	public static String MENU_STR="user_menu";
	//起始页
	public static String CURRENT = "current_s";
	//每页页数
	public static String PAGESIZE = "pagesize_s";
	//获取菜单第一级的ID值
	public static String UP_AREA_ID="1111111111,";
	//相册分类的字典编码
	public static String  CFG_GAL_TYPE = "cfg_gal_type";
	//初始化密码
	public static String INIT_PASSWORD = SysconfigFuc.getSysValue("cfg_init_password");
	//系统管理员角色代码，不能修改
	public static String SYS_ROLE_CODE = "syscode";  
	// 订单字典表的参数编码
	public static String ORDER_STATE = "cfg_order_state";
	// 订单字典表的参数编码
	public static String BILL_TYPE = "cfg_billpay_type";
	//当前系统用户所属角色
	public static String USER_ROLE ="user_role";
	//分隔符
	public static String POS =" > ";
}
