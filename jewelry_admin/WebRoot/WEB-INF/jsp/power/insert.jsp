<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>新增权限</title>
<script type="text/javascript" src="/include/admin/js/power.js"></script>
</head>
<body>
	<form id="validateForm" action="/admin/power/insert" method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/power/common.jsp"%>
			<div class="row50 operbtndiv">
				<input type="button" value="新增权限" class="btn operbtn"
					onclick="powerSubmit();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/power/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

