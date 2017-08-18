<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">摇一摇标识<span class="sp_span">:</span></td>
			<td class="td_right_two">
				<input  class="text " type="text" name="shake_id"    tipmsg="摇一摇标识" 
		 		  maxlength='20'  maxdatalength='20'    value="${shakeWinningRecord.shake_id}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">奖项设置标识<span class="sp_span">:</span></td>
			<td class="td_right_two">
				<input  class="text " type="text" name="awards_id"    tipmsg="奖项设置标识" 
		 		  maxlength='20'  maxdatalength='20'    value="${shakeWinningRecord.awards_id}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">中奖人帐户<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text" name="login_name"  isrequired='yes'  tipmsg="中奖人帐户" 
		 		  maxlength='60'  maxdatalength='60'    value="${shakeWinningRecord.login_name}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">中奖人标识<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text" name="account_id"  isrequired='yes'  tipmsg="中奖人标识" 
		 		  maxlength='20'  maxdatalength='20'    value="${shakeWinningRecord.account_id}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">中奖时间<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text" name="draw_time"  isrequired='yes'  tipmsg="中奖时间" 
		 		  maxlength='20000'  maxdatalength='20000'    value="${shakeWinningRecord.draw_time}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">是否已领奖<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text" name="is_draw"  isrequired='yes'  tipmsg="是否已领奖" 
		 		  maxlength='4'  maxdatalength='4'    value="${shakeWinningRecord.is_draw}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">领奖时间<span class="sp_span">:</span></td>
			<td class="td_right_two">
				<input  class="text " type="text" name="accept_time"    tipmsg="领奖时间" 
		 		  maxlength='20000'  maxdatalength='20000'    value="${shakeWinningRecord.accept_time}"/>
			</td>
		</tr>
<c:if test="${shakeWinningRecord.wr_id!=null}">
	<input type="hidden" name="wr_id" value="${shakeWinningRecord.wr_id}"/>
</c:if>

<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
	</table>
</div>

