<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>修改积分商品</title>
</head>
<body>
	<form id="validateForm" action="/admin/integral/update" method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/integral/common.jsp"%>
			<div class="row50 operbtndiv">
				<input type="hidden" name="token" value="${token}"> <input
					type="button" value="修改积分商品" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/integral/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

