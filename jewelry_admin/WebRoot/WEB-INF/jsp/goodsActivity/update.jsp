<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>修改商品活动</title>
</head>
<body>
	<form id="validateForm" action="/admin/goodsactivity/update"
		method="post">
		<div class="opercontent">
			<%@ include file="/WEB-INF/jsp/goodsActivity/common.jsp"%>
			<div class="row50 operbtndiv">
				<input type="hidden" name="token" value="${token}"> <input
					type="button" value="修改商品活动" class="btn operbtn"
					onclick="submitData();" /> <input type="button" class="btn return"
					onclick="returnGo('/admin/goodsactivity/list')" value="返回列表" />
			</div>
		</div>
	</form>
</body>
</html>

