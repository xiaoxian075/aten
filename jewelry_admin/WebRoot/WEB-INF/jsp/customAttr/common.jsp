<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">-<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="custom_value_id" isrequired='yes' tipmsg="-"
				maxlength='22' maxdatalength='22'
				value="${customAttr.custom_value_id}" /></td>
		</tr>
		<tr>
			<td class="td_left">-<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="attr_id" isrequired='yes' tipmsg="-"
				maxlength='22' maxdatalength='22' value="${customAttr.attr_id}" /></td>
		</tr>
		<tr>
			<td class="td_left">-<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="attr_value_id" tipmsg="-" maxlength='22' maxdatalength='22'
				value="${customAttr.attr_value_id}" /></td>
		</tr>
		<tr>
			<td class="td_left">-<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="custom_attr_value" isrequired='yes' tipmsg="-"
				maxlength='100' maxdatalength='100'
				value="${customAttr.custom_attr_value}" /></td>
		</tr>
		<tr>
			<td class="td_left">-<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="attr_type" isrequired='yes' tipmsg="-"
				maxlength='1' maxdatalength='1' value="${customAttr.attr_type}" /></td>
		</tr>
		<tr>
			<td class="td_left">-<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="quote_id" isrequired='yes' tipmsg="-"
				maxlength='22' maxdatalength='22' value="${customAttr.quote_id}" />
			</td>
		</tr>

	</table>
</div>

