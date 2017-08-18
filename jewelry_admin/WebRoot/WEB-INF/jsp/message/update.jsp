<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>修改消息</title>
</head>
<body>
	<form id="validateForm" action="/admin/message/update" method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/message/common.jsp"%>
			<div class="row50 operbtndiv">
				<input type="hidden" name="token" value="${token}"> <input
					type="button" value="修改消息" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/message/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

