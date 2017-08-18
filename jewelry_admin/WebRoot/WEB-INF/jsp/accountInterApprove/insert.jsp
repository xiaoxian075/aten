<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>新增积分调账</title>
</head>
<body>
	<form id="validateForm" action="/admin/accountInterApprove/insert"
		method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/accountInterApprove/common.jsp"%>
			<div class="row50 operbtndiv">
				<input type="hidden" name="token" value="${token}"> <input
					type="button" value="提交" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/integral/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

