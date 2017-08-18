<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="detail_img_list">
	<img class="img_height120 separate" id="img_${one_img_name}"
		<c:choose>
	   <c:when test="${!empty one_img_url}"> 
	   		src="${one_img_url}" 	
	   </c:when>
	   <c:otherwise>
	   		src="/include/admin/image/no_picture.png" 	
	   </c:otherwise>
	 </c:choose> />
	<input type="hidden" class="validate" changetip="${one_img_name}_div"
		id="hidden_${one_img_name}"
		<c:if test="${empty one_img_must}">isrequired="yes"</c:if>
		name="${one_img_name}" value="${one_img_url}" />
	<div id="fileQueue_${one_img_name}"></div>
	<div id="${one_img_name}_div" setwidth="200" setheight="25"
		tipmsg="${one_img_tip}">
		<table>
			<tr>
				<td><input type="file" id="file_${one_img_name}" /></td>
				<td><img class="cancel" src="/include/admin/image/cancel.png"
					onclick="cancelImg('${one_img_name}');" /></td>
			</tr>
		</table>
	</div>
	<script>image_one_upload("${one_img_name}");</script>
</div>
