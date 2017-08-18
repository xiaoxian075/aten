<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">品牌名称<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="brand_name" isrequired='yes' tipmsg="品牌名称"
				maxlength='20' maxdatalength='20' value="${brand.brand_name}" /></td>
		</tr>
		<tr>
			<td class="td_left">品牌logo<span class="must_span">*</span></td>
			<td class="td_right_two">
				<c:set var="one_img_name" value="brand_logo" />
				<c:set var="one_img_url" value="${brand.brand_logo}" />
				<c:set var="one_img_tip" value="请上传品牌logo" />
				<c:set var="one_img_proposal" value="图片大小不能大于2M"/>
				<%@ include file="/WEB-INF/common/one_image_show.jsp"%>
			</td>
		</tr>
		<tr>
			<td class="td_left">排序<span class="must_span">*</span></td>
			<td class="td_right" colspan="3"><input
				class="text validate sort_no" type="text" name="sort_no"
				isrequired="yes" datatype="jsInt" widthtip="100" tipmsg="排序"
				maxlength='6'  maxdatalength='6' value="${brand.sort_no}" /></td>
		</tr>
		<tr>
			<td class="td_left">是否启用<span class="must_span">*</span></td>
			<td class="td_right" colspan="3"><select class="validate"
				name="state" isrequired="yes" type="select" tipmsg="是否启用"
				widthtip="70">
					<option value="">请选择</option>
					<option value="1"<c:if test="${1==brand.state}"> selected</c:if>>启用</option>
						<option value="0" <c:if test="${0==brand.state}"> selected</c:if>>禁用</option>
				</select>
			</td>
		</tr>
<c:if test="${brand.brand_id!=null}">
	<input type="hidden" name="brand_id" value="${brand.brand_id}"/>
</c:if>

<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
	</table>
</div>

