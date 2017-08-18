<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left"><span style="font-size: 20px;">会员信息</span></td>
		</tr>

		<tr>
			<td class="td_left">会员账号<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="account_id" tipmsg="帐户id" maxlength='20' maxdatalength='20'
				value="${accountWithdrawBill.account_id}" /></td>
		</tr>
		<tr>
			<td class="td_left">当前可提现收益<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="amount" tipmsg="提现金额" maxlength='15,0' maxdatalength='15,0'
				value="${accountWithdrawBill.amount}" /></td>
		</tr>
		<tr>
			<td class="td_left">提现金额<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="amount" tipmsg="提现金额" maxlength='15,0' maxdatalength='15,0'
				value="${accountWithdrawBill.amount}" /></td>
		</tr>
		<tr>
			<td class="td_left">提现时间<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="create_time" tipmsg="提现时间"
				value="${accountWithdrawBill.create_time}" /></td>
		</tr>
		<tr>
			<td class="td_left">银行开户行<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="opening_bank" tipmsg="银行开户行" maxlength='128'
				maxdatalength='128' value="${accountWithdrawBill.opening_bank}" /></td>
		</tr>
		<tr>
			<td class="td_left">银行卡号<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="card_no" tipmsg="银行卡号" maxlength='60' maxdatalength='60'
				value="${accountWithdrawBill.card_no}" /></td>
		</tr>
		<tr>
			<td class="td_left"></td>
			<td class="td_right_two"></td>
		</tr>
		<tr>
			<td class="td_left"><span style="font-size: 20px;">审批意见</span></td>
		</tr>

		<tr>
			<td class="td_left">是否同意<span class="sp_span">:</span></td>
			<td class="td_right_two"><input type="radio" value="1"
				name="audit_state" checked="checked">同意 <input type="radio"
				value="2" name="audit_state">不同意
		</tr>
		<tr>
			<td class="td_left">备注<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="audit_note" tipmsg="备注" maxlength='10' maxdatalength='10'
				value="${accountWithdrawBill.audit_note}" /></td>
		</tr>



		<c:if test="${accountWithdrawBill.id!=null}">
			<input type="hidden" name="id" value="${accountWithdrawBill.id}" />
		</c:if>

		<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
	</table>
</div>

