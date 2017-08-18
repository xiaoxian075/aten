<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">鉴定机构名称<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="appraisal_name" isrequired="yes" tipmsg="鉴定机构名称"
				maxlength='100' maxdatalength='100'
				value="${appraisal.appraisal_name}" /></td>
		</tr>
		<tr>
			<td class="td_left">归属地<span class="must_span">*</span></td>
			<td class="td_right_two">
				<div id="area_id_div" tipmsg="归属地" setwidth="200" setheight="25"></div>
				<input class="validate" changetip="area_id_div" type="hidden"
				id="area_id" name="the_area" isrequired='yes'
				value="${appraisal.the_area}" />
			</td>
		</tr>
		<tr>
			<td class="td_left">联系人<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="appraisal_contacts" isrequired="yes" tipmsg="联系人"
				maxlength='50' maxdatalength='50'
				value="${appraisal.appraisal_contacts}" /></td>
		</tr>
		<tr>
			<td class="td_left">联系方式<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="contacts_way" isrequired="yes"
				tipmsg="联系方式" maxlength='20' maxdatalength='20'
				value="${appraisal.contacts_way}" /></td>
		</tr>
		<tr>
			<td class="td_left">排序<span class="must_span">*</span></td>
			<td class="td_right" colspan="3"><input
				class="text validate sort_no" type="text" name="sort_no"
				isrequired="yes" datatype="jsInt" widthtip="100" tipmsg="排序"
				maxlength='6' maxdatalength='6' value="${appraisal.sort_no}" /></td>
		</tr>
		<tr>
			<td class="td_left">是否启用<span class="must_span">*</span></td>
			<td class="td_right" colspan="3"><select class="validate"
				name="state" isrequired="yes" type="select" tipmsg="是否启用"
				widthtip="70">
					<option value="">请选择</option>
					<option value="1"
						<c:if test="${1==appraisal.state}"> selected</c:if>>启用</option>
						<option value="0" <c:if test="${0==appraisal.state}"> selected</c:if>>禁用</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="td_left">备注<span class="sp_span">:</span></td>
			<td class="td_right" colspan="3">
				<textarea class="validate" name="note" rows="3" cols="40" type="text" maxlength='200' maxdatalength='200'>${appraisal.note}</textarea>
			</td>
		</tr>
<c:if test="${appraisal.appraisal_id!=null}">
	<input type="hidden" name="appraisal_id" value="${appraisal.appraisal_id}"/>
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
			showLevel:"2"
        });
    });
</script>