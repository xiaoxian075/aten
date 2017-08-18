<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">帐户标识<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text" name="account_id"  isrequired='yes'  tipmsg="帐户标识" 
		 		  maxlength='20'  maxdatalength='20'    value="${sysmsg.account_id}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">消息标题<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text" name="sysmsg_title"  isrequired='yes'  tipmsg="消息标题" 
		 		  maxlength='50'  maxdatalength='50'    value="${sysmsg.sysmsg_title}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">消息简介<span class="sp_span">:</span></td>
			<td class="td_right_two">
				<input  class="text " type="text" name="introduction"    tipmsg="消息简介" 
		 		  maxlength='200'  maxdatalength='200'    value="${sysmsg.introduction}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">消息内容<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text" name="sysmsg_content"  isrequired='yes'  tipmsg="消息内容" 
		 		  maxlength='20000'  maxdatalength='20000'    value="${sysmsg.sysmsg_content}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">消息发布时间<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text" name="in_date"  isrequired='yes'  tipmsg="消息发布时间" 
		 		  maxlength='20000'  maxdatalength='20000'    value="${sysmsg.in_date}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">是否已读<span class="sp_span">:</span></td>
			<td class="td_right_two">
				<input  class="text " type="text" name="is_read"    tipmsg="是否已读" 
		 		  maxlength='4'  maxdatalength='4'    value="${sysmsg.is_read}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">类型<span class="sp_span">:</span></td>
			<td class="td_right_two">
				<input  class="text " type="text" name="skip_type"    tipmsg="类型" 
		 		  maxlength='4'  maxdatalength='4'    value="${sysmsg.skip_type}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">id<span class="sp_span">:</span></td>
			<td class="td_right_two">
				<input  class="text " type="text" name="relation_id"    tipmsg="id" 
		 		  maxlength='60'  maxdatalength='60'    value="${sysmsg.relation_id}"/>
			</td>
		</tr>
<c:if test="${sysmsg.sysmsg_id!=null}">
	<input type="hidden" name="sysmsg_id" value="${sysmsg.sysmsg_id}"/>
</c:if>

<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
	</table>
</div>

