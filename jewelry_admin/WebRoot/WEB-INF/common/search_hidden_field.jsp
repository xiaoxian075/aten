<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- 用于存放搜索数据的隐藏域用于回选-->
<c:if test="${!empty shfbList}">
	<c:forEach items="${shfbList}" var="item">
		<input type="hidden" name="${item.fieldName}"
			value="${item.fieldValue}" />
	</c:forEach>
</c:if>
