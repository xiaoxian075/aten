<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
			<td class="td_left">是否同意：</td>
			<td class="td_right" colspan="3"><label><input
					name="audit_state" type="radio" checked value="1" />同意</label> <label><input
					name="audit_state" type="radio" value="2" />不同意 </label></td>
		</tr>
		<tr>
			<td class="td_left">备注：</td>
			<td class="td_right" colspan="3"><textarea class="validate"
					name="approve_note" rows="3" cols="40" type="text"
					maxlength='50' maxdatalength='100'></textarea></td>
		</tr>
	</table>
	<input type="hidden" name="ia_id" value="${accountInterApprove.ia_id }" />
	<input type="hidden" name="io_type"
		value="${accountInterApprove.io_type}" />
</div>

