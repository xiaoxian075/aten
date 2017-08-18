<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>新增优惠券</title>
</head>
<body>
	<form id="validateForm" action="/admin/coupon/insert" method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/coupon/common.jsp"%>
			<div class="row50 operbtndiv">
				<input type="hidden" name="token" value="${token}"> <input
					type="button" value="发布" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/coupon/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

