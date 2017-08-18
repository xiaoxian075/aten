<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title></title>


</head>
<body>
	<form id="validateForm" action="/admin/accountBalanceApprove/update"
		method="post">
		<div class="opercontent">
			<%@ include
				file="/WEB-INF/jsp/accountBalanceApprove/approveCommon.jsp"%>
			<div class="row50 operbtndiv">
				<input type="hidden" name="token" value="${token}"> <input
					type="button" value="提交" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/accountBalanceApprove/list')" value="返回" />
			</div>
		</div>
	</form>
</body>
</html>

