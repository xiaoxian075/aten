<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>短信日志管理</title>
</head>
<body>
	<form action="/admin/smslog/list" method="post">
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
					<td>会员手机:</td>
					<td><input type="text" name="sms_mobile_s"
						value="${sms_mobile_s}" /></td>
					<td><input class="btn ol_btn" type="button" value="搜索"
						onclick="searchInfo('/admin/smslog/list');" /></td>
				</tr>
			</table>
		</div>
		<div class="show_line">
			<%@ include file="/WEB-INF/common/pageshowrow.jsp"%>
		</div>
		<div class="list_div">
			<table id="list_table" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th width="10%">标识</th>

					<th width="10%">发送手机</th>

					<th width="10%">信息类型</th>

					<th width="25%">发送内容</th>

					<th width="10%">发送时间</th>

					<th width="10%">发送状态</th>

					<th width="5%">操作</th>
				</tr>
				<c:if test="${!empty smslogList}">
					<c:forEach items="${smslogList}" var="item" varStatus="status">
						<tr>
							<td>${item.sms_id}</td>

							<td>${item.sms_mobile}</td>

							<td>${item.para_name}</td>

							<td class="list_td_left">${item.sms_msg}</td>

							<td>${item.send_time}</td>

							<td><c:if test="${item.is_send=='0'}">发送成功</c:if> <c:if
									test="${item.is_send=='1'}">
									<span class="span_red">发送失败</span>
								</c:if></td>

							<td>-</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty smslogList}">
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

