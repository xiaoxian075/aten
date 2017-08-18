<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="table_div">
	<table width="100%">
		<tr >
			<td class="td_left">礼品编码<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text" name="integral_number" isrequired='yes'  datatype="jsLetterOrNum"  tipmsg="礼品编码"
						value="${integral.integral_number}" maxlength='20'   maxdatalength='20'/>
			</td>
		</tr>
		<tr>
			<td class="td_left">礼品名称<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="integral_goods_name" isrequired='yes'
				tipmsg="礼品名称" maxlength='60' maxdatalength='60'
				value="${integral.integral_goods_name}" /></td>
		</tr>
		<tr>
			<td class="td_left">所需积分值<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="integral_value" isrequired='yes' datatype='jsInt'
				tipmsg="所需积分值" maxlength='7' maxdatalength='7'
				value="${integral.integral_value}" /></td>
		</tr>
		<tr>
			<td class="td_left">礼品图片<span class="must_span">*</span></td>
			<td class="td_right_two"><c:set var="one_img_name"
					value="integral_goods_img" /> <c:set var="one_img_url"
					value="${integral.integral_goods_img}" /> <c:set var="one_img_tip"
					value="礼品图片" /> <%@ include
					file="/WEB-INF/common/one_image_show.jsp"%>
			</td>
		</tr>
		<tr>
			<td class="td_left">库存<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="stock" isrequired='yes' tipmsg="库存" maxlength='7'
				maxdatalength='7' datatype='jsInt' value="${integral.stock}" /></td>
		</tr>
		<tr>
			<td class="td_left">排序<span class="must_span">*</span></td>
			<td class="td_right" colspan="3"><input
				class="text validate sort_no" type="text" name="sort_no"
				isrequired="yes" datatype="jsInt" widthtip="100" tipmsg="排序"
				maxlength='6'  maxdatalength='6' value="${integral.sort_no}" /></td>
		</tr>
		<tr>
			<td class="td_left">礼品描述：</td>
			<td class="td_right_two"><textarea id="integral_detail" name="integral_detail" type="text"
					tipmsg="礼品描述">${integral.integral_detail}</textarea> <script
					type="text/javascript">
		 			var editor =new UE.ui.Editor();
		 			editor.render("integral_detail"); 
		 			/* UE.getEditor('news_detail_textarea'); */
 			    </script></td>
		</tr>
		<c:if test="${integral.integral_id!=null}">
			<input type="hidden" name="integral_id"
				value="${integral.integral_id}" />
		</c:if>

		<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
	</table>
</div>

