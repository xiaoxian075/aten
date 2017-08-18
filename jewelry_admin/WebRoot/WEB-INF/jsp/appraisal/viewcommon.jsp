<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">鉴定机构名称：</td>
			<td class="td_right_two">${appraisal.appraisal_name}</td>
		</tr>
		<tr>
			<td class="td_left">归属地：</td>
			<td class="td_right_two">${appraisal.the_area}</td>
		</tr>
		<tr>
			<td class="td_left">联系人：</td>
			<td class="td_right_two">${appraisal.appraisal_contacts}</td>
		</tr>
		<tr>
			<td class="td_left">联系方式：</td>
			<td class="td_right_two">${appraisal.contacts_way}</td>
		</tr>
		<tr>
			<td class="td_left">排序：</td>
			<td class="td_right" colspan="3">${appraisal.sort_no}</td>
		</tr>
		<tr>
			<td class="td_left">是否启用：</td>
			<td class="td_right" colspan="3"><c:if
					test="${1==appraisal.state}"> 启用</c:if> <c:if
					test="${0==appraisal.state}"> 禁用</c:if></td>
		</tr>
		<tr>
			<td class="td_left">备注：</td>
			<td class="td_right" colspan="3">${appraisal.note}</td>
		</tr>

		<c:if test="${appraisal.appraisal_id!=null}">
			<input type="hidden" name="appraisal_id"
				value="${appraisal.appraisal_id}" />
		</c:if>

		<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
	</table>
</div>

<script type="text/javascript">
    $(document).ready(function(){
        //地区级联
        $("#area_id_div").cascadeSel({
            url:"/admin/area/normalList",
            name:"area_id",
            init_id:"${cfg_top_area}",
            li_id:"area_id",
            li_name:"area_name",
        });
    });
</script>