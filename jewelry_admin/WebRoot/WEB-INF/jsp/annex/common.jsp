<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">-<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="back_id" tipmsg="-" maxlength='int(11)'
				maxdatalength='int(11)' value="${annex.back_id}" /></td>
		</tr>
		<tr>
			<td class="td_left">-<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="the_cat" tipmsg="-" maxlength='varchar(60)'
				maxdatalength='varchar(60)' value="${annex.the_cat}" /></td>
		</tr>
		<tr>
			<td class="td_left">-<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="back_type" tipmsg="-" maxlength='tinyint(4)'
				maxdatalength='tinyint(4)' value="${annex.back_type}" /></td>
		</tr>
		<tr>
			<td class="td_left">-<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="info_id" tipmsg="-" maxlength='char(32)'
				maxdatalength='char(32)' value="${annex.info_id}" /></td>
		</tr>
		<tr>
			<td class="td_left">-<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="annex_type" tipmsg="-" maxlength='char(1)'
				maxdatalength='char(1)' value="${annex.annex_type}" /></td>
		</tr>
		<tr>
			<td class="td_left">-<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="up_file_name" tipmsg="-" maxlength='varchar(100)'
				maxdatalength='varchar(100)' value="${annex.up_file_name}" /></td>
		</tr>
		<tr>
			<td class="td_left">-<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="annex_url" tipmsg="-" maxlength='varchar(100)'
				maxdatalength='varchar(100)' value="${annex.annex_url}" /></td>
		</tr>
		<tr>
			<td class="td_left">-<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="remark" tipmsg="-" maxlength='varchar(100)'
				maxdatalength='varchar(100)' value="${annex.remark}" /></td>
		</tr>
		<tr>
			<td class="td_left">-<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="in_date" tipmsg="-" maxlength='datetime'
				maxdatalength='datetime' value="${annex.in_date}" /></td>
		</tr>
		<tr>
			<td class="td_left">-<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="sort_no" tipmsg="-" maxlength='int(11)'
				maxdatalength='int(11)' value="${annex.sort_no}" /></td>
		</tr>
		<tr>
			<td class="td_left">-<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="is_del" tipmsg="-" maxlength='char(1)' maxdatalength='char(1)'
				value="${annex.is_del}" /></td>
		</tr>
		<tr>
			<td class="td_left">-<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="file_size" tipmsg="-" maxlength='int(11)'
				maxdatalength='int(11)' value="${annex.file_size}" /></td>
		</tr>
		<c:if test="${annex.annex_id!=null}">
			<input type="hidden" name="annex_id" value="${annex.annex_id}" />
		</c:if>

		<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
	</table>
</div>

