<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	function typeChange(){
		var var_id =$("#var_id").val();
		if(var_id!=""){
			$("#parameter_id").val(var_id);
			$("#validateForm").attr("action","/admin/sysconfig/edit").submit();
		}else{
			$("#validateForm").attr("action","/admin/sysconfig/add").submit();
		}
	}
</script>
<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">变量名称<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="var_name" isrequired="yes" tipmsg="变量名称"
				maxlength="30" maxdatalength="30" value="${sysconfig.var_name}" /></td>
		</tr>
		<tr>
			<td class="td_left">变量类型<span class="must_span">*</span></td>
			<td class="td_right_two"><select class="validate select"
				onchange="typeChange()" name="var_type" isrequired="yes"
				type="select" tipmsg="变量类型" widthtip="70">
					<c:if test="${!empty commparaList}">
						<c:forEach items="${commparaList}" var="item" varStatus="status">
							<option value="${item.para_key}"
								<c:if test="${item.para_key==sysconfig.var_type}">selected</c:if>>${item.para_name}</option>
							</c:forEach>
						</c:if>
					</select>
 					
			</td>
		</tr>
		<tr>
			<td class="td_left" width="320">变量值<span class="must_span">*</span></td>
			<td  class="td_right_two">
				<c:if test="${sysconfig.var_type=='2'}"><!-- 图片控件 -->
					 <c:set var="one_img_name" value="var_value"/>
					 <c:set var="one_img_url" value="${sysconfig.var_value}"/>
					 <c:set var="one_img_tip" value="请上传图片"/>
					 <%@ include file="/WEB-INF/common/one_image_show.jsp"%>
 				</c:if>
 				<c:if test="${sysconfig.var_type=='1'}"><!-- 编辑框 -->
 					 <textarea id="var_value_textarea" name="var_value">${sysconfig.var_value}</textarea>
		 			 <script>
				 			  var editor =new UE.ui.Editor();
				 			  UE.getEditor('var_value_textarea');
		 			 </script> 
 				</c:if>
 				<c:if test="${sysconfig.var_type=='0'}"><!-- 文本框 -->
 					<div  class="f_left tb_value">
	 					<input  class="text validate" type="text" name="var_value"  isrequired="yes"  tipmsg="变量值" 
	 					maxlength="600"  value="${sysconfig.var_value}"/>
	 				</div>
 				</c:if>
 				<c:if test="${sysconfig.var_type=='4'}"><!-- select框-->
 					<div  class="f_left tb_value">
 						<select class="validate"  name="var_value"  isrequired="yes" type="select" tipmsg="变量值" widthtip="70">
							<option value="">请选择</option>
							<option value="1"
								<c:if test="${sysconfig.var_value=='1'}"> selected</c:if>>是</option>
							<option value="0" <c:if test="${sysconfig.var_value=='0'}"> selected</c:if>>否</option>
						</select>
	 				</div>
 				</c:if>
 				<c:if test="${sysconfig.var_type=='3'}"><!-- 文本域-->
 					<div  class="f_left tb_value">
 						<textarea class="validate textarea" name="var_value" isrequired="yes" rows="3" cols="40" type="textarea" 
 							maxlength="600"  tipmsg="变量值" >${sysconfig.var_value}</textarea>
	 				</div>
 				</c:if>
			</td>
		</tr>
		<tr>
			<td class="td_left">变量描述<span class="must_span">*</span></td>
			<td  class="td_right_two">
				<textarea class="validate textarea" name="var_desc" rows="3" cols="40" type="text" tipmsg="变量描述"
 					maxlength="200" setheight ="200" isrequired="yes">${sysconfig.var_desc}</textarea>
			</td>
		</tr>
		<tr>
			<td class="td_left">排序<span class="must_span">*</span></td>
			<td  class="td_right_two">
				<input  class="text validate sort_no" type="text" name="sort_no"  isrequired="yes"  tipmsg="排序"  widthtip="70"
 					maxlength="6"  datatype="jsInt" value="${sysconfig.sort_no}"/>
			</td>
		</tr>
	</table>
</div>


 		<input type="hidden" id="parameter_id" name="parameter_id" />
 		<input  type="hidden" id="var_id" name="var_id" value="${sysconfig.var_id}"/>
		<input  type="hidden" name="var_group" value="1"/>
		<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>