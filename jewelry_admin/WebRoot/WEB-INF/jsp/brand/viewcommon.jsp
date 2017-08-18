<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">品牌名称<span class="must_span">*</span></td>
			<td class="td_right_two">${brand.brand_name}</td>
		</tr>
		<tr>
			<td class="td_left">品牌编码<span class="must_span">*</span></td>
			<td class="td_right_two">${brand.en_name}</td>
		</tr>

		<tr>
			<td class="td_left">状态<span class="must_span">*</span></td>
			<td class="td_right_two"><c:if test="${brand.state==1}"> 已启用</c:if>
				<c:if test="${brand.state==0}"> 已禁用</c:if></td>
		</tr>

		<tr>
			<td class="td_left">品牌LOGO<span class="must_span">*</span></td>
			<td class="td_right_two"><c:set var="one_img_url"
					value="${brand.brand_logo}" /> <%@ include
					file="/WEB-INF/common/one_image_view.jsp"%>
			</td>
		</tr>

		<tr>
			<td class="td_left">排序<span class="must_span">*</span></td>
			<td class="td_right_two">${brand.sort_no}</td>
		</tr>

	</table>
</div>


<input type="hidden" name="word_index" value="1" />
<input type="hidden" name="is_hot" value="1" />
<input type="hidden" name="introduction" value="1" />
<input type="hidden" name="brand_site" value="1" />
<input type="hidden" name="brand_id" value="${brand.brand_id}" />
<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
