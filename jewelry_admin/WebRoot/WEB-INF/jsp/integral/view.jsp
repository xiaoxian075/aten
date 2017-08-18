<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>查看用户</title>
<style>
 	.ueditor_spaicle img {
 		max-width: 800px;
 		display: block;
 	}
</style>
</head>
<body>
	<form id="validateForm" action="" method="post">
		<div class="opercontent">
			<div class="table_div">
				<table width="100%">
					<tr>
						<td class="td_left">礼品编码：</td>
						<td class="td_right_two">${integral.integral_number}</td>
					</tr>
					<tr>
						<td class="td_left">礼品名称：</td>
						<td class="td_right_two">${integral.integral_goods_name}</td>
					</tr>
					<tr>
						<td class="td_left">所需积分值：</td>
						<td class="td_right_two">${integral.integral_value}</td>
					</tr>
					<tr>
						<td class="td_left">礼品图片：</td>
						<td class="td_right_two"><img class="img180"
							src="${ossImgServerUrl}${integral.integral_goods_img}" /></td>
					</tr>
					<tr>
						<td class="td_left">库存：</td>
						<td class="td_right_two">${integral.stock}</td>
					</tr>
					<tr>
						<td class="td_left">排序：</td>
						<td class="td_right_two">${integral.sort_no}</td>
					</tr>
					<tr>
						<td class="td_left">礼品描述：</td>
						<td class="td_right_two ueditor_spaicle">${integral.integral_detail}</td>
					</tr>
				</table>
			</div>

			<div class="row50 operbtndiv">
				<input type="button" class="btn return"
					onclick="returnGo('/admin/integral/list')" value="返回" />
			</div>
		</div>
	</form>
</body>
</html>

