<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">审批信息&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<td class="td_left">审批编号：</td>
			<td class="td_right_two">${accountInterApprove.approve_num }</td>
		</tr>
		<tr>
			<td class="td_left">提交时间：</td>
			<td class="td_right_two">${accountInterApprove.create_time }</td>
		</tr>
		<tr>
			<td class="td_left">提交人：</td>
			<td class="td_right_two">${accountInterApprove.submitter_name }</td>
		</tr>
		<tr>
			<td class="td_left">所属角色：</td>
			<td class="td_right_two">${accountInterApprove.submitter_rolename }</td>
		</tr>
		<tr>
			<td class="td_left">会员帐号：</td>
			<td class="td_right_two">${accountInterApprove.login_name }</td>
		</tr>
		<tr>
			<td class="td_left">积分：</td>
			<td class="td_right_two">${account.integral}</td>
		</tr>
		<tr>
			<td class="td_left">增减类型：</td>
			<td class="td_right_two"><c:if
					test="${accountInterApprove.io_type==2 }">增加</c:if> <c:if
					test="${accountInterApprove.io_type==1 }">减少</c:if></td>
		</tr>
		<tr>
			<td class="td_left">积分值：</td>
			<td class="td_right_two">${accountInterApprove.inter_value}</td>
		</tr>
		<tr>
			<td class="td_left">备注：</td>
			<td class="td_right" colspan="3">
				${accountInterApprove.submitter_note }</td>
		</tr>
		<tr>
			<td class="td_left">审批意见&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<td class="td_left">审批意见：</td>
			<td class="td_right" colspan="3"><c:if
					test="${accountInterApprove.audit_state==0}"> 待审批</c:if> <c:if
					test="${accountInterApprove.audit_state==1}"> 审批通过</c:if> <c:if
					test="${accountInterApprove.audit_state==2}"> 审批失败</c:if></td>
		</tr>
		<c:if test="${accountInterApprove.audit_state!=0}">
			<tr>
				<td class="td_left">审批人：</td>
				<td class="td_right_two">${accountInterApprove.approve_mana_name }</td>
			</tr>
			<tr>
				<td class="td_left">所属角色：</td>
				<td class="td_right_two">${accountInterApprove.approve_rolename }</td>
			</tr>
			<tr>
				<td class="td_left">审批时间：</td>
				<td class="td_right_two">${accountInterApprove.approve_time }</td>
			</tr>
			<tr>
				<td class="td_left">备注：</td>
				<td class="td_right" colspan="3">
					${accountInterApprove.approve_note }</td>
			</tr>
		</c:if>
	</table>
</div>

