<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>修改余额调账</title>
</head>
<body>
	<form id="validateForm" action="/admin/accountBalanceApprove/update"
		method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/accountBalanceApprove/common.jsp"%>
			<div class="row50 operbtndiv">
				<input type="hidden" name="token" value="${token}"> <input
					type="button" value="修改余额调账" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/accountbalanceapprove/list')"
					value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

