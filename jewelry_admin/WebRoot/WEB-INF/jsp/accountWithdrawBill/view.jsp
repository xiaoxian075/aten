<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>详情</title>
</head>
<body>
	<form id="validateForm" action="/admin/accountwithdrawbill/list"
		method="post">
		<div class="opercontent">
			<div class="table_div">
				<table width="100%">
					<tr>
						<td class="td_left"><span style="font-size: 20px;">会员信息</span></td>
					</tr>

					<tr>
						<td class="td_left">会员账号：</td>
						<td class="td_right_two">${account.login_name}</td>
					</tr>
					<tr>
						<td class="td_left">当前可提现收益：</td>
						<td class="td_right_two">${account.format_earnings}</td>
					</tr>
					<tr>
						<td class="td_left">提现金额：</td>
						<td class="td_right_two">${accountWithdrawBill.amount}</td>
					</tr>
					<tr>
						<td class="td_left">提现时间：</td>
						<td class="td_right_two">${accountWithdrawBill.create_time}</td>
					</tr>
					<tr>
						<td class="td_left">银行开户行：</td>
						<td class="td_right_two">${accountWithdrawBill.opening_bank}</td>
					</tr>
					<tr>
						<td class="td_left">银行卡号：</td>
						<td class="td_right_two">${accountWithdrawBill.card_no}</td>
					</tr>
					<tr>
						<td class="td_left"></td>
						<td class="td_right_two"></td>
					</tr>
					<tr>
						<td class="td_left"><span style="font-size: 20px;">审批意见</span></td>
					</tr>

					<tr>
						<td class="td_left">审批意见：</td>
						<td class="td_right_two"><c:if
								test="${accountWithdrawBill.audit_state==1}">
								<span style="color:green">审批通过</span>
							</c:if> <c:if test="${accountWithdrawBill.audit_state==2}">
								<span style="color:red">审批未通过</span>
							</c:if>
					</tr>
					<tr>
						<td class="td_left">审批人：</td>
						<td class="td_right_two">
							${accountWithdrawBill.audit_men_name}
					</tr>
					<tr>
						<td class="td_left">所属角色：</td>
						<td class="td_right_two">
							${accountWithdrawBill.audit_men_role}
					</tr>
					<tr>
						<td class="td_left">审批时间：</td>
						<td class="td_right_two">${accountWithdrawBill.audit_time}
					</tr>
					<tr>
						<td class="td_left">备注：</td>
						<td class="td_right_two">${accountWithdrawBill.audit_note}</td>
					</tr>
				</table>
			</div>
			<div class="row50 operbtndiv">
				<input type="button" class="btn return"
					onclick="returnGo('/admin/accountwithdrawbill/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

