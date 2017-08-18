<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">属性值<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text" name="attr_value"  isrequired='yes'  tipmsg="属性值" 
		 		  maxlength='32'  maxdatalength='32'    value="${attrValue.attr_value}"/>
				<input   type="hidden" name="old_attr_value" value="${attrValue.attr_value}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">排序<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="sort_no validate sort_no" type="text" name="sort_no"  isrequired="yes" datatype="jsInt"
						maxlength='6'  maxdatalength='6'  tipmsg="排序" 	maxlength="6"    value="${attrValue.sort_no}"/>
			</td>
		</tr>
				<%--<tr>--%>
			<%--<td class="td_left">图标:</td>--%>
			<%--<td class="td_right_two">--%>
		 		 <%--<c:set var="one_img_name" value="attr_value_ico"/>--%>
				 <%--<c:set var="one_img_url" value="${attrValue.attr_value_ico}"/>--%>
				 <%--<c:set var="one_img_tip" value="请上传图标!"/>--%>
				 <%--<c:set var="one_img_must" value="no"/>--%>
				 <%--<%@ include file="/WEB-INF/common/one_image_show.jsp"%>--%>
			<%--</td>--%>
		<%--</tr>--%>
		<input type="hidden" name="parameter_id" value="${parameter_id}"/>
<c:if test="${attrValue.attr_value_id!=null}">
	<input type="hidden" name="attr_value_id" value="${attrValue.attr_value_id}"/>
</c:if>
<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
	</table>
</div>

