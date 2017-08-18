<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">属性名称<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text" name="attr_name"   isrequired='yes'  tipmsg="属性名称"
		 		  maxlength='16'  maxdatalength='16'    value="${attr.attr_name}"/>
				<input   type="hidden" name="old_attr_name" value="${attr.attr_name}"/>
			</td>
		</tr>

		<tr>
			<td class="td_left">是否启用<span class="must_span">*</span></td>
			<td class="td_right_two"><select class="validate" name="state"
				isrequired='yes' type="select" tipmsg="是否启用" widthtip="100">
					<option value="">请选择</option>
					<option value="1"
						<c:if test="${attr.state==1}"> selected</c:if>>启用</option>
					<option value="0" <c:if test="${attr.state==0}"> selected</c:if>>禁用</option>
				</select>
			</td>
		</tr>

<c:if test="${attr.attr_id!=null}">
	<input type="hidden" name="parameter_id" value="${attr.attr_id}"/>
	<input type="hidden" name="attr_id" value="${attr.attr_id}"/>
</c:if>

<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
	</table>
</div>

