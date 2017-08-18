<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>订单退款管理</title>
</head>
<body>
	<form action="/admin/orderrefund/list" method="post">
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
					<td>退款/售后单号:</td>
					<td><input type="text" name="refund_id_s"
						value="${refund_id_s}" /></td>
					<td>订单号:</td>
					<td><input type="text" name="detail_id_s"
						value="${detail_id_s}" /></td>
					<td>服务类型:</td>
					<td><select class="selectProportion" name="refund_type_s">
							<c:forEach items="${refund_type}" var="refund_type">
								<option value="${refund_type.para_key}">${refund_type.para_name}</option>
							</c:forEach>
					</select></td>
					<td>订单状态:</td>
					<td><select class="selectProportion" name="refund_state_s">
							<c:forEach items="${refund_state}" var="refund_state">
								<option value="${refund_state.para_key}">${refund_state.para_name}</option>
							</c:forEach>
					</select></td>
					<td><input class="btn ol_colorbtn ol_greenbtn" type="button"
						value="查询" onclick="goInfo('/admin/orderRefund/list');" /> <input
						class="btn ol_colorbtn ol_bredbtn" type="button" value="清空"
						onclick="clearSearch('/admin/orderRefund/list');" /></td>
				</tr>
			</table>
		</div>
		<div class="show_line">
			<%@ include file="/WEB-INF/common/pageshowrow.jsp"%>
		</div>
		<div class="list_div">
			<table id="list_table" border="0" cellspacing="0" cellpadding="0">
				<tr>

					<th width="10%">退款/售后单号</th>

					<th width="10%">订单号</th>

					<th width="10%">订单金额（元）</th>

					<th width="10%">服务类型</th>

					<th width="10%">申请时间</th>

					<th width="10%">会员账户</th>

					<th width="10%">状态</th>

					<th width="10%">操作</th>
				</tr>
				<c:if test="${!empty orderRefundList}">
					<c:forEach items="${orderRefundList}" var="item" varStatus="status">
						<tr>
							<td>${item.refund_id}</td>
							<td>${item.detail_id}</td>
							<td>${item.refund_amount}</td>
							<td><c:if test="${item.refund_type==0}">
									<span class="span_green">仅退款</span>
								</c:if> <c:if test="${item.refund_type==1}">
									<span class="span_red">退货退款</span>
								</c:if> <c:if test="${item.refund_type==2}">
									<span class="span_red">售后订单</span>
								</c:if></td>
							<td>${item.refund_time}</td>
							<td>${item.buyer_nick}</td>
							<td><c:if test="${item.refund_state==0}">
									<span class="span_green">待确认</span>
								</c:if> <c:if test="${item.refund_state==1}">
									<span class="span_red">退款成功</span>
								</c:if> <c:if test="${item.refund_state==2}">
									<span class="span_red">退款失败 </span>
								</c:if></td>
							<td><c:if test="${item.refund_state==0}">
									<input class="btn ol_colorbtn ol_bluebtn" type="button"
										value="查看详情"
										onclick="goInfo('/admin/orderrefund/detail','${item.detail_id}');" />
								</c:if> <c:if test="${item.refund_state==1}">
									<input class="btn ol_colorbtn ol_bluebtn" type="button"
										value="修改"
										onclick="editInfo('/admin/orderrefund/edit','${item}');" />
								</c:if> <c:if test="${item.refund_state==2}">
									<input class="btn ol_colorbtn ol_bluebtn" type="button"
										value="修改"
										onclick="editInfo('/admin/orderrefund/edit','${item}');" />
								</c:if></td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty orderRefundList}">
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

