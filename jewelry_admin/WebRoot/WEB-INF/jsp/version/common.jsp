<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style type="text/css">
#hotel_policy_div div{
	margin-bottom:6px;
}
</style>
<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">更新系统<span class="must_span">*</span></td>
			<td class="td_right" colspan="3"><select class="validate" name="sys_type" isrequired="yes" type="select" tipmsg="更新系统"
				widthtip="70">
					<option value="">请选择</option>
					<option value="1" <c:if test="${1==version.sys_type}"> selected</c:if>>安卓</option>
					<option value="0" <c:if test="${0==version.sys_type}"> selected</c:if>>IOS</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="td_left">版本号<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text" name="update_version"  isrequired='yes'  tipmsg="版本号" 
		 		  maxlength='30'  maxdatalength='30'    value="${version.update_version}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">更新地址<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text" name="update_apk_url" style="width:380px;" isrequired='yes'  tipmsg="更新路径" 
		 		  maxlength='200'  maxdatalength='200'    value="${version.update_apk_url}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">数字版本号<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text" name="digital_version" isrequired='yes'  tipmsg="数字版本号" 
		 		  maxlength='2'  maxdatalength='2'    value="${version.digital_version}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">是否更新<span class="must_span">*</span></td>
			<td class="td_right" colspan="3"><select class="validate" name="is_update" isrequired="yes" type="select" tipmsg="是否更新"
				widthtip="70">
					<option value="">请选择</option>
					<option value="1" <c:if test="${1==version.is_update}"> selected</c:if>>是</option>
					<option value="0" <c:if test="${0==version.is_update}"> selected</c:if>>否</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="td_left">更新时间<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text" id="update_time" name="update_time"  isrequired='yes'  tipmsg="更新时间" 
		 		  maxlength='20000'  maxdatalength='20000'    value="${version.update_time}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">是否强制更新<span class="must_span">*</span></td>
			<td class="td_right" colspan="3"><select class="validate" name="force_update" isrequired="yes" type="select" tipmsg="是否强制更新"
				widthtip="70">
					<option value="">请选择</option>
					<option value="1" <c:if test="${1==version.force_update}"> selected</c:if>>是</option>
					<option value="0" <c:if test="${0==version.force_update}"> selected</c:if>>否</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="td_left">更新日志<span class="must_span">*</span></td>
			<td class="td_right_two">
				<%@ include file="/WEB-INF/jsp/version/policy.jsp"%>
			</td>
		</tr>
		<tr id="policyTr">
			<td class="td_left">
				<input type="button" value="新增更新日志" class="btn ol_greenbtn attrbtn addHotelAttr" onclick="addPolicy();"/>
			</td>
			<td class="td_right_two">
			</td>
		</tr>
<c:if test="${version.version_id!=null}">
	<input type="hidden" name="version_id" value="${version.version_id}"/>
</c:if>

<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
	</table>
</div>
<script type="text/javascript">
    $(function () {
    	// 时间设置
		$('#update_time').datetimepicker({
			minDate:new Date(),
			format:'Y-m-d H:i:s'
		});
    });
</script>
<script type="text/javascript">
	function wsSubmit(){
		var is_check = checkSubmitData();
		//验证酒店政策
		var hp_val="";
		var pos="###";
		$(".hp_txt").each(function(){
			hp_val += $(this).val().replace(pos,"")+pos;
		});
		$("#update_logs").val(hp_val);
		//验证是否成功
		if(is_check){
			$("#validateForm").submit();
		}
		goErrorPos();
	}
</script>