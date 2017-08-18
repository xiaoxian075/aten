<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>新增用户</title>
<%@ include file="/WEB-INF/common/img_control.jsp"%>
<%@ include file="/WEB-INF/common/ueditor.jsp"%>
</head>
<body>
	<form id="validateForm" action="/admin/manager/insert" method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/manager/common.jsp"%>
			<input type="hidden" name="token" value="${token}">
			<div class="row50 operbtndiv">
				<input type="button" value="新增用户" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/manager/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

