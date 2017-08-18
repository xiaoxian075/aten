<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">分类名称<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="cat_name" isrequired='yes' tipmsg="分类名称"
				maxlength='25' maxdatalength='25' value="${newscat.cat_name}" /></td>
		</tr>
		<c:if test="${!isEdit}">
		<tr>
			<td class="td_left">所属分类</td>
			<td class="td_right_two">
				<div id="cat_id_div" tipmsg="所属分类" setwidth="200" setheight="25"></div>
				<input class="validate" changetip="cat_id_div" type="hidden"
				id="cat_id" name="parent_cat_id" value="${newscat.parent_cat_id}" />
			</td>
		</tr>
		</c:if>
		<c:if test="${isEdit}">
		<tr>
			<td class="td_left">所属分类:</td>
			<td class="td_right_two">
				${newscat.parent_cat_id}
			</td>
		</tr>
		</c:if>
		<tr>
			<td class="td_left">排序<span class="must_span">*</span></td>
			<td class="td_right" colspan="3"><input
				class="text validate sort_no" type="text" name="sort_no"
				isrequired="yes" datatype="jsInt" widthtip="100" tipmsg="排序"
				maxlength='6' maxdatalength='6' value="${newscat.sort_no}" /></td>
		</tr>
		<tr>
			<td class="td_left">是否启用<span class="must_span">*</span></td>
			<td class="td_right" colspan="3"><select class="validate"
				name="state" isrequired="yes" type="select" tipmsg="是否启用"
				widthtip="70">
					<option value="">请选择</option>
					<option value="1"
						<c:if test="${1==newscat.state}"> selected</c:if>>启用</option>
						<option value="0" <c:if test="${0==newscat.state}"> selected</c:if>>禁用</option>
				</select>
			</td>
		</tr>
<c:if test="${newscat.cat_id!=null}">
	<input type="hidden" name="cat_id" value="${newscat.cat_id}"/>
</c:if>

<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
	</table>
</div>
<script type="text/javascript">
    $(function () {
    	$("#cat_id_div").cascadeSel({
            url:"/admin/newscat/normalList",
            name:"cat_id",
            init_id:"${cfg_news_cat}"
        });
    });
</script>
