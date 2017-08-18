<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>查看用户</title>
</head>
<body>
	<form id="validateForm" action="/admin/manager/view" method="post">
		<div class="opercontent">
			<div class="table_div">
				<table width="100%">
					<tr>
						<td class="td_left">鉴定机构编号<span class="must_span">*</span></td>
						<td class="td_right_two">${identify.iden_number}</td>
					</tr>

					<tr>
						<td class="td_left">鉴定机构名称<span class="must_span">*</span></td>
						<td class="td_right_two">${identify.iden_name}</td>
					</tr>

					<tr>
						<td class="td_left">归属地<span class="must_span">*</span></td>
						<td class="td_right_two">
							${identify.iden_province}${identify.iden_province}${identify.iden_province}
						</td>
					</tr>



					<tr>
						<td class="td_left">排序<span class="must_span">*</span></td>
						<td class="td_right_two">${identify.sort}</td>
					</tr>

					<tr>
						<td class="td_left">联系人<span class="must_span">*</span></td>
						<td class="td_right_two">${identify.iden_contacts}</td>
					</tr>

					<tr>
						<td class="td_left">联系电话<span class="must_span">*</span></td>
						<td class="td_right_two">${identify.iden_contacts_way}</td>
					</tr>
					<tr>
						<td class="td_left">状态<span class="sp_span">:</span></td>
						<td class="td_right_two"><c:if test="${identify.status==1}">启用 </c:if>
							<c:if test="${identify.status==0}">禁用 </c:if></td>
					</tr>


				</table>
			</div>

			<input type="hidden" name="iden_id" value="${identify.iden_id}" />
			<div class="row50 operbtndiv">
				<input type="button" class="btn return"
					onclick="returnGo('/admin/identify/list')" value="返回" />
			</div>
		</div>
	</form>
</body>
</html>

