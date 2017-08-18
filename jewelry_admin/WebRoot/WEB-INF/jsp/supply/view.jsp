<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>查看用户</title>
</head>
<body>
	<form id="validateForm" action="" method="post">
		<div class="opercontent">
			<div class="table_div">
				<table width="100%">
					<tr>
						<td class="td_left">供应商名称：</td>
						<td class="td_right_two">${supply.supply_name}</td>
					</tr>

					<tr>
						<td class="td_left">联系人：</td>
						<td class="td_right_two">${supply.supply_contacts}</td>
					</tr>
					<tr>
						<td class="td_left">联系电话：</td>
						<td class="td_right_two">${supply.supply_contacts_phone}</td>
					</tr>
					<tr>
						<td class="td_left">有效期：</td>
						<td class="td_right_two">${supply.valid_time_start} ~
							${supply.valid_time_end}</td>
					</tr>
					<tr>
						<td class="td_left">供应商营业执照号码：</td>
						<td class="td_right_two">${supply.license_number}</td>
					</tr>
					<tr>
						<td class="td_left">供应商营业执照照片：</td>
						<td class="td_right_two"><img class="img180"
							src="${ossImgServerUrl}${supply.license_picture}" /></td>
					</tr>
					<tr>
						<td class="td_left">供应商法人姓名：</td>
						<td class="td_right_two">${supply.legal_name}</td>
					</tr>
					<tr>
						<td class="td_left">供应商法人身份证号码：</td>
						<td class="td_right_two">${supply.legal_id_card_number}</td>
					</tr>
					<tr>
						<td class="td_left">供应商法人身份证照片：</td>
						<td class="td_right_two"><img class="img180"
							src="${ossImgServerUrl}${supply.legal_id_card_picture}" /></td>
					</tr>
					<tr>
						<td class="td_left">归属地区：</td>
						<td class="td_right_two">${supply.the_area}</td>
					</tr>
					<tr>
						<td class="td_left">是否启用：</td>
						<td class="td_right_two"><c:if test="${supply.state==1}">启用 </c:if>
							<c:if test="${supply.state==0}">禁用 </c:if></td>
					</tr>
					<tr>
						<td class="td_left">备注：</td>
						<td class="td_right" colspan="3">${supply.note}</td>
					</tr>
				</table>
			</div>

			<input type="hidden" name="iden_id" value="${supply.supply_id}" />
			<div class="row50 operbtndiv">
				<input type="button" class="btn return"
					onclick="returnGo('/admin/supply/list')" value="返回" />
			</div>
		</div>
	</form>
</body>
</html>

