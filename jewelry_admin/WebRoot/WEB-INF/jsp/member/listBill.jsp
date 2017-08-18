<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>消息管理</title>
</head>
<body>
	<form action="/admin/member/listBill" method="post">
		<div class="list_oper_div">
			<input class="btn return"
				type="button" value="返回列表" onclick="goInfo('/admin/member/list');" />
		</div>
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
					<td>账单流水号:</td>
					<td><input type="text" name="bill_number_vague_s"
						value="${bill_number_vague_s}" /></td>
					<td><input class="btn ol_colorbtn ol_greenbtn" type="button"
						value="查询" onclick="goInfo('/admin/member/listBill');" /> <input
						class="btn ol_colorbtn ol_bredbtn" type="button" value="清空"
						onclick="clearSearch('/admin/member/listBill');" /></td>
				</tr>
			</table>
		</div>
		<div class="show_line">
			<%@ include file="/WEB-INF/common/pageshowrow.jsp"%>
		</div>
		<div class="list_div">
			<table id="list_table" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th width="10%">账单流水号</th>

					<th width="10%">账单类型</th>

					<th width="10%">支付方式</th>

					<th width="10%">金额（元）</th>
					
					<th width="10%">收支类型</th>

					<th width="10%">账单时间</th>

				</tr>
				<c:if test="${!empty accountBillList}">
					<c:forEach items="${accountBillList}" var="item" varStatus="status">
						<tr>
							<td>${item.bill_number}</td>

							<td>${item.bill_name}</td>

							<td>${item.pay_way_str}</td>
							
							<td>${item.amount_str}</td>

							<td>${item.io_type_str}</td>
							
							<td>${item.create_time_str}</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty accountBillList}">
					<td colspan="6">暂无数据</td>
				</c:if>
			</table>
		</div>
		<div class="page_contain">
			<%@ include file="/WEB-INF/common/pagelist.jsp"%>
		</div>
		<%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
		<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
		<input type="hidden" name="parameter_id" value="${parameter_id}" />
	</form>
</body>
</html>

