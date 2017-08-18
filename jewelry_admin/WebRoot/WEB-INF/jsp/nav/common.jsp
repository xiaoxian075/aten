<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">导航名称<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="nav_name" isrequired='yes' tipmsg="导航名称不能为空"
				maxlength='50' value="${nav.nav_name}" /></td>
		</tr>
		<tr>
			<td class="td_left">导航图标<span class="sp_span">:</span></td>
			<td class="td_right_two"><c:set var="one_img_name"
					value="nav_ico" /> <c:set var="one_img_url" value="${nav.nav_ico}" />
				<c:set var="one_img_tip" value="请上传图标!" /> <c:set var="one_img_must"
					value="no" /> <%@ include file="/WEB-INF/common/one_image_show.jsp"%>
			</td>
		</tr>
		<tr>
			<td class="td_left">导航位置<span class="must_span">*</span></td>
			<td class="td_right_two"><select class="validate"
				name="nav_post" isrequired='yes' type="select" tipmsg="请选择导航位置"
				widthtip="100">
					<option value="">请选择</option>
					<option value="0"
						<c:if test="${nav.nav_post==0}"> selected</c:if>>首页</option>
		</select>  
	</td>
</tr>

		<tr>
			<td class="td_left">链接地址<span class="sp_span">:</span></td>
			<td class="td_right_two">
				<input  class="text " type="text" name="link_url"    tipmsg=""  style="width:380px;"
		 		  maxlength='100'    value="${nav.link_url}"/>
			</td>
		</tr>
<tr>
	<td class="td_left">状态<span class="must_span">*</span></td>
	<td class="td_right_two">	  
 		<select class="validate"  name="state" isrequired='yes' type="select" tipmsg="请选择状态" widthtip="100">
			<option value="">请选择</option>
			<option value="0" <c:if test="${nav.state==0}"> selected</c:if>>正常</option>
<option value="1" <c:if test="${nav.state==1}"> selected</c:if>>禁用</option>

		</select>  
	</td>
</tr>

		<tr>
			<td class="td_left">排序<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text" name="sort_no"  isrequired='yes'  tipmsg="排序" 
		 		  maxlength='6'    value="${nav.sort_no}"/>
			</td>
		</tr>
<c:if test="${nav.nav_id!=null}">
	<input type="hidden" name="nav_id" value="${nav.nav_id}"/>
</c:if>

	</table>
</div>

