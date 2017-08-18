<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">消息标题<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="msg_title" isrequired='yes' tipmsg="消息标题"
				maxlength='50' maxdatalength='50' value="${message.msg_title}" /></td>
		</tr>
		<tr>
			<td class="td_left">消息简介<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="introduction" tipmsg="消息简介" maxlength='200'
				maxdatalength='200' value="${message.introduction}" /></td>
		</tr>
		<tr>
			<td class="td_left">消息类型<span class="must_span">*</span></td>
			<td class="td_right" colspan="3"><select class="validate"
				name="msg_type" isrequired="yes" type="select" tipmsg="消息类型"
				widthtip="70">
					<option value="">请选择</option>
					<option value="0"
						<c:if test="${0==message.msg_type}"> selected</c:if>>公告类型</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="td_left">消息内容<span class="must_span">*</span></td>
			<td  class="td_right" colspan="3">
				<textarea class="validate" isrequired='yes' tipmsg="消息内容" name="msg_content" rows="3" cols="40" type="text" maxdatalength="100" >${message.msg_content}</textarea>
			</td>
		</tr>
		<tr>
			<td class="td_left">发布时间<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input class="text validate" type="text" id="in_date" name="in_date" isrequired='yes' tipmsg="发布时间" maxlength='20' maxdatalength='20' value="${message.in_date}"/> 
		 	</td>
		</tr>
<c:if test="${message.msg_id!=null}">
	<input type="hidden" name="msg_id" value="${message.msg_id}"/>
</c:if>

<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
	</table>
</div>
<script type="text/javascript">
    $(function () {
    	// 时间设置
		$('#in_date').datetimepicker({
			format:'Y-m-d H:i:s'
		});
    });
</script>
