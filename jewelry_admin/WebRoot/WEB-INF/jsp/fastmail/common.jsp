<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">物流承运商编号<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" id="fast_code" name="fast_code" isrequired='yes'
				tipmsg="物流承运商" maxlength='32' mindatalength='1' maxdatalength='32'
				datatype='jsLetter' value="${fastmail.fast_code}"
				onchange="checkfastcode()" /></td>
		</tr>
		<tr>
			<td class="td_left">物流承运商名称<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" id="fast_name" name="fast_name" isrequired='yes'
				tipmsg="物流承运商名称" maxlength='32' maxdatalength='32'
				value="${fastmail.fast_name}" onchange="checkfastname()" /></td>
		</tr>
		<tr>
			<td class="td_left">物流承运商logo<span class="must_span">*</span></td>
			<td class="td_right_two">
				<c:set var="one_img_name" value="fast_logo" /> 
				<c:set var="one_img_url" value="${fastmail.fast_logo}" /> 
				<c:set var="one_img_tip" value="请上传配送公司logo!" /> 
				<c:set var="one_img_proposal" value="图片大小不能大于2M"/>
				<%@ include file="/WEB-INF/common/one_image_show.jsp"%>
			</td>
		</tr>
		<%-- <tr>
			<td class="td_left">是否支持保价<span class="must_span">*</span></td>
			<td class="td_right_two"><select name="is_insured" type="select">
					<option value="1"
						<c:if test="${fastmail.is_insured==1}"> selected</c:if>>是</option>
					<option value="0" <c:if test="${fastmail.is_insured==0}"> selected</c:if>>否</option>
				</select>
			</td>
		</tr>
<tr>
	<td colspan="2">
		<div class="line_div ">
 			<div class="detail_div">
 			<p class="detail_div_p">
 				<b class="detail_div_b">配送方式描述<span class="sp_span">:</span></b>
 			</p>
	 		<div class="ueditor_content">
	 			  <textarea id="fast_desc_textarea" name="fast_desc">${fastmail.fast_desc}</textarea>
	 			  <script>
			 			  var editor =new UE.ui.Editor();
			 			  UE.getEditor('fast_desc_textarea');
	 			  </script> 
	 		</div>
			</div>
		</div>
	</td>
</tr>
		<tr>
			<td class="td_left">保价费率<span class="sp_span">:</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text" name="rate"  isrequired='yes'  tipmsg="保价费率" 
		 		  maxlength='15'  maxdatalength='15'  datatype='jsRmb'  
		 		  <c:choose>
		 		  	<c:when test="${empty fastmail }">value="0"</c:when>
		 		  	<c:otherwise>value="${fastmail.rate}"</c:otherwise>
		 		  </c:choose> 
		 		  	/>
			</td>
		</tr>
		<tr>
			<td class="td_left">最低保价额<span class="sp_span">:</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text" name="mix_insured"  isrequired='yes'  tipmsg="最低保价额" 
		 		  maxlength='15'  maxdatalength='15' datatype='jsRmb'
		 		 <c:choose>
		 		  	<c:when test="${empty fastmail }">value="0"</c:when>
		 		  	<c:otherwise>value="${fastmail.mix_insured}"</c:otherwise>
		 		  </c:choose> 
		 		  />
			</td>
		</tr>
		<tr>
			<td class="td_left">最高保价额<span class="sp_span">:</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text" name="max_insured" isrequired='yes'   tipmsg="最高保价额" 
		 		  maxlength='15'  maxdatalength='15'  datatype='jsRmb'
		 		 <c:choose>
		 		  	<c:when test="${empty fastmail }">value="0"</c:when>
		 		  	<c:otherwise>value="${fastmail.max_insured}"</c:otherwise>
		 		  </c:choose> 
		 		  />
			</td>
		</tr>
		<tr>
			<td class="td_left">是否支持到付<span class="sp_span">:</span></td>
			<td class="td_right_two">
				<select name="is_reach_pay" type="select" >
					<option value="1" <c:if test="${fastmail.is_reach_pay==1}"> selected</c:if>>是</option>
					<option value="0" <c:if test="${fastmail.is_reach_pay==0}"> selected</c:if>>否</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="td_left">配送方式的默认快递模板<span class="sp_span">:</span></td>
			<td class="td_right_two">
				<input  class="text " type="text" name="default_temp"  isrequired='yes'  tipmsg="配送方式的默认快递模板" 
		 		  maxlength='20000'  maxdatalength='20000'    value="${fastmail.default_temp}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">快运单号格式<span class="sp_span">:</span></td>
			<td class="td_right_two">
				<input  class="text " type="text" name="waybill_rule"  isrequired='yes'  tipmsg="快运单号格式" 
		 		  maxlength='100'  maxdatalength='100'    value="${fastmail.waybill_rule}"/>
			</td>
		</tr> --%>
		<tr>
			<td class="td_left">状态<span class="sp_span">:</span></td>
			<td class="td_right_two">
				<select name="state" type="select" isrequired='yes'  tipmsg="状态"  >
					<option value="1" <c:if test="${fastmail.state==1}"> selected</c:if>>启用</option>
					<option value="0" <c:if test="${fastmail.state==0}"> selected</c:if>>禁用</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="td_left">排序<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text" name="sort_no"  isrequired='yes'  tipmsg="排序" 
		 		  maxlength='6'  maxdatalength='6'    value="${fastmail.sort_no}" style="width:50px;"/>
			</td>
		</tr>
<c:if test="${fastmail.fast_id!=null}">
	<input type="hidden" name="fast_id" value="${fastmail.fast_id}"/>
</c:if>

<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
	</table>
</div>

