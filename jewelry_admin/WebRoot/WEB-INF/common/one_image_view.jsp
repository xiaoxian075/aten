<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 查看视图 -->
<img class="img_height120 separate"
	<c:choose>
	   <c:when test="${!empty one_img_url}"> 
	   		src="${ossImgServerUrl}${one_img_url}" 	
	   </c:when>
	   <c:otherwise>
	   		src="/include/admin/image/no_picture.png" 	
	   </c:otherwise>
	 </c:choose> />
