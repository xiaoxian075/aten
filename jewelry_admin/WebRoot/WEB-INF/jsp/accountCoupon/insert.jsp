<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>新增测试</title>
</head>
<body>
	<form id="validateForm" action="/admin/accountcoupon/insert"
		method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/accountCoupon/common.jsp"%>
			<div class="row50 operbtndiv">
				<input type="hidden" name="token" value="${token}"> <input
					type="button" value="新增测试" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/accountcoupon/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

