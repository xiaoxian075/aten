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
});
</script>
<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">用户名<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="mana_name" isrequired="yes" tipmsg="用户名"
				mindatalength="6" maxlength="50" maxdatalength="50"
				datatype="jsLetterNum" value="${manager.mana_name}" /> <input
				type="hidden" name="old_mana_name" value="${manager.mana_name}" /></td>
		</tr>

		<tr>
			<td class="td_left">姓名<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="real_name" isrequired="yes" tipmsg="姓名"
				maxlength="20" maxdatalength='20' value="${manager.real_name}" /></td>
		</tr>

		<tr>
			<td class="td_left">性别<span class="must_span">*</span></td>
			<td class="td_right_two"><select class="validate" name="sex"
				isrequired="yes" type="select" tipmsg="性别" widthtip="70">
					<option value="">请选择</option>
					<option value="1"
						<c:if test="${manager.sex==1}"> selected</c:if>>男</option>
					<option value="0" <c:if test="${manager.sex==0}"> selected</c:if>>女</option>
				</select>
			</td>
		</tr>
		
		<tr style="display:none;">
			<td class="td_left">头像<span class="sp_span">:</span></td>
			<td class="td_right_two">
				 <c:set var="one_img_name" value="header_img"/>
				 <c:set var="one_img_url" value="${manager.header_img}"/>
				 <c:set var="one_img_tip" value="请上传头像!"/>
				 <c:set var="one_img_must" value="no"/>
				 <%@ include file="/WEB-INF/common/one_image_show.jsp"%>
			</td>
		</tr>

		<tr>
			<td class="td_left">所属部门<span class="must_span">*</span></td>
			<td class="td_right_two">
				<div id="org_id_div" tipmsg="所属部门" setwidth="200" setheight="25"></div>
	 			 <input class="validate" changetip="org_id_div" type="hidden"  id="org_id"  name="the_org" isrequired="yes" value="${manager.the_org}"/>
			</td>
		</tr>
		
		<tr>
			<td class="td_left">所属角色<span class="must_span">*</span></td>
			<td class="td_right_two">
				<select class="validate" id="role_code"  name="role_code"  isrequired="yes" type="select" tipmsg="所属角色" widthtip="70">
					<option value="">请选择</option>
					<c:if test="${!empty roleList}">
						<c:forEach items="${roleList}" var="item" varStatus="status">
							<option value="${item.role_code}"
								<c:if test="${item.role_code==manager.role_code}">selected</c:if>>${item.role_name}</option>
						</c:forEach>
					</c:if>
				</select>
			</td>
		</tr>
		
		<tr>
			<td class="td_left">手机<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text"  isrequired="yes" name="phone"   datatype="jsPhone"
 					maxlength="12"  maxdatalength='12'  tipmsg="手机号"     value="${manager.phone}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">QQ<span class="sp_span">:</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text" name="qq"  tipmsg="QQ" 
 					maxlength="12"  maxdatalength='12'       value="${manager.qq}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">邮箱<span class="sp_span">:</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text" name="email"  datatype="jsEmail"  tipmsg="邮箱" 
 					maxlength="60"  maxdatalength='60'        value="${manager.email}"/>
			</td>
		</tr>
		<tr style="display:none;">
			<td class="td_left">商户状态<span class="must_span">*</span></td>
			<td class="td_right_two">
				<select class="validate"  name="state"  isrequired="yes" type="select"  widthtip="70">
						<option value="1" <c:if test="${manager.state==1}"> selected</c:if>>正常</option>
						<option value="0" <c:if test="${manager.state==0}"> selected</c:if>>禁用</option>
					</select>
			</td>
		</tr>
		<tr  style="display:none;">
			<td class="td_left">备注<span class="sp_span">:</span></td>
			<td class="td_right_two">
				<textarea class="validate" name="note" rows="3" cols="40" type="text" maxdatalength="80" >${manager.note}</textarea>
			</td>
		</tr>
	</table>
</div>

<input type="hidden" name="mana_id" value="${manager.mana_id}"/>
<input type="hidden" class="mana_type"  name="mana_type" value="${manager.mana_type}">
<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>