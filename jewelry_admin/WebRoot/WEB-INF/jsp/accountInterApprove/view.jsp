<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>查看用户</title>
</head>
<body>
	<form id="validateForm" action="" method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/accountInterApprove/viewCommon.jsp"%>
			<div class="row50 operbtndiv">
				<input type="button" class="btn return"
					onclick="returnGo('/admin/accountInterApprove/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

