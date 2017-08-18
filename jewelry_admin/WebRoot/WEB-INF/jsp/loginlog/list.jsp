<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>登录日志管理</title>
</head>
<body>
	<form action="/admin/loginlog/list" method="post">
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
					<td>会员名称:</td>
					<td><input type="text" name="back_name_s"
						value="${back_name_s}" /></td>
					<td><input class="btn ol_btn" type="button" value="搜索"
						onclick="searchInfo('/admin/loginlog/list');" /></td>
				</tr>
			</table>
		</div>
		<div class="show_line">
			<%@ include file="/WEB-INF/common/pageshowrow.jsp"%>
		</div>
		<div class="list_div">
			<table id="list_table" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th width="5%">ID</th>

					<th width="10%">客户名称</th>

					<th width="10%">客户类型</th>

					<th width="10%">登录ip</th>

					<th width="10%">登录时间</th>

					<th width="5%">操作</th>
				</tr>
				<c:if test="${!empty loginlogList}">
					<c:forEach items="${loginlogList}" var="item" varStatus="status">
						<tr>
							<td>${status.count}</td>

							<td>${item.back_name}</td>

							<td><c:if test="${item.back_type=='mana'}">运营商</c:if> <c:if
									test="${item.back_type=='memb'}">会员</c:if></td>

							<td>${item.login_ip}</td>

							<td>${item.login_time}</td>


							<td>-</td>

						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty loginlogList}">
					<td colspan="6">暂无数据</td>
				</c:if>
			</table>
		</div>
		<div class="page_contain">
			<%@ include file="/WEB-INF/common/pagelist.jsp"%>
		</div>
		<%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
	</form>
</body>
</html>

