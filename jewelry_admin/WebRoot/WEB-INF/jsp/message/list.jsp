<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>消息管理</title>
</head>
<body>
	<form action="/admin/message/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="新增消息"
				onclick="addInfo('/admin/message/add');" />
		</div>
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
					<td>消息标题:</td>
					<td><input type="text" name="msg_title_vague_s"
						value="${msg_title_vague_s}" /></td>
					<td><input class="btn ol_colorbtn ol_greenbtn" type="button"
						value="查询" onclick="goInfo('/admin/message/list');" /> <input
						class="btn ol_colorbtn ol_bredbtn" type="button" value="清空"
						onclick="clearSearch('/admin/message/list');" /></td>
				</tr>
			</table>
		</div>
		<div class="show_line">
			<%@ include file="/WEB-INF/common/pageshowrow.jsp"%>
		</div>
		<div class="list_div">
			<table id="list_table" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th width="10%">消息标题</th>

					<th width="10%">消息简介</th>

					<th width="10%">消息内容</th>

					<th width="10%">消息类型</th>

					<th width="10%">消息发布时间</th>

					<th width="10%">操作</th>
				</tr>
				<c:if test="${!empty messageList}">
					<c:forEach items="${messageList}" var="item" varStatus="status">
						<tr>
							<td>${item.msg_title}</td>

							<td>${item.introduction}</td>

							<td>${item.msg_content}</td>

							<td><c:if test="${item.msg_type==0}">公告</c:if></td>

							<td>${item.in_date}</td>

							<td><input class="btn ol_colorbtn ol_bluebtn" type="button"
								value="修改"
								onclick="editInfo('/admin/message/edit','${item.msg_id}');" /> <input
								class="btn ol_colorbtn ol_redbtn" type="button" value="删除"
								onclick="delInfo('/admin/message/delete','${item.msg_id}');" />
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty messageList}">
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

