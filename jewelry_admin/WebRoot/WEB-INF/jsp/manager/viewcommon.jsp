<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript"> 
$(document).ready(function(){ 
	//地区级联
	$("#org_id_div").cascadeSel({
		 url:"/admin/organize/normalList",
		 name:"org_id",
		 init_id:"1111111111",//待改成动态
		 li_id:"org_id",
		 li_name:"org_name",
	});	
	$("select").attr("disabled",true);
});
</script>
<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">用户名<span class="must_span">*</span></td>
			<td class="td_right_two">${manager.mana_name}</td>
		</tr>

		<tr>
			<td class="td_left">姓名<span class="must_span">*</span></td>
			<td class="td_right_two">${manager.real_name}</td>
		</tr>

		<tr>
			<td class="td_left">性别<span class="must_span">*</span></td>
			<td class="td_right_two"><c:if test="${manager.sex==1}">男 </c:if>
				<c:if test="${manager.sex==0}">女 </c:if></td>
		</tr>



		<tr>
			<td class="td_left">所属部门<span class="must_span">*</span></td>
			<td class="td_right_two">${organize_name}</td>
		</tr>

		<tr>
			<td class="td_left">所属角色<span class="must_span">*</span></td>
			<td class="td_right_two">${role_name}</td>
		</tr>

		<tr>
			<td class="td_left">手机<span class="must_span">*</span></td>
			<td class="td_right_two">${manager.phone}</td>
		</tr>
		<tr>
			<td class="td_left">QQ<span class="sp_span">:</span></td>
			<td class="td_right_two">${manager.qq}</td>
		</tr>
		<tr>
			<td class="td_left">邮箱<span class="sp_span">:</span></td>
			<td class="td_right_two">${manager.email}</td>
		</tr>


	</table>
</div>

<input type="hidden" name="mana_id" value="${manager.mana_id}" />
<input type="hidden" class="mana_type" name="mana_type"
	value="${manager.mana_type}">
<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>