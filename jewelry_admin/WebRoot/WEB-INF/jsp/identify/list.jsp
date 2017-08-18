<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>机构管理</title>
</head>
<body>
	<form action="/admin/identify/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="新增鉴定机构"
				onclick="addInfo('/admin/identify/add');" />
		</div>

		<div class="show_line">
			<%@ include file="/WEB-INF/common/pageshowrow.jsp"%>
		</div>
		<div class="list_div">
			<table id="list_table" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th width="5%">-</th>
					<th width="5%">鉴定机构编号</th>
					<th width="10%">鉴定机构名称</th>
					<th width="10%">归属地</th>
					<th width="10%">排序</th>
					<th width="10%">联系人</th>
					<th width="10%">联系电话</th>
					<th width="10%">操作</th>
				</tr>
				<c:if test="${!empty identifyList}">
					<c:forEach items="${identifyList}" var="item" varStatus="status">
						<tr>
							<td>${status.count}</td>
							<td>${item.iden_number}</td>
							<td>${item.iden_name}</td>
							<td>${item.iden_province}${item.iden_city}${item.iden_county}</td>
							<td>${item.sort}</td>
							<td>${item.iden_contacts}</td>
							<td>${item.iden_contacts_way}</td>
							<td><input class="btn ol_colorbtn ol_bluebtn" type="button"
								value="查看"
								onclick="editInfo('/admin/identify/view','${item.iden_id}');" />
								<input class="btn ol_colorbtn ol_bluebtn" type="button"
								value="修改"
								onclick="editInfo('/admin/identify/edit','${item.iden_id}');" />
								<input class="btn ol_colorbtn ol_bluebtn" type="button"
								value="删除"
								onclick="commonInfo('/admin/identify/delete','${item.iden_id}','确定删除该机构？');" />
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty identifyList}">
					<td colspan="8">暂无数据</td>
				</c:if>
			</table>
		</div>
		<div class="batchDiv">
			<span class="batch_span"><input class="all" type="checkbox" />全选</span>
			<input class="btn ol_colorbtn ol_redbtn" type="button" value="删除"
				onclick="commonBatchInfo('/admin/identify/batchdelete','确定删除该机构？');" />
		</div>
		<div class="page_contain">
			<%@ include file="/WEB-INF/common/pagelist.jsp"%>
		</div>
		<%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
	</form>
</body>
</html>

