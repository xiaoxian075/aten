<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">审批信息&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<td class="td_left">审批编号：</td>
			<td class="td_right_two">${accountBalanceApprove.approve_num }</td>
		</tr>
		<tr>
			<td class="td_left">提交时间：</td>
			<td class="td_right_two">${accountBalanceApprove.create_time }</td>
		</tr>
		<tr>
			<td class="td_left">提交人：</td>
			<td class="td_right_two">${accountBalanceApprove.submitter_name }</td>
		</tr>
		<tr>
			<td class="td_left">所属角色：</td>
			<td class="td_right_two">${accountBalanceApprove.submitter_rolename }</td>
		</tr>
		<tr>
			<td class="td_left">会员帐号：</td>
			<td class="td_right_two">${accountBalanceApprove.login_name }</td>
		</tr>
		<tr>
			<td class="td_left">昵称：</td>
			<td class="td_right_two">${account.nick_name }</td>
		</tr>
		<tr>
			<td class="td_left">性别：</td>
			<td class="td_right_two"><c:if test="${account.sex==1 }">男</c:if>
				<c:if test="${account.sex==2 }">女</c:if> <c:if
					test="${account.sex==3 }">保密</c:if></td>
		</tr>
		<tr>
			<td class="td_left">生日：</td>
			<td class="td_right_two">${account.birthday }</td>
		</tr>
		<tr>
			<td class="td_left">会员等级：</td>
			<td class="td_right_two"><c:if test="${account.lev==1 }">普通会员</c:if>
				<c:if test="${account.lev==2 }">vip会员</c:if></td>
		</tr>
		<tr>
			<td class="td_left">余额：</td>
			<td class="td_right_two">${balance}元</td>
		</tr>
		<c:if test="${accountBalanceApprove.io_type==2 }">
			<tr>
				<td class="td_left">充值金额：</td>
				<td class="td_right_two">${accountBalanceApprove.approve_amount}元</td>
			</tr>
		</c:if>
		<c:if test="${accountBalanceApprove.io_type==1 }">
			<tr>
				<td class="td_left">扣款金额：</td>
				<td class="td_right_two">${accountBalanceApprove.approve_amount}元</td>
			</tr>
		</c:if>
		<tr>
			<td class="td_left">上传凭证：</td>
			<td class="td_right_two"><c:set var="one_img_url"
					value="${accountBalanceApprove.submitter_img}" /> <%@ include
					file="/WEB-INF/common/one_image_view.jsp"%>
			</td>
		</tr>
		<tr>
			<td class="td_left">备注：</td>
			<td class="td_right" colspan="3">
				${accountBalanceApprove.submitter_note }</td>
		</tr>
		<tr>
			<td class="td_left">审批意见&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<td class="td_left">审批意见：</td>
			<td class="td_right" colspan="3"><c:if
					test="${accountBalanceApprove.audit_state==0}"> 待审批</c:if> <c:if
					test="${accountBalanceApprove.audit_state==1}"> 审批通过</c:if> <c:if
					test="${accountBalanceApprove.audit_state==2}"> 审批失败</c:if></td>
		</tr>
		<c:if test="${accountBalanceApprove.audit_state!=0}">
			<tr>
				<td class="td_left">审批人：</td>
				<td class="td_right_two">${accountBalanceApprove.approve_mana_name }</td>
			</tr>
			<tr>
				<td class="td_left">所属角色：</td>
				<td class="td_right_two">${accountBalanceApprove.approve_rolename }</td>
			</tr>
			<tr>
				<td class="td_left">审批时间：</td>
				<td class="td_right_two">${accountBalanceApprove.approve_time }</td>
			</tr>
			<tr>
				<td class="td_left">备注：</td>
				<td class="td_right" colspan="3">
					${accountBalanceApprove.approve_note }</td>
			</tr>
		</c:if>
	</table>
</div>

