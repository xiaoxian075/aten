<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>查看地名</title>
</head>
<body>
	<form id="validateForm" action="/admin/place/insert" method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/area/viewcommon.jsp"%>
			<div class="row50 operbtndiv">
				<input type="button" class="btn return"
					onclick="returnGo('/admin/area/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

