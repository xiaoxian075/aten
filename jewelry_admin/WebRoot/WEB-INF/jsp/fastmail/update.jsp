<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>修改物流承运商</title>
</head>
<body>
	<form id="validateForm" action="/admin/fastmail/update" method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/fastmail/common.jsp"%>
			<div class="row50 operbtndiv">
				<input type="hidden" name="token" value="${token}"> <input
					type="button" value="修改物流承运商" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/fastmail/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

