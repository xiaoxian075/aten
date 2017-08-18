<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
    $(function () {
        // 时间设置
        $('.time-input').datetimepicker({
            lang: 'ch',
            timepicker: true,
            format: 'Y-m-d H:i',
            formatDate: 'Y-m-d H:i',
        });
    });
</script>
<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">活动名称<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text" name="shake_name"  isrequired='yes'  tipmsg="活动名称"  style="width: 350px"
		 		  maxlength='16'  maxdatalength='16'    value="${shake.shake_name}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">开始时间<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate time-input" type="text" name="start_time"  isrequired='yes'  tipmsg="开始时间"
		 		  maxlength='30'  maxdatalength='30'    value="${shake.start_time}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">结束时间<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate time-input" type="text" name="end_time"  isrequired='yes'  tipmsg="结束时间"
		 		  maxlength='30'  maxdatalength='30'    value="${shake.end_time}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">每人最大中奖次数<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text" name="everyone_draw_num"  isrequired='yes'  tipmsg="每人最大中奖次数" 
		 		  maxlength='11'  maxdatalength='11'    value="${shake.everyone_draw_num}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">每日抽奖次数<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text" name="draw_num_day"  isrequired='yes'  tipmsg="每日抽奖次数" 
		 		  maxlength='3'  maxdatalength='3'    value="${shake.draw_num_day}"/>
			</td>
		</tr>
		<%--<tr >--%>
			<%--<td class="td_left">预计活动参考人数<span class="must_span">*</span></td>--%>
			<%--<td class="td_right_two">--%>
				<%--<input  class="text validate" type="text" name="lottery_activity_num"  isrequired='yes'  tipmsg="预计活动参考人数" --%>
		 		  <%--maxlength='11'  maxdatalength='11'    value="0"/>--%>
			<%--</td>--%>
		<%--</tr>--%>
		<input name="lottery_activity_num" type="hidden" value="0"/>
		<input name="draw_out_time" type="hidden" value="1"/>
		<%-- <tr>
			<td class="td_left">中奖领取过期时间<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate " type="text" name="draw_out_time"  isrequired='yes'  tipmsg="中奖领取过期时间"
						maxlength='11'  maxdatalength='11'    value="${shake.draw_out_time}"/>
			</td>
		</tr> --%>
		<tr>
			<td class="td_left">中奖概率<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text" name="probability_winning"  isrequired='yes'  tipmsg="中奖概率"
						maxlength='3'  maxdatalength='3'    value="${shake.probability_winning}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">状态<span class="sp_span">:</span></td>
			<td class="td_right_two">
				<select class="validate " name="state"  isrequired="yes" type="select" tipmsg="状态" widthtip="70">
					<option value="">请选择</option>
					<option value="1" <c:if test="${shake.state==1}"> selected</c:if>>启用</option>
					<option value="0" <c:if test="${shake.state==0}"> selected</c:if>>禁用</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="td_left">活动规则:</td>
			<td class="td_right_two">
				<textarea  name="activity_rule" rows="5" cols="50"
						   maxlength='600'  maxdatalength='600'  >${shake.activity_rule}</textarea>
			</td>
		</tr>
<c:if test="${shake.shake_id!=null}">
	<input type="hidden" name="parameter_id" value="${shake.shake_id}"/>
	<input type="hidden" name="shake_id" value="${shake.shake_id}"/>
</c:if>

<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
	</table>
</div>

