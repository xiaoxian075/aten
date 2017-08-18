<%@page import="com.communal.util.StringUtil"%>
<%@ page import="com.aten.model.orm.Nav"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@ page import="java.util.*"%>
<%@ page import="com.aten.function.SysmenuFuc"%>
<%@ page import="com.aten.model.orm.Sysmenu"%>
<%@ page import="com.communal.constants.Constant"%>
<%@ page import="com.aten.function.NavFuc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>珠宝-<decorator:title default="Welcome" /></title>
<link rel="shortcut icon" type="image/x-icon" href="/favicon.ico" />
<link rel="stylesheet" type="text/css"	href="/include/admin/css/index.css" />
<link rel="stylesheet" type="text/css"	href="/component/artDialog-5.0.3/skins/default.css" />
<link rel="stylesheet" type="text/css"	href="/include/admin/css/common.css" />
<link rel="stylesheet" type="text/css"	href="/include/admin/css/order.css" />
<link rel="stylesheet" type="text/css"	href="/include/admin/css/remark.css" />
<script type="text/javascript"	src="/include/common/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript"	src="/component/artDialog-5.0.3/artDialog.js"></script>
<script type="text/javascript" src="/include/common/js/common.js"></script>
<script type="text/javascript" src="/include/common/js/validate.js"></script>
<script type="text/javascript"	src="/include/common/js/project.plugin.js"></script>
<link rel="stylesheet" href="/component/lay/layui/css/layui.css">
<script src="/component/lay/layer/layer.js"></script>
<script src="/component/lay/layui/layui.js"></script>
<script src="/component/ztree/jquery.ztree.all.min.js"></script>
<link rel="stylesheet"	href="/component/ztree/metroStyle/metroStyle.css">
<%@ include file="/WEB-INF/common/img_control.jsp"%>
<%@ include file="/WEB-INF/common/ueditor.jsp"%>
<%@ include file="/WEB-INF/common/timepicker.jsp"%>
<decorator:head />
</head>
<body>
	<div class="navbar-top">
		<input id="sessionGetId" type="hidden" value="<%=session.getId()%>" />
		<%--<input id="imgServerUrl" type="hidden" value="<%=Constant.IMG_URL%>" />--%>
		<input id="imgServerUrl" type="hidden" value="" /> <input
			id="ossImgServerUrl" type="hidden" value="<%=Constant.OSS_IMG_URL%>" />
		<input id="noPicture" type="hidden" value="${noPicture}" />
		<div class="top">
			<b class="top-left f_left"> 云商珠宝管理系统 </b>
			<ul class="navfirst">
				<%
					String plat_code = "0";//管理角色 
					//获取请求地址
					String url = request.getRequestURI();
					//权限串
					String menu_right = "";
					//第一级菜单ID
					String first_menu_id = "";
					if (session.getAttribute(Constant.MENU_RIGHT) != null) {
						menu_right = session.getAttribute(Constant.MENU_RIGHT).toString();
					}
					if (session.getAttribute(Constant.FIRST_MENU_ID) != null) {
						first_menu_id = session.getAttribute(Constant.FIRST_MENU_ID).toString();
					}
					//运营商类型
					String mana_type = session.getAttribute(Constant.MANA_TYPE).toString();
					//根据请求地址返回的菜单串
					String level_menu = SysmenuFuc.getMenuPlatCodeList(url, plat_code);
					String fid = SysmenuFuc.getFirstMenuID(url, plat_code);
					if (fid != null && !fid.equals("")) {
						//存入session中
						first_menu_id = fid;
						session.setAttribute(Constant.FIRST_MENU_ID, fid);
					}
					//菜单串
					if (!level_menu.equals(""))
						session.setAttribute("level_menu", level_menu);
					if (session.getAttribute("level_menu") != null) {
						level_menu = session.getAttribute("level_menu").toString();
					}
					//根据系统顶级ID与菜单类型
					List<Sysmenu> firstMenuList = SysmenuFuc.getFirstMenuList("1", plat_code);
					if (firstMenuList != null && firstMenuList.size() > 0) {
						for (int j = 0; j < firstMenuList.size(); j++) {
							Sysmenu firstMenu = (Sysmenu) firstMenuList.get(j);
							String menu_name = "", menu_id = "", menu_url = "", target = "";
							if (firstMenu.getMenu_id() != null)
								menu_id = firstMenu.getMenu_id();
							//根据菜单串显示菜单
							if (mana_type.equals("1") && menu_right.indexOf(menu_id) == -1)
								continue;
							if (firstMenu.getMenu_name() != null)
								menu_name = firstMenu.getMenu_name();
							if (firstMenu.getMenu_url() != null)
								menu_url = firstMenu.getMenu_url();
							if (firstMenu.getTarget() != null)
								target = firstMenu.getTarget();
							//样式名字
							String selNavfirst = "", selNavA = "";

							if (first_menu_id.indexOf(menu_id) > -1) {
								selNavfirst = "selNavfirst";
								selNavA = "selNavA";
							}
				%>
				<li class="<%=selNavfirst%>"><a style="cursor:pointer;"
					class="<%=selNavA%>" href="<%=menu_url%>" target="<%=target%>"><%=menu_name%></a></li>
				<%
					}
					}
				%>

			</ul>

			<div class="top-right f_right">
				<b>您好,</b>
				<%
					String user_name = (String) session.getAttribute(Constant.USER_NAME);
				%>
				<span><%=user_name%></span> <span>|</span> <span><a
					href="/admin/manager/editpwd"><b>修改密码</b></a></span> <span>|</span> <span><a
					href="/loginexit"><b>退出</b></a></span>
			</div>
			<div class="clear"></div>
		</div>

	</div>
	<div class="main-content">

		<div class="nav-left">
			<%
				//根据系统顶级ID与菜单类型
				List<Sysmenu> secondMenuList = SysmenuFuc.getMenuListByRole(first_menu_id, "2", plat_code);
				if (secondMenuList != null && secondMenuList.size() > 0) {
					for (int i = 0; i < secondMenuList.size(); i++) {
						Sysmenu secondMenu = (Sysmenu) secondMenuList.get(i);
						String second_menu_name = "", second_menu_id = "", second_menu_url = "", second_target = "";
						if (secondMenu.getMenu_id() != null)
							second_menu_id = secondMenu.getMenu_id();
						if (mana_type.equals("1") && menu_right.indexOf(second_menu_id) == -1)
							continue;
						if (secondMenu.getMenu_name() != null)
							second_menu_name = secondMenu.getMenu_name();
						if (secondMenu.getMenu_url() != null)
							second_menu_url = secondMenu.getMenu_url();
						if (secondMenu.getTarget() != null)
							second_target = secondMenu.getTarget();
						//判断是不是在二级菜单中
						String second_style = "style='display:none;'";
						if (level_menu == "") {
							if (i == 0)
								second_style = "";
						} else if (level_menu.indexOf(second_menu_id) > -1) {
							second_style = "";
						}
			%>
			<ul class="nav-two">
				<p><%=second_menu_name%></p>
				<ul class="nav-three" <%=second_style%>>
					<%
						//获取第三级菜单
								List<Sysmenu> threeMenuList = SysmenuFuc.getFirstMenuList("3", plat_code);
								if (threeMenuList != null && threeMenuList.size() > 0) {
									for (Iterator th = threeMenuList.iterator(); th.hasNext();) {
										Sysmenu threeMenu = (Sysmenu) th.next();
										String three_menu_name = "", three_menu_id = "", three_menu_url = "", three_target = "",
												three_parent_id = "";
										if (threeMenu.getMenu_id() != null)
											three_menu_id = threeMenu.getMenu_id();
										if (mana_type.equals("1") && menu_right.indexOf(three_menu_id) == -1)
											continue;
										if (threeMenu.getMenu_name() != null)
											three_menu_name = threeMenu.getMenu_name();
										if (threeMenu.getMenu_url() != null)
											three_menu_url = threeMenu.getMenu_url();
										if (threeMenu.getTarget() != null)
											three_target = threeMenu.getTarget();
										if (threeMenu.getParent_menu_id() != null)
											three_parent_id = threeMenu.getParent_menu_id();

										if (three_parent_id.equals(second_menu_id)) {
											String three_class = "";
											if (level_menu.indexOf(three_menu_id) > -1) {
												three_class = "class='sel_nav'";
											}
					%>
					<li <%=three_class%>><a href="<%=three_menu_url%>"
						target="<%=three_target%>"><%=three_menu_name%></a></li>
					<%
						}
									}
								}
					%>
				</ul>
			</ul>

			<%
				}
				}
			%>
		</div>
		<div class="content_right">
			<div class="pagenav">
				<%
					String navPath = NavFuc.getNavName(url);
					if (navPath != null && !("").equals(navPath)) {
						session.setAttribute(Constant.NAV_PATH, navPath);
					} else {
						navPath = session.getAttribute(Constant.NAV_PATH).toString();
					}
				%>
				<%=navPath%>
			</div>
			<decorator:body />
		</div>

	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			//加载提示保存是否成功信息
			var promptmsg = "${promptmsg}";
			if (promptmsg != "") {
				art.dialog({
					title : '友情提示',
					content : promptmsg,
					time : 2000
				});
			}
			//表格样式
			if ($("#list_table").length > 0) {
				$("#list_table").projectTable();
			}
			//二级菜单选项卡的显示,右键显示
			$(".nav-two").mousedown(function(e) {
				if (e.which == 1) {
					$(".nav-three").hide();
					$(this).find(".nav-three").show();
				} else if (e.which == 2) {
					$(".nav-three").show();
				}
				return false; //阻止链接跳转.
			});
		});
	</script>
	<!-- 显示大图结构开始 -->
	<div class="look_big_pic">
		<div class="mask"></div>
		<div class="cont">
			<img src="" /> <span class="close_btn"></span>
		</div>
	</div>
	<!-- 显示大图结构结束 -->
</body>
</html>
