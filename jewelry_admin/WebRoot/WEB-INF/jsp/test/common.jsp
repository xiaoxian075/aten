<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">1<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="goods_id" tipmsg="1" maxlength='20' maxdatalength='20'
				value="${test.goods_id}" /></td>
		</tr>
		<tr>
			<td class="td_left">1<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="presale_endtime" tipmsg="1" maxlength='15,0'
				maxdatalength='15,0' value="${test.presale_endtime}" /></td>
		</tr>
		<tr>
			<td class="td_left">0：预售结束时间后 1：支付成功后<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="send_time_type" tipmsg="0：预售结束时间后 1：支付成功后" maxlength='4'
				maxdatalength='4' value="${test.send_time_type}" /></td>
		</tr>
		<tr>
			<td class="td_left">1<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="send_day_num" tipmsg="1" maxlength='4' maxdatalength='4'
				value="${test.send_day_num}" /></td>
		</tr>
		<tr>
			<td class="td_left">1<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="send_time" tipmsg="1" maxlength='15,0' maxdatalength='15,0'
				value="${test.send_time}" /></td>
		</tr>
		<tr>
			<td class="td_left">最多三位<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="limit_buy_num" tipmsg="最多三位" maxlength='4' maxdatalength='4'
				value="${test.limit_buy_num}" /></td>
		</tr>
		<c:if test="${test.full_id!=null}">
			<input type="hidden" name="full_id" value="${test.full_id}" />
		</c:if>

		<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
	</table>
</div>

