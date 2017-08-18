<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title></title>


</head>
<body>
	<form id="validateForm" action="/admin/accountInterApprove/update"
		method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/accountInterApprove/approveCommon.jsp"%>
			<div class="row50 operbtndiv">
				<input type="hidden" name="token" value="${token}"> <input
					type="button" value="提交" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/accountInterApprove/list')" value="返回" />
			</div>
		</div>
	</form>
</body>
</html>

