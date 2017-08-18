<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
        $(function () {
            // 时间设置
            $('#start_time,#end_time').datetimepicker({
                lang: 'ch',
                timepicker: true,
                format: 'Y-m-d H:i',
                formatDate: 'Y-m-d H:i',
            });
//            $("#start_time").datetimepicker({
//			format:'Y-m-d H:i:s',
//			language: 'zh',
//			minTime: true,
//			maxTime: true,
//			timepicker:true,
//			onSelectDate: function () {
//				var starttime=$("#start_time").val();
//				$("#end_time").datetimepicker({
//					minDate: starttime,
//					maxDate: false,
//				});
//			},
//		});
//
//		$("#end_time").datetimepicker({
//			format:'Y-m-d H:i:s',
//			language: 'zh',
//			minTime: true,
//			maxTime: true,
//			timepicker:true,
//			onSelectDate: function () {
//				var endtime=$("#end_time").val();
//				$("#start_time").datetimepicker({
//					minDate: false,
//					maxDate: endtime,
//				});
//			},
//		});
        });
    </script>
<div class="table_div">
	<table width="100%">


		<tr>
			<td class="td_left">广告名称<span class="must_span">*</span></td>
			<td class="td_right_two"><input class="text validate"
				type="text" name="ad_name" isrequired='yes' tipmsg="广告名称" maxlength='30'
				maxdatalength='30' value="${ad.ad_name}" /></td>
		</tr>
		<tr>
			<td class="td_left">广告图片<span class="must_span">*</span></td>
			<td class="td_right_two"><c:set var="one_img_name"
					value="img_path" /> <c:set var="one_img_url" value="${ad.img_path}" />
				<c:set var="one_img_tip" value="广告图片上传" /> <%@ include
					file="/WEB-INF/common/one_image_show.jsp"%>
			</td>
		</tr>

		<tr>
			<td class="td_left">广告跳转类型<span class="must_span">*</span></td>
			<td class="td_right_two"><select class="validate" name="ad_type"
				isrequired='yes' type="select" tipmsg="广告跳转类型" widthtip="130">
                   <option value="">请选择</option>
                    <c:if test="${!empty cfg_ad_type}">
                     	<c:forEach items="${cfg_ad_type}" var="item" varStatus="status">
		                 <option value="${item.para_key}"  <c:if test="${item.para_key==ad.ad_type}">selected="selected"</c:if>>${item.para_name}</option>
	                   </c:forEach>
                    </c:if>
				</select>
			</td>
		</tr>

		<tr>
			<td class="td_left">链接地址<span class="sp_span">:</span></td>
			<td class="td_right_two">
				<input  class="text validate" type="text" name="link_url"  style="width:380px;"
		 		  maxlength='120'  maxdatalength='120'   value="${ad.link_url}"/>
			</td>
		</tr>
		<tr >
			<td class="td_left">开始时间<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate" isrequired='yes' tipmsg="开始时间" type="text" id="start_time" name="start_time"
		 		  maxlength='7' value="${ad.start_time}"/>
			</td>
		</tr>
		<tr >
			<td class="td_left">结束时间<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate" isrequired='yes' tipmsg="结束时间"  type="text" id="end_time" name="end_time"
		 		  maxlength='7' value="${ad.end_time}"/>
			</td>
		</tr>
		<tr>
			<td class="td_left">备注<span class="sp_span">:</span></td>
			<td class="td_right_two">
		 		  <textarea class="validate" name="note" rows="3" cols="40" type="text"
 					maxlength="255"  >${ad.note}</textarea>
			</td>
		</tr>
	<tr>
		<td class="td_left">状态<span class="must_span">*</span></td>
		<td class="td_right_two">
	 		<select class="validate"  name="state" isrequired='yes' type="select" tipmsg="状态" widthtip="100">
				<option value="">请选择</option>
				<option value="1" <c:if test="${ad.state==1}"> selected</c:if>>启用</option>
				<option value="0" <c:if test="${ad.state==0}"> selected</c:if>>禁用</option>
						</select>
					</td>
			</tr>

		<tr>
			<td class="td_left">排序<span class="must_span">*</span></td>
			<td class="td_right_two">
				<input  class="text validate sort_no" type="text" name="sort_no" datatype="jsInt"  setwidth="120"
				isrequired='yes' tipmsg="排序"
		 		  maxlength='6'    value="${ad.sort_no}"/>
			</td>
		</tr>
<c:if test="${ad.ad_id!=null}">
	<input type="hidden" name="ad_id" value="${ad.ad_id}"/>
</c:if>
<input type="hidden" name="adv_id" value="${adv_id}"/>
		<input type="hidden" name="parameter_id" value="${parameter_id}"/>
	</table>
</div>
<%@ include file="/WEB-INF/common/list_hidden_value.jsp"%>
<%@ include file="/WEB-INF/common/search_hidden_field.jsp"%>
