<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>查看</title>
<style type="text/css">
.content {
	margin: 80px;
}
.content label{
	font-size: 20px;
}
.content table{
	margin-top: 10px;
	margin-bottom: 20px;
}
.content th{
	padding:20px;
}
.content td{
	padding:20px;
}
</style>
</head>
<body>
	<form id="validateForm" action="" method="post">
		<div class="content">
				<label>积分兑换基本信息</label>
				<table border="1" cellspacing="0" cellpadding="0">
					<tr>
						<th>兑换单号</th>
						<th>礼品编号</th>
						<th>礼品名称</th>
						<th>使用积分值</th>
						<th>兑换时间</th>
						<th>会员账户</th>
					</tr>
					<tr>
						<td>${oi.order_number}</td>
						<td>${integral.integral_number }</td>
						<td>${integral.integral_goods_name }</td>
						<td>${integral.integral_value } </td>
						<td>${oi.exchange_time}</td>
						<td>${oi.login_name }</td>
					</tr>
				</table>
				<label>收货人信息</label>
				<table border="1" cellspacing="0" cellpadding="0">
					<tr>
						<td>收货人姓名</td>
						<td>${orderExpress.consignee}</td>
					</tr>
					<tr>
						<td>联系电话</td>
						<td>${orderExpress.consignee_mobile }</td>
					</tr>
					<tr>
						<td>收货地址</td>
						<td>${orderExpress.consignee_address }</td>
					</tr>
				</table>
			<div class="row50 operbtndiv">
				<input type="button" class="btn return"
					onclick="goInfo('/admin/orderIntegral/list','${oi.integral_id}');" value="返回" />
			</div>
		</div>
		<%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
	</form>
</body>
</html>

