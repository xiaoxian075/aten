<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">-<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="rate_id" tipmsg="-" maxlength='bigint(20)'
				maxdatalength='bigint(20)' value="${divideRecord.rate_id}" /></td>
		</tr>
		<tr>
			<td class="td_left">-<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="divide_rate" tipmsg="-" maxlength='tinyint(4)'
				maxdatalength='tinyint(4)' value="${divideRecord.divide_rate}" /></td>
		</tr>
		<tr>
			<td class="td_left">-<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="oper_man_id" tipmsg="-" maxlength='bigint(20)'
				maxdatalength='bigint(20)' value="${divideRecord.oper_man_id}" /></td>
		</tr>
		<tr>
			<td class="td_left">-<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="oper_man" tipmsg="-" maxlength='varchar(50)'
				maxdatalength='varchar(50)' value="${divideRecord.oper_man}" /></td>
		</tr>
		<tr>
			<td class="td_left">-<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="oper_time" tipmsg="-" maxlength='datetime'
				maxdatalength='datetime' value="${divideRecord.oper_time}" /></td>
		</tr>
		<c:if test="${divideRecord.dr_record!=null}">
			<input type="hidden" name="dr_record"
				value="${divideRecord.dr_record}" />
		</c:if>

		<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
	</table>
</div>

