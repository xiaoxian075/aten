<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">创建时间<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="create_time" tipmsg="创建时间" maxlength='20000'
				maxdatalength='20000' value="${accountCoupon.create_time}" /></td>
		</tr>
		<tr>
			<td class="td_left">帐户id<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="account_id" tipmsg="帐户id" maxlength='20' maxdatalength='20'
				value="${accountCoupon.account_id}" /></td>
		</tr>
		<tr>
			<td class="td_left">优惠券id<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="coupon_id" tipmsg="优惠券id" maxlength='11' maxdatalength='11'
				value="${accountCoupon.coupon_id}" /></td>
		</tr>
		<tr>
			<td class="td_left">优惠券名称<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="coupon_name" tipmsg="优惠券名称" maxlength='60' maxdatalength='60'
				value="${accountCoupon.coupon_name}" /></td>
		</tr>
		<tr>
			<td class="td_left">优惠券面值<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="coupon_amount" tipmsg="优惠券面值" maxlength='10,0'
				maxdatalength='10,0' value="${accountCoupon.coupon_amount}" /></td>
		</tr>
		<tr>
			<td class="td_left">使用门槛<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="use_amount" tipmsg="使用门槛" maxlength='10,0'
				maxdatalength='10,0' value="${accountCoupon.use_amount}" /></td>
		</tr>
		<tr>
			<td class="td_left">使用类型 1 全部商品, 2 指定品类 <span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="use_type" tipmsg="使用类型 1 全部商品, 2 指定品类 " maxlength='11'
				maxdatalength='11' value="${accountCoupon.use_type}" /></td>
		</tr>
		<tr>
			<td class="td_left">截止有效期<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="end_time" tipmsg="截止有效期" maxlength='20000'
				maxdatalength='20000' value="${accountCoupon.end_time}" /></td>
		</tr>
		<tr>
			<td class="td_left">0 未使用 1 已使用 2 已过期<span class="sp_span">:</span></td>
			<td class="td_right_two"><input class="text " type="text"
				name="state" tipmsg="0 未使用 1 已使用 2 已过期" maxlength='11'
				maxdatalength='11' value="${accountCoupon.state}" /></td>
		</tr>
		<c:if test="${accountCoupon.id!=null}">
			<input type="hidden" name="id" value="${accountCoupon.id}" />
		</c:if>

		<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
	</table>
</div>

