<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>自定义属性管理</title>
</head>
<body>
	<form action="/admin/customattr/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="添加自定义属性"
				onclick="addInfo('/admin/customattr/add');" /> <input
				class="btn ol_btn" type="button" value="排序"
				onclick="sortInfo('/admin/customattr/sort');" />
		</div>
		<div class="searchDiv"></div>
		<div class="show_line">
			<%@ include file="/WEB-INF/common/pageshowrow.jsp"%>
		</div>
		<div class="list_div">
			<table id="list_table" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th width="5%">ID</th>

					<th width="10%">-</th>

					<th width="10%">-</th>

					<th width="10%">-</th>

					<th width="10%">-</th>

					<th width="10%">-</th>

					<th width="10%">-</th>

					<th width="5%">操作</th>
				</tr>
				<c:if test="${!empty customAttrList}">
					<c:forEach items="${customAttrList}" var="item" varStatus="status">
						<tr>
							<td>${status.count}</td>

							<td>${item.custom_value_id}</td>

							<td>${item.attr_id}</td>

							<td>${item.attr_value_id}</td>

							<td>${item.custom_attr_value}</td>

							<td>${item.attr_type}</td>

							<td>${item.quote_id}</td>


							<td><input type="button" class="edit_btn" title="修改"
								onclick="editInfo('/admin/customattr/edit','${item.custom_value_id}');">
								<input type="button" class="del_btn" title="删除"
								onclick="delInfo('/admin/customattr/delete','${item.custom_value_id}');">
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty customAttrList}">
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

