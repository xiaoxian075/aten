<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>修改菜单</title>
</head>
<body>
	<form id="validateForm" action="/admin/sysmenu/update" method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/sysmenu/common.jsp"%>
			<div class="row50 operbtndiv">
				<input type="button" value="修改菜单" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/sysmenu/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>
