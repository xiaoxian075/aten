<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">地名名称:</td>
			<td class="td_right_two">${area.area_name}</td>
		</tr>
		<tr>
			<td class="td_left">地区行政编码:</td>
			<td class="td_right_two">${area.xz_code}</td>
		</tr>
		<tr>
			<td class="td_left">行政级别:</td>
			<td class="td_right_two">${area.area_level_name}</td>
		</tr>
		<tr>
			<td class="td_left">状态:</td>
			<td class="td_right_two"><c:if test="${area.state==0}">
					<span style="color:red">已禁用</span>
				</c:if> <c:if test="${area.state==1}">
					<span style="color:green">已启用</span>
				</c:if></td>
		</tr>
		<tr>
			<td class="td_left">上级地区:</td>
			<td class="td_right_two">${area.parent_area_name}</td>
		</tr>
	</table>
</div>

<input type="hidden" name="place_id" value="${area.area_id}" />
