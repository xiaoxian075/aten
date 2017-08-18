<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>支付方式管理</title>
</head>
<body>
	<form action="/admin/payway/list" method="post">
		<div class="list_oper_div">
			<input class="btn ol_btn" type="button" value="新增支付方式"
				onclick="addInfo('/admin/payway/add');" /> <input class="btn ol_btn"
				type="button" value="排序" onclick="sortInfo('/admin/payway/sort');" />
		</div>
		<div class="searchDiv">
			<table class="searchTable">
				<tr>
					<td>支付方式:</td>
					<td><input type="text" name="pay_name_vange_s"
						value="${pay_name_vange_s}" /></td>
					<td><input class="btn ol_colorbtn ol_greenbtn" type="button"
						value="查询" onclick="goInfo('/admin/payway/list');" /> <input
						class="btn ol_colorbtn ol_bredbtn" type="button" value="清空"
						onclick="clearSearch('/admin/payway/list');" /></td>
				</tr>
			</table>
		</div>
		<div class="show_line">
			<%@ include file="/WEB-INF/common/pageshowrow.jsp"%>
		</div>
		<div class="list_div">
			<table id="list_table" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th width="3%"><input class="all" type="checkbox" /></th>

					<th width="10%">排序</th>

					<th width="10%">支付方式编码</th>

					<th width="10%">支付方式名称</th>


					<th width="10%">支付方式图标</th>

					<th width="10%">接口地址</th>

					<th width="10%">是否支持IOS支付</th>

					<th width="10%">是否支持安卓支付</th>

					<th width="10%">是否支持WEB支付</th>

					<th width="10%">操作</th>
				</tr>
				<c:if test="${!empty paywayList}">
					<c:forEach items="${paywayList}" var="item" varStatus="status">
						<tr>
							<td><input class="ids" type="checkbox"
								value="${item.pay_id}" /></td>

							<td><input class="sort_id" type="hidden"
								value="${item.pay_id}" /> <input class="sort_val" type="text"
								value="${item.sort_no}" maxlength="6" /></td>

							<td>${item.pay_id}</td>

							<td>${item.pay_name}</td>


							<td><img class="img180"
								src="${ossImgServerUrl}${item.pay_img}" /></td>

							<td>${item.pay_url}</td>

							<td><c:if test="${item.is_ios_pay==1}">
									<span class="span_green">是</span>
								</c:if> <c:if test="${item.is_ios_pay==0}">
									<span class="span_red">否</span>
								</c:if></td>

							<td><c:if test="${item.is_android_pay==1}">
									<span class="span_green">是</span>
								</c:if> <c:if test="${item.is_android_pay==0}">
									<span class="span_red">否</span>
								</c:if></td>

							<td><c:if test="${item.is_web_pay==1}">
									<span class="span_green">是</span>
								</c:if> <c:if test="${item.is_web_pay==0}">
									<span class="span_red">否</span>
								</c:if></td>

							<td><input class="btn ol_colorbtn ol_bluebtn" type="button"
								value="修改"
								onclick="editInfo('/admin/payway/edit','${item.pay_id}');" /> <input
								class="btn ol_colorbtn ol_redbtn" type="button" value="删除"
								onclick="delInfo('/admin/payway/delete','${item.pay_id}');" /></td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty paywayList}">
					<td colspan="11">暂无数据</td>
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

