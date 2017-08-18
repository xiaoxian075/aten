<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">-<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="cat_id" tipmsg="-" maxlength='char(10)'
				maxdatalength='char(10)' value="${catAttr.cat_id}" /></td>
		</tr>
		<tr>
			<td class="td_left">-<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="attr_id" tipmsg="-" maxlength='bigint(20)'
				maxdatalength='bigint(20)' value="${catAttr.attr_id}" /></td>
		</tr>
		<tr>
			<td class="td_left">-<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="show_type" tipmsg="-" maxlength='tinyint(4)'
				maxdatalength='tinyint(4)' value="${catAttr.show_type}" /></td>
		</tr>
		<tr>
			<td class="td_left">-<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="option_type" tipmsg="-" maxlength='tinyint(4)'
				maxdatalength='tinyint(4)' value="${catAttr.option_type}" /></td>
		</tr>
		<tr>
			<td class="td_left">-<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="is_alisa" tipmsg="-" maxlength='tinyint(4)'
				maxdatalength='tinyint(4)' value="${catAttr.is_alisa}" /></td>
		</tr>
		<tr>
			<td class="td_left">-<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="is_sku" tipmsg="-" maxlength='tinyint(4)'
				maxdatalength='tinyint(4)' value="${catAttr.is_sku}" /></td>
		</tr>
		<tr>
			<td class="td_left">-<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="is_key" tipmsg="-" maxlength='tinyint(4)'
				maxdatalength='tinyint(4)' value="${catAttr.is_key}" /></td>
		</tr>
		<tr>
			<td class="td_left">-<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="is_index" tipmsg="-" maxlength='tinyint(4)'
				maxdatalength='tinyint(4)' value="${catAttr.is_index}" /></td>
		</tr>
		<tr>
			<td class="td_left">-<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="is_must" tipmsg="-" maxlength='tinyint(4)'
				maxdatalength='tinyint(4)' value="${catAttr.is_must}" /></td>
		</tr>
		<tr>
			<td class="td_left">-<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="is_custom_value" tipmsg="-" maxlength='tinyint(4)'
				maxdatalength='tinyint(4)' value="${catAttr.is_custom_value}" /></td>
		</tr>
		<c:if test="${catAttr.ident_id!=null}">
			<input type="hidden" name="ident_id" value="${catAttr.ident_id}" />
		</c:if>

		<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
	</table>
</div>

