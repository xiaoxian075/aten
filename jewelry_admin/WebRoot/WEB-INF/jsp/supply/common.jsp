<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/common/img_control.jsp"%>
<%@ include file="/WEB-INF/common/timepicker.jsp"%>
<div class="table_div">
	<table width="100%">
		<tr>
			<td class="td_left">供应商名称<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="supply_name" isrequired="yes" tipmsg="供应商名称"
				maxlength='100' maxdatalength='100' value="${supply.supply_name}" />
			</td>
		</tr>
		<tr>
			<td class="td_left">联系人<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="supply_contacts" isrequired="yes" tipmsg="联系人"
				maxlength='50' maxdatalength='50' value="${supply.supply_contacts}" />
			</td>
		</tr>
		<tr>
			<td class="td_left">联系电话<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="supply_contacts_phone" isrequired="yes"
				tipmsg="联系电话" maxlength='20' maxdatalength='20'
				value="${supply.supply_contacts_phone}" /></td>
		</tr>
		<tr>
			<td class="td_left">有效期<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate w120"
				type="text" id="valid_time_start" name="valid_time_start"
				isrequired='yes' tipmsg="请选择有效期开始时间" maxlength='20'
				maxdatalength='20' value="${supply.valid_time_start}" /> - <input
				class="text   w120 validate" type="text" id="valid_time_end"
				name="valid_time_end" isrequired='yes' tipmsg="请选择有效期结束时间"
				maxlength='20' maxdatalength='20' value="${supply.valid_time_end}" />
			</td>
		</tr>
		<tr>
			<td class="td_left">供应商营业执照号码<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="license_number" isrequired="yes"
				datatype="jsLetterOrNum" tipmsg="供应商营业执照号码" maxlength='60'
				maxdatalength='60' value="${supply.license_number}" /></td>
		</tr>
		<tr>
			<td class="td_left">供应商营业执照照片<span class="must_span">*</span></td>
			<td class="td_right_two"><c:set var="one_img_name"
					value="license_picture" /> <c:set var="one_img_url"
					value="${supply.license_picture}" /> <c:set var="one_img_tip"
					value="供应商营业执照照片" /> 
					<c:set var="one_img_proposal" value="图片大小不能大于2M"/>
					<%@ include file="/WEB-INF/common/one_image_show.jsp"%>
			</td>
		</tr>
		<tr>
			<td class="td_left">供应商法人姓名<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate "
				type="text" name="legal_name" isrequired="yes" tipmsg="供应商法人姓名"
				maxlength='50' maxdatalength='50' value="${supply.legal_name}" /></td>
		</tr>
		<tr>
			<td class="td_left">供应商法人身份证号码<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate "
				type="text" name="legal_id_card_number" isrequired="yes"
				datatype="jsLetterOrNum" tipmsg="供应商法人身份证号码" maxlength='20'
				maxdatalength='64' value="${supply.legal_id_card_number}" /></td>
		</tr>
		<tr>
			<td class="td_left">供应商法人身份证照片<span class="must_span">*</span></td>
			<td class="td_right_two"><c:set var="one_img_name"
					value="legal_id_card_picture" /> <c:set var="one_img_url"
					value="${supply.legal_id_card_picture}" /> <c:set
					var="one_img_tip" value="供应商法人身份证照片" /> 
					<c:set var="one_img_proposal" value="图片大小不能大于2M"/>
					<%@ include
					file="/WEB-INF/common/one_image_show.jsp"%>
			</td>
		</tr>
		<tr>
			<td class="td_left">归属地<span class="must_span">*</span></td>
			<td class="td_right_two">
				<div id="area_id_div" tipmsg="归属地" setwidth="200" setheight="25"></div>
				<input class="validate" changetip="area_id_div" type="hidden"
				id="area_id" name="the_area" isrequired='yes'
				value="${supply.the_area}" />
			</td>
		</tr>
		<tr>
			<td class="td_left">排序<span class="must_span">*</span></td>
			<td class="td_right" colspan="3"><input
				class="text validate sort_no" type="text" name="sort_no"
				isrequired="yes" datatype="jsInt" widthtip="100" maxlength='6'
				maxdatalength='6' tipmsg="排序" value="${supply.sort_no}" /></td>
		</tr>
		<tr>
			<td class="td_left">是否启用<span class="must_span">*</span></td>
			<td class="td_right" colspan="3"><select class="validate"
				name="state" isrequired="yes" type="select" tipmsg="是否启用"
				widthtip="70">
					<option value="">请选择</option>
					<option value="1"
						<c:if test="${1==supply.state}"> selected</c:if>>启用</option>
						<option value="0" <c:if test="${0==supply.state}"> selected</c:if>>禁用</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="td_left">备注<span class="sp_span">:</span></td>
			<td class="td_right" colspan="3">
				<textarea class="validate" name="note" rows="3" cols="40" type="text" maxlength='200' maxdatalength='200'>${supply.note}</textarea>
			</td>
		</tr>
<c:if test="${supply.supply_id!=null}">
	<input type="hidden" name="supply_id" value="${supply.supply_id}"/>
</c:if>

<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
	</table>
</div>
<script type="text/javascript">
    $(function () {
		$("#valid_time_start").datetimepicker({
			format:'Y-m-d',
			language: 'zh',
			minTime: true,
			maxTime: true,
			timepicker:false,
			onSelectDate: function () {
				var starttime=$("#valid_time_start").val();
				$("#valid_time_end").datetimepicker({
					minDate: starttime,
					maxDate: false,
				});
			},
		});
		
		$("#valid_time_end").datetimepicker({
			format:'Y-m-d',
			language: 'zh',
			minTime: true,
			maxTime: true,
            timepicker:false,
			onSelectDate: function () {
				var endtime=$("#valid_time_end").val();
				$("#valid_time_start").datetimepicker({
					minDate: false,
					maxDate: endtime,
				});
			},
		});

        //地区级联
        $("#area_id_div").cascadeSel({
            url:"/admin/area/normalList",
            name:"area_id",
            init_id:"${cfg_top_area}",
            li_id:"area_id",
            li_name:"area_name"
        });
    });
</script>
