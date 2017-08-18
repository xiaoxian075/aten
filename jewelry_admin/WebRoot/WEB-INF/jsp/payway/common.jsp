<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/common/img_control.jsp"%>
<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">支付方式编码<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="pay_id" isrequired='yes' tipmsg="支付方式编码"
				maxlength='20' maxdatalength='20' value="${payway.pay_id}" /></td>
		</tr>
		<tr>
			<td class="td_left">支付方式名称<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="pay_name" isrequired='yes' tipmsg="支付方式名称"
				maxlength='30' maxdatalength='30' value="${payway.pay_name}" /></td>
		</tr>
		<tr>
			<td class="td_left">key<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="pay_key" isrequired='yes' tipmsg="key"
				maxlength='50' maxdatalength='50' value="${payway.pay_key}" /></td>
		</tr>
		<tr>
			<td class="td_left">密钥<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="pay_secret" isrequired='yes' tipmsg="密钥"
				maxlength='50' maxdatalength='50' value="${payway.pay_secret}" /></td>
		</tr>
		<tr>
			<td class="td_left">支付方式图标<span class="must_span">*</span></td>
			<td class="td_right_two"><c:set var="one_img_name"
					value="pay_img" /> <c:set var="one_img_url"
					value="${payway.pay_img}" /> <c:set var="one_img_tip"
					value="请上传支付方式图标" /> <%@ include
					file="/WEB-INF/common/one_image_show.jsp"%>
				<%--<input  class="text validate" type="text" name="pay_img"  isrequired='yes'  tipmsg="-" --%>
				<%--maxlength='60'  maxdatalength='60'    value="${payway.pay_img}"/>--%>
			</td>
		</tr>
		<tr>
			<td class="td_left">接口地址<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="pay_url" isrequired='yes' tipmsg="接口地址"
				maxlength='60' maxdatalength='60' value="${payway.pay_url}" /></td>
		</tr>
		<tr>
			<td class="td_left">是否支持IOS支付<span class="must_span">*</span></td>
			<td class="td_right_two"><select class="validate selectClass"
				name="is_ios_pay" type="select" isrequired='yes' setwidth="200"
				tipmsg="是否支持IOS支付">
					<option value="">请选择</option>
					<option value="1"
						<c:if test="${payway.is_ios_pay==1}"> selected</c:if>>是</option>
					<option value="0"  <c:if test="${payway.is_ios_pay==0}"> selected</c:if>>否</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="td_left">是否支持安卓支付<span class="must_span">*</span></td>
			<td class="td_right_two">
				<select class="validate selectClass" name="is_android_pay" type="select" isrequired='yes' setwidth="200"  tipmsg="是否支持安卓支付" >
					<option value="">请选择</option>
					<option value="1"  <c:if test="${payway.is_android_pay==1}"> selected</c:if>>是</option>
					<option value="0"  <c:if test="${payway.is_android_pay==0}"> selected</c:if>>否</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="td_left">是否支持WEB支付<span class="must_span">*</span></td>
			<td class="td_right_two">
				<select class="validate selectClass" name="is_web_pay" type="select" isrequired='yes' setwidth="200"  tipmsg="是否支持WEB支付" >
					<option value="">请选择</option>
					<option value="1"  <c:if test="${payway.is_web_pay==1}"> selected</c:if>>是</option>
					<option value="0"  <c:if test="${payway.is_web_pay==0}"> selected</c:if>>否</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="td_left">排序<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="sort_no validate" type="text" name="sort_no"  isrequired="yes" datatype="jsInt" 
 					  widthtip="100" tipmsg="排序" 	maxlength="6"    value="${payway.sort_no}"/>
			</td>
		</tr>
		
<c:if test="${payway.pay_id!=null}">
	<input type="hidden" name="old_pay_id" value="${payway.pay_id}"/>
</c:if>
		<input type="hidden" name="parameter_id" value="${parameter_id}">
<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
	</table>
</div>

