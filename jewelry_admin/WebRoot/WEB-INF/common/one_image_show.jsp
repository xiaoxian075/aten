<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.communal.constants.Constant"%>
<table>
<tr>
<td>
<div class="detail_img_list">
	<img class="img_height120 separate" id="img_${one_img_name}"
		<c:choose>
	   <c:when test="${!empty one_img_url}"> 
	   		src="${ossImgServerUrl}${one_img_url}" 	
	   </c:when>
	   <c:otherwise>
	   		src="${noPicture}" 
	   </c:otherwise>
	 </c:choose> />
	<input type="hidden" class="validate" changetip="${one_img_name}_div"
		id="hidden_${one_img_name}"
		<c:if test="${empty one_img_must}">isrequired="yes"</c:if>
		name="${one_img_name}" value="${one_img_url}" />
	<div id="${one_img_name}_div" setwidth="200" setheight="25" tipmsg="${one_img_tip}">
		<table>
			<tr>
				<td style="padding-left:4px;"><div style="position: relative;" id="file_${one_img_name}"></div></td>
				<td style="padding-left:3px;"><a id="cancel_${one_img_name}"
					onclick="cancelImg('${one_img_name}');"
					class="imgCancel uploadify-cancelbutton" href="javascript:void(0)">取消</a>
				</td>
			</tr>
		</table>
	</div>
	<script>image_one_upload("${one_img_name}");</script>
</div>
</td>
<td valign="bottom" style="padding-bottom:15px;">
<span>
	<img style="width:22px;height:20px;" src="/include/admin/image/tip.png"/>
	<b style="color:#585858;">${one_img_proposal}</b>
</span>
</td>
</tr>
</table>
