<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>修改角色</title>
<script type="text/javascript" src="/include/admin/js/role.js"></script>
<link rel="stylesheet" type="text/css"
	href="/include/admin/css/role.css" />
</head>
<body>
	<form id="validateForm" action="/admin/role/update" method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/role/common.jsp"%>
			<div class="row50 operbtndiv">
				<input type="button" value="修改角色" class="btn operbtn"
					onclick="roleSubmitData();" /> <input type="button"
					class="btn return" onclick="returnGo('/admin/role/list')"
					value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

