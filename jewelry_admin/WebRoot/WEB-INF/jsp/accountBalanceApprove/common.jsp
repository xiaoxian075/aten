<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/common/img_control.jsp"%>
<div class="table_div">
	<table width="100%">
		<tr></tr>
		<tr>
			<td class="td_left">会员账户：</td>
			<td class="td_right_two">${account.login_name }</td>
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
		<c:if test="${act==2 }">
			<tr>
				<td class="td_left">充值金额<span class="must_span">*</span></td>
				<td class="td_right_two"><input class="text validate "
					type="text" name="approve_amount" isrequired="yes" datatype="jsRmb"
					tipmsg="充值金额" maxlength='6' maxdatalength='6' value="" /></td>
			</tr>
		</c:if>
		<c:if test="${act==1 }">
			<tr>
				<td class="td_left">扣款金额<span class="must_span">*</span></td>
				<td class="td_right_two"><input class="text validate "
					type="text" name="approve_amount" isrequired="yes" datatype="jsRmb"
					tipmsg="扣款金额" maxlength='6' maxdatalength='6' value="" /></td>
			</tr>
		</c:if>
		<tr>
			<td class="td_left">上传凭证：</td>
			<td class="td_right_two"><c:set var="one_img_name"
					value="submitter_img" /> <c:set var="one_img_url" value="" /> <c:set
					var="one_img_tip" value="" /> <c:set var="one_img_must" value="no" />
				<%@ include file="/WEB-INF/common/one_image_show.jsp"%>
			</td>
		</tr>
		<tr>
			<td class="td_left">备注：</td>
			<td class="td_right" colspan="3"><textarea class="validate"
					name="submitter_note" rows="3" cols="40" type="text"
					maxlength='50' maxdatalength='100'></textarea></td>
		</tr>
		<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
	</table>
	<input type="hidden" name="account_id" value="${account.id}" /> <input
		type="hidden" name="login_name" value="${account.login_name}" /> <input
		type="hidden" name="io_type" value="${act}" />
</div>
