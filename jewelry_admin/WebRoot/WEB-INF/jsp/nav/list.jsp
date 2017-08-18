<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>导航管理</title>
</head>
<body>
	<form action="/admin/nav/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="添加导航"
				onclick="addInfo('/admin/nav/add');" /> <input class="btn ol_btn"
				type="button" value="排序" onclick="sortInfo('/admin/nav/sort');" />
		</div>
		<div class="searchDiv"></div>
		<div class="show_line">
			<%@ include file="/WEB-INF/common/pageshowrow.jsp"%>
		</div>
		<div class="list_div">
			<table id="list_table" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th width="5%">排序</th>

					<th width="10%">导航名称</th>

					<th width="10%">导航图标</th>

					<th width="10%">导航位置</th>

					<th width="10%">链接地址</th>

					<th width="10%">状态</th>

					<th width="5%">操作</th>
				</tr>
				<c:if test="${!empty navList}">
					<c:forEach items="${navList}" var="item" varStatus="status">
						<tr>
							<td><input class="sort_id" type="hidden"
								value="${item.nav_id}" /> <input class="sort_val" type="text"
								value="${item.sort_no}" maxlength="5" style="width:50px;" /></td>

							<td>${item.nav_name}</td>

							<td><c:if test="${!empty item.nav_ico}">
									<img class="img180" src="${item.nav_ico}" />
								</c:if> <c:if test="${empty item.nav_ico}">-</c:if></td>

							<td><c:if test="${item.nav_post==0}">首页</c:if></td>

							<td>${item.link_url}</td>

							<td><c:if test="${item.state=='0'}">
									<span class="span_green">正常</span>
								</c:if> <c:if test="${item.state=='1'}">
									<span class="span_red">禁用</span>
								</c:if></td>


							<td><input type="button" class="edit_btn" title="修改"
								onclick="editInfo('/admin/nav/edit','${item.nav_id}');">
								<input type="button" class="del_btn" title="删除"
								onclick="delInfo('/admin/nav/delete','${item.nav_id}');">
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty navList}">
					<td colspan="8">暂无数据</td>
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

