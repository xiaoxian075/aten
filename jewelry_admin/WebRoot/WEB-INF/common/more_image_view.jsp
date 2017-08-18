<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:if test="${empty more_img_num}">
	<c:set var="more_img_num" value="5" />
</c:if>

<!-- 初始个数 -->
<c:forEach var="index" begin="1" end="${more_img_num}" step="1">
	<c:set var="is_flag" value="0" />
	<!-- 默认无回选值 -->
	<c:set var="default_img" value="/include/admin/image/no_picture.png" />
	<!-- 已选值个数 -->
	<c:if test="${!empty more_img_url}">
		<c:forEach var="imgUrl" items="${fn:split(more_img_url,',')}"
			varStatus="status">
			<c:if test="${index==status.count}">
				<c:set var="is_flag" value="1" />
				<c:set var="default_img" value="${ossImgServerUrl}${imgUrl}" />
				<img class="img_height120 separate" id="img_detail_${index}"
					src="${default_img}" />
			</c:if>
		</c:forEach>
	</c:if>

</c:forEach>
