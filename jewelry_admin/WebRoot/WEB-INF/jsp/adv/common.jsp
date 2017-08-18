<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="table_div">
	<table width="100%">
		<c:if test="${!empty adv.adv_id}">
			<input type="hidden" name="adv_id" value="${adv.adv_id}">
		</c:if>
		<tr>
			<td class="td_left">广告位编码<span class="must_span">*</span></td>
			<td class="td_right_two"><c:if test="${empty adv.adv_id}">
					<input class="text validate" type="text" name="adv_code"
						isrequired='yes' tipmsg="广告位编码" maxlength='25' maxdatalength='25'
						value="${adv.adv_code}" />
				</c:if> <c:if test="${!empty adv.adv_id}">
					${adv.adv_code}
					<input class="text" type="hidden" name="adv_code"
						value="${adv.adv_code}" />
				</c:if></td>
		</tr>
		<tr>
			<td class="td_left">所属终端<span class="must_span">*</span></td>
			<td class="td_right_two"><select class="validate"
				name="the_terminal" isrequired='yes' type="select" tipmsg="所属终端"
				widthtip="100">
					<option value="">请选择</option>
					<option value="1"
						<c:if test="${adv.the_terminal==1}"> selected</c:if>>APP</option>
					<option value="2" <c:if test="${adv.the_terminal==2}"> selected</c:if>>WEB</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="td_left">广告位名称<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text" name="adv_name"  isrequired='yes'  tipmsg="广告位名称" 
		 		  maxlength='50'  maxdatalength='50'   value="${adv.adv_name}"/>
			</td>
		</tr>

		<tr>
			<td class="td_left">广告位类型<span class="must_span">*</span></td>
			<td class="td_right_two">
				<select class="validate"  name="adv_type" isrequired='yes' type="select" tipmsg="广告位类型" widthtip="100">
					<option value="">请选择</option>
					<option value="1" <c:if test="${adv.adv_type==1}"> selected</c:if>>幻灯片广告</option>
					<option value="0" <c:if test="${adv.adv_type==0}"> selected</c:if>>图片广告</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="td_left">是否启用<span class="must_span">*</span></td>
			<td class="td_right_two">
				<select class="validate"  name="state" isrequired='yes' type="select" tipmsg="状态" widthtip="100">
					<option value="">请选择</option>
					<option value="1" <c:if test="${adv.state==1}"> selected</c:if>>启用</option>
					<option value="0" <c:if test="${adv.state==0}"> selected</c:if>>禁用</option>
				</select>
			</td>
		</tr>

		<tr>
			<td class="td_left">广告位介绍<span class="sp_span">:</span></td>
			<td class="td_right_two">
		 		  <textarea class="validate" name="adv_introduce" rows="3" cols="45"
		 		   type="text" maxdatalength="150" >${adv.adv_introduce}</textarea>
			</td>
		</tr>
		
	</table>
</div>